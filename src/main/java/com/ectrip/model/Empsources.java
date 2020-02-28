package com.ectrip.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Empsources {
    jcxt("0","集成系统"),
    saas("1","SAAS"),
    zfq("2","支付桥"),
    zhyx("3","整合营销");

    private String type;
    private String name;

    Empsources(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Empsources typeOf(String type) {
        Empsources[] empsources = Empsources.values();
        for (Empsources empsource : empsources) {
            if (type.equals(empsource.getType())) {
                return empsource;
            }
        }
        throw new IllegalArgumentException("非法用户来源: " + type);
    }

    public static List<Map> allEmpsources() {
        Empsources[] empsources = Empsources.values();
        List<Map> list = new ArrayList<Map>();
        for (int i = 0; i < empsources.length; i++) {
            Map map=new HashMap();
            map.put("key",empsources[i].getType()+"");
            map.put("value",empsources[i].getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public String toString() {
        return "Empsources{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
