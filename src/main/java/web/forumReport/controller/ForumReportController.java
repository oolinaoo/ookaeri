package web.forumReport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import core.Core;
import web.forumReport.entity.ForumReport;
import web.forumReport.mapper.ForumReportMapper;

@Controller
@RequestMapping("forumReport")
public class ForumReportController {
	
	@Autowired
	private ForumReportMapper mapper;
	
	@GetMapping("listAll")
	@ResponseBody
	public List<ForumReport> listAll() { 
		List<ForumReport> report = mapper.listAll();
		return report;
	}
	
	@PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core add(@RequestBody ForumReport report) {
		Core core = new Core();
		core.setSuccess(mapper.add(report) > 0);
		return core;
	}
	
	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core update(@RequestBody ForumReport report) {
		Core core = new Core();
		core.setSuccess(mapper.update(report) > 0);
		return core;
	}

}
