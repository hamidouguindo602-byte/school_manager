package com.schoolmanagement.communication.repository;

import com.schoolmanagement.communication.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  List<Notification> findByUtilisateur_IdOrderByDateEnvoiDesc(Long idUtilisateur);
}
