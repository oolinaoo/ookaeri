package web.forumArticle.controller;

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
import web.forumArticle.entity.ForumArticle;
import web.forumArticle.mapper.ForumArticleMapper;
import web.forumArticle.service.ForumArticleService;

@Controller
@RequestMapping("forumArticle")
public class ForumArticleController {
	
	@Autowired
	private ForumArticleMapper mapper;
	@Autowired
	private ForumArticleService service;
	
	@GetMapping("listAll")
	@ResponseBody
	public List<ForumArticle> listAll() {
		List<ForumArticle> article = service.listAll();
		return article;
	}
	
	@GetMapping("artTypeJoinType")
	@ResponseBody
	public List<ForumArticle> artTypeJoinType() {
		List<ForumArticle> article = mapper.artTypeJoinType();
		return article;
	}
	
	@GetMapping("artJoinTypeJoinMsg")
	@ResponseBody
	public List<ForumArticle> artJoinTypeJoinMsg() {
		List<ForumArticle> article = mapper.artJoinTypeJoinMsg();
		return article;
	}
	
	@GetMapping("artJoinMsg")
	@ResponseBody
	public List<ForumArticle> artJoinMsg() {
		List<ForumArticle> article = mapper.artJoinMsg();
		return article;
	}
	
	@PostMapping(path = "typeList", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ForumArticle> typeList(@RequestBody ForumArticle article) {
		List<ForumArticle> theArticle = mapper.typeList(article);
		return theArticle;
	}
	
	@PostMapping(path = "artNoJoinMsg", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ForumArticle> artNoJoinMsg(@RequestBody ForumArticle article) {
		List<ForumArticle> theArticle = mapper.artNoJoinMsg(article);
		return theArticle;
	}
	
	@PostMapping(path = "listByMem", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ForumArticle> listByMem(@RequestBody ForumArticle article) {
		List<ForumArticle> theArticle = mapper.listByMem(article);
		return theArticle;
	}
	
	@PostMapping(path = "findByArtNo", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ForumArticle findByArtNo(@RequestBody ForumArticle article) {
		ForumArticle theArticle = mapper.findByArtNo(article);
		return theArticle;
	}
	
	@PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core add(@RequestBody ForumArticle article) {
		Core core = new Core();
		core.setSuccess(mapper.add(article) > 0);
		return core;
	}
	
	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core update(@RequestBody ForumArticle article) {
		Core core = new Core();
		core.setSuccess(mapper.update(article) > 0);
		return core;
	}
	
	@PostMapping(path = "updateViews", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core updateViews(@RequestBody ForumArticle article) {
		Core core = new Core();
		core.setSuccess(mapper.updateViews(article) > 0);
		return core;
	}

}
