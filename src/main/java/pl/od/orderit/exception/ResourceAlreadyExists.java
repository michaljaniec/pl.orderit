package pl.od.orderit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceAlreadyExists extends Throwable {
    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
