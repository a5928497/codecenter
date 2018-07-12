package com.yukoon.codecenter.controllers;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
	public String getCodes(Record record) {
		Integer record_id = recordService.getCodes(record);
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
		map.put("page",page);
		map.put("reward",reward);
		map.put("reward_id",record.getReward_id());
		return "backend/code_list";
	}
}
