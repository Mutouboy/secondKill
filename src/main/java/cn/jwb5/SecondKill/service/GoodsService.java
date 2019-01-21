package cn.jwb5.SecondKill.service;

import cn.jwb5.SecondKill.VO.GoodsVo;
import cn.jwb5.SecondKill.dao.GoodsDao;
import cn.jwb5.SecondKill.model.MiaoshaOrder;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.redis.GoodsKey;
import cn.jwb5.SecondKill.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangwenbin on 2019/1/12.
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    RedisService redisService;

    public List<GoodsVo> listGoodsList(){
        return goodsDao.listGoodsList();

    }

    public GoodsVo getGoodByGoodsId(long goodsId) {
        GoodsVo goodsVo = redisService.get(GoodsKey.byId,""+goodsId,GoodsVo.class);
        if (goodsVo != null){
            return goodsVo;
        }
        goodsVo = goodsDao.getGoodByGoodsId(goodsId);
        redisService.set(GoodsKey.byId,""+goodsId,goodsVo);
        return goodsVo;
    }


    public int reduceGoodsCount(long goodsId){
        return goodsDao.reduceGoodsCount(goodsId);
    }


}
