//package com.heef.halo.domain.context;
//
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//
//
///**
// * 登录应用上下文  (配置)
// *
// * @author heefM
// * @date 2025-07-21
// */
//public class LoginContextHolder {
//
//    private static final InheritableThreadLocal<Map<String, Object>> LOGIN_CONTEXT_THREAD_LOCAL = new InheritableThreadLocal<>();
//
//
//    /**
//     * 获取当前线程的登录上下文
//     *
//     * @return
//     */
//    public static Map<String, Object> getThreadLocalMap() {
//        Map<String,Object> map = LOGIN_CONTEXT_THREAD_LOCAL.get();
//        //如果登录上下文为空,则创建一个
//        if(Objects.isNull(LOGIN_CONTEXT_THREAD_LOCAL.get())){
//            map = new ConcurrentHashMap<>();
//            LOGIN_CONTEXT_THREAD_LOCAL.set(map);
//        }
//        return map;
//    }
//
//    /**
//     * 设置登录上下文
//     *
//     * @param key/value
//     */
//    public static void set(String key, Object value) {
//        if (key == null || value == null) {
//            throw new IllegalArgumentException("Key 或 Value 不能为 null");
//        }
//
//        Map<String, Object> map = getThreadLocalMap();
//        map.put(key,value);
//    }
//
//    /**
//     * 获取登录上下文
//     *
//     * @param key
//     * @return
//     */
//    public static Object get(String key){
//        Map<String, Object> threadLocalMap = getThreadLocalMap();
//        return threadLocalMap.get(key);
//    }
//
//    /**
//     * 移除登录上下文
//     */
//    public static void remove() {
//        LOGIN_CONTEXT_THREAD_LOCAL.remove();
//    }
//
//
//
//    /**
//     *  获取登录用户ID
//     * @return
//     */
//    public static String getLoginId(){
//        return (String) getThreadLocalMap().get("loginId");
//    }
//
//
//}
