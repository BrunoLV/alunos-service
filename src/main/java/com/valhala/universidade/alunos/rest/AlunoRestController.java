package com.valhala.universidade.alunos.rest;

import com.valhala.universidade.alunos.model.Aluno;
import com.valhala.universidade.alunos.rest.actions.ChangeStatusAction;
import com.valhala.universidade.alunos.services.AlunoService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(AlunoRestController.class);

    @Autowired
    private AlunoService service;

    @PatchMapping(value = "/{uuid}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable(value = "uuid") final String uuid,
                                             @RequestBody final ChangeStatusAction action) {
        LOGGER.info("Executando WS de mudanca de Status de Aluno.");
        boolean exist = service.exist(uuid);
        if (!exist) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        switch (action.getAction()) {
            case ACTIVATE:
                service.activate(uuid);
                break;
            case DEACTIVATE:
                service.deactivate(uuid);
                break;
            default:
                break;
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable(value = "uuid") final String uuid) {
        LOGGER.info("Executando WS de exclusao (logica) de Aluno.");
        boolean exist = service.exist(uuid);
        if (!exist) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deactivate(uuid);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        LOGGER.info("Executando WS de listagem de Alunos.");
        List<Aluno> lista = service.findAll();
        if (CollectionUtils.isEmpty(lista)) {
            return new ResponseEntity<List<Aluno>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Aluno>>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Aluno> findOneByUuid(@PathVariable(value = "uuid") final String uuid) {
        LOGGER.info("Executando WS de pesquisa de Aluno por Uuid.");
        Aluno aluno = service.findByUuid(uuid);
        if (aluno == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Aluno>(aluno, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody final Aluno aluno, UriComponentsBuilder builder) {
        LOGGER.info("Executando WS de criacao de Aluno.");
        Aluno alunoSaved = service.save(aluno);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/alunos/{uuid}").buildAndExpand(alunoSaved.getUuid()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<Void> update(@PathVariable(value = "uuid") final String uuid,
                                       @RequestBody final Aluno aluno) {
        LOGGER.info("Executando WS de edicao de Aluno.");
        boolean exist = service.exist(uuid);
        if (!exist) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.update(aluno, uuid);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
