package pl.twerd.invertersdatareceiver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message="validator.message.empty_id")
    private Integer id;
    @NotBlank(message="validator.message.empty_user_name")
    private String userName;
}
