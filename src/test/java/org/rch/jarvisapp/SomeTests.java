package org.rch.jarvisapp;

import org.assertj.core.util.Arrays;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@SpringBootTest
public class SomeTests {

    @Test
    void start(){
        System.out.println("123");
    }

    @Test
    void JSON2String(){
        String str1 = "{\"qwe\" : \"asd\"}";
        JSONObject obj = new JSONObject(str1);
        System.out.println(obj.toString());
    }

    @Test
    void NullTest(){
        Integer i;

    }

    @Test
    void LimitList(){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        logger.debug("hello");
        list.stream().limit(3).forEach(System.out::println);
    }

}
