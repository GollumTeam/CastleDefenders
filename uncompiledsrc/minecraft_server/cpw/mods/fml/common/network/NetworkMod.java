package cpw.mods.fml.common.network;

import cpw.mods.fml.common.network.NetworkMod$NULL;
import cpw.mods.fml.common.network.NetworkMod$SidedPacketHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE})
public @interface NetworkMod
{

boolean clientSideRequired() default false;

boolean serverSideRequired() default false;

String[] channels() default {};

String versionBounds() default "";

Class packetHandler() default NetworkMod$NULL.class;

Class tinyPacketHandler() default NetworkMod$NULL.class;

Class connectionHandler() default NetworkMod$NULL.class;

NetworkMod$SidedPacketHandler clientPacketHandlerSpec() default       @NetworkMod$SidedPacketHandler(
            channels = {},
            packetHandler = NetworkMod$NULL.class
        );

NetworkMod$SidedPacketHandler serverPacketHandlerSpec() default       @NetworkMod$SidedPacketHandler(
            channels = {},
            packetHandler = NetworkMod$NULL.class
        );
}
