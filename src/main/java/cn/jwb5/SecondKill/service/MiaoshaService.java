package cn.jwb5.SecondKill.service;

import cn.jwb5.SecondKill.VO.GoodsVo;
import cn.jwb5.SecondKill.model.OrderInfo;
import cn.jwb5.SecondKill.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by jiangwenbin on 2019/1/12.
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;


    @Transactional
    public OrderInfo miaosha(User user,GoodsVo goodsVo){

        //减库存
        goodsService.reduceGoodsCount(goodsVo.getId());

        return orderService.createOrder(user,goodsVo);

    }
}
