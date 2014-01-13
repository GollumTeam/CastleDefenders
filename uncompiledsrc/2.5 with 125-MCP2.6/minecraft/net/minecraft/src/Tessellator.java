package net.minecraft.src;

import java.nio.*;
import java.util.Arrays;
import org.lwjgl.opengl.*;

public class Tessellator
{
    private static int nativeBufferSize;
    private static int trivertsInBuffer;
    public static boolean renderingWorldRenderer = false;
    public boolean defaultTexture;
    private int rawBufferSize;
    public int textureID;

    /**
     * Boolean used to check whether quads should be drawn as four triangles. Initialized to true and never changed.
     */
    private static boolean convertQuadsToTriangles = false;

    /**
     * Boolean used to check if we should use vertex buffers. Initialized to false and never changed.
     */
    private static boolean tryVBO;

    /** The byte buffer used for GL allocation. */
    private static ByteBuffer byteBuffer;

    /** The same memory as byteBuffer, but referenced as an integer buffer. */
    private static IntBuffer intBuffer;

    /** The same memory as byteBuffer, but referenced as an float buffer. */
    private static FloatBuffer floatBuffer;

    /** Short buffer */
    private static ShortBuffer shortBuffer;
    private int rawBuffer[];

    /**
     * The number of vertices to be drawn in the next draw call. Reset to 0 between draw calls.
     */
    private int vertexCount;

    /** The first coordinate to be used for the texture. */
    private double textureU;

    /** The second coordinate to be used for the texture. */
    private double textureV;
    private int brightness;

    /** The color (RGBA) value to be used for the following draw call. */
    private int color;

    /**
     * Whether the current draw object for this tessellator has color values.
     */
    private boolean hasColor;

    /**
     * Whether the current draw object for this tessellator has texture coordinates.
     */
    private boolean hasTexture;
    private boolean hasBrightness;

    /**
     * Whether the current draw object for this tessellator has normal values.
     */
    private boolean hasNormals;

    /** The index into the raw buffer to be used for the next data. */
    private int rawBufferIndex;

    /**
     * The number of vertices manually added to the given draw call. This differs from vertexCount because it adds extra
     * vertices when converting quads to triangles.
     */
    private int addedVertices;

    /** Disables all color information for the following draw call. */
    private boolean isColorDisabled;

    /** The draw mode currently being used by the tessellator. */
    public int drawMode;

    /**
     * An offset to be applied along the x-axis for all vertices in this draw call.
     */
    public double xOffset;

    /**
     * An offset to be applied along the y-axis for all vertices in this draw call.
     */
    public double yOffset;

    /**
     * An offset to be applied along the z-axis for all vertices in this draw call.
     */
    public double zOffset;

    /** The normal to be applied to the face being drawn. */
    private int normal;

    /** The static instance of the Tessellator. */
    public static Tessellator instance;

    /** Whether this tessellator is currently in draw mode. */
    public boolean isDrawing;

    /** Whether we are currently using VBO or not. */
    private static boolean useVBO;

    /** An IntBuffer used to store the indices of vertex buffer objects. */
    private static IntBuffer vertexBuffers;

    /**
     * The index of the last VBO used. This is used in round-robin fashion, sequentially, through the vboCount vertex
     * buffers.
     */
    private int vboIndex;

    /** Number of vertex buffer objects allocated for use. */
    private static int vboCount;

    public Tessellator()
    {
        defaultTexture = false;
        rawBufferSize = 0;
        textureID = 0;
        vertexCount = 0;
        hasColor = false;
        hasTexture = false;
        hasBrightness = false;
        hasNormals = false;
        rawBufferIndex = 0;
        addedVertices = 0;
        isColorDisabled = false;
        isDrawing = false;
        vboIndex = 0;
        rawBuffer = null;
    }

