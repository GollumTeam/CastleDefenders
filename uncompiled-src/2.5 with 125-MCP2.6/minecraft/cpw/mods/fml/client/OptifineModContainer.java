package cpw.mods.fml.client;

import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.ModMetadata;
import java.lang.reflect.Field;

public class OptifineModContainer extends FMLModContainer
{
    private String optifineVersion;
    private ModMetadata metadata;

    public OptifineModContainer(Class class1)
    {
        super("Optifine");

        try
        {
            optifineVersion = (String)class1.getField("VERSION").get(null);
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }

    public String getName()
    {
        return "Optifine";
    }

    public String getVersion()
    {
        return optifineVersion;
    }
}
