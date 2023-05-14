package top.naccl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.naccl.entity.Sentence;

import java.util.List;

/**
 * @author WayLon
 * @create 2023-05-14 15:23
 * 备注:
 */
@Mapper
@Repository
public interface SentenceMapper {

    List<Sentence> getSentence();
}
