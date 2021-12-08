package web.rule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.rule.entity.Rule;
import web.rule.mapper.RuleMapper;

@Controller
@RequestMapping("rule")
public class RuleController {
	
	@Autowired
	private RuleMapper mapper;
	
	@GetMapping("t1")
	public String t1(Model model) {
		final List<Rule> list = mapper.listAll();
		model.addAttribute("ruleList", list);
		return "result"; 
	}

}
