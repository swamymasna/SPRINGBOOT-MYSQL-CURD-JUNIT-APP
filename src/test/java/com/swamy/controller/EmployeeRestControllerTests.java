package com.swamy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swamy.dto.EmployeeDto;
import com.swamy.service.EmployeeService;

@WebMvcTest
public class EmployeeRestControllerTests {

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private MockMvc mockMvc;

	private EmployeeDto employeeDto;

	private String empJson;

	@BeforeEach
	public void setup() throws Exception {

		employeeDto = new EmployeeDto(1, "swamy", 56000.00, "Hyd");

		ObjectMapper mapper = new ObjectMapper();
		empJson = mapper.writeValueAsString(employeeDto);
	}

	@Test
	public void whenSaveEmployee_thenReturnSavedEmployee() throws Exception {

//		when(employeeService.saveEmployee(employeeDto)).thenReturn(employeeDto);
		when(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class))).thenReturn(employeeDto);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(empJson);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isCreated());

		//		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		//		int status = response.getStatus();
		//		assertEquals(201, status);




	}

	@Test
	public void whenGetAllEmployees_thenEmployeesList() throws Exception {

		List<EmployeeDto> list = List.of(employeeDto);
		when(employeeService.getAllEmployees()).thenReturn(list);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees");

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());

//		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
//		int status = response.getStatus();
//		assertEquals(200, status);
	}

	@Test
	public void whenGetEmployeeById_thenReturnEmployee() throws Exception {

		when(employeeService.getEmployeeById(employeeDto.getEmployeeId())).thenReturn(employeeDto);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/"+employeeDto.getEmployeeId());

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());

//		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
//		int status = response.getStatus();
//		assertEquals(200, status);
	}

	@Test
	public void whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {

		when(employeeService.updateEmployee(employeeDto.getEmployeeId(), employeeDto)).thenReturn(employeeDto);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/employees/"+employeeDto.getEmployeeId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(empJson);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());

//		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
//		int status = response.getStatus();
//		assertEquals(200, status);
	}

	@Test
	public void whenDeleteEmployeeById_thenReturnNothing() throws Exception {

		when(employeeService.getEmployeeById(employeeDto.getEmployeeId())).thenReturn(employeeDto);

		employeeService.deleteEmployee(employeeDto.getEmployeeId());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/employees/"+employeeDto.getEmployeeId());

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());

//		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
//		int status = response.getStatus();
//		assertEquals(200, status);
	}

}
