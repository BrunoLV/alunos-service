package com.valhala.universidade.alunos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valhala.universidade.alunos.jpa.listeners.AlunoPersistListener;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(value = {AlunoPersistListener.class})
@Table(name = "tb_alunos")
@Data
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String uuid;
    private String nome;
    private String documento;
    private Long matricula;

    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    private String telefone;
    private String email;
    private Boolean ativo = Boolean.TRUE;

}
