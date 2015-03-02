/**
 *
 */
package com.hightml.scanman;

/**
 * @author Marcel
 *
 */

import com.hightml.scanman.jpa.CategoryRepository;
import com.hightml.scanman.value.Category;
import com.hightml.scanman.value.Keyword;
import com.sun.jna.Native;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TessAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


@Configuration
@EnableAutoConfiguration
@ComponentScan
@Slf4j
public class Application {
    public final static String SCAN_DIRECTORY = (new File(".")).getAbsolutePath();


    public static void main(String[] args) throws Exception {


        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        CategoryRepository repository = context.getBean(CategoryRepository.class);


        repository.save(new Category("Hypotheek documenten", "HYPO"));
        repository.save(new Category("Zorg", "ZORG"));
        repository.save(new Category("Recepten & Kookkunst", "KOKEN"));

        Category bank = new Category("Bankzaken", "BANK");
        repository.save(bank);

        Category marcelPriveBank = new Category("Prive bank Marcel", "MARCELBANK");
        marcelPriveBank.setParent(bank);
        marcelPriveBank.setCategoryKeywords(Arrays.asList("ABN", "ABNAMRO", "private"));

        log.debug("Marcel : "+marcelPriveBank);

        repository.save(marcelPriveBank);

        Iterable<Category> categories = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Category category : categories) {
            System.out.println(category);
        }

        System.out.println();

        log.debug(SCAN_DIRECTORY);


    }
}