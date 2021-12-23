package web.fac.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.fac.entity.FacVO;
import web.fac.mapper.FacMapper;

@Controller
@RequestMapping("fac")
@MultipartConfig(fileSizeThreshold = 45 * 1024 * 1024, maxFileSize = 45 * 1024 * 1024, maxRequestSize = 45 * 5 * 1024
* 1024)
public class FacController {
	
	@Autowired
	private FacMapper mapper;
	public Integer facInsertNo;
	
	// 全部公設基本資料
	@PostMapping(path = "listAllFac")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<FacVO> listAllFac() {
		final List<FacVO> list = mapper.listAll();
		return list;
	}
	
	// 單一公設基本資料
	@PostMapping(path = "listOneFac", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public FacVO listOneFac(@RequestBody FacVO facVO) {
		Integer facNo = facVO.getFacNo();
		final FacVO oneFacVO = mapper.listOne(facNo);
		return oneFacVO;
	}
	
	// 公設開放日，回傳串接之後的字串
	@GetMapping(path = "listAllDate")
	@ResponseBody
	@Transactional(readOnly = true)
	public String listAllDate(@RequestParam(value = "facNo") Integer facNo) {
		
		final String[] dateList = mapper.listAllDate(facNo);
		String allDate ="";		
		
		for(int i = 0; i < dateList.length ;i++) {
			allDate = allDate + dateList[i] + " ";
		}
		
		return allDate;
	}
	
	// 公設開放時間VO
	@PostMapping(path = "openTime", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<FacVO> openTime(@RequestBody FacVO facVO){
		Integer facNo = facVO.getFacNo();
		final List<FacVO> list = mapper.listOpenTime(facNo);
		return list;
	}
	
	// 公設開放時間，回傳串接之後的字串
	@GetMapping(path = "listAllTime")
	@ResponseBody
	@Transactional(readOnly = true)
	public String listAllTime(@RequestParam(value = "facNo") Integer facNo) {
		
		final Integer startTime = mapper.listStartTime(facNo);
		final Integer endTime = mapper.listEndTime(facNo);
		
		String openTime = startTime + ":00 ~ " + endTime + ":00";
		
		return openTime;
	}
	
	// 後臺公設列表圖片顯示	
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
	
	// 後臺公設地圖
	@GetMapping(path = "listByFloorAddr", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Transactional(readOnly = true)
	public List<FacVO> listByFloorAddr(@RequestParam(value = "facAddr") String facAddr) {
		final List<FacVO> list = mapper.listByAddr(facAddr);
		return list;
	}
	
	//	新增公設
	@PostMapping(path = "addNewFac", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer addNewFac(@RequestBody FacVO facVO) {
		final int insert = mapper.insertNewFac(facVO);
		if(insert == 1) {
			facInsertNo = mapper.findNewestFacNo();	
		}
		else {
			return 0;	
		}
		return facInsertNo;
	}
	
	// 新增公設開放日
	@GetMapping(path = "addNewFacDate", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer addNewFacDate(@RequestParam(value = "facNo") Integer facNo,
			 @RequestParam(value = "facOpenDate") String facOpenDate) {
		int insert = mapper.insertNewFacDate(facNo, facOpenDate);
		return insert;
	}
	
	// 新增公設開放時間
	@GetMapping(path = "addNewFacTime", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer addNewFacTime(@RequestParam(value = "facNo") Integer facNo,
			@RequestParam(value = "facOpenTime") String facOpenTime,
			@RequestParam(value = "startTime") Integer startTime,
			@RequestParam(value = "endTime") Integer endTime) {
		int insert = mapper.insertNewFacTime(facNo, facOpenTime, startTime, endTime);		
		return insert;
	}
	
	// 點選編及公設時，彈窗列出公設資料
	@PostMapping(path = "listFacDetail", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<FacVO> listFacDetail(@RequestBody FacVO facVO){
		Integer facNo = facVO.getFacNo();
		final List<FacVO> list = mapper.facDetail(facNo);
		return list;
	}
	
	// 公設更新
	@PostMapping(path = "facEdit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer facEdit(@RequestBody FacVO facVO) {
		
		final Integer edit = mapper.facDetailUpdate(facVO);
		return edit;
	}
	
	// 刪除公設的開放時間和日期
	@PostMapping(path = "facDateTimeDelete", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer facDateTimeDelete(@RequestBody FacVO facVO) {
		Integer facNo = facVO.getFacNo();
		Integer delete = mapper.deleteDate(facNo);
		delete = mapper.deleteTime(facNo);
		return delete;
	}
}
