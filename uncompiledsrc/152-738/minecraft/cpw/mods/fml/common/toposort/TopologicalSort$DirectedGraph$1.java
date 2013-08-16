package cpw.mods.fml.common.toposort;

import cpw.mods.fml.common.toposort.TopologicalSort$DirectedGraph;
import java.util.Comparator;

class TopologicalSort$DirectedGraph$1 implements Comparator
{
    final TopologicalSort$DirectedGraph this$0;

    TopologicalSort$DirectedGraph$1(TopologicalSort$DirectedGraph var1)
    {
        this.this$0 = var1;
    }

    public int compare(Object var1, Object var2)
    {
        return TopologicalSort$DirectedGraph.access$000(this.this$0).indexOf(var1) - TopologicalSort$DirectedGraph.access$000(this.this$0).indexOf(var2);
    }
}
