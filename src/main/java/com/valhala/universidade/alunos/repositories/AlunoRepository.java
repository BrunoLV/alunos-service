package com.valhala.universidade.alunos.repositories;

import com.valhala.universidade.alunos.model.Aluno;
import com.valhala.universidade.alunos.model.projections.IdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findAllByAtivoTrue();

    Aluno findByUuid(String uuid);

    IdProjection findIdByUuid(String uuid);

    @Modifying
    @Query("UPDATE Aluno a SET a.ativo = true WHERE a.uuid = ?1")
    void setActiveFor(String uuid);

    @Modifying
    @Query("UPDATE Aluno a SET a.ativo = false WHERE a.uuid = ?1")
    void setInactiveFor(String uuid);

    boolean existsByUuid(String uuid);

    @Query("SELECT MAX(a.matricula) FROM Aluno a")
    Long findMaxMatricula();
}
