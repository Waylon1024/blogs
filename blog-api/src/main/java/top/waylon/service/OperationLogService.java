package top.waylon.service;

import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.waylon.entity.OperationLog;
import top.waylon.exception.PersistenceException;
import top.waylon.mapper.OperationLogMapper;
import top.waylon.model.dto.UserAgentDTO;
import top.waylon.util.IpAddressUtils;
import top.waylon.util.UserAgentUtils;

import java.util.List;

@Service
public class OperationLogService {
	@Autowired
	OperationLogMapper operationLogMapper;
	@Autowired
	UserAgentUtils userAgentUtils;

	public List<OperationLog> getOperationLogListByDate(String startDate, String endDate) {
		return operationLogMapper.getOperationLogListByDate(startDate, endDate);
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveOperationLog(OperationLog log) {
		String ipSource = IpAddressUtils.getCityInfo(log.getIp());
		UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		log.setIpSource(ipSource);
		log.setOs(userAgentDTO.getOs());
		log.setBrowser(userAgentDTO.getBrowser());
		if (operationLogMapper.saveOperationLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteOperationLogById(Long id) {
		if (operationLogMapper.deleteOperationLogById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}
}
