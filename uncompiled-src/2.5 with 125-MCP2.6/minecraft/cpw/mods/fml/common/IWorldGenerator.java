package cpw.mods.fml.common;

import java.util.Random;

public interface IWorldGenerator
{
    public abstract void generate(Random random, int i, int j, Object aobj[]);
}
