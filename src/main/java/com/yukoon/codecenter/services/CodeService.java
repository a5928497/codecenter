package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.entities.Record;
import com.yukoon.codecenter.mappers.CodeMapper;
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

	@Transactional
	public List<Code> batchInsert(Record record) {
		List<Code> list = new ArrayList<>();
		List<Code> result = new ArrayList<>();
		int flag = record.hashCode();
		System.out.println(flag);
		for (int i = 0;i<record.getTotal() ;i++) {
			Code code = new Code();
			code.setReward_id(record.getReward_id()).setStatus(1).setExpiration_date(new Date())
					.setRecord_id(record.getId()).setFlag(flag);
			list.add(code);
		}
		codeMapper.insertAll(list);
		result = codeMapper.findByFlag(flag);
		return result;
	}

	@Transactional
	public void updateCodeAndClearFlag(Code code) {
		codeMapper.updateCodeAndClearFlag(code);
	}
}
