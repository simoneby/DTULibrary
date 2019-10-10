package com.example.demo.accessingsatamysql;
import javax.websocket.Session;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




import java.util.Optional;


@SpringBootApplication
public class AccessingDataMysqlApplication{



    public static void main(String[] args) {

        SpringApplication.run(AccessingDataMysqlApplication.class, args);


    }


}

