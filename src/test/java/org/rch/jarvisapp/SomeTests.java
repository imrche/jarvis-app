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

    @Test
    void Qwe(){
        Integer n = null;
        try {
            Integer i = 1 - n;
            System.out.println("yes");
        }catch (NullPointerException e){
            System.out.println("no");
        }
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        class Tst1{
            Integer s = 321;
        }
        class Tst2 implements Cloneable{
            Tst1 tst1 = new Tst1();

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return (Tst2)super.clone();
            }
        }

        Tst2 t1 = new Tst2();
        Tst2 t2 = (Tst2)t1.clone();

        System.out.println(t1.tst1.s.equals(t2.tst1.s));
        System.out.println("stop");
    }

}
