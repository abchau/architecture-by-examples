package com.abchau.archexamples.subscribe.inputadapter.web;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class SubscribeControllerIT {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should_render_subscribe_page() throws Exception {
		// given
		RequestBuilder requestBuilder = get("/subscribe");

		// when
		this.mockMvc.perform(requestBuilder)
			.andDo(print())
			// then
			.andExpect(status().isOk())
			// and
			.andExpect(content().string(containsString("Subscribe")));
	}

	@Test
	public void should_render_input_is_empty() throws Exception {
		// given
		RequestBuilder requestBuilder = post("/subscribe")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("email", "");

		// when
		this.mockMvc.perform(requestBuilder)
			.andDo(print())
			// then
			.andExpect(status().is4xxClientError())
			// and
			.andExpect(content().string(containsString("Input is empty")));
	}

	@Test
	public void should_render_invalid_email_format() throws Exception {
		// given
		RequestBuilder requestBuilder = post("/subscribe")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("email", "abcabc");

		// when
		this.mockMvc.perform(requestBuilder)
			.andDo(print())
			// then
			.andExpect(status().is4xxClientError())
			// and
			.andExpect(content().string(containsString("Invalid email format")));
	}

	@Test
	public void should_render_subscribed() throws Exception {
		// given
		RequestBuilder requestBuilder = post("/subscribe")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("email", "abc@abc.com");

		// when
		this.mockMvc.perform(requestBuilder)
			.andDo(print())
			// then
			.andExpect(status().isCreated())
			// and
			.andExpect(content().string(containsString("subscribed")));
	}

	@Test
	public void should_render_already_exist() throws Exception {
		// given
		this.mockMvc.perform(post("/subscribe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", "abc@abc.com"));

		// and
		RequestBuilder requestBuilder = post("/subscribe")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("email", "abc@abc.com");
	
		// when
		this.mockMvc.perform(requestBuilder)
			.andDo(print())
			// then
			.andExpect(status().is4xxClientError())
			// and
			.andExpect(content().string(containsString("already exist")));
	}

}
