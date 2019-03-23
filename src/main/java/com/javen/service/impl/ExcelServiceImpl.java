package com.javen.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javen.mapper.ExcelMapper;
import com.javen.model.User;
import com.javen.service.ExcelService;


@Service
public class ExcelServiceImpl implements ExcelService {

	
	@Autowired
	private ExcelMapper excelMapper;
	
	@Override
	public List<User> queryUserAll() {
		// TODO Auto-generated method stub
		return excelMapper.selectUserList();
	}

	/**
	 * 文件的导出
	 */
	@Override
	public void exportExcel(List<User> userList, OutputStream outputStream) throws IOException {
		//1.创建工作簿
        HSSFWorkbook hwb =new HSSFWorkbook();
        //1.1创建合并单元格
        //CellRangeAddress cellRangeAddress =new CellRangeAddress(0,0,0,4);
        //2.创建工作表
        HSSFSheet sheet = hwb.createSheet("用户信息表");
        //2.1添加合并单元格
        //sheet.addMergedRegion(cellRangeAddress);
        //3.1创建第一行及单元格
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("用户信息");
        //3.2创建第二行及单元格
        HSSFRow row2 = sheet.createRow(1);
        String[] row2Cell = {"编号","姓名","性别","年龄","部门名称"};
        for (int i =0 ; i < row2Cell.length ; i++ ){
            row2.createCell(i).setCellValue(row2Cell[i]);
        }
        //3.3创建第三行及单元格
        if(userList!= null && userList.size()>0){
            for(int j=0 ; j<userList.size() ;j++){
                HSSFRow rowUser = sheet.createRow(j+2);
                rowUser.createCell(0).setCellValue(userList.get(j).getId());
                rowUser.createCell(1).setCellValue(userList.get(j).getName());
                rowUser.createCell(2).setCellValue(userList.get(j).getSex());
                rowUser.createCell(3).setCellValue(userList.get(j).getAge());
                //rowUser.createCell(4).setCellValue(userList.get(j).getId());
            }
        }
        //5.输出
        hwb.write(outputStream);
		
	}

	
	/**
	 * 文件的导入
	 */
	@Override
	public void importExcel(MultipartFile userExcel) throws IOException, EncryptedDocumentException, InvalidFormatException {
		//将文件转换成流  -----获取输入流
        InputStream inputStream = userExcel.getInputStream();
        //通过文件的输入流 ----工作簿工厂类 创建读取工作簿
        Workbook workbook = WorkbookFactory.create(inputStream);
        //获取第一页工作表
        Sheet sheet = workbook.getSheetAt(0);
        //获取表的总行数
        int rows=sheet.getPhysicalNumberOfRows();
        //创建一个集合
        List<User> userList = new ArrayList<User>();
        //如果行数大于2行
        if(rows>2){
            //获取单元格---思路--先定位有多少行再定位每一行的列的单元格的值
            for (int i = 2; i < rows; i++) {
                Row row = sheet.getRow(i);
                //创建对象用来装载值
                User user =new User();
               //下面都是从行的单元格中获取数据的，将值存入对象中
                try {
                    String id = row.getCell(0).getStringCellValue();
                    user.setId(Integer.parseInt(id));
                } catch (IllegalStateException e) {
                    int id=(int)row.getCell(0).getNumericCellValue();
                    user.setId(id);
                }
                String name = row.getCell(1).getStringCellValue();
                user.setName(name);
                 
                String sex = row.getCell(2).getStringCellValue();
                user.setSex(sex);
                try {
                    String age = row.getCell(3).getStringCellValue();
                    user.setAge(Integer.parseInt(age));
                } catch (IllegalStateException e) {
                    int age=(int)row.getCell(3).getNumericCellValue();
                    user.setAge(age);
                }
                //设值将对象存入集合
                user.setDeptname("kafa");
                userList.add(user);
            }
        }
        //批量添加对象到数据库中
        excelMapper.addUser(userList);//方法
        inputStream.close();
    }
		
}
