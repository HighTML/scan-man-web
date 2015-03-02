/**
 * 
 */
package com.hightml.scanman.controllers;

import com.hightml.scanman.jpa.CategoryRepository;
import com.hightml.scanman.value.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Marcel
 *
 */
@RestController
@RequestMapping(value="/categories/", produces=APPLICATION_JSON_VALUE)
@Slf4j
public class CategoryController {
	
	@Autowired
    private CategoryRepository categoryRepository;

	@RequestMapping(value="", method=GET)
	public List<Category> getCategories() {
		return categoryRepository.findAllParents();
	}

    @RequestMapping(value="{id:[\\d]}")
    @ResponseBody
    public Category getCategory(@PathVariable("id") int id){
        return categoryRepository.findById(id);
    }

    @RequestMapping(value="{id:[\\D]}")
    @ResponseBody
    public Category method8(@PathVariable("id") String code ){
        return categoryRepository.findByCode(code);
    }


    // not there yet
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveCategory(@RequestBody Category category) {
        categoryRepository.save(category);
    }

}
