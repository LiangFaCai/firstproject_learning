package com.javen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javen.model.City;
import com.javen.service.CityService;

@RestController
public class TestController {
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping("/test")
	public String test() {
		
		return "hello";
	}
	
	@RequestMapping("/city/{id}")
	public Map getList(HttpServletRequest request,@PathVariable("id")Integer id) {
		City city=cityService.getList(id);
		HashMap<String,Object> hashMap = new HashMap<>();
		hashMap.put("city", city);
		return hashMap;
	}
	
	@RequestMapping("/city")
	public List<Map<String,Object>> getList(HttpServletRequest request,Map<String,Object> map) {
		Page<Object> page = PageHelper.startPage(0,10);
		map.put("page",page);
		List<Map<String,Object>> list=cityService.getListByMap(map);
		return list;
	}

	@RequestMapping("/cityPage")
	public Map<String, Object> getListPage(HttpServletRequest request) {
		Map<String,Object> hashMap = new HashMap<>();
		PageHelper.startPage(0,10);
		List<City> list=cityService.getListByMapPage();
		PageInfo<City> pageInfo=new PageInfo<City>(list,10);
		hashMap.put("pageInfos", pageInfo);
		return hashMap;
	}
}
