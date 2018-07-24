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
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.TileEntityCarrier;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.item.inventory.type.InventoryRow;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.Stack;

@ConfigSerializable
public class iTrade extends Shop {
	@Setting
	private Inventory toGive;
	@Setting
	private Inventory toTake;

	@SuppressWarnings("unused")
	public iTrade() {
	}

	iTrade(Player player, Location<World> sign) throws ExceptionInInitializerError {
		super(sign);
		String type = "iTrade";
		if (!player.hasPermission("carrotshop.admin.itrade"))
			throw new ExceptionInInitializerError(Lang.SHOP_PERM.replace("%type%", type));
		Stack<Location<World>> locations = ShopsData.getItemLocations(player);
		if (locations.size() < 2)
			throw new ExceptionInInitializerError(Lang.SHOP_CHEST2.replace("%type%", type));
		Optional<TileEntity> chestTakeOpt = locations.get(0).getTileEntity();
		Optional<TileEntity> chestGiveOpt = locations.get(1).getTileEntity();
		if (!chestTakeOpt.isPresent() || !chestGiveOpt.isPresent() || !(chestTakeOpt.get() instanceof TileEntityCarrier) || !(chestGiveOpt.get() instanceof TileEntityCarrier))
			throw new ExceptionInInitializerError(Lang.SHOP_CHEST2.replace("%type%", type));
		Inventory chestTake = ((TileEntityCarrier) chestTakeOpt.get()).getInventory();
		Inventory chestGive = ((TileEntityCarrier) chestGiveOpt.get()).getInventory();
		if (chestTake.totalItems() == 0 || chestGive.totalItems() == 0)
			throw new ExceptionInInitializerError(Lang.SHOP_CHEST_EMPTY);
		toTake = Inventory.builder().from(chestTake).build(CarrotShop.getInstance());
		toGive = Inventory.builder().from(chestGive).build(CarrotShop.getInstance());
		for (Inventory item : chestGive.slots()) {
			if (item.peek().isPresent())
				toGive.offer(item.peek().get());
		}
		for (Inventory item : chestTake.slots()) {
			if (item.peek().isPresent())
				toTake.offer(item.peek().get());
		}
		ShopsData.clearItemLocations(player);
		player.sendMessage(Text.of(TextColors.DARK_GREEN, Lang.SHOP_DONE.replace("%type%", type)));
		done(player);
		info(player);
	}

	@Override
	public void info(Player player) {
		player.sendMessage(
				Lang.build(
						Lang.SHOP_FORMAT_TRADE,
						ImmutableMap.<String, Text>builder()
								.put("giveItems", new InventoryDecorator(toTake).getTooltippedItemList())
								.put("getItems", new InventoryDecorator(toGive).getTooltippedItemList())
								.build()
				)
		);

		update();
	}

	@Override
	public boolean trigger(Player player) {
		Inventory inv = player.getInventory().query(QueryOperationTypes.INVENTORY_TYPE.of(InventoryRow.class));

		if (!hasEnough(inv, toTake)) {
			player.sendMessage(Text.of(TextColors.DARK_RED, Lang.SHOP_ITEMS));
			return false;
		}

		for (Inventory item : toTake.slots()) {
			if (item.peek().isPresent()) {
				Optional<ItemStack> template = getTemplate(inv, item.peek().get());
				if (template.isPresent()) {
					// Find all items that match in our inventory and remove the amount of them
					ItemStackDecorator neededStack = new ItemStackDecorator(item.peek().get().copy());
					inv.query(
							QueryOperationTypes.ITEM_STACK_CUSTOM.of(neededStack::exactItemMatch)
					).poll(neededStack.getQuantity());
				}
			}
		}
		for (Inventory item : toGive.slots()) {
			if (item.peek().isPresent()) {
				inv.offer(item.peek().get().copy()).getRejectedItems().forEach(action -> putItemInWorld(action, player.getLocation()));
			}
		}

		ShopsLogs.log(getOwner(), player, "trade", super.getLocation(), Optional.empty(), Optional.empty(), Optional.of(toGive), Optional.of(toTake));

		player.sendMessage(
				Lang.build(
						Lang.SHOP_RECAP_TRADE,
						ImmutableMap.<String, Text>builder()
								.put("giveItems", new InventoryDecorator(toTake).getTooltippedItemList())
								.put("getItems", new InventoryDecorator(toGive).getTooltippedItemList())
								.build()
				)
		);

		return true;
	}

	@Override
	public boolean canLoopCurrency(Player src) {
		return false;
	}
}
