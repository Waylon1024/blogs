package top.waylon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import top.waylon.service.RedisService;

@SpringBootApplication
@EnableScheduling
public class BlogApiApplication {
    @Autowired
    private RedisService redisService;

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

}
