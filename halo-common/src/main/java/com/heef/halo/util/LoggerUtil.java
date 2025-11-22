package com.heef.halo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;

/**
 * 日志工具类 - 大厂日志规范
 * 提供结构化日志输出，统一日志格式
 *
 * @author heefM
 * @date 2025-11-22
 */
public class LoggerUtil {

    /**
     * 记录方法入口日志
     *
     * @param log    logger对象
     * @param method 方法名
     * @param params 入参
     */
    public static void logMethodIn(Logger log, String method, Object... params) {
        if (log.isDebugEnabled()) {
            log.debug("→ [METHOD IN] {} | params: {}", method, formatJson(params));
        }
    }

    /**
     * 记录方法成功日志
     *
     * @param log    logger对象
     * @param method 方法名
     * @param result 返回结果
     */
    public static void logMethodOut(Logger log, String method, Object result) {
        if (log.isDebugEnabled()) {
            log.debug("← [METHOD OUT] {} | result: {}", method, formatJson(result));
        }
    }

    /**
     * 记录API请求日志
     *
     * @param log        logger对象
     * @param httpMethod HTTP方法(GET/POST/PUT/DELETE)
     * @param apiPath    API路径
     * @param params     请求参数
     */
    public static void logApiRequest(Logger log, String httpMethod, String apiPath, Object params) {
        if (log.isInfoEnabled()) {
            log.info("→ [API REQUEST] {} {} | params: {}", httpMethod, apiPath, formatJson(params));
        }
    }

    /**
     * 记录API响应日志
     *
     * @param log      logger对象
     * @param httpCode HTTP状态码
     * @param apiPath  API路径
     * @param response 响应数据
     */
    public static void logApiResponse(Logger log, int httpCode, String apiPath, Object response) {
        if (log.isInfoEnabled()) {
            log.info("← [API RESPONSE] {} {} | response: {}", httpCode, apiPath, formatJson(response));
        }
    }

    /**
     * 记录业务处理日志
     *
     * @param log     logger对象
     * @param business 业务描述
     * @param data    业务数据
     */
    public static void logBusiness(Logger log, String business, Object data) {
        if (log.isInfoEnabled()) {
            log.info("◆ [BUSINESS] {} | data: {}", business, formatJson(data));
        }
    }

    /**
     * 记录错误日志（简洁版）
     *
     * @param log     logger对象
     * @param context 上下文描述
     * @param e       异常对象
     */
    public static void logError(Logger log, String context, Exception e) {
        log.error("✗ [ERROR] {} | msg: {}", context, e.getMessage());
        log.debug("  Exception Details: ", e);
    }

    /**
     * 记录警告日志
     *
     * @param log     logger对象
     * @param warning 警告描述
     * @param data    相关数据
     */
    public static void logWarn(Logger log, String warning, Object data) {
        log.warn("⚠ [WARN] {} | data: {}", warning, formatJson(data));
    }

    /**
     * 对JSON对象进行格式化
     * 如果对象为空或非JSON格式，直接返回toString()
     *
     * @param obj 要格式化的对象
     * @return 格式化后的JSON字符串，单行紧凑格式
     */
    public static String formatJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            // 使用单行紧凑格式，避免过长占用多行
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }

    /**
     * 对JSON对象进行美化格式化（调试用）
     * 输出为多行缩进格式，便于查看复杂结构
     *
     * @param obj 要格式化的对象
     * @return 格式化后的JSON字符串，多行缩进格式
     */
    public static String formatJsonPretty(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
        } catch (Exception e) {
            return obj.toString();
        }
    }
}
