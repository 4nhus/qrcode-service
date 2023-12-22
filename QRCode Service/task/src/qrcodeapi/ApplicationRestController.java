package qrcodeapi;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qrcodeapi.exceptions.*;

@RestController
public class ApplicationRestController {
    @GetMapping("/api/health")
    public ResponseEntity<String> getHealth() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/qrcode")
    public ResponseEntity<Object> getQRCode(@RequestParam String contents, @RequestParam(defaultValue = "250") Integer size, @RequestParam(defaultValue = "png") String type, @RequestParam(defaultValue = "L") String correction) {
        if (contents == null || contents.trim().isEmpty()) {
            throw new InvalidContentsException();
        }

        if (size < 150 || size > 350) {
            throw new InvalidImageSizeException();
        }

        switch (correction) {
            case "L":
            case "M":
            case "Q":
            case "H":
                break;
            default:
                throw new InvalidCorrectionException();
        }

        switch (type) {
            case "png":
            case "jpeg":
            case "gif":
                break;
            default:
                throw new InvalidImageTypeException();
        }

        return ResponseEntity.ok().contentType(MediaType.valueOf("image/" + type)).body(QREncoder.createFromContentsString(contents, size, ErrorCorrectionLevel.valueOf(correction)));
    }
}
