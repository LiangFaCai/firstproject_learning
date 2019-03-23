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
	 * �ļ��ĵ���
	 */
	@Override
	public void exportExcel(List<User> userList, OutputStream outputStream) throws IOException {
		//1.����������
        HSSFWorkbook hwb =new HSSFWorkbook();
        //1.1�����ϲ���Ԫ��
        //CellRangeAddress cellRangeAddress =new CellRangeAddress(0,0,0,4);
        //2.����������
        HSSFSheet sheet = hwb.createSheet("�û���Ϣ��");
        //2.1��Ӻϲ���Ԫ��
        //sheet.addMergedRegion(cellRangeAddress);
        //3.1������һ�м���Ԫ��
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("�û���Ϣ");
        //3.2�����ڶ��м���Ԫ��
        HSSFRow row2 = sheet.createRow(1);
        String[] row2Cell = {"���","����","�Ա�","����","��������"};
        for (int i =0 ; i < row2Cell.length ; i++ ){
            row2.createCell(i).setCellValue(row2Cell[i]);
        }
        //3.3���������м���Ԫ��
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
        //5.���
        hwb.write(outputStream);
		
	}

	
	/**
	 * �ļ��ĵ���
	 */
	@Override
	public void importExcel(MultipartFile userExcel) throws IOException, EncryptedDocumentException, InvalidFormatException {
		//���ļ�ת������  -----��ȡ������
        InputStream inputStream = userExcel.getInputStream();
        //ͨ���ļ��������� ----������������ ������ȡ������
        Workbook workbook = WorkbookFactory.create(inputStream);
        //��ȡ��һҳ������
        Sheet sheet = workbook.getSheetAt(0);
        //��ȡ���������
        int rows=sheet.getPhysicalNumberOfRows();
        //����һ������
        List<User> userList = new ArrayList<User>();
        //�����������2��
        if(rows>2){
            //��ȡ��Ԫ��---˼·--�ȶ�λ�ж������ٶ�λÿһ�е��еĵ�Ԫ���ֵ
            for (int i = 2; i < rows; i++) {
                Row row = sheet.getRow(i);
                //������������װ��ֵ
                User user =new User();
               //���涼�Ǵ��еĵ�Ԫ���л�ȡ���ݵģ���ֵ���������
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
                //��ֵ��������뼯��
                user.setDeptname("kafa");
                userList.add(user);
            }
        }
        //������Ӷ������ݿ���
        excelMapper.addUser(userList);//����
        inputStream.close();
    }
		
}
