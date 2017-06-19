package com.springboot.controller;

import java.util.List;
import java.util.Map;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.repository.CrudRepository;

import org.apache.commons.beanutils.BeanUtils;

import com.springboot.model.User;
import com.springboot.service.UserService;
import com.springboot.service.EntityService;
import com.springboot.util.CustomErrorType;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/api")
public abstract class RestApiController<T, ID extends Serializable>  {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	private CrudRepository<T, ID> repo;

	public RestApiController(CrudRepository<T, ID> repo) {
        this.repo = repo;
        logger.info("------In  RestApiController this.repo = {}", this.repo);
    }

	// -------------------Retrieve All Entities---------------------------------------------

    @RequestMapping
    public ResponseEntity<List<T>> listAll() {
		logger.info("Fetching all Entities");
        Iterable<T> all = this.repo.findAll();
        if (Lists.newArrayList(all).isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You may decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<T>>(Lists.newArrayList(all), HttpStatus.OK);
    }

	// -------------------Retrieve Single Entity------------------------------------------

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<T> get(@PathVariable ID id) {
		logger.info("Fetching Entity with id {}", id);
        // return this.repo.findOne(id);
        T t = this.repo.findOne(id);

        if (t == null) {
			logger.error("Entity with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Entity with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<T>(t, HttpStatus.OK);
    }
// -------------------Retrieve Single Entity By Name ------------------------------------------

    /*@RequestMapping(value="/Entity/{name}", method=RequestMethod.GET)
    public ResponseEntity<T> get(@PathVariable String name) {
		logger.info("Fetching Entity with id {}", name);
        // return this.repo.findOne(name);
        T t = this.repo.findByName(name);

        if (t == null) {
			logger.error("Entity with name {} not found.", name);
			return new ResponseEntity(new CustomErrorType("Entity with name " + name 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<T>(t, HttpStatus.OK);
    }*/

	// -------------------Create a Entity-------------------------------------------

    @RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> create(@RequestBody T json) {
		logger.info("Creating Entity : {}", json);
        logger.debug("create() with body {} of type {}", json, json.getClass());
        T created = this.repo.save(json);
        Map<String, Object> m = Maps.newHashMap();
        m.put("success", true);
        m.put("created", created);
		return new ResponseEntity<Map<String, Object>>(m, HttpStatus.CREATED);
    }

	// ------------------- Update a Entity ------------------------------------------------

    @RequestMapping(value="/{id}", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> update(@PathVariable ID id, @RequestBody T json) {
        logger.debug("update() of id#{} with body {}", id, json);
        logger.debug("T json is of type {}", json.getClass());

        T entity = this.repo.findOne(id);
        try {
            BeanUtils.copyProperties(entity, json);
        }
        catch (Exception e) {
            logger.warn("while copying properties", e);
            throw Throwables.propagate(e);
        }

        logger.debug("merged entity: {}", entity);

        T updated = this.repo.save(entity);
        logger.debug("updated enitity: {}", updated);

        Map<String, Object> m = Maps.newHashMap();
        m.put("success", true);
        m.put("id", id);
        m.put("updated", updated);
		return new ResponseEntity<Map<String, Object>>(m, HttpStatus.OK);
    }

	// ------------------- Delete a Entity-----------------------------------------

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> delete(@PathVariable ID id) {
		logger.info("Deleting Entity with id {}", id);

        this.repo.delete(id);
        Map<String, Object> m = Maps.newHashMap();
        m.put("success", true);
		return new ResponseEntity<Map<String, Object>>(m, HttpStatus.NO_CONTENT);
    }
    // ------------------- Delete all Entity-----------------------------------------

    @RequestMapping(method=RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteAll() {
		logger.info("Deletes all entities managed by the repository.");

        this.repo.deleteAll();
        Map<String, Object> m = Maps.newHashMap();
        m.put("success", true);
		return new ResponseEntity<Map<String, Object>>(m, HttpStatus.NO_CONTENT);
    }
}