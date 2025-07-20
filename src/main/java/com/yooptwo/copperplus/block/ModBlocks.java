package com.yooptwo.copperplus.block;

import com.yooptwo.copperplus.CopperPlus;
import com.yooptwo.copperplus.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    // DeferredRegister
    public static final DeferredRegister.Blocks Blocks = DeferredRegister.createBlocks(CopperPlus.MODID);

    public static final DeferredBlock<Block> ANCIENT_COPPER_BLOCK = registerBlock("ancient_copper_block" ,
            () ->new Block(BlockBehaviour.Properties.of().strength(3.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER)));



    //
    private static  <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = Blocks.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }



    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Register blocks to IEventBus
    public static void register(IEventBus eventBus){
        Blocks.register(eventBus);
    }
}
