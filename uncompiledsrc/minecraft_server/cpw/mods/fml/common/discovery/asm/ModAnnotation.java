package cpw.mods.fml.common.discovery.asm;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.discovery.asm.ASMModParser$AnnotationType;
import cpw.mods.fml.common.discovery.asm.ModAnnotation$EnumHolder;
import java.util.ArrayList;
import java.util.Map;
import org.objectweb.asm.Type;

public class ModAnnotation
{
    ASMModParser$AnnotationType type;
    Type asmType;
    String member;
    Map values = Maps.newHashMap();
    private ArrayList arrayList;
    private Object array;
    private String arrayName;
    private ModAnnotation parent;

    public ModAnnotation(ASMModParser$AnnotationType var1, Type var2, String var3)
    {
        this.type = var1;
        this.asmType = var2;
        this.member = var3;
    }

    public ModAnnotation(ASMModParser$AnnotationType var1, Type var2, ModAnnotation var3)
    {
        this.type = var1;
        this.asmType = var2;
        this.parent = var3;
    }

    public String toString()
    {
        return Objects.toStringHelper("Annotation").add("type", this.type).add("name", this.asmType.getClassName()).add("member", this.member).add("values", this.values).toString();
    }

    public ASMModParser$AnnotationType getType()
    {
        return this.type;
    }

    public Type getASMType()
    {
        return this.asmType;
    }

    public String getMember()
    {
        return this.member;
    }

    public Map getValues()
    {
        return this.values;
    }

    public void addArray(String var1)
    {
        this.arrayList = Lists.newArrayList();
        this.arrayName = var1;
    }

    public void addProperty(String var1, Object var2)
    {
        if (this.arrayList != null)
        {
            this.arrayList.add(var2);
        }
        else
        {
            this.values.put(var1, var2);
        }
    }

    public void addEnumProperty(String var1, String var2, String var3)
    {
        this.values.put(var1, new ModAnnotation$EnumHolder(this, var2, var3));
    }

    public void endArray()
    {
        this.values.put(this.arrayName, this.arrayList);
        this.arrayList = null;
    }

    public ModAnnotation addChildAnnotation(String var1, String var2)
    {
        return new ModAnnotation(ASMModParser$AnnotationType.SUBTYPE, Type.getType(var2), this);
    }
}
