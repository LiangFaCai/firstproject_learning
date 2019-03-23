package com.javen.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.javen.mapper.AzcItemInfoMapper;
import com.javen.model.AzcItemInfo;
import com.javen.model.ExcelBean;
import com.javen.model.ExcelUtil;
import com.javen.service.AzcItemInfoService;

public class AzcItemInfoServiceImpl implements AzcItemInfoService {
	
	@Autowired
	private AzcItemInfoMapper azcItemInfoMapper;
	
	
	
	@Override
    public void importExcelInfo(InputStream in, MultipartFile file) {
 
        try{
            List<List<Object>> listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            List<AzcItemInfo> azcitemList = new ArrayList<AzcItemInfo>();
            //����listob���ݣ������ݷŵ�List��
            for (int i = 0; i < listob.size(); i++) {
                List<Object> ob = listob.get(i);
                AzcItemInfo azcItemInfo =new AzcItemInfo();
                azcItemInfo.setId(String.valueOf(ob.get(0)));
                azcItemInfo.setName(String.valueOf(ob.get(1)));
                azcItemInfo.setItemurl(String.valueOf(ob.get(2)));
                azcItemInfo.setClassfy(String.valueOf(ob.get(3)));
                azcItemInfo.setZrf(String.valueOf(ob.get(4)));
                azcItemInfo.setDate(String.valueOf(ob.get(5)));
 
                azcitemList.add(azcItemInfo);
            }
            azcItemInfoMapper.insertAzcItemList(azcitemList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


	@Override
    public XSSFWorkbook exportExcelInfo(String idList) {
        XSSFWorkbook xssfWorkbook=null;
        try {
            //����ID��������
            String[] list = idList.split(",");
            List<AzcItemInfo> azcItemInfoList = new ArrayList<AzcItemInfo>();
            for(int i=0;i<list.length;i++){
                AzcItemInfo azcItemInfo = azcItemInfoMapper.selectByPrimaryKey(list[i]);
                azcItemInfoList.add(azcItemInfo);
            }
            List<ExcelBean> excel=new ArrayList<>();
            Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
 
            //���ñ�����
            excel.add(new ExcelBean("ID","id",0));
            excel.add(new ExcelBean("����","name",0));
            excel.add(new ExcelBean("itemurl","itemurl",0));
            excel.add(new ExcelBean("classfy","classfy",0));
            excel.add(new ExcelBean("zrf","zrf",0));
            excel.add(new ExcelBean("date","date",0));
            map.put(0, excel);
            String sheetName ="AzcItemInfo";
            xssfWorkbook = ExcelUtil.createExcelFile(AzcItemInfo.class, azcItemInfoList, map, sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xssfWorkbook;
    }
}
