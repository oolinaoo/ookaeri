package web.forumCollections.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import web.forumCollections.entity.ForumCollectionsBo;

public interface ForumCollectionsMapper {
	
	List<ForumCollectionsBo> findByMem(String memAcct);
	Integer add(ForumCollectionsBo collections);
	Integer delete(@Param("memAcct") String memAcct, @Param("forArtNo") Integer forArtNo);

}
