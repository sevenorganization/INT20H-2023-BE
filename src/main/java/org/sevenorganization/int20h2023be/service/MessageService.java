package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> findMessageForCandidate();
}
