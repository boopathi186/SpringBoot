package com.attendance.attendance_management.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddTest {

    @Test
    void add() {
        Add add = new Add();
        assertEquals(8,add.add(3,5),"number should be 10");
    }
}