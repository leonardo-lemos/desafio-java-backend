package dev.leonardolemos.desafiobackendwine.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseEntityTest {

    @Test
    public void test_get_id_is_not_new() {
        DummyEntity entity = new DummyEntity();
        entity.setId(1L);

        boolean isNew = entity.getNew();

        assertFalse(isNew);
    }

    @Test
    public void test_get_id_is_new() {
        DummyEntity entity = new DummyEntity();

        boolean isNew = entity.getNew();

        assertTrue(isNew);
    }

    private class DummyEntity extends BaseEntity {
    }
}
