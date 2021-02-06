package zwf.mymall.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zwf
 * @create 2020-12-23-14:15
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    Set<Integer> set = new HashSet<>();
    //初始化
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for (Integer val:vals) {
            set.add(val);
        }
    }
    //信息校验
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(set.contains(value)){
            return  true;
        }else{
            return false;
        }

    }
}
