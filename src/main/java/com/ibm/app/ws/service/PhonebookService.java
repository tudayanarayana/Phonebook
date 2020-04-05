package com.ibm.app.ws.service;

import java.util.List;

import com.ibm.app.ws.model.request.PhonebookEntryDetails;
import com.ibm.app.ws.model.request.UpdatePhonebookEntryDetails;
import com.ibm.app.ws.model.response.PhonebookEntry;

public interface PhonebookService {

	List<PhonebookEntry> getAllPhonebookEntries(Integer pageNo, Integer pageSize, String sortBy);

	PhonebookEntry getPhonebookEntryById(Long phonebookId);

	List<PhonebookEntry> getPhonebookEntriesBySurName(String surName, Integer pageNo, Integer pageSize, String sortBy);

	PhonebookEntry createPhonebookEntry(PhonebookEntryDetails phonebookEntryDetails);

	PhonebookEntry updatePhonebookEntry(Long phonebookId, UpdatePhonebookEntryDetails updatePhonebookEntryDetails);

	String deletePhonebookEntry(Long phonebookId);
}
