package cn.jwb5.SecondKill.controller;

import cn.jwb5.SecondKill.VO.GoodsVo;
import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.model.MiaoshaOrder;
import cn.jwb5.SecondKill.model.OrderInfo;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.service.GoodsService;
import cn.jwb5.SecondKill.service.MiaoshaService;
import cn.jwb5.SecondKill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by jiangwenbin on 2019/1/12.
 */
@Controller
@RequestMapping("/miaosha/*")
public class MiaoshaController {


    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    OrderService orderService;


    /**
     * 未优化下，QPS：284   5000并发   超卖126
     * 预热后   QPS：330
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("do_miaosha")
    public String doMiaosha(Model model, User user, @RequestParam("goodsId") long goodsId){
        //判断用户是否登录
        if (user == null){
            model.addAttribute("errmsg", ErrorMsg.NO_LOGIN.getMsg());
            return "fail";
        }
        //判断库存是否充足
        GoodsVo goodsVo = goodsService.getGoodByGoodsId(goodsId);
        if (goodsVo.getStockCount()<=0){
            model.addAttribute("errmsg",ErrorMsg.NO_GOODS.getMsg());
            return "fail";
        }

        //判断是否重复秒杀
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserGood(user,goodsId);
        if (miaoshaOrder != null){
            model.addAttribute("errmsg",ErrorMsg.REPEAT.getMsg());
            return "fail";
        }
        //减库存，下订单，插订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
        if (orderInfo != null){
            model.addAttribute("orderInfo",orderInfo);
            model.addAttribute("goods",goodsVo);
            return "order_detail";
        }
        return "fail";
    }
}
