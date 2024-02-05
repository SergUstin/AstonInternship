package list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleLinkedListTest {

    @Test
    void testAdd_ShouldAddedElement_WhenEmptyList() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

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
    void testAdd_ShouldAddedBeginElement_WhenListHasElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("супер");
        list.add("Java");
        list.add("программист");
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
    void testAdd_ShouldAddedFirstHalfElement_WhenListHasElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("программист");
        list.add("!");
        list.add(2, "Java");

        assertAll("",
                () -> assertEquals(5, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3)),
                () -> assertEquals("!", list.get(4))
        );
    }

    @Test
    void testAdd_ShouldAddedSecondHalfElement_WhenListHasElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("!");
        list.add(3, "программист");

        assertAll("",
                () -> assertEquals(5, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3)),
                () -> assertEquals("!", list.get(4))
        );
    }

    @Test
    void testIndexOf_ShouldGetIndexOfElement_WhenListHasElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals(0, list.indexOf("Я")),
                () -> assertEquals(1, list.indexOf("супер")),
                () -> assertEquals(2, list.indexOf("Java")),
                () -> assertEquals(3, list.indexOf("программист"))
        );
    }

    @Test
    void testRemoveByIndex_ShouldRemoveElementByIndex_WhenListHasElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

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
    void testRemoveOneElementByIndex_ShouldRemoveElementByIndex_WhenListHasOneElement() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");

        list.remove(0);

        assertEquals(0, list.size());
    }

    @Test
    void testRemoveFirstOfTwoElementsByIndex_ShouldRemoveElementByIndex_WhenListHasTwoElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");

        list.remove(0);
        assertEquals(1, list.size());
        assertEquals("супер", list.get(0));

    }

    @Test
    void testRemoveSecondOfTwoElementsByIndex_ShouldRemoveElementByIndex_WhenListHasTwoElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");

        list.remove(1);
        assertEquals(1, list.size());
        assertEquals("Я", list.get(0));

    }

    @Test
    void testRemoveFirstOfManyByIndex_ShouldRemoveElementByIndex_WhenListHasManyElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

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
    void testRemoveLastOfManyByIndex_ShouldRemoveElementByIndex_WhenListHasManyElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

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
    void testRemoveInMiddleOfManyByIndex_ShouldRemoveElementByIndex_WhenListHasManyElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

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
    void testRemoveOneElementByElement_ShouldRemoveElementByElement_WhenListHasOneElement() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        assertTrue(list.remove("Я"));
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveFirstElementOfTwoElementByElement_ShouldRemoveElementByElement_WhenListHasTwoElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");

        assertTrue(list.remove("Я"));
        assertEquals(1, list.size());
        assertEquals("супер", list.get(0));

    }

    @Test
    void testRemoveSecondElementOfTwoElementByElement_ShouldRemoveElementByElement_WhenListHasTwoElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");

        assertTrue(list.remove("супер"));
        assertEquals(1, list.size());
        assertEquals("Я", list.get(0));

    }

    @Test
    void testRemoveFirstOfManyByElement_ShouldRemoveElementByElement_WhenListHasManyElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

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
    void testRemoveLastOfManyByElement_ShouldRemoveElementByElement_WhenListHasManyElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

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
    void testRemoveInMiddleOfManyByElement_ShouldRemoveElementByElement_WhenListHasManyElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertTrue(list.remove("Java"));

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
    void testRemoveNotExistElementOfManyByElement_ShouldRemoveElementByElement_WhenListIsEmpty() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        assertThrows(IllegalArgumentException.class, () -> list.remove(4));

    }

    @Test
    void testClear_ShouldRemoveAllElements_WhenListHasManyElements() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        list.add("Я");
        list.add("супер");
        list.add("Java");
        list.add("программист");

        list.clear();
        assertEquals(0, list.size());
    }
}