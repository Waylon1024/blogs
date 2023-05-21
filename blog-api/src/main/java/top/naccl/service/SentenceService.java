package top.naccl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.naccl.entity.Sentence;
import top.naccl.mapper.SentenceMapper;

import java.util.List;

/**
 * @author WayLon
 * @create 2023-05-14 15:18
 * 备注:
 */
@Service
public class SentenceService {
    @Autowired
    SentenceMapper sentenceMapper;

    public List<Sentence> getSentence() {
        List<Sentence> sentence = sentenceMapper.getSentence();
        return sentence;
    }

    public List<Sentence> getSentencesList() {
        List<Sentence> sentenceList = sentenceMapper.getSentencesList();
        return sentenceList;
    }

    public List<Integer> getSentencesTypeList() {
        return sentenceMapper.getSentencesTypeList();
    }

    public Integer editSentence(Sentence sentence) {
        return sentenceMapper.editSentence(sentence);
    }

    public Integer addSingleSentence(Sentence sentence) {
        return sentenceMapper.addSingleSentence(sentence);
    }
}
