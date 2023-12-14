package ru.mystudy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class WallTest {

    @Test
    @DisplayName("Тест без объекта")
    public void testing_with_null_object() {
        Assertions.assertNull(Utils.cache(null));
    }

    @Test
    @DisplayName("Тест без изменения состояния")
    public void testing_without_change_state() {
        Wall spyWall = Mockito.spy(new Wall(10, 4));
        Wallable cacheWall = Utils.cache(spyWall);

        cacheWall.getSquare();   // 1 time
        cacheWall.getSquare();   // cache
        Mockito.verify(spyWall, times(1)).getSquare();
    }

    @Test
    @DisplayName("Тест после смены состояния")
    public void testing_with_change_state() {
        Wall spyWall = Mockito.spy(new Wall(10, 4));
        Wallable cacheWall = Utils.cache(spyWall);

        cacheWall.getSquare();   // 1 time
        cacheWall.setLength(9);
        cacheWall.getSquare();   // 2 time
        cacheWall.getSquare();   // cache
        Mockito.verify(spyWall, times(2)).getSquare();
    }

    @Test
    @DisplayName("Тест после возврата в кэшированное состояние")
    public void testing_return_to_cache_state() {
        Wall spyWall = Mockito.spy(new Wall(10, 4));
        Wallable cacheWall = Utils.cache(spyWall);

        cacheWall.getSquare();   // 1 time
        cacheWall.setLength(9);
        cacheWall.getSquare();   // 2 time
        cacheWall.setLength(10);
        cacheWall.getSquare();   // cache
        Mockito.verify(spyWall, times(2)).getSquare();
    }

    @Test
    @DisplayName("Тест удаления из кэша")
    public void testing_remove_from_cache_state() throws Exception {
        Wall spyWall = Mockito.spy(new Wall(10, 4));
        Wallable cacheWall = Utils.cache(spyWall);

        cacheWall.getSquare();   // 1 time
        Thread.sleep(1200);// 1200 > 1000  - clear value to cache
        cacheWall.getSquare();   // 2 time
        Mockito.verify(spyWall, times(2)).getSquare();
    }

    @Test
    @DisplayName("Тест с ожиданием, но без удаления из кэша")
    public void testing_timeout_without_remove_from_cache_state() throws Exception {
        Wall spyWall = Mockito.spy(new Wall(10, 4));
        Wallable cacheWall = Utils.cache(spyWall);

        cacheWall.getSquare();   // 1 time
        Thread.sleep(800); // 800 < 1000
        cacheWall.getSquare();   // cache
        Mockito.verify(spyWall, times(1)).getSquare();
    }
}
