package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.swamy.dto.EmployeeDto;
import com.swamy.entity.Employee;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	private ModelMapper modelMapper;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

		Employee employee = modelMapper.map(employeeDto, Employee.class);

		Employee savedEmployee = employeeRepository.save(employee);

		return modelMapper.map(savedEmployee, EmployeeDto.class);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		List<Employee> employees = employeeRepository.findAll();

		return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDto getEmployeeById(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Employee Not Found With Id : %s ", employeeId)));

		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employeeDto) {

		Employee existingEmployee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Employee Not Found With Id : %s ", employeeId)));

		existingEmployee.setEmployeeName(employeeDto.getEmployeeName());
		existingEmployee.setEmployeeSalary(employeeDto.getEmployeeSalary());
		existingEmployee.setEmployeeAddress(employeeDto.getEmployeeAddress());

		Employee savedEmployee = employeeRepository.save(existingEmployee);

		return modelMapper.map(savedEmployee, EmployeeDto.class);
	}

	@Override
	public void deleteEmployee(Integer employeeId) {

		employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Employee Not Found With Id : %s ", employeeId)));

		employeeRepository.deleteById(employeeId);
	}

}
