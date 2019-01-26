package cn.jwb5.SecondKill.service;

import cn.jwb5.SecondKill.VO.GoodsVo;
import cn.jwb5.SecondKill.VO.MiaoShaVO;
import cn.jwb5.SecondKill.model.OrderInfo;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.rabbitmq.MQSender;
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

    @Autowired
    MQSender mqSender;


    @Transactional
    public void miaosha(User user,long goodId){
        MiaoShaVO miaoShaVO = new MiaoShaVO();
        miaoShaVO.setUser(user);
        miaoShaVO.setGoodsId(goodId);
        mqSender.sendMsg2MiaoshaQue(miaoShaVO);
    }

    @Transactional
    public OrderInfo miaosha(MiaoShaVO miaoShaVO){

        //减库存
        goodsService.reduceGoodsCount(miaoShaVO.getGoodsId());
        return orderService.createOrder(miaoShaVO.getUser(),miaoShaVO.getGoodsId());

    }



}
