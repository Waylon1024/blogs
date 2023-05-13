package top.naccl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import top.naccl.constant.RedisKeyConstants;
import top.naccl.service.RedisService;
import top.naccl.util.upload.QiniuUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class BlogApiApplication {
    @Autowired
    private RedisService redisService;

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

}
