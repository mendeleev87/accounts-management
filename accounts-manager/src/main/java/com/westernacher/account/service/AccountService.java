package com.westernacher.account.service;

import java.util.List;

import com.westernacher.account.exception.InvalidIdException;
import com.westernacher.account.model.Account;

public interface AccountService {
	
	Account save(Account a);
	
	List<Account> findAll();
	
	void delete(Integer id) throws InvalidIdException;

}
