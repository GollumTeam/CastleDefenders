package mods.CastleDef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMerc extends BlockContainer
{
    private Icon sides;
    private Icon bottom;
    private Icon top;
    private Icon[][] iconBuffer;

    public BlockMerc(int var1)
    {
        super(var1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        this.sides = var1.registerIcon("CastleDef:BlockMerc2");
        this.bottom = var1.registerIcon("CastleDef:BlockMerc");
        this.top = var1.registerIcon("CastleDef:BlockMerc");
        this.blockIcon = var1.registerIcon("CastleDef:BlockMerc");
    }

    public Icon getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return var1 == 0 ? this.bottom : (var1 == 1 ? this.top : this.sides);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityBlockMerc();
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6, int var7)
    {
        super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6, var7);
        int var8 = 15 + var1.rand.nextInt(15) + var1.rand.nextInt(15);
        this.dropXpOnBlockBreak(var1, var2, var3, var4, var8);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World var1, int var2, int var3, int var4)
    {
        return 0;
    }
}
