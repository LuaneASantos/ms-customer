package com.example.api.service;

import com.example.api.entity.Address;
import com.example.api.entity.Customer;
import com.example.api.exceptionHandler.CustomerNotFoundException;
import com.example.api.repository.CustomerRepository;
import com.example.api.request.AddressRequest;
import com.example.api.response.ViaCEPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private ViaCEPService viaCEPService;

    public void insertAddress(Long customerId, String cep, AddressRequest request) {

        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));

        ViaCEPResponse viaCEPResponse = this.viaCEPService.getInformationCEP(cep);

        this.addAddressToCustomer(customer, viaCEPResponse, request);

    }

    public void addAddressToCustomer(Customer customer, ViaCEPResponse viaCEPResponse, AddressRequest request) {

        Address address = new Address();
        address.setNumber(request.getNumber());
        address.setComplement(request.getComplement());
        address.setCity(viaCEPResponse.getLocalidade());
        address.setCountry("Brasil");
        address.setStreet(viaCEPResponse.getLogradouro());
        address.setZipCode(viaCEPResponse.getCep());
        address.setNeighborhood(viaCEPResponse.getBairro());
        address.setState(viaCEPResponse.getUf());

        customer.getAddresses().add(address);

        customerRepository.save(customer);
    }

}
