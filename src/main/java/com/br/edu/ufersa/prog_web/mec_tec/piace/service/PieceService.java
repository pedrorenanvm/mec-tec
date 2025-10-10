package com.br.edu.ufersa.prog_web.mec_tec.piace.service;

import com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto.CreatePieceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto.ReturnPieceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto.UpdatePieceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.piace.exception.PieceAlreadyExist;
import com.br.edu.ufersa.prog_web.mec_tec.piace.exception.PieceNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.piace.model.entity.Piece;
import com.br.edu.ufersa.prog_web.mec_tec.piace.model.repository.PieceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class PieceService {
    private final PieceRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PieceService(PieceRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
    @Transactional(readOnly = true)
    public Page<ReturnPieceDTO> findAll(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Piece> pageResult;

        if (searchTerm == null || searchTerm.isBlank()) {
            pageResult = repository.findAll(pageable);
        } else {
            pageResult = repository.findAllPaginate(searchTerm, pageable);
        }

        return pageResult.map(e -> modelMapper.map(e, ReturnPieceDTO.class));
    }


    public ReturnPieceDTO findById(UUID id) {
        Piece peace = repository.findById(id)
                .orElseThrow(() -> new PieceNotFound("Peace not found."));
        return modelMapper.map(peace, ReturnPieceDTO.class);
    }

    public ReturnPieceDTO create(CreatePieceDTO dto) {
        if (repository.findByName(dto.getName()).isPresent()) {
            throw new PieceAlreadyExist("Peace with this name already exists.");
        }

        Piece peace = modelMapper.map(dto, Piece.class);
        Piece created = repository.save(peace);
        return modelMapper.map(created, ReturnPieceDTO.class);
    }

    public ReturnPieceDTO update(UpdatePieceDTO dto) {
        Piece peace = repository.findById(dto.getId())
                .orElseThrow(() -> new PieceNotFound("Peace not found."));

        if (!peace.getName().equals(dto.getName())
                && repository.findByName(dto.getName()).isPresent()) {
            throw new PieceAlreadyExist("Peace with this name already exists.");
        }

        modelMapper.map(dto, peace);
        Piece updated = repository.save(peace);
        return modelMapper.map(updated, ReturnPieceDTO.class);
    }

    public void delete(UUID id) {
        Piece peace = repository.findById(id)
                .orElseThrow(() -> new PieceNotFound("Peace not found."));
        repository.delete(peace);
    }
}
