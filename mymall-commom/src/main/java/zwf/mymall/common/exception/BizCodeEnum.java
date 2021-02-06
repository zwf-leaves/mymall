package zwf.mymall.common.exception;

/**
 * @author zwf
 * @create 2020-12-23-10:57
 */
/*
* 10:通用
*     001：参数格式校验
* 11：shangpin
* 12：订单
* 13：购物车
* 14：物流
* */
public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000,"未知异常"),
    VALID_EXCEPTION(10001,"参数格式校验异常");

    private Integer code;
    private String msg;

    BizCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
