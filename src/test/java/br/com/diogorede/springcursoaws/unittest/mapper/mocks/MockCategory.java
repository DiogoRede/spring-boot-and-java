package br.com.diogorede.springcursoaws.unittest.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.diogorede.springcursoaws.data.vo.v1.CategoryVo;
import br.com.diogorede.springcursoaws.entities.Category;


public class MockCategory {

    public Category mockEntity() {
        return mockEntity(0);
    }
    
    public CategoryVo mockVO() {
        return mockVO(0);
    }
    
    public List<Category> mockEntityList() {
        List<Category> categories = new ArrayList<Category>();
        for (int i = 0; i < 14; i++) {
            categories.add(mockEntity(i));
        }
        return categories;
    }

    public List<CategoryVo> mockVOList() {
        List<CategoryVo> vo = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            vo.add(mockVO(i));
        }
        return vo;
    }
    
    public Category mockEntity(Integer number) {
        Category category = new Category();
        category.setId(number.longValue());
        category.setName("Name Test" + number);
        return category;
    }

    public CategoryVo mockVO(Integer number) {
        CategoryVo category = new CategoryVo();
        category.setKey(number.longValue());
        category.setName("Name Test" + number);
        return category;
    }
}