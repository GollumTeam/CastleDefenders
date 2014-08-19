package forge;

import net.minecraft.src.ItemStack;

public interface IItemRenderer
{
    public static final class ItemRendererHelper extends Enum
    {
        public static final ItemRendererHelper ENTITY_ROTATION;
        public static final ItemRendererHelper ENTITY_BOBBING;
        public static final ItemRendererHelper EQUIPPED_BLOCK;
        public static final ItemRendererHelper BLOCK_3D;
        public static final ItemRendererHelper INVENTORY_BLOCK;
        private static final ItemRendererHelper $VALUES[];

        public static ItemRendererHelper[] values()
        {
            return (ItemRendererHelper[])$VALUES.clone();
        }

        public static ItemRendererHelper valueOf(String s)
        {
            return (ItemRendererHelper)Enum.valueOf(forge.IItemRenderer$ItemRendererHelper.class, s);
        }

        static
        {
            ENTITY_ROTATION = new ItemRendererHelper("ENTITY_ROTATION", 0);
            ENTITY_BOBBING = new ItemRendererHelper("ENTITY_BOBBING", 1);
            EQUIPPED_BLOCK = new ItemRendererHelper("EQUIPPED_BLOCK", 2);
            BLOCK_3D = new ItemRendererHelper("BLOCK_3D", 3);
            INVENTORY_BLOCK = new ItemRendererHelper("INVENTORY_BLOCK", 4);
            $VALUES = (new ItemRendererHelper[]
                    {
                        ENTITY_ROTATION, ENTITY_BOBBING, EQUIPPED_BLOCK, BLOCK_3D, INVENTORY_BLOCK
                    });
        }

        private ItemRendererHelper(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class ItemRenderType extends Enum
    {
        public static final ItemRenderType ENTITY;
        public static final ItemRenderType EQUIPPED;
        public static final ItemRenderType INVENTORY;
        public static final ItemRenderType FIRST_PERSON_MAP;
        private static final ItemRenderType $VALUES[];

        public static ItemRenderType[] values()
        {
            return (ItemRenderType[])$VALUES.clone();
        }

        public static ItemRenderType valueOf(String s)
        {
            return (ItemRenderType)Enum.valueOf(forge.IItemRenderer$ItemRenderType.class, s);
        }

        static
        {
            ENTITY = new ItemRenderType("ENTITY", 0);
            EQUIPPED = new ItemRenderType("EQUIPPED", 1);
            INVENTORY = new ItemRenderType("INVENTORY", 2);
            FIRST_PERSON_MAP = new ItemRenderType("FIRST_PERSON_MAP", 3);
            $VALUES = (new ItemRenderType[]
                    {
                        ENTITY, EQUIPPED, INVENTORY, FIRST_PERSON_MAP
                    });
        }

        private ItemRenderType(String s, int i)
        {
            super(s, i);
        }
    }

    public abstract boolean handleRenderType(ItemStack itemstack, ItemRenderType itemrendertype);

    public abstract boolean shouldUseRenderHelper(ItemRenderType itemrendertype, ItemStack itemstack, ItemRendererHelper itemrendererhelper);

    public abstract void renderItem(ItemRenderType itemrendertype, ItemStack itemstack, Object aobj[]);
}
