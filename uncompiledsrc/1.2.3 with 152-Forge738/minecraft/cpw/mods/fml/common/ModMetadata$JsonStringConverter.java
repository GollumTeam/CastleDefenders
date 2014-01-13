package cpw.mods.fml.common;

import argo.jdom.JsonNode;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.ModMetadata$1;
import cpw.mods.fml.common.ModMetadata$JsonArrayConverter;

final class ModMetadata$JsonStringConverter implements Function
{
    private ModMetadata$JsonStringConverter() {}

    public Object apply(JsonNode var1)
    {
        return var1.hasElements() ? Lists.transform(var1.getElements(), new ModMetadata$JsonArrayConverter((ModMetadata$1)null)) : var1.getText();
    }

    public Object apply(Object var1)
    {
        return this.apply((JsonNode)var1);
    }

    ModMetadata$JsonStringConverter(ModMetadata$1 var1)
    {
        this();
    }
}
