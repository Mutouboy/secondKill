package cn.jwb5.SecondKill.service;

import cn.jwb5.SecondKill.VO.GoodsVo;
import cn.jwb5.SecondKill.dao.OrderDao;
import cn.jwb5.SecondKill.model.MiaoshaOrder;
import cn.jwb5.SecondKill.model.OrderInfo;
import cn.jwb5.SecondKill.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by jiangwenbin on 2019/1/12.
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public long insertOrder(OrderInfo orderInfo) {
        return orderDao.insertOrder(orderInfo);
    }

    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder) {
        return orderDao.insertMiaoshaOrder(miaoshaOrder);
    }


    public MiaoshaOrder getMiaoshaOrderByUserGood(User user, long goodsId) {
        return orderDao.getMiaoshaOrderByUserGood(user,goodsId);
    }


    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();

        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(Long.valueOf(user.getId()));
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setGoodsCount(1);

        long orderId = insertOrder(orderInfo);

        miaoshaOrder.setUserId(Long.valueOf(user.getId()));
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);

        int result = insertMiaoshaOrder(miaoshaOrder);

        return orderInfo;
    }
}
