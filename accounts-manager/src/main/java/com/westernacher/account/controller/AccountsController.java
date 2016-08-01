package com.westernacher.account.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.westernacher.account.dto.AccountDTO;
import com.westernacher.account.dto.UpdateFieldDTO;
import com.westernacher.account.exception.InvalidIdException;
import com.westernacher.account.model.Account;
import com.westernacher.account.service.AccountService;

@Controller
@RequestMapping("/rest/accounts")
public class AccountsController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Account>> findAll() {
		return new ResponseEntity<List<Account>>(accountService.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Account> addAccount(@RequestBody AccountDTO dto) {
		Account newAccount = new Account(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getDateOfBirth());
		Account result = accountService.save(newAccount);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Account> updateAccount(@PathVariable Integer id, @RequestBody AccountDTO dto)
			throws InvalidIdException {
		Account existingAccount = new Account(dto.getFirstName(), dto.getLastName(), dto.getEmail(),
				dto.getDateOfBirth());
		existingAccount.setId(id);
		Account result = accountService.update(id, existingAccount);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/update/{id}/firstName", method = RequestMethod.PATCH)
	@ResponseBody
	public void updateFirstName(@PathVariable Integer id, @RequestBody UpdateFieldDTO<String> dto)
			throws InvalidIdException {
		accountService.updateFirstName(id, dto.getNewValue());
	}

	@RequestMapping(value = "/update/{id}/lastName", method = RequestMethod.PATCH)
	@ResponseBody
	public void updateLastName(@PathVariable Integer id, @RequestBody UpdateFieldDTO<String> dto)
			throws InvalidIdException {
		accountService.updateLastName(id, dto.getNewValue());
	}

	@RequestMapping(value = "/update/{id}/email", method = RequestMethod.PATCH)
	@ResponseBody
	public void updateEmail(@PathVariable Integer id, @RequestBody UpdateFieldDTO<String> dto)
			throws InvalidIdException {
		accountService.updateEmail(id, dto.getNewValue());
	}

	@RequestMapping(value = "/update/{id}/dateOfBirth", method = RequestMethod.PATCH)
	@ResponseBody
	public void updateDateOfBirth(@PathVariable Integer id, @RequestBody UpdateFieldDTO<Date> dto)
			throws InvalidIdException {
		accountService.updateDateOfBirth(id, dto.getNewValue());
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteAccount(@PathVariable Integer id) throws InvalidIdException {
		accountService.delete(id);
	}
}
