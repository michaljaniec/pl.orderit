package pl.od.orderit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class RequiredFieldsAreNotAvailable extends Throwable {
    public RequiredFieldsAreNotAvailable(String message){super(message);}
}