    /**
     * Draws the data set up in this tessellator and resets the state to prepare for new drawing.
     */
    public int draw()
    {
        if (!isDrawing)
        {
            throw new IllegalStateException("Not tesselating!");
        }

        isDrawing = false;
        int i = 0;

        do
        {
            if (i >= vertexCount)
            {
                break;
            }

            int j = 0;

            if (drawMode == 7 && convertQuadsToTriangles)
            {
                Math.min(vertexCount - i, trivertsInBuffer);
            }
            else
            {
                j = Math.min(vertexCount - i, nativeBufferSize >> 5);
            }

            Tessellator _tmp = this;
            intBuffer.clear();
            intBuffer.put(rawBuffer, i * 8, j * 8);
            Tessellator _tmp1 = this;
            byteBuffer.position(0);
            byteBuffer.limit(j * 32);
            i += j;
            Tessellator _tmp2 = this;

            if (useVBO)
            {
                vboIndex = (vboIndex + 1) % vboCount;
                Tessellator _tmp3 = this;
                ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBuffers.get(vboIndex));
                ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, byteBuffer, ARBVertexBufferObject.GL_STREAM_DRAW_ARB);
            }

            if (hasTexture)
            {
                Tessellator _tmp4 = this;

                if (useVBO)
                {
                    GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, 12L);
                }
                else
                {
                    Tessellator _tmp5 = this;
                    floatBuffer.position(3);
                    GL11.glTexCoordPointer(2, 32, floatBuffer);
                }

                GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            }

            if (hasBrightness)
            {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                Tessellator _tmp6 = this;

                if (useVBO)
                {
                    GL11.glTexCoordPointer(2, GL11.GL_SHORT, 32, 28L);
                }
                else
                {
                    Tessellator _tmp7 = this;
                    shortBuffer.position(14);
                    GL11.glTexCoordPointer(2, 32, shortBuffer);
                }

                GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }

            if (hasColor)
            {
                Tessellator _tmp8 = this;

                if (useVBO)
                {
                    GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 32, 20L);
                }
                else
                {
                    Tessellator _tmp9 = this;
                    byteBuffer.position(20);
                    GL11.glColorPointer(4, true, 32, byteBuffer);
                }

                GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
            }

            if (hasNormals)
            {
                Tessellator _tmp10 = this;

                if (useVBO)
                {
                    GL11.glNormalPointer(GL11.GL_UNSIGNED_BYTE, 32, 24L);
                }
                else
                {
                    Tessellator _tmp11 = this;
                    byteBuffer.position(24);
                    GL11.glNormalPointer(32, byteBuffer);
                }

                GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
            }

            Tessellator _tmp12 = this;

            if (useVBO)
            {
                GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, 0L);
            }
            else
            {
                Tessellator _tmp13 = this;
                floatBuffer.position(0);
                GL11.glVertexPointer(3, 32, floatBuffer);
            }

            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

            if (drawMode == 7 && convertQuadsToTriangles)
            {
                GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, j);
            }
            else
            {
                GL11.glDrawArrays(drawMode, 0, j);
            }

            GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

            if (hasTexture)
            {
                GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            }

            if (hasBrightness)
            {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }

            if (hasColor)
            {
                GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
            }

            if (hasNormals)
            {
                GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
            }
        }
        while (true);

        if (rawBufferSize > 0x20000 && rawBufferIndex < rawBufferSize << 3)
        {
            rawBufferSize = 0;
            rawBuffer = null;
        }

        int k = rawBufferIndex * 4;
        reset();
        return k;
    }

    /**
     * Clears the tessellator state in preparation for new drawing.
     */
    private void reset()
    {
        vertexCount = 0;
        Tessellator _tmp = this;
        byteBuffer.clear();
        rawBufferIndex = 0;
        addedVertices = 0;
    }

    /**
     * Sets draw mode in the tessellator to draw quads.
     */
    public void startDrawingQuads()
    {
        startDrawing(7);
    }

    /**
     * Resets tessellator state and prepares for drawing (with the specified draw mode).
     */
    public void startDrawing(int par1)
    {
        if (isDrawing)
        {
            throw new IllegalStateException("Already tesselating!");
        }
        else
        {
            isDrawing = true;
            reset();
            drawMode = par1;
            hasNormals = false;
            hasColor = false;
            hasTexture = false;
            hasBrightness = false;
            isColorDisabled = false;
            return;
        }
    }

    /**
     * Sets the texture coordinates.
     */
    public void setTextureUV(double par1, double par3)
    {
        hasTexture = true;
        textureU = par1;
        textureV = par3;
    }

    public void setBrightness(int par1)
    {
        hasBrightness = true;
        brightness = par1;
    }

    /**
     * Sets the RGB values as specified, converting from floats between 0 and 1 to integers from 0-255.
     */
    public void setColorOpaque_F(float par1, float par2, float par3)
    {
        setColorOpaque((int)(par1 * 255F), (int)(par2 * 255F), (int)(par3 * 255F));
    }

    /**
     * Sets the RGBA values for the color, converting from floats between 0 and 1 to integers from 0-255.
     */
    public void setColorRGBA_F(float par1, float par2, float par3, float par4)
    {
        setColorRGBA((int)(par1 * 255F), (int)(par2 * 255F), (int)(par3 * 255F), (int)(par4 * 255F));
    }

    /**
     * Sets the RGB values as specified, and sets alpha to opaque.
     */
    public void setColorOpaque(int par1, int par2, int par3)
    {
        setColorRGBA(par1, par2, par3, 255);
    }

    /**
     * Sets the RGBA values for the color. Also clamps them to 0-255.
     */
    public void setColorRGBA(int par1, int par2, int par3, int par4)
    {
        if (!isColorDisabled)
        {
            if (par1 > 255)
            {
                par1 = 255;
            }

            if (par2 > 255)
            {
                par2 = 255;
            }

            if (par3 > 255)
            {
                par3 = 255;
            }

            if (par4 > 255)
            {
                par4 = 255;
            }

            if (par1 < 0)
            {
                par1 = 0;
            }

            if (par2 < 0)
            {
                par2 = 0;
            }

            if (par3 < 0)
            {
                par3 = 0;
            }

            if (par4 < 0)
            {
                par4 = 0;
            }

            hasColor = true;

            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
            {
                color = par4 << 24 | par3 << 16 | par2 << 8 | par1;
            }
            else
            {
                color = par1 << 24 | par2 << 16 | par3 << 8 | par4;
            }
        }
    }

    /**
     * Adds a vertex specifying both x,y,z and the texture u,v for it.
     */
    public void addVertexWithUV(double par1, double par3, double par5, double par7, double par9)
    {
        setTextureUV(par7, par9);
        addVertex(par1, par3, par5);
    }

    /**
     * Adds a vertex with the specified x,y,z to the current draw call. It will trigger a draw() if the buffer gets
     * full.
     */
    public void addVertex(double par1, double par3, double par5)
    {
        if (rawBufferIndex >= rawBufferSize - 32)
        {
            if (rawBufferSize == 0)
            {
                rawBufferSize = 0x10000;
                rawBuffer = new int[rawBufferSize];
            }
            else
            {
                rawBufferSize *= 2;
                rawBuffer = Arrays.copyOf(rawBuffer, rawBufferSize);
            }
        }

        addedVertices++;

        if (drawMode == 7 && convertQuadsToTriangles && addedVertices % 4 == 0)
        {
            for (int i = 0; i < 2; i++)
            {
                int j = 8 * (3 - i);

                if (hasTexture)
                {
                    rawBuffer[rawBufferIndex + 3] = rawBuffer[(rawBufferIndex - j) + 3];
                    rawBuffer[rawBufferIndex + 4] = rawBuffer[(rawBufferIndex - j) + 4];
                }

                if (hasBrightness)
                {
                    rawBuffer[rawBufferIndex + 7] = rawBuffer[(rawBufferIndex - j) + 7];
                }

                if (hasColor)
                {
                    rawBuffer[rawBufferIndex + 5] = rawBuffer[(rawBufferIndex - j) + 5];
                }

                rawBuffer[rawBufferIndex + 0] = rawBuffer[(rawBufferIndex - j) + 0];
                rawBuffer[rawBufferIndex + 1] = rawBuffer[(rawBufferIndex - j) + 1];
                rawBuffer[rawBufferIndex + 2] = rawBuffer[(rawBufferIndex - j) + 2];
                vertexCount++;
                rawBufferIndex += 8;
            }
        }

        if (hasTexture)
        {
            rawBuffer[rawBufferIndex + 3] = Float.floatToRawIntBits((float)textureU);
            rawBuffer[rawBufferIndex + 4] = Float.floatToRawIntBits((float)textureV);
        }

        if (hasBrightness)
        {
            rawBuffer[rawBufferIndex + 7] = brightness;
        }

        if (hasColor)
        {
            rawBuffer[rawBufferIndex + 5] = color;
        }

        if (hasNormals)
        {
            rawBuffer[rawBufferIndex + 6] = normal;
        }

        rawBuffer[rawBufferIndex + 0] = Float.floatToRawIntBits((float)(par1 + xOffset));
        rawBuffer[rawBufferIndex + 1] = Float.floatToRawIntBits((float)(par3 + yOffset));
        rawBuffer[rawBufferIndex + 2] = Float.floatToRawIntBits((float)(par5 + zOffset));
        rawBufferIndex += 8;
        vertexCount++;
    }

    /**
     * Sets the color to the given opaque value (stored as byte values packed in an integer).
     */
    public void setColorOpaque_I(int par1)
    {
        int i = par1 >> 16 & 0xff;
        int j = par1 >> 8 & 0xff;
        int k = par1 & 0xff;
        setColorOpaque(i, j, k);
    }

    /**
     * Sets the color to the given color (packed as bytes in integer) and alpha values.
     */
    public void setColorRGBA_I(int par1, int par2)
    {
        int i = par1 >> 16 & 0xff;
        int j = par1 >> 8 & 0xff;
        int k = par1 & 0xff;
        setColorRGBA(i, j, k, par2);
    }

    /**
     * Disables colors for the current draw call.
     */
    public void disableColor()
    {
        isColorDisabled = true;
    }

    /**
     * Sets the normal for the current draw call.
     */
    public void setNormal(float par1, float par2, float par3)
    {
        hasNormals = true;
        byte byte0 = (byte)(int)(par1 * 127F);
        byte byte1 = (byte)(int)(par2 * 127F);
        byte byte2 = (byte)(int)(par3 * 127F);
        normal = byte0 & 0xff | (byte1 & 0xff) << 8 | (byte2 & 0xff) << 16;
    }

    /**
     * Sets the translation for all vertices in the current draw call.
     */
    public void setTranslation(double par1, double par3, double par5)
    {
        xOffset = par1;
        yOffset = par3;
        zOffset = par5;
    }

    /**
     * Offsets the translation for all vertices in the current draw call.
     */
    public void addTranslation(float par1, float par2, float par3)
    {
        xOffset += par1;
        yOffset += par2;
        zOffset += par3;
    }

    static
    {
        nativeBufferSize = 0x200000;
        trivertsInBuffer = (nativeBufferSize / 48) * 6;
        tryVBO = false;
        byteBuffer = GLAllocation.createDirectByteBuffer(nativeBufferSize * 4);
        intBuffer = byteBuffer.asIntBuffer();
        floatBuffer = byteBuffer.asFloatBuffer();
        shortBuffer = byteBuffer.asShortBuffer();
        instance = new Tessellator();
        useVBO = false;
        vboCount = 10;
        instance.defaultTexture = true;
        useVBO = tryVBO && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;

        if (useVBO)
        {
            vertexBuffers = GLAllocation.createDirectIntBuffer(vboCount);
            ARBVertexBufferObject.glGenBuffersARB(vertexBuffers);
        }
    }
}
