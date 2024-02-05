package list;


public interface List<T> { // документировать надо всегда апи, а не реализацию, т.е дока на методы должна была быть здесь.
    // А уже в реализациях либо ничего, либо очень короткие комменты про особенность реализации. Но это так, придирка, просто на будущее имей в виду

    String INCORRECT_INDEX_ERR_MSG = "не корректный индекс";

    int size();

    void add(T element);

    void add(int index, T element);

    void remove(int index);

    boolean remove(T element);

    T get(int index);

    void set(int index, T element);

    int indexOf(T element);

    void clear();

    boolean contains(T element);
}
