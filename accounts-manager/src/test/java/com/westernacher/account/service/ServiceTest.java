package com.westernacher.account.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.westernacher.account.configuration.DatabaseConfiguration;
import com.westernacher.account.configuration.test.TestConfiguration;
import com.westernacher.account.exception.InvalidIdException;
import com.westernacher.account.model.Account;
import com.westernacher.account.repository.AccountsRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, TestConfiguration.class})
public class ServiceTest {
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final int VALID_ID = 1;
	
	public static final int INVALID_ID = 20;
	
	public static final String BILL = "Bill";
	
	private Account john;
	
	@Mock
	private AccountService accountService;
	
	@Autowired
	private AccountsRepository rep;
	
	@Before
	public void init() throws ParseException {
		MockitoAnnotations.initMocks(this);
		
		john = rep.getOne(VALID_ID);
		john.setId(null);
	}
	
	@Test
	public void testUpdateWithValidId() throws InvalidIdException {
		john.setFirstName(BILL);
		Account bill = accountService.update(VALID_ID, john);
		assertEquals(BILL, bill.getFirstName());
	}
	
	@Test
	public void testDeleteWithValidId() throws InvalidIdException {
		rep.save(john);
		assertEquals(2, rep.count());
		accountService.delete(john.getId());
		assertEquals(1, accountService.findAll().size());
	}
	


}
