package list;

import java.util.Objects;

/**
 * Класс DoubleLinkedList представляет двусвязный список.
 * Реализует интерфейс List.
 * @param <T> тип элементов, хранящихся в узлах списка.
 */
public class DoubleLinkedList<T> implements List<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    /**
     * Вложенный класс Node представляет узлы в двусвязном списке.
     * @param <T> тип элементов, хранящихся в узлах.
     */
    private static class Node<T> {

        private T element;
        private Node<T> next;
        private Node<T> previous;
    }

    /**
     * Возвращает текущий размер списка.
     * @return размер списка.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Добавляет элемент в конец списка.
     * @param element добавляемый элемент.
     */
    @Override
    public void add(T element) {
        add(size, element);

    }

    /**
     * Добавляет элемент в указанный индекс списка.
     * @param index индекс, в который нужно добавить элемент.
     * @param element добавляемый элемент.
     * @throws IllegalArgumentException если индекс некорректный.
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException(INCORRECT_INDEX_ERR_MSG);
        }

        Node<T> node = new Node<>();
        node.element = element;

        if (size == 0) {
            first = node;
            last = node;
        } else if (index == size) {
            last.next = node;
            node.previous = last;
            last = node;
        } else if (index == 0) {
            first.previous = node;
            node.next = first;
            first = node;
        } else {
            Node<T> corruntNode = getNode(index);
            node.next = corruntNode;
            node.previous = corruntNode.previous;
            corruntNode.previous.next = node;
            corruntNode.previous = node;
        }
        size++;
    }

    /**
     * Удаляет элемент из списка по указанному индексу.
     *
     * @param index индекс удаляемого элемента
     * @throws IllegalArgumentException если индекс некорректный
     */
    @Override
    public void remove(int index) {
        // Обрабатываем случай, когда индекс находится вне допустимого диапазона
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(INCORRECT_INDEX_ERR_MSG);
        }

        // Обрабатываем случай, когда в списке есть только один элемент
        if (size == 1) {
            first = null;
            last = null;
        }
        // Обрабатываем случай, когда в списке два элемента
        else if (size == 2) {
            if (index == 0) {
                first = last;
                last.previous = null;
            } else {
                last = first;
                first.next = null;
            }
        }
        // Обрабатываем случай, когда удаляется первый элемент
        else if (index == 0) {
            first = first.next;
            first.previous = null;
        }
        // Обрабатываем случай, когда удаляется последний элемент
        else if (index == size - 1) {
            last = last.previous;
            last.next = null;
        }
        // Удаляемый элемент находится между первым и последним элементом
        else {
            Node<T> node = getNode(index);
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
    }

    /**
     * Удаляет указанный элемент из списка.
     *
     * @param element элемент, который нужно удалить из списка
     * @return true, если элемент был успешно удален, иначе false
     */
    @Override
    public boolean remove(T element) {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            if (Objects.equals(first.element, element)) {
                first = null;
                last = null;
                size--;
                return true;
            }
            return false;
        }

        if (size == 2) {
            if (Objects.equals(first.element, element)) {
                first = last;
                last.previous = null;
                size--;
                return true;
            } else if (Objects.equals(last.element, element)) {
                last = first;
                first.next = null;
                size--;
                return true;
            }
            return false;
        }

        if (Objects.equals(first.element, element)) {
            first = first.next;
            first.previous = null;
            size--;
            return true;
        } else {
            Node<T> node = first;

            for (int i = 1; i < size; i++) {
                if (Objects.equals(node.next.element, element)) {
                    node.next = node.next.next;
                    if (i == size - 1) {
                        last = node;
                    } else {
                        node.next.previous = node;
                    }
                    size--;
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента
     * @return элемент, находящийся по указанному индексу
     */
    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    /**
     * Заменяет элемент по указанному индексу на указанный элемент.
     *
     * @param index индекс элемента для замены
     * @param element новый элемент
     */
    @Override
    public void set(int index, T element) {
        Node<T> node = getNode(index);
        node.element = element;
    }

    /**
     * Возвращает индекс первого вхождения указанного элемента в список.
     *
     * @param element элемент, индекс которого нужно найти
     * @return индекс первого вхождения элемента или -1, если элемент отсутствует в списке
     */
    @Override
    public int indexOf(T element) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(node.element, element)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    /**
     * Удаляет все элементы из списка.
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Возвращает true, если указанный элемент присутствует в списке.
     *
     * @param element элемент, наличие которого нужно проверить
     * @return true, если элемент присутствует в списке, иначе false
     */
    @Override
    public boolean contains(T element) {
        return false;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(INCORRECT_INDEX_ERR_MSG);
        }

        Node<T> node;

        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }
}
