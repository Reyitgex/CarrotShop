package com.carrot.carrotshop.decorator;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Map;

public interface IItemStackDecorator extends ItemStack {
    Text getDisplayName();

    Text getTooltip();

    boolean exactItemMatch(ItemStack otherItem);

    Map<DataQuery, Object> getNBTData();
}
