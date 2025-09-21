package com.dailin.movie_app.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dailin.movie_app.dto.response.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// combina @ResponseBody y @ControllerAdvice, sirve para poner todos los metodos que esten anotados con @ExceptionHandler
@RestControllerAdvice 
public class GlobalExceptionHandler {

    // Metodo para manejar expeciones (todas en una)
    @ExceptionHandler({
        Exception.class,
        ObjectNotFoundException.class,
        InvalidPasswordException.class,
        MethodArgumentTypeMismatchException.class, // el tipo de dato del argumento no coinciden 
        MethodArgumentNotValidException.class, // el arg no es valido (según jakarta validation)
        HttpRequestMethodNotSupportedException.class, // 405: method http no permitido
        HttpMediaTypeNotSupportedException.class, // formato no soportado (solo JSON)
        HttpMessageNotReadableException.class, // formato de datos ilegible 
    }) 
    public ResponseEntity<ApiError> handleAllExceptions(
        Exception exception, 
        HttpServletRequest request,
        HttpServletResponse response
    ) {

        ZoneId zoneId = ZoneId.of("America/Bogota");
        LocalDateTime timestamp = LocalDateTime.now(zoneId); // la hora actual

        if(exception instanceof ObjectNotFoundException objectNotFoundException) {
            return this.handleObjectNotFoundException(objectNotFoundException, request, response, timestamp);
        }
        else if(exception instanceof InvalidPasswordException invalidPasswordException) {
            return this.handleInvalidPasswordException(invalidPasswordException, request, response, timestamp);
        }
        else if(exception instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){
            return this.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, request, response, timestamp);
        }
        else if(exception instanceof MethodArgumentNotValidException methodArgumentNotValidException){
            return this.handleMethodArgumentNotValidException(methodArgumentNotValidException, request, response, timestamp);
        }
        else if(exception instanceof HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException){
            return this.handleHttpRequestMethodNotSupportedException(httpRequestMethodNotSupportedException, request, response, timestamp);
        }
        else if(exception instanceof HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException){
            return this.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, request, response, timestamp);
        }
        else if(exception instanceof HttpMessageNotReadableException httpMessageNotReadableException){
            return this.handleHttpMessageNotReadableException(httpMessageNotReadableException, request, response, timestamp);
        }
        else {
            return this.handleException(exception, request, response, timestamp);
        }
    }

    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp) {
        
        int httpStatus = HttpStatus.BAD_REQUEST.value(); 

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "Opps! Error reading the HTTP message body. "+
            "Make sure the request is corretly formatted and contains valid data.", 
            httpMessageNotReadableException.getMessage(), 
            timestamp,
            null
        );

        return ResponseEntity.status(httpStatus).body(apiError);     
    }

    private ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp) {
        
        int httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(); 

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "Unsupported Media Type: The server is unable to process the requested entity in the format provided in the request. "+
            "Supported media types are: " + httpMediaTypeNotSupportedException.getSupportedMediaTypes() + 
            " and you send: " + httpMediaTypeNotSupportedException.getContentType(), 
            httpMediaTypeNotSupportedException.getMessage(), 
            timestamp,
            null
        );

        return ResponseEntity.status(httpStatus).body(apiError);     
    }

    private ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp) {

        int httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value(); 

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "Oops! Method not allowed. Check the HTTP method of your request.", 
            httpRequestMethodNotSupportedException.getMessage(), 
            timestamp,
            null
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp) {
        
        int httpStatus = HttpStatus.BAD_REQUEST.value();
        
        // Lista con los detalles de errores de validacion (jakarta validation)
        List<ObjectError> errors = methodArgumentNotValidException.getAllErrors();
        List<String> details = errors.stream().map( error -> {

            // se comprueba que cada error sea una instancia FielErrors (campos definidos en la entidad)
            if(error instanceof FieldError fieldError) { 
                // devuelve el nombre del atributo/campo que no se validó y su mensaje
               return fieldError.getField() + ": "+ fieldError.getDefaultMessage(); 
            }

            return error.getDefaultMessage();

        }).toList(); // para que los convierta en una lista (de detalles de type String)

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "The request contains invalid or incomplete parameters. " +
            "Please verify and provide the required information before trying again.", 
            methodArgumentNotValidException.getMessage(), 
            timestamp,
            details
        );
        
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp) {
            
        int httpStatus = HttpStatus.BAD_REQUEST.value(); 
        Object valueRejected = methodArgumentTypeMismatchException.getValue(); // devuelve el valor del argumento - lo saca como obj porque desconoce su type
        String propertyName = methodArgumentTypeMismatchException.getName(); // devuelve el nombre de la propiedad (args)

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "Invalid Request: The provided value '"+valueRejected+"' does not have expected data type for the "+propertyName, 
            methodArgumentTypeMismatchException.getMessage(), 
            timestamp,
            null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request, HttpServletResponse response,
            LocalDateTime timestamp) {
    
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(); 

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "Opps! Something went wrong on our server. Please try again later.", 
            exception.getMessage(), 
            timestamp,
            null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException,
            HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        
        int httpStatus = HttpStatus.BAD_REQUEST.value(); 

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "Invalid Password: The provided password does not meet the required criteria, "+invalidPasswordException.getErrorDescription(), 
            invalidPasswordException.getMessage(), 
            timestamp,
            null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleObjectNotFoundException(Exception objectNotFoundException,
            HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
       
        int httpStatus = HttpStatus.NOT_FOUND.value(); 

        ApiError apiError =  new ApiError(
            httpStatus,
            request.getRequestURL().toString(), 
            request.getMethod(), 
            "I'm sorry, the requested information could not be found. " + 
                "Please check the URL or try another search ", 
            objectNotFoundException.getMessage(), 
            timestamp,
            null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
