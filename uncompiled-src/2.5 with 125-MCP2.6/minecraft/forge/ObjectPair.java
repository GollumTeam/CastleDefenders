package forge;

public class ObjectPair
{
    private Object object1;
    private Object object2;

    public ObjectPair(Object obj, Object obj1)
    {
        object1 = obj;
        object2 = obj1;
    }

    public Object getValue1()
    {
        return object1;
    }

    public Object getValue2()
    {
        return object2;
    }

    public void setValue1(Object obj)
    {
        object1 = obj;
    }

    public void setValue2(Object obj)
    {
        object2 = obj;
    }
}
