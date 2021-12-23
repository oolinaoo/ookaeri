package web.news.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import web.news.dao.NewsDAO;
import web.news.entity.News;


@Repository
public class NewsDAOImpl implements NewsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void add(News news) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(News news) {
		Session session = sessionFactory.getCurrentSession();
		
	}

	@Override
	public News findByPk(Integer newsNo) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(News.class, newsNo);
		
	}

	@Override
	public List<News> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Query<News> query = session.createQuery("FROM NEWS", News.class); // how do i get rid of this?
		List<News> news = query.list();
		return news;
	}

}
