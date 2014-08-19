package cpw.mods.fml.common.toposort;

import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.ModContainer;
import java.util.*;

public class ModSorter
{
    private TopologicalSort.DirectedGraph modGraph;
    private ModContainer beforeAll;
    private ModContainer afterAll;
    private ModContainer before;
    private ModContainer after;

    public ModSorter(List list, Map map)
    {
        beforeAll = new FMLModContainer("DummyBeforeAll");
        afterAll = new FMLModContainer("DummyAfterAll");
        before = new FMLModContainer("DummyBefore");
        after = new FMLModContainer("DummyAfter");
        buildGraph(list, map);
    }

    private void buildGraph(List list, Map map)
    {
        modGraph = new TopologicalSort.DirectedGraph();
        modGraph.addNode(beforeAll);
        modGraph.addNode(before);
        modGraph.addNode(afterAll);
        modGraph.addNode(after);
        modGraph.addEdge(before, after);
        modGraph.addEdge(beforeAll, before);
        modGraph.addEdge(after, afterAll);
        ModContainer modcontainer;

        for (Iterator iterator = list.iterator(); iterator.hasNext(); modGraph.addNode(modcontainer))
        {
            modcontainer = (ModContainer)iterator.next();
        }

        Iterator iterator1 = list.iterator();

        do
        {
            if (!iterator1.hasNext())
            {
                break;
            }

            ModContainer modcontainer1 = (ModContainer)iterator1.next();
            boolean flag = false;
            boolean flag1 = false;
            Iterator iterator2 = modcontainer1.getPreDepends().iterator();

            do
            {
                if (!iterator2.hasNext())
                {
                    break;
                }

                String s = (String)iterator2.next();
                flag = true;

                if (s.equals("*"))
                {
                    modGraph.addEdge(modcontainer1, afterAll);
                    modGraph.addEdge(after, modcontainer1);
                    flag1 = true;
                }
                else
                {
                    modGraph.addEdge(before, modcontainer1);

                    if (map.containsKey(s))
                    {
                        modGraph.addEdge(map.get(s), modcontainer1);
                    }
                }
            }
            while (true);

            iterator2 = modcontainer1.getPostDepends().iterator();

            do
            {
                if (!iterator2.hasNext())
                {
                    break;
                }

                String s1 = (String)iterator2.next();
                flag1 = true;

                if (s1.equals("*"))
                {
                    modGraph.addEdge(beforeAll, modcontainer1);
                    modGraph.addEdge(modcontainer1, before);
                    flag = true;
                }
                else
                {
                    modGraph.addEdge(modcontainer1, after);

                    if (map.containsKey(s1))
                    {
                        modGraph.addEdge(modcontainer1, map.get(s1));
                    }
                }
            }
            while (true);

            if (!flag)
            {
                modGraph.addEdge(before, modcontainer1);
            }

            if (!flag1)
            {
                modGraph.addEdge(modcontainer1, after);
            }
        }
        while (true);
    }

    public List sort()
    {
        List list = TopologicalSort.topologicalSort(modGraph);
        list.removeAll(Arrays.asList(new ModContainer[]
                {
                    beforeAll, before, after, afterAll
                }));
        return list;
    }
}
