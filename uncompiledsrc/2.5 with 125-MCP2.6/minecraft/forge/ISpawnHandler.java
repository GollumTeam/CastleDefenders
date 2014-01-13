package forge;

import java.io.*;

public interface ISpawnHandler
{
    public abstract void writeSpawnData(DataOutputStream dataoutputstream) throws IOException;

    public abstract void readSpawnData(DataInputStream datainputstream) throws IOException;
}
