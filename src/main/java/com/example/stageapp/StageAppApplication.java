package com.example.stageapp;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication //Configuration + EnableAutoConfiguration + ComponentScan
@Log
@EnableFeignClients
@EnableDiscoveryClient
public class StageAppApplication  implements CommandLineRunner {


//Analyze "stage-app": sqp_dcb840a58a1b3b5bf63627a11589453386785604

	//    @Autowired
//    MovieService movieService;


	public static void main(String[] args) {
//            config();
		SpringApplication.run(StageAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//        movieService.addAllMongoDBKeys();
	}

//    public static void config(){
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//        Movie movie = context.getBean("movie", Movie.class);
//        movie.saluta();
//
//        MovieController movieController = context.getBean("movieController", MovieController.class);
//        movieController.saluta();
//
//        MovieService movieService = context.getBean("movieService", MovieService.class);
//        movieService.saluta();
//
//    }

}
