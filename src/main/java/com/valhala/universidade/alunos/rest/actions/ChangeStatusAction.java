package com.valhala.universidade.alunos.rest.actions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class ChangeStatusAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private EnumChangeStatusAction action;

}
