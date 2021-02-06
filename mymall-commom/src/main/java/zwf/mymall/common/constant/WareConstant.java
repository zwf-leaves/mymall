package zwf.mymall.common.constant;

/**
 * @author zwf
 * @create 2020-12-31-18:26
 */
public enum  WareConstant {
    CREATED(0,"新建"),ASSIGNED(1,"已分配"),
    RECEIVE(2,"已接收"),FINISH(3,"已完成"),
    EXCEPTION(4,"有异常");
    private String msg;
    private Integer code;

    private WareConstant(Integer code,String msg ) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
    public enum  PurchaseDetialConstant {
        CREATED(0,"新建"),ASSIGNED(1,"已分配"),
        BUYING(2,"正在采购"),FINISH(3,"已完成"),
        HSSERROR(4,"采购失败");
        private String msg;
        private Integer code;

        private PurchaseDetialConstant(Integer code,String msg ) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public Integer getCode() {
            return code;
        }
    }
}

