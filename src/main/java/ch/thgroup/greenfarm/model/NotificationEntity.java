package ch.thgroup.greenfarm.model;

//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
@Table(name = "notification_test", schema = "greenfarm")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "farmer_first_name", length = 100)
    private String farmerFirstName;

    @Column(name = "title_lg", length = 100, nullable = false)
    private String title;

    @Column(name = "body_lg", length = 200, nullable = false)
    private String body;

    @Column(name = "delievered", nullable = false)
    private boolean delivered = false;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFarmerFirstName() {
        return farmerFirstName;
    }

    public void setFarmerFirstName(String farmerFirstName) {
        this.farmerFirstName = farmerFirstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    // Constructors
    public NotificationEntity() {
    }

    public NotificationEntity(String farmerFirstName, String title, String body, boolean delivered) {
        this.farmerFirstName = farmerFirstName;
        this.title = title;
        this.body = body;
        this.delivered = delivered;
    }
}
