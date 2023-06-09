package top.waylon.service;

import com.github.pagehelper.PageHelper;
import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.waylon.entity.Moment;
import top.waylon.exception.NotFoundException;
import top.waylon.exception.PersistenceException;
import top.waylon.mapper.MomentMapper;
import top.waylon.util.markdown.MarkdownUtils;

import java.util.List;
@Service
public class MomentService {
	@Autowired
	MomentMapper momentMapper;
	//每页显示5条动态
	private static final int pageSize = 5;
	//动态列表排序方式
	private static final String orderBy = "create_time desc";
	//私密动态提示
	private static final String PRIVATE_MOMENT_CONTENT = "<p>此条为私密动态，仅发布者可见！</p>";

	public List<Moment> getMomentList() {
		return momentMapper.getMomentList();
	}

	public List<Moment> getMomentVOList(Integer pageNum, boolean adminIdentity) {
		PageHelper.startPage(pageNum, pageSize, orderBy);
		List<Moment> moments = momentMapper.getMomentList();
		for (Moment moment : moments) {
			if (adminIdentity || moment.getPublished()) {
				moment.setContent(MarkdownUtils.markdownToHtmlExtensions(moment.getContent()));
			} else {
				moment.setContent(PRIVATE_MOMENT_CONTENT);
			}
		}
		return moments;
	}

	@Transactional(rollbackFor = Exception.class)
	public void addLikeByMomentId(Long momentId) {
		if (momentMapper.addLikeByMomentId(momentId) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateMomentPublishedById(Long momentId, Boolean published) {
		if (momentMapper.updateMomentPublishedById(momentId, published) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	public Moment getMomentById(Long id) {
		Moment moment = momentMapper.getMomentById(id);
		if (moment == null) {
			throw new NotFoundException("动态不存在");
		}
		return moment;
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteMomentById(Long id) {
		if (momentMapper.deleteMomentById(id) != 1) {
			throw new PersistenceException("删除失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveMoment(Moment moment) {
		if (momentMapper.saveMoment(moment) != 1) {
			throw new PersistenceException("动态添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateMoment(Moment moment) {
		if (momentMapper.updateMoment(moment) != 1) {
			throw new PersistenceException("动态修改失败");
		}
	}
}
