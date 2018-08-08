package com.valhala.universidade.alunos.validators;

import com.valhala.universidade.alunos.model.Aluno;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@Component
public class AlunoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Aluno.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Aluno aluno = (Aluno) o;
        Set<ConstraintViolation<Aluno>> constraints = Validation.buildDefaultValidatorFactory().getValidator().validate(aluno);

        if (CollectionUtils.isNotEmpty(constraints)) {
            constraints.stream().forEach(constraintViolation -> errors.rejectValue(constraintViolation.getPropertyPath().toString(), null, constraintViolation.getMessage()));
        }
    }
}
