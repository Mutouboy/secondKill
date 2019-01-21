package cn.jwb5.SecondKill.controller;

import cn.jwb5.SecondKill.VO.GoodsDetailVo;
import cn.jwb5.SecondKill.VO.GoodsVo;
import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.constant.UserConstant;
import cn.jwb5.SecondKill.model.Goods;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.redis.HtmlKey;
import cn.jwb5.SecondKill.redis.RedisService;
import cn.jwb5.SecondKill.redis.UserKey;
import cn.jwb5.SecondKill.result.Result;
import cn.jwb5.SecondKill.service.GoodsService;
import cn.jwb5.SecondKill.service.UserService;
import cn.jwb5.SecondKill.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by jiangwenbin on 2019/1/9.
 */
@Controller
@RequestMapping("/goods/*")
public class GoodsController {

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;


    /**
     * 5000
     * 未优化下，QPS:287
     * 页面缓存后  QPS:485
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletResponse response, HttpServletRequest request,
                         Model model, User user){

        String html = null;
        html=redisService.get(HtmlKey.GOODS_LIST_KEY,"",String.class);

        if (!StringUtils.isEmpty(html)){
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsList();
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsList);
        IContext ctx = new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(), model.asMap(),applicationContext);
        html=thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);

        redisService.set(HtmlKey.GOODS_LIST_KEY,"",html);
        return html;
    }

    @RequestMapping(value = "to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletResponse response, HttpServletRequest request,
                           Model model,User user,@PathVariable("goodsId") long goodsId){

        String html = null;
        html=redisService.get(HtmlKey.GOODS_DETAIL_KEY,""+goodsId,String.class);

        if (!StringUtils.isEmpty(html)){
            return html;
        }

        GoodsVo good = goodsService.getGoodByGoodsId(goodsId);

        long startTime = good.getStartDate().getTime();
        long endTime = good.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainTime = 0;

        if (now<startTime){
            miaoshaStatus=0;
            remainTime=(int)((startTime-now)/1000);
        }else if (now>endTime){
            miaoshaStatus = 2;
            remainTime=-1;
        }else {
            miaoshaStatus=1;
            remainTime=0;
        }

        model.addAttribute("good",good);
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainTime",remainTime);

        IContext ctx = new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(), model.asMap(),applicationContext);
        html=thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);

        redisService.set(HtmlKey.GOODS_DETAIL_KEY,"",html);
        return html;

    }

    @RequestMapping(value = "toDetail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> toDetailStatic(User user, @PathVariable("goodsId") long goodsId){
        GoodsVo good = goodsService.getGoodByGoodsId(goodsId);

        long startTime = good.getStartDate().getTime();
        long endTime = good.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainTime = 0;

        if (now<startTime){
            miaoshaStatus=0;
            remainTime=(int)((startTime-now)/1000);
        }else if (now>endTime){
            miaoshaStatus = 2;
            remainTime=-1;
        }else {
            miaoshaStatus=1;
            remainTime=0;
        }

        GoodsDetailVo detailVo = new GoodsDetailVo();
        detailVo.setGood(good);
        detailVo.setMiaoshaStatus(miaoshaStatus);
        detailVo.setRemainTime(remainTime);
        detailVo.setUser(user);
        Result result = Result.success(detailVo);
        return result;

    }
}
