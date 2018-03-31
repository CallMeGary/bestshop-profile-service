package io.gary.bestshop.profile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile {

    @Id
    @NotBlank
    private String username;

    @Email
    @NotBlank
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @Length(min = 8, max = 16)
    @JsonIgnoreProperties(allowSetters = true)
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    private String mobilePhone;

    @JsonIgnoreProperties(allowGetters = true)
    private Integer purchaseCount = 0;

    @JsonIgnoreProperties(allowGetters = true)
    private BigDecimal purchaseAmount = ZERO;

    @JsonIgnoreProperties(allowGetters = true)
    private ProfileStatus status;

    @JsonIgnoreProperties(allowGetters = true)
    private LocalDateTime createdAt;

    @JsonIgnoreProperties(allowGetters = true)
    private LocalDateTime lastModifiedAt;

    private List<DeliveryAddress> deliveryAddresses;

}
