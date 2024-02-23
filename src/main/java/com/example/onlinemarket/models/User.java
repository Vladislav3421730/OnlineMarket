package com.example.onlinemarket.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Size(min = 4,max = 25,message = "Логин должен содержать от 4 до 25 символов")
    @NotBlank(message = "Логин не может состоять из пробелов")
    @Column(name="login")
    private String login;

    @Column(name="password")
    private String Password;

    @Column(name="PhoneNumber")
    private String PhoneNumber;

    @NotNull
    @Column(name="email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    private Image image;

    @Transient
    private String ConfirmPassword;

    @Column(name="IsBun")
    private boolean IsBun;

    @PrePersist
    private void init(){
        IsBun=false;
    }

    public boolean isAdmin(){
        return roleSet.contains(Role.ROLE_ADMIN);
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    private List<Product> products=new ArrayList<>();


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Set<Role> roleSet=new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleSet;
    }

    @Override
    public String getPassword() { return Password;
    }

    @Override
    public String getUsername() {return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !IsBun;
    }
}
