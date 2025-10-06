package com.br.edu.ufersa.prog_web.mec_tec.machine.service;


import com.br.edu.ufersa.prog_web.mec_tec.customer.model.entity.Customer;
import com.br.edu.ufersa.prog_web.mec_tec.customer.model.repository.CustomerRepository;
import com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto.CreateMachineDTO;
import com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto.ReturnMachineDTO;
import com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto.UpdateMachineDTO;
import com.br.edu.ufersa.prog_web.mec_tec.machine.exception.MachineNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Category;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Machine;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.repository.MachineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;


@Service
public class MachineService{

    private final MachineRepository repository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customer;

    @Autowired
    public MachineService(MachineRepository machineRepository, ModelMapper modelMapper, CustomerRepository customer) {
        this.repository = machineRepository;
        this.modelMapper = modelMapper;
        this.customer = customer;
    }
    @Transactional(readOnly = true)
    public ReturnMachineDTO findByID(UUID id){
        Machine machine = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Machine not found"));
        return modelMapper.map(machine, ReturnMachineDTO.class);
    }
    @Transactional(readOnly = true)
    public Page<ReturnMachineDTO> findAll(Pageable pageable){
        return repository.findAll(pageable)
                .map( m -> modelMapper.map(m, ReturnMachineDTO.class));
    }

    @Transactional
    public ReturnMachineDTO create(CreateMachineDTO dto){
        //verificar se o cliente existe
        Customer existingCustomer = customer.findById(dto.getCustomerID())
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + dto.getCustomerID() + " not found"));

        Category category = Category.valueOf(dto.getCategory().toUpperCase());

        Machine newMachine = new Machine();
        newMachine.setModel(dto.getModel());
        newMachine.setBrand(dto.getBrand());
        newMachine.setDescription(dto.getDescription());
        newMachine.setCategory(category);
        newMachine.setCustomer(existingCustomer);

        Machine savedMachine = repository.save(newMachine);

        return modelMapper.map(savedMachine, ReturnMachineDTO.class);
    }
    @Transactional
    public ReturnMachineDTO update(UpdateMachineDTO dto){
        Machine newMachine = repository.findById(dto.getId())
                .orElseThrow(() -> new MachineNotFound("Machine not found with ID " + dto.getId()));

        Category category = Category.valueOf(dto.getCategory().toUpperCase());

        newMachine.setModel(dto.getModel());
        newMachine.setBrand(dto.getBrand());
        newMachine.setDescription(dto.getDescription());
        newMachine.setCategory(category);
        newMachine.setCustomer(newMachine.getCustomer());

        Machine savedMachine = repository.save(newMachine);

       //modelMapper.map(machine, dto);
       return modelMapper.map(savedMachine, ReturnMachineDTO.class);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id){
        Machine machine = repository.findById(id)
                .orElseThrow(() -> new MachineNotFound("Machine not found with ID " + id));
        repository.delete(machine);
    }

}
