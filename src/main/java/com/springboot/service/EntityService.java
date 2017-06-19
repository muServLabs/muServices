package com.springboot.service;


import java.util.List;

import com.springboot.model.GenEntity;

import org.springframework.data.repository.CrudRepository;


public interface EntityService extends CrudRepository<GenEntity, Long>{
	
	// Iterable<GenEntity> findAll();

	// GenEntity findByName(String name);
}
