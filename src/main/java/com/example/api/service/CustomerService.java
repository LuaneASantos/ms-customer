package com.example.api.service;

import com.example.api.dto.AddressDto;
import com.example.api.dto.CustomerDto;
import com.example.api.entity.Address;
import com.example.api.entity.Customer;
import com.example.api.exceptionHandler.CustomerNotFoundException;
import com.example.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

	private final CustomerRepository repository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	@Autowired
	public CustomerService(CustomerRepository repository, EntityManager entityManager) {
		this.repository = repository;
		this.entityManager = entityManager;
	}

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Customer findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
	}

	public PageImpl<CustomerDto> findCustomersByFilters(String name, String email, String gender, String city, String state, Pageable pageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> root = cq.from(Customer.class);
		Join<Customer, Address> joinAddress = root.join("addresses", JoinType.LEFT);

		List<Predicate> predicates = new ArrayList<>();

		if (name != null && !name.isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
		}

		if (email != null && !email.isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
		}

		if (gender != null) {
			predicates.add(cb.equal(root.get("gender"), gender));
		}

		if (city != null && !city.isEmpty()) {
			predicates.add(cb.like(cb.lower(joinAddress.get("city")), "%" + city.toLowerCase() + "%"));
		}

		if (state != null && !state.isEmpty()) {
			predicates.add(cb.like(cb.lower(joinAddress.get("state")), "%" + state.toLowerCase() + "%"));
		}

		cq.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Customer> query = entityManager.createQuery(cq);
		// Adiciona a configuração de paginação
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<CustomerDto> customers = query.getResultList().stream().map(c-> {

			List<AddressDto> addressDtos = new ArrayList<>();

			c.getAddresses().stream().forEach(a -> {
				addressDtos.add(new AddressDto(a.getId(), a.getStreet(), a.getNumber(), a.getCity(), a.getCountry(), a.getZipCode(), a.getComplement(), a.getNeighborhood(), a.getState()));
			});

			return new CustomerDto(c.getId(), c.getName(), c.getEmail(), c.getGender(), addressDtos);

		}).collect(Collectors.toList());

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cb.count(countQuery.from(Customer.class)));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		return new PageImpl<>(customers, pageable, totalCount);

	}

	public Customer updateCustomer(Long customerId, Customer updatedCustomer) {

		Customer customer = repository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		customer.setName(updatedCustomer.getName());
		customer.setEmail(updatedCustomer.getEmail());
		customer.setGender(updatedCustomer.getGender());

		return repository.save(customer);
	}

	public Customer createCustomer(Customer customer) {
		return repository.save(customer);
	}

	public void deleteCustomer(Long customerId) {
		if (repository.existsById(customerId)) {
			repository.deleteById(customerId);
		} else {
			throw new CustomerNotFoundException("Customer not found");
		}
	}
}
