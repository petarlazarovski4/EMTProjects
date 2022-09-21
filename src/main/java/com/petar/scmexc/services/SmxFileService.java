package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.SmxFile;
import com.marco.scmexc.models.exceptions.FileNotFoundException;
import com.marco.scmexc.repository.SmxFileRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class SmxFileService {


    private final SmxFileRepository repository;

    public SmxFileService(SmxFileRepository repository) {
        this.repository = repository;
    }

    public SmxFile getFile(Long id) {
        //exception to add
        return repository.findById(id).orElseThrow(()->new FileNotFoundException(id));
    }

    public Stream<SmxFile> getAllFiles() {
        return repository.findAll().stream();
    }

    public Stream<SmxFile> getAllFilesByMaterial(Long materialID) {
        return repository.findAll().stream().filter(smxFile -> smxFile.getItem().getMaterial().getId().equals(materialID));
    }
}
