package com.example.backend_sem2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter
@Entity
@SuperBuilder
//@ToString(callSuper = true)
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String username;
    private String password;
    private Character gender;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "customer_address")
    private String customerAddress;
    private boolean enabled = false;
    private LocalDate dob;
    private String avatarUrl;
    @JsonIgnore
    @Column(name = "verification_code")
    private String verificationCode;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authoritySet;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    private List<Comment> commentList;

    @OneToMany(
            mappedBy = "user", fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH
    })
    private List<Order> orderList;

    public void addAuthority(Authority authority){
        if(authoritySet == null){
            authoritySet = new HashSet<>();
        }
        authoritySet.add(authority);
    }

    public Integer getAge(){
        if (dob == null) return 0;
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public String getFullName(){
        String fullName = "";
        if(this.firstName != null) fullName += this.firstName;
        if(this.lastName != null) fullName += this.lastName;
        return fullName;
    }
}
