/**
 * 
 */
package com.hightml.scanman.controllers;

import com.hightml.scanman.jpa.ScanRepository;
import com.hightml.scanman.value.Scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Marcel
 *
 */
@RestController
@RequestMapping(value="/scans/", produces=APPLICATION_JSON_VALUE)
public class ScanController {
	
	@Autowired
    private ScanRepository scanRepository;

	@RequestMapping(value="", method=GET)
	public List<Scan> getScans() {
		return scanRepository.findAll();
	}



}
