package cpw.mods.fml.common.toposort;

import cpw.mods.fml.common.toposort.TopologicalSort$DirectedGraph$1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TopologicalSort$DirectedGraph implements Iterable
{
    private final Map graph = new HashMap();
    private List orderedNodes = new ArrayList();

    public boolean addNode(Object var1)
    {
        if (this.graph.containsKey(var1))
        {
            return false;
        }
        else
        {
            this.orderedNodes.add(var1);
            this.graph.put(var1, new TreeSet(new TopologicalSort$DirectedGraph$1(this)));
            return true;
        }
    }

    public void addEdge(Object var1, Object var2)
    {
        if (this.graph.containsKey(var1) && this.graph.containsKey(var2))
        {
            ((SortedSet)this.graph.get(var1)).add(var2);
        }
        else
        {
            throw new NoSuchElementException("Missing nodes from graph");
        }
    }

    public void removeEdge(Object var1, Object var2)
    {
        if (this.graph.containsKey(var1) && this.graph.containsKey(var2))
        {
            ((SortedSet)this.graph.get(var1)).remove(var2);
        }
        else
        {
            throw new NoSuchElementException("Missing nodes from graph");
        }
    }

    public boolean edgeExists(Object var1, Object var2)
    {
        if (this.graph.containsKey(var1) && this.graph.containsKey(var2))
        {
            return ((SortedSet)this.graph.get(var1)).contains(var2);
        }
        else
        {
            throw new NoSuchElementException("Missing nodes from graph");
        }
    }

    public Set edgesFrom(Object var1)
    {
        if (!this.graph.containsKey(var1))
        {
            throw new NoSuchElementException("Missing node from graph");
        }
        else
        {
            return Collections.unmodifiableSortedSet((SortedSet)this.graph.get(var1));
        }
    }

    public Iterator iterator()
    {
        return this.orderedNodes.iterator();
    }

    public int size()
    {
        return this.graph.size();
    }

    public boolean isEmpty()
    {
        return this.graph.isEmpty();
    }

    public String toString()
    {
        return this.graph.toString();
    }

    static List access$000(TopologicalSort$DirectedGraph var0)
    {
        return var0.orderedNodes;
    }
}
