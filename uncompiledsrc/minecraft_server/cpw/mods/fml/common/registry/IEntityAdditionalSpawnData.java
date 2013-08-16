package cpw.mods.fml.common.registry;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public interface IEntityAdditionalSpawnData
{
    void writeSpawnData(ByteArrayDataOutput var1);

    void readSpawnData(ByteArrayDataInput var1);
}
