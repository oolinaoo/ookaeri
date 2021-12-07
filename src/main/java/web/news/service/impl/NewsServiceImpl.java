package web.news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.news.dao.NewsDAO;
import web.news.entity.News;
import web.news.service.NewsService;

@Service
@Transactional
public class NewsServiceImpl implements NewsService{

	@Autowired
	private NewsDAO dao;

	@Override
	public void add(News news) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(News news) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<News> getAll() {
		return dao.getAll();
	}

	@Override
	public News findByPk(Integer newsNo) {
		return dao.findByPk(newsNo);
	}
}
