package top.naccl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.naccl.entity.Sentence;
import top.naccl.model.vo.Result;
import top.naccl.service.SentenceService;

import java.util.List;

/**
 * @author WayLon
 * @create 2023-05-14 15:13
 * 备注:
 */
@RestController
public class SentenceController {
    @Autowired
    SentenceService sentenceService;

    @GetMapping("/getSentence")
    public Result getSentence() {
        List<Sentence> sentence = sentenceService.getSentence();
        return Result.ok("获取一言成功", sentence);
    }
}
