package cpw.mods.fml.common.discovery;

import java.util.Map;

public class ASMDataTable$ASMData
{
    private ModCandidate candidate;
    private String annotationName;
    private String className;
    private String objectName;
    private Map annotationInfo;

    public ASMDataTable$ASMData(ModCandidate var1, String var2, String var3, String var4, Map var5)
    {
        this.candidate = var1;
        this.annotationName = var2;
        this.className = var3;
        this.objectName = var4;
        this.annotationInfo = var5;
    }

    public ModCandidate getCandidate()
    {
        return this.candidate;
    }

    public String getAnnotationName()
    {
        return this.annotationName;
    }

    public String getClassName()
    {
        return this.className;
    }

    public String getObjectName()
    {
        return this.objectName;
    }

    public Map getAnnotationInfo()
    {
        return this.annotationInfo;
    }

    static ModCandidate access$000(ASMDataTable$ASMData var0)
    {
        return var0.candidate;
    }
}
