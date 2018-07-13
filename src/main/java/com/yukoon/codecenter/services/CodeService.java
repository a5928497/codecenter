package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.Record;
import com.yukoon.codecenter.mappers.CodeMapper;
import com.yukoon.codecenter.utils.CodeUtil;
import com.yukoon.codecenter.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CodeService {
	@Autowired
	private CodeMapper codeMapper;

	//添加兑换码，并获取所有添加的兑换码对象
	@Transactional
	public List<Code> batchInsert(Record record,Date date) {
		List<Code> list = new ArrayList<>();
		List<Code> result = new ArrayList<>();
		int flag = record.hashCode();
		for (int i = 0;i<record.getTotal() ;i++) {
			Code code = new Code();
			code.setReward_id(record.getReward_id());
			code.setStatus(1);
			code.setExpiration_date(date);
			code.setRecord_id(record.getId());
			code.setFlag(flag);
			list.add(code);
		}
		codeMapper.insertAll(list);
		result = codeMapper.findByFlag(flag);
		return result;
	}

	//更新兑换码并清除flag
	@Transactional
	public void updateCodeAndClearFlag(Code code) {
		codeMapper.updateCodeAndClearFlag(code);
	}

	//通过record_id查找所有兑换码，返回分页对象
	@Transactional
	public Page findAllByRecordId(Integer pageNo,Integer pageSize,Integer record_id) {
		return PageableUtil.page(pageNo,pageSize,codeMapper.findAllByRecordId(record_id));
	}

	//解码兑换码并返回code对象
	@Transactional
	public Code decode(String codeNumber) {
		Long coid_id = CodeUtil.decodeCode(codeNumber);
		Code code = codeMapper.findById(coid_id);
		if (null != code && codeNumber.equals(code.getCode())){
			return code;
		}else {
			return null;
		}
	}

	//更新code中status字段为2，即已用
	@Transactional
	public void cash(Code code) {
		codeMapper.cash(code);
	}
}
