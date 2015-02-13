/**
 * 
 */
package com.hightml.scanman;

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

public class ScanRepository {



    public static List<Scan> findAll() {
        List<Scan> scans = new ArrayList<>();
        scans.add(new Scan("src/test/resources/sample-scan.pdf"));
        return scans;
    }


    public static Scan getOne(String f) {
        Scan scan = new Scan("src/test/resources/sample-scan.pdf");
        scan.readText();

        return scan;
    }
}
