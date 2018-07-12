package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Record;
import com.yukoon.codecenter.mappers.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RecordService {
	@Autowired
	private RecordMapper recordMapper;

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
}
