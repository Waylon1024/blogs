package top.waylon.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.waylon.entity.Sentence;

import java.util.ArrayList;
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

    List<Sentence> getSentencesList(@Param("req") Sentence sentence);

    List<Integer> getSentencesTypeList();

    int editSentence(Sentence sentence);

    int addSingleSentence(Sentence sentence);

    Integer addExcelSentence(ArrayList<Sentence> sentenceList);

    Integer deleteSentenceById(Long id);

    Integer deleteSentencesByIds(List<Long> idList);
}

