package com.example.demo.util;

import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 工具.
 *
 * @author koveer
 * @since 1.0
 */
public class Utils {
    /**
     * 将字符串编码为base64字符串
     *
     * @param path 图片路径
     * @return java.lang.String
     * @author koveer
     * -2023/3/13 14:30
     */
    public static String PathToBase64(String path) {
        return new String(Base64Utils.encode(path.getBytes()));
    }

    /**
     * 解码base64字符串为路径
     *
     * @param base64 待解码的base64字符串
     * @return java.lang.String
     * @author koveer
     * -2023/3/13 14:30
     */
    public static String Base64ToPath(String base64) {
        return new String(Base64Utils.decode(base64.getBytes()));
    }

    /**
     * 将文件覆盖到另一路径下，保留文件属性，返回目标文件路径的base64
     *
     * @param source 原始文件路径
     * @param target 目标文件路径
     * @return java.lang.String
     * @author koveer
     * -2023/3/13 14:59
     */
    public static String CutPic(String source, String target) {

        Path fromFile = Paths.get(source);
        Path toFile = Paths.get(target);
        try {
            Files.move(fromFile, toFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return PathToBase64(target);
    }

//    public static void SecretMove(Path fromFile,Path toFile){
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fromFile))
//    }
}
