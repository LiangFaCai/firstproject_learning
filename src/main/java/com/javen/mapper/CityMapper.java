package com.javen.mapper;

import java.util.List;
import java.util.Map;

import com.javen.model.City;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

	List<Map<String, Object>> getListByMap(Map<String, Object> map);

	List<City> getListByMapPage();
}