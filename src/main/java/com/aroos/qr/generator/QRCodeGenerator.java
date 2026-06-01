package com.aroos.qr.generator;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.services.IRemainderBits;
import com.aroos.qr.generator.common.services.RemainderBits;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.ec.IErrorCorrectionEncoder;
import com.aroos.qr.generator.encoding.Capacities;
import com.aroos.qr.generator.encoding.EncodingMode;
import com.aroos.qr.generator.encoding.IQREncoding;
import com.aroos.qr.generator.modules.DataBitModules;
import com.aroos.qr.generator.modules.IDataBitModules;
import com.aroos.qr.generator.modules.IQRCode;
import com.aroos.qr.generator.modules.masking.IQRCodeMasking;
import com.aroos.qr.generator.modules.masking.IQRCodeMasking.MaskResult;
import com.aroos.qr.generator.modules.meta.FormatInfoModules;
import com.aroos.qr.generator.modules.meta.IFormatInfoModules;
import com.aroos.qr.generator.modules.meta.IVersionInfoModules;
import com.aroos.qr.generator.modules.meta.VersionInfoModules;
import com.aroos.qr.generator.png.PNGWriter;

/**
 * The {@link QRCodeGenerator} class implements the top level driver for 
 * generating a QR code. It implements a single method which takes in a string
 * to be encoded and outputs a fully constructed QR code with the string
 * encoded.
 */
public final class QRCodeGenerator implements IQRCodeGenerator
{
    private final IRemainderBits remainderBits;

    public QRCodeGenerator()
    {
        this.remainderBits = new RemainderBits();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IQRCode generate(final String content, final ErrorCorrectionLevel level)
    {
        // Initial configuration
        final EncodingMode mode = EncodingMode.factory().analyze(content);
        final int version = Capacities.getSmallestVersion(content.length(), mode, level);
        final QRConfiguration config = new QRConfiguration(version, level, mode);

        return this.generateWithConfig(content, config);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public IQRCode generate(final String content, final ErrorCorrectionLevel level, final int version)
    {
        // Initial configuration
        final EncodingMode mode = EncodingMode.factory().analyze(content);
        final QRConfiguration config = new QRConfiguration(version, level, mode);

        return this.generateWithConfig(content, config);
    }

    private IQRCode generateWithConfig(final String content, final QRConfiguration config)
    {
        // Encoding step
        final IQREncoding encoding = IQREncoding.factory().provide(config);
        final IBitStream data = encoding.encode(content);

        // Error correction/data construction step
        final IErrorCorrectionEncoder ecEncoder = IErrorCorrectionEncoder.factory().provide();
        final IBitStream errorCorrectedData = ecEncoder.encode(data, config);
        final IBitStream withRemainder = this.remainderBits.addRemainderBits(errorCorrectedData, config);

        // QR code generation
        final IQRCode code = IQRCode.factory().provide(config);

        PNGWriter.writeImage(code.toPNG(10), "only_patterns.png");

        final IDataBitModules dataBits = new DataBitModules(code);

        dataBits.accept(withRemainder);

        // Masking
        final IQRCodeMasking masking = IQRCodeMasking.instance();
        final MaskResult maskResult = masking.mask(code);
        final IQRCode masked = maskResult.masked();

        // Format/version
        final IFormatInfoModules formatModules = new FormatInfoModules(masked);
        final IVersionInfoModules versionModules = new VersionInfoModules(masked);

        formatModules.applyFormatInfo(maskResult.maskUsed(), config);
        versionModules.applyVersionInfo(config);

        return masked;
    }
}