package cpw.mods.fml.common;

import argo.jdom.JsonNode;
import com.google.common.base.Function;
import cpw.mods.fml.common.ModMetadata$1;

final class ModMetadata$JsonArrayConverter implements Function
{
    private ModMetadata$JsonArrayConverter() {}

    public String apply(JsonNode var1)
    {
        return var1.getText();
    }

    public Object apply(Object var1)
    {
        return this.apply((JsonNode)var1);
    }

    ModMetadata$JsonArrayConverter(ModMetadata$1 var1)
    {
        this();
    }
}
