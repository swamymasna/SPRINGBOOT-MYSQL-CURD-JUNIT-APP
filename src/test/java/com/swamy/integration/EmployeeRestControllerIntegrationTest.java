package com.swamy.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swamy.dto.EmployeeDto;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.EmployeeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeRestControllerIntegrationTest {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeRepository employeeRepository;

	private EmployeeDto employeeDto;

	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		employeeRepository.deleteAll();
		employeeDto = EmployeeDto.builder().employeeName("Swamy").employeeSalary(56000.00).employeeAddress("Hyd")
				.build();
		mapper = new ObjectMapper();
	}

	@Test
	public void testSaveEmployee() throws Exception {

		// MockHttpServletRequestBuilder requestBuilder =
		// MockMvcRequestBuilders.post("/api/employees")
		// .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDto));
		// int status =
		// mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();
		// assertEquals(201, status);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDto));

		// mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isCreated());

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.employeeName", is(employeeDto.getEmployeeName())))
				.andExpect(jsonPath("$.employeeSalary", is(employeeDto.getEmployeeSalary())))
				.andExpect(jsonPath("$.employeeAddress", is(employeeDto.getEmployeeAddress())));
	}

	@Test
	public void testGetAllEmployees() throws Exception {

		EmployeeDto savedEmp1 = employeeService.saveEmployee(employeeDto);
		EmployeeDto savedEmp2 = employeeService.saveEmployee(employeeDto);
		List<EmployeeDto> employeesList = List.of(savedEmp1, savedEmp2);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees");

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(employeesList.size())));
	}

	@Test
	public void testGetEmployeeById() throws Exception {

		EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/employees/" + savedEmployee.getEmployeeId());

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeName", is(employeeDto.getEmployeeName())))
				.andExpect(jsonPath("$.employeeSalary", is(employeeDto.getEmployeeSalary())))
				.andExpect(jsonPath("$.employeeAddress", is(employeeDto.getEmployeeAddress())));

	}

	@Test
	public void testUpdateEmployee() throws Exception {

		EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);

		EmployeeDto updatedEmployee = new EmployeeDto();
		updatedEmployee.setEmployeeName("King");
		updatedEmployee.setEmployeeSalary(78000.00);
		updatedEmployee.setEmployeeAddress("Knr");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/employees/" + savedEmployee.getEmployeeId()).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(updatedEmployee));

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeName", is(updatedEmployee.getEmployeeName())))
				.andExpect(jsonPath("$.employeeSalary", is(updatedEmployee.getEmployeeSalary())))
				.andExpect(jsonPath("$.employeeAddress", is(updatedEmployee.getEmployeeAddress())));
	}

	@Test
	public void testDeleteEmployeeById() throws Exception {

		EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/employees/" + savedEmployee.getEmployeeId());

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
	}
}
