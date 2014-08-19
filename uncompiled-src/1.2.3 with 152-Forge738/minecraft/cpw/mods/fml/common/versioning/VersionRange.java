package cpw.mods.fml.common.versioning;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class VersionRange
{
    private final ArtifactVersion recommendedVersion;
    private final List restrictions;

    private VersionRange(ArtifactVersion var1, List var2)
    {
        this.recommendedVersion = var1;
        this.restrictions = var2;
    }

    public ArtifactVersion getRecommendedVersion()
    {
        return this.recommendedVersion;
    }

    public List getRestrictions()
    {
        return this.restrictions;
    }

    public VersionRange cloneOf()
    {
        ArrayList var1 = null;

        if (this.restrictions != null)
        {
            var1 = new ArrayList();

            if (!this.restrictions.isEmpty())
            {
                var1.addAll(this.restrictions);
            }
        }

        return new VersionRange(this.recommendedVersion, var1);
    }

    public static VersionRange createFromVersionSpec(String var0) throws InvalidVersionSpecificationException
    {
        if (var0 == null)
        {
            return null;
        }
        else
        {
            ArrayList var1 = new ArrayList();
            String var2 = var0;
            DefaultArtifactVersion var3 = null;
            ArtifactVersion var4 = null;
            ArtifactVersion var5 = null;

            while (var2.startsWith("[") || var2.startsWith("("))
            {
                int var6 = var2.indexOf(")");
                int var7 = var2.indexOf("]");
                int var8 = var7;

                if ((var7 < 0 || var6 < var7) && var6 >= 0)
                {
                    var8 = var6;
                }

                if (var8 < 0)
                {
                    throw new InvalidVersionSpecificationException("Unbounded range: " + var0);
                }

                Restriction var9 = parseRestriction(var2.substring(0, var8 + 1));

                if (var5 == null)
                {
                    var5 = var9.getLowerBound();
                }

                if (var4 != null && (var9.getLowerBound() == null || var9.getLowerBound().compareTo(var4) < 0))
                {
                    throw new InvalidVersionSpecificationException("Ranges overlap: " + var0);
                }

                var1.add(var9);
                var4 = var9.getUpperBound();
                var2 = var2.substring(var8 + 1).trim();

                if (var2.length() > 0 && var2.startsWith(","))
                {
                    var2 = var2.substring(1).trim();
                }
            }

            if (var2.length() > 0)
            {
                if (var1.size() > 0)
                {
                    throw new InvalidVersionSpecificationException("Only fully-qualified sets allowed in multiple set scenario: " + var0);
                }

                var3 = new DefaultArtifactVersion(var2);
                var1.add(Restriction.EVERYTHING);
            }

            return new VersionRange(var3, var1);
        }
    }

    private static Restriction parseRestriction(String var0) throws InvalidVersionSpecificationException
    {
        boolean var1 = var0.startsWith("[");
        boolean var2 = var0.endsWith("]");
        String var3 = var0.substring(1, var0.length() - 1).trim();
        int var5 = var3.indexOf(",");
        Restriction var4;

        if (var5 < 0)
        {
            if (!var1 || !var2)
            {
                throw new InvalidVersionSpecificationException("Single version must be surrounded by []: " + var0);
            }

            DefaultArtifactVersion var6 = new DefaultArtifactVersion(var3);
            var4 = new Restriction(var6, var1, var6, var2);
        }
        else
        {
            String var10 = var3.substring(0, var5).trim();
            String var7 = var3.substring(var5 + 1).trim();

            if (var10.equals(var7))
            {
                throw new InvalidVersionSpecificationException("Range cannot have identical boundaries: " + var0);
            }

            DefaultArtifactVersion var8 = null;

            if (var10.length() > 0)
            {
                var8 = new DefaultArtifactVersion(var10);
            }

            DefaultArtifactVersion var9 = null;

            if (var7.length() > 0)
            {
                var9 = new DefaultArtifactVersion(var7);
            }

            if (var9 != null && var8 != null && var9.compareTo(var8) < 0)
            {
                throw new InvalidVersionSpecificationException("Range defies version ordering: " + var0);
            }

            var4 = new Restriction(var8, var1, var9, var2);
        }

        return var4;
    }

    public static VersionRange createFromVersion(String var0, ArtifactVersion var1)
    {
        List var2 = Collections.emptyList();

        if (var1 == null)
        {
            var1 = new DefaultArtifactVersion(var0);
        }

        return new VersionRange((ArtifactVersion)var1, var2);
    }

    public VersionRange restrict(VersionRange var1)
    {
        List var2 = this.restrictions;
        List var3 = var1.restrictions;
        List var4;

        if (!var2.isEmpty() && !var3.isEmpty())
        {
            var4 = this.intersection(var2, var3);
        }
        else
        {
            var4 = Collections.emptyList();
        }

        ArtifactVersion var5 = null;

        if (var4.size() > 0)
        {
            Iterator var6 = var4.iterator();

            while (var6.hasNext())
            {
                Restriction var7 = (Restriction)var6.next();

                if (this.recommendedVersion != null && var7.containsVersion(this.recommendedVersion))
                {
                    var5 = this.recommendedVersion;
                    break;
                }

                if (var5 == null && var1.getRecommendedVersion() != null && var7.containsVersion(var1.getRecommendedVersion()))
                {
                    var5 = var1.getRecommendedVersion();
                }
            }
        }
        else if (this.recommendedVersion != null)
        {
            var5 = this.recommendedVersion;
        }
        else if (var1.recommendedVersion != null)
        {
            var5 = var1.recommendedVersion;
        }

        return new VersionRange(var5, var4);
    }

    private List intersection(List var1, List var2)
    {
        ArrayList var3 = new ArrayList(var1.size() + var2.size());
        Iterator var4 = var1.iterator();
        Iterator var5 = var2.iterator();
        Restriction var6 = (Restriction)var4.next();
        Restriction var7 = (Restriction)var5.next();
        boolean var8 = false;

        while (!var8)
        {
            if (var6.getLowerBound() != null && var7.getUpperBound() != null && var6.getLowerBound().compareTo(var7.getUpperBound()) > 0)
            {
                if (var5.hasNext())
                {
                    var7 = (Restriction)var5.next();
                }
                else
                {
                    var8 = true;
                }
            }
            else if (var6.getUpperBound() != null && var7.getLowerBound() != null && var6.getUpperBound().compareTo(var7.getLowerBound()) < 0)
            {
                if (var4.hasNext())
                {
                    var6 = (Restriction)var4.next();
                }
                else
                {
                    var8 = true;
                }
            }
            else
            {
                ArtifactVersion var9;
                boolean var11;
                int var13;

                if (var6.getLowerBound() == null)
                {
                    var9 = var7.getLowerBound();
                    var11 = var7.isLowerBoundInclusive();
                }
                else if (var7.getLowerBound() == null)
                {
                    var9 = var6.getLowerBound();
                    var11 = var6.isLowerBoundInclusive();
                }
                else
                {
                    var13 = var6.getLowerBound().compareTo(var7.getLowerBound());

                    if (var13 < 0)
                    {
                        var9 = var7.getLowerBound();
                        var11 = var7.isLowerBoundInclusive();
                    }
                    else if (var13 == 0)
                    {
                        var9 = var6.getLowerBound();
                        var11 = var6.isLowerBoundInclusive() && var7.isLowerBoundInclusive();
                    }
                    else
                    {
                        var9 = var6.getLowerBound();
                        var11 = var6.isLowerBoundInclusive();
                    }
                }

                ArtifactVersion var10;
                boolean var12;

                if (var6.getUpperBound() == null)
                {
                    var10 = var7.getUpperBound();
                    var12 = var7.isUpperBoundInclusive();
                }
                else if (var7.getUpperBound() == null)
                {
                    var10 = var6.getUpperBound();
                    var12 = var6.isUpperBoundInclusive();
                }
                else
                {
                    var13 = var6.getUpperBound().compareTo(var7.getUpperBound());

                    if (var13 < 0)
                    {
                        var10 = var6.getUpperBound();
                        var12 = var6.isUpperBoundInclusive();
                    }
                    else if (var13 == 0)
                    {
                        var10 = var6.getUpperBound();
                        var12 = var6.isUpperBoundInclusive() && var7.isUpperBoundInclusive();
                    }
                    else
                    {
                        var10 = var7.getUpperBound();
                        var12 = var7.isUpperBoundInclusive();
                    }
                }

                if (var9 != null && var10 != null && var9.compareTo(var10) == 0)
                {
                    if (var11 && var12)
                    {
                        var3.add(new Restriction(var9, var11, var10, var12));
                    }
                }
                else
                {
                    var3.add(new Restriction(var9, var11, var10, var12));
                }

                if (var10 == var7.getUpperBound())
                {
                    if (var5.hasNext())
                    {
                        var7 = (Restriction)var5.next();
                    }
                    else
                    {
                        var8 = true;
                    }
                }
                else if (var4.hasNext())
                {
                    var6 = (Restriction)var4.next();
                }
                else
                {
                    var8 = true;
                }
            }
        }

        return var3;
    }

    public String toString()
    {
        return this.recommendedVersion != null ? this.recommendedVersion.toString() : Joiner.on(',').join(this.restrictions);
    }

    public ArtifactVersion matchVersion(List var1)
    {
        ArtifactVersion var2 = null;
        Iterator var3 = var1.iterator();

        while (var3.hasNext())
        {
            ArtifactVersion var4 = (ArtifactVersion)var3.next();

            if (this.containsVersion(var4) && (var2 == null || var4.compareTo(var2) > 0))
            {
                var2 = var4;
            }
        }

        return var2;
    }

    public boolean containsVersion(ArtifactVersion var1)
    {
        Iterator var2 = this.restrictions.iterator();
        Restriction var3;

        do
        {
            if (!var2.hasNext())
            {
                return false;
            }

            var3 = (Restriction)var2.next();
        }
        while (!var3.containsVersion(var1));

        return true;
    }

    public boolean hasRestrictions()
    {
        return !this.restrictions.isEmpty() && this.recommendedVersion == null;
    }

    public boolean equals(Object var1)
    {
        if (this == var1)
        {
            return true;
        }
        else if (!(var1 instanceof VersionRange))
        {
            return false;
        }
        else
        {
            VersionRange var2 = (VersionRange)var1;
            boolean var3 = this.recommendedVersion == var2.recommendedVersion || this.recommendedVersion != null && this.recommendedVersion.equals(var2.recommendedVersion);
            var3 &= this.restrictions == var2.restrictions || this.restrictions != null && this.restrictions.equals(var2.restrictions);
            return var3;
        }
    }

    public int hashCode()
    {
        byte var1 = 7;
        int var2 = 31 * var1 + (this.recommendedVersion == null ? 0 : this.recommendedVersion.hashCode());
        var2 = 31 * var2 + (this.restrictions == null ? 0 : this.restrictions.hashCode());
        return var2;
    }

    public boolean isUnboundedAbove()
    {
        return this.restrictions.size() == 1 && ((Restriction)this.restrictions.get(0)).getUpperBound() == null && !((Restriction)this.restrictions.get(0)).isUpperBoundInclusive();
    }

    public String getLowerBoundString()
    {
        return this.restrictions.size() == 1 ? ((Restriction)this.restrictions.get(0)).getLowerBound().getVersionString() : "";
    }
}
