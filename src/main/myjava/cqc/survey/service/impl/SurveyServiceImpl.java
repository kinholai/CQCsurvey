package cqc.survey.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cqc.survey.service.SurveyService;

@Service("SurveyServiceImpl")
@Transactional
public class SurveyServiceImpl extends CommonServiceImpl implements
		SurveyService
{
	@SuppressWarnings("finally")
	public HttpServletResponse downloadFile(HttpServletRequest request, 
			HttpServletResponse response, String fileRealname)
	{
		File file = new File(fileRealname);
		InputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
	        response.setContentType("multipart/form-data");			
//			response.setContentType("application/x-msdownload");
	        response.setContentType("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        //2.设置文件头：最后一个参数是设置下载文件名(设置编码,使得正常显示中文文件名)  
	        response.setHeader("Content-Disposition", "attachment;fileName="+new String(file.getName().getBytes("GBK"), "ISO8859-1"));/* + 
	        										file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()));*/
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        bos = new BufferedOutputStream(response.getOutputStream());
	        byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) 
			{
				bos.write(buff, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {		
			try {
				if (bis != null) 
				{
					bis.close();
				}
				if (bos != null) 
				{
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}
	}
}
