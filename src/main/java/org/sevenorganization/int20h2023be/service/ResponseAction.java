package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.entity.User;

public interface ResponseAction<E> {
    void accept(Long id, User user);
    void cancel(Long id);
    void reject(Long id);
}
