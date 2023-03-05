package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.enumeration.Workload;
import org.sevenorganization.int20h2023be.service.WorkloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workloads")
public class WorkloadResource {

    private final WorkloadService workloadService;

    @GetMapping
    public ResponseEntity<List<Workload>> findAllWorkloads() {
        return ResponseEntity.ok(workloadService.findAllWorkloads());
    }
}
