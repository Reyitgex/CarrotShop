package com.carrot.carrotshop.decorator;

import com.carrot.carrotshop.CarrotShop;
import org.spongepowered.api.data.*;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.translation.Translation;

import java.util.*;

public class ItemStackDecorator implements IItemStackDecorator {
    protected ItemStack itemStack;

    public ItemStackDecorator(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public Text getDisplayName() {
        Text displayName = itemStack.get(Keys.DISPLAY_NAME).orElse(Text.of(itemStack.getTranslation()));
        TextColor itemColor = displayName.getColor();

        // Color the item aqua if it has an enchantment
        if (itemStack.get(EnchantmentData.class).isPresent()) {
            itemColor = TextColors.AQUA;
        }

        // idk what this does, uncomment it if find issues
//        if (!displayName.getChildren().isEmpty()) {
//            itemColor = displayName.getChildren().get(0).getColor();
//        }

        return Text.builder().color(itemColor)
                .append(displayName)
                .build();
    }

    @Override
    public Text getTooltip() {
        CarrotShop.getLogger().info(itemStack.createSnapshot().toContainer().getValues(true).toString());

        return Text.builder()
                .append(Text.of("["), getDisplayName(), Text.of("]"))
                .onHover(TextActions.showItem(itemStack.createSnapshot()))
                .build();
    }

    @Override
    public boolean exactItemMatch(ItemStack otherItemStack) {
        otherItemStack = otherItemStack.copy();
        otherItemStack.setQuantity(getQuantity());
        ItemStackDecorator otherItemStackCopy = new ItemStackDecorator(otherItemStack);

        if (!itemStack.equalTo(otherItemStack))
            return false;

        // check NBT because equalTo doesn't
        return getNBTData().toString().equals(otherItemStackCopy.getNBTData().toString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<DataQuery, Object> getNBTData() {
        return (Map<DataQuery, Object>) toContainer().getValues(true)
                .getOrDefault(DataQuery.of("UnsafeData"), new HashMap<DataQuery, Object>());
    }

    @Override
    public ItemType getType() {
        return itemStack.getType();
    }

    @Override
    public int getQuantity() {
        return itemStack.getQuantity();
    }

    @Override
    public void setQuantity(int quantity) throws IllegalArgumentException {
        itemStack.setQuantity(quantity);
    }

    @Override
    public int getMaxStackQuantity() {
        return itemStack.getMaxStackQuantity();
    }

    @Override
    public ItemStackSnapshot createSnapshot() {
        return itemStack.createSnapshot();
    }

    @Override
    public boolean equalTo(ItemStack that) {
        return itemStack.equalTo(that);
    }

    @Override
    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    @Override
    public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
        return itemStack.get(key);
    }

    @Override
    public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
        return itemStack.getValue(key);
    }

    @Override
    public boolean supports(Key<?> key) {
        return itemStack.supports(key);
    }

    @Override
    public ItemStack copy() {
        return itemStack.copy();
    }

    @Override
    public Set<Key<?>> getKeys() {
        return itemStack.getKeys();
    }

    @Override
    public Set<ImmutableValue<?>> getValues() {
        return itemStack.getValues();
    }

    @Override
    public boolean validateRawData(DataView container) {
        return itemStack.validateRawData(container);
    }

    @Override
    public void setRawData(DataView container) throws InvalidDataException {
        itemStack.setRawData(container);
    }

    @Override
    public int getContentVersion() {
        return itemStack.getContentVersion();
    }

    @Override
    public DataContainer toContainer() {
        return itemStack.toContainer();
    }

    @Override
    public <T extends Property<?, ?>> Optional<T> getProperty(Class<T> propertyClass) {
        return itemStack.getProperty(propertyClass);
    }

    @Override
    public Collection<Property<?, ?>> getApplicableProperties() {
        return itemStack.getApplicableProperties();
    }

    @Override
    public <T extends DataManipulator<?, ?>> Optional<T> get(Class<T> containerClass) {
        return itemStack.get(containerClass);
    }

    @Override
    public <T extends DataManipulator<?, ?>> Optional<T> getOrCreate(Class<T> containerClass) {
        return itemStack.getOrCreate(containerClass);
    }

    @Override
    public boolean supports(Class<? extends DataManipulator<?, ?>> holderClass) {
        return itemStack.supports(holderClass);
    }

    @Override
    public <E> DataTransactionResult offer(Key<? extends BaseValue<E>> key, E value) {
        return itemStack.offer(key, value);
    }

    @Override
    public DataTransactionResult offer(DataManipulator<?, ?> valueContainer, MergeFunction function) {
        return itemStack.offer(valueContainer, function);
    }

    @Override
    public DataTransactionResult remove(Class<? extends DataManipulator<?, ?>> containerClass) {
        return itemStack.remove(containerClass);
    }

    @Override
    public DataTransactionResult remove(Key<?> key) {
        return itemStack.remove(key);
    }

    @Override
    public DataTransactionResult undo(DataTransactionResult result) {
        return itemStack.undo(result);
    }

    @Override
    public DataTransactionResult copyFrom(DataHolder that, MergeFunction function) {
        return itemStack.copyFrom(that, function);
    }

    @Override
    public Collection<DataManipulator<?, ?>> getContainers() {
        return itemStack.getContainers();
    }

    @Override
    public Translation getTranslation() {
        return itemStack.getTranslation();
    }
}
