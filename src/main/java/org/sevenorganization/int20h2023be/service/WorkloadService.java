package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.enumeration.Workload;

import java.util.List;

public interface WorkloadService {
    List<Workload> findAllWorkloads();
}
