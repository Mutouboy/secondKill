package cn.jwb5.SecondKill.dao;

import cn.jwb5.SecondKill.model.MiaoshaOrder;
import cn.jwb5.SecondKill.model.OrderInfo;
import cn.jwb5.SecondKill.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by jiangwenbin on 2019/1/12.
 */
@Mapper
public interface OrderDao {

    @Insert("insert into miaosha_order (user_id,order_id,goods_id) values (#{userId},#{orderId},#{goodsId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);


    @Insert("insert into order_info (user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date) " +
            "values (#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insertOrder(OrderInfo orderInfo);

    @Select("select * from miaosha_order where user_id=#{user.id} and goods_id = #{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserGood(@Param("user") User user, @Param("goodsId") long goodsId);
}
