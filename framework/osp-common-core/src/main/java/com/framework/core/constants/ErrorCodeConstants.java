package com.framework.core.constants;

/**
 * 说明:
 * 
 * @author guansq
 * @date 2012-12-10 下午02:56:48
 * @modify
 */
public class ErrorCodeConstants {
    public enum ErrorCode {
        /**
         * 错误编码规范： 长度：7位 00 0 00 00 含义： 12位：系统，商品系统是01 3位：消息等级，默认为1
         * 45位：模块，(01)00_product_business (02)00_product_admin
         * (03)00_product_syn 67位：具体错误 系统异常：0110301-0110399
         */
        /* 21100** */
        SYSTEM_ERROR("0110001", "系统运行异常！"), 
        CALL_SRC_ERROR("0120001","接口调用方来源不明"),
        PARAM_ERROR("0110002", "参数不正确！"),
        DUPLICATE_KEY_ERROR("0110004", "主键已存在！");
        private String errorCode;
        private String memo;

        private ErrorCode() {
        };

        private ErrorCode(String errorCode, String memo) {
            this.errorCode = errorCode;
            this.memo = memo;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
