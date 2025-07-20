package com.yooptwo.copperplus.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class ChiselItem extends Item {
    //use Map.ofEntries(),this map contains all the transformation
    Map<Block, Block> CHISEL_MAP_ALL = Map.ofEntries(
            Map.entry(Blocks.STONE, Blocks.NETHERRACK),
            Map.entry(Blocks.NETHERRACK, Blocks.END_STONE),
            Map.entry(Blocks.END_STONE, Blocks.STONE),
            Map.entry(Blocks.DEEPSLATE, Blocks.OBSIDIAN),
            Map.entry(Blocks.OBSIDIAN, Blocks.DEEPSLATE),
            Map.entry(Blocks.NETHERITE_BLOCK, Blocks.DIAMOND_BLOCK),
            Map.entry(Blocks.DIAMOND_BLOCK, Blocks.EMERALD_BLOCK),
            Map.entry(Blocks.EMERALD_BLOCK, Blocks.GOLD_BLOCK),
            Map.entry(Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK),
            Map.entry(Blocks.IRON_BLOCK, Blocks.COAL_BLOCK),
            Map.entry(Blocks.ANCIENT_DEBRIS, Blocks.DIAMOND_ORE),
            Map.entry(Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE),
            Map.entry(Blocks.EMERALD_ORE, Blocks.GOLD_ORE),
            Map.entry(Blocks.GOLD_ORE, Blocks.IRON_ORE),
            Map.entry(Blocks.IRON_ORE, Blocks.COAL_ORE),
            Map.entry(Blocks.COAL_ORE, Blocks.OAK_WOOD),
            Map.entry(Blocks.OAK_WOOD, Blocks.GRAVEL),
            Map.entry(Blocks.GRAVEL, Blocks.SAND)
    );



    public ChiselItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (CHISEL_MAP_ALL.containsKey(clickedBlock)) {
            if(!level.isClientSide()){
                level.setBlockAndUpdate(context.getClickedPos(), CHISEL_MAP_ALL.get(clickedBlock).defaultBlockState());

                context.getItemInHand().hurtAndBreak(1,((ServerLevel)level),
                        context.getPlayer(),item ->context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
