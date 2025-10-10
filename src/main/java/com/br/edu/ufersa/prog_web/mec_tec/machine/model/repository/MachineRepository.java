package com.br.edu.ufersa.prog_web.mec_tec.machine.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MachineRepository extends JpaRepository<Machine, UUID> {

//    Optional<Machine> findByName(String name);
//    Optional<Machine> findByBrand(String brand);
//    Optional<Machine> findByCustomer(Customer customer);

}
