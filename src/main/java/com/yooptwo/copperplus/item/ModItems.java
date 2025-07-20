package com.yooptwo.copperplus.item;

import com.yooptwo.copperplus.CopperPlus;
import com.yooptwo.copperplus.item.custom.ChiselItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    //定义了DeferredRegister.Items的实例，用于延迟注册ModItems
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CopperPlus.MODID);

    public static final DeferredItem<Item> ANCIENT_COPPER_INGOT = ITEMS.register("ancient_copper_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COPPER_CHISEL = ITEMS.register("copper_chisel",
            () -> new ChiselItem(new Item.Properties().durability(350).stacksTo(1)));

    //定义一个用于注册物品的方法，参数使用IEventBus
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
