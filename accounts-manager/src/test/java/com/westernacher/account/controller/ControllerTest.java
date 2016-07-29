package com.westernacher.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.westernacher.account.configuration.DatabaseConfiguration;
import com.westernacher.account.configuration.WebConfiguration;
import com.westernacher.account.configuration.test.TestConfiguration;
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
	private UpdateFieldDTO<Long> validDateDTO = new UpdateFieldDTO<>();
	private UpdateFieldDTO<Long> invalidDateDTO = new UpdateFieldDTO<>();

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

		validDateDTO.setNewValue(DATE_FORMAT.parse(VALID_DATE_STR).getTime());
		invalidDateDTO.setNewValue(DATE_FORMAT.parse(INVALID_DATE_STR).getTime());
	}

	@Test
	public void testDeleteWithValidId() throws Exception {
		mockMvc.perform(delete("/rest/accounts/delete/" + VALID_ID).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateFirstNameValid() throws Exception {
		String content = gson.toJson(validNameDTO);
		mockMvc.perform(patch("/rest/accounts/update/" + VALID_ID + "/firstName").content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void testUpdateLastNameValid() throws Exception {
		String content = gson.toJson(validNameDTO);
		mockMvc.perform(patch("/rest/accounts/update/" + VALID_ID + "/lastName").content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void testUpdateEmailValid() throws Exception {
		String content = gson.toJson(new UpdateFieldDTO<String>(VALID_EMAIL));
		mockMvc.perform(patch("/rest/accounts/update/" + VALID_ID + "/email").content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void testUpdateDoBValid() throws Exception {
		String content = gson.toJson(validDateDTO);
		mockMvc.perform(patch("/rest/accounts/update/" + VALID_ID + "/dateOfBirth").content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	
	@Test
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete("/rest/accounts/delete/" + INVALID_ID).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone()).andReturn();
	}

}
