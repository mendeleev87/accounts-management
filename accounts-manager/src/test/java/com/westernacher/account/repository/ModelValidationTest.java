package com.westernacher.account.repository;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;

import com.westernacher.account.configuration.DatabaseConfiguration;
import com.westernacher.account.configuration.test.TestConfiguration;
import com.westernacher.account.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseConfiguration.class, TestConfiguration.class })
public class ModelValidationTest {

	@Autowired
	private AccountsRepository crud;
	
	public static final int TEST_ID = 1;

	public static final String VALID_NAME = "Hans";

	public static final String VALID_NAME_TWO_PARTS = "Hans Peter";

	public static final String VALID_NAME_HYPHEN = "Hans-Peter";

	public static final String VALID_NAME_THREE_PARTS = "Hans Peter Oliver";

	public static final String VALID_NAME_HYPHEN_SPACES = "Hans - Peter";

	public static final String INVALID_NAME = "Hans123@_";
	
	public static final String VALID_EMAIL = "Hans123@gmail.com";
	
	public static final String INVALID_EMAIL = "Hans123";
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final String VALID_DATE_STR = "20/06/1980";
	
	public static final String INVALID_DATE_STR = "20/06/2020";

	@Test
	public void testValidFirstNamesUpdate() {
		updateFirstNameWithFixture(VALID_NAME);
		updateFirstNameWithFixture(VALID_NAME_TWO_PARTS);
		updateFirstNameWithFixture(VALID_NAME_HYPHEN);
		updateFirstNameWithFixture(VALID_NAME_THREE_PARTS);
		updateFirstNameWithFixture(VALID_NAME_HYPHEN_SPACES);
	}
	
	private void updateFirstNameWithFixture(String fixture) {
		crud.updateFirstName(TEST_ID, fixture);
		Account a = crud.getOne(TEST_ID);
		String actual = a.getFirstName();
		assertEquals(fixture, actual);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testInvalidFirstNameUpdate() {
		crud.updateFirstName(TEST_ID, INVALID_NAME);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidFirstNameUpdateNull() {
		crud.updateFirstName(TEST_ID, null);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidFirstNameUpdateEmpty() {
		crud.updateFirstName(TEST_ID, "");
	}
	
	@Test
	public void testValidLastNamesUpdate() {
		updateLastNameWithFixture(VALID_NAME);
		updateLastNameWithFixture(VALID_NAME_TWO_PARTS);
		updateLastNameWithFixture(VALID_NAME_HYPHEN);
		updateLastNameWithFixture(VALID_NAME_THREE_PARTS);
		updateLastNameWithFixture(VALID_NAME_HYPHEN_SPACES);
	}
	
	private void updateLastNameWithFixture(String fixture) {
		crud.updateLastName(TEST_ID, fixture);
		Account a = crud.getOne(TEST_ID);
		String actual = a.getLastName();
		assertEquals(fixture, actual);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testInvalidLastNameUpdate() {
		crud.updateLastName(TEST_ID, INVALID_NAME);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidLastNameUpdateNull() {
		crud.updateLastName(TEST_ID, null);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidLastNameUpdateEmpty() {
		crud.updateLastName(TEST_ID, "");
	}
	
	@Test
	public void testValidEmailUpdate() {
		crud.updateEmail(TEST_ID, VALID_EMAIL);
		
		Account a = crud.getOne(TEST_ID);
		String actual = a.getEmail();
		assertEquals(VALID_EMAIL, actual);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidEmailUpdate() {
		crud.updateEmail(TEST_ID, INVALID_EMAIL);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidEmailUpdateNull() {
		crud.updateEmail(TEST_ID, null);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidEmailUpdateEmpty() {
		crud.updateEmail(TEST_ID, "");
	}
	
	@Test
	public void testValidDoBUpdate() throws ParseException {
		Date validDate = DATE_FORMAT.parse(VALID_DATE_STR);
		crud.updateDateOfBirth(TEST_ID, validDate);
		
		Account a = crud.getOne(TEST_ID);
		Date actual = a.getDateOfBirth();
		assertEquals(validDate, actual);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidDoBUpdate() throws ParseException {
		Date invalidDate = DATE_FORMAT.parse(INVALID_DATE_STR);
		crud.updateDateOfBirth(TEST_ID, invalidDate);
	}
	
	@Test
	public void testUpdateAccountWithValidFields() throws ParseException {
		Account validAccount = saveAccountWithFixtures(VALID_NAME_HYPHEN, VALID_NAME, VALID_EMAIL, VALID_DATE_STR);
		Account dbAccount = crud.getOne(TEST_ID);
		
		assertEquals(validAccount.getFirstName(), dbAccount.getFirstName());
		assertEquals(validAccount.getLastName(), dbAccount.getLastName());
		assertEquals(validAccount.getEmail(), dbAccount.getEmail());
		assertEquals(validAccount.getDateOfBirth(), dbAccount.getDateOfBirth());
	}

	private Account saveAccountWithFixtures(String firstName, String lastName, String email, String dateStr) throws ParseException {
		Date date = DATE_FORMAT.parse(dateStr);
		Account account = new Account(firstName, lastName, email, date);
		account.setId(TEST_ID);
		crud.save(account);
		return account;
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidFirstName() throws ParseException {
		saveAccountWithFixtures(INVALID_NAME, VALID_NAME, VALID_EMAIL, VALID_DATE_STR);
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidFirstNameNull() throws ParseException {
		saveAccountWithFixtures(null, VALID_NAME, VALID_EMAIL, VALID_DATE_STR);
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidFirstNameEmpty() throws ParseException {
		saveAccountWithFixtures("", VALID_NAME, VALID_EMAIL, VALID_DATE_STR);
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidLastName() throws ParseException {
		saveAccountWithFixtures(VALID_NAME, INVALID_NAME, VALID_EMAIL, VALID_DATE_STR);
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidLastNameNull() throws ParseException {
		saveAccountWithFixtures(VALID_NAME, null, VALID_EMAIL, VALID_DATE_STR);
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidLastNameEmpty() throws ParseException {
		saveAccountWithFixtures(VALID_NAME, "", VALID_EMAIL, VALID_DATE_STR);
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidEmail() throws ParseException {
		saveAccountWithFixtures(VALID_NAME, VALID_NAME, INVALID_EMAIL, VALID_DATE_STR);
	}
	
	@Test(expected = TransactionSystemException.class)
	public void testUpdateAccountWithInvalidDoB() throws ParseException {
		saveAccountWithFixtures(VALID_NAME, VALID_NAME, VALID_EMAIL, INVALID_DATE_STR);
	}
	

}
