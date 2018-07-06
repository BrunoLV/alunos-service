package com.valhala.universidade.alunos.jpa.listeners;

import com.valhala.universidade.alunos.model.Aluno;

import javax.persistence.PrePersist;
import java.util.Calendar;
import java.util.UUID;

public class AlunoPersistListener {

    @PrePersist
    public void setUUID(Aluno aluno) {
        aluno.setUuid(UUID.randomUUID().toString());
        aluno.setDataCadastro(Calendar.getInstance().getTime());
        aluno.setAtivo(Boolean.TRUE);
    }

}
