package com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;


@Entity
@Table(name = "orders")
public class Order extends RepresentationModel {

    @Id
    @GeneratedValue
    @JsonView(Views.Internal.class)
    private Long orderId;

    @Column(name = "order_desc")
    //@JsonView(Views.External.class)
    private String orderDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDescription='" + orderDescription + '\'' +
                ", user=" + user +
                '}';
    }
}
