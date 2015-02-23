/**
 * 
 */
package com.hightml.scanman.jpa;

import com.hightml.scanman.value.Category;
import com.hightml.scanman.value.Scan;
import com.hightml.scanman.value.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcel
 *
 */

public interface ScanRepository extends JpaRepository<Scan, Integer> {




}
