package com.intrum.task.file.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrum.task.data.entities.ProcessFileEntity;
import com.intrum.task.data.repository.ProcessFileRepository;

@Service
public class ProcessFileService {
	
	@Autowired
	private ProcessFileRepository processFileRepository;
	
	public ProcessFileEntity saveProcessFile (final String fileName, final Date processDate) {
		
		ProcessFileEntity entity = new ProcessFileEntity();
		
		entity.setFileName(fileName);
		entity.setProcessDate(processDate);

		processFileRepository.save(entity);
		
		return entity;
	}
	
	public ProcessFileEntity checkFile (final String fileName) {
		
		Optional<ProcessFileEntity> result = this.processFileRepository.findById(fileName);
		
		return result == null || result.isEmpty() ? null : result.get();
	}
}