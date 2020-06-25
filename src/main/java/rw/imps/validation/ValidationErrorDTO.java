package rw.imps.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
    String ErrorType;

    public String getErrorType() {
        return ErrorType;
    }

    public void setErrorType(String errorType) {
        ErrorType = errorType;
    }

    public ValidationErrorDTO() {

    }

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

}
