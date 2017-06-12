package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.service.Generator;

@Service
public class NumberService {

	@Autowired
	private Generator<Integer> generator;
	
	public NumberService(){
		
	}
	
	public Integer getNumber(){
		return generator.generate();
	}
}