package com.br.edu.ufersa.prog_web.mec_tec.task.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t FROM Task t " +
            "JOIN FETCH t.machine m " +
            "WHERE LOWER(t.name) LIKE %:searchTerm% " +
            "OR LOWER(m.model) LIKE %:searchTerm% " +
            "OR LOWER(t.description) LIKE %:searchTerm%")
    Page<Task> findAllPaginate(@Param("searchTerm") String searchTerm, Pageable pageable);

}
