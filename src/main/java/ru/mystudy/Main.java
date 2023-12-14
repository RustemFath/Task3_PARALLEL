package ru.mystudy;

public class Main {
    public static void main(String[] args) throws Exception {
        Wall origWall = new Wall(10, 4);
        Wallable cacheWall = Utils.cache(origWall);

        cacheWall.getSquare();
        cacheWall.getSquare();

        cacheWall.setLength(9);
        cacheWall.getSquare();
        cacheWall.getSquare();

        cacheWall.setLength(10);
        cacheWall.getSquare();
        cacheWall.getSquare();

        Thread.sleep(1500);
        cacheWall.getSquare();
        cacheWall.getSquare();
    }
}