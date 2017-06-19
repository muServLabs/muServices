package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.data.repository.CrudRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.model.GenEntity;
import com.springboot.service.EntityService;

@Controller
@RequestMapping("/api")
public class EntityController extends RestApiController<GenEntity, Long> {

	public static final Logger logger = LoggerFactory.getLogger(EntityController.class);

    @Autowired
    public EntityController(EntityService repo) {
        super(repo);
        logger.info("------In  EntityController repo = {}", repo);
    }
}

