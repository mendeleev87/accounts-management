package com.westernacher.account.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
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
		return new ResponseEntity<ErrorMessageDTO>(dto, HttpStatus.GONE);
	}

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<ErrorMessageDTO> constraintViolation(ConstraintViolationException ex) {
		ErrorMessageDTO dto = new ErrorMessageDTO(HttpStatus.BAD_REQUEST, "Invalid field data.", ex.getMessage());
		return new ResponseEntity<ErrorMessageDTO>(dto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<ErrorMessageDTO> transactionFailed(TransactionSystemException ex) {
		ErrorMessageDTO dto = new ErrorMessageDTO(HttpStatus.BAD_REQUEST,
				"Transaction failed because of invalid field data. ", ex.getMessage());
		return new ResponseEntity<ErrorMessageDTO>(dto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<ErrorMessageDTO> catchException(Exception ex) {
		ErrorMessageDTO dto = new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR, "A server error occured. ",
				ex.getMessage());
		return new ResponseEntity<ErrorMessageDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
