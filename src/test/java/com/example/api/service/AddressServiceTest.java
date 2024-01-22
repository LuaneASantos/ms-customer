package com.example.api.service;

import com.example.api.entity.Customer;
import com.example.api.repository.CustomerRepository;
import com.example.api.request.AddressRequest;
import com.example.api.response.ViaCEPResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ViaCEPService viaCEPService;

    @InjectMocks
    private AddressService addressServiceMock;

    @Test
    public void testInsertAddress() {

        Long customerId = 1L;
        String cep = "89036002";
        AddressRequest request = createMockAddressRequest();
        Customer customer = createMockCustomer();
        ViaCEPResponse viaCEPResponse = createMockViaCEPResponse();

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(viaCEPService.getInformationCEP(cep)).thenReturn(viaCEPResponse);

        addressServiceMock.insertAddress(customerId, cep, request);

        verify(customerRepository).findById(customerId);
        verify(viaCEPService).getInformationCEP(cep);

    }

    @Test
    public void testAddAddressToCustomer() {

        Customer customer = createMockCustomer();
        ViaCEPResponse viaCEPResponse = createMockViaCEPResponse();
        AddressRequest request = createMockAddressRequest();

        addressServiceMock.addAddressToCustomer(customer, viaCEPResponse, request);

        verify(customerRepository).save(customer);
    }

    private Customer createMockCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Maria");
        customer.setEmail("maria@gmail.com");
        customer.setGender("F");

        return customer;
    }

    private AddressRequest createMockAddressRequest() {
        AddressRequest request = new AddressRequest();
        request.setNumber(12L);
        request.setComplement("Apt 4");

        return request;
    }

    private ViaCEPResponse createMockViaCEPResponse() {
        ViaCEPResponse viaCEPResponse = new ViaCEPResponse();
        viaCEPResponse.setLogradouro("Rua Teste");
        viaCEPResponse.setBairro("Bairro Teste");
        viaCEPResponse.setLocalidade("Cidade Teste");
        viaCEPResponse.setUf("SC");
        viaCEPResponse.setCep("89036010");

        return viaCEPResponse;
    }

}
