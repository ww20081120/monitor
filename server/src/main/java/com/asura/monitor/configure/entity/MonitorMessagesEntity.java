package com.asura.monitor.configure.entity;
import com.asura.framework.base.entity.BaseEntity;


/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016-09-21 08:18:08
 * @since 1.0
 */
public class MonitorMessagesEntity extends BaseEntity{

    private String ipAddress;
    private String monitorName;
    private String cnt;
    private String indexName;
    private String value;
    private String ip;
    private String groupsName;
    private String stime;
    private Long time;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 报警次数
     */
    private int alarmCount;

    /**
     * This field corresponds to the database column monitor_messages.messages_id
     * Comment: 主键
     * @param messagesId the value for monitor_messages.messages_id
     */

    private java.lang.Integer messagesId;


    /**
     * This field corresponds to the database column monitor_messages.member
     * Comment: 
     * @param member the value for monitor_messages.member
     */

    private java.lang.String member;


    /**
     * This field corresponds to the database column monitor_messages.messages_time
     * Comment: 插入数据时间
     * @param messagesTime the value for monitor_messages.messages_time
     */

    private java.sql.Timestamp messagesTime;


    /**
     * This field corresponds to the database column monitor_messages.server_id
     * Comment: 参考CMDB的服务器ID
     * @param serverId the value for monitor_messages.server_id
     */

    private java.lang.Integer serverId;


    /**
     * This field corresponds to the database column monitor_messages.scripts_id
     * Comment: 参考scripts_id
     * @param scriptsId the value for monitor_messages.scripts_id
     */

    private java.lang.Integer scriptsId;


    /**
     * This field corresponds to the database column monitor_messages.groups_id
     * Comment: 参考业务组的ID
     * @param groupsId the value for monitor_messages.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column monitor_messages.severtity_id
     * Comment: 参考severity_id
     * @param severtityId the value for monitor_messages.severtity_id
     */

    private java.lang.Integer severtityId;


    /**
     * This field corresponds to the database column monitor_messages.email
     * Comment: 邮件地址
     * @param email the value for monitor_messages.email
     */

    private java.lang.String email;


    /**
     * This field corresponds to the database column monitor_messages.mobile
     * Comment: 手机地址
     * @param mobile the value for monitor_messages.mobile
     */

    private java.lang.String mobile;


    /**
     * This field corresponds to the database column monitor_messages.ding
     * Comment: 钉钉地址
     * @param ding the value for monitor_messages.ding
     */

    private java.lang.String ding;


    /**
     * This field corresponds to the database column monitor_messages.weixin
     * Comment: 微信地址
     * @param weixin the value for monitor_messages.weixin
     */

    private java.lang.String weixin;


    /**
     * This field corresponds to the database column monitor_messages.messages
     * Comment: 报警消息
     * @param messages the value for monitor_messages.messages
     */

    private java.lang.String messages;

    public int getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(int alarmCount) {
        this.alarmCount = alarmCount;
    }

    /**
     * This field corresponds to the database column monitor_messages.messages_id
     * Comment: 主键
     * @param messagesId the value for monitor_messages.messages_id
     */
    public void setMessagesId(java.lang.Integer messagesId){
       this.messagesId = messagesId;
    }

    /**
     * This field corresponds to the database column monitor_messages.member
     * Comment: 
     * @param member the value for monitor_messages.member
     */
    public void setMember(java.lang.String member){
       this.member = member;
    }

    /**
     * This field corresponds to the database column monitor_messages.messages_time
     * Comment: 插入数据时间
     * @param messagesTime the value for monitor_messages.messages_time
     */
    public void setMessagesTime(java.sql.Timestamp messagesTime){
       this.messagesTime = messagesTime;
    }

    /**
     * This field corresponds to the database column monitor_messages.server_id
     * Comment: 参考CMDB的服务器ID
     * @param serverId the value for monitor_messages.server_id
     */
    public void setServerId(java.lang.Integer serverId){
       this.serverId = serverId;
    }

