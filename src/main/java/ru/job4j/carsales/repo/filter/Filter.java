package ru.job4j.carsales.repo.filter;

import java.util.Map;

interface Filter {
    String buildFilterQuery();

    Map<String, Object> getParametersMap();
}
