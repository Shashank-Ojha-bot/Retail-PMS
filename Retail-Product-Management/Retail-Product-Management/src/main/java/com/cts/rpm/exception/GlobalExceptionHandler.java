package com.cts.rpm.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ExceptionDetails> handleAuthorizationException(AuthorizationException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.FORBIDDEN, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.FORBIDDEN);
	}

	/*@ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionDetails> handleFeignStatusException(FeignException ex, HttpServletResponse response) {
		log.error("handles by handleFeignStatusException");
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }*/
	

	@ExceptionHandler(FeignException.class)
    public ModelAndView handleFeignStatusException(FeignException ex, HttpServletResponse response) {
		log.error("handles by handleFeignStatusException");
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
		//log.error("Error   "+ex.getMessage());
	
		ModelAndView model= new ModelAndView("error-page");
		if(ex.getMessage().contains("out of") || ex.getMessage().contains("not found"))
		{
		String str=ex.getMessage().split("message")[1].split(":")[1];
		String str1=str.substring(1,str.length()-3);
		System.out.println(str1);
		//model= new ModelAndView("error-page");
		model.addObject("errorMsg", str1);
		model.addObject("error", exceptionDetail.getStatus());
		}
		else {
			model.addObject("errorMsg", ex.getMessage());
			model.addObject("error", exceptionDetail.getStatus());
		}
		return model;
		//return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }
	

	
	@ExceptionHandler(ProductOutOfStockException.class)
	public ResponseEntity<ExceptionDetails> handleProductOutOfStockException(ProductOutOfStockException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleAuthorizationException(ProductNotFoundException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidRatingException.class)
	public ResponseEntity<ExceptionDetails> handleInvalidRatingException(InvalidRatingException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request){
		log.error("handles by");
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
}
