package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public interface CategoryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    CategoryDto toDto (Category category);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    Category updateCategory (CategoryDto categoryDto, @MappingTarget Category category);
}
