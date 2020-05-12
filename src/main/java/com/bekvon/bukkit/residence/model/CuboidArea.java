package com.bekvon.bukkit.residence.model;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CuboidArea {
    private World world;
    private int highx, highy, highz, lowx, lowy, lowz;

    public CuboidArea(Location startLoc, Location endLoc) {
        this(startLoc.getWorld(), startLoc.getBlockX(), startLoc.getBlockY(), startLoc.getBlockZ(), endLoc.getBlockX(), endLoc.getBlockY(), endLoc.getBlockZ());
        Assert.assertEquals(startLoc.getWorld(), endLoc.getWorld());
    }

    public CuboidArea(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        if(world == null){
            throw new IllegalArgumentException("Invalid null world");
        }
        this.world = world;
        highx = Math.max(x1, x2);
        highy = Math.max(y1, y2);
        highz = Math.max(z1, z2);
        lowx = Math.min(x1, x2);
        lowy = Math.min(y1, y2);
        lowz = Math.min(z1, z2);
    }

    public static CuboidArea newLoad(String root, World world) {
        if (root == null || !root.contains(":")) {
            throw new IllegalArgumentException("Invalid residence physical location...");
        }
        String[] split = root.split(":");
        CuboidArea newArea;
        try {
            int x1 = Integer.parseInt(split[0]);
            int y1 = Integer.parseInt(split[1]);
            int z1 = Integer.parseInt(split[2]);
            int x2 = Integer.parseInt(split[3]);
            int y2 = Integer.parseInt(split[4]);
            int z2 = Integer.parseInt(split[5]);
            newArea = new CuboidArea(world, x1, y1, z1, x2, y2, z2);
        } catch (Exception e) {
            throw new RuntimeException("Failed while constructing a new Cuboid Location", e);
        }

        return newArea;
    }

    public static CuboidArea load(Map<String, Object> root, World world) {
        if (root == null) {
            throw new IllegalArgumentException("Invalid residence physical location");
        }
        int x1 = (Integer) root.get("X1");
        int y1 = (Integer) root.get("Y1");
        int z1 = (Integer) root.get("Z1");
        int x2 = (Integer) root.get("X2");
        int y2 = (Integer) root.get("Y2");
        int z2 = (Integer) root.get("Z2");
        CuboidArea newArea = new CuboidArea(world, x1, y1, z1, x2, y2, z2);
        return newArea;
    }

    public boolean contains(CuboidArea area) {
        if(!this.getWorld().getName().equals(area.getWorld().getName())){
            return false;
        }
        return (this.containsPoint(area.lowx, area.lowy, area.lowz) && this.containsPoint(area.highx, area.highy, area.highz));
    }

    public boolean containsLoc(Location loc) {
        if (loc == null || !Objects.requireNonNull(loc.getWorld()).getName().equals(world.getName())) {
            return false;
        }

        return containsPoint(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public boolean containsPoint(int x, int y, int z){
        return x >= lowx && x <= highx && y >= lowy && y <= highy && z >= lowz && z <= highz;
    }
    
    public boolean collides(CuboidArea area) {
        if (!area.getWorld().equals(this.getWorld())) {
            return false;
        }

        // If any of the following conditions are met, we can theoretically draw a plane that separates the cubes in the remaining distance
        if (area.highx < this.lowx) return false;
        if (area.highy < this.lowy) return false;
        if (area.highz < this.lowz) return false;
        if (area.lowx > this.highx) return false;
        if (area.lowy > this.highy) return false;
        if (area.lowz > this.highz) return false;

        // Insersects otherwise
        return true;
    }

    public long getSize(boolean considerY) {
        // Residence.getInstance().getConfigManager().isNoCostForYBlocks()
        int xsize = highx - lowx + 1;
        int ysize = highy - lowy + 1;
        int zsize = highz - lowz + 1;

        return considerY ? xsize * ysize * zsize : xsize * zsize;
    }

    public int getXSize() {
        return highx - lowx + 1;
    }

    public int getYSize() {
        return highy - lowy + 1;
    }

    public int getZSize() {
        return highz - lowz + 1;
    }

    public Location getHighLoc() {
        return new Location(this.world, this.highx, this.highy, this.highz);
    }

    public Location getLowLoc() {
        return new Location(this.world, this.lowx, this.lowy, this.lowz);
    }

    public World getWorld() {
        return world;
    }

    public Map<String, Object> save() {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("X1", this.lowx);
        root.put("Y1", this.lowy);
        root.put("Z1", this.lowz);
        root.put("X2", this.highx);
        root.put("Y2", this.highy);
        root.put("Z2", this.highz);
        return root;
    }

    public String newSave() {
        return lowx + ":" + lowy + ":" + lowz + ":" + highx + ":" + highy + ":" + highz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuboidArea that = (CuboidArea) o;
        return highx == that.highx &&
                highy == that.highy &&
                highz == that.highz &&
                lowx == that.lowx &&
                lowy == that.lowy &&
                lowz == that.lowz &&
                world.equals(that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, highx, highy, highz, lowx, lowy, lowz);
    }
}
