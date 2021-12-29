package web.mem.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.mem.entity.MemFacVO;
import web.mem.entity.MemPackVO;
import web.mem.mapper.MemMsgMapper;
import web.payment.entity.ManagementPaymentVO;

@Controller
@RequestMapping("mem")
public class MemberController {
	
	@Autowired
	private MemMsgMapper mapper;
	
	
	/* https://stackoverflow.com/questions/56824338/missingservletrequestparameterexception-required-string-parameter-cinclient-i 
	 * -> @RequestParam is used for query parameters, like: http://localhost:8080/blogs/save?cinClient=myValue
	 * 所以這個annotation應該只能用在GET(?)
	 */
	@GetMapping("memJoinPack")
	@ResponseBody
	public List<MemPackVO> memJoinPack(@RequestParam(value = "memAcct") String memAcct){
		List<MemPackVO> list = mapper.memJoinPack(memAcct);
		return list;
	}
	
	@GetMapping("memManagePay")
	@ResponseBody
	public List<ManagementPaymentVO> memManagePay(@RequestParam(value = "memAcct") String memAcct){
		List<ManagementPaymentVO> list = mapper.memManagePay(memAcct);
		return list;
	}
	
	@GetMapping("facJoinFacHist")
	@ResponseBody
	public List<MemFacVO> facJoinFacHist(@RequestParam(value = "memAcct") String memAcct){
		List<MemFacVO> list = mapper.facJoinFacHist(memAcct);
		return list;
	}
	
	@GetMapping("updateArtState")
	@ResponseBody
	public Integer updateArtState(@Param("memAcct") String memAcct, 
			   @Param("forArtNo") Integer forArtNo) {
		Integer infectedRows = mapper.updateArtState(memAcct, forArtNo);
		return infectedRows;
	}
	
}
