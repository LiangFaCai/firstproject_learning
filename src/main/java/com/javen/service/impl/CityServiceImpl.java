package com.javen.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javen.mapper.CityMapper;
import com.javen.model.City;
import com.javen.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;
	
	@Override
	public City getList(Integer id) {
		// TODO Auto-generated method stub
		return cityMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> getListByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return cityMapper.getListByMap(map);
	}

	@Override
	public List<City> getListByMapPage() {
		// TODO Auto-generated method stub
		return cityMapper.getListByMapPage();
	}

}
