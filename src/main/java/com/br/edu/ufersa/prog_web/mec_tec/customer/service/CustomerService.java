package com.br.edu.ufersa.prog_web.mec_tec.customer.service;

import com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto.CreateCustomerDTO;
import com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto.ReturnCustomerDTO;
import com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto.UpdateCustomerDTO;
import com.br.edu.ufersa.prog_web.mec_tec.customer.exception.CustomerAlreadyExistsException;
import com.br.edu.ufersa.prog_web.mec_tec.customer.exception.CustomerNotFoundException;
import com.br.edu.ufersa.prog_web.mec_tec.customer.model.entity.Customer;
import com.br.edu.ufersa.prog_web.mec_tec.customer.model.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerService(CustomerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<ReturnCustomerDTO> findAll() {
        List<Customer> customers = repository.findAll();
        return customers.stream().map(c -> modelMapper.map(c, ReturnCustomerDTO.class)).toList();
    }

    public ReturnCustomerDTO findById(UUID id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
        return modelMapper.map(customer, ReturnCustomerDTO.class);
    }

    public ReturnCustomerDTO create(CreateCustomerDTO dto) {
        if (repository.findByCpf(dto.getCpf()).isPresent()) {
            throw new CustomerAlreadyExistsException("A customer with this cpf already exists.");
        }

        if (repository.findByPhone(dto.getPhone()).isPresent()) {
            throw new CustomerAlreadyExistsException("A customer with this phone already exists.");
        }

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException("A customer with this email already exists.");
        }

        Customer customer = modelMapper.map(dto, Customer.class);

        Customer createCustomer = repository.save(customer);
        return modelMapper.map(createCustomer, ReturnCustomerDTO.class);
    }

    public ReturnCustomerDTO update(UpdateCustomerDTO dto) {
        Customer customer = repository.findById(dto.getId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found."));

        if (!customer.getCpf().equals(dto.getCpf()) && repository.findByCpf(dto.getCpf()).isPresent()) {
            throw new CustomerAlreadyExistsException("A customer with this cpf already exists.");
        }

        if (!customer.getPhone().equals(dto.getPhone()) && repository.findByPhone(dto.getPhone()).isPresent()) {
            throw new CustomerAlreadyExistsException("A customer with this phone already exists.");
        }

        if (!customer.getEmail().equals(dto.getEmail()) && repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException("A customer with this email already exists.");
        }

        modelMapper.map(dto, customer);

        Customer updateCustomer = repository.save(customer);

        return modelMapper.map(updateCustomer, ReturnCustomerDTO.class);
    }

    public void delete(UUID id) {
        Customer customer = new Customer();
        customer.setId(id);

        repository.delete(customer);
    }
}
