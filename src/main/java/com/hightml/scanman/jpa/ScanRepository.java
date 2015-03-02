/**
 * 
 */
package com.hightml.scanman.jpa;

import com.hightml.scanman.value.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Marcel
 *
 */

public interface ScanRepository extends JpaRepository<Scan, Integer> {

    public Scan findById(int id);

    public Scan findByDigest(String digest);


    public final static String FIND_NEW_QUERY = "SELECT s FROM Scan s WHERE s.flattenedToImageFile = false";

    @Query(FIND_NEW_QUERY)
    public List<Scan> findAllNewScans();





}
