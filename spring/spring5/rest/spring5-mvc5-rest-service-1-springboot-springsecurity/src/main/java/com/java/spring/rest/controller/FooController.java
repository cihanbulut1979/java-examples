package com.java.spring.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.java.spring.rest.model.Foo;

@Controller
@RequestMapping("/foos")
class FooController {

	public static Map<Long, Foo> foos = new HashMap<Long, Foo>();
	
	static{
		foos.put(1L, new Foo(1L, "Foo-1"));
		foos.put(2L, new Foo(2L, "Foo-2"));
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Foo> findAll() {
		return new ArrayList(foos.values());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Foo findOne(@PathVariable("id") Long id) {
		return foos.get(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Long create(@RequestBody Foo foo) {
		foos.put(foo.getId(), foo);

		return foo.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("id") Long id, @RequestBody Foo foo) {

		foos.put(id, foo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		foos.remove(id);
	}

}
