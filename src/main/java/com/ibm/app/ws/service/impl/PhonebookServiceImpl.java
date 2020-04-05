package com.ibm.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ibm.app.ws.model.request.PhonebookEntryDetails;
import com.ibm.app.ws.model.request.UpdatePhonebookEntryDetails;
import com.ibm.app.ws.model.response.PhonebookEntry;
import com.ibm.app.ws.repository.PhonebookRepository;
import com.ibm.app.ws.service.PhonebookService;

@Service
public class PhonebookServiceImpl implements PhonebookService {

	@Autowired
	PhonebookRepository phonebookRepository;

	@Override
	public List<PhonebookEntry> getAllPhonebookEntries(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<PhonebookEntry> pagedResult = phonebookRepository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<PhonebookEntry>();
		}
	}

	@Override
	public PhonebookEntry getPhonebookEntryById(Long phonebookId) {
		Optional<PhonebookEntry> phonebookEntryOptional = phonebookRepository.findById(phonebookId);
		if (!phonebookEntryOptional.isPresent()) {
			return null;
		}
		return phonebookEntryOptional.get();
	}

	@Override
	public List<PhonebookEntry> getPhonebookEntriesBySurName(String surName, Integer pageNo, Integer pageSize,
			String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<PhonebookEntry> pagedResult = phonebookRepository.findBySurName(surName, paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<PhonebookEntry>();
		}
	}

	@Override
	public PhonebookEntry createPhonebookEntry(PhonebookEntryDetails phonebookEntryDetails) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		PhonebookEntry phonebookEntry = modelMapper.map(phonebookEntryDetails, PhonebookEntry.class);
		phonebookRepository.save(phonebookEntry);
		return phonebookEntry;
	}

	@Override
	public PhonebookEntry updatePhonebookEntry(Long phonebookId,
			UpdatePhonebookEntryDetails updatePhonebookEntryDetails) {
		Optional<PhonebookEntry> phonebookEntryOptional = phonebookRepository.findById(phonebookId);
		if (phonebookEntryOptional.isPresent()) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			PhonebookEntry phonebookEntry = phonebookEntryOptional.get();
			Long addressId = phonebookEntry.getAddress().getId();
			phonebookEntry = modelMapper.map(updatePhonebookEntryDetails, PhonebookEntry.class);
			phonebookEntry.setId(phonebookId);
			phonebookEntry.getAddress().setId(addressId);
			phonebookRepository.save(phonebookEntry);
			return phonebookEntry;
		}
		return null;
	}

	@Override
	public String deletePhonebookEntry(Long phonebookId) {
		String message = null;
		Optional<PhonebookEntry> phonebookEntryOptional = phonebookRepository.findById(phonebookId);
		if (phonebookEntryOptional.isPresent()) {
			phonebookRepository.deleteById(phonebookId);
			message = "success";
		}
		return message;
	}
}
