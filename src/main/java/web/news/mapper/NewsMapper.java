package web.news.mapper;

import java.util.List;

import web.news.entity.News;

public interface NewsMapper {
	
	List<News> listAllA();
	List<News> listAllD();
	List<News> listAllInAdmin();
	Integer add(News news);
	Integer update(News news);

}
