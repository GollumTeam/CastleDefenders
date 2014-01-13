package cpw.mods.fml.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE})
public @interface Mod
{
    String modid();

String name() default "";

String version() default "";

String dependencies() default "";

boolean useMetadata() default false;

String acceptedMinecraftVersions() default "";

String bukkitPlugin() default "";

String modExclusionList() default "";

String certificateFingerprint() default "";

String modLanguage() default "java";

String asmHookClass() default "";
}
