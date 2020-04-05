package com.ibm.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ibm.app.ws.model.response.ErrorDetails;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage().toString(), errorMessageDescription);
		return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { PhonebookEntryNotFoundException.class })
	public ResponseEntity<Object> PhonebookEntryNotFoundException(PhonebookEntryNotFoundException ex,
			WebRequest request) {
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage().toString(), errorMessageDescription);
		return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage().toString(),
				ex.getBindingResult().toString());
		return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
