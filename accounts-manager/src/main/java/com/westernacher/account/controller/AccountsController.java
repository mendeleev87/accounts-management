package com.westernacher.account.controller;

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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Account> saveAccount(@RequestBody Account a) {
		Account result = null;
		try {
			result = accountService.save(a);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteAccount(@PathVariable Integer id) throws InvalidIdException {
		accountService.delete(id);
	}
}
