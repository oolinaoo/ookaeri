package web.forumType.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import core.Core;
import web.forumType.entity.ForumType;
import web.forumType.mapper.ForumTypeMapper;

@Controller
@RequestMapping("forumType")
public class ForumTypeController {
	
	@Autowired
	private ForumTypeMapper mapper;
	
	@GetMapping("listAll")
	@ResponseBody
	public List<ForumType> listAll() { 
		List<ForumType> type = mapper.listAll();
		return type;
	}
	
	@PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core add(@RequestBody ForumType type) {
		Core core = new Core();
		core.setSuccess(mapper.add(type) > 0);
		return core;
	}
	
	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core update(@RequestBody ForumType type) {
		Core core = new Core();
		core.setSuccess(mapper.update(type) > 0);
		return core;
	}

}
