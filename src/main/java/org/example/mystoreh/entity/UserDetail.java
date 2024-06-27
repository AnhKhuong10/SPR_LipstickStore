package org.example.mystoreh.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name= "user_detail" )
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "gender", length = 2)
    private int gender;

    @Column(name = "dob", length = 11)
    private String dob;

    @Column(name="mobile", length = 11, unique = true)
    private String mobile;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    //2chieu
    //User          UserDetail
    //phuthuoc      chuLienKet
}
