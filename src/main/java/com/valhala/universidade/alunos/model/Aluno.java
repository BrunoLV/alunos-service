package com.valhala.universidade.alunos.model;

import com.valhala.universidade.alunos.jpa.listeners.AlunoPersistListener;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    private Long id;

    private String uuid;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(message = "Nome deve ser informado com no máximo 2 caracteres.", min = 2)
    private String nome;

    @NotBlank(message = "Documento é obrigatório")
    private String documento;

    private Long matricula;

    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    private String telefone;
    private String email;
    private Boolean ativo = Boolean.TRUE;

}
