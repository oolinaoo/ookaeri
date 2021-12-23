package web.forumArticle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.forumArticle.entity.ForumArticle;
import web.forumArticle.mapper.ForumArticleMapper;
import web.forumArticle.service.ForumArticleService;

@Service
@Transactional
public class ForumArticleServiceImpl implements ForumArticleService{
	
	@Autowired
	private ForumArticleMapper mapper;
	
	public List<ForumArticle> listAll() {
		return mapper.listAll();
	}; 
	
	
}
