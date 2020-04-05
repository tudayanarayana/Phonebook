package com.ibm.app.ws.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ibm.app.ws.model.response.PhonebookEntry;

@Repository
public interface PhonebookRepository extends PagingAndSortingRepository<PhonebookEntry, Long> {

	Page<PhonebookEntry> findBySurName(String surName, Pageable pageable);
}
