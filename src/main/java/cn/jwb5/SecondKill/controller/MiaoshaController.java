package cn.jwb5.SecondKill.controller;

import cn.jwb5.SecondKill.VO.GoodsVo;
import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.model.MiaoshaOrder;
import cn.jwb5.SecondKill.model.OrderInfo;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.redis.GoodsKey;
import cn.jwb5.SecondKill.redis.RedisService;
import cn.jwb5.SecondKill.result.Result;
import cn.jwb5.SecondKill.service.GoodsService;
import cn.jwb5.SecondKill.service.MiaoshaService;
import cn.jwb5.SecondKill.service.OrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenbin on 2019/1/12.
 */
@Controller
@RequestMapping("/miaosha/*")
public class MiaoshaController implements InitializingBean{


    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;


    public static final Map<Long,Boolean> miaoshaGoodsMap = new HashMap<Long,Boolean>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo>  goodsVoList = goodsService.listGoodsList();
        for (GoodsVo goodsVo : goodsVoList){
            redisService.set(GoodsKey.miaoshaById,""+goodsVo.getId(),goodsVo.getStockCount());
            miaoshaGoodsMap.put(goodsVo.getId(),false);
        }
    }

    /**
     * 未优化下，QPS：284   5000并发   超卖126
     * 预热后   QPS：330
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result doMiaosha(Model model, User user, @RequestParam("goodsId") long goodsId){
        //判断用户是否登录
        if (user == null){
            return Result.setError(ErrorMsg.NO_LOGIN);
        }
        //判断库存是否充足
        boolean over = miaoshaGoodsMap.get(goodsId);
        if (over){
            return Result.setError(ErrorMsg.NO_GOODS);
        }
        int stock = redisService.get(GoodsKey.miaoshaById,""+goodsId,int.class);
        if (stock<1){
            return Result.setError(ErrorMsg.NO_GOODS);
        }
        long remain = redisService.decr(GoodsKey.miaoshaById,""+goodsId);
        if (remain<1){
            miaoshaGoodsMap.put(goodsId,true);
        }

        //减库存，下订单，插订单
        miaoshaService.miaosha(user,goodsId);

        return Result.success("排队中");
    }
}
