package com.westernacher.account.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.westernacher.account.configuration.AccountsConfiguration;
import com.westernacher.account.configuration.DatabaseConfiguration;
import com.westernacher.account.configuration.WebAppInitializer;
import com.westernacher.account.configuration.WebConfiguration;
import com.westernacher.account.configuration.test.TestConfiguration;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfiguration.class, DatabaseConfiguration.class, TestConfiguration.class })
@WebAppConfiguration
public class RestApiTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void init() {
		this.mockMvc = webAppContextSetup(wac).build();
	}

	@Test
	public void testDeleteMethod() throws Exception {
		MvcResult result = mockMvc.perform(delete("/rest/accounts/delete/20").accept(MediaType.APPLICATION_JSON))
				//.andExpect(status().isGone())
				.andReturn();
		System.out.println(result.getResponse().getStatus());
		System.out.println(result.getResponse().getContentAsString());

	}

}
