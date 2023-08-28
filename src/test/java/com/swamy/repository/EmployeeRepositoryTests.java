package com.swamy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swamy.entity.Employee;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setup() throws Exception {
		employee = new Employee(1, "swamy", 56000.00, "Hyd");
	}

	@Test
	public void testSaveEmployee() {

		Employee savedEmployee = employeeRepository.save(employee);

		assertThat(savedEmployee.getEmployeeId()).isGreaterThan(0);
	}

	@Test
	public void testGetAllEmployees() {

		employeeRepository.save(employee);
		List<Employee> list = employeeRepository.findAll();
		assertThat(list.size()).isGreaterThan(0);
	}

	@Test
	public void testGetEmployeeById() {

		Employee savedEmployee = employeeRepository.save(employee);
		Employee existingEmpObj = employeeRepository.findById(savedEmployee.getEmployeeId()).get();
		assertThat(existingEmpObj.getEmployeeId()).isGreaterThan(0);
	}

	@Test
	public void testUpdateEmployee() {

		Employee savedEmployee = employeeRepository.save(employee);
		Employee existingEmpObj = employeeRepository.findById(savedEmployee.getEmployeeId()).get();
		existingEmpObj.setEmployeeName("SWAMY.M");
		existingEmpObj.setEmployeeSalary(78000.00);
		existingEmpObj.setEmployeeAddress("KNR");
		Employee updatedEmployee = employeeRepository.save(existingEmpObj);
		assertEquals("SWAMY.M", updatedEmployee.getEmployeeName());
	}

	@Test
	public void testDeleteEmployeeById() {

		Employee savedEmployee = employeeRepository.save(employee);
		Employee existingEmpObj = employeeRepository.findById(savedEmployee.getEmployeeId()).get();
		employeeRepository.deleteById(existingEmpObj.getEmployeeId());
		Optional<Employee> optEmp = employeeRepository.findById(existingEmpObj.getEmployeeId());
		assertThat(optEmp).isEmpty();
	}

}
