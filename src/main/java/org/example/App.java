package org.example;

import org.example.config.SpringConfig;
import org.example.dao.impl.AdvancedCatRepository;
import org.example.dao.impl.SpringCatRepository;
import org.example.entity.Cat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws SQLException {
//        System.out.println( "Hello" );
//
//        AdvancedCatRepository catRepository = new AdvancedCatRepository();
//
//        Cat cat1 = new Cat();
//        cat1.setId(1L);
//        cat1.setName("Murzik");
//        cat1.setWeight(34);
//        cat1.setAngry(false);
//
//        Cat cat2 = new Cat();
//        cat2.setId(2L);
//        cat2.setName("Barsik");
//        cat2.setWeight(34);
//        cat2.setAngry(false);
//
//        Cat cat3 = new Cat();
//        cat3.setId(3L);
//        cat3.setName("Tutanhamon");
//        cat3.setWeight(56);
//        cat3.setAngry(false);
//
//        try {
//            catRepository.createTable();
//
//            catRepository.addElement(cat1);
//            catRepository.addElement(cat2);
//
//            for (Cat cat : catRepository.getAll()) {
//                System.out.println(cat);
//            }
//            System.out.println("***********************");
//
//            System.out.println(catRepository.getById(1L));
//            System.out.println("***********************");
//
//            catRepository.update(1L, "Persik", 25, true);
//            System.out.println(catRepository.getById(1L));
//            System.out.println("***********************");
//
//            catRepository.remove(2L);
//
//            for (Cat cat : catRepository.getAll()) {
//                System.out.println(cat);
//            }
//            System.out.println("***********************");
//
//            catRepository.addElement(cat3);
//            for (Cat cat : catRepository.getAll()) {
//                System.out.println(cat);
//            }
//            System.out.println("***********************");
//
//            catRepository.closeConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        System.out.println("Hello SpringJDBC!!!");

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SpringCatRepository repo = context.getBean(SpringCatRepository.class);
        //repo.init();

        repo.getAll().forEach(System.out::println);
        System.out.println("**************");

        System.out.println(repo.getById(2L));
        System.out.println("**************");

        repo.update(1L, "Arbuzik", 50, true);
        System.out.println(repo.getById(1L));
        System.out.println("**************");

        repo.remove(4L);
        repo.getAll().forEach(System.out::println);
        System.out.println("**************");
    }
}