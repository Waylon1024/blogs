package top.waylon.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.waylon.constant.RedisKeyConstants;
import top.waylon.service.RedisService;
import top.waylon.util.upload.QiniuUtils;

import java.util.Set;

/**
 * @author WayLon
 * @create 2023-05-13 18:07
 * 备注:
 */
@Component
public class ClearFirstPictureScheduleTask {
    @Autowired
    RedisService redisService;

    /**
     * 定时清理预览图的垃圾图片
     */
    public void syncClearFirstPicture(){
        Set<String> diff = redisService.getDiff(RedisKeyConstants.ALL_FIRST_PICTURE, RedisKeyConstants.EFFECT_FIRST_PICTURE);
        if (diff != null) {
            for (String picName : diff) {
                //从Redis集合中删除图片名称
                redisService.deleteValueBySet(RedisKeyConstants.ALL_FIRST_PICTURE, picName);
                //删除七牛云服务器上的图片,照片名称非全链接，所以此处需要转换
                String fileName = picName.substring(picName.lastIndexOf("/") + 1);
                QiniuUtils.deleteFileFromQiniu("blog_firstPicture/" + fileName);
            }
        }
    }
}

