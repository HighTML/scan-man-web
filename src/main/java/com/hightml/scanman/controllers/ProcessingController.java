package com.hightml.scanman.controllers;

import com.hightml.scanman.processing.ScanProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 28/02/15
 * Time: 14:13
 * <p>
 * Copyright by HighTML.
 */
@RestController
@RequestMapping(value="/process", produces=APPLICATION_JSON_VALUE)
@Slf4j
public class ProcessingController {

    @Autowired
    ScanProcessor scanProcessor;

    @RequestMapping(value="/start", method=GET)
    public String start() {
        try {
            scanProcessor.start();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        return "DONE";
    }
}