package com.hai.tang.model;

public enum EventEnum {

    OK(0, "成功"),
    ERROR_A(100, "错误A"),
    ERROR_B(200, "错误B");

    EventEnum(int number, String description) {
        this.code = number;
        this.description = description;
    }

    private final int code;
    private final String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    //根据code查找description
    public static String getdesc(int code) {
        for (EventEnum s : EventEnum.values()) {
            if (code == s.getCode()) {
                return s.getDescription();
            }
        }
        return "";
    }

    //判断Status里是否有此code
    public static boolean hasCode(int code) {
        for (EventEnum s : EventEnum.values()) {
            if (code == s.getCode()) {
                return true;
            }
        }
        return false;
    }

    //获取所有的description
    public static String[] getdesc() {
        String[] arry = new String[EventEnum.values().length];
        int i = 0;
        for (EventEnum s : EventEnum.values()) {
            arry[i] = s.getDescription();
            i++;
        }
        return arry;
    }

    //判断该枚举中是否含有传入的枚举名称
    public static boolean contains(String enumName) {
        if (enumName == null) {
            return false;
        }
        for (EventEnum c : EventEnum.values()) {
            if (c.name().equals(enumName)) {
                return true;
            }
        }
        return false;
    }
}

