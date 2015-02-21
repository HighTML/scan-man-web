package com.hightml.scanman.rest;


import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 20/02/15
 * Time: 17:48
 * <p>
 * Copyright by HighTML.
 */
@Controller
public class DumpDbController {

    private final Object monitor = new Object();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DumpDbController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/dump", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public FileSystemResource dumpDb() throws IOException {
        synchronized (this.monitor) {
            File dump = new File("dump.sql");
            if (dump.exists()) {
                dump.delete();
            }
            this.jdbcTemplate.execute("script to '" + dump.getAbsolutePath() + "'");
            // HSQLDB use SCRIPT
            // H@ use SCRIPT TO

            return new FileSystemResource(dump.getAbsolutePath());
        }
    }
}



