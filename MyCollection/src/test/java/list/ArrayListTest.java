package list;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static list.ArrayList.mergeSort;
import static list.ArrayList.quickSort;
import static org.junit.jupiter.api.Assertions.*;

// тесты очень хорошие, молодец!
class ArrayListTest {

    private ArrayList<String> list;

    private void testStandardPhraseFourElement_ShouldAddedElement_WhenEmptyList() {
        assertAll("Проверяем расположение в листе элементов",
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("программист", list.get(3))
        );
    }

    private void testStandardPhraseFiveElement_ShouldAddedElementToEnd_WhenListHasManyElements() {
        assertAll("Проверяем расположение в листе элементов",
                this::testStandardPhraseFourElement_ShouldAddedElement_WhenEmptyList,
                () -> assertEquals("!", list.get(4))
        );
    }

    private void fillListThreeElement() {
        list.add("супер");
        list.add("Java");
        list.add("программист");
    }

    private void fillListFourElement() {
        list.add("Я");
        fillListThreeElement();
    }

    private void fillListFiveElement() {
        fillListFourElement();
        list.add("!");
    }

    @Test
    void testIndexOf_ShouldGetElementByIndex_WhenListHasManyElements() {
        list = new ArrayList<>(3);
        fillListThreeElement();
        list.add(0, "Я");
        list.add("!");
        assertAll("Сценарий проверки метода indexOf",
                this::testStandardPhraseFourElement_ShouldAddedElement_WhenEmptyList,
                () -> assertEquals(4, list.indexOf("!")),
                () -> assertEquals(-1, list.indexOf("Hello"))
        );
    }

    @Test
    void testAdd_ShouldAddedElements_WhenListHasManyElements() {
        list = new ArrayList<>();
        fillListFourElement();
        assertAll("Сценарий проверки метода indexOf",
                () -> assertEquals(4, list.size()),
                this::testStandardPhraseFourElement_ShouldAddedElement_WhenEmptyList
        );
    }

    @Test
    void testAddWithCapacity_ShouldAddedElements_WhenListHasCapacity() {
        list = new ArrayList<>(2);
        fillListFourElement();
        assertAll("Сценарий проверки метода с увеличением вместимости",
                () -> assertEquals(4, list.size()),
                this::testStandardPhraseFourElement_ShouldAddedElement_WhenEmptyList
        );
    }

    @Test
    void testAddWithRecreateLeft_ShouldAddedElements_WhenListHasManyElements() {
        list = new ArrayList<>(3);
        fillListThreeElement();
        list.add(0, "Я");
        list.add("!");

        assertAll("Сценарий проверки добавления елементов со сдвигом в ???",
                () -> assertEquals(5, list.size()),
                this::testStandardPhraseFiveElement_ShouldAddedElementToEnd_WhenListHasManyElements
        );
    }

    @Test
    void testAddWithLeftReserve_ShouldAddedElements_WhenListHasManyElementsWithCapacity() {
        list = new ArrayList<>(3);
        list.add("Java");
        list.add("программист");
        list.add("!");
        list.add(0, "Я");
        list.add(1, "супер");
        assertAll("",
                () -> assertEquals(5, list.size()),
                this::testStandardPhraseFiveElement_ShouldAddedElementToEnd_WhenListHasManyElements
        );
    }

    @Test
    void testAddWithLeftShift_ShouldAddedElements_WhenListHasManyElementsWithCapacity() {
        list = new ArrayList<>(4);
        list.add("Я");
        list.add("Java");
        list.add("программист");
        list.add(1, "супер");
        assertAll("",
                () -> assertEquals(4, list.size()),
                this::testStandardPhraseFourElement_ShouldAddedElement_WhenEmptyList
        );
    }

    @Test
    void testAddWithRightShift_ShouldAddedElements_WhenListHasManyElementsWithCapacity() {
        list = new ArrayList<>(4);
        list.add("супер");
        list.add("Java");
        list.add(0, "Я");
        list.add("программист");
        assertAll("",
                () -> assertEquals(4, list.size()),
                this::testStandardPhraseFourElement_ShouldAddedElement_WhenEmptyList
        );
    }

    @Test
    void testRemoveLeft_ShouldRemoveElementByIndex_WhenListHasManyElements() {
        list = new ArrayList<>();
        fillListFiveElement();
        list.remove(1);
        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("Java", list.get(1)),
                () -> assertEquals("программист", list.get(2)),
                () -> assertEquals("!", list.get(3))
        );
    }

    @Test
    void testRemoveRight_ShouldRemoveElementByIndex_WhenListHasManyElements() {
        list = new ArrayList<>();
        fillListFiveElement();
        list.remove(3);
        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("супер", list.get(1)),
                () -> assertEquals("Java", list.get(2)),
                () -> assertEquals("!", list.get(3))
        );
    }

    @Test
    @SneakyThrows
    void testReduceSize_ShouldReduceSizeList_WhenListHasManyElementsWithCapacity() {
        list = new ArrayList<>(100);
        fillListFiveElement();
        list.remove(1);
        assertAll("",
                () -> assertEquals(4, list.size()),
                () -> assertEquals("Я", list.get(0)),
                () -> assertEquals("Java", list.get(1)),
                () -> assertEquals("программист", list.get(2)),
                () -> assertEquals("!", list.get(3))
        );
        Field field = ArrayList.class.getDeclaredField("items");
        field.setAccessible(true);
        assertEquals(50, ((Object[]) field.get(list)).length);
    }

    @Test
    public void testQuickSortWithIntegerArray_ShouldSortList_WhenListHasIntegerElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(3);
        arr.add(1);
        arr.add(4);
        arr.add(1);
        arr.add(5);
        arr.add(9);
        arr.add(2);
        arr.add(6);
        arr.add(5);
        arr.add(3);

        // Ожидаемый результат после сортировки
        Integer[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 6, 9};

        quickSort(arr, 0, arr.size() - 1);

        // Проверяем, соответствует ли отсортированный массив ожидаемому результату
        for (int i = 0; i < arr.size(); i++) {
            assertEquals(expected[i], arr.get(i));
        }
    }

    @Test
    public void testQuickSortWithCharacterArray_ShouldSortList_WhenListHasCharacterElements() {
        ArrayList<Character> arr = new ArrayList<>();
        arr.add('d');
        arr.add('b');
        arr.add('a');
        arr.add('c');

        Character[] expected = {'a', 'b', 'c', 'd'};

        quickSort(arr, 0, arr.size() - 1);

        for (int i = 0; i < arr.size(); i++) {
            assertEquals(expected[i], arr.get(i));
        }
    }

    @Test
    public void testMergeSortWithIntegerArray_ShouldSortList_WhenListHasIntegerElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(3);
        arr.add(1);
        arr.add(4);
        arr.add(1);
        arr.add(5);
        arr.add(9);
        arr.add(2);
        arr.add(6);
        arr.add(5);
        arr.add(3);

        Integer[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 6, 9};

        mergeSort(arr, 0, arr.size() - 1);

        for (int i = 0; i < arr.size(); i++) {
            assertEquals(expected[i], arr.get(i));
        }
    }

    @Test
    public void testMergeSortWithCharacterArray_ShouldSortList_WhenListHasCharacterElements() {
        ArrayList<Character> arr = new ArrayList<>();
        arr.add('d');
        arr.add('b');
        arr.add('a');
        arr.add('c');

        Character[] expected = {'a', 'b', 'c', 'd'};

        mergeSort(arr, 0, arr.size() - 1);

        for (int i = 0; i < arr.size(); i++) {
            assertEquals(expected[i], arr.get(i));
        }
    }
}