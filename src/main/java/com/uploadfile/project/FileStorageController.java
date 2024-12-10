package com.uploadfile.project;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dbimage")
public class FileStorageController {
	@Autowired
	FileStorageService service;

	@PostMapping("upload")
	public ResponseEntity<?> upload(@RequestParam("image") MultipartFile file) throws IOException {

		FileStorageEntity e = service.uploadImage(file);
		return new ResponseEntity("Uploaded Successfully", HttpStatus.CREATED);

	}

	@GetMapping("download")
	public ResponseEntity<?> download(@RequestParam String name) throws IOException {

		byte[] downloadedImage = service.downloadImage(name);
		HttpHeaders headers = new HttpHeaders();
		
        headers.setContentType(MediaType.valueOf("image/png"));
		return new ResponseEntity(downloadedImage,headers,HttpStatus.OK);

	}

}
