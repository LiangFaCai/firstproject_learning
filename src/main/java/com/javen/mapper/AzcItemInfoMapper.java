package com.javen.mapper;

import java.util.List;

import com.javen.model.AzcItemInfo;

public interface AzcItemInfoMapper {

	void insertAzcItemList(List<AzcItemInfo> azcitemList);

	AzcItemInfo selectByPrimaryKey(String string);

}
