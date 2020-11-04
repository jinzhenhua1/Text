package com.jzh.basemodule.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * <p>文件读写工具类<p>
 *
 * @author jinzhenhua
 * @version 1.0 , create at 2020/4/8 16:47
 */
public class FileUtil {

    private static final String TAG = FileUtil.class.getName();

    /**
     * 文件存储的内容类型，普通log
     */
    private static final String FILE_TYPE_NORMAL_LOG = "log.txt";
    /**
     * 文件存储的内容类型，异常log
     */
    private static final String FILE_TYPE_EX_LOG = "ex.txt";

    /**
     * 全局线程池
     */
    private static ThreadPoolUtil threadPoolUtil = ThreadPoolUtil.getInstance();

    private FileUtil() {
    }

    /**
     * 写异常日志
     *
     * @param e 异常
     */
    public static void asyncWriteExLog(final String e, final String savePath) {
        threadPoolUtil.execute(() -> FileUtil.writeLog(e, FileUtil.FILE_TYPE_EX_LOG, savePath));
    }

    /**
     * 写异常日志
     *
     * @param e 异常
     */
    public static void asyncWriteExLog(final Exception e, final String savePath) {
        threadPoolUtil.execute(() -> writeExLog(e, savePath));
    }

    /**
     * 存储异常信息
     *
     * @param e 异常
     */
    private static void writeExLog(final Exception e, final String savePath) {
        if (e == null) {
            return;
        }
        StackTraceElement[] stackTraces = e.getStackTrace();
        if (stackTraces == null || stackTraces.length == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement s : stackTraces) {
            sb.append(s).append("\n");
        }
        FileUtil.writeLog(sb.toString() + "\n", FileUtil.FILE_TYPE_EX_LOG, savePath);
    }

    /**
     * 写普通log
     *
     * @param log 内容
     */
    public static void asyncWriteNormalLog(final String log, final String savePath) {
        threadPoolUtil.execute(() -> writeLog(log, FILE_TYPE_NORMAL_LOG, savePath));
    }

