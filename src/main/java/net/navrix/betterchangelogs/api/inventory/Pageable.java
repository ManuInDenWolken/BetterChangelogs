package net.navrix.betterchangelogs.api.inventory;

public interface Pageable {

    Pageable previousPage();

    Pageable nextPage();

    default boolean hasNext() {
        return nextPage() != null;
    }

    default boolean hasPrevious() {
        return previousPage() != null;
    }

    default boolean isFirst() {
        return previousPage() == null;
    }

    default boolean isLast() {
        return nextPage() == null;
    }

    int getIndex();

}
