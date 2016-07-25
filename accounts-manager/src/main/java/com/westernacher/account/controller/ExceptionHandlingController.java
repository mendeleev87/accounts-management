package com.westernacher.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.westernacher.account.dto.ErrorMessageDTO;
import com.westernacher.account.exception.InvalidIdException;

@ControllerAdvice
public class ExceptionHandlingController {
	
	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<ErrorMessageDTO> invalidId(InvalidIdException ex) {
		ErrorMessageDTO dto = new ErrorMessageDTO(HttpStatus.GONE, "Invalid id.", ex.getMessage());
		return new ResponseEntity<ErrorMessageDTO> (dto, HttpStatus.GONE);
	}

}
