package com.swamy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {

	private Integer employeeId;

	private String employeeName;

	private Double employeeSalary;

	private String employeeAddress;
}
