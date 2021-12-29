package com.tynet.saas.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.tynet.saas.common.function.ThrowingSupplier;
import com.tynet.saas.common.service.ByteWrapper;
import com.tynet.saas.common.service.adapter.ByteWrapperAdapter;
import com.tynet.saas.common.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

/**
 * 简单{@link ByteWrapper}实现
 *
 * @author Created by 思伟 on 2021/12/27
 */
public class SimpleByteWrapper extends ByteWrapperAdapter
        implements ByteWrapper, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * default constructor
     *
     * @param bytes {@code byte} array.
     */
    public SimpleByteWrapper(@NotNull byte[] bytes) {
        super(Objects.requireNonNull(bytes));
    }

    /**
     * extend constructor
     *
     * @param chars {@code char} array.
     */
    public SimpleByteWrapper(char[] chars) {
        this(StrUtil.utf8Bytes(StringUtils.valueOf(chars)));
    }

    /**
     * extend constructor
     *
     * @param string {@code String}.
     */
    public SimpleByteWrapper(String string) {
        this(StrUtil.utf8Bytes(string));
    }

    /**
     * extend constructor
     *
     * @param instance {@literal this}.
     */
    public SimpleByteWrapper(ByteWrapper instance) {
        this(instance.getBytes());
    }

    /**
     * extend constructor
     *
     * @param file {@code File}.
     */
    public SimpleByteWrapper(File file) {
        this(((ThrowingSupplier<byte[]>) () -> FileUtils.readFileToByteArray(file)).get());
    }

    /**
     * extend constructor
     * <p>
     * 此方法不关闭提供的 {@code InputStream}输入流
     * 如果需要，调用者有责任关闭流。
     * </p>
     *
     * @param inputStream {@code InputStream}.
     */
    public SimpleByteWrapper(InputStream inputStream) {
        this(((ThrowingSupplier<byte[]>) () -> IOUtils.toByteArray(inputStream)).get());
    }

    @Override
    public String toString() {
        // 转为字符串
        return StrUtil.utf8Str(this.getBytes());
    }

    /**
     * just test
     */
    public static void main(String[] args) throws IOException {
        System.out.println(StringUtils.toString("just_test".toCharArray()));

        System.out.println(new SimpleByteWrapper("just_test".toCharArray()));
        System.out.println(new SimpleByteWrapper("just_test"));
        System.out.println(new SimpleByteWrapper(new SimpleByteWrapper("just_test")));
        System.out.println(new SimpleByteWrapper(new File("D:\\test.txt")));
        try (FileInputStream inputStream = FileUtils.openInputStream(new File("D:\\test.txt"))) {
            System.out.println(new SimpleByteWrapper(inputStream));
        }
    }

}
