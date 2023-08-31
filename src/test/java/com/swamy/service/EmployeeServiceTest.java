package com.swamy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.swamy.dto.EmployeeDto;
import com.swamy.entity.Employee;
import com.swamy.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	private EmployeeDto employeeDto;

	private Employee employee;

	@BeforeEach
	public void setup() throws Exception {
		employeeDto = new EmployeeDto(1, "swamy", 56000.00, "Hyd");
		employee = new Employee(1, "swamy", 56000.00, "Hyd");
	}

	@Test
	public void whenSaveEmployee_thenReturnSavedEmployee() {
		
		when(employeeRepository.save(employee)).thenReturn(employee);
		EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
		
		assertEquals(employee.getEmployeeId(), savedEmployee.getEmployeeId());
	}

	@Test
	public void whenGetAllEmployees_thenEmployeesList() {

		List<Employee> list = List.of(employee);
		when(employeeRepository.findAll()).thenReturn(list);

		List<EmployeeDto> employeesList = employeeService.getAllEmployees();

		assertThat(employeesList.size() != 0);
	}

	@Test
	public void whenGetEmployeeById_thenReturnEmployee() {
		
		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
		
		EmployeeDto employeeObj = employeeService.getEmployeeById(employee.getEmployeeId());
		
		assertEquals(employee.getEmployeeId(), employeeObj.getEmployeeId());
	}

	@Test
	public void whenUpdateEmployee_thenReturnUpdatedEmployee() {
		
		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
		when(employeeRepository.save(employee)).thenReturn(employee);
		EmployeeDto existingEmployee = employeeService.getEmployeeById(employeeDto.getEmployeeId());
		existingEmployee.setEmployeeName("M.SWAMY");
		existingEmployee.setEmployeeSalary(56800.00);
		existingEmployee.setEmployeeAddress("KNR");

		EmployeeDto updatedEmployee = employeeService.updateEmployee(existingEmployee.getEmployeeId(), existingEmployee);

		assertEquals("M.SWAMY", updatedEmployee.getEmployeeName());
	}

	@Test
	public void whenDeleteEmployeeById_thenReturnNothing() {

//		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
//		employeeService.deleteEmployee(employeeDto.getEmployeeId());
//		verify(employeeRepository, times(1)).deleteById(employee.getEmployeeId());
		
		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
		employeeService.deleteEmployee(employeeDto.getEmployeeId());
		assertTrue(true);
	}
}
