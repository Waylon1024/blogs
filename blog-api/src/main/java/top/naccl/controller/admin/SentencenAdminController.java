package top.naccl.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.naccl.annotation.OperationLogger;
import top.naccl.entity.Sentence;
import top.naccl.model.vo.Result;
import top.naccl.service.SentenceService;
import top.naccl.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 博客美文后台管理
 * @Author: waylon
 * @Date: 2023-05-19
 */
@RestController
@RequestMapping("/admin")
public class SentencenAdminController {
    @Autowired
    SentenceService sentenceService;

    /**
     * 获取博客标签列表
     *
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @GetMapping("/sentences")
    public Result sentences(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Sentence> pageInfo = new PageInfo<>(sentenceService.getSentencesList());
        return Result.ok("请求成功", pageInfo);
    }

    /**
     * 获取美文类型列表
     *
     * @return
     */
    @GetMapping("/sentencesTypeList")
    public Result getSentencesTypeList() {
        List<Integer> sentencesTypeList = sentenceService.getSentencesTypeList();
        return Result.ok("获取美文类型列表成功", sentencesTypeList);
    }

    /**
     * 修改美文
     *
     * @param sentence
     * @return
     */
    @OperationLogger("修改美文")
    @PutMapping("/editSentence")
    public Result editSentence(@RequestBody Sentence sentence) {
        if (StringUtils.isEmpty(sentence.getContent()) || StringUtils.isEmpty(String.valueOf(sentence.getType()))) {
            return Result.error("参数不能为空");
        }
        Integer integer = sentenceService.editSentence(sentence);
        if (integer == 0) {
            return Result.error("美文更新失败");
        }
        return Result.ok("美文更新成功");
    }

    /**
     * 单条添加美文
     *
     * @param sentence
     * @return
     */
    @OperationLogger("单条添加美文")
    @PostMapping("/addSingleSentence")
    public Result addSingleSentence(@RequestBody Sentence sentence) {
        Integer integer = sentenceService.addSingleSentence(sentence);
        if (integer == 0) {
            return Result.error("美文插入失败");
        }
        return Result.ok("美文插入成功");
    }

    /**
     * 批量添加美文
     *
     * @param sentenceList
     * @return
     */
    @OperationLogger("批量添加美文")
    @PostMapping("/addExcelSentence")
    public Result addExcelSentence(@RequestBody ArrayList<Sentence> sentenceList) {
        Integer integer = sentenceService.addExcelSentence(sentenceList);
        if (integer == 0) {
            return Result.error("批量添加失败");
        }
        return Result.ok("批量添加成功");
    }

}
