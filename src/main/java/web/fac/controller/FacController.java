package web.fac.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.fac.entity.FacVO;
import web.fac.mapper.FacMapper;

@Controller
@RequestMapping("fac")
//@MultipartConfig(fileSizeThreshold = 45 * 1024 * 1024, maxFileSize = 45 * 1024 * 1024, maxRequestSize = 45 * 5 * 1024
//* 1024)
public class FacController {
	
	@Autowired
	private FacMapper mapper;
	
	@PostMapping(path = "listAllFac")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<FacVO> listAllFac() {
		final List<FacVO> list = mapper.listAll();
		return list;
	}
		
	@RequestMapping(path = "facPhotoByFacNo", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public void facPhotoByFacNo (@RequestParam(value = "facNo") Integer facNo, HttpServletResponse res) {
		try {
			ServletOutputStream os = res.getOutputStream();
			FacVO facVO = mapper.photoByFacno(facNo);
			
			byte[] photo = facVO.getFacPhoto();
		
			res.setContentType("image/jpeg");
			res.setCharacterEncoding("UTF-8");
			res.setContentLength(photo.length);
			os.write(photo);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	
}
