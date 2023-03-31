package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.MarkPic;
import com.example.demo.entity.OriPic;
import com.example.demo.entity.Result;
import com.example.demo.mapper.*;
import com.example.demo.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CURDService {
    final TaskMapper taskMapper;
    final OriPicMapper oriPicMapper;
    final MarkPicMapper markPicMapper;
    final ResultMapper resultMapper;
    final DetectService detectService;
    final MsgMapper msgMapper;
    final BatchMapper batchMapper;
    final WebSocketServer webSocketServer;

    @Value("${pythonPath}")
    String pythonPath;

    @Value("${picSavePath}")
    String picSavePath;

    /**
     * 将上传的原始图片存入数据库，可批量存入，每次存入的图片为同一任务，同一任务最多取5张图片
     *
     * @param files 上传的文件
     * @return int 上传文件数
     * @author koveer
     */
//    @Deprecated
//    public int webUpload(MultipartFile[] files) {
//        if (files.length != 0) {
//            //创建任务的时间和ID
//            int i = 0;
//            //创建任务信息
//            Task task = new Task();
//            task.setCreateTime(new Date());
//            int id = taskMapper.insert(task);
//            int taskId = task.getId();
//            OriPic[] oriPic = new OriPic[5];
//
//
//            for (MultipartFile file :
//                    files) {
//                //上传超过5个文件时，只读取前5个
//                if (i == 6)
//                    break;
//                //原始图片及信息
//                oriPic[i] = new OriPic();
//                oriPic[i].setTaskId(taskId);
//                oriPic[i].setTaskNumber(i + 1);
//
//
////                try {
//////                    oriPic[i].setOriPic(file.getBytes());
////                } catch (IOException e) {
////                    throw new RuntimeException(e);
////                }
//                i++;
//            }
//            for (int j = 0; j < AutoService.VAR; j++) {
//                if (oriPic[j] != null) {
//                    oriPicMapper.insert(oriPic[j]);
//                    //对图片进行处理
////                    detectService.detect(oriPic[j]);
//                }
//            }
//        }
//        return files.length;
//    }

    /**
     * 递归删除文件夹
     *
     * @param filePath   文件夹路径
     * @param deleteSelf 是否删除文件夹本身
     * @author koveer
     * -2023/2/17 0:18
     */
    public void deleteFile(String filePath, boolean deleteSelf, String con) {
        File file = new File(filePath);
        File file2 = new File(con);
        if (file.isFile())  //判断是否为文件，是，则删除
        {
            file.delete();
        } else //不为文件，则为文件夹
        {
            String[] childFilePath = file.list();//获取文件夹下所有文件相对路径
            if (childFilePath != null) {
                for (String path : childFilePath) {
                    deleteFile(file.getAbsoluteFile() + "/" + path, deleteSelf, con);//递归，对每个都进行判断
                }
                if (deleteSelf && !file.getPath().equals(file2.getPath()))
                    file.delete(); // 如果不保留文件夹本身 则执行此行代码
            }
        }
    }

    public void deleteFile(String filePath, boolean deleteSelf) {
        deleteFile(filePath, deleteSelf, filePath);
    }

    /**
     * 初始化图片文件、检测结果文件
     *
     * @param filePath 文件路径
     * @author koveer
     * -2023/2/17 0:19
     */
    public void init(String filePath) {
        deleteFile(filePath, true);
        deleteFile(pythonPath + "\\runs\\detect", true);
    }

    /**
     * 项目主要功能的实现：
     * 从文件夹中获取图片，存入数据库，获取检测结果图片以及检测结果，存入数据库
     *
     * @param line 图片存放的文件夹路径
     * @return boolean 是否运行成功
     * @author koveer
     * -2023/2/17 0:20
     */
    public boolean upload(String[] line) {
        //获取原始图片需要的数据
        Map<String, MarkPic> map = detectService.detect(line);
        //***

        //处理每张标记图片对应的原始图片
        for (Map.Entry<String, MarkPic> entry : map.entrySet()) {
            //获取信息
            String path = entry.getKey();
            path = path.replaceAll("IMG", "IMGtmp");
            MarkPic markPic = entry.getValue();
            //***

            //录入原始图片
            OriPic oriPic = new OriPic();
            oriPic.setOriId(markPic.getMarkId());
            oriPic.setTaskNumber(markPic.getTaskNumber());
            oriPic.setTaskId(markPic.getTaskId());

            //2.0 将图片移动到指定路径，存储图片路径
            StringBuilder picname = new StringBuilder(path);
            picname.reverse();
            picname = new StringBuilder(picname.substring(0, picname.indexOf("\\")));
            picname.reverse();
            String batch = batchMapper.getNew();
            String target = picSavePath + "\\" + batch + "\\ori\\" + picname;
            System.out.println(target);
            String oriBase64 = Utils.MovePic(path, target);
            oriPic.setOriPic(oriBase64);

            //1.0 将图片存入数据库
//            byte[] pic = new byte[0];
//            try {
//                pic = Files.readAllBytes(Paths.get(path));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            oriPic.setOriPic(pic);

            int taskId = oriPic.getTaskId();
            int taskNumber = oriPic.getTaskNumber();
            int i = oriPicMapper.insert(oriPic);

            if (taskNumber == AutoService.VAR) {
                msgMapper.Myinsert(taskId, 1);
                webSocketServer.sendMessage("admin",msgMapper.getNew());
            }
        }
        //***
        return true;
    }

    /**
     * 通过检测时间分类检索
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @return com.example.demo.entity.Result[]
     * @author koveer
     * -2023/2/22 20:13
     */
    public Result[] searchByTime(Date beginDate, Date endDate) {
        Result[] results = taskMapper.selectResultAllByTaskDate(beginDate, endDate);
        return results;
    }

    /**
     * 通过检测结果分类检索
     *
     * @param str 检测结果
     * @return com.example.demo.entity.Result[]
     * @author koveer
     */
    public List<Result> searchByResult(List<String> str) {
        QueryWrapper<Result> queryWrapper = new QueryWrapper<>();
        for (String s :
                str) {
            queryWrapper.ne(s, 0);
        }
        List<Result> results = resultMapper.selectList(queryWrapper);
        return results;
    }


}
