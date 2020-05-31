package com.tjr.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kg.apc.jmeter.threads.SteppingThreadGroup;
import netscape.javascript.JSObject;
import org.apache.jmeter.assertions.JSONPathAssertion;
import org.apache.jmeter.assertions.XPathAssertion;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.control.*;
import org.apache.jmeter.control.gui.OnceOnlyControllerGui;
import org.apache.jmeter.control.gui.SwitchControllerGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.modifiers.CounterConfig;
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
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jmeter.threads.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JmeterReadUntil {



    public static void getTree(JSONArray jsonArray, HashTree tree) {
        Collection<Object> col = tree.list();
        for (Object obj : col) {
            String className = obj.getClass().getName();
            JSONObject jsonObject = new JSONObject();

            if (className.endsWith(".TestPlan")) {
                TestPlan testPlan = (TestPlan) obj;
                getTestPlan(jsonObject, testPlan);
            } else if (className.endsWith(".Arguments")) {
                Arguments arguments = (Arguments) obj;
                getArgument(jsonObject, arguments, Arrays.asList("name", "value", "metadata"));
            } else if (className.endsWith(".ThreadGroup")) {
                ThreadGroup threadGroup = (ThreadGroup) obj;
                getThreadGroup(jsonObject, threadGroup);
            }else if (className.endsWith(".PostThreadGroup")) {
                PostThreadGroup postThreadGroup = (PostThreadGroup) obj;
                getPostThreadGroup(jsonObject, postThreadGroup);
            } else if (className.endsWith(".SetupThreadGroup")) {
                SetupThreadGroup setupThreadGroup = (SetupThreadGroup) obj;
                getSetupThreadGroup(jsonObject, setupThreadGroup);
            } else if (className.endsWith(".SteppingThreadGroup")) {
                SteppingThreadGroup steppingThreadGroup = (SteppingThreadGroup) obj;
                getSteppingThreadGroup(jsonObject, steppingThreadGroup);
            } else if (className.endsWith(".HeaderManager")) {
                HeaderManager headerManager = (HeaderManager) obj;
                getHeaderManager(jsonObject, headerManager);
            } else if (className.endsWith(".CookieManager")) {
                CookieManager cookieManager = (CookieManager) obj;
                getCookieManager(jsonObject, cookieManager);
            } else if (className.endsWith(".JavaConfig")) {
                JavaConfig javaConfig = (JavaConfig) obj;
                getJavaConfig(jsonObject, javaConfig);
            } else if (className.endsWith(".HTTPSamplerProxy")) {
                HTTPSamplerProxy httpSamplerProxy = (HTTPSamplerProxy) obj;
                getHTTPSamplerProxy(jsonObject, httpSamplerProxy);
            } else if (className.endsWith(".TransactionController")) {
                TransactionController transactionController = (TransactionController) obj;
                getTransactionController(jsonObject, transactionController);
            } else if (className.endsWith(".IfController")) {
                IfController ifController = (IfController) obj;
                getIfController(jsonObject, ifController);
            } else if (className.endsWith(".LoopController")) {
                LoopController loopController = (LoopController) obj;
                getLoopController(jsonObject, loopController);
            } else if (className.endsWith(".WhileController")) {
                WhileController whileController = (WhileController) obj;
                getWhileController(jsonObject, whileController);
            } else if (className.endsWith(".OnceOnlyController")) {
                OnceOnlyController onceOnlyController = (OnceOnlyController) obj;
                getOnceOnlyController(jsonObject, onceOnlyController);
            } else if (className.endsWith(".ThroughputController")) {
                ThroughputController throughputController = (ThroughputController) obj;
                getThroughputController(jsonObject, throughputController);
            } else if (className.endsWith(".SwitchController")) {
                SwitchController switchController = (SwitchController) obj;
                getSwitchControllerGui(jsonObject, switchController);
            } else if (className.endsWith(".JSONPathAssertion")) {
                JSONPathAssertion jsonPathAssertion = (JSONPathAssertion) obj;
                getJSONPathAssertion(jsonObject, jsonPathAssertion);
            } else if (className.endsWith(".XPathAssertion")) {
                XPathAssertion xPathAssertion = (XPathAssertion) obj;
                getXPathAssertion(jsonObject, xPathAssertion);
            } else if (className.endsWith(".ConstantTimer")) {
                ConstantTimer constantTimer = (ConstantTimer) obj;
                getConstantTimer(jsonObject, constantTimer);
            }else if (className.endsWith(".CSVDataSet")) {
                CSVDataSet csvDataSet = (CSVDataSet) obj;
                getCSVDataSet(jsonObject, csvDataSet);
            }else if (className.endsWith(".ConfigTestElement")) {
                ConfigTestElement configTestElement = (ConfigTestElement) obj;
                getConfigTestElement(jsonObject, configTestElement);
            }else if (className.endsWith(".CounterConfig")) {
                CounterConfig counterConfig = (CounterConfig) obj;
                getCounterConfig(jsonObject, counterConfig);
            }
            if (jsonObject.size() == 0) {
                continue;
            }
            jsonObject.put("testclass", className);
            jsonObject.put("uid", CreateUUID.getUIID());
            jsonArray.add(jsonObject);
            HashTree tree1 = tree.getTree(obj);
            System.out.println(className);
            if (tree1.isEmpty()) {
                continue;
            }
            JSONArray childJSONArray = new JSONArray();
            jsonObject.put("children", childJSONArray);
            getTree(childJSONArray, tree1);

        }


    }



    private static void getCounterConfig(JSONObject jsonObject, CounterConfig counterConfig) {

        jsonObject.put("name",counterConfig.getName());
        jsonObject.put("enabled", counterConfig.isEnabled());
        jsonObject.put("comments",counterConfig.getPropertyAsString("TestPlan.comments"));


        jsonObject.put("start", counterConfig.getPropertyAsString("CounterConfig.start"));
        jsonObject.put("end", counterConfig.getPropertyAsString("CounterConfig.end"));
        jsonObject.put("incr", counterConfig.getPropertyAsString("CounterConfig.incr"));
        jsonObject.put("format", counterConfig.getPropertyAsString("CounterConfig.format"));
        jsonObject.put("per_user", counterConfig.getPropertyAsBoolean("CounterConfig.per_user"));


    }


    private static void getConfigTestElement(JSONObject jsonObject, ConfigTestElement configTestElement) {
        jsonObject.put("name", configTestElement.getName());
        jsonObject.put("enabled", configTestElement.isEnabled());
        jsonObject.put("comments",configTestElement.getPropertyAsString("TestPlan.comments"));


        jsonObject.put("server", configTestElement.getPropertyAsString("FTPSampler.server"));
        jsonObject.put("port", configTestElement.getPropertyAsString("FTPSampler.port"));
        jsonObject.put("filename", configTestElement.getPropertyAsString("FTPSampler.filename"));
        jsonObject.put("localfilename", configTestElement.getPropertyAsString("FTPSampler.localfilename"));
        jsonObject.put("inputdata", configTestElement.getPropertyAsString("FTPSampler.inputdata"));


        jsonObject.put("binarymode", configTestElement.getPropertyAsBoolean("FTPSampler.binarymode"));
        jsonObject.put("saveresponse", configTestElement.getPropertyAsBoolean("FTPSampler.saveresponse"));
        jsonObject.put("upload", configTestElement.getPropertyAsBoolean("FTPSampler.upload"));
    }

    private static void getCSVDataSet(JSONObject jsonObject, CSVDataSet csvDataSet) {
        jsonObject.put("name", csvDataSet.getName());
        jsonObject.put("enabled", csvDataSet.isEnabled());
        jsonObject.put("comments",csvDataSet.getPropertyAsString("TestPlan.comments"));


        jsonObject.put("delimiter", csvDataSet.getPropertyAsString("csvDataSet"));
        jsonObject.put("fileEncoding", csvDataSet.getPropertyAsString("fileEncoding"));
        jsonObject.put("filename", csvDataSet.getPropertyAsString("filename"));
        jsonObject.put("ignoreFirstLine", csvDataSet.getPropertyAsBoolean("ignoreFirstLine"));
        jsonObject.put("quotedData", csvDataSet.getPropertyAsBoolean("quotedData"));

        jsonObject.put("recycle", csvDataSet.getPropertyAsBoolean("recycle"));
        jsonObject.put("shareMode", csvDataSet.getPropertyAsString("shareMode"));
        jsonObject.put("stopThread", csvDataSet.getPropertyAsBoolean("stopThread"));
        jsonObject.put("variableNames", csvDataSet.getPropertyAsString("variableNames"));

    }

    private static void getConstantTimer(JSONObject jsonObject, ConstantTimer constantTimer) {
        jsonObject.put("name", constantTimer.getName());
        jsonObject.put("enabled", constantTimer.isEnabled());
        jsonObject.put("delay", constantTimer.getPropertyAsBoolean("ConstantTimer.delay"));
        jsonObject.put("comments",constantTimer.getPropertyAsString("TestPlan.comments"));

    }

    private static void getXPathAssertion(JSONObject jsonObject, XPathAssertion xPathAssertion) {
        jsonObject.put("name", xPathAssertion.getName());
        jsonObject.put("enabled", xPathAssertion.isEnabled());
        jsonObject.put("comments",xPathAssertion.getPropertyAsString("TestPlan.comments"));

        jsonObject.put("negate", xPathAssertion.getPropertyAsBoolean("XPath.negate"));
        jsonObject.put("xpath", xPathAssertion.getPropertyAsString("XPath.xpath"));

        jsonObject.put("validate", xPathAssertion.getPropertyAsBoolean("XPath.validate"));
        jsonObject.put("whitespace", xPathAssertion.getPropertyAsBoolean("XPath.whitespace"));
        jsonObject.put("tolerant", xPathAssertion.getPropertyAsBoolean("XPath.tolerant"));
        jsonObject.put("namespace", xPathAssertion.getPropertyAsBoolean("XPath.namespace"));
    }

    private static void getJSONPathAssertion(JSONObject jsonObject, JSONPathAssertion jsonPathAssertion) {
        jsonObject.put("name", jsonPathAssertion.getName());
        jsonObject.put("enabled", jsonPathAssertion.isEnabled());
        jsonObject.put("comments",jsonPathAssertion.getPropertyAsString("TestPlan.comments"));

        jsonObject.put("JSON_PATH", jsonPathAssertion.getPropertyAsString("JSON_PATH"));
        jsonObject.put("EXPECTED_VALUE", jsonPathAssertion.getPropertyAsString("EXPECTED_VALUE"));
        jsonObject.put("JSONVALIDATION", jsonPathAssertion.getPropertyAsBoolean("JSONVALIDATION"));
        jsonObject.put("EXPECT_NULL", jsonPathAssertion.getPropertyAsBoolean("EXPECT_NULL"));
        jsonObject.put("INVERT", jsonPathAssertion.getPropertyAsBoolean("INVERT"));
        jsonObject.put("ISREGEX", jsonPathAssertion.getPropertyAsBoolean("ISREGEX"));

    }

    private static void getSwitchControllerGui(JSONObject jsonObject, SwitchController switchController) {
        jsonObject.put("name", switchController.getName());
        jsonObject.put("enabled", switchController.isEnabled());
        jsonObject.put("style", switchController.getPropertyAsString("SwitchController.value"));
        jsonObject.put("comments",switchController.getPropertyAsString("TestPlan.comments"));

    }

    private static void getThroughputController(JSONObject jsonObject, ThroughputController throughputController) {
        jsonObject.put("name", throughputController.getName());
        jsonObject.put("enabled", throughputController.isEnabled());
        jsonObject.put("comments",throughputController.getPropertyAsString("TestPlan.comments"));

        jsonObject.put("style", throughputController.getPropertyAsBoolean("ThroughputController.style"));

        jsonObject.put("perThread", throughputController.getPropertyAsInt("ThroughputController.perThread"));

        jsonObject.put("maxThroughput", throughputController.getPropertyAsInt("ThroughputController.maxThroughput"));
        jsonObject.put("percentThroughput", throughputController.getPercentThroughput());
    }

    private static void getOnceOnlyController(JSONObject jsonObject, OnceOnlyController onceOnlyController) {
        jsonObject.put("name", onceOnlyController.getName());
        jsonObject.put("enabled", onceOnlyController.isEnabled());
        jsonObject.put("comments",onceOnlyController.getPropertyAsString("TestPlan.comments"));

    }

    private static void getWhileController(JSONObject jsonObject, WhileController whileController) {
        jsonObject.put("name", whileController.getName());
        jsonObject.put("enabled", whileController.isEnabled());
        jsonObject.put("comments",whileController.getPropertyAsString("TestPlan.comments"));


        jsonObject.put("condition", whileController.getPropertyAsString("WhileController.condition"));
    }

    private static void getLoopController(JSONObject jsonObject, LoopController loopController) {
        jsonObject.put("name", loopController.getName());
        jsonObject.put("enabled", loopController.isEnabled());
        jsonObject.put("continue_forever", loopController.getPropertyAsBoolean("LoopController.continue_forever"));
        jsonObject.put("loops", loopController.getPropertyAsString("LoopController.loops"));
        jsonObject.put("comments",loopController.getPropertyAsString("TestPlan.comments"));

    }

    private static void getIfController(JSONObject jsonObject, IfController ifController) {
        jsonObject.put("name", ifController.getName());
        jsonObject.put("enabled", ifController.isEnabled());
        jsonObject.put("comments",ifController.getPropertyAsString("TestPlan.comments"));

    }

    private static void getTransactionController(JSONObject jsonObject, TransactionController transactionController) {
        jsonObject.put("name", transactionController.getName());
        jsonObject.put("enabled", transactionController.isEnabled());
        jsonObject.put("comments",transactionController.getPropertyAsString("TestPlan.comments"));


    }

    private static void getHTTPSamplerProxy(JSONObject jsonObject, HTTPSamplerProxy httpSamplerProxy) {
        jsonObject.put("name", httpSamplerProxy.getName());
        jsonObject.put("enabled", httpSamplerProxy.isEnabled());
        jsonObject.put("comments",httpSamplerProxy.getPropertyAsString("TestPlan.comments"));

        HTTPFileArg[] httpFileArgs = httpSamplerProxy.getHTTPFiles();

        if (httpFileArgs != null && httpFileArgs.length > 0) {
            List<JSONObject> fileList = new ArrayList<>();
            for (HTTPFileArg fileArgs : httpFileArgs) {
                JSONObject map = new JSONObject();
                map.put("path", fileArgs.getPropertyAsString("File.path"));
                map.put("paramname", fileArgs.getPropertyAsString("File.paramname"));
                map.put("mimetype", fileArgs.getPropertyAsString("File.mimetype"));
                fileList.add(map);
            }
            jsonObject.put("files", fileList);
        }

        Arguments arguments = httpSamplerProxy.getArguments();
        JSONArray argumentsArray = getArguments(arguments, Arrays.asList("name", "value", "metadata", "always_encode", "use_equals"));
        jsonObject.put("arguments", argumentsArray);

        jsonObject.put("domain", httpSamplerProxy.getPropertyAsString("HTTPSampler.domain"));
        jsonObject.put("port", httpSamplerProxy.getPropertyAsString("HTTPSampler.port"));
        jsonObject.put("protocol", httpSamplerProxy.getPropertyAsString("HTTPSampler.protocol"));
        jsonObject.put("contentEncoding", httpSamplerProxy.getPropertyAsString("HTTPSampler.contentEncoding"));
        jsonObject.put("method", httpSamplerProxy.getPropertyAsString("HTTPSampler.method"));
        jsonObject.put("follow_redirects", httpSamplerProxy.getPropertyAsString("HTTPSampler.follow_redirects"));

        jsonObject.put("auto_redirects", httpSamplerProxy.getPropertyAsString("HTTPSampler.auto_redirects"));
        jsonObject.put("use_keepalive", httpSamplerProxy.getPropertyAsString("HTTPSampler.use_keepalive"));
        jsonObject.put("DO_MULTIPART_POST", httpSamplerProxy.getPropertyAsString("HTTPSampler.DO_MULTIPART_POST"));
        jsonObject.put("embedded_url_re", httpSamplerProxy.getPropertyAsString("HTTPSampler.embedded_url_re"));
        jsonObject.put("connect_timeout", httpSamplerProxy.getPropertyAsString("HTTPSampler.connect_timeout"));
        jsonObject.put("response_timeout", httpSamplerProxy.getPropertyAsString("HTTPSampler.response_timeout"));


    }

    private static void getJavaConfig(JSONObject jsonObject, JavaConfig javaConfig) {
        jsonObject.put("name", javaConfig.getName());
        jsonObject.put("enabled", javaConfig.isEnabled());
        jsonObject.put("classname", javaConfig.getPropertyAsString("classname"));
        jsonObject.put("comments",javaConfig.getPropertyAsString("TestPlan.comments"));

        Arguments arguments = javaConfig.getArguments();
        JSONArray argumentsArray = getArguments(arguments, Arrays.asList("name", "value", "metadata"));
        jsonObject.put("arguments", argumentsArray);
    }

    private static void getCookieManager(JSONObject jsonObject, CookieManager cookieManager) {
        jsonObject.put("name", cookieManager.getName());
        jsonObject.put("enabled", cookieManager.isEnabled());
        jsonObject.put("comments",cookieManager.getPropertyAsString("TestPlan.comments"));

        jsonObject.put("clearEachIteration", cookieManager.getPropertyAsBoolean("CookieManager.clearEachIteration"));
        List<JSONObject> cookies = new ArrayList<>();

        CollectionProperty collectionProperty = cookieManager.getCookies();


        collectionProperty.forEach((JMeterProperty jm) -> {

            Cookie cookie = (Cookie) jm.getObjectValue();

            JSONObject map = new JSONObject();
            map.put("name", cookie.getName());
            map.put("value", cookie.getPropertyAsString("Cookie.value"));

            map.put("domain", cookie.getPropertyAsString("Cookie.domain"));
            map.put("path", cookie.getPropertyAsString("Cookie.path"));
            map.put("secure", cookie.getPropertyAsString("Cookie.secure"));
            map.put("expires", cookie.getPropertyAsString("Cookie.expires"));
            map.put("path_specified", cookie.getPropertyAsString("Cookie.path_specified"));
            map.put("domain_specified", cookie.getPropertyAsString("Cookie.domain_specified"));

            cookies.add(map);
        });
        jsonObject.put("cookies", cookies);
    }


    private static  void getHeaderManager(JSONObject jsonObject, HeaderManager headerManager) {
        jsonObject.put("name", headerManager.getName());
        jsonObject.put("enabled", headerManager.isEnabled());
        jsonObject.put("comments",headerManager.getPropertyAsString("TestPlan.comments"));

        List<JSONObject> headers = new ArrayList<>();

        CollectionProperty collectionProperty = headerManager.getHeaders();

        collectionProperty.forEach((JMeterProperty jm) -> {

            Header header = (Header) jm.getObjectValue();


            JSONObject map = new JSONObject();
            map.put("name", header.getPropertyAsString("Header.name"));
            map.put("value", header.getPropertyAsString("Header.value"));
            headers.add(map);
        });
        jsonObject.put("headers", headers);
    }

    private static void getSteppingThreadGroup(JSONObject jsonObject, SteppingThreadGroup steppingThreadGroup) {

        jsonObject.put("name", steppingThreadGroup.getName());
        jsonObject.put("enabled", steppingThreadGroup.isEnabled());
        jsonObject.put("on_sample_error", steppingThreadGroup.getPropertyAsString("ThreadGroup.on_sample_error"));
        jsonObject.put("num_threads", steppingThreadGroup.getPropertyAsInt("ThreadGroup.num_threads"));
        jsonObject.put("comments",steppingThreadGroup.getPropertyAsString("TestPlan.comments"));

        jsonObject.put("threads_initial_delay", steppingThreadGroup.getPropertyAsString("Threads initial delay"));
        jsonObject.put("start_users_count", steppingThreadGroup.getPropertyAsString("Start users count"));
        jsonObject.put("start_users_count_burst", steppingThreadGroup.getPropertyAsString("Start users count burst"));
        jsonObject.put("start_users_period", steppingThreadGroup.getPropertyAsString("Start users period"));
        jsonObject.put("stop_users_count", steppingThreadGroup.getPropertyAsString("Stop users count"));

        jsonObject.put("stop_users_period", steppingThreadGroup.getPropertyAsString("Stop users period"));
        jsonObject.put("flighttime", steppingThreadGroup.getPropertyAsString("flighttime"));
        jsonObject.put("rampUp", steppingThreadGroup.getPropertyAsString("rampUp"));


        JMeterProperty loopPro = steppingThreadGroup.getProperty("ThreadGroup.main_controller");
        LoopController loopController = (LoopController) loopPro.getObjectValue();


        JSONObject loopJson = new JSONObject();

        loopJson.put("continue_forever", loopController.getPropertyAsBoolean("LoopController.continue_forever"));
        loopJson.put("loops", loopController.getPropertyAsInt("LoopController.loops"));

        jsonObject.put("loop", loopJson);

    }

    private static void getSetupThreadGroup(JSONObject jsonObject, SetupThreadGroup setupThreadGroup) {
        jsonObject.put("name", setupThreadGroup.getName());
        jsonObject.put("enabled", setupThreadGroup.isEnabled());
        jsonObject.put("comments",setupThreadGroup.getPropertyAsString("TestPlan.comments"));

        jsonObject.put("on_sample_error", setupThreadGroup.getPropertyAsString("ThreadGroup.on_sample_error"));
        jsonObject.put("num_threads", setupThreadGroup.getPropertyAsInt("ThreadGroup.num_threads"));
        jsonObject.put("ramp_time", setupThreadGroup.getPropertyAsInt("ThreadGroup.ramp_time"));
        jsonObject.put("scheduler", setupThreadGroup.getPropertyAsBoolean("ThreadGroup.scheduler"));
        jsonObject.put("duration", setupThreadGroup.getPropertyAsInt("ThreadGroup.duration"));
        jsonObject.put("delay", setupThreadGroup.getPropertyAsInt("ThreadGroup.delay"));
        jsonObject.put("same_user_on_next_iteration", setupThreadGroup.getPropertyAsBoolean("ThreadGroup.same_user_on_next_iteration"));


        JMeterProperty loopPro = setupThreadGroup.getProperty("ThreadGroup.main_controller");
        LoopController loopController = (LoopController) loopPro.getObjectValue();


        JSONObject loopJson = new JSONObject();

        loopJson.put("continue_forever", loopController.getPropertyAsBoolean("LoopController.continue_forever"));
        loopJson.put("loops", loopController.getPropertyAsInt("LoopController.loops"));

        jsonObject.put("loop", loopJson);

    }
    private static void getPostThreadGroup(JSONObject jsonObject, PostThreadGroup postThreadGroup) {
        jsonObject.put("name", postThreadGroup.getName());
        jsonObject.put("enabled", postThreadGroup.isEnabled());
        jsonObject.put("comments",postThreadGroup.getPropertyAsString("TestPlan.comments"));

        jsonObject.put("on_sample_error", postThreadGroup.getPropertyAsString("ThreadGroup.on_sample_error"));
        jsonObject.put("num_threads", postThreadGroup.getPropertyAsInt("ThreadGroup.num_threads"));
        jsonObject.put("ramp_time", postThreadGroup.getPropertyAsInt("ThreadGroup.ramp_time"));
        jsonObject.put("scheduler", postThreadGroup.getPropertyAsBoolean("ThreadGroup.scheduler"));
        jsonObject.put("duration", postThreadGroup.getPropertyAsInt("ThreadGroup.duration"));
        jsonObject.put("delay", postThreadGroup.getPropertyAsInt("ThreadGroup.delay"));
        jsonObject.put("same_user_on_next_iteration", postThreadGroup.getPropertyAsBoolean("ThreadGroup.same_user_on_next_iteration"));


        JMeterProperty loopPro = postThreadGroup.getProperty("ThreadGroup.main_controller");
        LoopController loopController = (LoopController) loopPro.getObjectValue();


        JSONObject loopJson = new JSONObject();

        loopJson.put("continue_forever", loopController.getPropertyAsBoolean("LoopController.continue_forever"));
        loopJson.put("loops", loopController.getPropertyAsInt("LoopController.loops"));

        jsonObject.put("loop", loopJson);

    }
    private static void getThreadGroup(JSONObject jsonObject, ThreadGroup threadGroup) {
        jsonObject.put("comments",threadGroup.getPropertyAsString("TestPlan.comments"));
        jsonObject.put("name", threadGroup.getName());
        jsonObject.put("enabled", threadGroup.isEnabled());
        jsonObject.put("on_sample_error", threadGroup.getPropertyAsString("ThreadGroup.on_sample_error"));
        jsonObject.put("num_threads", threadGroup.getPropertyAsInt("ThreadGroup.num_threads"));
        jsonObject.put("ramp_time", threadGroup.getPropertyAsInt("ThreadGroup.ramp_time"));
        jsonObject.put("scheduler", threadGroup.getPropertyAsBoolean("ThreadGroup.scheduler"));
        jsonObject.put("duration", threadGroup.getPropertyAsInt("ThreadGroup.duration"));
        jsonObject.put("delay", threadGroup.getPropertyAsInt("ThreadGroup.delay"));
        jsonObject.put("same_user_on_next_iteration", threadGroup.getPropertyAsBoolean("ThreadGroup.same_user_on_next_iteration"));


        JMeterProperty loopPro = threadGroup.getProperty("ThreadGroup.main_controller");
        LoopController loopController = (LoopController) loopPro.getObjectValue();


        JSONObject loopJson = new JSONObject();

        loopJson.put("continue_forever", loopController.getPropertyAsBoolean("LoopController.continue_forever"));
        loopJson.put("loops", loopController.getPropertyAsInt("LoopController.loops"));

        jsonObject.put("loop", loopJson);


    }

    private static void getTestPlan(JSONObject jsonObject, TestPlan testPlan) {

        jsonObject.put("comments",testPlan.getPropertyAsBoolean("TestPlan.comments"));
        jsonObject.put("functional_mode", testPlan.isFunctionalMode());
        jsonObject.put("tearDown_on_shutdown", testPlan.getPropertyAsBoolean("TestPlan.tearDown_on_shutdown"));
        jsonObject.put("serialize_threadgroups", testPlan.getPropertyAsBoolean("TestPlan.serialize_threadgroups"));
        jsonObject.put("user_define_classpath", testPlan.getPropertyAsString("TestPlan.user_define_classpath"));
        jsonObject.put("name", testPlan.getName());
        jsonObject.put("enabled", testPlan.isEnabled());

        Arguments arguments = testPlan.getArguments();
        JSONArray argumentsArray = getArguments(arguments, Arrays.asList("name", "value", "metadata"));

        jsonObject.put("arguments", argumentsArray);

    }

    private static void getArgument(JSONObject jsonObject, Arguments arguments, List<String> argCols) {
        JSONArray argumentsArray = getArguments(arguments, argCols);
        jsonObject.put("name", arguments.getName());
        jsonObject.put("enabled", arguments.isEnabled());
        jsonObject.put("comments",arguments.getPropertyAsString("TestPlan.comments"));
        jsonObject.put("arguments", argumentsArray);
    }

    private static JSONArray getArguments(Arguments arguments, List<String> argCols) {
        JSONArray argumentsArray = new JSONArray();
        CollectionProperty collectionProperty = arguments.getArguments();
        collectionProperty.forEach((JMeterProperty jMeterProperty) -> {
            String name = jMeterProperty.getName();
            Argument srgument = (Argument) jMeterProperty.getObjectValue();
            JSONObject argObj = new JSONObject();
            argObj.put("name", name);
            for (String str : argCols) {
                argObj.put(str, srgument.getPropertyAsString("Argument." + str));
            }
            argumentsArray.add(argObj);
        });
        return argumentsArray;
    }


}
