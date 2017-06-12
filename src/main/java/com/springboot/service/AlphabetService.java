package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import com.springboot.service.Generator;

@Service
public class AlphabetService {

	@Autowired
	private Generator<String> generator;
	
	public AlphabetService(){
		
	}
	
	public String getAlphabet(){
		return generator.generate();
	}
	
}