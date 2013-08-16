package cpw.mods.fml.common.discovery;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.ImmutableMap.Builder;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.discovery.ASMDataTable$ASMData;
import cpw.mods.fml.common.discovery.ASMDataTable$ModContainerPredicate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ASMDataTable
{
    private SetMultimap globalAnnotationData = HashMultimap.create();
    private Map containerAnnotationData;
    private List containers = Lists.newArrayList();

    public SetMultimap getAnnotationsFor(ModContainer var1)
    {
        if (this.containerAnnotationData == null)
        {
            Builder var2 = ImmutableMap.builder();
            Iterator var3 = this.containers.iterator();

            while (var3.hasNext())
            {
                ModContainer var4 = (ModContainer)var3.next();
                Multimap var5 = Multimaps.filterValues(this.globalAnnotationData, new ASMDataTable$ModContainerPredicate(var4));
                var2.put(var4, ImmutableSetMultimap.copyOf(var5));
            }

            this.containerAnnotationData = var2.build();
        }

        return (SetMultimap)this.containerAnnotationData.get(var1);
    }

    public Set getAll(String var1)
    {
        return this.globalAnnotationData.get(var1);
    }

    public void addASMData(ModCandidate var1, String var2, String var3, String var4, Map var5)
    {
        this.globalAnnotationData.put(var2, new ASMDataTable$ASMData(var1, var2, var3, var4, var5));
    }

    public void addContainer(ModContainer var1)
    {
        this.containers.add(var1);
    }
}
