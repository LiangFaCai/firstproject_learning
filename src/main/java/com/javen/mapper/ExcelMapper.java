package com.javen.mapper;

import java.util.List;

import com.javen.model.User;

public interface ExcelMapper {

	List<User> selectUserList();

	void addUser(List<User> userList);
}
