package com.swamy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.swamy.entity.Employee;

@TestPropertySource("classpath:db.properties")
@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setup() throws Exception {
		employee = new Employee(1, "swamy", 56000.00, "Hyd");
	}

	@Order(1)
	@Test
	public void whenEmployeeObj_thenReturnSavedEmployee() {

		Employee savedEmployee = employeeRepository.save(employee);

		Employee empObj = employeeRepository.findById(savedEmployee.getEmployeeId()).get();

		assertThat(empObj.getEmployeeName()).isEqualTo(employee.getEmployeeName());
	}

	@Order(2)
	@Test
	public void whenFindAll_thenReturnEmployeesList() {

		employeeRepository.save(employee);

		List<Employee> employees = employeeRepository.findAll();

		assertThat(employees.size()).isGreaterThan(0);
	}

	@Order(3)
	@Test
	public void whenFindById_ThenReturnEmployee() {

		Employee empObj = employeeRepository.save(employee);

		Employee employeeObject = employeeRepository.findById(empObj.getEmployeeId()).get();

		assertEquals(employeeObject.getEmployeeName(), employee.getEmployeeName());
	}

	@Order(4)
	@Test
	public void whenDeleteById_thenReturnNothing() {

		Employee employeeObj = employeeRepository.save(employee);

		employeeRepository.deleteById(employeeObj.getEmployeeId());

		Optional<Employee> optEmp = employeeRepository.findById(employeeObj.getEmployeeId());
		assertThat(optEmp).isEmpty();

//		List<Employee> empsList = employeeRepository.findAll();
//		assertThat(empsList).isEmpty();
	}

	@Order(5)
	@Test
	public void whenEmployeeObject_thenReturnUpdatedEmployee() {

		Employee savedEmployee = employeeRepository.save(employee);

		Employee employeeObj = employeeRepository.findById(savedEmployee.getEmployeeId()).get();

		employeeObj.setEmployeeName("TARAK");

		Employee updatedEmployee = employeeRepository.save(employeeObj);

		assertThat(updatedEmployee.getEmployeeName()).isEqualTo("TARAK");
	}
}
