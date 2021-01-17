package com.cpms.exception_handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cpms.dto.ErrorResponse;
import com.cpms.dto.ResponseDTO;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * @param ex - If User is not found then this method handles the raised Exception
	 * @param request
	 * @return - Returns Response Entity with error message along with Status code set to 404 ie., Not Found to FrontEnd Angular.
	 */
	@ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(RecordNotFoundException ex,
                                                WebRequest request) {
        String details = ex.getMessage();
        ErrorResponse error = new ErrorResponse("RecordNotFound", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	/**
	 *	This Method is called for @Valid since it throws
	 *	MethodArgumentNotValidException
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		List<String> errs = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldErr -> fieldErr.getDefaultMessage()).collect(Collectors.toList());
		body.put("errors", errs);
		return new ResponseEntity<Object>(body, headers, status);
	}

	/**
	 * @param ex - On occurance of Constraint Violation this method is called. 
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method handles all sort of exception raised if a specific exception is not handled by above methods.
	 * @param ex - This Exception Object stores raised exception details.
	 * @param request
	 * @return - Returns Response Entity with error message along with Status code set to 500 ie., Internal Server Error to FrontEnd Angular.
	 */
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        String details = ex.getMessage();
        ErrorResponse error = new ErrorResponse("ServerError", details);
        //return ResponseEntity.status(HttpStatus.OK).body("Login Success!!");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
//	@Override
//	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		
//		return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), status);
//	}
	

}