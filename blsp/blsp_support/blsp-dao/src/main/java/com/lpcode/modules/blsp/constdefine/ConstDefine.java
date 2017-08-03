package com.lpcode.modules.blsp.constdefine;
/**
 * Package: com.lpcode.constdefine.message<br/>
 * Date: 15-6-2<br/>
 * Time: 下午3:06<br/>
 *
 * @author pengs
 */
public class ConstDefine {
  public static final Integer API_CALL_TYPE_ADMIN=0;
  public static final Integer API_CALL_TYPE_MEMBER=1;

  public static final int MAX_SEND_TIMES =3;
  public static final int SEND_TYPE_API=1;
  public static final int SEND_TYPE_MANUAL=2;
  public static final String SEND_BY_MOBILE="S";
  public static final String SEND_BY_EMAIL="E";
  
  public static final String CODE_SUCCESS = "0";
  public static final String MSG_CODE_SUCCESS ="SUCCESS";

  public static final String CODE_SIGN_INVALID="22310000";
  public static final String MSG_SIGN_INVALID="签名无效";

  public static final String CODE_NO_CONTENT="22310001";
  public static final String MSG_NO_CONTENT="无接收人";

  public static final String CODE_NO_RECEIVER="22310002";
  public static final String MSG_NO_RECEIVER="无接收人";

  public static final String CODE_NO_TEMPLATE="22310003";
  public static final String MSG_NO_TEMPLATE="无消息模版定义";

  public static final String CODE_ERR_TEMPLATE="22310004";
  public static final String MSG_ERR_TEMPLATE="消息模版处理错误";

  public static final String CODE_EMAIL_EXCEPTION="22310005";
  public static final String MSG_EMAIL_EXCEPTION="消息模版处理错误";


  public static final String API_CODE_TEMPLATE_CREATE="10101001";//新创建一个会员
  public static final String API_CODE_TEMPLATE_INFO="10101002";//获取会员详情
  public static final String API_CODE_TEMPLATE_UPDATE="10101003";//更新会员信息
  public static final String API_CODE_TEMPLATE_DELETE="10101003";//更新会员信息
  public static final String API_CODE_TEMPLATE_LIST="10101018";//会员列表


}
