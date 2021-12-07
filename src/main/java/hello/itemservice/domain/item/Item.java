package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 핵심 도메인 설정
 */

@Data   //핵심 도메인 개발 시 위험할 수 있다.(예측하지 못하게 동작할 수 있기 때문)
public class Item {

    private Long id;
    private String itemName;
    private Integer price;  //Integer로 선언해서 Null값이 포함될 수 있도록 설정
    private Integer quantity;

    public Item(){}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
