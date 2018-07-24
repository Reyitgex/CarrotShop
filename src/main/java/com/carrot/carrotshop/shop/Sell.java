package com.carrot.carrotshop.shop;

import com.carrot.carrotshop.CarrotShop;
import com.carrot.carrotshop.Lang;
import com.carrot.carrotshop.ShopsData;
import com.carrot.carrotshop.ShopsLogs;
import com.carrot.carrotshop.decorator.InventoryDecorator;
import com.carrot.carrotshop.decorator.ItemStackDecorator;
import com.google.common.collect.ImmutableMap;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.TileEntityCarrier;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.item.inventory.type.InventoryRow;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

@ConfigSerializable
public class Sell extends Shop {
	@Setting
	private Inventory itemsTemplate;
	@Setting
	private Location<World> sellerChest;
	@Setting
	private int price;

	@SuppressWarnings("unused")
	public Sell() {
	}

	Sell(Player player, Location<World> sign) throws ExceptionInInitializerError {
		super(sign);
		String type = "Sell";
		if (!player.hasPermission("carrotshop.create.sell"))
			throw new ExceptionInInitializerError(Lang.SHOP_PERM.replace("%type%", type));
		Stack<Location<World>> locations = ShopsData.getItemLocations(player);
		if (locations.isEmpty())
			throw new ExceptionInInitializerError(Lang.SHOP_CHEST.replace("%type%", type));
		Optional<TileEntity> chestOpt = locations.peek().getTileEntity();
		if (!chestOpt.isPresent() || !(chestOpt.get() instanceof TileEntityCarrier))
			throw new ExceptionInInitializerError(Lang.SHOP_CHEST.replace("%type%", type));
		Inventory items = ((TileEntityCarrier) chestOpt.get()).getInventory();
		if (items.totalItems() == 0)
			throw new ExceptionInInitializerError(Lang.SHOP_CHEST_EMPTY);
		price = getPrice(sign);
		if (price < 0)
			throw new ExceptionInInitializerError(Lang.SHOP_PRICE);
		sellerChest = locations.peek();
		itemsTemplate = Inventory.builder().from(items).build(CarrotShop.getInstance());
		for (Inventory item : items.slots()) {
			if (item.peek().isPresent())
				itemsTemplate.offer(item.peek().get());
		}
		setOwner(player);
		ShopsData.clearItemLocations(player);
		player.sendMessage(Text.of(TextColors.DARK_GREEN, Lang.SHOP_DONE.replace("%type%", type)));
		done(player);
		info(player);
	}

	@Override
	public List<Location<World>> getLocations() {
		List<Location<World>> locations = super.getLocations();
		locations.add(sellerChest);
		return locations;
	}

	@Override
	public boolean update() {
		Optional<TileEntity> chest = sellerChest.getTileEntity();
		if (chest.isPresent() && chest.get() instanceof TileEntityCarrier) {
			Inventory chestInv = ((TileEntityCarrier) chest.get()).getInventory();
			if (chestInv.capacity() - chestInv.size() >= itemsTemplate.size()) {
				setOK();
				return true;
			}
		}
		setFail();
		return false;
	}

	@Override
	public void info(Player player) {
		if (!update()) {
			player.sendMessage(Text.of(TextColors.GOLD, Lang.SHOP_FULL));
			return;
		}

		player.sendMessage(
				Lang.build(
						Lang.SHOP_FORMAT_SELL,
						ImmutableMap.<String, Text>builder()
								.put("items", new InventoryDecorator(itemsTemplate).getTooltippedItemList())
								.put("price", Text.of(formatPrice(price)))
								.build()
				)
		);
	}

	@Override
	public boolean trigger(Player player) {
		Inventory inv = player.getInventory().query(QueryOperationTypes.INVENTORY_TYPE.of(InventoryRow.class));

		if (!hasEnough(inv, itemsTemplate)) {
			player.sendMessage(Text.of(TextColors.DARK_RED, Lang.SHOP_ITEMS));
			return false;
		}
		Optional<TileEntity> chest = sellerChest.getTileEntity();
		if (chest.isPresent() && chest.get() instanceof TileEntityCarrier) {
			Inventory chestInv = ((TileEntityCarrier) chest.get()).getInventory();
			if (chestInv.capacity() - chestInv.size() < itemsTemplate.size()) {
				player.sendMessage(Text.of(TextColors.GOLD, Lang.SHOP_FULL));
				update();
				return false;
			}
		}

		UniqueAccount buyerAccount = CarrotShop.getEcoService().getOrCreateAccount(getOwner()).get();
		if (buyerAccount.getBalance(getCurrency()).compareTo(BigDecimal.valueOf(price)) < 0) {
			player.sendMessage(Text.of(TextColors.DARK_RED, Lang.SHOP_OMONEY));
			return false;
		}

		Inventory invChest = ((TileEntityCarrier) chest.get()).getInventory();

		for (Inventory item : itemsTemplate.slots()) {
			if (item.peek().isPresent()) {
				Optional<ItemStack> template = getTemplate(inv, item.peek().get());
				if (template.isPresent()) {
					// Find all items that match in our inventory and remove the amount of them
					ItemStackDecorator neededStack = new ItemStackDecorator(item.peek().get().copy());
					Optional<ItemStack> items = inv.query(
							QueryOperationTypes.ITEM_STACK_CUSTOM.of(neededStack::exactItemMatch)
					).poll(neededStack.getQuantity());

					// Give items to chest
					if (items.isPresent()) {
						invChest.offer(items.get());
					} else {
						return false;
					}
				}
			}
		}

		UniqueAccount sellerAccount = CarrotShop.getEcoService().getOrCreateAccount(player.getUniqueId()).get();
		TransactionResult result = buyerAccount.transfer(sellerAccount, getCurrency(), BigDecimal.valueOf(price), CarrotShop.getCause());
		if (result.getResult() != ResultType.SUCCESS) {
			player.sendMessage(Text.of(TextColors.DARK_RED, Lang.SHOP_OMONEY));
			return false;
		}

		ShopsLogs.log(getOwner(), player, "sell", super.getLocation(), Optional.of(price), getRawCurrency(), Optional.of(itemsTemplate), Optional.empty());

		Map<String, Text> varMap = ImmutableMap.<String, Text>builder()
				.put("player", Text.of(player.getName()))
				.put("items", new InventoryDecorator(itemsTemplate).getTooltippedItemList())
				.put("price", Text.of(formatPrice(price)))
				.build();

		player.sendMessage(
				Lang.build(
						Lang.SHOP_RECAP_SELL,
						varMap
				)
		);

		if (!CarrotShop.noSpam(getOwner())) {
			Optional<Player> seller = Sponge.getServer().getPlayer(getOwner());

			seller.ifPresent(player1 -> player1.sendMessage(
					Lang.build(
							Lang.SHOP_RECAP_OSELL,
							varMap
					)
			));
		}

		update();
		return true;
	}

}