    /**
     * 写文件
     *
     * @param log      日志内容
     * @param typeName 日志类型，不同日志取名不一样
     * @param savePath 保存的路径，如：sdcard/.../ipcp/log
     */
    public static void writeLog(String log, final String typeName, final String savePath) {
        File dirCrash = new File(savePath);
        if (!dirCrash.exists()) {
            boolean mkdir = dirCrash.mkdirs();
            if (!mkdir) {
                LogUtils.e(TAG, new Exception("创建文件夹失败，请检查路径、权限等配置"));
                return;
            }
        }
        String logFilePath;
        if (savePath.endsWith("/")) {
            logFilePath = savePath + DateUtil.getDateStyle(DateUtil.FORMAT_TYPE_FOUR) + "_" + typeName;
        } else {
            logFilePath = savePath + File.separator + DateUtil.getDateStyle(DateUtil.FORMAT_TYPE_FOUR) + "_" + typeName;
        }
        File file = new File(logFilePath);
        FileWriter writer = null;
        try {
            if (!file.exists()) {
                boolean create = file.createNewFile();
                if (!create) {
                    LogUtils.e(TAG, new Exception("创建文件失败，请检查路径、权限等配置"));
                    return;
                }
            }
            String nowTime = DateUtil.getDateStyle(DateUtil.FORMAT_TYPE_ELEVEN);
            writer = new FileWriter(file, true);
            writer.append(nowTime);
            writer.append("\n");
            writer.append(log);
            writer.append("\n\n\n");
            writer.flush();
        } catch (IOException e) {
            LogUtils.e(TAG, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LogUtils.e(TAG, e);
                }
            }
        }
    }

    /**
     * 读取文件
     *
     * @param filePath 具体到文件名字 如：sdcard/.../demo/config.txt
     * @return 文件内容
     */
    public String readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            LogUtils.e(TAG, "文件不存在，无法读取....");
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String content;
            StringBuilder sb = new StringBuilder();
            while ((content = reader.readLine()) != null) {
                sb.append(content);
            }
            return sb.toString();
        } catch (IOException e) {
            LogUtils.e(TAG, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LogUtils.e(TAG, e);
                }
            }
        }
        return null;
    }

    /**
     * 根据文件路径 文件名称获取文件对应的uri
     *
     * @param path     文件路径如：/mnt/sdcard
     * @param filename 文件名称
     * @return uri
     */
    public static Uri getStorePathUriWithCreate(String path, String filename) {
        //图片存储目录
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        //图片文件路径
        file = new File(path, filename);
        return Uri.fromFile(file);
    }

    /**
     * 压缩图片并替换原文件
     *
     * @param cameraFile 图片路径
     */
    public static void compressPicture(String cameraFile) {
        Bitmap bitmap = FileUtil.compressImageFromFile(cameraFile);
        File f = new File(cameraFile);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
            bitmap = null;
        }
    }

    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //只读边,不读内容
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;
        float ww = 480f;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        //设置采样率
        newOpts.inSampleSize = be;

        //该模式是默认的,可不设
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // 同时设置才会有效
        newOpts.inPurgeable = true;
        //当系统内存不够时候图片自动被回收
        newOpts.inInputShareable = true;

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }


    /**
     * 旋转照片并替换原文件
     */
    public static void rotatePicture(String cameraFile) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        File file = new File(cameraFile);
        //获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
        int degree = readPictureDegree(file.getAbsolutePath());
        if (degree != 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(cameraFile, bitmapOptions);
            bitmap = rotaingImageView(degree, bitmap);
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bitmap.recycle();
                bitmap = null;
            }
        }

    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath
     * @return Bitmap
     * @author hjb
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return inSampleSize
     * @author hjb
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        return inSampleSize;
    }

    /**
     * 保存文件
     *
     * @param in       输入流
     * @param filePath 文件路径，包含名称，如：sdcard/a/b/test.jpg
     * @param callback 文件写入进度回调
     * @return 保存后的文件
     */
    public static File saveFile(InputStream in, String filePath, FileEditCallback callback) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        long sum = 0;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }
        try {
            fos = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                callback.onProgress(finalSum);
            }
            fos.flush();
            return file;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LogUtils.e(TAG, e);
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                LogUtils.e(TAG, e);
            }
        }
    }

    /**
     * 统计数据库缓存大小
     *
     * @param context context
     * @return size
     * @throws Exception e
     */
    public static String getDatabasesCacheSize(Context context) throws Exception {
        File cacheDbFile = new File("/data/data/" + context.getPackageName() + "/databases");
        return getCacheSize(cacheDbFile);
    }

    /**
     * 获取缓存大小
     */
    public static String getCacheSize(File... files) throws Exception {
        long size = 0L;
        for (File file : files) {
            size += getFolderSize(file);
        }
        return getFormatSize(size);
    }

    /**
     * 获取文件大小
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            int length = fileList.length;
            for (int i = 0; i < length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 删除附件，有可能是文件夹，有可能是文件
     *
     * @param filePath 文件路径：文件夹：sdcard/sd/file  文件：sdcard/sd/file/file.jpg
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            LogUtils.e(TAG, "文件夹不存在：" + filePath);
        }
        //是文件，直接删除
        if (file.isFile()) {
            file.delete();
        } else {
            //是文件夹，需要遍历所有文件，先删除文件，在删除文件夹
            deleteDirFile(file);
        }
    }

    /**
     * 删除文件夹和文件夹里面的文件
     */
    public static void deleteDirFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                // 删除所有文件
                file.delete();
            } else if (file.isDirectory()) {
                // 递规的方式删除文件夹
                deleteDirFile(file);
            }
        }
        // 删除目录本身
        dir.delete();
    }

    public interface FileEditCallback {
        /**
         * 文件编辑进度
         *
         * @param currentPro 当前进度
         */
        void onProgress(long currentPro);
    }
}
