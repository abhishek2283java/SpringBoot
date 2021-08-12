package org.abhishek.om.owner.exception;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
//https://www.youtube.com/watch?v=nUgwmveBg-0&list=PLJ-lrQx0LAbzYtfjdJX9SpIT6io-y_CaJ&index=14
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Method not supported");
		ApiErrorModel error = new ApiErrorModel(message, details, status, LocalDateTime.now());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//String message = ex.getFieldError("ownername").getDefaultMessage();
		String message = "Validation failed";
		
		List<String> details = new ArrayList<>();
		details.add("Method Argument Not valid");
		details.add(ex.getBindingResult().toString());
		ApiErrorModel error = new ApiErrorModel(message, details, status, LocalDateTime.now());
		
		return ResponseEntity.status(status).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Media not supported");
		ApiErrorModel error = new ApiErrorModel(message, details, status, LocalDateTime.now());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(OwnerNotFoundException.class)
	public ResponseEntity<Object> handleOwnerNotFoundException(OwnerNotFoundException ex) {
		
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Owner not found");
		ApiErrorModel error = new ApiErrorModel(message, details, HttpStatus.NOT_FOUND, LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	//All other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAll(Exception ex) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Inside handleAll");
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Other Exception");
		details.add(ex.getLocalizedMessage());
		ApiErrorModel error = new ApiErrorModel(message, details, HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
		
		return ResponseEntity.badRequest().body(error);
	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//		
//		String message = ex.getMessage();
//		List<String> details = new ArrayList<>();
//		details.add("Bad Request");
//		ApiErrorModel error = new ApiErrorModel(message, details, HttpStatus.BAD_REQUEST, LocalDateTime.now());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//	}
	
}
