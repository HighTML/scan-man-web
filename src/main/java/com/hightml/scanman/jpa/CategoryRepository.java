/**
 * 
 */
package com.hightml.scanman.jpa;

import com.hightml.scanman.value.Category;
import com.hightml.scanman.value.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Marcel
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    public final static String FIND_PARENTS_QUERY = "SELECT c FROM Category c WHERE c.parent = null";

    @Query(FIND_PARENTS_QUERY)
    public List<Category> findAllParents();

    public Category findById(int id);

    public Category findByCode(String code);

    public List<Category> findByDisplayNameLike(String name);
}
