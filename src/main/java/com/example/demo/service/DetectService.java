package com.example.demo.service;

import com.example.demo.entity.MarkPic;
import com.example.demo.entity.Result;
import com.example.demo.entity.Task;
import com.example.demo.mapper.*;
import com.example.demo.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DetectService {

    @Value("${pythonPath}")
    String pythonPath;
    final MarkPicMapper markPicMapper;
    final ResultMapper resultMapper;
    final TaskMapper taskMapper;
    final BatchMapper batchMapper;
    final NameMapper nameMapper;

    @Value("${pythonPath}")
    String PythonPath;

    final static int VAR = 5;

    @Value("${picSavePath}")
    String picSavePath;


    /**
     * 项目主要功能的实现：
     * 从文件夹中获取图片，存入数据库，获取检测结果图片以及检测结果，存入数据库.
     *
     * @param line 图片存放的文件夹路径
     * @return java.util.Map<java.lang.String, com.example.demo.entity.MarkPic>
     * @author koveer
     * -2023/2/22 20:18
     * @since 2.0
     * -2023/3/22 13:13
     */
    public Map<String, MarkPic> detect(String[] line) {
        //识别图片
        List<List<String>> listList = judge(line);
        Map<String, MarkPic> oripath = new LinkedHashMap<>();
        //***

        //创建任务
        Task task = new Task();
        task.setCreateTime(new Date());
        String aNew = batchMapper.getNew();
        task.setBatch(aNew);
        int id = taskMapper.insert(task);
        int taskId = task.getId();
        //***

        //处理每张图片
        int i = 1;
        for (List<String> list :
                listList) {

            //插入标记后的图片
            MarkPic markPic = new MarkPic();
            markPic.setTaskId(taskId);
            markPic.setTaskNumber(i);
            i++;
            //获取标记后图片名称及后缀
            StringBuilder picname = new StringBuilder(list.get(0));
            picname.reverse();
            picname = new StringBuilder(picname.substring(0, picname.indexOf("\\")));
            picname.reverse();
            //***

            String markPicPath = pythonPath + "\\runs\\detect\\exp\\" + picname;
            //2.0 将图片移动到指定路径，存储图片路径
            String batch = batchMapper.getNew();
            String pname = nameMapper.getNew();
            Utils.CopyPic(markPicPath, "E:\\PIC\\" + batch + "\\mark\\" + pname + "\\" + picname);
            String markBase64 = Utils.MovePic(markPicPath, picSavePath + "\\" + batch + "\\mark\\" + picname);
            markPic.setMarkPic(markBase64);

            //1.0 将图片存入数据库
//            byte[] pic = new byte[0];
//            try {
//                pic = Files.readAllBytes(Paths.get(markPicPath));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            markPic.setMarkPic(pic);

            markPicMapper.insert(markPic);

            //***

            //插入结果
            Result result = new Result();
            result.setTaskId(markPic.getTaskId());
            result.setMarkId(markPic.getMarkId());
            //没有缺陷
            if (list.size() == 1) {
                oripath.put(list.get(0), markPic);
            } else {//***
                //有缺陷
                Iterator<String> iterator = list.iterator();
                iterator.next();
                while (iterator.hasNext()) {
                    String[] temp = iterator.next().split(",");
                    switch (temp[1]) {
                        case "A":
                            result.setDef1(Integer.parseInt(temp[0]));
                            break;
                        case "B":
                            result.setDef2(Integer.parseInt(temp[0]));
                            break;
                        case "C":
                            result.setDef3(Integer.parseInt(temp[0]));
                            break;
                        case "D":
                            result.setDef4(Integer.parseInt(temp[0]));
                            break;
                    }
                }
                //获取每张原始图片的地址
                oripath.put(list.get(0), markPic);
                //***
            }
            //***

            resultMapper.insert(result);
        }
        return oripath;

    }

    /**
     * 外层的list，每一个元素代表一张图片
     * 内层的list，[0]代表图片地址，后面的元素代表缺陷数量和类型
     *
     * @return java.util.List<java.util.List < java.lang.String>>
     * @author koveer
     * -2023/2/20 14:04
     */
    @Deprecated
    public List<List<String>> runDetect() {
        Process proc;
        List<List<String>> list = new ArrayList<>();
        try {

            String[] cmd = new String[]{pythonPath + "\\venv\\Scripts\\detect.bat"};
            proc = Runtime.getRuntime().exec(cmd, null, new File(pythonPath + "\\venv\\Scripts"));
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println("********************");
                System.out.println(line);
                if (line.startsWith("image")) {
                    //temp[0] 图片位置 剩下的是缺陷数量 缺陷类型
//                    System.out.println("********************");
//                    System.out.println(line);
                    String[] ss = line.split(" ");
                    List<String> temp = new ArrayList<>();
                    temp.add(ss[2].substring(0, ss[2].length() - 1));
                    for (String s :
                            ss) {
                        if (s.startsWith("[") && s.endsWith("]"))
                            temp.add(s.substring(1, s.length() - 1));
                    }
                    list.add(temp);
                }
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 对于形如 image 图片路径 [缺陷数量,缺陷类型] [缺陷数量,缺陷类型]@格式的字符串，外层层List每一个元素代表一张图片对应的输出
     * 内层list[0]表示图片绝对路径，之后每个元素代表一种缺陷的数量.
     *
     * @param line image行
     * @return java.util.List<java.util.List < java.lang.String>>
     * @author koveer
     * -2023/3/27 15:58
     * @since 1.0
     */
    public List<List<String>> judge(String[] line) {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < VAR; i++) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            //temp[0] 图片位置 剩下的是缺陷数量 缺陷类型
            String[] ss = line[i].split(" ");
            List<String> temp = new ArrayList<>();
            System.out.println("****************");
            System.out.println(Arrays.toString(ss));
            //图片位置
            temp.add(ss[2].substring(0, ss[2].length() - 1));
            for (String s :
                    ss) {
                if (s.startsWith("[") && s.endsWith("]"))
                    //遍历缺陷
                    temp.add(s.substring(1, s.length() - 1));
            }
//                    System.out.println(temp);
            list.add(temp);
        }
        return list;
    }
    //***
}
