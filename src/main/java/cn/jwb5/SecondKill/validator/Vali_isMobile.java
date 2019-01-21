package cn.jwb5.SecondKill.validator;
import cn.jwb5.SecondKill.utils.StringUtils;
import cn.jwb5.SecondKill.utils.ValidatorUtils;

import javax.validation.*;
/**
 * Created by jiangwenbin on 2019/1/8.
 */
public class Vali_isMobile implements ConstraintValidator<IsMobile,String>{

    private boolean required = false;

    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidatorUtils.isMobile(s);
        }else {
            if (StringUtils.isEmpty(s)){
                return true;
            }else {
                return ValidatorUtils.isMobile(s);
            }
        }
    }
}
