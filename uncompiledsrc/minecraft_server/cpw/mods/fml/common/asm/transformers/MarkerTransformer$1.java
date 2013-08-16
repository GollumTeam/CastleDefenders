package cpw.mods.fml.common.asm.transformers;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

class MarkerTransformer$1 implements LineProcessor
{
    final MarkerTransformer this$0;

    MarkerTransformer$1(MarkerTransformer var1)
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

            if (var3.size() != 2)
            {
                throw new RuntimeException("Invalid config file line " + var1);
            }
            else
            {
                ArrayList var4 = Lists.newArrayList(Splitter.on(",").trimResults().split((CharSequence)var3.get(1)));
                Iterator var5 = var4.iterator();

                while (var5.hasNext())
                {
                    String var6 = (String)var5.next();
                    MarkerTransformer.access$000(this.this$0).put(var3.get(0), var6);
                }

                return true;
            }
        }
    }

    public Object getResult()
    {
        return this.getResult();
    }
}
