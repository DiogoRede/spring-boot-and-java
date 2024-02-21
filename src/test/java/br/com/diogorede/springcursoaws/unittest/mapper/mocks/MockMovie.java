package br.com.diogorede.springcursoaws.unittest.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.diogorede.springcursoaws.data.vo.v1.MovieVo;
import br.com.diogorede.springcursoaws.entities.Movie;


public class MockMovie {

    MockCategory mockCategory;
    MockPerson mockPerson;

    public Movie mockEntity() {
        return mockEntity(0);
    }
    
    public MovieVo mockVO() {
        return mockVO(0);
    }
    
    public List<Movie> mockEntityList() {
        List<Movie> movies = new ArrayList<Movie>();
        for (int i = 0; i < 14; i++) {
            movies.add(mockEntity(i));
        }
        return movies;
    }

    public List<MovieVo> mockVOList() {
        List<MovieVo> vo = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            vo.add(mockVO(i));
        }
        return vo;
    }

    public Movie mockEntity(Integer number) {
        Movie movie = new Movie();

        movie.setId(number.longValue());
        movie.setName("Name Test" + number);
        movie.setDescription("Description Test" + number);
        movie.setPriority(number);
        movie.setYear(number);

        mockCategory = new MockCategory();
        mockPerson = new MockPerson();

        movie.setCategory(mockCategory.mockEntity(number));
        movie.setPerson(mockPerson.mockEntity(number));

        return movie;
    }

    public MovieVo mockVO(Integer number) {
        MovieVo movie = new MovieVo();
        movie.setKey(number.longValue());
        movie.setName("Name Test" + number);
        movie.setDescription("Description Test" + number);
        movie.setPriority(number);
        movie.setYear(number);

        mockCategory = new MockCategory();
        mockPerson = new MockPerson();

        movie.setCategory(mockCategory.mockEntity(number));
        movie.setPerson(mockPerson.mockEntity(number));

        return movie;
    }
}