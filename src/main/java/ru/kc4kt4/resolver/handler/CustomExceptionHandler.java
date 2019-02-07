package ru.kc4kt4.resolver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kc4kt4.resolver.exception.ApplicationNotFoundException;
import ru.kc4kt4.resolver.exception.InternalServerError;
import ru.kc4kt4.resolver.exception.MapperServiceException;
import ru.kc4kt4.resolver.exception.ProcessApplicationException;
import ru.kc4kt4.resolver.response.ErrorResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * The type Custom exception handler.
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class CustomExceptionHandler {

    private final static String MISSING_MESSAGE = "Ooops! Message is missing!";

    /**
     * Handle internal server error error response.
     *
     * @param e the exception
     * @param httpResponse the http response
     * @return the error response
     */
    @ExceptionHandler(InternalServerError.class)
    @ResponseBody
    public ErrorResponse handleInternalServerError(InternalServerError e,
                                         HttpServletResponse httpResponse) {
        log.error(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String errorMessage = e.getMessage() == null ? MISSING_MESSAGE : e.getMessage();
        return new ErrorResponse(errorMessage);
    }

    /**
     * Handle mapper service exception error response.
     *
     * @param e the exception
     * @param httpResponse the http response
     * @return the error response
     */
    @ExceptionHandler({MapperServiceException.class, ProcessApplicationException.class})
    @ResponseBody
    public ErrorResponse handleMapperServiceException(MapperServiceException e,
                                         HttpServletResponse httpResponse) {
        log.error(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        String errorMessage = e.getMessage() == null ? MISSING_MESSAGE : e.getMessage();
        return new ErrorResponse(errorMessage);
    }

    /**
     * Handle application not found exception error response.
     *
     * @param e the exception
     * @param httpResponse the http response
     * @return the error response
     */
    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleApplicationNotFoundException(ApplicationNotFoundException e,
                                         HttpServletResponse httpResponse) {
        log.error(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        String errorMessage = e.getMessage() == null ? MISSING_MESSAGE : e.getMessage();
        return new ErrorResponse(errorMessage);
    }

    /**
     * Handle exception error response.
     *
     * @param e the exception
     * @param httpResponse the http response
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception e,
                                         HttpServletResponse httpResponse) {
        log.error(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        String errorMessage = e.getMessage() == null ? MISSING_MESSAGE : e.getMessage();
        return new ErrorResponse(errorMessage);
    }
}
