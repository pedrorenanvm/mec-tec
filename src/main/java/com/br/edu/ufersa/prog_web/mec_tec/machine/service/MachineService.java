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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class MachineService{

    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository, ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }
    @Transactional(readOnly = true)
    public ReturnMachineDTO findByID(UUID id){
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new MachineNotFound("Machine not found"));
        return modelMapper.map(machine, ReturnMachineDTO.class);
    }
    @Transactional(readOnly = true)
    public Page<ReturnMachineDTO> findAll(String customerName, String brand, String model, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Machine> pageResult;

        if(customerName != null && !customerName.isBlank()) {
            pageResult = machineRepository.findByCustomerName(customerName, pageable);
        } else if (brand != null && !brand.isBlank()) {
            pageResult = machineRepository.findByBrand(brand, pageable);
        } else if (model != null && !model.isBlank()) {
            pageResult = machineRepository.findByModel(model, pageable);
        } else {
            pageResult = machineRepository.findAll(pageable);
        }

        return pageResult.map(machine -> modelMapper.map(machine, ReturnMachineDTO.class));
    }

    @Transactional
    public ReturnMachineDTO create(CreateMachineDTO dto){
        //verificar se o cliente existe
        Customer existingCustomer = customerRepository.findById(dto.getCustomerID())
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + dto.getCustomerID() + " not found"));

        Category category = Category.valueOf(dto.getCategory().toUpperCase());

        Machine newMachine = new Machine();
        newMachine.setModel(dto.getModel());
        newMachine.setBrand(dto.getBrand());
        newMachine.setDescription(dto.getDescription());
        newMachine.setCategory(category);
        newMachine.setCustomer(existingCustomer);

        Machine savedMachine = machineRepository.save(newMachine);

        return modelMapper.map(savedMachine, ReturnMachineDTO.class);
    }
    @Transactional
    public ReturnMachineDTO update(UpdateMachineDTO dto){
        Machine existingMachine = machineRepository.findById(dto.getId())
                .orElseThrow(() -> new MachineNotFound("Machine not found with ID " + dto.getId()));

        Customer customer = customerRepository.findById(dto.getCustomerID())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID " + dto.getCustomerID()));

        Category category = Category.valueOf(dto.getCategory().toUpperCase());

        existingMachine.setModel(dto.getModel());
        existingMachine.setBrand(dto.getBrand());
        existingMachine.setDescription(dto.getDescription());
        existingMachine.setCategory(category);
        existingMachine.setCustomer(customer);

        Machine savedMachine = machineRepository.save(existingMachine);
        return modelMapper.map(savedMachine, ReturnMachineDTO.class);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id){
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new MachineNotFound("Machine not found with ID " + id));
        machineRepository.delete(machine);
    }

}
