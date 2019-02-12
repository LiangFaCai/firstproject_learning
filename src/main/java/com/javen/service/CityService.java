package com.javen.service;

import java.util.List;
import java.util.Map;

import com.javen.model.City;

public interface CityService {

	City getList(Integer id);

	List<Map<String, Object>> getListByMap(Map<String, Object> map);

	List<City> getListByMapPage();

}
