package web.forumCollections.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import core.Core;
import web.forumCollections.entity.ForumCollectionsBo;
import web.forumCollections.mapper.ForumCollectionsMapper;

@Controller
@RequestMapping("forumCollections")
public class ForumCollectionsController {
	
	@Autowired
	private ForumCollectionsMapper mapper;
	
	@PostMapping(path = "findByMem", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ForumCollectionsBo> findByMem(HttpSession session) {
		String memAcct = (String)session.getAttribute("memAcct");
		List<ForumCollectionsBo> collections = mapper.findByMem(memAcct);
		return collections;
	}
	
	@PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core add(@RequestBody ForumCollectionsBo collectionsBo) {
		Core core = new Core();
		core.setSuccess(mapper.add(collectionsBo) > 0);
		return core;
	}
	
	@PostMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core delete(@RequestBody ForumCollectionsBo collectionsBo) {
		String memAcct = collectionsBo.getMemAcct();
		Integer forArtNo = collectionsBo.getForArtNo();
		Core core = new Core();
		core.setSuccess(mapper.delete(memAcct, forArtNo) > 0);
		return core;
	}

}
