package com.ibm.app.ws.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.app.ws.exceptions.PhonebookEntryNotFoundException;
import com.ibm.app.ws.model.request.PhonebookEntryDetails;
import com.ibm.app.ws.model.request.UpdatePhonebookEntryDetails;
import com.ibm.app.ws.model.response.PhonebookEntry;
import com.ibm.app.ws.service.PhonebookService;

@RestController
@RequestMapping("/phonebook")
public class PhonebookController {

	@Autowired
	private PhonebookService phonebookService;

	@GetMapping(headers = "X-API-VERSION=1")
	public ResponseEntity<List<PhonebookEntry>> retrieveAllPhonebookEntries(
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String surName) {
		List<PhonebookEntry> phonebook = phonebookService.getAllPhonebookEntries(pageNo, pageSize, sortBy);
		if (null != surName) {
			List<PhonebookEntry> surnameFilteredPhonebookEntries = phonebookService
					.getPhonebookEntriesBySurName(surName, pageNo, pageSize, sortBy);
			return new ResponseEntity<List<PhonebookEntry>>(surnameFilteredPhonebookEntries, new HttpHeaders(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<PhonebookEntry>>(phonebook, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(path = "/{phonebookId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, headers = "X-API-VERSION=1")
	public ResponseEntity<PhonebookEntry> retrievePhonebookEntry(@PathVariable Long phonebookId) {
		PhonebookEntry phonebookEntry = phonebookService.getPhonebookEntryById(phonebookId);
		if (null != phonebookEntry) {
			return new ResponseEntity<PhonebookEntry>(phonebookEntry, HttpStatus.OK);
		} else {
			throw new PhonebookEntryNotFoundException("PhonebookId-" + phonebookId);
		}
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, headers = "X-API-VERSION=1")
	public ResponseEntity<PhonebookEntry> createPhonebookEntry(
			@Valid @RequestBody PhonebookEntryDetails phonebookEntryDetails) {
		PhonebookEntry phonebookEntry = phonebookService.createPhonebookEntry(phonebookEntryDetails);
		return new ResponseEntity<PhonebookEntry>(phonebookEntry, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{phonebookId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE }, headers = "X-API-VERSION=1")
	public ResponseEntity<PhonebookEntry> updatePhonebookEntry(@PathVariable Long phonebookId,
			@Valid @RequestBody UpdatePhonebookEntryDetails updatePhonebookEntryDetails) {
		PhonebookEntry phonebookEntry = phonebookService.updatePhonebookEntry(phonebookId, updatePhonebookEntryDetails);
		if (null != phonebookEntry) {
			return new ResponseEntity<PhonebookEntry>(phonebookEntry, HttpStatus.CREATED);
		} else {
			throw new PhonebookEntryNotFoundException("PhonebookId-" + phonebookId);
		}
	}

	@DeleteMapping(path = "/{phonebookId}", headers = "X-API-VERSION=1")
	public ResponseEntity<Void> deletePhonebookEntry(@PathVariable Long phonebookId) {
		String message = phonebookService.deletePhonebookEntry(phonebookId);
		if ("success".equals(message)) {
			return ResponseEntity.noContent().build();
		} else {
			throw new PhonebookEntryNotFoundException("PhonebookId-" + phonebookId);
		}
	}
}
