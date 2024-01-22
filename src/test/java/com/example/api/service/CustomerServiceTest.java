package com.example.api.service;

import com.example.api.dto.CustomerDto;
import com.example.api.entity.Customer;
import com.example.api.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testFindCustomersByFilters() {

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Customer> cq = mock(CriteriaQuery.class);
        Root<Customer> root = mock(Root.class);
        TypedQuery<Customer> query = mock(TypedQuery.class);
        Pageable pageable = mock(Pageable.class);
        CriteriaQuery<Long> countQuery = mock(CriteriaQuery.class);

        List<Customer> customerList = createMockCustomers();

        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Customer.class)).thenReturn(cq);
        when(cb.createQuery(Long.class)).thenReturn(countQuery);
        when(cq.from(Customer.class)).thenReturn(root);
        when(root.join("addresses", JoinType.LEFT)).thenReturn(mock(Join.class));
        when(entityManager.createQuery(any(CriteriaQuery.class))).thenReturn(query);
        when(pageable.getOffset()).thenReturn(0L);
        when(pageable.getPageSize()).thenReturn(0);
        when(countQuery.select(cb.count(countQuery.from(Customer.class)))).thenReturn(countQuery);
        when(entityManager.createQuery(countQuery).getSingleResult()).thenReturn(1L);
        when(query.getResultList()).thenReturn(customerList);

        Page<CustomerDto> result = customerService.findCustomersByFilters("Maria", null, "F", null, null, pageable);

        assert !result.isEmpty();
        assert result.getContent().get(0) instanceof CustomerDto;

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getContent().size());

    }

    private List<Customer> createMockCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Maria Julia Silva");
        customer1.setEmail("Maria@gmail.com");
        customer1.setGender("F");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Maria Carolina Santos");
        customer2.setEmail("MariaC@gmail.com");
        customer2.setGender("F");

        return Arrays.asList(customer1, customer2);
    }
    @Test
    public void testUpdateCustomer() {

        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerService customerService = new CustomerService(repository);
        Customer existingCustomer = createMockCustomer();
        Customer updatedCustomer = createMockCustomer();
        updatedCustomer.setName("Updated Name");

        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(existingCustomer));
        when(repository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        assert result != null;
        assert result.getName().equals("Updated Name");

        verify(repository).findById(anyLong());
        verify(repository).save(any(Customer.class));
    }

    @Test
    public void testCreateCustomer() {

        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerService customerService = new CustomerService(repository);
        Customer newCustomer = createMockCustomer();
        newCustomer.setId(null);

        when(repository.save(any(Customer.class))).thenReturn(newCustomer);

        Customer result = customerService.createCustomer(newCustomer);

        assert result != null;

        verify(repository).save(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() {

        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerService customerService = new CustomerService(repository);

        when(repository.existsById(anyLong())).thenReturn(true);

        customerService.deleteCustomer(1L);

        verify(repository).existsById(anyLong());
        verify(repository).deleteById(anyLong());
    }

    private Customer createMockCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Maria");
        customer.setEmail("maria@gmail.com");
        customer.setGender("F");

        return customer;
    }

}
