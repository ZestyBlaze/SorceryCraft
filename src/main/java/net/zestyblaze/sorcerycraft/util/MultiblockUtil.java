package net.zestyblaze.sorcerycraft.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.zestyblaze.sorcerycraft.registry.SCBlockInit;

public class MultiblockUtil {
    public static boolean checkStructure(Level level, BlockPos pos) {
        return (checkCentreCandle(level, pos) && checkCentre(level, pos) && checkGold(level, pos) && checkQuartzBlocks(level, pos) && checkOuterCandles(level, pos));
    }

    private static boolean checkCentre(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == SCBlockInit.CRYSTALISED_REDSTONE_BLOCK) {
            if(level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.AMETHYST_BLOCK) {
                if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == SCBlockInit.CRYSTALISED_REDSTONE_BLOCK) {
                    if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.AMETHYST_BLOCK) {
                        if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == SCBlockInit.CRYSTALISED_REDSTONE_BLOCK) {
                            if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.AMETHYST_BLOCK) {
                                if (level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == SCBlockInit.CRYSTALISED_REDSTONE_BLOCK) {
                                    return level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.AMETHYST_BLOCK;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkCentreCandle(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.UP);
        if(level.getBlockState(mutable).getBlock() == Blocks.PURPLE_CANDLE) {
            if(level.getBlockState(mutable).getValue(BlockStateProperties.LIT)) {
                return level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 1;
            }
        }
        return false;
    }

    private static boolean checkGold(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        if(level.getBlockState(mutable.move(Direction.NORTH, 2)).getBlock() == Blocks.GOLD_BLOCK) {
            if(level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.GOLD_BLOCK) {
                mutable.move(Direction.EAST);
                if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.GOLD_BLOCK) {
                    if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.GOLD_BLOCK) {
                        if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.GOLD_BLOCK) {
                            mutable.move(Direction.SOUTH);
                            if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.GOLD_BLOCK) {
                                if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.GOLD_BLOCK) {
                                    if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.GOLD_BLOCK) {
                                        mutable.move(Direction.WEST);
                                        if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.GOLD_BLOCK) {
                                            if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.GOLD_BLOCK) {
                                                if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.GOLD_BLOCK) {
                                                    mutable.move(Direction.NORTH);
                                                    return level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.GOLD_BLOCK;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkQuartzBlocks(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        if(level.getBlockState(mutable.move(Direction.NORTH, 3)).getBlock() == Blocks.QUARTZ_BLOCK) {
            if(level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.QUARTZ_BLOCK) {
                mutable.move(Direction.EAST);
                if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                    mutable.move(Direction.EAST);
                    if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                        if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                            if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                mutable.move(Direction.SOUTH);
                                if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                    mutable.move(Direction.SOUTH);
                                    if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                        if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                            if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                                mutable.move(Direction.WEST);
                                                if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                                    mutable.move(Direction.WEST);
                                                    if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                                        if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                                            if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                                                mutable.move(Direction.NORTH);
                                                                if(level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.QUARTZ_BLOCK) {
                                                                    mutable.move(Direction.NORTH);
                                                                    return level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.QUARTZ_BLOCK;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkOuterCandles(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.UP);
        if(level.getBlockState(mutable.move(Direction.NORTH, 3)).getBlock() == Blocks.PURPLE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 3) {
            if(level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2) {
                mutable.move(Direction.EAST);
                if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.BLACK_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 1) {
                    mutable.move(Direction.EAST);
                    if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2) {
                        if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.PURPLE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 3) {
                            if(level.getBlockState(mutable.move(Direction.SOUTH)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2) {
                                mutable.move(Direction.SOUTH);
                                if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.BLACK_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 1) {
                                    mutable.move(Direction.SOUTH);
                                    if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2) {
                                        if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.PURPLE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 3) {
                                            if(level.getBlockState(mutable.move(Direction.WEST)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2) {
                                                mutable.move(Direction.WEST);
                                                if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.BLACK_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 1) {
                                                    mutable.move(Direction.WEST);
                                                    if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2) {
                                                        if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.PURPLE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 3) {
                                                            if(level.getBlockState(mutable.move(Direction.NORTH)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2) {
                                                                mutable.move(Direction.NORTH);
                                                                if(level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.BLACK_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 1) {
                                                                    mutable.move(Direction.NORTH);
                                                                    return level.getBlockState(mutable.move(Direction.EAST)).getBlock() == Blocks.WHITE_CANDLE && level.getBlockState(mutable).getValue(CandleBlock.LIT) && level.getBlockState(mutable).getValue(CandleBlock.CANDLES) == 2;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void executeFinish(Level level, BlockPos pos) {
        snuffCandles(level, pos);
        drainRedstoneBlocks(level, pos);
    }

    private static void snuffCandles(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.UP);
        level.setBlockAndUpdate(mutable, level.getBlockState(mutable).setValue(CandleBlock.LIT, false));

        level.setBlockAndUpdate(mutable.move(Direction.NORTH, 3), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        level.setBlockAndUpdate(mutable.move(Direction.EAST), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.EAST);
        level.setBlockAndUpdate(mutable.move(Direction.SOUTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.EAST);
        level.setBlockAndUpdate(mutable.move(Direction.SOUTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        level.setBlockAndUpdate(mutable.move(Direction.SOUTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        level.setBlockAndUpdate(mutable.move(Direction.SOUTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.SOUTH);
        level.setBlockAndUpdate(mutable.move(Direction.WEST), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.SOUTH);
        level.setBlockAndUpdate(mutable.move(Direction.WEST), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        level.setBlockAndUpdate(mutable.move(Direction.WEST), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        level.setBlockAndUpdate(mutable.move(Direction.WEST), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.WEST);
        level.setBlockAndUpdate(mutable.move(Direction.NORTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.WEST);
        level.setBlockAndUpdate(mutable.move(Direction.NORTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        level.setBlockAndUpdate(mutable.move(Direction.NORTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        level.setBlockAndUpdate(mutable.move(Direction.NORTH), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.NORTH);
        level.setBlockAndUpdate(mutable.move(Direction.EAST), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
        mutable.move(Direction.NORTH);
        level.setBlockAndUpdate(mutable.move(Direction.EAST), level.getBlockState(mutable).setValue(CandleBlock.LIT, false));
    }

    private static void drainRedstoneBlocks(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        level.setBlockAndUpdate(mutable.move(Direction.NORTH), SCBlockInit.DRAINED_CRYSTALISED_REDSTONE_BLOCK.defaultBlockState());
        mutable.move(Direction.SOUTH);
        level.setBlockAndUpdate(mutable.move(Direction.EAST), SCBlockInit.DRAINED_CRYSTALISED_REDSTONE_BLOCK.defaultBlockState());
        mutable.move(Direction.WEST);
        level.setBlockAndUpdate(mutable.move(Direction.SOUTH), SCBlockInit.DRAINED_CRYSTALISED_REDSTONE_BLOCK.defaultBlockState());
        mutable.move(Direction.NORTH);
        level.setBlockAndUpdate(mutable.move(Direction.WEST), SCBlockInit.DRAINED_CRYSTALISED_REDSTONE_BLOCK.defaultBlockState());
    }
}
