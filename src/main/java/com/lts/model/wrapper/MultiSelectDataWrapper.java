package com.lts.model.wrapper;

/**
 * This wrapper returns the date in a "ready to process form" for isteven-multiselect component.
 * We need to have a 'name' for displaying the option and a 'ticked' for the reset function to work.
 * The other props ('id' and 'data') are for comparisons and identification of the underlaying selected objects.
 *
 * @param <T>
 */
public class MultiSelectDataWrapper<T> {

    private Long id;
    private String name;
    private boolean ticked;
    private T data;

    public MultiSelectDataWrapper(Long id, String name, T data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isTicked() {
        return ticked;
    }

    public T getData() {
        return data;
    }
}
