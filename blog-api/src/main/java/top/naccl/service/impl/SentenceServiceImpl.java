package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.naccl.entity.Sentence;
import top.naccl.mapper.SentenceMapper;
import top.naccl.service.SentenceService;

import java.util.List;

/**
 * @author WayLon
 * @create 2023-05-14 15:22
 * 备注:
 */
@Service
public class SentenceServiceImpl implements SentenceService {
    @Autowired
    SentenceMapper sentenceMapper;
    @Override
    public List<Sentence> getSentence() {
        List<Sentence> sentence = sentenceMapper.getSentence();
        return sentence;
    }
}
