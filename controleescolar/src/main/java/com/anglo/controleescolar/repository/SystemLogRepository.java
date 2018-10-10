package com.anglo.controleescolar.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anglo.controleescolar.repository.entity.SystemLog;

@Repository
public interface SystemLogRepository extends PagingAndSortingRepository<SystemLog, Integer> {

}
