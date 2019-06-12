package com.zhuzichu.mvvm.global.cache;

import android.content.Context;
import android.text.format.Formatter;
import com.zhuzichu.mvvm.utils.SDCardUtilsKt;

import java.io.File;

public class GlobalCache {
    private final static String GLIDE_DISCACHE_DIR = "/glide_cache_dir";


    public static File getDiskCacheDir(Context context, String last) {
        String path;
        if (SDCardUtilsKt.isSDCardMounted()) {
            path = context.getExternalCacheDir() + last;
        } else {
            path = context.getCacheDir() + last;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsoluteFile();
    }


    /**
     * 获取glide缓存目录
     *
     * @param context context
     * @return 缓存目录
     */
    public static File getGlideDiskCacheDir(Context context) {
        return getDiskCacheDir(context, GLIDE_DISCACHE_DIR);
    }


    /**
     * 获取Glide缓存大小
     *
     * @param context context
     * @return 缓存大小
     */
    public static String getGlidecacheFileSizeStr(Context context) {
        long fileSize = getGlidecacheFileSizeNum(context);
        return Formatter.formatFileSize(context, fileSize);
    }

    public static long getGlidecacheFileSizeNum(Context context) {
        long fileSize = 0;
        File file = getGlideDiskCacheDir(context);
        for (File childFile : file.listFiles()) {
            fileSize += childFile.length();
        }
        return fileSize;
    }
}
