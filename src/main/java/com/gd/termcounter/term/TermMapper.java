package com.gd.termcounter.term;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TermMapper {
    TermMapper INSTANCE = Mappers.getMapper(TermMapper.class);

    Term termDtoToTerm(TermDTO termDTO);
}