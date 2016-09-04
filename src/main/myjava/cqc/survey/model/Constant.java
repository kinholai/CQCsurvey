package cqc.survey.model;

/**
 * 常量池
 * @author Kinho
 *
 */
public class Constant
{

	/**
	 * 问题选项表示控件
	 */
	public static final String INPUT_SELECT = "下拉框";
	
	public static final String INPUT_RADIO = "单选框";
	
	public static final String INPUT_CHECKBOX = "复选框";
	
	public static final String INPUT_TEXT = "输入框";
	
	public static final String INPUT_TEXTAREA = "文本框";
			
	
	/**
	 * 调查 发布状态 or问卷板块 录入状态
	 */
	public static final Short UNUSING_STATE = 0; //未发布 or 未录入
	
	public static final Short USING_STATE = 1; //已发布 or 已录入
	
	
	/**
	 * 调查 限值状态
	 */
	public static final String UNLIMITED_STATE_NAME = "同一IP不限提交次数";
	public static final Short UNLIMITED_STATE_VALUE = 0; 
	
	public static final String SINGLE_IP_STATE_NAME = "同一IP限提交x次";
	public static final Short SINGLE_IP_STATE_VALUE = 1; 
	
	public static final String WHITELIST_UNLIMITED_STATE_NAME = "仅白名单IP不限提交次数";
	public static final Short WHITELIST_UNLIMITED_STATE_VALUE = 2; 
	
	public static final String BLACKLIST_LIMITED_STATE_NAME = "黑名单IP限提交x次，非黑名单IP不限提交次数";
	public static final Short BLACKLIST_LIMITED_STATE_VALUE = 3;
	
	public static final String MIXED_STATE_NAME = "仅白名单IP不限提交次数，且黑名单IP限提交x次";
	public static final Short MIXED_STATE_VALUE = 4;
	
}
