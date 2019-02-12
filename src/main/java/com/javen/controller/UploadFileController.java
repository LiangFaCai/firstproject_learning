package com.javen.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class UploadFileController {
	
	//上传页面
	@RequestMapping("/uploadPage")
	public String uploadPage() {
		
		return "upload";
	}
	//上传
	@RequestMapping("/fileupload")
	public String uploadFile(HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddHHmmssSS");
		String res = dateFormat.format(new Date());
		//上传的根路径
		String rootPath  = request.getSession().getServletContext().getRealPath("resource/uploads/");
		
		 // 原始名称
        String originalFileName = file.getOriginalFilename();
        // 新文件名
        String newFileName = "sliver" + res + originalFileName.substring(originalFileName.lastIndexOf("."));
        // 创建年月文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1));

        // 新文件--上传的路径
        File newFile = new File(rootPath + File.separator + dateDirs + File.separator + newFileName);
        // 判断目标文件所在目录是否存在
        if( !newFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile+"--");
        // 将内存中的数据写入磁盘
        file.transferTo(newFile);
        // 完整的url
        String fileUrl = date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH)+1) + "/" + newFileName;
        System.out.println(fileUrl);
        return  "index";
	}
	
	@RequestMapping("/down")  
    public void down(HttpServletRequest request,HttpServletResponse response) throws Exception{  
       
        String fileName = request.getSession().getServletContext().getRealPath("resource/uploads/")+File.separator+"2019"+File.separator+"2"+File.separator+"sliver20190212190638271.jpg";  
        System.out.println(fileName);
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));  
          
        String filename = "下载文件.jpg";  
        
        filename = URLEncoder.encode(filename,"UTF-8");  
        
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
            
        response.setContentType("multipart/form-data");   
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();  
    }  
	
}
