package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.*;
import com.yukoon.codecenter.mappers.RecordMapper;
import com.yukoon.codecenter.utils.CodeUtil;
import com.yukoon.codecenter.utils.PageableUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordService {
	@Autowired
	private RecordMapper recordMapper;
	@Autowired
	private CodeService codeService;
	@Autowired
	private UserService userService;

	//添加记录并获取新添加记录的id
	@Transactional
	public Record insertAndGetRecord(Record record) {
		Date date = new Date();
		record.setApply_date(date);
		int flag = record.hashCode();
		record.setFlag(flag);
		recordMapper.addRecord(record);
		Record result = recordMapper.findByFlag(flag);
		recordMapper.clearFlag();
		return result;
	}

	//添加记录、兑换码，并获取新加记录id
	@Transactional
	public Integer getCodes(Record record,Date date) {
		Record new_record = insertAndGetRecord(record);
		List<Code> codes = codeService.batchInsert(new_record,date);
		for (Code code:codes) {
			code.setCode(CodeUtil.encodeCode(code.getId()));
			codeService.updateCodeAndClearFlag(code);
		}
		return new_record.getId();
	}

	//查询单个record
	@Transactional
	public Record findById(Integer id) {
		return recordMapper.findById(id);
	}

	//分页查询某个用户下所有申领记录
	@Transactional
	public Page findByUserId(Integer pageNo,Integer pageSize,Integer user_id) {
		List<Records2Show> list = converter(recordMapper.findByUserid(user_id));
		return PageableUtil.page(pageNo,pageSize,list);
	}

	//分页查询所有用户的申领记录
	@Transactional
	public Page findALL(Integer pageNo,Integer pageSize) {
		List<Records2Show> list = converter(recordMapper.findAll());
		return PageableUtil.page(pageNo,pageSize,list);
	}

	public List<Records2Show> converter(List<Record> records) {
		List<Records2Show> list = new ArrayList<>();
		for (Record record :records) {
			User user = userService.findByid(record.getUser_id());
			list.add(new Records2Show().setUser(user).setRecord(record));
		}
		return list;
	}

}
