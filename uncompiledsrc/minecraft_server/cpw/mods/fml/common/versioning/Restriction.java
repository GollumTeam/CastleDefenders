package cpw.mods.fml.common.versioning;

public class Restriction
{
    private final ArtifactVersion lowerBound;
    private final boolean lowerBoundInclusive;
    private final ArtifactVersion upperBound;
    private final boolean upperBoundInclusive;
    public static final Restriction EVERYTHING = new Restriction((ArtifactVersion)null, false, (ArtifactVersion)null, false);

    public Restriction(ArtifactVersion var1, boolean var2, ArtifactVersion var3, boolean var4)
    {
        this.lowerBound = var1;
        this.lowerBoundInclusive = var2;
        this.upperBound = var3;
        this.upperBoundInclusive = var4;
    }

    public ArtifactVersion getLowerBound()
    {
        return this.lowerBound;
    }

    public boolean isLowerBoundInclusive()
    {
        return this.lowerBoundInclusive;
    }

    public ArtifactVersion getUpperBound()
    {
        return this.upperBound;
    }

    public boolean isUpperBoundInclusive()
    {
        return this.upperBoundInclusive;
    }

    public boolean containsVersion(ArtifactVersion var1)
    {
        int var2;

        if (this.lowerBound != null)
        {
            var2 = this.lowerBound.compareTo(var1);

            if (var2 == 0 && !this.lowerBoundInclusive)
            {
                return false;
            }

            if (var2 > 0)
            {
                return false;
            }
        }

        if (this.upperBound != null)
        {
            var2 = this.upperBound.compareTo(var1);

            if (var2 == 0 && !this.upperBoundInclusive)
            {
                return false;
            }

            if (var2 < 0)
            {
                return false;
            }
        }

        return true;
    }

    public int hashCode()
    {
        byte var1 = 13;
        int var2;

        if (this.lowerBound == null)
        {
            var2 = var1 + 1;
        }
        else
        {
            var2 = var1 + this.lowerBound.hashCode();
        }

        var2 *= this.lowerBoundInclusive ? 1 : 2;

        if (this.upperBound == null)
        {
            var2 -= 3;
        }
        else
        {
            var2 -= this.upperBound.hashCode();
        }

        var2 *= this.upperBoundInclusive ? 2 : 3;
        return var2;
    }

    public boolean equals(Object var1)
    {
        if (this == var1)
        {
            return true;
        }
        else if (!(var1 instanceof Restriction))
        {
            return false;
        }
        else
        {
            Restriction var2 = (Restriction)var1;

            if (this.lowerBound != null)
            {
                if (!this.lowerBound.equals(var2.lowerBound))
                {
                    return false;
                }
            }
            else if (var2.lowerBound != null)
            {
                return false;
            }

            if (this.lowerBoundInclusive != var2.lowerBoundInclusive)
            {
                return false;
            }
            else
            {
                if (this.upperBound != null)
                {
                    if (!this.upperBound.equals(var2.upperBound))
                    {
                        return false;
                    }
                }
                else if (var2.upperBound != null)
                {
                    return false;
                }

                return this.upperBoundInclusive == var2.upperBoundInclusive;
            }
        }
    }

    public String toString()
    {
        StringBuilder var1 = new StringBuilder();
        var1.append(this.isLowerBoundInclusive() ? "[" : "(");

        if (this.getLowerBound() != null)
        {
            var1.append(this.getLowerBound().toString());
        }

        var1.append(",");

        if (this.getUpperBound() != null)
        {
            var1.append(this.getUpperBound().toString());
        }

        var1.append(this.isUpperBoundInclusive() ? "]" : ")");
        return var1.toString();
    }
}
