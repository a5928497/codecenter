package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.Record;
import com.yukoon.codecenter.mappers.RecordMapper;
import com.yukoon.codecenter.utils.CodeUtil;
import com.yukoon.codecenter.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RecordService {
	@Autowired
	private RecordMapper recordMapper;
	@Autowired
	private CodeService codeService;

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
	public Integer getCodes(Record record) {
		Record new_record = insertAndGetRecord(record);
		List<Code> codes = codeService.batchInsert(new_record);
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
		return PageableUtil.page(pageNo,pageSize,recordMapper.findByUserid(user_id));
	}
}
