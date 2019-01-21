package cn.jwb5.SecondKill.dao;

import cn.jwb5.SecondKill.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by jiangwenbin on 2019/1/7.
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getById(@Param("id") String id);

    @Insert("insert into user values (#{id},#{name})")
    int insert(@Param("id") String id,
               @Param("name") String name);




    @Insert("insert into user (id,nickname,password,salt,register_date,login_count) " +
            "values (#{id},#{nickname},#{password},#{salt},#{registerDate},#{loginCount})")
    int insertUser(User user);

    @Update("update user set password = #{password} where id = #{id}")
    int updatePassword(User user);
}
