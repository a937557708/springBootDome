package com.tjr.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class JmeterUntil {

    public static JSONArray getTreeList(String jmeterPath, String jmxPath) throws IOException {
        HashTree tree = loadTree(jmeterPath, jmxPath);
        JSONArray jsonArray = new JSONArray();
        JmeterReadUntil.getTree(jsonArray, tree);
        return jsonArray;
    }

    public static HashTree loadTree(String jmeterPath, String jmxPath) throws IOException {
        loadProperties(jmeterPath);
        HashTree tree = SaveService.loadTree(new File(jmxPath));
        return tree;
    }

    public static void loadProperties(String jmeterPath) throws IOException {
        // JMeter Engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        // Initialize Properties, logging, locale, etc.
        JMeterUtils.loadJMeterProperties(jmeterPath + "/bin/jmeter.properties");
        JMeterUtils.setJMeterHome(jmeterPath);
        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();
    }

    public static void saveTree(HashTree tree, String jmxPath) throws IOException {
        SaveService.saveTree(tree, new FileOutputStream(new File(jmxPath)));
    }


    public static void main(String[] args) throws IOException {
        JSONArray jsonArray=  JmeterUntil.getTreeList("D:/java/apache-jmeter-5.3","D:\\java\\HTTP请求.jmx");
        HashTree tree = loadTree("D:/java/apache-jmeter-5.3", "D:\\java\\HTTP请求.jmx");
        JmeterWriteUntil.updateJmx(jsonArray,tree);
        saveTree(tree, "D:\\java\\HTTP请求tst.jmx");
//        HashTree  tree1= tree.getTree(TestPlan.class);s
//        HashTree  tree2 = tree1.getTree("ThreadGroup");
//        System.out.println(JSONObject.toJSONString(jsonArray));

    }
}
