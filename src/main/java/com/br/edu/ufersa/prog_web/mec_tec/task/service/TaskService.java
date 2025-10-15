package com.br.edu.ufersa.prog_web.mec_tec.task.service;


import com.br.edu.ufersa.prog_web.mec_tec.machine.exception.MachineNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Machine;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.repository.MachineRepository;
import com.br.edu.ufersa.prog_web.mec_tec.piace.exception.PieceNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.piace.model.entity.Piece;
import com.br.edu.ufersa.prog_web.mec_tec.piace.model.repository.PieceRepository;
import com.br.edu.ufersa.prog_web.mec_tec.task.api.dto.CreateTaskDTO;
import com.br.edu.ufersa.prog_web.mec_tec.task.api.dto.ReturnTaskDTO;
import com.br.edu.ufersa.prog_web.mec_tec.task.api.dto.UpdateTaskDTO;
import com.br.edu.ufersa.prog_web.mec_tec.task.exception.TaskNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.TaskStatus;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MachineRepository machineRepository;
    private final PieceRepository pieceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, MachineRepository machineRepository, PieceRepository pieceRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.machineRepository = machineRepository;
        this.pieceRepository = pieceRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public Page<ReturnTaskDTO> findAll(String customerName, String machineModel, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> pageResult;

        if(status != null && !status.isBlank()){
            try {
                TaskStatus statusEnum = TaskStatus.valueOf(status.toUpperCase());
                pageResult = taskRepository.findByStatus(statusEnum, pageable);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid status value provided: '" + status +
                        "'. Accepted values are PENDING, IN_PROGRESS, COMPLETED, CANCELED.");
            }
        } else if ( customerName != null && !customerName.isBlank()){
            pageResult = taskRepository.findByCustomerName(customerName, pageable);
        } else if ( machineModel != null && !machineModel.isBlank()){
            pageResult = taskRepository.findByMachineModel(machineModel, pageable);
        } else {
            pageResult = taskRepository.findAll(pageable);
        }

        return pageResult.map(task -> modelMapper.map(task, ReturnTaskDTO.class));
    }

    @Transactional
    public ReturnTaskDTO findById(UUID id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found"));
        return modelMapper.map(task, ReturnTaskDTO.class);
    }

    @Transactional
    public ReturnTaskDTO create(CreateTaskDTO dto){
        Machine machine = machineRepository.findById(dto.getMachineId())
                .orElseThrow(() -> new MachineNotFound("Machine not found with ID: " + dto.getMachineId()));

        List<Piece> pieces = new ArrayList<>();
        if (dto.getPiecesId() != null && !dto.getPiecesId().isEmpty()) {
            pieces = pieceRepository.findAllById(dto.getPiecesId());

            if(pieces.size() != dto.getPiecesId().size()){
                throw new PieceNotFound("One or more pieces not found");
            }
        }

        Task newTask = new Task();
        newTask.setName(dto.getName());
        newTask.setPrice(dto.getPrice());
        newTask.setDescription(dto.getDescription());
        newTask.setMachine(machine);
        newTask.setPieces(pieces);
        newTask.setStatus(TaskStatus.PENDING); // já deixa setado como pedente

        Task savedTask = taskRepository.save(newTask);
        return modelMapper.map(savedTask, ReturnTaskDTO.class);
    }

    @Transactional
    public ReturnTaskDTO update(UpdateTaskDTO dto){
        Task existingTask = taskRepository.findById(dto.getId())
                .orElseThrow(() -> new TaskNotFound("Task not found with ID: " + dto.getId()));

        Machine machine = machineRepository.findById(dto.getMachineId())
                .orElseThrow(() -> new MachineNotFound("Machine not found with ID: " + dto.getMachineId()));

        List<Piece> pieces = new ArrayList<>();
        if (dto.getPiecesId() != null && !dto.getPiecesId().isEmpty()) {
            pieces = pieceRepository.findAllById(dto.getPiecesId());

            if(pieces.size() != dto.getPiecesId().size()){
                throw new PieceNotFound("One or more pieces not found");
            }
        }

        TaskStatus newStatus = TaskStatus.valueOf(dto.getStatus().toUpperCase());

        existingTask.setName(dto.getName());
        existingTask.setPrice(dto.getPrice());
        existingTask.setDescription(dto.getDescription());
        existingTask.setStatus(newStatus);
        existingTask.setMachine(machine);
        existingTask.setPieces(pieces);

        Task savedTask = taskRepository.save(existingTask);
        return modelMapper.map(savedTask, ReturnTaskDTO.class);

    }

    @Transactional
    public void delete(UUID id){
        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> new TaskNotFound("Task not found with ID: " + id));
        taskRepository.delete(task);
    }
}
