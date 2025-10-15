package com.br.edu.ufersa.prog_web.mec_tec.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Configurações gerais
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);

        // Ignora automaticamente campos de auditoria ao mapear de DTO para entidade
        mapper.addConverter(new AbstractConverter<Object, Object>() {
            @Override
            protected Object convert(Object source) {
                if (source == null) return null;

                Field[] fields = source.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equalsIgnoreCase("createdAt") ||
                            field.getName().equalsIgnoreCase("updatedAt") ||
                            field.getName().equalsIgnoreCase("deletedAt")) {
                        field.setAccessible(true);
                        try {
                            field.set(source, null);
                        } catch (IllegalAccessException e) {
                            // ignora
                        }
                    }
                }
                return source;
            }
        });

        return mapper;
    }
}
