package com.westernacher.account.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.westernacher.account.configuration.DatabaseConfiguration;
import com.westernacher.account.configuration.test.TestConfiguration;
import com.westernacher.account.exception.InvalidIdException;
import com.westernacher.account.model.Account;
import com.westernacher.account.repository.AccountsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseConfiguration.class, TestConfiguration.class })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ServiceTest {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	public static final int VALID_ID = 1;

	public static final int INVALID_ID = 20;

	public static final String BILL = "Bill";

	public static final String JOHN = "John";

	private Account john;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountsRepository rep;

	@Before
	public void init() throws ParseException {
		john = rep.getOne(VALID_ID);
	}

	@Test
	public void testUpdateWithValidId() throws InvalidIdException {
		john.setFirstName(BILL);
		accountService.update(VALID_ID, john);
		Account bill = rep.getOne(VALID_ID);
		assertEquals(BILL, bill.getFirstName());
	}

	@Test(expected = InvalidIdException.class)
	public void testUpdateWithInvalidId() throws InvalidIdException {
		accountService.update(INVALID_ID, john);
	}

	@Test
	public void testDeleteWithValidId() throws InvalidIdException {
		accountService.delete(VALID_ID);
		assertEquals(0, accountService.findAll().size());
	}

	@Test(expected = InvalidIdException.class)
	public void testDeleteWithInvalidId() throws InvalidIdException {
		accountService.delete(INVALID_ID);
	}
	
}
