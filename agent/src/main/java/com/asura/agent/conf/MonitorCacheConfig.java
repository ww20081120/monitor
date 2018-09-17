package com.asura.agent.conf;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 监控缓存key，统一配置入口
 *
 * @author zhaozq
 * @version 1.0
 * @Date 2016-09-14
 */
public class MonitorCacheConfig {

    /**
     * // 存放每个主机对应的在cmdb的id
     */
    public static String hostsIdKey = "cache_hosts_id_";

    /**
     * // 存放主机的配置文件， 里面放的配置 cache_host_configure_hostId
     */
    public static String cacheHostCnfigureKey = "cache_host_configure_";

    /**
     * // 存放组的配置文件，里面放的配置
     */
    public static String cacheGroupsConfigureKey = "cache_groups_configure_";

    /**
     * // 项目缓存 cache_item_itemId CACHE_ITEM_KEY
     */
    public static String cacheItemKey = "cache_item_";


    /**
     * // 联系组的缓存key，存放报警联系组的 cache_contact_group_groupId
     */
    public static String cacheContactGroupKey = "cache_contact_group_";

    /**
     * // 联系人的key，存储联系人 cache_contact_contactsId
     */
    public static String cacheContactKey = "cache_contact_";

    /**
     * // 缓存模板配置
     */
    public static String cacheTemplateKey = "cache_template_";


    /**
     * // 保存配置文件 cache_configure_configureId
     */
    public static String cacheConfigureKey = "cache_configure_";

    /**
     * // 保存所有host的id,有效的监控, ageng可以从这里判断是否需要开启监控
     */
    public static String cacheAllHostIsValid = "cache_all_host_is_valid";

    /**
     * // 保存所有groups的id,有效的监控, ageng可以从这里判断是否需要开启监控
     */
    public static String cacheAllGroupsIsValid = "cache_all_groups_is_valid";

    /**
     * // 存放每个host id的组的id,能获取到自己的组 cache_host_groups_hostID
     */
    public static String cacheHostGroupsKey = "cache_host_groups_";

    /**
     * //  缓存业务线的名称 cache_group_name_业务线ID, MAP类型
     */
    public static String cacheGroupName = "cache_group_name";

    /**
     * // 存放每个host id对应的所有配置文件id cache_host_config_HostId
     */
    public static String cacheHostConfigKey = "cache_host_config_";

    /**
     * // 存放每个组对应的所有配置文件
     */
    public static String cacheGroupConfigKey = "cache_group_config_";

    /**
     * // 存放每个主机的业务线 cache_get_cache_host_groups_ip地址
     */
    public static String getCacheHostGroupsKey = "cache_get_cache_host_groups_";

    /**
     * // 报警队列设置, 使用redis队列, 不保证数据
     */
    public static String cacheAlarmQueueKey = "cache_alarm_queue";


    /**
     * // 每个host存放一个总的update类型，每次有更新时里面记录更新的类型, 然后agent去获取相对应的数据
     * // 队列类型, 每台主机一个key 主机ID
     */
    public static String cacheHostUpdateQueue = "cache_host_update_map_";

    /**
     * // 存放主机是否存活 cache_host_is_update_id Long 类型
     */
    public static String cacheHostIsUpdate = "cache_host_is_update_";

    /**
     * // 存放每个主机id对应的IP地址 cache_host_id_to_ip_CMDB的server_id
     */
    public static String cacheHostIdToIp = "cache_host_id_to_ip_";

    /**
     * // 分布式检查agent锁 MAP类型 {"serverId", 检查时间}
     */
    public static String cacheCheckMonitorLock = "cache_check_monitor_lock";

    /**
     * // 存放分布式检查agent报警次数 cache_agent_alarm_number_ServerId int
     */
    public static String cacheAgentAlarmNumber = "cache_agent_alarm_number__";

    /**
     * // 存放agent不响应的队列， map类型， id加时间戳
     */
    public static String cacheAgentUnreachable = "cache_agent_unreachable";

    /**
     * // 存放agent正常上传数据的
     */
    public static String cacheAgentIsOk = "cache_agent_is_ok";

    /**
     * // 存放每个业务线里面的服务器ID  cache_groups_hosts_服务器ID， HashSet
     */
    public static String cacheGroupsHosts = "cache_groups_hosts_";


    /**
     * // 缓存管理员组,所有的报警都发管理员组, Map 类型
     */
    public static String cacheIsAdmin = "cache_is_admin";

    /**
     * // 缓存默认监控
     */
    public static String cacheIsDefault = "cache_is_default";

    /**
     * // 发送报警开关
     */
    public static String cacheAlarmSwitch = "cache_alarm_switch";

    /**
     * // 存放每个机器的报警状态数据 cache_host_status_map_hostid
     */
    public static String cacheHostStatusMap = "cache_host_status_map_";

    /**
     * // 队列消息存放默认脚本更新后触发监控更新 cache_default_change_queue_HOSTID
     */
    public static String cacheDefaultChangeQueue = "cache_default_change_queue_";

    /**
     * // 存放服务器信息的缓存 map 类型
     * // 存放启动的端口, 主机名, cache_agent_server_info_hostid
     */
    public static String cacheAgentServerInfo = "cache_agent_server_info_";

    /**
     * // 缓存push服务器的地址,多个用逗号分隔
     */
    public static String cachePushServer = "cache_push_server";

    /**
     * // 存储监控 cache_agent_version_hostid
     */
    public static final String cacheAgentVersion = "cache_agent_v_";

    /**
     * // 上报agent的cpu数量
     */
    public static final String cacheAgentCpu = "cache_agent_cpu_";

    /**
     * // 缓存服务器端口号 + 服务器IP地址
     */
    public static final String getCachePushServerPort = "cache_push_server_port_";


    /**
     * // 缓存单独配置的监控信息,由agent获取，减少server端计算压力
     * //    cache_alarm_item_itemId
     * // 里面是一个map，里面存放用户，生效时间, key为用户名
     */
    public static final String cacheAlarmItem = "cache_alarm_item_";

    /**
     * // 缓存某个业务线的
     * // 里面是一个map，里面存放用户，生效时间, key为用户名
     */
    public static final String cacheAlarmGroups = "cache_alarm_groups_";

    /**
     * // 缓存某种服务的
     * // 里面是一个map，里面存放用户，生效时间, key为用户名
     */
    public static final String cacheAlarmService = "cache_alarm_service_";

    /**
     * // 缓存某个服务器的
     * // 里面是一个map，里面存放用户，生效时间, key为用户名
     */
    public static final String cacheAlarmServer = "cache_alarm_server_";

    /**
     * // 存储每个主机的serviceId _ ip地址
     */
    public static final String cacheHostServiceId = "cache_host_service_id_";

    /**
     * // 缓存每个IP地址的服务器信息 _ip map
     */
    public static final String cacheIpHostInfo = "cache_ip_host_info_";

}
