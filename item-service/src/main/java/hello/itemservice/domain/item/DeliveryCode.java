package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@AllArgsConstructor

public class DeliveryCode {
    private String code;
    private String displayName;
}
