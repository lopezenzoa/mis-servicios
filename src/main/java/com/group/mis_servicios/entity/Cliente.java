package com.group.mis_servicios.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Cliente extends User {
    private String phoneNumber;
}
