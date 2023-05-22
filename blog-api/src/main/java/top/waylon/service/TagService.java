package top.waylon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.waylon.constant.RedisKeyConstants;
import top.waylon.entity.Tag;
import top.waylon.exception.NotFoundException;
import top.waylon.exception.PersistenceException;
import top.waylon.mapper.TagMapper;

import java.util.List;
@Service
public class TagService {
	@Autowired
	TagMapper tagMapper;
	@Autowired
	RedisService redisService;

	public List<Tag> getTagList() {
		return tagMapper.getTagList();
	}

	public List<Tag> getTagListNotId() {
		String redisKey = RedisKeyConstants.TAG_CLOUD_LIST;
		List<Tag> tagListFromRedis = redisService.getListByValue(redisKey);
		if (tagListFromRedis != null) {
			return tagListFromRedis;
		}
		List<Tag> tagList = tagMapper.getTagListNotId();
		redisService.saveListToValue(redisKey, tagList);
		return tagList;
	}

	public List<Tag> getTagListByBlogId(Long blogId) {
		return tagMapper.getTagListByBlogId(blogId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveTag(Tag tag) {
		if (tagMapper.saveTag(tag) != 1) {
			throw new PersistenceException("标签添加失败");
		}
		redisService.deleteCacheByKey(RedisKeyConstants.TAG_CLOUD_LIST);
	}

	public Tag getTagById(Long id) {
		Tag tag = tagMapper.getTagById(id);
		if (tag == null) {
			throw new NotFoundException("标签不存在");
		}
		return tag;
	}

	public Tag getTagByName(String name) {
		return tagMapper.getTagByName(name);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteTagById(Long id) {
		if (tagMapper.deleteTagById(id) != 1) {
			throw new PersistenceException("标签删除失败");
		}
		redisService.deleteCacheByKey(RedisKeyConstants.TAG_CLOUD_LIST);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateTag(Tag tag) {
		if (tagMapper.updateTag(tag) != 1) {
			throw new PersistenceException("标签更新失败");
		}
		redisService.deleteCacheByKey(RedisKeyConstants.TAG_CLOUD_LIST);
		//修改了标签名或颜色，可能有首页文章关联了标签，也要更新首页缓存
		redisService.deleteCacheByKey(RedisKeyConstants.HOME_BLOG_INFO_LIST);
	}
}
