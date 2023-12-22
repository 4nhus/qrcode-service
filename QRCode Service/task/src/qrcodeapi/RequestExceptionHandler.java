package qrcodeapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import qrcodeapi.exceptions.*;

@ControllerAdvice
public class RequestExceptionHandler {
    public record Error(String error) {}

    @ExceptionHandler(InvalidImageSizeException.class)
    public ResponseEntity<Error> handleInvalidImageSize() {
        return ResponseEntity.badRequest().body(new Error("Image size must be between 150 and 350 pixels"));
    }

    @ExceptionHandler(InvalidImageTypeException.class)
    public ResponseEntity<Error> handleInvalidImageType() {
        return ResponseEntity.badRequest().body(new Error("Only png, jpeg and gif image types are supported"));
    }

    @ExceptionHandler(InvalidContentsException.class)
    public ResponseEntity<Error> handleInvalidContents() {
        return ResponseEntity.badRequest().body(new Error("Contents cannot be null or blank"));
    }

    @ExceptionHandler(InvalidCorrectionException.class)
    public ResponseEntity<Error> handleInvalidCorrection() {
        return ResponseEntity.badRequest().body(new Error("Permitted error correction levels are L, M, Q, H"));
    }
}
