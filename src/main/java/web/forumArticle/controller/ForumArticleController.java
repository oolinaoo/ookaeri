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
import web.news.entity.News;
import web.rule.entity.Rule;
import web.rule.mapper.RuleMapper;

@Controller
@RequestMapping("forumArticle")
public class ForumArticleController {
	
	@Autowired
	private ForumArticleMapper mapper;
	
	@GetMapping("listAll")
	@ResponseBody
	public List<ForumArticle> listAll() {
		List<ForumArticle> article = mapper.listAll();
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

}
