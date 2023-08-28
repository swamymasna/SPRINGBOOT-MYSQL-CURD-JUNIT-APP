package com.swamy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.swamy.dto.EmployeeDto;
import com.swamy.entity.Employee;
import com.swamy.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTests {

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

	@Order(1)
	@Test
	public void testSaveEmployee() {
		
		when(employeeRepository.save(employee)).thenReturn(employee);
		EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
		
		assertEquals(employee.getEmployeeId(), savedEmployee.getEmployeeId());
	}

	@Order(2)
	@Test
	public void testGetAllEmployees() {

		List<Employee> list = List.of(employee);
		when(employeeRepository.findAll()).thenReturn(list);

		List<EmployeeDto> employeesList = employeeService.getAllEmployees();

		assertThat(employeesList.size() != 0);
	}

	@Order(3)
	@Test
	public void testGetEmployeeById() {
		
		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
		
		EmployeeDto employeeObj = employeeService.getEmployeeById(employee.getEmployeeId());
		
		assertEquals(employee.getEmployeeId(), employeeObj.getEmployeeId());
	}

	@Order(4)
	@Test
	public void testUpdateEmployee() {
		
		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
		when(employeeRepository.save(employee)).thenReturn(employee);
		EmployeeDto existingEmployee = employeeService.getEmployeeById(employeeDto.getEmployeeId());
		existingEmployee.setEmployeeName("M.SWAMY");
		existingEmployee.setEmployeeSalary(56800.00);
		existingEmployee.setEmployeeAddress("KNR");

		EmployeeDto updatedEmployee = employeeService.updateEmployee(existingEmployee.getEmployeeId(), existingEmployee);

		assertEquals("M.SWAMY", updatedEmployee.getEmployeeName());
	}

	@Order(5)
	@Test
	public void testDeleteEmployee() {

		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
		employeeService.deleteEmployee(employeeDto.getEmployeeId());
		verify(employeeRepository, times(1)).deleteById(employee.getEmployeeId());
	}
}
