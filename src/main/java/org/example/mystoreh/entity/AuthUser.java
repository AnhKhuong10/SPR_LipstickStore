package org.example.mystoreh.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private Long id;
    private Date createAt;
    private boolean deleted;
    private int status;
    public AuthUser(String username,
                    String password,
                    Collection<? extends GrantedAuthority> authorities,
                    Long id,
                    Date createAt,
                    boolean deleted,
                    int status) {
        super(username, password, authorities);
        this.id = id;
        this.createAt = createAt;
        this.deleted = deleted;
        this.status = status;
    }
}
