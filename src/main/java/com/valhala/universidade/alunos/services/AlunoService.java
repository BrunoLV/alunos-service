package com.valhala.universidade.alunos.services;

import com.valhala.universidade.alunos.model.Aluno;
import com.valhala.universidade.alunos.model.projections.IdProjection;
import com.valhala.universidade.alunos.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void activate(final String uuid) {
        repository.setActiveFor(uuid);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deactivate(final String uuid) {
        repository.setInactiveFor(uuid);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Aluno> findAll() {
        List<Aluno> alunos = repository.findAllByAtivoTrue();
        return alunos;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Aluno findByUuid(final String uuid) {
        Aluno aluno = repository.findByUuid(uuid);
        return aluno;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Aluno save(final Aluno aluno) {
        Long matricula = getNextMatricula();
        aluno.setMatricula(matricula);
        Aluno alunoSaved = repository.save(aluno);
        return alunoSaved;
    }

    public void update(final Aluno aluno, String uuid) {
        IdProjection projection = repository.findIdByUuid(uuid);
        if (projection != null) {
            aluno.setId(projection.getId());
            save(aluno);
        }
    }

    public boolean exist(String uuid) {
        return repository.existsByUuid(uuid);
    }

    private Long getNextMatricula() {
        Long matricula = repository.findMaxMatricula();
        return matricula != null ? matricula + 1L : 1L;
    }

}
