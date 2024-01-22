package com.example.api.controller;

import com.example.api.dto.CustomerDto;
import com.example.api.entity.Customer;
import com.example.api.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@Api(tags = "Customer")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	@ApiOperation("Find customer by id.")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@GetMapping
	@ApiOperation("Find customer by filters.")
	public PageImpl<CustomerDto> findByFilter(@RequestParam(name = "name", required = false) String name,
											  @RequestParam(name = "email", required = false) String email,
											  @RequestParam(name = "gender", required = false) String gender,
											  @RequestParam(name = "city", required = false) String city,
											  @RequestParam(name = "state", required = false) String state,
											  @RequestParam(defaultValue = "0") int page,
											  @RequestParam(defaultValue = "10") int size,
											  @RequestParam(defaultValue = "id,asc") String sort) {
		return service.findCustomersByFilters(name, email, gender, city, state, PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort))));
	}

	@PutMapping("/{customerId}")
	@ApiOperation("Update customer by id.")
	public Customer updateCustomer(
			@PathVariable Long customerId,
			@RequestBody Customer updatedCustomer) {
		return service.updateCustomer(customerId, updatedCustomer);
	}

	@PostMapping
	@ApiOperation("Create customer.")
	public Customer createCustomer(@RequestBody Customer customer) {
		return service.createCustomer(customer);
	}

	@ApiOperation("Delete customer by id.")
	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable Long customerId) {
		service.deleteCustomer(customerId);
	}
}
