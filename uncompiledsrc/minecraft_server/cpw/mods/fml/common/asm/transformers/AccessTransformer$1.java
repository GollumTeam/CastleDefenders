package cpw.mods.fml.common.asm.transformers;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import cpw.mods.fml.common.asm.transformers.AccessTransformer$Modifier;
import java.io.IOException;
import java.util.ArrayList;

class AccessTransformer$1 implements LineProcessor
{
    final AccessTransformer this$0;

    AccessTransformer$1(AccessTransformer var1)
    {
        this.this$0 = var1;
    }

    public Void getResult()
    {
        return null;
    }

    public boolean processLine(String var1) throws IOException
    {
        String var2 = ((String)Iterables.getFirst(Splitter.on('#').limit(2).split(var1), "")).trim();

        if (var2.length() == 0)
        {
            return true;
        }
        else
        {
            ArrayList var3 = Lists.newArrayList(Splitter.on(" ").trimResults().split(var2));

            if (var3.size() > 2)
            {
                throw new RuntimeException("Invalid config file line " + var1);
            }
            else
            {
                AccessTransformer$Modifier var4 = new AccessTransformer$Modifier(this.this$0, (AccessTransformer$1)null);
                AccessTransformer$Modifier.access$100(var4, (String)var3.get(0));
                ArrayList var5 = Lists.newArrayList(Splitter.on(".").trimResults().split((CharSequence)var3.get(1)));

                if (var5.size() == 1)
                {
                    var4.modifyClassVisibility = true;
                }
                else
                {
                    String var6 = (String)var5.get(1);
                    int var7 = var6.indexOf(40);

                    if (var7 > 0)
                    {
                        var4.desc = var6.substring(var7);
                        var4.name = var6.substring(0, var7);
                    }
                    else
                    {
                        var4.name = var6;
                    }
                }

                AccessTransformer.access$200(this.this$0).put(((String)var5.get(0)).replace('/', '.'), var4);
                return true;
            }
        }
    }

    public Object getResult()
    {
        return this.getResult();
    }
}
