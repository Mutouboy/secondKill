package cn.jwb5.SecondKill.dao;

import cn.jwb5.SecondKill.VO.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by jiangwenbin on 2019/1/12.
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsList();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where mg.goods_id = #{goodsId}")
    GoodsVo getGoodByGoodsId(@Param("goodsId") long goodsId);

    //更新时追加判断解决超卖问题，数据库会自动加锁
    //秒杀订单增加联合唯一约束，插入重复数据会失败，解决超买问题
    @Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId} and stock_count>0")
    int reduceGoodsCount(long goodsId);
}
