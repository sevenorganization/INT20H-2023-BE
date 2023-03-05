package org.sevenorganization.int20h2023be.service.impl;

import org.sevenorganization.int20h2023be.model.enumeration.Workload;
import org.sevenorganization.int20h2023be.service.WorkloadService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DefaultWorkloadService implements WorkloadService {

    @Override
    public List<Workload> findAllWorkloads() {
        return Arrays.stream(Workload.values()).toList();
    }
}
