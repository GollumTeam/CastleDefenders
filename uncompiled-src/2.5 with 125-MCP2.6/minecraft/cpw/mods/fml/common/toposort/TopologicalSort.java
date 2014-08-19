package cpw.mods.fml.common.toposort;

import java.io.PrintStream;
import java.util.*;

public class TopologicalSort
{
    public static class DirectedGraph
        implements Iterable
    {
        private final Map graph = new HashMap();
        private List orderedNodes;

        public boolean addNode(Object obj)
        {
            if (graph.containsKey(obj))
            {
                return false;
            }
            else
            {
                orderedNodes.add(obj);
                graph.put(obj, new TreeSet(new Comparator()
                {
                    public int compare(Object obj1, Object obj2)
                    {
                        return orderedNodes.indexOf(obj1) - orderedNodes.indexOf(obj2);
                    }
                }
                                          ));
                return true;
            }
        }

        public void addEdge(Object obj, Object obj1)
        {
            if (!graph.containsKey(obj) || !graph.containsKey(obj1))
            {
                throw new NoSuchElementException("Missing nodes from graph");
            }
            else
            {
                ((SortedSet)graph.get(obj)).add(obj1);
                return;
            }
        }

        public void removeEdge(Object obj, Object obj1)
        {
            if (!graph.containsKey(obj) || !graph.containsKey(obj1))
            {
                throw new NoSuchElementException("Missing nodes from graph");
            }
            else
            {
                ((SortedSet)graph.get(obj)).remove(obj1);
                return;
            }
        }

        public boolean edgeExists(Object obj, Object obj1)
        {
            if (!graph.containsKey(obj) || !graph.containsKey(obj1))
            {
                throw new NoSuchElementException("Missing nodes from graph");
            }
            else
            {
                return ((SortedSet)graph.get(obj)).contains(obj1);
            }
        }

        public Set edgesFrom(Object obj)
        {
            if (!graph.containsKey(obj))
            {
                throw new NoSuchElementException("Missing node from graph");
            }
            else
            {
                return Collections.unmodifiableSortedSet((SortedSet)graph.get(obj));
            }
        }

        public Iterator iterator()
        {
            return orderedNodes.iterator();
        }

        public int size()
        {
            return graph.size();
        }

        public boolean isEmpty()
        {
            return graph.isEmpty();
        }

        public String toString()
        {
            return graph.toString();
        }

        public DirectedGraph()
        {
            orderedNodes = new ArrayList();
        }
    }

    public TopologicalSort()
    {
    }

    public static List topologicalSort(DirectedGraph directedgraph)
    {
        DirectedGraph directedgraph1 = reverse(directedgraph);
        ArrayList arraylist = new ArrayList();
        HashSet hashset = new HashSet();
        HashSet hashset1 = new HashSet();
        Object obj;

        for (Iterator iterator = directedgraph1.iterator(); iterator.hasNext(); explore(obj, directedgraph1, arraylist, hashset, hashset1))
        {
            obj = iterator.next();
        }

        return arraylist;
    }

    public static DirectedGraph reverse(DirectedGraph directedgraph)
    {
        DirectedGraph directedgraph1 = new DirectedGraph();
        Object obj;

        for (Iterator iterator = directedgraph.iterator(); iterator.hasNext(); directedgraph1.addNode(obj))
        {
            obj = iterator.next();
        }

        for (Iterator iterator1 = directedgraph.iterator(); iterator1.hasNext();)
        {
            Object obj1 = iterator1.next();
            Iterator iterator2 = directedgraph.edgesFrom(obj1).iterator();

            while (iterator2.hasNext())
            {
                Object obj2 = iterator2.next();
                directedgraph1.addEdge(obj2, obj1);
            }
        }

        return directedgraph1;
    }

    public static void explore(Object obj, DirectedGraph directedgraph, List list, Set set, Set set1)
    {
        if (set.contains(obj))
        {
            if (set1.contains(obj))
            {
                return;
            }
            else
            {
                System.out.printf("%s: %s\n%s\n%s\n", new Object[]
                        {
                            obj, list, set, set1
                        });
                throw new IllegalArgumentException("There was a cycle detected in the input graph, sorting is not possible");
            }
        }

        set.add(obj);
        Object obj1;

        for (Iterator iterator = directedgraph.edgesFrom(obj).iterator(); iterator.hasNext(); explore(obj1, directedgraph, list, set, set1))
        {
            obj1 = iterator.next();
        }

        list.add(obj);
        set1.add(obj);
    }
}
