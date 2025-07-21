package com.yooptwo.copperplus.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class ChiselItem extends Item {
    //use Map.ofEntries(),this map contains all the transformation
    Map<Block, Block> CHISEL_MAP_ALL = Map.ofEntries(
            //stones' transformation
            Map.entry(Blocks.STONE, Blocks.NETHERRACK),
            Map.entry(Blocks.NETHERRACK, Blocks.END_STONE),
            Map.entry(Blocks.END_STONE, Blocks.STONE),
            //deepslate's transformation
            Map.entry(Blocks.DEEPSLATE, Blocks.OBSIDIAN),
            Map.entry(Blocks.OBSIDIAN, Blocks.DEEPSLATE),
            //ore blocks' transformation
            Map.entry(Blocks.NETHERITE_BLOCK, Blocks.DIAMOND_BLOCK),
            Map.entry(Blocks.DIAMOND_BLOCK, Blocks.EMERALD_BLOCK),
            Map.entry(Blocks.EMERALD_BLOCK, Blocks.GOLD_BLOCK),
            Map.entry(Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK),
            Map.entry(Blocks.IRON_BLOCK, Blocks.COAL_BLOCK),
            //ores' transformation
            Map.entry(Blocks.ANCIENT_DEBRIS, Blocks.DIAMOND_ORE),
            Map.entry(Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE),
            Map.entry(Blocks.EMERALD_ORE, Blocks.GOLD_ORE),
            Map.entry(Blocks.GOLD_ORE, Blocks.IRON_ORE),
            Map.entry(Blocks.IRON_ORE, Blocks.COAL_ORE),
            Map.entry(Blocks.COAL_ORE, Blocks.OAK_WOOD),
            Map.entry(Blocks.OAK_WOOD, Blocks.DIRT),
            Map.entry(Blocks.DIRT, Blocks.GRAVEL),
            Map.entry(Blocks.GRAVEL, Blocks.SAND)
    );


    //use Map.ofEntries(),this map contains all the transformation's extra drops.
    Map<Block, Item> CHISEL_DROP = Map.ofEntries(
            //ore blocks' extra drops
            Map.entry(Blocks.NETHERITE_BLOCK, Items.NETHERITE_INGOT),
            Map.entry(Blocks.DIAMOND_BLOCK, Items.DIAMOND),
            Map.entry(Blocks.EMERALD_BLOCK, Items.EMERALD),
            Map.entry(Blocks.GOLD_BLOCK, Items.GOLD_INGOT),
            Map.entry(Blocks.IRON_BLOCK, Items.IRON_INGOT),
            Map.entry(Blocks.COAL_BLOCK, Items.COAL),
            //ores' extra drops
            Map.entry(Blocks.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP),
            Map.entry(Blocks.GOLD_ORE, Items.RAW_GOLD),
            Map.entry(Blocks.IRON_ORE, Items.RAW_IRON),
            Map.entry(Blocks.COAL_ORE, Items.COAL),
            Map.entry(Blocks.OAK_WOOD, Items.STICK),
            Map.entry(Blocks.DIRT, Items.WHEAT_SEEDS),
            Map.entry(Blocks.GRAVEL, Items.FLINT)
    );

    Map<Block, Integer> CHISEL_EXPERIENCE = Map.ofEntries(
            //ores' experience drop
            Map.entry(Blocks.ANCIENT_DEBRIS, 4),
            Map.entry(Blocks.DIAMOND_ORE, 3),
            Map.entry(Blocks.EMERALD_ORE, 2),
            Map.entry(Blocks.GOLD_ORE, 2),
            Map.entry(Blocks.IRON_ORE, 2),
            Map.entry(Blocks.COAL_ORE, 1),
            Map.entry(Blocks.OAK_WOOD, 1),
            Map.entry(Blocks.DIRT, 1),
            Map.entry(Blocks.GRAVEL, 1),
            //ore blocks' experience drop
            Map.entry(Blocks.NETHERITE_BLOCK, 8),
            Map.entry(Blocks.DIAMOND_BLOCK, 6),
            Map.entry(Blocks.EMERALD_BLOCK, 4),
            Map.entry(Blocks.GOLD_BLOCK, 4),
            Map.entry(Blocks.IRON_BLOCK, 4)
    );
    public ChiselItem(Properties properties) {
        super(properties);
    }

    private void doExtraDrop(Level level, Block block, BlockPos pos){
        if(!level.isClientSide()){
            if(CHISEL_DROP.containsKey(block)){
                ItemStack dropItem = CHISEL_DROP.get(block).getDefaultInstance();
                ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, dropItem);
                level.addFreshEntity(itemEntity);
            }
        }
    }

    private  void doExtraExperience(Level level,Block clickedBlock, BlockPos pos){
        if(!level.isClientSide()){
            if(CHISEL_EXPERIENCE.containsKey(clickedBlock)){
                int amount = CHISEL_EXPERIENCE.get(clickedBlock);
                ExperienceOrb orb = new ExperienceOrb(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, amount);
                level.addFreshEntity(orb);
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (CHISEL_MAP_ALL.containsKey(clickedBlock)) {
            if(!level.isClientSide()){
                level.setBlockAndUpdate(context.getClickedPos(), CHISEL_MAP_ALL.get(clickedBlock).defaultBlockState());
                doExtraDrop(level,clickedBlock,context.getClickedPos());
                doExtraExperience(level,clickedBlock,context.getClickedPos());
                context.getItemInHand().hurtAndBreak(1,((ServerLevel)level),
                context.getPlayer(),item ->context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
                level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
