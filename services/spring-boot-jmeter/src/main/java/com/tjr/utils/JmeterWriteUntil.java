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

public class JmeterWriteUntil {


    public static void updateJmx(JSONArray jsonArray, HashTree tree) {
        if (jsonArray == null) {
            return;
        }
        if (jsonArray.size() == 0) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject treeObj = (JSONObject) jsonArray.get(i);
            String className = treeObj.getString("testclass");

            Collection<Object> colList = tree.list();
            Object[] treeArr = colList.toArray();
            Object obj = treeArr[i];

            if (className.endsWith("TestPlan")) {
                TestPlan testPlan = (TestPlan) obj;
                writeTestPlan(testPlan, treeObj);
            } else if (className.endsWith("Arguments")) {
                Arguments arguments = (Arguments) obj;
                writeArgument(arguments, Arrays.asList("name", "value", "metadata"), treeObj);
            } else if (className.endsWith("ThreadGroup")) {
                ThreadGroup threadGroup = (ThreadGroup) obj;
                writeThreadGroup(threadGroup, treeObj);
            } else if (className.endsWith("SetupThreadGroup")) {
                SetupThreadGroup setupThreadGroup = (SetupThreadGroup) obj;
                writeSetupThreadGroup(setupThreadGroup, treeObj);

            } else if (className.endsWith("SteppingThreadGroup")) {
                SteppingThreadGroup steppingThreadGroup = (SteppingThreadGroup) obj;

                writeSteppingThreadGroup(steppingThreadGroup, treeObj);

            } else if (className.endsWith("HeaderManager")) {
                HeaderManager headerManager = (HeaderManager) obj;
                writeHeaderManager(headerManager, treeObj);

            } else if (className.endsWith("CookieManager")) {
                CookieManager cookieManager = (CookieManager) obj;
                writeCookieManager(cookieManager, treeObj);
            } else if (className.endsWith("JavaConfig")) {
                JavaConfig javaConfig = (JavaConfig) obj;

                writeJavaConfigr(javaConfig, treeObj);

            } else if (className.endsWith("HTTPSamplerProxy")) {
                HTTPSamplerProxy httpSamplerProxy = (HTTPSamplerProxy) obj;
                writeHTTPSamplerProxy(httpSamplerProxy, treeObj);

            } else if (className.endsWith("HTTPSamplerProxy")) {
                TransactionController transactionController = (TransactionController) obj;
                writeTransactionController(transactionController, treeObj);

            } else if (className.endsWith("IfController")) {
                IfController ifController = (IfController) obj;
                writeIfController(ifController, treeObj);


            } else if (className.endsWith("LoopController")) {
                LoopController loopController = (LoopController) obj;
                writeLoopController(loopController, treeObj);


            } else if (className.endsWith("WhileController")) {
                WhileController whileController = (WhileController) obj;
                writeWhileController(whileController, treeObj);

            } else if (className.endsWith("OnceOnlyController")) {
                OnceOnlyController onceOnlyController = (OnceOnlyController) obj;
                writeOnceOnlyController(onceOnlyController, treeObj);

            } else if (className.endsWith("ThroughputController")) {
                ThroughputController throughputController = (ThroughputController) obj;
                writeThroughputController(throughputController, treeObj);


            } else if (className.endsWith("SwitchControllerGui")) {
                SwitchController switchController = (SwitchController) obj;

                writeSwitchController(switchController, treeObj);


            } else if (className.endsWith("JSONPathAssertion")) {
                JSONPathAssertion jsonPathAssertion = (JSONPathAssertion) obj;
                writeJSONPathAssertion(jsonPathAssertion, treeObj);


            } else if (className.endsWith("XPathAssertion")) {
                XPathAssertion xPathAssertion = (XPathAssertion) obj;
                writeXPathAssertion(xPathAssertion, treeObj);


            } else if (className.endsWith("ConstantTimer")) {
                ConstantTimer constantTimer = (ConstantTimer) obj;
                writeConstantTimer(constantTimer, treeObj);

            } else if (className.endsWith("CSVDataSet")) {
                CSVDataSet csvDataSet = (CSVDataSet) obj;
                writeCSVDataSet(csvDataSet, treeObj);

            } else if (className.endsWith("ConfigTestElement")) {
                ConfigTestElement configTestElement = (ConfigTestElement) obj;
                writeConfigTestElement(configTestElement, treeObj);

            } else if (className.endsWith("CounterConfig")) {
                CounterConfig counterConfig = (CounterConfig) obj;
                writeCounterConfig(counterConfig, treeObj);

            }


        }


    }

    private static void writeCounterConfig(CounterConfig counterConfig, JSONObject treeObj) {
        counterConfig.setEnabled(treeObj.getBooleanValue("enabled"));
        counterConfig.setName(treeObj.getString("name"));
        counterConfig.setProperty("CounterConfig.start",treeObj.getString("start"));
        counterConfig.setProperty("CounterConfig.end",treeObj.getString("end"));
        counterConfig.setProperty("CounterConfig.incr",treeObj.getString("incr"));
        counterConfig.setProperty("CounterConfig.name",treeObj.getString("name"));
        counterConfig.setProperty("CounterConfig.format",treeObj.getString("format"));
        counterConfig.setProperty("CounterConfig.per_user",treeObj.getBooleanValue("per_user"));


    }

    private static void writeConfigTestElement(ConfigTestElement configTestElement, JSONObject treeObj) {
        configTestElement.setEnabled(treeObj.getBooleanValue("enabled"));
        configTestElement.setName(treeObj.getString("name"));
        configTestElement.setProperty("FTPSampler.server",treeObj.getString("server"));
        configTestElement.setProperty("FTPSampler.port",treeObj.getString("port"));
        configTestElement.setProperty("FTPSampler.filename",treeObj.getString("filename"));
        configTestElement.setProperty("FTPSampler.localfilename",treeObj.getString("localfilename"));
        configTestElement.setProperty("FTPSampler.inputdata",treeObj.getString("inputdata"));


        configTestElement.setProperty("CounterConfig.binarymode",treeObj.getBooleanValue("binarymode"));
        configTestElement.setProperty("CounterConfig.saveresponse",treeObj.getBooleanValue("saveresponse"));


    }

    private static void writeCSVDataSet(CSVDataSet csvDataSet, JSONObject treeObj) {

        csvDataSet.setEnabled(treeObj.getBooleanValue("enabled"));
        csvDataSet.setName(treeObj.getString("name"));
        csvDataSet.setProperty("delimiter",treeObj.getString("delimiter"));
        csvDataSet.setProperty("fileEncoding",treeObj.getString("fileEncoding"));
        csvDataSet.setProperty("filename",treeObj.getString("filename"));
        csvDataSet.setProperty("ignoreFirstLine",treeObj.getBooleanValue("ignoreFirstLine"));
        csvDataSet.setProperty("quotedData",treeObj.getBooleanValue("quotedData"));
        csvDataSet.setProperty("recycle",treeObj.getBooleanValue("recycle"));
        csvDataSet.setProperty("shareMode",treeObj.getString("shareMode"));
        csvDataSet.setProperty("stopThread",treeObj.getBooleanValue("stopThread"));
        csvDataSet.setProperty("variableNames",treeObj.getString("variableNames"));

    }

    private static void writeConstantTimer(ConstantTimer constantTimer, JSONObject treeObj) {

        constantTimer.setEnabled(treeObj.getBooleanValue("enabled"));
        constantTimer.setName(treeObj.getString("name"));
        constantTimer.setProperty("ConstantTimer.delay",treeObj.getBooleanValue("delay"));

    }


    private static void writeXPathAssertion(XPathAssertion xPathAssertion, JSONObject treeObj) {
        xPathAssertion.setEnabled(treeObj.getBooleanValue("enabled"));
        xPathAssertion.setName(treeObj.getString("name"));
        xPathAssertion.setProperty("XPath.negate",treeObj.getBooleanValue("negate"));
        xPathAssertion.setProperty("XPath.xpath",treeObj.getString("xpath"));
        xPathAssertion.setProperty("XPath.validate",treeObj.getBooleanValue("validate"));
        xPathAssertion.setProperty("XPath.whitespace",treeObj.getBooleanValue("whitespace"));
        xPathAssertion.setProperty("XPath.tolerant",treeObj.getBooleanValue("tolerant"));
        xPathAssertion.setProperty("XPath.namespace",treeObj.getBooleanValue("namespace"));
    }

    private static void writeJSONPathAssertion(JSONPathAssertion jsonPathAssertion, JSONObject treeObj) {
        jsonPathAssertion.setEnabled(treeObj.getBooleanValue("enabled"));
        jsonPathAssertion.setName(treeObj.getString("name"));
        jsonPathAssertion.setProperty("JSON_PATH",treeObj.getString("JSON_PATH"));
        jsonPathAssertion.setProperty("EXPECTED_VALUE",treeObj.getString("EXPECTED_VALUE"));
        jsonPathAssertion.setProperty("JSONVALIDATION",treeObj.getBooleanValue("JSONVALIDATION"));
        jsonPathAssertion.setProperty("EXPECT_NULL",treeObj.getBooleanValue("EXPECT_NULL"));
        jsonPathAssertion.setProperty("INVERT",treeObj.getBooleanValue("INVERT"));
        jsonPathAssertion.setProperty("ISREGEX",treeObj.getBooleanValue("ISREGEX"));



    }

    private static void writeSwitchController(SwitchController switchController, JSONObject treeObj) {
        switchController.setEnabled(treeObj.getBooleanValue("enabled"));
        switchController.setName(treeObj.getString("name"));
        switchController.setProperty("SwitchController.style",treeObj.getString("style"));
    }

    private static void writeThroughputController(ThroughputController throughputController, JSONObject treeObj) {
        throughputController.setEnabled(treeObj.getBooleanValue("enabled"));
        throughputController.setName(treeObj.getString("name"));


    }

    private static void writeOnceOnlyController(OnceOnlyController onceOnlyController, JSONObject treeObj) {
        onceOnlyController.setEnabled(treeObj.getBooleanValue("enabled"));
        onceOnlyController.setName(treeObj.getString("name"));


    }

    private static void writeWhileController(WhileController whileController, JSONObject treeObj) {
        whileController.setEnabled(treeObj.getBooleanValue("enabled"));
        whileController.setName(treeObj.getString("name"));
        whileController.setProperty("WhileController.condition",treeObj.getString("condition"));
    }

    private static void writeLoopController(LoopController loopController, JSONObject treeObj) {
        loopController.setEnabled(treeObj.getBooleanValue("enabled"));
        loopController.setName(treeObj.getString("name"));
        loopController.setProperty("LoopController.continue_forever",treeObj.getString("continue_forever"));
        loopController.setProperty("LoopController.loops",treeObj.getString("loops"));
    }

    private static void writeIfController(IfController ifController, JSONObject treeObj) {
        ifController.setEnabled(treeObj.getBooleanValue("enabled"));
        ifController.setName(treeObj.getString("name"));
    }

    private static void writeTransactionController(TransactionController transactionController, JSONObject treeObj) {
        transactionController.setEnabled(treeObj.getBooleanValue("enabled"));
        transactionController.setName(treeObj.getString("name"));

    }

    private static void writeHTTPSamplerProxy(HTTPSamplerProxy httpSamplerProxy, JSONObject treeObj) {
        httpSamplerProxy.setEnabled(treeObj.getBooleanValue("enabled"));
        httpSamplerProxy.setName(treeObj.getString("name"));

        JSONArray files=treeObj.getJSONArray("files");
        if(files!=null &&files.size()>0){
            HTTPFileArg[] httpFileArgs=new HTTPFileArg[files.size()];
            for(int i=0;i<files.size();i++){
                JSONObject jsonF=(JSONObject) files.get(i);

                HTTPFileArg httpFileArg=new HTTPFileArg();
                httpFileArg.setProperty("File.path",jsonF.getString("path"));
                httpFileArg.setProperty("File.paramname",jsonF.getString("paramname"));
                httpFileArg.setProperty("File.mimetype",jsonF.getString("mimetype"));
                httpFileArgs[i]=httpFileArg;
            }
            httpSamplerProxy.setHTTPFiles(httpFileArgs);
        }

        httpSamplerProxy.setProperty("HTTPSampler.domain",treeObj.getString("domain"));
        httpSamplerProxy.setProperty("HTTPSampler.port",treeObj.getString("port"));
        httpSamplerProxy.setProperty("HTTPSampler.protocol",treeObj.getString("protocol"));
        httpSamplerProxy.setProperty("HTTPSampler.contentEncoding",treeObj.getString("contentEncoding"));
        httpSamplerProxy.setProperty("HTTPSampler.method",treeObj.getString("method"));
        httpSamplerProxy.setProperty("HTTPSampler.follow_redirects",treeObj.getString("follow_redirects"));
        httpSamplerProxy.setProperty("HTTPSampler.auto_redirects",treeObj.getString("auto_redirects"));
        httpSamplerProxy.setProperty("HTTPSampler.use_keepalive",treeObj.getString("use_keepalive"));
        httpSamplerProxy.setProperty("HTTPSampler.DO_MULTIPART_POST",treeObj.getString("DO_MULTIPART_POST"));
        httpSamplerProxy.setProperty("HTTPSampler.embedded_url_re",treeObj.getString("embedded_url_re"));
        httpSamplerProxy.setProperty("HTTPSampler.connect_timeout",treeObj.getString("connect_timeout"));
        httpSamplerProxy.setProperty("HTTPSampler.response_timeout",treeObj.getString("response_timeout"));

        JSONArray argArray = treeObj.getJSONArray("arguments");
        Arguments arguments = httpSamplerProxy.getArguments();
        arguments.clear();
        writeArguments(arguments,Arrays.asList("name", "value", "metadata", "always_encode", "use_equals"), argArray);
    }

    private static void writeJavaConfigr(JavaConfig javaConfig, JSONObject treeObj) {
        javaConfig.setEnabled(treeObj.getBooleanValue("enabled"));
        javaConfig.setName(treeObj.getString("name"));


        JSONArray argArray = treeObj.getJSONArray("arguments");
        Arguments arguments = javaConfig.getArguments();
        arguments.clear();
        writeArguments(arguments,Arrays.asList("name", "value", "metadata"), argArray);
    }


    private static void writeCookieManager(CookieManager cookieManager, JSONObject treeObj) {
        cookieManager.setEnabled(treeObj.getBooleanValue("enabled"));
        cookieManager.setName(treeObj.getString("name"));
        JSONArray cookies=treeObj.getJSONArray("cookies");
        if(cookies!=null && cookies.size()>0){
            cookieManager.clear();
            for (Object obj : cookies) {
                JSONObject cookieObj = (JSONObject) obj;

                Cookie cookie =new Cookie();
                cookie.setProperty("Header.name",cookieObj.getString("name"));
                cookie.setProperty("Header.value",cookieObj.getString("value"));

                cookie.setProperty("Header.domain",cookieObj.getString("domain"));
                cookie.setProperty("Header.path",cookieObj.getString("path"));
                cookie.setProperty("Header.secure",cookieObj.getString("secure"));
                cookie.setProperty("Header.expires",cookieObj.getString("expires"));
                cookie.setProperty("Header.path_specified",cookieObj.getString("path_specified"));
                cookie.setProperty("Header.domain_specified",cookieObj.getString("domain_specified"));

                cookieManager.add(cookie);
            }
        }
    }

    private static void writeHeaderManager(HeaderManager headerManager, JSONObject treeObj) {
        headerManager.setEnabled(treeObj.getBooleanValue("enabled"));
        headerManager.setName(treeObj.getString("name"));
        JSONArray headers=treeObj.getJSONArray("headers");
        if(headers!=null && headers.size()>0){
            headerManager.clear();
            for (Object obj : headers) {
                JSONObject headObj = (JSONObject) obj;

                Header header =new Header();
                header.setProperty("Header.name",headObj.getString("name"));
                header.setProperty("Header.value",headObj.getString("value"));
                headerManager.add(header);
            }
        }


    }

    private static void writeSteppingThreadGroup(SteppingThreadGroup steppingThreadGroup, JSONObject treeObj) {
        steppingThreadGroup.setEnabled(treeObj.getBooleanValue("enabled"));
        steppingThreadGroup.setName(treeObj.getString("name"));
        steppingThreadGroup.setProperty("ThreadGroup.on_sample_error", treeObj.getString("on_sample_error"));
        steppingThreadGroup.setProperty("ThreadGroup.num_threads", treeObj.getIntValue("num_threads"));


        steppingThreadGroup.setProperty("Threads initial delay", treeObj.getString("threads_initial_delay"));
        steppingThreadGroup.setProperty("Start users count", treeObj.getString("start_users_count"));
        steppingThreadGroup.setProperty("Start users count burst", treeObj.getString("start_users_count_burst"));
        steppingThreadGroup.setProperty("start_users_period", treeObj.getString("Start users period"));
        steppingThreadGroup.setProperty("stop_users_count", treeObj.getString("Stop users count"));

        steppingThreadGroup.setProperty("stop_users_period", treeObj.getString("Stop users period"));
        steppingThreadGroup.setProperty("flighttime", treeObj.getString("flighttime"));
        steppingThreadGroup.setProperty("rampUp", treeObj.getString("rampUp"));

        JSONObject loopJson = treeObj.getJSONObject("loop");
        JMeterProperty loopPro = steppingThreadGroup.getProperty("ThreadGroup.main_controller");
        LoopController loopController = (LoopController) loopPro.getObjectValue();
        loopController.setProperty("LoopController.continue_forever", loopJson.getBooleanValue("continue_forever"));
        loopController.setProperty("LoopController.loops", loopJson.getIntValue("loops"));

        loopPro.setObjectValue(loopController);

        steppingThreadGroup.setProperty(loopPro);


    }

    private static void writeSetupThreadGroup(SetupThreadGroup setupThreadGroup, JSONObject treeObj) {

        setupThreadGroup.setEnabled(treeObj.getBooleanValue("enabled"));
        setupThreadGroup.setName(treeObj.getString("name"));
        setupThreadGroup.setProperty("ThreadGroup.on_sample_error", treeObj.getString("on_sample_error"));
        setupThreadGroup.setProperty("ThreadGroup.num_threads", treeObj.getIntValue("num_threads"));
        setupThreadGroup.setProperty("ThreadGroup.ramp_time", treeObj.getIntValue("ramp_time"));
        setupThreadGroup.setProperty("ThreadGroup.scheduler", treeObj.getBooleanValue("scheduler"));
        setupThreadGroup.setProperty("ThreadGroup.duration", treeObj.getIntValue("duration"));
        setupThreadGroup.setProperty("ThreadGroup.delay", treeObj.getIntValue("delay"));
        setupThreadGroup.setProperty("ThreadGroup.same_user_on_next_iteration", treeObj.getBooleanValue("same_user_on_next_iteration"));


        JSONObject loopJson = treeObj.getJSONObject("loop");
        JMeterProperty loopPro = setupThreadGroup.getProperty("ThreadGroup.main_controller");
        LoopController loopController = (LoopController) loopPro.getObjectValue();
        loopController.setProperty("LoopController.continue_forever", loopJson.getBooleanValue("continue_forever"));
        loopController.setProperty("LoopController.loops", loopJson.getIntValue("loops"));

        loopPro.setObjectValue(loopController);

        setupThreadGroup.setProperty(loopPro);

    }

    private static void writeThreadGroup(ThreadGroup threadGroup, JSONObject treeObj) {

        threadGroup.setEnabled(treeObj.getBooleanValue("enabled"));
        threadGroup.setName(treeObj.getString("name"));
        threadGroup.setProperty("ThreadGroup.on_sample_error", treeObj.getString("on_sample_error"));
        threadGroup.setProperty("ThreadGroup.num_threads", treeObj.getIntValue("num_threads"));
        threadGroup.setProperty("ThreadGroup.ramp_time", treeObj.getIntValue("ramp_time"));
        threadGroup.setProperty("ThreadGroup.scheduler", treeObj.getBooleanValue("scheduler"));
        threadGroup.setProperty("ThreadGroup.duration", treeObj.getIntValue("duration"));
        threadGroup.setProperty("ThreadGroup.delay", treeObj.getIntValue("delay"));
        threadGroup.setProperty("ThreadGroup.same_user_on_next_iteration", treeObj.getBooleanValue("same_user_on_next_iteration"));


        JSONObject loopJson = treeObj.getJSONObject("loop");
        JMeterProperty loopPro = threadGroup.getProperty("ThreadGroup.main_controller");
        LoopController loopController = (LoopController) loopPro.getObjectValue();
        loopController.setProperty("LoopController.continue_forever", loopJson.getBooleanValue("continue_forever"));
        loopController.setProperty("LoopController.loops", loopJson.getIntValue("loops"));

        loopPro.setObjectValue(loopController);

        threadGroup.setProperty(loopPro);
    }

    private static void writeTestPlan(TestPlan testPlan, JSONObject treeObj) {
        testPlan.setProperty("TestPlan.comments", treeObj.getString("comments"));
        testPlan.setProperty("TestPlan.functional_mode", treeObj.getString("functional_mode"));
        testPlan.setProperty("TestPlan.tearDown_on_shutdown", treeObj.getString("tearDown_on_shutdown"));
        testPlan.setProperty("TestPlan.serialize_threadgroups", treeObj.getString("serialize_threadgroups"));
        testPlan.setProperty("TestPlan.user_define_classpath", treeObj.getString("user_define_classpath"));
        testPlan.setName(treeObj.getString("name"));
        testPlan.setEnabled(treeObj.getBoolean("enabled"));

        JSONArray argArray = treeObj.getJSONArray("arguments");
        Arguments arguments = testPlan.getArguments();
        arguments.clear();
        writeArguments(arguments, Arrays.asList("name", "value", "metadata"), argArray);

    }

    private static void writeArgument(Arguments arguments, List<String> argList, JSONObject jsonObject) {
        arguments.setEnabled(jsonObject.getBooleanValue("enabled"));
        arguments.setName(jsonObject.getString("name"));
        JSONArray argArray = jsonObject.getJSONArray("arguments");
        arguments.clear();
        writeArguments(arguments, Arrays.asList("name", "value", "metadata"), argArray);
    }

    private static void writeArguments(Arguments arguments, List<String> argList, JSONArray argArray) {
        for (Object obj : argArray) {
            JSONObject argJson = (JSONObject) obj;
            Argument argument = new Argument();

            for (String str : argList) {
                argument.setProperty("Argument." + str, argJson.getString(str));
            }
            arguments.addArgument(argument);
        }
    }
}
