package com.dailycodework.beautifulcare.entity;

import com.dailycodework.beautifulcare.entity.enums.TreatmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a treatment process.
 */
@Data
@Entity
@Table(name = "treatment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private User specialist;

    private String note;

    @Enumerated(EnumType.STRING)
    private TreatmentStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "treatment_services", joinColumns = @JoinColumn(name = "treatment_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    @Builder.Default
    private List<ServiceEntity> services = new ArrayList<>();

    @OneToOne(mappedBy = "treatment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TreatmentResult result;

    /**
     * Start the treatment.
     * 
     * @return This treatment object with updated status and start time
     */
    public Treatment start() {
        this.status = TreatmentStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
        return this;
    }

    /**
     * Complete the treatment.
     * 
     * @return This treatment object with updated status and completion time
     */
    public Treatment complete() {
        this.status = TreatmentStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        return this;
    }

    /**
     * Cancel the treatment.
     * 
     * @return This treatment object with updated status
     */
    public Treatment cancel() {
        this.status = TreatmentStatus.CANCELLED;
        return this;
    }

    /**
     * Check if the treatment has results.
     * 
     * @return true if treatment has a result, false otherwise
     */
    public boolean hasResult() {
        return this.result != null;
    }
}