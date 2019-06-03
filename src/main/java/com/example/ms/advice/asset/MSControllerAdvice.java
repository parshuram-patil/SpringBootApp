package com.example.ms.advice.asset;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.ms.exception.asset.SearchAssetExceptionException;
import com.example.psp.constants.Constants;
import com.example.psp.constants.PropertyValues;
import com.example.psp.exception.DevErrorResponse;
import com.example.psp.exception.ErrorResponse;

@ControllerAdvice
public class MSControllerAdvice extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
		ErrorResponse errors = getErrorResponse(ex);

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
		ErrorResponse errors = getErrorResponse(ex);

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SearchAssetExceptionException.class)
	protected ResponseEntity<ErrorResponse> handleAssetException(SearchAssetExceptionException ex) {
		ErrorResponse errors = getErrorResponse(ex);

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}


	private ErrorResponse getErrorResponse(Exception ex) {
		ErrorResponse errors = null;

		if (PropertyValues.ACTIVE_PROFILE.equals(Constants.PROFILE_DEV)) {
			errors = new DevErrorResponse();
			((DevErrorResponse) errors).setException(ex);

		} else {
			errors = new ErrorResponse();
		}

		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());

		return errors;
	}

}