package com.schoolmanagement.communication.repository;

import com.schoolmanagement.communication.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUtilisateur_IdOrderByDateEnvoiDesc(
            Long idUtilisateur
    );
}