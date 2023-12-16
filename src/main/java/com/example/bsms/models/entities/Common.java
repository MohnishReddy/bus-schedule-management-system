package com.example.bsms.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @CreatedDate
    private Date createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;
}
