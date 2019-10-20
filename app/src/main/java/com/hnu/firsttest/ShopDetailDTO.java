package com.hnu.firsttest;

import java.math.BigDecimal;

/**
 * 返回用户商铺的所有信息
 *
 * @author zhaohaojie
 * @date 2019-10-20 12:18
 */

public class ShopDetailDTO {

    private Integer shopId;
    private Integer goodsId;
    private String shopName;
    private String goodsName;
    private BigDecimal price;
    private String pictureURL;//可以null
    private String address;
}

