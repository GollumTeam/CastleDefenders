package cpw.mods.fml.common.toposort;

import java.util.Set;

public class ModSortingException$SortingExceptionData
{
    private Object firstBadNode;
    private Set visitedNodes;

    final ModSortingException this$0;

    public ModSortingException$SortingExceptionData(ModSortingException var1, Object var2, Set var3)
    {
        this.this$0 = var1;
        this.firstBadNode = var2;
        this.visitedNodes = var3;
    }

    public Object getFirstBadNode()
    {
        return this.firstBadNode;
    }

    public Set getVisitedNodes()
    {
        return this.visitedNodes;
    }
}
