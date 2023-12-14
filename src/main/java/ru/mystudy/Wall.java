package ru.mystudy;

import java.util.Objects;

public class Wall implements Wallable, Savable {
    private int length;
    private int height;

    public Wall(int length, int height) {
        this.length = length;
        this.height = height;
    }

    @Override
    @Cache(1000)
    public int getSquare() {
        System.out.println("invoke getSquare");
        return length * height;
    }

    @Override
    @Mutator
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    @Mutator
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Object getSave() {
        return new WallSave(this);
    }

    private static class WallSave {
        private final int length;
        private final int height;

        public WallSave(Wall wall) {
            this.length = wall.length;
            this.height = wall.height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WallSave wall = (WallSave) o;
            return length == wall.length && height == wall.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(length, height);
        }
    }
}
