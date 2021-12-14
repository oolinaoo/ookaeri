package web.pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

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
	

}
