package com.gd.termcounter.job;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    Job jobDtoToJob(JobDTO jobDTO);
}