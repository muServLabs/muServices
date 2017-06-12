package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springboot.service.AlphabetService;
import com.springboot.service.NumberService;

import com.springboot.model.Employee;
import com.springboot.model.Address;


@SpringBootApplication(scanBasePackages={"com.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class SpringBootRestApiApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApp.class, args);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		Employee e1=(Employee)context.getBean("e1");
		e1.show();
		
		NumberService numService = (NumberService)context.getBean("numberService");
		System.out.println(" Number = " + numService.getNumber());
		System.out.println(" Number = " + numService.getNumber());
		
		AlphabetService alphabetService = (AlphabetService)context.getBean("alphabetService");
		System.out.println(" Alphabet =  " + alphabetService.getAlphabet());
		System.out.println(" Alphabet =  " + alphabetService.getAlphabet());


	}
}
