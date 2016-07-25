package com.westernacher.account.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.westernacher.account.configuration.DatabaseConfiguration;
import com.westernacher.account.configuration.test.TestConfiguration;
import com.westernacher.account.model.Account;
import com.westernacher.account.repository.AccountsRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, TestConfiguration.class})
public class ServiceTest {
	
	private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	private Account bill;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountsRepository rep;
	
	@Before
	public void init() throws ParseException {
		bill = new Account();
		bill.setFirstName("Bill");
		bill.setLastName("Gates");
		bill.setEmail("bill.gates@microsoft.com");
		bill.setDateOfBirth(format.parse("28/10/1955"));
	}
	
	@Test
	public void testSaveAccount() {
		Account dbBill = accountService.save(bill);
		assertEquals(2, accountService.findAll().size());
		rep.delete(dbBill);
		assertEquals(1, rep.count());
	}
	
	@Test
	public void testDeleteAccount() {
		rep.save(bill);
		assertEquals(2, rep.count());
		accountService.delete(bill.getId());
		assertEquals(1, accountService.findAll().size());
	}
	
	@Test
	public void testFindAllAccounts() throws ParseException {
		List<Account> accounts = accountService.findAll();
		assertEquals(1, accounts.size());
		Account account = accounts.get(0);
		assertEquals("John", account.getFirstName());
		assertEquals("Smith", account.getLastName());
		assertEquals("john.smith@gmail.com", account.getEmail());
		assertEquals(format.parse("20/03/1980"), account.getDateOfBirth());
	}

}
