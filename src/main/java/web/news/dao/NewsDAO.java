package web.news.dao;

import java.util.List;

import web.news.entity.News;

public interface NewsDAO {
	
	void add(News news);
	void update(News news);
	News findByPk(Integer newsNo);
	List<News> getAll();

}
