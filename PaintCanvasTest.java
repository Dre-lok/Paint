package com.example.paintapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaintCanvasTest {
    PaintCanvas canvas = new PaintCanvas();

    @Test
    void canvasSizeCheck(){
        assertEquals(1750, canvas.getCanvasHeight());
        assertEquals(950, canvas.getCANVAS_WIDTH());
    }

    @Test
    void sizeNullCheck(){
        assertNull(canvas.getSize());
    }

    @Test
    void gcIsClear(){
        assertNull(canvas.getGc());
    }
}