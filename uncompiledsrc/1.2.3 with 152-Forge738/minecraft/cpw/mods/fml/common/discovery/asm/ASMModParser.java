package cpw.mods.fml.common.discovery.asm;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.discovery.ModCandidate;
import cpw.mods.fml.common.discovery.asm.ASMModParser$AnnotationType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;

public class ASMModParser
{
    private Type asmType;
    private int classVersion;
    private Type asmSuperType;
    private LinkedList annotations = Lists.newLinkedList();
    private String baseModProperties;

    public ASMModParser(InputStream var1) throws IOException
    {
        try
        {
            ClassReader var2 = new ClassReader(var1);
            var2.accept(new ModClassVisitor(this), 0);
        }
        catch (Exception var3)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var3, "Unable to read a class file correctly", new Object[0]);
            throw new LoaderException(var3);
        }
    }

    public void beginNewTypeName(String var1, int var2, String var3)
    {
        this.asmType = Type.getObjectType(var1);
        this.classVersion = var2;
        this.asmSuperType = !Strings.isNullOrEmpty(var3) ? Type.getObjectType(var3) : null;
    }

    public void startClassAnnotation(String var1)
    {
        ModAnnotation var2 = new ModAnnotation(ASMModParser$AnnotationType.CLASS, Type.getType(var1), this.asmType.getClassName());
        this.annotations.addFirst(var2);
    }

    public void addAnnotationProperty(String var1, Object var2)
    {
        ((ModAnnotation)this.annotations.getFirst()).addProperty(var1, var2);
    }

    public void startFieldAnnotation(String var1, String var2)
    {
        ModAnnotation var3 = new ModAnnotation(ASMModParser$AnnotationType.FIELD, Type.getType(var2), var1);
        this.annotations.addFirst(var3);
    }

    public String toString()
    {
        return Objects.toStringHelper("ASMAnnotationDiscoverer").add("className", this.asmType.getClassName()).add("classVersion", this.classVersion).add("superName", this.asmSuperType.getClassName()).add("annotations", this.annotations).add("isBaseMod", this.isBaseMod(Collections.emptyList())).add("baseModProperties", this.baseModProperties).toString();
    }

    public Type getASMType()
    {
        return this.asmType;
    }

    public int getClassVersion()
    {
        return this.classVersion;
    }

    public Type getASMSuperType()
    {
        return this.asmSuperType;
    }

    public LinkedList getAnnotations()
    {
        return this.annotations;
    }

    public void validate() {}

    public boolean isBaseMod(List var1)
    {
        return this.getASMSuperType().equals(Type.getType("LBaseMod;")) || this.getASMSuperType().equals(Type.getType("Lnet/minecraft/src/BaseMod;")) || var1.contains(this.getASMSuperType().getClassName());
    }

    public void setBaseModProperties(String var1)
    {
        this.baseModProperties = var1;
    }

    public String getBaseModProperties()
    {
        return this.baseModProperties;
    }

    public void sendToTable(ASMDataTable var1, ModCandidate var2)
    {
        Iterator var3 = this.annotations.iterator();

        while (var3.hasNext())
        {
            ModAnnotation var4 = (ModAnnotation)var3.next();
            var1.addASMData(var2, var4.asmType.getClassName(), this.asmType.getClassName(), var4.member, var4.values);
        }
    }

    public void addAnnotationArray(String var1)
    {
        ((ModAnnotation)this.annotations.getFirst()).addArray(var1);
    }

    public void addAnnotationEnumProperty(String var1, String var2, String var3)
    {
        ((ModAnnotation)this.annotations.getFirst()).addEnumProperty(var1, var2, var3);
    }

    public void endArray()
    {
        ((ModAnnotation)this.annotations.getFirst()).endArray();
    }

    public void addSubAnnotation(String var1, String var2)
    {
        ModAnnotation var3 = (ModAnnotation)this.annotations.getFirst();
        this.annotations.addFirst(var3.addChildAnnotation(var1, var2));
    }

    public void endSubAnnotation()
    {
        ModAnnotation var1 = (ModAnnotation)this.annotations.removeFirst();
        this.annotations.addLast(var1);
    }
}
