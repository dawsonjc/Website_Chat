package com.brewery.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@EnableCassandraRepositories
@SpringBootApplication(scanBasePackages = { "com.brewery.web" })
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

        Printer<Integer> printer = new Printer<Integer>(1);

    }

    public static class Printer<T extends Object> {
        T thing;


        public Printer(T thing) {
            this.thing = thing;
        }


        public void print() {
            System.out.println(this.thing.toString());
        }
    }
}
