package com.tjr.selenium.service;

import autoitx4java.AutoItX;
import com.jacob.com.LibraryLoader;

import java.io.File;
import java.util.Locale;

public class SeleniumService {


    /**

     * 调用AutoITX包进行文件上传操作

     */

    public void autoUploadMode(String filePath){

        try {

            AutoItX auto=new AutoItX();

            auto.winActivate("打开");

            auto.winWait("[CLASS:#32770]","",10);

            auto.ControlSetText("打开", "", "Edit1", filePath);

            auto.sleep(500);

            auto.controlClick("打开", "", "Button1");

            Thread.sleep(2000);

        } catch (Exception e) {

            // TODO: handle exception

        }

    }

    public static void main(String[] args) {
        File file = new File("lib", "jacob-1.18-x64.dll");
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
     new SeleniumService().autoUploadMode("F:\\教程\\深入JVM内核—原理、诊断与优化\\总纲\\certpic_uid_29825_lesson_184_certtype_3_1415322091.jpg");
    }

}
