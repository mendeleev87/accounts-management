package com.westernacher.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.westernacher.account.configuration.DatabaseConfiguration;
import com.westernacher.account.configuration.WebConfiguration;
import com.westernacher.account.configuration.test.TestConfiguration;
import com.westernacher.account.dto.AccountDTO;
import com.westernacher.account.dto.UpdateFieldDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfiguration.class, DatabaseConfiguration.class, TestConfiguration.class })
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerTest {

	public static int VALID_ID = 1;

	public static final int INVALID_ID = 20;

	public static final String VALID_NAME = "Hans";

	public static final String INVALID_NAME = "Hans123@_";

	public static final String VALID_EMAIL = "Hans123@gmail.com";

	public static final String INVALID_EMAIL = "Hans123";

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	public static final String VALID_DATE_STR = "20/06/1980";

	public static final String INVALID_DATE_STR = "20/06/2020";

	private UpdateFieldDTO<String> validNameDTO = new UpdateFieldDTO<>();
	private UpdateFieldDTO<String> invalidNameDTO = new UpdateFieldDTO<>();
	private UpdateFieldDTO<Date> validDateDTO = new UpdateFieldDTO<>();
	private UpdateFieldDTO<Date> invalidDateDTO = new UpdateFieldDTO<>();

	private AccountDTO accountDTO = new AccountDTO();
	
	private Date invalidDate;
	
	private Date validDate;

	@Autowired
	private Gson gson;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void init() throws ParseException {
		this.mockMvc = webAppContextSetup(wac).build();

		validNameDTO.setNewValue(VALID_NAME);
		invalidNameDTO.setNewValue(INVALID_NAME);

		validDate = DATE_FORMAT.parse(VALID_DATE_STR);
		invalidDate = DATE_FORMAT.parse(INVALID_DATE_STR);
		validDateDTO.setNewValue(validDate);
		invalidDateDTO.setNewValue(invalidDate);

		accountDTO.setFirstName(VALID_NAME);
		accountDTO.setLastName(VALID_NAME);
		accountDTO.setEmail(VALID_EMAIL);
		accountDTO.setDateOfBirth(validDate);

	}
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get("/rest/accounts").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testAddValid() throws Exception {
		doPost(accountDTO).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void testUpdateValid() throws Exception {
		doPut(accountDTO, VALID_ID)
		.andExpect(status().isOk())
		.andReturn();
	}

	@Test
	public void testDeleteWithValidId() throws Exception {
		mockMvc.perform(delete("/rest/accounts/delete/" + VALID_ID).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateFirstNameValid() throws Exception {
		String content = gson.toJson(validNameDTO);
		doPatch(content, VALID_ID, "/firstName");
	}

	@Test
	public void testUpdateLastNameValid() throws Exception {
		String content = gson.toJson(validNameDTO);
		doPatch(content, VALID_ID, "/lastName");
	}

	@Test
	public void testUpdateEmailValid() throws Exception {
		String content = gson.toJson(new UpdateFieldDTO<String>(VALID_EMAIL));
		doPatch(content, VALID_ID, "/email");
	}

	@Test
	public void testUpdateDoBValid() throws Exception {
		String content = gson.toJson(validDateDTO);
		doPatch(content, VALID_ID, "/dateOfBirth");
	}

	@Test
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete("/rest/accounts/delete/" + INVALID_ID).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone()).andReturn();
	}

	@Test
	public void testAddWithInvalidFirstName() throws Exception {
		accountDTO.setFirstName(INVALID_NAME);
		doPost(accountDTO).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void testAddWithInvalidLastName() throws Exception {
		accountDTO.setLastName(null);
		doPost(accountDTO).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void testAddWithInvalidEmail() throws Exception {
		accountDTO.setEmail("");
		doPost(accountDTO).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void testUpdateWithInvalidId() throws Exception {
		doPut(accountDTO, INVALID_ID).andExpect(status().isGone()).andReturn();
	}
	
	@Test
	public void testUpdateWithInvalidFirstName() throws Exception {
		accountDTO.setFirstName(INVALID_NAME);
		doPut(accountDTO, VALID_ID).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void testUpdateWithInvalidEmail() throws Exception {
		accountDTO.setEmail(INVALID_EMAIL);
		doPut(accountDTO, VALID_ID).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void testUpdateWithInvalidDoB() throws Exception {
		accountDTO.setDateOfBirth(invalidDate);
		doPut(accountDTO, VALID_ID).andExpect(status().isBadRequest()).andReturn();
	}

	private ResultActions doPost(AccountDTO accountDTO) throws Exception {
		String content = gson.toJson(accountDTO);
		ResultActions actions = mockMvc.perform(post("/rest/accounts/new/").content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		return actions;

	}

	private void doPatch(String content, int id, String path) throws Exception {
		mockMvc.perform(patch("/rest/accounts/update/" + id + path).content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}

	private ResultActions doPut(AccountDTO accountDTO, int id) throws Exception {
		String content = gson.toJson(accountDTO);
		return mockMvc.perform(put("/rest/accounts/update/" + id).content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

}
