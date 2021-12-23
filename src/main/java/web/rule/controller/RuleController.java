package web.rule.controller;

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
import web.news.entity.News;
import web.rule.entity.Rule;
import web.rule.mapper.RuleMapper;

@Controller
@RequestMapping("rule")
public class RuleController {
	
	@Autowired
	private RuleMapper mapper;
	
//	//Test
//	@GetMapping("t1")
//	public String t1(Model model) {
//		final List<Rule> list = mapper.listAll();
//		model.addAttribute("ruleList", list);
//		return "result"; 
//	}
	
	@GetMapping("listAll")
	@ResponseBody
	public List<Rule> listAll() {
		List<Rule> rule = mapper.listAll();
		return rule;
	}
	
	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Core update(@RequestBody Rule rule) {
		Core core = new Core();
		int count = mapper.update(rule);
		core.setSuccess(count > 0);
		return core;
	}

}
