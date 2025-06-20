package com.group.mis_servicios.model.entity;

import com.group.mis_servicios.model.enums.States;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", columnDefinition = "DATETIME", nullable = false, unique = true)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private States status;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
