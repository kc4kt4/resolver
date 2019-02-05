package ru.kc4kt4.resolver.handler;

import com.sun.xml.internal.txw2.IllegalSignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kc4kt4.resolver.exception.ApplicationNotFoundException;
import ru.kc4kt4.resolver.exception.InternalServerError;
import ru.kc4kt4.resolver.exception.MapperServiceException;
import ru.kc4kt4.resolver.response.ErrorResponse;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class CustomExceptionHandler {

    private final static String MISSING_MESSAGE = "Ooops! Message is missing!";

    @ExceptionHandler(InternalServerError.class)
    @ResponseBody
    public ErrorResponse handleInternalServerError(IllegalSignatureException e,
                                         HttpServletResponse httpResponse) {
        log.error(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String errorMessage = e.getMessage() == null ? MISSING_MESSAGE : e.getMessage();
        return new ErrorResponse(errorMessage);
    }

    @ExceptionHandler(MapperServiceException.class)
    @ResponseBody
    public ErrorResponse handleMapperServiceException(IllegalSignatureException e,
                                         HttpServletResponse httpResponse) {
        log.error(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        String errorMessage = e.getMessage() == null ? MISSING_MESSAGE : e.getMessage();
        return new ErrorResponse(errorMessage);
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleApplicationNotFoundException(IllegalSignatureException e,
                                         HttpServletResponse httpResponse) {
        log.error(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        String errorMessage = e.getMessage() == null ? MISSING_MESSAGE : e.getMessage();
        return new ErrorResponse(errorMessage);
    }
}
