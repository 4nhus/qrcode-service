package qrcodeapi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class QREncoder {
    private static final QRCodeWriter writer = new QRCodeWriter();
    private static Map<EncodeHintType, ErrorCorrectionLevel> hints;
    public static BufferedImage createFromContentsString(String contents, int size, ErrorCorrectionLevel level) {
        hints = Map.of(EncodeHintType.ERROR_CORRECTION, level);
        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            BufferedImage completelyWhiteQRCode = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = completelyWhiteQRCode.createGraphics();

            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, size, size);

            return completelyWhiteQRCode;
        }
    }
}
