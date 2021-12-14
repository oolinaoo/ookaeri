package web.news.controller;

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
import web.news.entity.News;
import web.news.mapper.NewsMapper;

@Controller
@RequestMapping("news")
public class NewsController {

	@Autowired
	private NewsMapper mapper;

	@GetMapping("listAllA")
	@ResponseBody
	public List<News> listAllA() {
		List<News> news = mapper.listAllA();
		return news;
	}
	
	@GetMapping("listAllD")
	@ResponseBody
	public List<News> listAllD() {
		List<News> news = mapper.listAllD();
		return news;
	}
	
	@GetMapping("listAllInAdmin")
	@ResponseBody
	public List<News> listAllInAdmin() {
		List<News> news = mapper.listAllInAdmin();
		return news;
	}

	@PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core add(@RequestBody News news) {
		Core core = new Core();
		core.setSuccess(mapper.add(news) > 0);
		return core;
	}

	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core update(@RequestBody News news) {
		Core core = new Core();
		core.setSuccess(mapper.update(news) > 0);
		return core;
	}

}
