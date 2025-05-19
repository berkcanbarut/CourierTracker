package migros.one.logistic.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadArgumentException extends RuntimeException{
    private Integer statusCode;
    private String error;

    public BadArgumentException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.error = BadArgumentException.class.toString();
    }
}
