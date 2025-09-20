package com.example.demo.mappers;

import com.example.demo.dto.BenutzerDtoReq;
import com.example.demo.dto.BenutzerDtoRes;
import com.example.demo.models.Benutzer;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BenutzerMapper {

    BenutzerDtoRes toDto(Benutzer benutzer);
    Benutzer toEntity(BenutzerDtoReq benutzerDtoReq);

}
