package com.agoda.assessment.repository;

import java.util.List;
import java.util.Set;

public interface IdRepository {
    int countAll();

    boolean exists(Integer id);

    void replace(int partitionKey, Set<Integer> newIds);

    List<Boolean> exists(List<Integer> ids);
}
