package com.carrot.carrotshop.decorator;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;

public interface IInventoryDecorator extends Inventory {
    Text getTooltippedItemList();
}
