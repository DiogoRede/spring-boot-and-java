package br.com.diogorede.springcursoaws.services;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diogorede.springcursoaws.controllers.CategoryController;
import br.com.diogorede.springcursoaws.data.vo.v1.CategoryVo;
import br.com.diogorede.springcursoaws.entities.Category;
import br.com.diogorede.springcursoaws.exceptions.RequiredObjectIsNullException;
import br.com.diogorede.springcursoaws.exceptions.ResourceNotFoundException;
import br.com.diogorede.springcursoaws.mapper.DozerMapper;
import br.com.diogorede.springcursoaws.repositories.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public List<CategoryVo> findAll(){
        var categories = DozerMapper.parseListObjects(repository.findAll(), CategoryVo.class);
        categories.stream()
            .forEach(movie -> {
                try {
                    movie.add(linkTo(methodOn(CategoryController.class).findById(movie.getKey())).withSelfRel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        return categories;
    }

    public CategoryVo findById(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        CategoryVo vo = DozerMapper.parseObject(entity, CategoryVo.class);
        try {
            vo.add(linkTo(methodOn(CategoryController.class).findById(vo.getKey())).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public CategoryVo create(CategoryVo category){
        if(category==null) throw new RequiredObjectIsNullException();

        var entity = DozerMapper.parseObject(category, Category.class);
        var vo = DozerMapper.parseObject(repository.save(entity), CategoryVo.class);
        try {
            vo.add(linkTo(methodOn(CategoryController.class).findById(vo.getKey())).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
    public CategoryVo update(CategoryVo category){
        if(category==null) throw new RequiredObjectIsNullException();

        var entity = DozerMapper.parseObject(category, Category.class);

        var vo = DozerMapper.parseObject(repository.save(entity), CategoryVo.class);

        try {
            vo.add(linkTo(methodOn(CategoryController.class).findById(vo.getKey())).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
    
    public void delete(Long id){
        repository.deleteById(id);
    }

}