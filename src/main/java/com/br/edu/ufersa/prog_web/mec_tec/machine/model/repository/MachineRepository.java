package com.br.edu.ufersa.prog_web.mec_tec.machine.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MachineRepository extends JpaRepository<Machine, UUID> {

    // busca por modelo
    @Query("SELECT m FROM Machine m WHERE LOWER(m.model) LIKE LOWER(CONCAT('%', :model, '%'))")
    Page<Machine> findByModel(@Param("model") String model, Pageable pageable);

    //busca por brand
    @Query("SELECT m FROM Machine m WHERE LOWER(m.brand) LIKE LOWER(CONCAT('%', :brand, '%'))")
    Page<Machine> findByBrand(@Param("brand") String brand , Pageable pageable);

    // busca por cliente
    @Query("SELECT m FROM Machine m JOIN m.customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :customerName, '%'))")
    Page<Machine> findByCustomerName(@Param("customerName") String customerName, Pageable pageable);

}
