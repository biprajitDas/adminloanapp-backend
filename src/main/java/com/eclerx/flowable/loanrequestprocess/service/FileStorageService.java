package com.eclerx.flowable.loanrequestprocess.service;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eclerx.flowable.loanrequestprocess.exception.FileStorageException;
import com.eclerx.flowable.loanrequestprocess.exception.MyFileNotFoundException;
import com.eclerx.flowable.loanrequestprocess.property.FileStorageProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService {
		
	private Logger logger = LoggerFactory.getLogger(FileStorageService.class);
	   private final Path fileStorageLocation;

	    @Autowired
	    public FileStorageService(FileStorageProperties fileStorageProperties) {
	        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
	                .toAbsolutePath().normalize();

	        try {
	            Files.createDirectories(this.fileStorageLocation);
	        } catch (Exception ex) {
	            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
	        }
	    }

	    public String storeFile(MultipartFile file,String name) {
	        // Normalize file name
	        String fileName = name;//String.valueOf(id); //StringUtils.cleanPath(file.getOriginalFilename());
	        if(file.getContentType().equals("application/pdf")) {
	        	fileName=fileName+".pdf";
	        	logger.info("application/pdf ...."+fileName);
	        }
	        else if(file.getContentType().equals("image/jpeg")) {
	        	fileName=fileName+".jpg";
	        	logger.info("image/jpeg ...."+fileName);
	        }


	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            // Copy file to the target location (Replacing existing file with the same name)
	            Path targetLocation = this.fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	            return fileName;
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	    }

	    public Resource loadFileAsResource(String fileName) {
	        try {
	            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                throw new MyFileNotFoundException("File not found " + fileName);
	            }
	        } catch (MalformedURLException ex) {
	            throw new MyFileNotFoundException("File not found " + fileName, ex);
	        }
	    }
}
