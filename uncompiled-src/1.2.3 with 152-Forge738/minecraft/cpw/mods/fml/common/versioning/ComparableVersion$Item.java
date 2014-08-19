package cpw.mods.fml.common.versioning;

interface ComparableVersion$Item
{
    int INTEGER_ITEM = 0;
    int STRING_ITEM = 1;
    int LIST_ITEM = 2;

    int compareTo(ComparableVersion$Item var1);

    int getType();

    boolean isNull();
}
