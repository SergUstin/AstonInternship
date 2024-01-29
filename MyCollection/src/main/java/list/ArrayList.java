package list;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс ArrayList предоставляет динамический массив и реализует интерфейс List.
 * <T> - тип элементов, которые будут храниться в списке.
 */
public class ArrayList<T> implements List<T> {

    // Содержит элементы ArrayList
    private Object[] items;

    // Количество элементов в ArrayList
    private int size;

    // Начальная позиция для добавления элементов
    private int start;

    /**
     * Конструктор по умолчанию создает ArrayList с начальной вместимостью 10.
     */
    public ArrayList() {
        items = new Object[10];
    }

    /**
     * Конструктор создает ArrayList с указанной вместимостью.
     * @param capacity - исходная вместимость списка.
     * @throws IllegalArgumentException если capacity < 1.
     */
    public ArrayList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("вместимость должна быть больше ноля");
        }
        items = new Object[capacity];
    }

    /**
     * Возвращает количество элементов в списке.
     * @return количество элементов в списке.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Добавляет элемент в конец списка.
     * @param element - элемент который будет добавлен.
     */
    @Override
    public void add(T element) {
        add(size, element);
    }

    /**
     * Добавляет элемент в указанную позицию в списке.
     * @param index - индекс в котором будет добавлен элемент.
     * @param element - элемент который будет добавлен.
     * @throws IllegalArgumentException если index меньше 0 или больше текущего размера списка.
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("не корректный индекс");
        }
        if (size == 0) {
            items[start] = element;
        } else if (size < items.length) { // Есть резерв
            if (index <= size / 2) {  // Вставка в первую половину
                if (start == 0) { // Нет резерва в начале
                    addLeftShift(index, element);
                } else { // Есть резерв в начале
                    addLeft(index, element);
                }
            } else {  // Вставка во вторую половину
                if (start + size < items.length) { // Есть резерв в конце
                    addRight(index, element);
                } else { // Нет резерва в конце
                    addRightShift(index, element);
                }
            }
        } else { // Резерва нет
            recreateItems(index, element);
        }
        size++;
    }

    private void addLeftShift(int index, T element) {
        for (int i = size - 1; i >= index ; i--) {
            items[start + i + 1] = items[i];
        }
        items[start + index] = element;
        for (int i = index - 1; i >= 0 ; i--) {
            items[start + i] = items[i];
        }
        Arrays.fill(items, 0, start, null);
    }

    private void addLeft(int index, T element) {
        for (int i = 0; i < index; i++) {
            items[start + i - 1] = items[start + i];
        }
        start--;
        items[start + index] = element;
    }
    private void addRight(int index, T element) {
        for (int i = size; i > index; i--) {
            items[start + i] = items[start + i - 1];
        }
        items[start + index] = element;
    }
    private void addRightShift(int index, T element) {
        int newStart = start / 2;
        for (int i = 0; i < index; i++) {
            items[start + i - 1] = items[start + i];
        }
        items[start + index] = element;
        if (newStart != start - 1) {
            for (int i = index; i < size ; i++) {
                items[newStart + i + 1] = items[start + i];
            }
        }
        start = newStart;
        Arrays.fill(items, start + size, items.length, null);

    }

    private void recreateItems(int index, T elment) {
        int newStart = start;
        if (newStart == 0 && index <= size / 2) {
            newStart = (items.length * 2 - size) / 2;
        }
        Object[] newItems = new Object[items.length * 2];
        for (int i = 0; i < index; i++) {
            newItems[newStart + i] = items[start + i];
        }
        newItems[newStart + index] = elment;
        for (int i = index; i < size; i++) {
            newItems[newStart + i + 1] = items[start + i];
        }
        items = newItems;
        start = newStart;
    }

    /**
     * Метод для удаления элемента из списка по индексу.
     * Если индекс меньше 0 или больше текущего размера списка, будет выброшено исключение IllegalArgumentException.
     * <p>
     * Алгоритм работы:
     * 1) Если объем массива в 4 раза превышает текущий размер списка, то вызывается внутренний метод recreateItems(index).
     * 2) Если индекс удаляемого элемента менее или равен половине размера списка, алгоритм выполняет удаление из левой
     * части: сдвигает все элементы находящиеся слева от удаляемого элемента вправо на одну позицию, затем обнуляет
     * первый элемент в списке.
     * 3) В противном случае, алгоритм выполняет удаление из правой части: сдвигает все элементы справа от удаляемого
     * элемента влево на одну позицию, затем обнуляет последний элемент в списке.
     * 4) Уменьшает значение size на 1, это отражает что размер списка стал меньше на один элемент.
     *
     * @param  index - позиция элемента в списке, который необходимо удалить.
     * @throws IllegalArgumentException если индекс меньше 0 или больше или равен размеру списка.
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("не корректный индекс");
        }
        if (items.length > size * 4) {
            recreateItems(index);
        } else {
            if (index <= size / 2) { // Удаление из левой части
                for (int i = index; i > 0; i--) {
                    items[start + i] = items[start + i - 1];
                }
                items[start] = null;
                start++;
            } else { // Удаление из правой части
                for (int i = index; i < size - 1; i++) {
                    items[start + i] = items[start + i + 1];
                }
                items[size] = null;
            }
        }
        size--;
    }

    private void recreateItems(int index) {
        int newStart = start;
        if (newStart == 0) {
            newStart = (items.length / 2 - size) / 2;
        }
        Object[] newItems = new Object[items.length / 2];
        for (int i = 0; i < index; i++) {
            newItems[newStart + i] = items[start + i];
        }
        for (int i = index; i < size - 1; i++) {
            newItems[newStart + i] = items[start + i + 1];
        }
        items = newItems;
        start = newStart;
    }

    /**
     * Удаляет первое вхождение указанного элемента из списка, если он присутствует.
     *
     * @param  element - элемент, который необходимо удалить из списка.
     * @return true, если удаление выполнено успешно, в противном случае - false.
     */
    @Override
    public boolean remove(T element) {
        int index = indexOf(element);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Возвращает элемент по указанному индексу в списке.
     *
     * @param  index - индекс элемента, который нужно вернуть
     * @return      - элемент с указанным индексом в списке
     * @throws IllegalArgumentException если указанный индекс является отрицательным или превышает размер списка
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("не корректный индекс");
        }
        return (T) items[start + index];
    }

    /**
     * Заменяет элемент по указанному индексу в списке заданным элементом.
     *
     * @param index   - индекс элемента, который нужно заменить
     * @param element - новый элемент, который нужно вставить в список
     * @throws IllegalArgumentException если указанный индекс является отрицательным или превышает размер списка
     */
    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("не корректный индекс");
        }
        items[start + index] = element;
    }

    /**
     * Определяет индекс указанного элемента в списке.
     *
     * @param element - элемент, индекс которого нужно найти в списке
     * @return индекс указанного элемента в списке; -1, если элемент не найден
     */
    @Override
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[start + i], element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Удаляет все элементы из списка, обнуляя массив и сбрасывая размер списка на 0.
     */
    @Override
    public void clear() {
        Arrays.fill(items, null);
        size = 0;
    }

    /**
     * Проверяет, содержит ли список указанный элемент.
     *
     * @param element - элемент, наличие которого необходимо проверить
     * @return true, если список содержит указанный элемент; в противном случае - false
     */
    @Override
    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }

    // Покрыть тестами все методы ниже!!!
    /**
     * Метод для сортировки части списка методом быстрой сортировки.
     *
     * @param arr  список, который требуется отсортировать
     * @param low  нижний индекс части списка
     * @param high верхний индекс части списка
     * @param <T>  тип элементов списка, реализующий интерфейс Comparable
     */
    public static  <T extends Comparable<T>> void quickSort(ArrayList<T> arr, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(arr, low, high);

            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    /**
     * Метод для разделения части списка на две части относительно опорного элемента.
     *
     * @param arr  список, в котором требуется произвести разделение
     * @param low  нижний индекс разделяемой части списка
     * @param high верхний индекс разделяемой части списка
     * @param <T>  тип элементов списка, реализующий интерфейс Comparable
     * @return индекс опорного элемента после разделения
     */
    private static  <T extends Comparable<T>> int partition(ArrayList<T> arr, int low, int high) {
        T pivot = arr.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (arr.get(j).compareTo(pivot) < 0) {
                i++;

                T temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        T temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);

        return i + 1;
    }

    /**
     * Сортирует указанный массив с помощью сортировки слиянием.
     *
     * @param arr Массив, который нужно отсортировать.
     * @param left Индекс начала сортируемого участка массива.
     * @param right Индекс конца сортируемого участка массива.
     * @param <T> Тип элементов массива, который реализует интерфейс Comparable.
     */
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> arr, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            merge(arr, left, middle, right);
        }
    }

    /**
     * Сливает два отсортированных участка массива в один отсортированный участок.
     *
     * @param arr Массив, в котором необходимо выполнить слияние.
     * @param left Индекс начала первого участка.
     * @param middle Индекс конца первого участка (включительно).
     * @param right Индекс конца второго участка.
     * @param <T> Тип элементов массива, который реализует интерфейс Comparable.
     */
    private static <T extends Comparable<T>> void merge(ArrayList<T> arr, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        ArrayList<T> leftArray = new ArrayList<T>();
        ArrayList<T> rightArray = new ArrayList<T>();

        for (int i = 0; i < n1; i++) {
            leftArray.add(arr.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(arr.get(middle + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray.get(i).compareTo(rightArray.get(j)) <= 0) {
                arr.set(k, leftArray.get(i));
                i++;
            } else {
                arr.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr.set(k, leftArray.get(i));
            i++;
            k++;
        }
        while (j < n2) {
            arr.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }
}

