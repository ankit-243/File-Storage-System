package com.uploadfile.project;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepo extends JpaRepository<FileStorageEntity, Long> {
	Optional<FileStorageEntity> findByName(String name);
}
