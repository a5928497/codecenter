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
	public void batchInsert(Record record) {
		List<Code> list = new ArrayList<>();
		for (int i = 0;i<record.getTotal() ;i++) {
			Code code = new Code();
			code.setReward_id(record.getReward_id()).setStatus(1).setExpiration_date(new Date());
			list.add(code);
		}
		codeMapper.insertAll(list);
	}
}
