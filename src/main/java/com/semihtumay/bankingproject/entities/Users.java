package com.semihtumay.bankingproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "First name can not be blank")
    @Length(message = "First name must contain min 2 max 50 character.", min = 2, max = 50)
    private String firstname;
    @NotBlank(message = "Surname can not be blank")
    @Length(message = "Surname must contain min 2 max 50 character.", min = 2, max = 50)
    private String surname;
    @Length(message = "IDNumber must contain 11 character.",min = 11, max = 11)
    @Digits(message = "ID number must consist of numbers.",integer = 11, fraction = 0)
    private String idNumber;
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email Format Error")
    @Length(message = "Maximum 60", max = 60)
    private String email;
    @NotBlank(message = "Password can not be blank")
    @Pattern(message = "Password must contain min one upper,lower letter and 0-9 digit number ", regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})")
    private String password;
    @NotBlank(message = "Phone number can not be blank")
    @Length(message = "Phone number must contain min 2 max 50 character.", min = 2, max = 50)
    private String phone;
    private boolean enabled=false;
    private boolean tokenExpired=false;
    private String verificationCode;
    @OneToMany(fetch =  FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Account> accounts;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "user_role",
            joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;

}
