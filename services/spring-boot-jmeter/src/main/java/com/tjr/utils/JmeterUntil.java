package com.tjr.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kg.apc.jmeter.threads.SteppingThreadGroup;
import netscape.javascript.JSObject;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Cookie;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPFileArg;
import org.apache.jmeter.protocol.http.util.HTTPResultConverter;
import org.apache.jmeter.protocol.java.config.JavaConfig;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import   org.apache.jmeter.threads.*;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class JmeterUntil {




    public static void main(String[] args) throws IOException {
        JSONArray jsonArray=  JmeterReadUntil.loadTree("D:/java/apache-jmeter-5.3","D:\\java\\HTTP请求.jmx");

//        HashTree  tree1= tree.getTree(TestPlan.class);
//        HashTree  tree2 = tree1.getTree("ThreadGroup");
        System.out.println(JSONObject.toJSONString(jsonArray));

    }
}
