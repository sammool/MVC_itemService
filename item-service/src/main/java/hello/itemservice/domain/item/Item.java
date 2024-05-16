package hello.itemservice.domain.item;

import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class Item {

   // @NotNull(groups = UpdateCheck.class)//수정 요규사항
    private Long id;

    //@NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    //@NotNull(message ="값을 입력해주세요", groups = {SaveCheck.class, UpdateCheck.class})
    //@Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    //@NotNull
    //@Max(value = 9999, groups = {SaveCheck.class})
    private Integer quantity;

    private Boolean open; //판매 여부
    private List<String> regions; //등록 지역
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송 방식

    public Item(){

    }

    public Item(String itemName, Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
