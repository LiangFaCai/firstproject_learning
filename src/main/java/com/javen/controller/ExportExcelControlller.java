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
 * excel文件的导入导出
 * 引用仅供学习-----https://www.cnblogs.com/colourless/p/10012253.html
 * @author hand
 *
 */
@Controller
@RequestMapping("/user")
public class ExportExcelControlller {

	@Autowired
	private ExcelService userService;
	
	/**
	 * 跳转到页面
	 * @return
	 */
	@RequestMapping("/import")
	public String test() {
		
	   return "import";
	}
	
	/**
	 * 获取用户json
	 * @return
	 */
	@RequestMapping("/queryUserAll")
	@ResponseBody
	public List<User> getList(){
		List<User> queryUserAll = userService.queryUserAll();
		return queryUserAll;
	}
	
	/**
	 * excel文件的导出
	 * @param request
	 * @param response
	 */
    @RequestMapping("/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletRequest request,HttpServletResponse response){
        try {
            //获取数据源
            List<User> userList = userService.queryUserAll();
            //导出excel
            response.setHeader("Content-Disposition","attachment;filename="+new String("用户信息.xls".getBytes(),"ISO-8859-1"));
            response.setContentType("application/x-excel;charset=UTF-8");
            OutputStream outputStream = response.getOutputStream();
            //导出
            userService.exportExcel(userList,outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * excel文件的导入
     * @param userExcel
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/importExcel")
    //@ResponseBody
    public String importExcel(MultipartFile userExcel,HttpServletRequest request,HttpSession session){
        if(userExcel == null){
            session.setAttribute("excelName", "未上传文件，上传失败！");
            return "redirect:import";
        }
        String userExcelFileName = userExcel.getOriginalFilename();
        if(!userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
            session.setAttribute("excelName", "文件格式不正确！请使用.xls或.xlsx后缀的文档，导入失败！");
            return "redirect:import";
        }
        //导入
        try {
			userService.importExcel(userExcel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        session.setAttribute("excelName", "导入成功！");
        return "redirect:import";
    }
    
    
    
    
}