    /**
     * This field corresponds to the database column monitor_messages.scripts_id
     * Comment: 参考scripts_id
     * @param scriptsId the value for monitor_messages.scripts_id
     */
    public void setScriptsId(java.lang.Integer scriptsId){
       this.scriptsId = scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_messages.groups_id
     * Comment: 参考业务组的ID
     * @param groupsId the value for monitor_messages.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column monitor_messages.severtity_id
     * Comment: 参考severity_id
     * @param severtityId the value for monitor_messages.severtity_id
     */
    public void setSevertityId(java.lang.Integer severtityId){
       this.severtityId = severtityId;
    }

    /**
     * This field corresponds to the database column monitor_messages.email
     * Comment: 邮件地址
     * @param email the value for monitor_messages.email
     */
    public void setEmail(java.lang.String email){
       this.email = email;
    }

    /**
     * This field corresponds to the database column monitor_messages.mobile
     * Comment: 手机地址
     * @param mobile the value for monitor_messages.mobile
     */
    public void setMobile(java.lang.String mobile){
       this.mobile = mobile;
    }

    /**
     * This field corresponds to the database column monitor_messages.ding
     * Comment: 钉钉地址
     * @param ding the value for monitor_messages.ding
     */
    public void setDing(java.lang.String ding){
       this.ding = ding;
    }

    /**
     * This field corresponds to the database column monitor_messages.weixin
     * Comment: 微信地址
     * @param weixin the value for monitor_messages.weixin
     */
    public void setWeixin(java.lang.String weixin){
       this.weixin = weixin;
    }

    /**
     * This field corresponds to the database column monitor_messages.messages
     * Comment: 报警消息
     * @param messages the value for monitor_messages.messages
     */
    public void setMessages(java.lang.String messages){
       this.messages = messages;
    }

    /**
     * This field corresponds to the database column monitor_messages.messages_id
     * Comment: 主键
     * @return the value of monitor_messages.messages_id
     */
     public java.lang.Integer getMessagesId() {
        return messagesId;
    }

    /**
     * This field corresponds to the database column monitor_messages.member
     * Comment: 
     * @return the value of monitor_messages.member
     */
     public java.lang.String getMember() {
        return member;
    }

    /**
     * This field corresponds to the database column monitor_messages.messages_time
     * Comment: 插入数据时间
     * @return the value of monitor_messages.messages_time
     */
     public java.sql.Timestamp getMessagesTime() {
        return messagesTime;
    }

    /**
     * This field corresponds to the database column monitor_messages.server_id
     * Comment: 参考CMDB的服务器ID
     * @return the value of monitor_messages.server_id
     */
     public java.lang.Integer getServerId() {
        return serverId;
    }

    /**
     * This field corresponds to the database column monitor_messages.scripts_id
     * Comment: 参考scripts_id
     * @return the value of monitor_messages.scripts_id
     */
     public java.lang.Integer getScriptsId() {
        return scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_messages.groups_id
     * Comment: 参考业务组的ID
     * @return the value of monitor_messages.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column monitor_messages.severtity_id
     * Comment: 参考severity_id
     * @return the value of monitor_messages.severtity_id
     */
     public java.lang.Integer getSevertityId() {
        return severtityId;
    }

    /**
     * This field corresponds to the database column monitor_messages.email
     * Comment: 邮件地址
     * @return the value of monitor_messages.email
     */
     public java.lang.String getEmail() {
        return email;
    }

    /**
     * This field corresponds to the database column monitor_messages.mobile
     * Comment: 手机地址
     * @return the value of monitor_messages.mobile
     */
     public java.lang.String getMobile() {
        return mobile;
    }

    /**
     * This field corresponds to the database column monitor_messages.ding
     * Comment: 钉钉地址
     * @return the value of monitor_messages.ding
     */
     public java.lang.String getDing() {
        return ding;
    }

    /**
     * This field corresponds to the database column monitor_messages.weixin
     * Comment: 微信地址
     * @return the value of monitor_messages.weixin
     */
     public java.lang.String getWeixin() {
        return weixin;
    }

    /**
     * This field corresponds to the database column monitor_messages.messages
     * Comment: 报警消息
     * @return the value of monitor_messages.messages
     */
     public java.lang.String getMessages() {
        return messages;
    }
}
