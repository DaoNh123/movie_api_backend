package com.example.backend_sem2.entity;

import com.example.backend_sem2.enums.GenderEnum;
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
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String email;
    private LocalDate dob;
    private String avatarUrl;

    private boolean enabled = false;
    @Column(name = "verification_code")
    private String verificationCode;

//    @Column(name = "phone_number")            // remove
//    private String phoneNumber;
//    @Column(name = "customer_address")        // remove
//    private String customerAddress;

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
