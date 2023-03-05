package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.dto.ResumeDto;
import org.sevenorganization.int20h2023be.model.entity.Resume;
import org.sevenorganization.int20h2023be.model.entity.User;

public interface ResumeService {
    Resume updateResume(ResumeDto resumeDto, User user);
}
