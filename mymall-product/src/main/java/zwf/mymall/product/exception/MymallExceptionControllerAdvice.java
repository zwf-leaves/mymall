package zwf.mymall.product.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zwf.mymall.common.exception.BizCodeEnum;
import zwf.mymall.common.utils.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zwf
 * @create 2020-12-23-10:20
 */
@Slf4j
@RestControllerAdvice(basePackages = "zwf.mymall.product.controller")
public class MymallExceptionControllerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R exceptionHandlerValid(MethodArgumentNotValidException e){
        log.error("数据校验异常,异常类型"+e.getMessage()+e.getClass());
        BindingResult result = e.getBindingResult();
        Map<String,String> map=new HashMap();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach((item)->{
                map.put(item.getField(),item.getDefaultMessage());
            });
            return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data",map);

    }
    @ExceptionHandler(value = {Throwable.class})
    public R exceptionHandler(Throwable e){

        log.error("错误：",e);
        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(),BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }

}
