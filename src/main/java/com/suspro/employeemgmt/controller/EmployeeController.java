package com.suspro.employeemgmt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.suspro.employeemgmt.exception.ResourceNotFoundException;
import com.suspro.employeemgmt.model.Employee;
import com.suspro.employeemgmt.model.EmployeeDetails;
import com.suspro.employeemgmt.model.Product;
import com.suspro.employeemgmt.repository.EmployeeRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CrossOrigin(origins = "http://localhost:3000")
@RefreshScope
@RestController
@RequestMapping("/employees/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public static final String EMPLOYEE_SERVICE="employeeService";

	@Autowired
	RestTemplate restTemplate;

	// get all employees
	@GetMapping("/")
	@CircuitBreaker(name=EMPLOYEE_SERVICE, fallbackMethod="getAllTheEmployeesWithProducts")
	public List<EmployeeDetails> getAllEmployees(){
		List<Employee> emps = employeeRepository.findAll();
		List<EmployeeDetails> empDetailsList = new ArrayList<EmployeeDetails>(); 
		for(Employee emp : emps) {
			List<Product> products = this.restTemplate.getForObject("http://product-service/products/employees/"+emp.getEmployeeId() , List.class);

			EmployeeDetails empdtls = new EmployeeDetails();
			empdtls.setEmployeeId(emp.getEmployeeId());
			empdtls.setFirstName(emp.getFirstName());
			empdtls.setLastName(emp.getLastName());
			empdtls.setEmailId(emp.getEmailId());
			empdtls.setProducts(products);

			empDetailsList.add(empdtls);
		}
		return empDetailsList;
	}	
	
	public List<EmployeeDetails> getAllTheEmployeesWithProducts(Exception e){
		List<Product> products1 = Arrays.asList(new Product(1,"XYZ","Fruits","1000",1),new Product(2,"ABC","Fruits","1001",1));
		List<Product> products2 = Arrays.asList(new Product(3,"MNO","Meats","2000",2),new Product(4,"OPQ","Vegetables","200",2));
		List<Product> products3 = Arrays.asList(new Product(5,"QRS","Fruits","300",3),new Product(6,"STU","Vegetables","301",3));
		return Stream.of(
                new EmployeeDetails(1, "Venkat", "Sarma", "venkat@gmail.com", products1),
                new EmployeeDetails(2, "Prashant", "Panigrahi", "prashant@gmail.com", products2),
                new EmployeeDetails(3, "Susanta", "Panigrahi", "susanta@gmail.com", products3),
                new EmployeeDetails(4, "Anubhav", "Kumar", "anubhav@gmail.com", products1),
                new EmployeeDetails(5, "abhinav", "Kumar", "abhinav@gmail.com", products2)
        ).collect(Collectors.toList());
	}

	// create employee rest api
	@PostMapping("/")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	// get employee by id rest api

	@GetMapping("/{empid}")
	public ResponseEntity<EmployeeDetails> getEmployeeById(@PathVariable("empid") long empid) {
		Employee employee = employeeRepository.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + empid));

		List<Product> products = this.restTemplate.getForObject("http://product-service/products/employees/"+empid , List.class);


		EmployeeDetails empdtls = new EmployeeDetails();
		empdtls.setEmployeeId(employee.getEmployeeId());
		empdtls.setFirstName(employee.getFirstName());
		empdtls.setLastName(employee.getLastName());
		empdtls.setEmailId(employee.getEmailId());
		empdtls.setProducts(products);

		return ResponseEntity.ok(empdtls);
	}


	// update employee rest api

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());

		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	// delete employee rest api
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}


}
