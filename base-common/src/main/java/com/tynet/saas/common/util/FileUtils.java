package com.tynet.saas.common.util;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * 文件工具类
 *
 * @author Created by 思伟 on 2019/12/31
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 文件系统路径URL前缀
     */
    public static final String FILE_URL_PREFIX = ResourceUtils.FILE_URL_PREFIX;

    /**
     * 校验文件名是否是所属文件类型(不区分大小写)
     *
     * @param fileName  文件名
     * @param fileTypes 文件类型
     * @return boolean
     */
    public final static boolean validateFileType(String fileName, CharSequence... fileTypes) {
        if (!ObjectUtils.isEmpty(fileName) && !ObjectUtils.isEmpty(fileTypes)) {
            for (final CharSequence fileType : fileTypes) {
                if (StringUtils.endsWithIgnoreCase(fileName, fileType)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 把字节数B转化为KB、MB、GB
     *
     * @param size 字节数B
     */
    public final static String getPrintSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        int increasing = 1024;
        if (size < increasing) {
            return String.valueOf(size) + "B";
        } else {
            size = size / increasing;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < increasing) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / increasing;
        }
        if (size < increasing) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / increasing;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    /**
     * 拼接文件路径(同时会转换\或/出现2次以上的转/)推荐使用
     * For windows style path, we replace '\' to '/'.
     *
     * @param url 文件路径
     * @return 拼接后的文件路径
     */
    public static final String generateFileUrl(final String... url) {
        if (null == url) {
            return null;
        }
        // StringUtils.join自动过滤Null值
        String uri = StringUtils.join(url, StringUtils.equals(
                File.separator, "\\") ? "/" : File.separator);
        // (?i)在前面 不区分大小写
        return uri.replaceAll("(\\\\|/)+", Matcher.quoteReplacement("/"));
    }

    /**
     * 读取输入流并返回临时文件
     *
     * @param source           输入流{@code InputStream}, 不能为 {@code null}，将自动关闭
     * @param fileRelativePath 需要保存文件的相对路径，可以为 {@code null},默认使用时间戳保存
     * @return 临时 {@code File}
     */
    public static File getTempFile(final InputStream source, String fileRelativePath) throws IOException {
        final String tempOutFilePath = generateFileUrl(
                getTempDirectoryPath(),
                Optional.ofNullable(fileRelativePath)
                        .orElse(StringUtils.toString(DateUtils.currentSecondTimestamp()))
        );
        final File destination = new File(tempOutFilePath);
        copyInputStreamToFile(source, destination);
        return destination;
    }

    /**
     * 将Base64字符串解码为字节数组
     *
     * @param base64Str 任意Base64字符串(支持图片、等...)
     * @return byte[]
     */
    public static byte[] decodeBase64ToBytes(final String base64Str) {
        return Base64.decodeBase64(
                // 从分隔符第一次出现的位置向后截取
                StringUtils.defaultIfBlank(StringUtils.substringAfter(base64Str, ";base64,"), base64Str)
        );
    }

    /**
     * 根据文件路径获取文件名
     *
     * @param filePath    文件路径
     * @param defaultName 当获取到为空时，要返回默认的文件名
     * @return 获取到的文件名或默认值
     */
    public static String getFileName(final String filePath, final String defaultName) {
        return StringUtils.defaultIfBlank(FileUtil.getName(filePath), defaultName);
    }

    /**
     * 根据文件路径获取文件扩展名
     *
     * @param filePath       文件路径
     * @param defaultExtName 当获取到为空时，要返回默认的扩展名
     * @return 获取到的扩展名或默认值
     */
    public static String getFileExtName(final String filePath, final String defaultExtName) {
        return StringUtils.defaultIfBlank(FileUtil.extName(filePath), defaultExtName);
    }

}
