package cpw.mods.fml.common.toposort;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.toposort.TopologicalSort$DirectedGraph;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ModSorter
{
    private TopologicalSort$DirectedGraph modGraph;
    private ModContainer beforeAll = new DummyModContainer("BeforeAll");
    private ModContainer afterAll = new DummyModContainer("AfterAll");
    private ModContainer before = new DummyModContainer("Before");
    private ModContainer after = new DummyModContainer("After");

    public ModSorter(List var1, Map var2)
    {
        this.buildGraph(var1, var2);
    }

    private void buildGraph(List var1, Map var2)
    {
        this.modGraph = new TopologicalSort$DirectedGraph();
        this.modGraph.addNode(this.beforeAll);
        this.modGraph.addNode(this.before);
        this.modGraph.addNode(this.afterAll);
        this.modGraph.addNode(this.after);
        this.modGraph.addEdge(this.before, this.after);
        this.modGraph.addEdge(this.beforeAll, this.before);
        this.modGraph.addEdge(this.after, this.afterAll);
        Iterator var3 = var1.iterator();
        ModContainer var4;

        while (var3.hasNext())
        {
            var4 = (ModContainer)var3.next();
            this.modGraph.addNode(var4);
        }

        var3 = var1.iterator();

        while (var3.hasNext())
        {
            var4 = (ModContainer)var3.next();

            if (var4.isImmutable())
            {
                this.modGraph.addEdge(this.beforeAll, var4);
                this.modGraph.addEdge(var4, this.before);
            }
            else
            {
                boolean var5 = false;
                boolean var6 = false;
                Iterator var7 = var4.getDependencies().iterator();
                ArtifactVersion var8;
                String var9;

                while (var7.hasNext())
                {
                    var8 = (ArtifactVersion)var7.next();
                    var5 = true;
                    var9 = var8.getLabel();

                    if (var9.equals("*"))
                    {
                        this.modGraph.addEdge(var4, this.afterAll);
                        this.modGraph.addEdge(this.after, var4);
                        var6 = true;
                    }
                    else
                    {
                        this.modGraph.addEdge(this.before, var4);

                        if (Loader.isModLoaded(var9))
                        {
                            this.modGraph.addEdge(var2.get(var9), var4);
                        }
                    }
                }

                var7 = var4.getDependants().iterator();

                while (var7.hasNext())
                {
                    var8 = (ArtifactVersion)var7.next();
                    var6 = true;
                    var9 = var8.getLabel();

                    if (var9.equals("*"))
                    {
                        this.modGraph.addEdge(this.beforeAll, var4);
                        this.modGraph.addEdge(var4, this.before);
                        var5 = true;
                    }
                    else
                    {
                        this.modGraph.addEdge(var4, this.after);

                        if (Loader.isModLoaded(var9))
                        {
                            this.modGraph.addEdge(var4, var2.get(var9));
                        }
                    }
                }

                if (!var5)
                {
                    this.modGraph.addEdge(this.before, var4);
                }

                if (!var6)
                {
                    this.modGraph.addEdge(var4, this.after);
                }
            }
        }
    }

    public List sort()
    {
        List var1 = TopologicalSort.topologicalSort(this.modGraph);
        var1.removeAll(Arrays.asList(new ModContainer[] {this.beforeAll, this.before, this.after, this.afterAll}));
        return var1;
    }
}
