package org.sid;

import org.sid.dao.CategoryRepository;
import org.sid.dao.ProductRepository;
import org.sid.entities.Category;
import org.sid.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class CathalogueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CathalogueServiceApplication.class, args);
	}

    @Bean
	CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository){
		return args -> {
			categoryRepository.deleteAll();
			Stream.of("C1 Ordi","C2 imprimantes").forEach(c->{
				categoryRepository.save(new Category(c.split(" ")[0],c.split("")[1], new ArrayList<>()));
			});

			categoryRepository.findAll().forEach(System.out::println);


			productRepository.deleteAll();
			Category c1=categoryRepository.findById("C1").get();
            Stream.of("P1","P2","P3","P4").forEach(p->{
               Product p1= productRepository.save(new Product(null,p,Math.random()*1000,c1));
                c1.getProducts().add(p1);
                categoryRepository.save(c1);
            });


			Category c2=categoryRepository.findById("C2").get();
			Stream.of("P5","P6").forEach(p->{
				Product p2=productRepository.save(new Product(null,p,Math.random()*1000,c2));

				c2.getProducts().add(p2);
				categoryRepository.save(c2);
			});
			productRepository.findAll().forEach(p->{
				System.out.println(p.toString());
			});

		};



	}
}
