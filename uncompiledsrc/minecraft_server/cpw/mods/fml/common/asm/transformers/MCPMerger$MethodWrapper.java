package cpw.mods.fml.common.asm.transformers;

import com.google.common.base.Objects;
import org.objectweb.asm.tree.MethodNode;

class MCPMerger$MethodWrapper
{
    private MethodNode node;
    public boolean client;
    public boolean server;

    public MCPMerger$MethodWrapper(MethodNode var1)
    {
        this.node = var1;
    }

    public boolean equals(Object var1)
    {
        if (var1 != null && var1 instanceof MCPMerger$MethodWrapper)
        {
            MCPMerger$MethodWrapper var2 = (MCPMerger$MethodWrapper)var1;
            boolean var3 = Objects.equal(this.node.name, var2.node.name) && Objects.equal(this.node.desc, var2.node.desc);

            if (var3)
            {
                var2.client |= this.client;
                var2.server |= this.server;
                this.client |= var2.client;
                this.server |= var2.server;
            }

            return var3;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return Objects.hashCode(new Object[] {this.node.name, this.node.desc});
    }

    public String toString()
    {
        return Objects.toStringHelper(this).add("name", this.node.name).add("desc", this.node.desc).add("server", this.server).add("client", this.client).toString();
    }

    static MethodNode access$000(MCPMerger$MethodWrapper var0)
    {
        return var0.node;
    }
}
