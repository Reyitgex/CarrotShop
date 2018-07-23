package com.carrot.carrotshop.decorator;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryProperty;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.query.QueryOperation;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.Translation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class InventoryDecorator implements IInventoryDecorator {
    protected Inventory inventory;

    public InventoryDecorator(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Text getTooltippedItemList() {
        Text.Builder builder = Text.builder();

        boolean firstItemAdded = false;
        for (Inventory item : inventory.slots()) {
            if (item.peek().isPresent()) {
                ItemStackDecorator itemStack = new ItemStackDecorator(item.peek().get());

                if (firstItemAdded)
                    builder.append(Text.of(", "));

                builder.append(Text.of(itemStack.getTooltip(), " x ", itemStack.getQuantity()));

                firstItemAdded = true;
            }
        }

        return builder.build();
    }

    @Override
    public Inventory parent() {
        return inventory.parent();
    }

    @Override
    public Inventory root() {
        return inventory.root();
    }

    @Override
    public <T extends Inventory> Iterable<T> slots() {
        return inventory.slots();
    }

    @Override
    public <T extends Inventory> T first() {
        return inventory.first();
    }

    @Override
    public <T extends Inventory> T next() {
        return inventory.next();
    }

    @Override
    public Optional<ItemStack> poll() {
        return inventory.poll();
    }

    @Override
    public Optional<ItemStack> poll(int limit) {
        return inventory.poll(limit);
    }

    @Override
    public Optional<ItemStack> peek() {
        return inventory.peek();
    }

    @Override
    public Optional<ItemStack> peek(int limit) {
        return inventory.peek(limit);
    }

    @Override
    public InventoryTransactionResult offer(ItemStack stack) {
        return inventory.offer(stack);
    }

    @Override
    public InventoryTransactionResult set(ItemStack stack) {
        return inventory.set(stack);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public int totalItems() {
        return inventory.totalItems();
    }

    @Override
    public int capacity() {
        return inventory.capacity();
    }

    @Override
    public boolean hasChildren() {
        return inventory.hasChildren();
    }

    @Override
    public boolean contains(ItemStack stack) {
        return inventory.contains(stack);
    }

    @Override
    public boolean contains(ItemType type) {
        return inventory.contains(type);
    }

    @Override
    public boolean containsAny(ItemStack stack) {
        return inventory.containsAny(stack);
    }

    @Override
    public int getMaxStackSize() {
        return inventory.getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int size) {
        inventory.setMaxStackSize(size);
    }

    @Override
    public <T extends InventoryProperty<?, ?>> Collection<T> getProperties(Inventory child, Class<T> property) {
        return inventory.getProperties(child, property);
    }

    @Override
    public <T extends InventoryProperty<?, ?>> Collection<T> getProperties(Class<T> property) {
        return inventory.getProperties(property);
    }

    @Override
    public <T extends InventoryProperty<?, ?>> Optional<T> getProperty(Inventory child, Class<T> property, Object key) {
        return inventory.getProperty(child, property, key);
    }

    @Override
    public <T extends InventoryProperty<?, ?>> Optional<T> getInventoryProperty(Inventory child, Class<T> property) {
        return inventory.getInventoryProperty(child, property);
    }

    @Override
    public <T extends InventoryProperty<?, ?>> Optional<T> getProperty(Class<T> property, Object key) {
        return inventory.getProperty(property, key);
    }

    @Override
    public <T extends InventoryProperty<?, ?>> Optional<T> getInventoryProperty(Class<T> property) {
        return inventory.getInventoryProperty(property);
    }

    @Override
    public <T extends Inventory> T query(QueryOperation<?>... operations) {
        return inventory.query(operations);
    }

    @Override
    public PluginContainer getPlugin() {
        return inventory.getPlugin();
    }

    @Override
    public InventoryArchetype getArchetype() {
        return inventory.getArchetype();
    }

    @Override
    public Inventory intersect(Inventory inventory) {
        return inventory.intersect(inventory);
    }

    @Override
    public Inventory union(Inventory inventory) {
        return inventory.union(inventory);
    }

    @Override
    public boolean containsInventory(Inventory inventory) {
        return inventory.containsInventory(inventory);
    }

    @Override
    public Iterator<Inventory> iterator() {
        return inventory.iterator();
    }

    @Override
    public Translation getName() {
        return inventory.getName();
    }
}
