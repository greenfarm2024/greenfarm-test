package ch.thgroup.greenfarm.service;

import ch.thgroup.greenfarm.model.NotificationEntity;
import ch.thgroup.greenfarm.model.NotificationEntity;
import ch.thgroup.greenfarm.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationEntity> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<NotificationEntity> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public NotificationEntity createNotification(NotificationEntity notification) {
        return notificationRepository.save(notification);
    }

    public NotificationEntity updateNotification(Long id, NotificationEntity notificationDetails) {
        NotificationEntity notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setFarmerFirstName(notificationDetails.getFarmerFirstName());
        notification.setTitle(notificationDetails.getTitle());
        notification.setBody(notificationDetails.getBody());
        notification.setDelivered(notificationDetails.isDelivered());
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
