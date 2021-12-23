package web.addr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.addr.entity.AddressVO;
import web.addr.mapper.AddrMapper;

@Controller
@RequestMapping("addr") //請求的網址
public class AddrController {
	
	//自動new一個新物件
	@Autowired
	private AddrMapper mapper;
	
	@GetMapping("listAll") //doGet()的API網址
	@ResponseBody //代表要回傳東西
	public List<AddressVO> listAll(){
		List<AddressVO> addr = mapper.listAll();
		return addr;
	}
	
	@PostMapping("insert")
	@ResponseBody
	public Integer insert(@RequestBody AddressVO addressVO) {
		mapper.insert(addressVO);
		Integer addrNo = addressVO.getAddrNo(); //新增資料，回傳自增主鍵值
		return addrNo;
	
	}
	
	
}
