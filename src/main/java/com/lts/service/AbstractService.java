package com.lts.service;

import org.springframework.data.domain.Sort;

public abstract class AbstractService {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_DENUMIRE = "denumire";
    private static final String COLUMN_DESCRIPTION = "description";

    public Sort getSorterById() {
        return getSorter(COLUMN_ID, null);
    }

    public Sort getSorterByCode() {
        return getSorter(COLUMN_CODE, null);
    }

    public Sort getSorterByName() {
        return getSorter(COLUMN_DENUMIRE, null);
    }

    public Sort getSorterByDescription() {
        return getSorter(COLUMN_DESCRIPTION, null);
    }

    public Sort getSorter(String columnName) {
        return getSorter(columnName, null);
    }

    public Sort getSorter(String columnName, Sort.Direction direction) {
        return new Sort(direction != null ? direction : Sort.Direction.ASC, columnName);
    }

}
