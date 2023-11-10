package LicenseProject.License.dto;

import lombok.*;

// DataBase
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class MemberDTO {
    private long id;
    private String memberEmail;
    private String memberPassword;

}
