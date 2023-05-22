package top.waylon.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.waylon.annotation.OperationLogger;
import top.waylon.entity.Sentence;
import top.waylon.model.vo.Result;
import top.waylon.service.SentenceService;
import top.waylon.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 博客美文后台管理
 * @Author: waylon
 * @Date: 2023-05-19
 */
@RestController
@RequestMapping("/admin")
@Api(value = "美文管理", tags = "美文管理")
public class SentencenAdminController {
    @Autowired
    SentenceService sentenceService;

    /**
     * 获取美文列表
     *
     * @return
     */
    @PostMapping("/sentences")
    @ApiOperation("获取美文列表")
    public Result sentences(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody Sentence sentence) {
        String orderBy = "id desc";// 排序方式
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Sentence> pageInfo = new PageInfo<>(sentenceService.getSentencesList(sentence));
        return Result.ok("请求成功", pageInfo);
    }

    /**
     * 获取美文类型列表
     *
     * @return
     */
    @OperationLogger("获取美文类型列表")
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

    /**
     * 根据id删除美文
     *
     * @return
     */
    @OperationLogger("根据id删除美文")
    @DeleteMapping("/deleteSentenceById")
    public Result deleteSentenceById(@RequestParam Long id) {
        Integer integer = sentenceService.deleteSentenceById(id);
        if (integer == 0) {
            return Result.error("删除美文失败");
        }
        return Result.ok("成功删除美文");
    }

    /**
     * 根据id批量删除美文
     *
     * @param idList
     * @return
     */
    @OperationLogger("根据id批量删除美文")
    @DeleteMapping("/deleteSentencesByIds")
    public Result deleteSentencesByIds(@RequestBody List<Long> idList) {
        Integer integer = sentenceService.deleteSentencesByIds(idList);
        if (integer == 0) {
            return Result.error("批量删除失败");
        }
        return Result.ok("批量删除成功");
    }
}
