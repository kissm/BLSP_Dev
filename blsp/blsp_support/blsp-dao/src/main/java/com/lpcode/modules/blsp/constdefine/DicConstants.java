package com.lpcode.modules.blsp.constdefine;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DicConstants {
	
	/**
	 * 项目金额
	 */
	public static final String DIC_TYPE_PROJECT_PRICE = "project_price";
	/**
	 * 项目用途
	 */
	public static final String DIC_TYPE_PROJECT_USEAGE = "project_useage";
	/**
	 * 用地类型
	 */
	public static final String DIC_TYPE_LAND_TYPE = "land_type";

	/**====事项时限类型  start=====**/
	
	/**
	 * 一般时限
	 */
	public static final String TASK_DEFINE_TIMER_CAT_NORMAL = "1";//一般时限
	
	/**
	 * 条件时限
	 */
	public static final String TASK_DEFINE_TIMER_CAT_CONDITION = "2";//条件时限
	
	/**====事项时限类型  end=====**/
	
	/**====条件类时限维度  start=====**/
	
	/**
	 * 金额
	 */
	public static final String TASK_DEFINE_COND_DIM_TYPE_PRICE= "1";//金额
	
	/**
	 * 用途
	 */
	public static final String TASK_DEFINE_COND_DIM_TYPE_USEAGE = "2";//用途
	
	/**====条件类时限维度  end=====**/
	
	
	/**====用地类型  start=====**/
	
	/**
	 * 规划用地
	 */
	public static final String TASK_DEFINE_LAND_TYPE_PLAN= "1";//规划用地
	
	/**
	 * 招拍挂用地
	 */
	public static final String TASK_DEFINE_LAND_TYPE_INVITATION = "2";//招拍挂用地
	
	/**====用地类型  end=====**/
	
	
	/**====时限类型  start=====**/
	/**
	 * 自然日
	 */
	public static final String TASK_DEFINE_DIM_TYPE_CALENDARDAY = "1";//自然日
	
	/**
	 * 工作日
	 */
	public static final String TASK_DEFINE_DIM_TYPE_WORKDAY = "2";//工作日
	
	/**====时限类型  end=====**/
	
	
	/**====材料条件类型  start=====**/
	/**
	 * 用地类型
	 */
	public static final String TASK_DEFINE_CONDITION_LAND_TYPE = "land_type";//用地类型
	
	/**
	 * 项目初步设计评审
	 */
	public static final String TASK_DEFINE_CONDITION_NEED_PRE_AUDIT = "need_pre_audit";//项目初步设计评审
	
	/**
	 * 特殊专业工程
	 */
	public static final String TASK_DEFINE_CONDITION_SPECIAL_PROJECT = "special_project";//特殊专业工程
	
	/**
	 * 基础部分工程或地基处理工程
	 */
	public static final String TASK_DEFINE_CONDITION_WITH_BASE_PART = "with_base_part";//基础部分工程或地基处理工程
	
	/**
	 * 信息化类项目
	 */
	public static final String TASK_DEFINE_CONDITION_IT_TYPE = "it_type";//信息化类项目
	/**
	 * 市场类项目
	 */
	public static final String TASK_DEFINE_CONDITION_GOV_TYPE = "gov_type";//市场类项目

	/**====材料条件类型  end=====**/

	/**
	 * 事项任务审批中
	 */
	public static final String TASK_STATUS_IN_PROCESS = "1";
	
	/**
	 * 暂停到期，恢复的模板id
	 */
	public static final String SMS_TEMPLATE_TASK_PAUSE_END = "S010006";
	/**
	 * 超时未审批
	 */
	public static final String SMS_TEMPLATE_TASK_OVER_DUE = "S010003";
	/**
	 * 上报数据出错警报
	 */
	public static final String SMS_TEMPLATE_PUSH_WARNING = "S010010";

	/**====版本信息  start=====**/
	
	public static final String SERVICE_VERSION= "1";//版本信息，暂为 1 ，后续增加
	
	/**====版本信息  end=====**/
	
	/**====授权信息  start=====**/
	
	public static final String PERMISSION_INFO= "";//授权信息，暂为空 ，后续增加
	
	/**====授权信息  end=====**/
	private static SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");

	public static SimpleDateFormat sdf_1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getSysdate(){
		return sdf.format(new Date());
	}
	
	/**====状态码  start=====**/
	
	/**
	 * 正常码
	 */
	public static final String SERVICE_STATUS_0 = "0";
	/**
	 * 接口版本错误
	 */
	public static final String SERVICE_STATUS_10 = "10";//接口版本错误
	/**
	 * 时间戳格式错
	 */
	public static final String SERVICE_STATUS_20 = "20";//时间戳格式错
	/**
	 * 非法的请求平台id
	 */
	public static final String SERVICE_STATUS_30 = "30";//非法的请求平台id
	/**
	 * 非法的请求账号
	 */
	public static final String SERVICE_STATUS_40 = "40";//非法的请求账号
	/**
	 * 非法的请求ip
	 */
	public static final String SERVICE_STATUS_50 = "50";//非法的请求ip
	/**
	 * 未授权的接口请求
	 */
	public static final String SERVICE_STATUS_60 = "60";//未授权的接口请求
	/**
	 * 接口参数错误/缺少必填参数
	 */
	public static final String SERVICE_STATUS_70 = "70";//接口参数错误/缺少必填参数
	/**
	 * 接口参数值总长度超过最大字符数限制
	 */
	public static final String SERVICE_STATUS_80 = "80";//接口参数值总长度超过最大字符数限制
	/**
	 * 总线内部处理错误，从值100到199
	 */
	public static final String SERVICE_STATUS_100  = "100";//总线内部处理错误，从值100到199
	
	
	public static final String BLSP_RF_FORM1 = "blsp_rf_form1";
	
	public static final String BLSP_RF_FORM2 = "blsp_rf_form2";
	
	/**
	 * 证件类型：身份证
	 */
	public static final String CERT_TYPE_IDENTITY = "1";

	/**
	 * 材料是重复材料（不叠加）如身份证
	 */
	public static final String IS_ORIGINAL_CUMULATION = "0";

	/**
	 * 材料不是重复材料（叠加 用于一份原件,不做多份的时候）
	 */
	public static final String UN_ORIGINAL_CUMULATION = "1";

	/**
	 * 项目类型  1:政府   2:企业
	 */

	public static final String PROJECT_TYPE_ZF = "1";
	public static final String PROJECT_TYPE_QY = "2";

	/**====YES_OR_NO  start=====**/

	public static final String YES = "是";//个人

	public static final String NO = "否";//企业

	/**====YES_OR_NO end=====**/

	/**====TRUE_OR_FALSE  start=====**/

	public static final int TRUE = 1;//

	public static final int FALSE = 0;//

	/**====TRUE_OR_FALSE end=====**/


	/**====TRUE_OR_FALSE  start=====**/

	public static final String TEMPLATE_TYPE_GR = "1";//投资建设类表单

	public static final String TEMPLATE_TYPE_QY = "2";//投资建设类表单

	public static final String TEMPLATE_TYPE_TZJS = "3";//投资建设类表单

	/**====TRUE_OR_FALSE end=====**/

	/**====申办类型  start=====**/

	public static final String SB_TYPE_GR = "个人";//个人

	public static final String SB_TYPE_QY = "企业";//企业

	public static final String SB_TYPE_TZJS = "投资建设";//投资建设


	/**====申办类型 end=====**/

	/**====申办受理状态  start=====**/

	public static final String SB_STATUS_DSL = "待受理";//待受理

	public static final String SB_STATUS_YSL = "预受理";//预受理

	public static final String SB_STATUS_BJ = "办结";//办结

	public static final String SB_STATUS_BJCL = "补交材料";//补交材料

	public static final String SB_STATUS_TH = "退回";//退回

	public static final String SB_STATUS_SL = "受理";//受理

	public static final String SB_STATUS_BH = "驳回";//受理

	public static final String SB_STATUS_BYSL = "不予受理";//不予受理

	public static final String SB_STATUS_YLQ = "已领取";//已领取



	public static final String REPORT_STATUS_SB = "申办";
	public static final String REPORT_STATUS_WSYSL = "网上预受理";
	public static final String REPORT_STATUS_YSLTH = "不予受理";//预受理退回
	public static final String REPORT_STATUS_SL = "受理";
	public static final String REPORT_STATUS_BJGZ = "补交告知";
	public static final String REPORT_STATUS_BJSL = "补交受理";
	public static final String REPORT_STATUS_TBCXSQ = "特别程序申请";
	public static final String REPORT_STATUS_TBCXSQJG = "特别程序申请结果";
	public static final String REPORT_STATUS_BJ = "办结";
	public static final String REPORT_STATUS_LZDJ = "领证登记";
	public static final String REPORT_STATUS_CB = "承办";
	public static final String REPORT_STATUS_SH = "审核";
	public static final String REPORT_STATUS_PZ = "批准";
	public static final String REPORT_STATUS_CBTH = "承办退回";
	public static final String REPORT_STATUS_SHTH = "审核退回";
	public static final String REPORT_STATUS_PZTH = "批准退回";



	/**====申办受理状态  end=====**/

	/**====申办领证状态  start=====**/

	public static final String SB_LZ_STATUS_WLQ = "未领取";//

	public static final String SB_LZ_STATUS_YLQ = "已领取";//
	/**====申办领证状态  end=====**/

	/**====短信通知状态  start=====**/

	public static final String SB_SMS_YTZ = "已通知";//

	public static final String SB_SMS_WTZ = "未通知";//
	/**====短信通知状态  end=====**/


	/**====申办电子文件审核状态  start=====**/

	public static final String ATTACHMENT_DZWJSHSTATUS_WSH = "0";//未审核

	public static final String ATTACHMENT_DZWJSHSTATUS_TG = "1";//通过

	public static final String ATTACHMENT_DZWJSHSTATUS_BTG = "-1";//未通过

	/**====申办电子文件审核状态  end=====**/

	/**====流程特别程序 start=====**/

	public static final String MESSAGE_EVENT_NAME = "specialMessage";//

	public static final String ACT_PRE_USER_ID = "preUserId";//

	public static final String ACT_USER_ID = "userId";//

	public static final String ACT_SPECIAIL_RESULT_LABEL = "speciailResultLabel";//

	/**====流程特别程序  end=====**/

	/**====办结意见 start=====**/

	public static final String SB_BJYJ_BZCS = "补正不来办结";//

	/**====办结意见 end=====**/

	/**====申报数据来源 start=====**/

	public static final String SB_SOURCE_SYSTEM_WSBS_ADMIN = "0";//

	public static final String SB_SOURCE_SYSTEM_JIWAN_PHJ = "1";//

	public static final String SB_SOURCE_SYSTEM_WSBS = "2";//

	public static final String SB_SOURCE_SYSTEM_APP = "3";//

	/**====申报数据来源 end=====**/


	/**====申报办结类型start=====**/

	public static final String SB_BJ_TYPE_CZBJ = "0";//出证办结

	public static final String SB_BJ_TYPE_THBJ = "1";//退回办结

	public static final String SB_BJ_TYPE_ZFBJ = "2";//作废办结

	public static final String SB_BJ_TYPE_SCBJ = "3";//删除办结

	public static final String SB_BJ_TYPE_ZBBJ = "4";//转报办结

	public static final String SB_BJ_TYPE_BJBLBJ = "5";//补交不来办结

	public static final String SB_BJ_TYPE_BJ = "6";//办结

	/**====申报办结类型 end=====**/

	/**====ems快递业务类型  start=====**/

	public static final String EMS_BUSINESS_TYPE_BZKD= "1";//1:标准快递

	public static final String EMS_BUSINESS_TYPE_JJKD= "4";//4:经济快递

	public static final String EMS_BUSINESS_TYPE_DSDF= "8";//8:代收到付

	public static final String EMS_BUSINESS_TYPE_KDBG= "9";//9:快递包裹

	/**====ems快递业务类型   end=====**/

	/**====ems快递订单状态  start=====**/

	public static final String EMS_ORDER_TYPE_XJ= "1";//1新建

	public static final String EMS_ORDER_TYPE_JX= "2";//2进行

	public static final String EMS_ORDER_TYPE_WC= "3";//3完成

	public static final String EMS_ORDER_TYPE_QX= "4";//4取消

	/**====ems快递订单状态   end=====**/

	/**====业务操作日志常量start=====**/

	public static final String LOG_MENU_WSYW = "网上业务受理";

	public static final String LOG_OPERATE_YSL = "预受理";

	public static final String LOG_OPERATE_SL = "受理";

	public static final String LOG_OPERATE_BH = "驳回";

	public static final String LOG_MENU_JHYW = "叫号业务受理";

	public static final String LOG_OPERATE_CLBQ = "材料不齐";

	public static final String LOG_OPERATE_XGSX = "修改事项";

	public static final String LOG_MENU_CKYW = "窗口业务受理";

	public static final String LOG_OPERATE_BYSL = "不予受理";

	public static final String LOG_MENU_BJYW = "补交业务受理";

	public static final String LOG_MENU_ZXZX = "在线咨询回复";

	public static final String LOG_OPERATE_HF = "回复";

	public static final String LOG_OPERATE_XGHF = "修改回复";

	public static final String LOG_OPERATE_QXGK = "取消公开";

	public static final String LOG_OPERATE_GK = "公开";

	public static final String LOG_OPERATE_SC = "删除";

	public static final String LOG_MENU_LZDJ = "领证登记管理";

	public static final String LOG_OPERATE_LZDJ = "领证登记";

	public static final String LOG_MENU_LQYJ = "廉情预警数据补录";

	public static final String LOG_OPERATE_BL = "补录";

	public static final String LOG_MENU_YYFW = "预约服务办理";

	public static final String LOG_OPERATE_YYFWBL = "办理";

	public static final String LOG_MENU_DB = "我的待办业务";

	public static final String LOG_OPERATE_DBBL = "办理";

	public static final String LOG_OPERATE_TH = "退回";

	public static final String LOG_OPERATE_TBCX = "特别程序";

	public static final String LOG_MENU_TBCX = "特别程序办理";

	public static final String LOG_OPERATE_TJ = "提交";

	public static final String LOG_MENU_THYWSL = "退回业务受理";

	public static final String LOG_COLUMN_BIZMODULE ="bizModule";

	public static final String LOG_COLUMN_OPERATEOBJECTID ="operateObjectId";

	public static final String LOG_COLUMN_OPERATEOBJECTNAME ="operateObjectName";

	public static final String LOG_COLUMN_OPERATETABLENAME ="operateTableName";

	public static final String LOG_COLUMN_OPERATEDESCRIPTION ="operateDescription";

	public static final String LOG_COLUMN_OPERATEVALUE ="operateValue";

	public static final String LOG_COLUMN_OPERATEOLDVALUE ="operateOldValue";

	public static final String LOG_COLUMN_SUCCESSFLAG ="successFlag";
	/**====业务操作日志常量end=====**/

	/**====申请事项是否变更  start=====**/

	public static final String SERVICE_IS_CHANGED= "1";//已修改

	public static final String SERVICE_IS_NOT_CHANGED = "0";//未修改

	/**====申请事项是否变更   end=====**/

	/**====新增事项配置通用流程，通用流程FlowKey  start=====**/

	public static final String TBNEWITEM_FLOWKEY= "process_tylc";

	/**====新增事项配置通用流程，通用流程FlowKey   end=====**/




	public static final String APPLY_PROJECT_QUERY_ERROR_CODE_0 = "0"; //成功

	public static final String APPLY_PROJECT_QUERY_ERROR_CODE_10 = "10";//服务器内部错误

	public static final String APPLY_PROJECT_QUERY_ERROR_CODE_20 = "20";//token不正确

	public static final String APPLY_PROJECT_QUERY_ERROR_CODE_30 = "30";//分页信息不正确

	public static final String ZZJGDM_CODE = "50"; //组织机构代码证的类型编码预设
	public static final String SQQY_CODE = "2"; //申请人为企业时
	public static final String KWXZQY_CODE = "440404"; //金湾行政区域码

	/**数据上报状态**/
	public static final Integer SB_ISJH_NOUP = 0;//未上报的
	public static final Integer SB_ISJH_UP_SUCCESS = 1;//上报成功
	public static final Integer SB_ISJH_UP_FAIL = 2;//上报失败
	/**1为上报统一平台，2为共享平台，3为廉情预警**/
	public static final Integer SB_TYPE_TYPT = 1;//统一平台
	public static final Integer SB_TYPE_GXPT = 2;//共享平台
	public static final Integer SB_TYPE_LQYJ = 3;//廉情预警
}



