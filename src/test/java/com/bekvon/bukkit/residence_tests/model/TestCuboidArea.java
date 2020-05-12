package com.bekvon.bukkit.residence_tests.model;


import com.bekvon.bukkit.residence.model.CuboidArea;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestCuboidArea {

    @Test
    public void testNullWorld() {
        Assertions.assertThrows(RuntimeException.class, () -> new CuboidArea(null, 0, 0, 0, 0, 0, 0));
    }

    @Test
    public void testSizes() {
        World w = Mockito.mock(World.class);
        CuboidArea area = new CuboidArea(w, 0, 0, 0, 6, 7, 8);
        Assertions.assertEquals(area.getXSize(), 7);
        Assertions.assertEquals(area.getYSize(), 8);
        Assertions.assertEquals(area.getZSize(), 9);
    }

    @Test
    public void testArea() {
        World w = Mockito.mock(World.class);
        CuboidArea area = new CuboidArea(w, 0, 0, 0, 6, 7, 8);
        Assertions.assertEquals(area.getSize(false), 7*9);
        Assertions.assertEquals(area.getSize(true), 7*8*9);
    }

    @Test
    public void highTest() {
        World w = Mockito.mock(World.class);
        CuboidArea area = new CuboidArea(w, 5, 99, 4, 1, 99, 8);
        Location l = area.getHighLoc();
        Assertions.assertEquals(l.getBlockX(), 5);
        Assertions.assertEquals(l.getBlockY(), 99);
        Assertions.assertEquals(l.getBlockZ(), 8);
    }

    @Test
    public void lowTest() {
        World w = Mockito.mock(World.class);
        CuboidArea area = new CuboidArea(w, 5, 99, 4, 1, 99, 8);
        Location l = area.getLowLoc();
        Assertions.assertEquals(l.getBlockX(), 1);
        Assertions.assertEquals(l.getBlockY(), 99);
        Assertions.assertEquals(l.getBlockZ(), 4);
    }

    @Test
    public void getWorldTest() {
        World w = Mockito.mock(World.class);
        CuboidArea area = new CuboidArea(w, 5, 99, 4, 1, 99, 8);
        Assertions.assertSame(area.getWorld(), w);
    }

    @Test
    public void containsTest() {
        World w1 = Mockito.mock(World.class);
        World w2 = Mockito.mock(World.class);
        Mockito.when(w1.getName()).thenReturn("world1");
        Mockito.when(w2.getName()).thenReturn("world2");

        CuboidArea area = new CuboidArea(w1, 0, 0, 0, 20, 30, 40);
        Location l1 = new Location(w1, 1, 1, 1);
        Location l2 = new Location(w2, 1, 1, 1);
        Assertions.assertTrue(area.containsLoc(l1));
        Assertions.assertFalse(area.containsLoc(l2));

        Location l12 = new Location(w1, -1, 1, 1);
        Location l13 = new Location(w1, 1, -1, 1);
        Location l14 = new Location(w1, 1, 1, -1);
        Location l15 = new Location(w1, 100, 1, 1);
        Location l16 = new Location(w1, 1, 100, 1);
        Location l17 = new Location(w1, 1, 1, 100);
        Assertions.assertFalse(area.containsLoc(l12));
        Assertions.assertFalse(area.containsLoc(l13));
        Assertions.assertFalse(area.containsLoc(l14));
        Assertions.assertFalse(area.containsLoc(l15));
        Assertions.assertFalse(area.containsLoc(l16));
        Assertions.assertFalse(area.containsLoc(l17));

        Location linf = new Location(w1, 0, 0, 0);
        Location lsup = new Location(w1, 20, 30, 40);
        Assertions.assertTrue(area.containsLoc(linf));
        Assertions.assertTrue(area.containsLoc(lsup));
    }

    @Test
    public void containsCuboidTest() {
        World w1 = Mockito.mock(World.class);
        World w2 = Mockito.mock(World.class);
        Mockito.when(w1.getName()).thenReturn("world1");
        Mockito.when(w2.getName()).thenReturn("world2");

        CuboidArea area = new CuboidArea(w1, 0, 0, 0, 20, 30, 40);
        CuboidArea areaEq = new CuboidArea(w1, 0, 0, 0, 20, 30, 40);
        CuboidArea areaDifWorld = new CuboidArea(w2, 0, 0, 0, 20, 30, 40);
        Assertions.assertTrue(area.contains(areaEq));
        Assertions.assertTrue(areaEq.contains(area));
        Assertions.assertFalse(area.contains(areaDifWorld));
        Assertions.assertFalse(areaDifWorld.contains(area));

        CuboidArea bigger = new CuboidArea(w1, -100, -100, -100, 100, 100, 100);
        Assertions.assertTrue(bigger.contains(area));
        Assertions.assertFalse(area.contains(bigger));

        // Only two coords in -> False
        CuboidArea xmin = new CuboidArea(w1, -1, 1, 1, 10, 5, 4);
        CuboidArea xmax = new CuboidArea(w1, 1, -1, 1, 10, 5, 4);
        CuboidArea ymin = new CuboidArea(w1, 1, 1, -1, 10, 5, 4);
        CuboidArea ymax = new CuboidArea(w1, 1, 1, 1, 100, 5, 4);
        CuboidArea zmin = new CuboidArea(w1, 1, 1, 1, 10, 500, 4);
        CuboidArea zmax = new CuboidArea(w1, 1, 1, 1, 10, 5, 400);

        Assert.assertFalse(area.contains(xmin));
        Assert.assertFalse(xmin.contains(area));

        Assert.assertFalse(area.contains(xmax));
        Assert.assertFalse(xmax.contains(area));

        Assert.assertFalse(area.contains(ymin));
        Assert.assertFalse(ymin.contains(area));

        Assert.assertFalse(area.contains(ymax));
        Assert.assertFalse(ymax.contains(area));

        Assert.assertFalse(area.contains(zmin));
        Assert.assertFalse(zmin.contains(area));

        Assert.assertFalse(area.contains(zmax));
        Assert.assertFalse(zmax.contains(area));
    }

    @Test
    public void collidesCuboidTest() {
        World w1 = Mockito.mock(World.class);
        World w2 = Mockito.mock(World.class);
        Mockito.when(w1.getName()).thenReturn("world1");
        Mockito.when(w2.getName()).thenReturn("world2");

        CuboidArea area = new CuboidArea(w1, 0, 0, 0, 20, 30, 40);
        CuboidArea areaDifWorld = new CuboidArea(w2, 0, 0, 0, 20, 30, 40);

        Assertions.assertFalse(area.collides(areaDifWorld));
        Assertions.assertFalse(areaDifWorld.collides(area));

        CuboidArea commonPoint = new CuboidArea(w1, -1000, -1000, -1000, 0, 0, 0);
        Assertions.assertTrue(area.collides(commonPoint));
        Assertions.assertTrue(commonPoint.collides(area));

        CuboidArea almostEquals = new CuboidArea(w1, 1, 1, 1, 19, 29, 39);
        CuboidArea equals = new CuboidArea(w1, 1, 1, 1, 19, 29, 39);
        Assertions.assertTrue(area.collides(almostEquals));
        Assertions.assertTrue(almostEquals.collides(area));
        Assertions.assertTrue(area.collides(equals));
        Assertions.assertTrue(equals.collides(area));

        CuboidArea stickX = new CuboidArea(w1, -1000, 0, 0, 1000, 0, 0);
        CuboidArea stickY = new CuboidArea(w1, 0, -100, 0, 0, 1000, 0);
        CuboidArea stickZ = new CuboidArea(w1, 0, 0, -1000, 0, 0, 1000);
        Assertions.assertTrue(area.collides(stickX));
        Assertions.assertTrue(stickX.collides(area));
        Assertions.assertTrue(area.collides(stickY));
        Assertions.assertTrue(stickY.collides(area));
        Assertions.assertTrue(area.collides(stickZ));
        Assertions.assertTrue(stickZ.collides(area));
    }
}
