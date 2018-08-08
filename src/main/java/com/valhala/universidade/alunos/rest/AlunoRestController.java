package com.valhala.universidade.alunos.rest;

import com.valhala.universidade.alunos.model.Aluno;
import com.valhala.universidade.alunos.model.mapper.AlunoMapper;
import com.valhala.universidade.alunos.rest.actions.ChangeStatusAction;
import com.valhala.universidade.alunos.rest.exceptions.ValidationException;
import com.valhala.universidade.alunos.services.AlunoService;
import com.valhala.universidade.alunos.validators.AlunoValidator;
import com.valhala.universidade.canonico.AlunoDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(AlunoRestController.class);

    @Autowired
    private AlunoService service;

    @Autowired
    private AlunoMapper mapper;

    @Autowired
    private AlunoValidator validator;

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
    public ResponseEntity<List<AlunoDto>> findAll() {
        LOGGER.info("Executando WS de listagem de Alunos.");

        List<Aluno> alunos = service.findAll();
        if (CollectionUtils.isEmpty(alunos)) {
            return new ResponseEntity<List<AlunoDto>>(HttpStatus.NO_CONTENT);
        }

        ArrayList<AlunoDto> lista = new ArrayList<>(mapper.toAlunoDtoCollection(alunos));
        return new ResponseEntity<List<AlunoDto>>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<AlunoDto> findOneByUuid(@PathVariable(value = "uuid") final String uuid) {
        LOGGER.info("Executando WS de pesquisa de Aluno por Uuid.");

        Aluno aluno = service.findByUuid(uuid);
        if (aluno == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AlunoDto alunoDto = mapper.toAlunoDto(aluno);

        return new ResponseEntity<AlunoDto>(alunoDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody final AlunoDto alunoDto, UriComponentsBuilder builder) {
        LOGGER.info("Executando WS de criacao de Aluno.");
        Aluno aluno = mapper.toAluno(alunoDto);

        validarAluno(aluno);

        Aluno alunoSaved = service.save(aluno);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/alunos/{uuid}").buildAndExpand(alunoSaved.getUuid()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<Void> update(@PathVariable(value = "uuid") final String uuid,
                                       @RequestBody final AlunoDto alunoDto) {
        LOGGER.info("Executando WS de edicao de Aluno.");

        boolean exist = service.exist(uuid);
        if (!exist) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Aluno aluno = mapper.toAluno(alunoDto);

        validarAluno(aluno);

        service.update(aluno, uuid);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private void validarAluno(Aluno aluno) {
        BindingResult errors = new BeanPropertyBindingResult(aluno, "aluno");
        validator.validate(aluno, errors);
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

}
