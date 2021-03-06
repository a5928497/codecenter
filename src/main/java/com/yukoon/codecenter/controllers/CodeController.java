package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.Record;
import com.yukoon.codecenter.entities.Reward;
import com.yukoon.codecenter.services.CodeService;
import com.yukoon.codecenter.services.RecordService;
import com.yukoon.codecenter.services.RewardService;
import com.yukoon.codecenter.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class CodeController {
	private final static Integer PAGE_SIZE = 10;
	@Autowired
	private RewardService rewardService;
	@Autowired
	private UserService userService;
	@Autowired
	private RecordService recordService;
	@Autowired
	private CodeService codeService;

	//日期字符串转换
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	//后台前往兑换券申领页面
	@GetMapping("/code")
	public String toGetCodes(Map<String,Object> map) {
		Subject currentUser  = SecurityUtils.getSubject();
		String username = currentUser.getPrincipal().toString();
		if (username != null) {
			map.put("user_id",userService.findIdByUsername(username));
			map.put("rewards",rewardService.findAllAlive());
			return "backend/get_codes_input";
		}else {
			return "redirect:/logout";
		}
	}

	//后台申领兑换券
	@PostMapping("/code")
	public String getCodes(Record record, Date expiration_date) {
		Integer record_id = recordService.getCodes(record,expiration_date);
		return "redirect:/codes/" + record_id;
	}

	//后台查询某记录下的所有兑换券
	@GetMapping("/codes/{record_id}")
	public String findAllByRecordId(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
									@PathVariable("record_id")Integer record_id,Map<String,Object> map) {
		if (pageNo <1) {
			pageNo = 1;
		}
		Page page = codeService.findAllByRecordId(pageNo,PAGE_SIZE,record_id);
		Record record = recordService.findById(record_id);
		Reward reward = rewardService.findById(record.getReward_id());
		map.put("record_id",record_id);
		map.put("page",page);
		map.put("reward",reward);
		map.put("reward_id",record.getReward_id());
		return "backend/code_list";
	}

	//后台查询某记录下的所有兑换券
	@GetMapping("/codes")
	public String findAllByRecordIdWithRequestParam(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
													@RequestParam(value = "record_id",required = true)Integer record_id,
													Map<String,Object> map) {
		if (pageNo <1) {
			pageNo = 1;
		}
		Page page = codeService.findAllByRecordId(pageNo,PAGE_SIZE,record_id);
		Record record = recordService.findById(record_id);
		Reward reward = rewardService.findById(record.getReward_id());
		map.put("record_id",record_id);
		map.put("page",page);
		map.put("reward",reward);
		map.put("reward_id",record.getReward_id());
		return "backend/code_list";
	}
}
