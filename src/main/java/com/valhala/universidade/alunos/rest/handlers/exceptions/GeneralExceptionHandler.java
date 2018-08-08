package com.valhala.universidade.alunos.rest.handlers.exceptions;

import com.valhala.universidade.alunos.rest.exceptions.ValidationException;
import com.valhala.universidade.canonico.ErroDto;
import com.valhala.universidade.canonico.ErroValidacaoDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDto> handleException(Exception e) {
        ErroDto erro = new ErroDto();
        erro.setMensagem("Ocorreu um erro na execução da operação.");
        erro.setDescricao("Trata-se de um erro interno da aplicação.");
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroDto> handleDataIntegratyViolation(DataIntegrityViolationException e) {
        ErroDto erro = new ErroDto();
        erro.setMensagem("Houve violação de integridade de dados na execução dessa ação.");
        erro.setDescricao("Trata-se de um erro disparado quando existe a tentantiva de gravar informações duplicadas na base de dados da aplicação.");
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErroDto> handleValidationErrors(ValidationException e) {
        ErroDto erro = new ErroDto();
        erro.setMensagem("Existem dados inválidos na requisição.");
        erro.setDescricao("Trata-se de erro na validação de dados enviados para a api.");

        List<ErroValidacaoDto> errosValidacao = new ArrayList<>();
        e.getBindingResult().getFieldErrors().stream().forEach(fieldError -> {
            ErroValidacaoDto erroValidacao = new ErroValidacaoDto();
            erroValidacao.setCampo(fieldError.getField());
            erroValidacao.setMensagem(fieldError.getDefaultMessage());
            errosValidacao.add(erroValidacao);
        });

        erro.setErros(errosValidacao);

        return new ResponseEntity<>(erro, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
