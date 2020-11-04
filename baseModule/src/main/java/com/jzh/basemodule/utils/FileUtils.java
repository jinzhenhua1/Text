package com.jzh.basemodule.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/3 17:21
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 复制文件
     * @param oldFilePath
     * @param newFilePath
     * @return
     * @throws IOException
     */
    public static boolean fileCopy(String oldFilePath,String newFilePath,String fileName) {
        //如果原文件不存在
        if(fileExists(oldFilePath) == false){
            return false;
        }
        try{
            //获得原文件流
            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
            byte[] data = new byte[1024];

            File newfolder = new File(newFilePath);
            if(!newfolder.exists()){
                newfolder.mkdir();
                newfolder.mkdirs();
            }
            File newFile = new File(newFilePath,fileName);
            if(!newFile.exists()){
                newFile.createNewFile();
            }

            //输出流
            FileOutputStream outputStream =new FileOutputStream(newFile);
            //开始处理流
            while (inputStream.read(data) != -1) {
                outputStream.write(data);
            }
            inputStream.close();
            outputStream.close();
            return true;
        }catch (Exception e){
            LogUtils.e(TAG,e.toString());
            return false;
        }
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

}
