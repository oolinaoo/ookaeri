package web.fachist.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.fachist.entity.FachistVO;
import web.fachist.mapper.FachistMapper;

@Controller
@RequestMapping("fachist")
public class FachistController{
	String memAcct = "";

	@Autowired
	private FachistMapper mapper;
	
	// 公設租借
	@PostMapping(path = "facReserve", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer facReserve(@RequestBody FachistVO fachistVO) {
		final int insert = mapper.insertFacReserve(fachistVO);
		return insert;		// insert = 1
	}
	
	// 列出全部租借紀錄，後臺用
	@PostMapping(path = "fachistView")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<FachistVO> fachistView() {
		final List<FachistVO> list = mapper.listViewFacMem();
		return list;
	}
	
	@PostMapping(path = "fachistViewMemacct")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<FachistVO> fachistViewMemacct(HttpSession session){
		memAcct =(String) session.getAttribute("memAcct");

		final List<FachistVO> list = mapper.listViewMemHist(memAcct);
		return list;
	}
	
	
	// 列出該設施該日各時段的租借紀錄
	@PostMapping(path = "facResTimeHist", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Transactional(readOnly = true)
	public List<FachistVO> facResTimeHist(@RequestBody FachistVO fachistVO) {
		Integer facNo = fachistVO.getFacNo();
		Date hisDate = fachistVO.getHistDate();
		final List<FachistVO> list = mapper.findHistTimeAmountByDate(facNo, hisDate);
		return list;
	}
	
	
	// 列出某公設該月各天的租借狀況
	@RequestMapping(path = "facResDateHist", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Map> facResDateHist(
			@RequestParam(value = "facNo") Integer facNo, @RequestParam(value = "month") Integer month, @RequestParam(value = "year") Integer year){
		List<Map> facMonth = mapper.listFacDateHistByMonth(facNo, month, year);
		return facMonth;
	}
	
	// 取消租借公設
	@PostMapping(path = "fachistDelete", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer fachistDelete(@RequestBody FachistVO fachistVO) {
		final int delete = mapper.deleteFachist(fachistVO);
		return delete;
	}
	
	// 更新預約公設人數
	@PostMapping(path = "fachistAmtUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String fachistAmtUpdate(@RequestBody FachistVO fachistVO) {
		String updateSuccess = "";
		final int update = mapper.updateFachist(fachistVO);
		if(update == 1) {
			updateSuccess = "人數更新成功！";
		} else {
			updateSuccess = "人數更新失敗！";
		}
		return updateSuccess;
	}
	
	
	
}
