package com.br.edu.ufersa.prog_web.mec_tec.peace.service;

import com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto.CreatePeaceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto.ReturnPeaceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto.UpdatePeaceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.peace.exception.PeaceAlreadyExist;
import com.br.edu.ufersa.prog_web.mec_tec.peace.exception.PeaceNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.peace.model.entity.Peace;
import com.br.edu.ufersa.prog_web.mec_tec.peace.model.repository.PeaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PeaceService {
    private final PeaceRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PeaceService(PeaceRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<ReturnPeaceDTO> findAll() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, ReturnPeaceDTO.class))
                .toList();
    }

    public ReturnPeaceDTO findById(UUID id) {
        Peace peace = repository.findById(id)
                .orElseThrow(() -> new PeaceNotFound("Peace not found."));
        return modelMapper.map(peace, ReturnPeaceDTO.class);
    }

    public ReturnPeaceDTO create(CreatePeaceDTO dto) {
        if (repository.findByName(dto.getName()).isPresent()) {
            throw new PeaceAlreadyExist("Peace with this name already exists.");
        }

        Peace peace = modelMapper.map(dto, Peace.class);
        Peace created = repository.save(peace);
        return modelMapper.map(created, ReturnPeaceDTO.class);
    }

    public ReturnPeaceDTO update(UpdatePeaceDTO dto) {
        Peace peace = repository.findById(dto.getId())
                .orElseThrow(() -> new PeaceNotFound("Peace not found."));

        if (!peace.getName().equals(dto.getName())
                && repository.findByName(dto.getName()).isPresent()) {
            throw new PeaceAlreadyExist("Peace with this name already exists.");
        }

        modelMapper.map(dto, peace);
        Peace updated = repository.save(peace);
        return modelMapper.map(updated, ReturnPeaceDTO.class);
    }

    public void delete(UUID id) {
        Peace peace = repository.findById(id)
                .orElseThrow(() -> new PeaceNotFound("Peace not found."));
        repository.delete(peace);
    }
}
