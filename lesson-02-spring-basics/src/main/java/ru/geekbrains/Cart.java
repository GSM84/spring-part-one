package ru.geekbrains;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Integer, Integer> cartProducts = new HashMap<>();

    public void addToCart(int _id) {
        if (cartProducts.containsKey(_id)) {
            cartProducts.put(_id, cartProducts.get(_id) + 1);
        } else {
            cartProducts.put(_id, 1);
        }
    }

    public void removeFromCard(int _id){
        if (cartProducts.containsKey(_id)) {
            if (cartProducts.get(_id) > 1){
                cartProducts.put(_id, cartProducts.get(_id) - 1);
            } else if (cartProducts.get(_id) == 1) {
                cartProducts.remove(_id);
            }

        }
    }

    public void printCart(){
        for (Map.Entry<Integer, Integer> e: cartProducts.entrySet()) {
            System.out.println(String.format(" id %s, count %s",  e.getKey(), e.getValue()));
        }
    }

}
