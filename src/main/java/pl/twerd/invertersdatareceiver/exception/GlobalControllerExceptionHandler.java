package pl.twerd.invertersdatareceiver.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ExceptionInfo handleIllegalArgument(HttpServletRequest req, IllegalArgumentException exception) {    	    	
		return new ExceptionInfo(req.getRequestURI(), exception.getMessage());
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionInfo handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException exception) {
                          
            BindingResult result = exception.getBindingResult();                
            String errorsMessage = result.getAllErrors().stream()
            		.map((e) -> e.getDefaultMessage())
            		.reduce((s1, s2) -> s1 + "; " + s2).orElse("");
             
            return new ExceptionInfo(req.getRequestURI(), errorsMessage);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ExceptionInfo MethodArgumentTypeMismatchException(HttpServletRequest req, MethodArgumentTypeMismatchException exception) {
                          
            String errorsMessage = exception.getRootCause().getMessage();
             
            return new ExceptionInfo(req.getRequestURI(), errorsMessage);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ExceptionInfo handleException(HttpServletRequest req, Exception exception) {
        exception.printStackTrace();
        return new ExceptionInfo(req.getRequestURI(), "Internal server error");
    }
}