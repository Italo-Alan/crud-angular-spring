package com.italo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.italo.enums.Category;
import com.italo.model.Course;
import com.italo.model.Lesson;
import com.italo.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDataBase(CourseRepository courseRepository){
		return args -> {
			courseRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular com Spring");
			c.setCategory(Category.FRONT_END);

			Lesson l = new Lesson();

			l.setName("Aprendendo Angular");
			l.setYoutubeUrl("Nb4uxLxdvxo");
			l.setCourse(c);
			c.getLessons().add(l);

			Lesson l1 = new Lesson();

			l1.setName("Aprendendo Angular");
			l1.setYoutubeUrl("Nb4uxLxdvxo");
			l1.setCourse(c);
			c.getLessons().add(l1);

			courseRepository.save(c);
		};
	}
}
