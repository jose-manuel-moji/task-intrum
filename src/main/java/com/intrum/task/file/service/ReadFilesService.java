package com.intrum.task.file.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.intrum.task.api.dto.PayoutDTO;
import com.intrum.task.api.service.SubmitDataService;
import com.intrum.task.data.service.PayoutService;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class ReadFilesService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReadFilesService.class);
	
	@Value("${file.folderIn}")
	private String folderIn;
	
	@Value("${file.folderProcess}")
	private String folderProcess;
	
	@Value("${file.baseFileName}")
	private String baseFileName;
	
	@Value("${file.fieldDelimiter}")
	private Character fieldDelimiter;
	
	@Autowired
	private PayoutService payoutService;
	
	@Autowired
	private ProcessFileService processFileService;
	
	@Autowired
	private SubmitDataService submitDataService;
	
	public void readPayout() {
	    	File folder = new File(folderIn);
	    	File[] listOfFiles = folder.listFiles();
	    	List<String> processFiles = new ArrayList<String>();
	    	final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
	    	
	    	
	    	// REad Files
	    	for (File f : listOfFiles) {
	    		
	    		boolean process = false;
	    		Date date = null;
	    		
	    		// Check file exists in path
	    		if(f.exists() && f.isFile()) {
	    			final String filename = f.getName();
	    			
	    			// check file exist in database
	    			if (this.processFileService.checkFile(filename) != null) {
	    				logger.info("The file has already been processed");
	    				// Copy to move file
	    				processFiles.add(filename);
	    			} else {
	    				
	    				// WK Files
	    				if (filename.startsWith(baseFileName)) {
	    					try {
								date = sdf.parse(filename.replaceAll(baseFileName, ""));
								process = this.processWK(f);
	    					} catch (ParseException e) {
	    						logger.error("File Pattern error");
	    					}
	    				}
	    				
	    				// Customer other countrys....
	    				
	    				if (process) {
	    					// Copy to proccess file
	    					processFiles.add(filename);
	    					this.processFileService.saveProcessFile(filename, date);
	    				}
	    			}
	    			
	    		}
	    	}
	    	
	    	for (String file : processFiles) {
	    		try {
	    		Files.move(new File(folderIn + file).toPath(), 
						new File(folderProcess + file).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
	    		} catch (IOException e) {
					logger.error(e.getMessage());
				}
	    	}
	    	
	}
	
	//@Scheduled(fixedRate = 5000)
	@Scheduled(cron = "0 */5 0-4 * * *")
	public void scheduler() {
		
		logger.info("Scheduling");
		
		this.readPayout();
	}
	
	
	private boolean processWK (File f) {
		
		boolean process = false;
		FileReader fileReader = null;
		
		try {
	    	// WK Files
			logger.info("File --> "  + f.getName());
			
			fileReader = new FileReader(f);
			
			new CsvToBeanBuilder<PayoutDTO>(fileReader)
					.withSeparator(fieldDelimiter)
					.withIgnoreQuotations(true)
	                .withType(PayoutDTO.class)
	                .build()
	                .parse()
	                .forEach(aux -> {
	                	this.payoutService.savePayout(aux);
	                	this.submitDataService.submitPayout(aux);
	                });

			process = true;
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		
		return process;
	}
}
