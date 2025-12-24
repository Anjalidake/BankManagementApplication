package com.example.bankapp.exception;

import java.time.LocalDateTime;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.TransactionSystemException;

@ControllerAdvice
public class GlobalExceptionHandler {
    //handle specific exceptions    
	@ExceptionHandler(AccountException.class)
	  public ResponseEntity<ErrorDetails> handleAccountException(AccountException accountException, WebRequest webRequest){
		  
		  ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
				                                      accountException.getMessage(),
				                                      webRequest.getDescription(false),
				                                      "ACCOUNT_NOT_FOUND"  
				                                      );
		  return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);			  
		  
	  }
	
	

	@ExceptionHandler({OptimisticLockingFailureException.class, TransactionSystemException.class})
	public ResponseEntity<ErrorDetails> handleOptimisticLocking(Exception ex, WebRequest request) {
	    String message = "The account was updated by another transaction. Please try again.";

	    // If TransactionSystemException wraps OptimisticLockingFailureException, unwrap it
	    if (ex instanceof TransactionSystemException tse && tse.getRootCause() instanceof OptimisticLockingFailureException) {
	        message = "The account was updated by another transaction. Please try again.";
	    }

	    ErrorDetails errorDetails = new ErrorDetails(
	            LocalDateTime.now(),
	            message,
	            request.getDescription(false),
	            "CONCURRENT_UPDATE"
	    );

	    return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}

    
	// handle generic exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGenericException(Exception exception,WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				                                     exception.getMessage(), 
				                                     webRequest.getDescription(false),
				                                     "INTERNAL_SERVER_ERROR"
				                                     );
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
