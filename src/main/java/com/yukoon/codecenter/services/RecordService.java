package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.entities.Record;
import com.yukoon.codecenter.mappers.RecordMapper;
import com.yukoon.codecenter.utils.CodeUtil;
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

	@Transactional
	public void getCodes(Record record) {
		Record new_record = insertAndGetRecord(record);
		List<Code> codes = codeService.batchInsert(new_record);
		for (Code code:codes) {
			code.setCode(CodeUtil.encodeCode(code.getId()));
			codeService.updateCodeAndClearFlag(code);
		}
	}
}
