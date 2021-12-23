package web.pack.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import core.Core;
import web.news.entity.News;
import web.pack.entity.PackageVO;
import web.pack.mapper.PackMapper;
import web.rule.entity.Rule;
import web.rule.mapper.RuleMapper;

@Controller
@RequestMapping("pack")
public class PackController {
	
	@Autowired
	private PackMapper mapper;
	
	
	@GetMapping(path = "listAllPack")
	@ResponseBody
	public List<PackageVO> listAllPack() {
		System.out.println("有進入");
		final List<PackageVO> list = mapper.listAll();
		System.out.println(list);
		return list; 
	}
//	新增資料時地址編號動態產生
	@GetMapping(path = "listAddrNo")
	@ResponseBody
	public List<Integer> listAddrNo() {
		System.out.println("有進入");
		final List<Integer> list = mapper.listAddr();
		System.out.println(list);
		return list; 
	}
	
	@PostMapping(path = "add")
	@ResponseBody
	public Core add(PackageVO packageVO) {
		Integer addrNo = packageVO.getAddrNo();
		String memAcct = mapper.memAcct(addrNo); 
		System.out.println("addrNo:"+addrNo);
		System.out.println("memAcct:"+memAcct);
		Core core = new Core();
		core.setSuccess(mapper.add(packageVO) > 0);
		core.setSuccess(mapper.addNotify(memAcct,addrNo) > 0);
		return core;
	}
	
	@PostMapping(path = "update")
	@ResponseBody
	public Core update( PackageVO packageVO) {
		Core core = new Core();
		core.setSuccess(mapper.update(packageVO) > 0);
		return core;
	}
	
//	@GetMapping(path = "listNotify")
//	@ResponseBody
//	public List<String> listNotify() throws IndexOutOfBoundsException {
//		System.out.println("有進入");
//		 List<String> list = new ArrayList <String>() ;
//		 List<String> list2 = mapper.listNotifyMsg() ;
//		 List<String> list3 = mapper.listNotifyTime() ;
//		for(int i =0; i<=list2.size();i++ ) {
//			try {
//				String str = list2.get(i)+" "+list3.get(i);
//				list.add(str);
//			}catch(IndexOutOfBoundsException e) {
//				System.out.println("超過索引值");
//			}
//		}
//		return list; 
//	}
//	
//	@GetMapping(path = "countNotifyState")
//	@ResponseBody
//	public Integer countNotifyState() {
//		System.out.println("有進入");
//		final Integer countNotifyState = mapper.countNotifyState();
//		System.out.println(countNotifyState);
//		return countNotifyState; 
//	}
//	
//	@GetMapping(path = "updateNotifyState")
//	@ResponseBody
//	public Integer updateNotifyState() {
//		System.out.println("有進入");
//		final Integer updateNotifyState = mapper.updateNotifyState();
//		System.out.println(updateNotifyState);
//		return updateNotifyState; 
//	}
//	
//	@GetMapping(path = "deleteNotify")
//	@ResponseBody
//	public Integer deleteNotify() {
//		System.out.println("有進入");
//		final Integer deleteNotify = mapper.deleteNotify();
//		System.out.println(deleteNotify);
//		return deleteNotify; 
//	}
	

}
