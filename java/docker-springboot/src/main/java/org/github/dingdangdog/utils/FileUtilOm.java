package org.github.dingdangdog.utils;

import java.io.*;

/**
 * 文件操作工具类
 *
 * @author hupg
 */
public class FileUtilOm {

    public static String getFileInfo(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = in.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param fileInfo 文件内容
     * @description 根据入参，保存文件
     */
    public static void saveFile(String filePath, String fileName, String fileInfo) {
        File file = new File(filePath + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter fw = new FileWriter(file)) {
            //判断file是否存在
            if (!file.exists()) {
                //如果不存在file文件，则创建
                file.createNewFile();
            }
            fw.write(fileInfo);
            //这里要说明一下，write方法是写入缓存区，并没有写进file文件里面，要使用flush方法才写进去
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
