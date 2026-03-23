package com.example.__HoangAnhThu.config;


import com.example.__HoangAnhThu.entity.Category;
import com.example.__HoangAnhThu.repository.CategoryRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<String, Category> {

    private final CategoryRepository categoryRepository;

    public CategoryConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return categoryRepository.findById(Long.parseLong(source)).orElse(null);
    }
}