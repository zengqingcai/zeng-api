package com.ectrip.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Empbyisuse {
    jy(0,"禁用"),
    yx(1,"有效"),
    hmd(2,"黑名单"),
    dsh(3,"待审核");

    private int type;
    private String name;

    Empbyisuse(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Empbyisuse typeOf(int type) {
        Empbyisuse[] empbyisuses = Empbyisuse.values();
        for (Empbyisuse empbyisuse : empbyisuses) {
            if (empbyisuse.getType() == type) {
                return empbyisuse;
            }
        }
        throw new IllegalArgumentException("非法用户状态: " + type);
    }

    public static List<Map> allEmpByiute() {
        Empbyisuse[] empbyisuses = Empbyisuse.values();
        List<Map> list = new ArrayList<Map>();
        for (int i = 0; i < empbyisuses.length; i++) {
            Map map=new HashMap();
            map.put("key",empbyisuses[i].getType()+"");
            map.put("value",empbyisuses[i].getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public String toString() {
        return "Empbyisuse{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
