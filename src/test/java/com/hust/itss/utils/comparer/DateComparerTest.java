package com.hust.itss.utils.comparer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateComparerTest {

    @Before
    public void before(){
        System.out.println("BEFORE TEST, Now: " + new Date());
    }

    @Test
    public void test1(){
        System.out.println("Test 1:... ");
        Date date = new Date();
        date.setMonth(11);
        date.setDate(11);
        assertFalse(DateComparer.afterNow(date, "10:15AM"));
    }

    @Test
    public void test2(){
        System.out.println("Test 2:... ");
        Date date = new Date();
        date.setMonth(11);
        date.setDate(14);
        assertTrue(DateComparer.afterNow(date, "02:10AM"));
    }

    @After
    public void after(){
        System.out.println("AFTER TEST.");

    }
}