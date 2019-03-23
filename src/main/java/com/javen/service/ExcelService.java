package com.javen.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.javen.model.User;


public interface ExcelService {

	List<User> queryUserAll();

	void exportExcel(List<User> userList, OutputStream outputStream) throws IOException;

	void importExcel(MultipartFile userExcel) throws IOException, EncryptedDocumentException, InvalidFormatException;

}
