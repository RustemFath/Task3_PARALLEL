package ru.mystudy;

import org.junit.jupiter.api.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class NTest {

    private Wallable cacheWall;

    private long startTime;

    @BeforeAll
    public void beforeAll() {
        startTime = System.currentTimeMillis();

        cacheWall = Utils.cache(new Wall(1, 4));
        cacheWall.getSquare();

        for (int i = 2; i < 100; i++) {
            cacheWall.setLength(i);
            cacheWall.getSquare();
        }
        cacheWall.setLength(1);
        System.out.println("beforeAll end: Прошло времени, мс: " + (System.currentTimeMillis() - startTime));
    }

    @DisplayName("Вызов кэшированного метода")
    @RepeatedTest(10000)
    public void test_cache_method(RepetitionInfo repetitionInfo) {
        cacheWall.setLength(repetitionInfo.getCurrentRepetition()%100);
        cacheWall.getSquare();
    }

    @AfterAll
    public void afterAll() {
        System.out.println("afterAll end: Прошло времени, мс: " + (System.currentTimeMillis() - startTime));
    }
}
