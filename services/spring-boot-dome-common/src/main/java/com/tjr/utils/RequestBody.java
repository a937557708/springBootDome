package com.tjr.utils;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "soapenv:Body")
public class RequestBody {

    
    @XmlElement(required = true,name="eapp:aaaaaa")
    public BodyContent aaaaaaa;
    //get set方法省略
    
}


