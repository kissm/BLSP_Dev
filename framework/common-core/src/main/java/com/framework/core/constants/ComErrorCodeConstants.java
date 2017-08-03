/**
 * @Probject Name: 00_product_common
 * @Path: com.bailian.core.constantsComErrorCodeConstants.java
 * @Create By wangfei
 * @Create In 2014年9月29日 上午11:07:39
 */
package com.framework.core.constants;

/**
 * 通用系统异常信息
 *
 * @Class Name ComErrorCodeConstants
 * @Author wangfei
 * @Create In 2014年9月29日
 */
public class ComErrorCodeConstants {
	public enum ErrorCode {
		/**
		 * 错误编码规范： 长度：8位 00 0 00 000 含义如下：
		 * 12位：系统编码，00为通用，01为会员，02为商品，03为订单，04为营销，05，购物车，06，搜素
		 * 3位：消息等级，处理成功为0，INFO为1，警告为2，错误为3 严重错误为4 默认为1 45位：模块，01 678位：具体错误
		 * 系统异常：00100001-00100099
		 */
		SYSTEM_SUCCESS("0", "成功"), ACCESS_TOKEN_ERROR("01300010", "获取ACCESS_TOKEN失败"), JSAPI_TICKET_ERROR(
				"01300011", "获取JSAPI_TICKET失败"), SIGN_ERROR("01300012", "加密码验签失败"), COMMON_WARN("00200000",
						"系统警告"), PARA_NORULE_WARN("00200000", "请求参数格式不符合规则"), SYSTEM_ERROR("00300000",
								"系统处理异常"), VALIDATE_ERROR("00300001", "校验处理异常");

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
