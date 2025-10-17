package com.br.edu.ufersa.prog_web.mec_tec.task.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // Genérico
    @Query("SELECT t FROM Task t " +
            "JOIN FETCH t.machine m " +
            "WHERE LOWER(t.name) LIKE %:searchTerm% " +
            "OR LOWER(m.model) LIKE %:searchTerm% " +
            "OR LOWER(t.description) LIKE %:searchTerm%")
    Page<Task> findAllPaginate(@Param("searchTerm") String searchTerm, Pageable pageable);

    // nome do cliente
    @Query("SELECT t FROM Task t JOIN t.machine m JOIN m.customer c " +
            "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :customerName, '%'))")
    Page<Task> findByCustomerName(@Param("customerName") String customerName, Pageable pageable);

    // modelo da maquina
    @Query("SELECT t FROM Task t JOIN t.machine m " +
            "WHERE LOWER(m.model) LIKE LOWER(CONCAT('%', :machineModel, '%'))")
    Page<Task> findByMachineModel(@Param("machineModel") String machineModel, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    Page<Task> findByStatus(@Param("status") TaskStatus status, Pageable pageable);
}
