package com.javen.service;

import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface AzcItemInfoService {

	void importExcelInfo(InputStream in, MultipartFile file);

	XSSFWorkbook exportExcelInfo(String idList);

}
