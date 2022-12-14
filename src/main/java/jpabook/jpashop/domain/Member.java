package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;


    private String name;

    @Embedded//내장을 포함한다.
    private Address address;

    @JsonIgnore//넌 빠져
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
