package web.payment.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import core.Core;
import util.Util;
import web.payment.entity.ManagementPaymentVO;
import web.payment.mapper.ManagementPaymentMapper;
import web.payment.service.PayService;

@Controller
@RequestMapping("payment")
public class PaymentController {
	
	@Autowired
	private ManagementPaymentMapper mapper;
	
	
	//===============設置排程器=================
		
	Timer timer;
	@GetMapping(path = "init")
	@ResponseBody
	public void init() throws ServletException {
	    TimerTask task = new TimerTask(){ 
		        public void run() {
		        	Calendar c = Calendar.getInstance();
					int day = c.get(Calendar.DAY_OF_MONTH);
		
			        if(day ==1) {	
		        		
		        		List<String> listMemAcct= mapper.listMemAcct();
		        		List<Integer> listAddrNo= mapper.listAddrNo();
		        		for(int i =0; i<= listMemAcct.size(); i++) {	        			
		        			String memAcct = listMemAcct.get(i);		        			
	        				Integer addrNo =listAddrNo.get(i);
	        				Integer payAmount = 3600;
							String adminAcct = "gary1";
							Integer payState = 1;
							//設定截止日
							Calendar pd = Calendar.getInstance();
							pd.set(Calendar.DATE,pd.getActualMaximum(Calendar.DATE));//設定每月月底為繳費截止日
							java.util.Date date1 = pd.getTime();
							java.sql.Date payDeadline = new java.sql.Date(date1.getTime());

							//設定期數
							Calendar pp = Calendar.getInstance();	
							Integer payPeriod = (pp.get(Calendar.YEAR)*100) +pp.get(Calendar.MONTH)+1 ;
	        				String str = String.valueOf(payPeriod);
	        				String notifyMessage = "本期"+str+"期管理費為3600";
							ManagementPaymentVO payVO = new ManagementPaymentVO();
							payVO.setMemAcct(memAcct);
							payVO.setAddrNo(addrNo);
							payVO.setPayDeadline(payDeadline);
							payVO.setPayAmount(payAmount);
							payVO.setPayPeriod(payPeriod);
							payVO.setAdminAcct(adminAcct);
							payVO.setPayState(payState);
							mapper.add(payVO);
							mapper.addNotify(memAcct, addrNo, notifyMessage);
		        			
		        		}
		        	
					}	
			        
		        }
		 };
	    
	    timer = new Timer(); 
	    Calendar cal = new GregorianCalendar(2021, Calendar.DECEMBER, 1, 0, 0, 0);
	    //計算間隔到下個月1號天數
		    Calendar a = Calendar.getInstance();
			a.set(Calendar.DATE, 1);
			a.roll(Calendar.DATE, -1);
			long maxDate = a.get(Calendar.DATE);
		
	    
	    timer.schedule(task, cal.getTime(),maxDate*60*60*1000);
//		    timer.scheduleAtFixedRate(task, cal.getTime(),24*60*60*1000);
	    System.out.println("已建立排程!");       
	}
	public void destroy() {
        timer.cancel();
        System.out.println("已移除排程!");
	}
	
	
	
	
	
	@GetMapping(path = "listAllPayment")
	@ResponseBody
	public List<ManagementPaymentVO> listAllPayment() {
		final List<ManagementPaymentVO> list = mapper.listAll();
		return list; 
	}
	
	@GetMapping(path = "unPaid")
	@ResponseBody
	public List<ManagementPaymentVO> unPaid() {
		final List<ManagementPaymentVO> list = mapper.unPaid();
		return list; 
	}
	@GetMapping(path = "Paid")
	@ResponseBody
	public List<ManagementPaymentVO> Paid() {
		final List<ManagementPaymentVO> list = mapper.paid();
		return list; 
	}
	
	@PostMapping(path = "add")
	@ResponseBody
	public Core add(ManagementPaymentVO managementPaymentVO) {
		Core core = new Core();
		core.setSuccess(mapper.add(managementPaymentVO) > 0);
		return core;
	}
	
	@PostMapping(path = "update")
	@ResponseBody
	public Core update( ManagementPaymentVO managementPaymentVO) {
		Core core = new Core();
		core.setSuccess(mapper.update(managementPaymentVO) > 0);
		return core;
	}

}
