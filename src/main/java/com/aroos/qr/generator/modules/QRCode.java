package com.aroos.qr.generator.modules;

import java.util.ArrayList;
import java.util.List;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.png.PNGImage;
import com.aroos.qr.generator.png.pixels.Grayscale;
import com.aroos.qr.generator.png.pixels.IColor;

/*
 * The {@link QRCode} class implements behavior for a structure representing
 * a graphical QR code. 
 */
final class QRCode implements IQRCode
{
    private final List<List<Module>> modules;
    private final int version;
    private final int size;

    QRCode(final QRConfiguration config)
    {
        this.version = config.version();
        this.size = computeSize(config.version());
        this.modules = new ArrayList<>();
        
        for (int i = 0; i < this.size; i++)
        {
            this.modules.add(new ArrayList<>());

            for (int j = 0; j < this.size; j++)
            {
                this.modules.get(i).add(new Module(false, false));
            }
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // region IQRCode
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize()
    {
        return this.size;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getVersion()
    {
        return this.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getModule(final int x, final int y)
    {
        checkBounds(x, y);

        return this.modules.get(x).get(y).value();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModule(final int x, final int y, final boolean value)
    {
        checkBounds(x, y);

        this.setModule(x, y, new Module(value, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReservedModule(final int x, final int y, final boolean value)
    {
        checkBounds(x, y);
        
        this.setModule(x, y, new Module(value, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReserved(final int x, final int y)
    {
        checkBounds(x, y);
        
        return this.modules.get(x).get(y).isReserved();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PNGImage toPNG(int scaling)
    {
        final PNGImage image = new PNGImage(this.size * scaling, this.size * scaling);

        for (int i = 0; i < this.size; i++)
        {
            for (int j = 0; j < this.size; j++)
            {
                final Module module = this.modules.get(i).get(j);

                for (int k = 0; k < scaling; k++)
                {
                    for (int l = 0; l < scaling; l++)
                    {
                        image.setPixel((i * scaling) + k, (j * scaling) + l, module.toColor());
                    }
                }
            }
        }

        return image;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////

    private void setModule(final int x, final int y, final Module module)
    {
        // If you're setting a reserved module, you can always override a
        // previously reserved module. Otherwise, throw the exception.
        if (this.isReserved(x, y) && !module.isReserved())
        {
            throw reservedModule(x, y);
        }

        this.modules.get(x).set(y, module);
    }

    private void checkBounds(final int x, final int y)
    {
        if (x < 0 || x >= this.size)
        {
            throw new IllegalArgumentException("x coordinate out of bounds: [given: %d, size: %d]".formatted(x));
        }
        
        if (y < 0 || y >= this.size)
        {
            throw new IllegalArgumentException("y coordinate out of bounds: [given: %d, size: %d]".formatted(x));
        }
    }

    private static int computeSize(final int version)
    {
        return (((version - 1) * 4) + 21);
    }

    private static ReservedModuleException reservedModule(final int x, final int y)
    {
        return new ReservedModuleException("The module at [%d, %d] is reserved.".formatted(x, y));
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private types
    ////////////////////////////////////////////////////////////////////////////

    private static record Module(boolean value, boolean isReserved)
    {
        public IColor toColor()
        {
            return value
                ? new Grayscale(0, 255)
                : new Grayscale(255, 255);
        }
    }
}