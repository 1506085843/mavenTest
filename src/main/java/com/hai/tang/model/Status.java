package com.hai.tang.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Status {

    OK(0, "成功"),
    ERROR_A(100, "错误A"),
    ERROR_B(200, "错误B");

    private final int code;

    private final String description;

    Status(int number, String description) {
        this.code = number;
        this.description = description;
    }

    // 使用 Map 提高查找效率
    private static final Map<Integer, Status> STATUS_MAP = new HashMap<>();

    static {
        Arrays.stream(Status.values()).forEach(status -> STATUS_MAP.put(status.getCode(), status));
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    //根据code查找description
    public static String getdesc(int code) {
        Status status = STATUS_MAP.get(code);
        return status != null ? status.getDescription() : "";
    }

    //判断Status里是否有此code
    public static boolean hasCode(int code) {
        return STATUS_MAP.containsKey(code);
    }

    //获取所有的description
    public static List<String> getdesc() {
        return Arrays.stream(Status.values()).map(Status::getDescription).collect(Collectors.toList());
    }

    //判断该枚举中是否含有传入的枚举名称
    public static boolean contains(String enumName) {
        if (enumName == null) {
            return false;
        }
        try {
            Status.valueOf(enumName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

