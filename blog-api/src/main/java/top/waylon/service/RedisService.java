package top.waylon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.waylon.model.vo.BlogInfo;
import top.waylon.model.vo.PageResult;
import top.waylon.util.JacksonUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
	@Autowired
	RedisTemplate jsonRedisTemplate;

	public PageResult<BlogInfo> getBlogInfoPageResultByHash(String hash, Integer pageNum) {
		if (jsonRedisTemplate.opsForHash().hasKey(hash, pageNum)) {
			Object redisResult = jsonRedisTemplate.opsForHash().get(hash, pageNum);
			PageResult<BlogInfo> pageResult = JacksonUtils.convertValue(redisResult, PageResult.class);
			return pageResult;
		} else {
			return null;
		}
	}

	public void saveKVToHash(String hash, Object key, Object value) {
		jsonRedisTemplate.opsForHash().put(hash, key, value);
	}

	public void saveMapToHash(String hash, Map map) {
		jsonRedisTemplate.opsForHash().putAll(hash, map);
	}

	public Map getMapByHash(String hash) {
		return jsonRedisTemplate.opsForHash().entries(hash);
	}


	public Object getValueByHashKey(String hash, Object key) {
		return jsonRedisTemplate.opsForHash().get(hash, key);
	}

	public void incrementByHashKey(String hash, Object key, int increment) {
		if (increment < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		jsonRedisTemplate.opsForHash().increment(hash, key, increment);
	}

	public void deleteByHashKey(String hash, Object key) {
		jsonRedisTemplate.opsForHash().delete(hash, key);
	}

	public <T> List<T> getListByValue(String key) {
		List<T> redisResult = (List<T>) jsonRedisTemplate.opsForValue().get(key);
		return redisResult;
	}

	public <T> void saveListToValue(String key, List<T> list) {
		jsonRedisTemplate.opsForValue().set(key, list);
	}

	public <T> Map<String, T> getMapByValue(String key) {
		Map<String, T> redisResult = (Map<String, T>) jsonRedisTemplate.opsForValue().get(key);
		return redisResult;
	}

	public <T> void saveMapToValue(String key, Map<String, T> map) {
		jsonRedisTemplate.opsForValue().set(key, map);
	}

	public <T> T getObjectByValue(String key, Class t) {
		Object redisResult = jsonRedisTemplate.opsForValue().get(key);
		T object = (T) JacksonUtils.convertValue(redisResult, t);
		return object;
	}

	public void incrementByKey(String key, int increment) {
		if (increment < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		jsonRedisTemplate.opsForValue().increment(key, increment);
	}

	public void saveObjectToValue(String key, Object object) {
		jsonRedisTemplate.opsForValue().set(key, object);
	}

	public void saveValueToSet(String key, Object value) {
		jsonRedisTemplate.opsForSet().add(key, value);
	}

	public int countBySet(String key) {
		return jsonRedisTemplate.opsForSet().size(key).intValue();
	}

	public void deleteValueBySet(String key, Object value) {
		jsonRedisTemplate.opsForSet().remove(key, value);
	}

	public boolean hasValueInSet(String key, Object value) {
		return jsonRedisTemplate.opsForSet().isMember(key, value);
	}

	public void deleteCacheByKey(String key) {
		jsonRedisTemplate.delete(key);
	}

	public boolean hasKey(String key) {
		return jsonRedisTemplate.hasKey(key);
	}

	public void expire(String key, long time) {
		jsonRedisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	public void saveFirstPicture(String key, String filename) {
		jsonRedisTemplate.opsForSet().add(key, filename);
	}

	public void saveEffectFirstPicture(String key, String filename) {
		jsonRedisTemplate.opsForSet().add(key, filename);
	}

	public Set<String> getFirstPicture(String key) {
		return jsonRedisTemplate.opsForSet().members(key);
	}

	public Set<String> getDiff(String allFirstPicture, String effectFirstPicture) {
		return jsonRedisTemplate.opsForSet().difference(allFirstPicture,effectFirstPicture);
	}

}
