package web.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import web.pack.entity.PackageVO;
import web.pack.mapper.PackMapper;
import web.payment.entity.ManagementPaymentVO;
import web.payment.mapper.ManagementPaymentMapper;

@Controller
@RequestMapping("payment")
public class PaymentController {
	
	@Autowired
	private ManagementPaymentMapper mapper;
	
	@GetMapping(path = "listAllPayment")
	@ResponseBody
	public List<ManagementPaymentVO> listAllPayment() {
		final List<ManagementPaymentVO> list = mapper.listAll();
		return list; 
	}

}
