package com.cpms.exception_handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cpms.dto.ErrorResponse;
import com.cpms.dto.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//	@ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
//    @ExceptionHandler(BadCredentialsException.class)
//    public void handleConflict() {
//        // Nothing to do
//    }
	
	@ExceptionHandler(ResourceAlreadyExists.class)					// 409
	public ResponseEntity<ErrorResponse> resourceAlreadyExists(ResourceAlreadyExists ex) {
		String details = ex.getMessage();
		ErrorResponse exception = new ErrorResponse("Conflict", details);
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);  
	}

	@ExceptionHandler(CustomException.class)								//400
	public ResponseEntity<String> customException(CustomException ex) {
		String details = ex.getMessage();
		ErrorResponse exception = new ErrorResponse("Bad Request", details);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	//@ExceptionHandler(UnauthorizedException.class)						//401
	 @ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> unauthorizedException(BadCredentialsException ex) {
		String details = ex.getMessage();
		ErrorResponse exception = new ErrorResponse("Unauthorized", details);

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception);
	}


	/**
	 * @param ex - If User is not found then this method handles the raised Exception
	 * @param request
	 * @return - Returns Response Entity with error message along with Status code set to 404 ie., Not Found to FrontEnd Angular.
	 */
	@ExceptionHandler(RecordNotFoundException.class)					//404
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException(RecordNotFoundException ex,
			WebRequest request) {
		String details = ex.getMessage();
		ErrorResponse exception = new ErrorResponse("RecordNotFound", details);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
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
		int httpStatus = status.value();
		List<String> exceptions = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldErr -> fieldErr.getDefaultMessage()).collect(Collectors.toList());
		body.put("Exceptions", exceptions);
		return ResponseEntity.status(httpStatus).body(body);
		//return new ResponseEntity<Object>(body, headers, status);
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


	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), status);
	}
	
//	@ExceptionHandler(AsyncUncaughtExceptionHandler.class)
//	public final ResponseEntity<?> handleAsyncExceptionHandler(AsyncUncaughtExceptionHandler ex, WebRequest request){
//		return new ResponseEntity<>(new ResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	
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
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	

}
