package list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleLinkedListTest {

    @Test
    void testAdd_ShouldAddElements_WhenSingleLinkedListIsEmpty() {
        SingleLinkedList<String> list = new SingleLinkedList<>(); // тут то же самое

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testAdd_ShouldAddElementsWithIndex_WhenSingleLinkedListIsEmpty() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("супер");
        list.add("Java");
        list.add("программист");
        list.add(0, "Я");
        list.add(1, ",");

        assertAll("",
                () -> assertEquals(5, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals(",", list.get(1)),
                () -> assertEquals("супер", list.get(2)),
                () -> assertEquals("Java", list.get(3)),
                () -> assertEquals("программист", list.get(4))
        );
    }

    @Test
    void testAdd_ShouldWrongEmptyList_WhenSingleLinkedListIsEmpty() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        assertThrows(IllegalArgumentException.class, () -> list.add(2, "!"));
    }

    @Test
    void testSet_ShouldSetElementsByIndex_WhenSingleLinkedListHasElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");
        list.set(3, "QA");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("QA", list.get(3))
        );
    }

    @Test
    void testSet_ShouldWrongSetElementsByIndex_WhenSingleLinkedListIsEmpty() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        assertThrows(IllegalArgumentException.class, () -> list.set(2, "!"));
    }

    @Test
    void testIndexOf_ShouldGetElementByIndex_WhenSingleLinkedListHasElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertEquals(3, list.indexOf("программист"));
        assertEquals(-1, list.indexOf("QA"));
    }

    @Test
    void testRemoveOneElement_ShouldRemoveElementByIndex_WhenSingleLinkedListHasOneElement() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.remove(0);

        assertEquals(0, list.size());

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemovedFirstOfTwoElement_ShouldRemoveElementByIndex_WhenSingleLinkedListHasTwoElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.remove(0);

        assertEquals(1, list.size());
        assertEquals("супер", list.get(0));

        list.add(0, "Я");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemoveSecondOfTwoElement_ShouldRemoveElementByIndex_WhenSingleLinkedListHasTwoElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.remove(1);

        assertEquals(1, list.size());
        assertEquals("Я", list.get(0));

        list.add(1, "супер");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemoveFirstElementOfMany_ShouldRemoveElementByIndex_WhenSingleLinkedListHasManyElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        list.remove(0);

        assertAll("",
                () -> assertEquals(3, list.size()),
                () -> assertEquals("супер", list.get(0)),
                () -> assertEquals("Java", list.get(1)),
                () -> assertEquals("программист", list.get(2))
        );

        list.add(0, "Я");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemoveLastOfMany_ShouldRemoveElementByIndex_WhenSingleLinkedListHasManyElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        list.remove(3);

        assertAll("",
                () -> assertEquals(3, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2))
        );

        list.add(3, "программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );

    }

    @Test
    void testRemoveOfMany_ShouldRemoveElementByIndex_WhenSingleLinkedListHasManyElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        list.remove(2);

        assertAll("",
                () -> assertEquals(3, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("программист", list.get(2))
        );

        list.add(2, "Java");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );

    }

    @Test
    void testRemoveElementOfElements_ShouldRemoveElementByElement_WhenSingleLinkedListHasOneElement() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");

        assertTrue(list.remove("Я"));

        assertEquals(0, list.size());

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );

    }

    @Test
    void testRemoveFirstOfTwoElementOfElement_ShouldRemoveElementByElement_WhenSingleLinkedListHasTwoElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");

        assertTrue(list.remove("Я"));
        assertEquals(1, list.size());
        assertEquals("супер", list.get(0));

        list.add(0, "Я");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemoveSecondOfTwoElementOfElement_ShouldRemoveElementByElement_WhenSingleLinkedListHasTwoElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");

        assertTrue(list.remove("супер"));
        assertEquals(1, list.size());
        assertEquals("Я", list.get(0));

        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemoveFirstOfManyOfElements_ShouldRemoveElementByElement_WhenSingleLinkedListHasManyElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertTrue(list.remove("Я"));

        assertAll("",
                () -> assertEquals(3, list.size()),
                () -> assertEquals("супер", list.get(0)),
                () -> assertEquals("Java", list.get(1)),
                () -> assertEquals("программист", list.get(2))
        );

        list.add(0, "Я");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemoveLastOfManyOfElements_ShouldRemoveElementByElement_WhenSingleLinkedListHasManyElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertTrue(list.remove("программист"));

        assertAll("",
                () -> assertEquals(3, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2))
        );

        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );

    }

    @Test
    void testRemoveOfManyOfElements_ShouldRemoveElementByElement_WhenSingleLinkedListHasManyElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertTrue(list.remove("супер"));

        assertAll("",
                () -> assertEquals(3, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("Java", list.get(1)),
                () -> assertEquals("программист", list.get(2))
        );

        list.add(1, "супер");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    @Test
    void testRemoveOfEmptyOfElements_ShouldRemoveElementByElement_WhenSingleLinkedListIsEmpty() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        assertFalse(list.remove("Hello"));
        assertEquals(0, list.size());

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );

    }

    @Test
    void testClear_ShouldRemoveAllElements_WhenSingleLinkedListHasManyElements() {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        list.clear();
        assertEquals(0, list.size());

    }

}