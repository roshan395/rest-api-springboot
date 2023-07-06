package com.roshan.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		SomeBean someBean = new SomeBean("Value1", "Value2", "Value3");
		Set<String> field = new HashSet<String>();
		field.add("field1");
		field.add("field3");
		MappingJacksonValue mappingJacksonValue = filter(someBean, field);
		return mappingJacksonValue;
	}

	private MappingJacksonValue filter(Object obj, Set<String> field) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(obj);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(field);
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List<SomeBean> list = Arrays.asList(new SomeBean("Value1", "Value2", "Value3"),
				new SomeBean("Value4", "Value5", "Value6"));
		Set<String> field = new HashSet<String>();
		field.add("field1");
		field.add("field2");
		MappingJacksonValue mappingJacksonValue = filter(list, field);
		return mappingJacksonValue;
	}

}
