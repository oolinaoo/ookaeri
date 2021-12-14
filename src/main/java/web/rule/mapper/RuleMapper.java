package web.rule.mapper;

import java.util.List;

import web.rule.entity.Rule;

public interface RuleMapper {
	
	List<Rule> listAll();
	Integer update(Rule rule);

}
