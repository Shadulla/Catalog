package com.shadulla.catalog.modules.categories.adapter.data;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = true, length = 50, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 50, updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "updated_by", nullable = true, length = 50, updatable = false)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant lastModifiedDate = Instant.now();

}

