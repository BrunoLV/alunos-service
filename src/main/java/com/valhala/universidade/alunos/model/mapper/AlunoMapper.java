package com.valhala.universidade.alunos.model.mapper;

import com.valhala.universidade.alunos.model.Aluno;
import com.valhala.universidade.canonico.AlunoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "id", ignore = true)
    Aluno toAluno(AlunoDto alunoDto);

    AlunoDto toAlunoDto(Aluno aluno);

    Collection<Aluno> toAlunoCollection(Collection<AlunoDto> collectionDto);

    Collection<AlunoDto> toAlunoDtoCollection(Collection<Aluno> collection);

}
