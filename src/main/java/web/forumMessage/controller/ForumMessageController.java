package web.forumMessage.controller;

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
import web.forumArticle.mapper.ForumArticleMapper;
import web.forumMessage.entity.ForumMessage;
import web.forumMessage.entity.ForumMessageBo;
import web.forumMessage.mapper.ForumMessageMapper;

@Controller
@RequestMapping("forumMessage")
public class ForumMessageController {
	
	@Autowired
	private ForumMessageMapper mapper;
	
	@GetMapping("listAll")
	@ResponseBody
	public List<ForumMessage> listAll() {
		List<ForumMessage> message = mapper.listAll();
		return message;
	}
	
	@GetMapping("msgJoinArtJoinType")
	@ResponseBody
	public List<ForumMessageBo> msgJoinArtJoinType() {
		List<ForumMessageBo> message = mapper.msgJoinArtJoinType();
		return message;
	}
	
	@PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core add(@RequestBody ForumMessage message) {
		Core core = new Core();
		core.setSuccess(mapper.add(message) > 0);
		return core;
	}
	
	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core update(@RequestBody ForumMessage message) {
		Core core = new Core();
		core.setSuccess(mapper.update(message) > 0);
		return core;
	}

}
