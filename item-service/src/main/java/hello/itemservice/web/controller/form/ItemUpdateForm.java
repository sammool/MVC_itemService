package hello.itemservice.web.controller.form;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;
    
    @NotNull(message ="값을 입력해주세요")
    @Range(min = 1000, max = 1000000)
    private Integer price;

    
    @NotNull(message ="값을 입력해주세요")
    private Integer quantity;
}
