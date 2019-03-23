package com.javen.controller;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javen.model.User;
import com.javen.service.ExcelService;
/**
 * excel�ļ��ĵ��뵼��
 * ���ý���ѧϰ-----https://www.cnblogs.com/colourless/p/10012253.html
 * @author hand
 *
 */
@Controller
@RequestMapping("/user")
public class ExportExcelControlller {

	@Autowired
	private ExcelService userService;
	
	/**
	 * ��ת��ҳ��
	 * @return
	 */
	@RequestMapping("/import")
	public String test() {
		
	   return "import";
	}
	
	/**
	 * ��ȡ�û�json
	 * @return
	 */
	@RequestMapping("/queryUserAll")
	@ResponseBody
	public List<User> getList(){
		List<User> queryUserAll = userService.queryUserAll();
		return queryUserAll;
	}
	
	/**
	 * excel�ļ��ĵ���
	 * @param request
	 * @param response
	 */
    @RequestMapping("/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletRequest request,HttpServletResponse response){
        try {
            //��ȡ����Դ
            List<User> userList = userService.queryUserAll();
            //����excel
            response.setHeader("Content-Disposition","attachment;filename="+new String("�û���Ϣ.xls".getBytes(),"ISO-8859-1"));
            response.setContentType("application/x-excel;charset=UTF-8");
            OutputStream outputStream = response.getOutputStream();
            //����
            userService.exportExcel(userList,outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * excel�ļ��ĵ���
     * @param userExcel
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/importExcel")
    //@ResponseBody
    public String importExcel(MultipartFile userExcel,HttpServletRequest request,HttpSession session){
        if(userExcel == null){
            session.setAttribute("excelName", "δ�ϴ��ļ����ϴ�ʧ�ܣ�");
            return "redirect:import";
        }
        String userExcelFileName = userExcel.getOriginalFilename();
        if(!userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
            session.setAttribute("excelName", "�ļ���ʽ����ȷ����ʹ��.xls��.xlsx��׺���ĵ�������ʧ�ܣ�");
            return "redirect:import";
        }
        //����
        try {
			userService.importExcel(userExcel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        session.setAttribute("excelName", "����ɹ���");
        return "redirect:import";
    }
    
    
    
    
}
