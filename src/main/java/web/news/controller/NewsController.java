package web.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.news.entity.News;
import web.news.service.NewsService;

@RestController
@RequestMapping("news")
public class NewsController {

	@Autowired
	private NewsService service;

	@GetMapping("findByPk")
	public News findByPk(Integer newsNo) {
		News item = service.findByPk(newsNo);
		return item;
	}

}
