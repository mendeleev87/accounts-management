package com.westernacher.account.service;

import java.util.Date;
import java.util.List;

import com.westernacher.account.exception.InvalidIdException;
import com.westernacher.account.model.Account;

public interface AccountService {
	
	Account save(Account a);
	
	List<Account> findAll();
	
	void delete(Integer id) throws InvalidIdException;

	Account update(Integer id, Account existingAccount) throws InvalidIdException;

	void updateFirstName(Integer id, String newValue) throws InvalidIdException;

	void updateLastName(Integer id, String newValue) throws InvalidIdException;

	void updateEmail(Integer id, String newValue) throws InvalidIdException;

	void updateDateOfBirth(Integer id, Date newValue) throws InvalidIdException;

}
