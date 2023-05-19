package top.naccl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.naccl.entity.Sentence;
import top.naccl.exception.PersistenceException;
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

    public void editSentence(Sentence sentence) {
        if ( sentenceMapper.editSentence(sentence)!=1){
            throw new PersistenceException("美文更新失败");
        }

    }
}