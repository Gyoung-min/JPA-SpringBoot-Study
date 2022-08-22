package jpabook.jpashop.domain;

import jpabook.jpashop.service.exception.NotEnoughStockException;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stackQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //비즈니스 로직
    public void addStack(int quantity) {
        this.stackQuantity += quantity;
    }

    public void removeStack(int quantity) {
       int restStack = this.stackQuantity - quantity;
       if(restStack < 0) {
           throw new NotEnoughStockException("need more stock");
       }
       this.stackQuantity = restStack;
    }


}
