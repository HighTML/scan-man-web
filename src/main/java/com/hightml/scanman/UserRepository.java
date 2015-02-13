/**
 * 
 */
package com.hightml.scanman;

import com.hightml.scanman.value.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcel
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>{

}
