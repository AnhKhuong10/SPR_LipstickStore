package org.example.mystoreh.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUserDetailDTO {
    private String mobile;
    private int gender;
    private String email;
    private String password;
}
