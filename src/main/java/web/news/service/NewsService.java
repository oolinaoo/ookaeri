package web.news.service;

import java.util.List;

import web.news.entity.News;

public interface NewsService {
	
	void add(News news);
	void update(News news);
	News findByPk(Integer newsNo);
	List<News> getAll();

}
