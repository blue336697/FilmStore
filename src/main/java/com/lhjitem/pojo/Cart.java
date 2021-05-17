package com.lhjitem.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/*购物车对象*/
public class Cart {
//    private Integer totalCount;
//    private BigDecimal totalPrice;
    //LinkedHashMap保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的。
    private Map<Integer,CartItem> items = new LinkedHashMap<>();

    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        //1.先查看购物车是否添加过此商品
        CartItem item = items.get(cartItem.getId());
        //只要为null就是之前没有添加过
        if(item == null){
            // 之前没添加过此商品
            items.put(cartItem.getId(), cartItem);
        }else {
            //如果已添加哎，则数量累加，金额翻倍，如果没有添加过，则放到集合中
            item.setCount(item.getCount() + 1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     *清空购物车
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     * @param id
     * @param count
     */
    public void updateCount(Integer id, Integer count){
        //先查看购物车中是否有此商品，如果有，修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if(cartItem != null){
            cartItem.setCount(count);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }

    }
    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }


    /*这里由于总金额和总数量都是根据购物车来的，所以不用这两个的set方法了*/
    public Integer getTotalCount() {
        Integer totalCount = 0;

        for (Map.Entry<Integer, CartItem> integerCartItemEntry : items.entrySet()) {
            totalCount += integerCartItemEntry.getValue().getCount();
        }

        /*for (CartItem item: items.values()) {
                totalCount += item.getCount();
        }*/
        return totalCount;
    }



    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);

        for (Map.Entry<Integer, CartItem> integerCartItemEntry : items.entrySet()) {
            totalPrice = totalPrice.add(integerCartItemEntry.getValue().getTotalPrice());
        }
        return totalPrice;
    }



    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }
}
