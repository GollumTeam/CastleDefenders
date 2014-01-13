package cpw.mods.fml.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD})
public @interface SidedProxy
{

String clientSide() default "";

String serverSide() default "";

    @Deprecated

String bukkitSide() default "";

String modId() default "";
}
