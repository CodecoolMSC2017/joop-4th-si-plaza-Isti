package com.codecool.plaza.api;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoodProductTest {

    @Test
    void isStillConsumable() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2030/03/15");
        FoodProduct food = new FoodProduct("testFood", 987123654L, "testManufacturer", 5, date);

        assertTrue(food.isStillConsumable());

        date = sdf.parse("2001/08/01");
        food = new FoodProduct("testFood", 987123654L, "testManufacturer", 5, date);

        assertFalse(food.isStillConsumable());
    }
}