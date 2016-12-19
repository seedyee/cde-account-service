package io.cde.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 程序入口.
 *
 * @author lcl
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

    /**s
     * main方法.
     *
     * @param args 参数
     */
    public static void main(final String[] args) {
        final Logger logger = LoggerFactory.getLogger(Application.class);
        logger.info("service start");
        SpringApplication.run(Application.class, args);
    }
}