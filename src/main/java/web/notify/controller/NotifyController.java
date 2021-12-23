package web.notify.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.notify.mapper.NotifyMapper;
@Controller
@RequestMapping("notify")
public class NotifyController {
	
	@Autowired
	private NotifyMapper mapper;
	
	@GetMapping(path = "listNotify")
	@ResponseBody
	public List<String> listNotify(HttpSession session) throws IndexOutOfBoundsException {
		 System.out.println("有進入");
		 String memAcct =(String) session.getAttribute("memAcct");
		 List<String> list = new ArrayList <String>() ;
		 List<String> list2 = mapper.listNotifyMsg(memAcct) ;
		 List<String> list3 = mapper.listNotifyTime(memAcct) ;
		for(int i =0; i<=list2.size();i++ ) {
			try {
				String str = list2.get(i)+" "+list3.get(i);
				list.add(str);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("超過索引值");
			}
		}
		return list; 
	}
	
	@GetMapping(path = "countNotifyState")
	@ResponseBody
	public Integer countNotifyState(HttpSession session) {
		System.out.println("有進入");
		String memAcct =(String) session.getAttribute("memAcct");
		final Integer countNotifyState = mapper.countNotifyState(memAcct);
		System.out.println(countNotifyState);
		return countNotifyState; 
	}
	
	@GetMapping(path = "updateNotifyState")
	@ResponseBody
	public Integer updateNotifyState(HttpSession session) {
		System.out.println("有進入");
		String memAcct =(String) session.getAttribute("memAcct");
		final Integer updateNotifyState = mapper.updateNotifyState(memAcct);
		System.out.println(updateNotifyState);
		return updateNotifyState; 
	}
	
	@GetMapping(path = "deleteNotify")
	@ResponseBody
	public Integer deleteNotify(HttpSession session) {
		System.out.println("有進入");
		String memAcct =(String) session.getAttribute("memAcct");
		final Integer deleteNotify = mapper.deleteNotify(memAcct);
		System.out.println(deleteNotify);
		return deleteNotify; 
	}
}
