package com.tjr.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "aa1",
    "aa2",
    "aa3"
   
})
@XmlRootElement(name = "eapp:aaaaaa")
public class BodyContent {

          @XmlElement(required = true,name="eapp:compNo")
        protected String aa1;
        @XmlElement(required = true,name="eapp:agentCode")
        protected String aa2;
        @XmlElement(required = true,name="eapp:eAppId")
        protected String aa3;              
        //get set方法省略
    
}