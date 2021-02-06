package zwf.mymall.common.constant;

/**
 * @author zwf
 * @create 2020-12-27-22:23
 */
public class ProductConstant {
    public enum AttrEnue{
        ATTR_TYPE_BASE(1,"基本属性"),ATTY_TYPE_SALE(0,"销售属性");

        private String msg;
        private int code;
        AttrEnue(int code,String msg) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }
}
