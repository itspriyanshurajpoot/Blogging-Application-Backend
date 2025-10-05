package com.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jwt_tokens")
public class Token extends BaseEntity{

    private String token;
    private Integer loginCount = 0;
    private Boolean isLogout = false;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
