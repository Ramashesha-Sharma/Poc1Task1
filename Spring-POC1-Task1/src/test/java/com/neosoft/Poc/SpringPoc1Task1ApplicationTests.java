package com.neosoft.Poc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;


import com.neosoft.Poc.Controller.UserController;
import com.neosoft.Poc.Repository.UserRepository;
import com.neosoft.Poc.Service.UserService;
import com.neosoft.Poc.model.User;

@WebMvcTest(UserController.class)
class SpringPoc1Task1ApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;
	// ObjectMapper provides functionality for reading and writing JSON,
	// either to and from basic POJOs

	@MockBean
	UserService userService;

	@MockBean
	UserRepository usersRepository;
	
	@MockBean
	UserController uc;

	User RECORD_1 = new User(10L, "Abc", "Def",  new Date(1999 - 8 - 11), new Date(2021 - 12 - 10),
			   "9876543210","577126",false);
	User RECORD_2 = new User(20L, "Ghi", "Jkl",  new Date(1999 - 10 - 21), new Date(2021 - 11 - 20),
			   "9876543211","577127",false);
	User RECORD_3 = new User(30L, "Mno", "Pqr",  new Date(1999 - 12 - 31), new Date(2021 - 10 - 30),
			   "9876543212","577128",false);

	@Test
	void contextLoads() {
	}

	@Test
	public void getAllRecords_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

		Mockito.when(userService.GetUser()).thenReturn(records);
		// When findAll called then ready with records (No DB calls)
		mockMvc.perform(MockMvcRequestBuilders.get("/User").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()) // 200
				.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[2].fname", is("Mno")));
	}

		
	@Test
	public void deleteUsersById_success() throws Exception {

		Mockito.when(userService.deleteUser(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/User/delete/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


	@Test
	public void getUserByPincode_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		Mockito.when(usersRepository.findByPincode(RECORD_1.getPincode())).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/User/search/pincode/577126").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].pincode", is("577126")));
	}
	@Test
	public void getUsersByFirstName_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		Mockito.when(usersRepository.findByFname(RECORD_1.getFname())).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/User/search/fname/Abc").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].fname", is("Abc")));
	}

	@Test
	public void getUsersBySurname_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		Mockito.when(usersRepository.findBySurname(RECORD_1.getSurname())).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/User/search/surname/Def").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].surname", is("Def")));
	}


	@Test
	public void updateUsersRecord_success() throws Exception {
		User record = User.builder().id(10L).fname("John").surname("Doe")
				.DOB(new Date(1990 - 12 - 11)).joiningDate(new Date(2021 - 8 - 15))
				.mobileNo("9988776655").pincode("577120").deleted(false).build();

		Mockito.when(usersRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));

		Mockito.when(userService.updateById(RECORD_1.getId(),record)).thenReturn(record);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/User/edit/10")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(record));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.fname", is("John")));
	}


}
