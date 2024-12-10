package com.uploadfile.project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	@Autowired
	private FileStorageRepo fileStorageRepo;

	// methods to store the image and retrive the image from the db

	public FileStorageEntity uploadImage(MultipartFile file) throws IOException {
		return fileStorageRepo.save(FileStorageEntity.builder().name(file.getOriginalFilename())
				.imageData(FileStorageService.compressImage(file.getBytes())).build());

	}

	public byte[] downloadImage(String imageName) {
		Optional<FileStorageEntity> imageData = fileStorageRepo.findByName(imageName);

		return FileStorageService.decompressImage(imageData.get().getImageData());
	}

	// while storing into the db i want to compress the file and decompress the
	// image while retrieving it
	public static byte[] compressImage(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		while (!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);

		}
		try {
			outputStream.close();
		} catch (Exception ex) {

		}

		return outputStream.toByteArray();
	}

	public static byte[] decompressImage(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];

		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				outputStream.write(tmp, 0, count);

			}
			outputStream.close();
		} catch (Exception ex) {

		}
		return outputStream.toByteArray();
	}

}
