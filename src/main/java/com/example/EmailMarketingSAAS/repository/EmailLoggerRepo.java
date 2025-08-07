package com.example.EmailMarketingSAAS.repository;

import com.example.EmailMarketingSAAS.entity.EmailLog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailLoggerRepo extends CrudRepository<EmailLog, UUID> {

    Optional<EmailLog> findById(UUID id);
}
