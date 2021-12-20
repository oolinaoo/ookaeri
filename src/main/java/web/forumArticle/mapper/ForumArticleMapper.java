package web.forumArticle.mapper;

import java.util.List;

import web.forumArticle.entity.ForumArticle;

public interface ForumArticleMapper {
	
	List<ForumArticle> listAll();
	List<ForumArticle> artTypeJoinType();
	List<ForumArticle> artJoinTypeJoinMsg();
	List<ForumArticle> artJoinMsg();
	List<ForumArticle> typeList(ForumArticle article);
	List<ForumArticle> artNoJoinMsg(ForumArticle article);
	Integer add(ForumArticle article);
	Integer update(ForumArticle article);

}
