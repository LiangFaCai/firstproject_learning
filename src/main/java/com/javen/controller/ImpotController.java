//package com.javen.controller;
//
//import java.io.BufferedOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.runner.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import com.alibaba.fastjson.JSONObject;
//import com.javen.service.AzcItemInfoService;
//
//import javax.servlet.http.HttpServletResponse;
//
//public class ImpotController {
//	@Autowired
//	private AzcItemInfoService	azcItemInfoService;
//	
//	
//	@RequestMapping("/import")
//    @ResponseBody
//    public Object impotr(@RequestParam("file") MultipartFile file){
//        Result result = new Result();
//        try {
//            InputStream in = file.getInputStream();
// 
//            azcItemInfoService.importExcelInfo(in,file);
//            in.close();
//            result.setMsg("ok");
//            result.setStatue(200);
//        }catch (Exception e){
//            result.setMsg("err");
//            result.setStatue(500);
//        }
//        return JSONObject.toJSON(result);
//    }
//
//	
//	@RequestMapping("/export")
//    public void export(HttpServletResponse response,String idList) {
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition", "attachment;filename=AzcItemInfo.xlsx;charset=UTF-8");
//        XSSFWorkbook workbook = azcItemInfoService.exportExcelInfo(idList);
//        try {
//            OutputStream output  = response.getOutputStream();
//            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
//            workbook.write(bufferedOutPut);
//            bufferedOutPut.flush();
//            bufferedOutPut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
