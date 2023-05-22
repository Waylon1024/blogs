package top.waylon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.waylon.entity.VisitRecord;
import top.waylon.mapper.VisitRecordMapper;

@Service
public class VisitRecordService {
	@Autowired
	VisitRecordMapper visitRecordMapper;

	@Transactional(rollbackFor = Exception.class)
	public void saveVisitRecord(VisitRecord visitRecord) {
		visitRecordMapper.saveVisitRecord(visitRecord);
	}
}

