package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

    @OneToMany(mappedBy = "customer")
    private List<ProvidersList> favorites;

    @OneToMany(mappedBy = "customer")
    private List<Call> calls;
}
