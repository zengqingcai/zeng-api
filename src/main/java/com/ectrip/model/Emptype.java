package com.ectrip.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Emptype {
    jqyh(0,"平台用户"),
    ptyh(1,"景区用户"),
    gys(2,"供应商"),
    fxs(3,"分销商");

    private int type;
    private String name;

    Emptype(int type, String name) {
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

    public static Emptype typeOf(int type) {
        Emptype[] empTypes = Emptype.values();
        for (Emptype empType : empTypes) {
            if (empType.getType() == type) {
                return empType;
            }
        }
        throw new IllegalArgumentException("非法用户类型: " + type);
    }

    public static List<Map> allEmpType() {
        Emptype[] empTypes = Emptype.values();
        List<Map> list = new ArrayList<Map>();
        for (int i = 0; i < empTypes.length; i++) {
            Map map=new HashMap();
            map.put("key",empTypes[i].getType()+"");
            map.put("value",empTypes[i].getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public String toString() {
        return "Emptype{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
