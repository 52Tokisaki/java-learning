package com.lesama.aop;

import com.lesama.mapper.OperateLogMapper;
import com.lesama.pojo.OperateLog;
import com.lesama.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面
 * 记录系统中所有增删改操作
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 定义切点：使用@LogOperation注解的方法
     */
    @Pointcut("@annotation(com.lesama.annotation.LogOperation)")
    public void logOperationPointcut() {
    }

    /**
     * 环绕通知：在方法执行前后记录日志
     */
    @Around("logOperationPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 获取目标方法信息
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = formatMethodParams(args);

        // 获取操作人ID
        Integer operateEmpId = getCurrentUserId();

        try {
            // 执行目标方法
            Object result = joinPoint.proceed();

            // 计算执行耗时
            long costTime = System.currentTimeMillis() - startTime;

            // 记录操作日志
            String returnValue = formatReturnValue(result);
            saveOperateLog(operateEmpId, className, methodName, methodParams,
                    returnValue, costTime);

            return result;

        } catch (Exception e) {
            // 如果方法执行异常，记录异常信息到返回值中
            long costTime = System.currentTimeMillis() - startTime;
            String errorMsg = "异常信息: " + e.getMessage();

            saveOperateLog(operateEmpId, className, methodName, methodParams,
                    errorMsg, costTime);

            // 继续抛出异常，不影响业务逻辑
            throw e;
        }
    }

    /**
     * 格式化方法参数
     * 将参数数组转换为字符串
     */
    private String formatMethodParams(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(formatObject(args[i]));
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 格式化返回值
     */
    private String formatReturnValue(Object result) {
        if (result == null) {
            return "null";
        }
        return formatObject(result);
    }

    /**
     * 格式化对象为字符串
     * 支持基本类型、字符串、数组、集合等
     */
    private String formatObject(Object obj) {
        if (obj == null) {
            return "null";
        }

        // 基本类型或字符串直接返回
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Number || obj instanceof Boolean || obj instanceof Character) {
            return obj.toString();
        }

        // 数组处理
        if (obj.getClass().isArray()) {
            return Arrays.deepToString((Object[]) obj);
        }

        // 其他对象使用toString
        return obj.toString();
    }

    /**
     * 保存操作日志
     */
    private void saveOperateLog(Integer operateEmpId, String className, String methodName,
                                String methodParams, String returnValue, Long costTime) {
        try {
            OperateLog operateLog = new OperateLog();
            operateLog.setOperateEmpId(operateEmpId);
            operateLog.setOperateTime(LocalDateTime.now());
            operateLog.setClassName(className);
            operateLog.setMethodName(methodName);

            // 截断参数和返回值，避免超过字段长度限制（2000字符）
            operateLog.setMethodParams(truncateString(methodParams, 2000));
            operateLog.setReturnValue(truncateString(returnValue, 2000));
            operateLog.setCostTime(costTime);

            // 插入日志
            operateLogMapper.insert(operateLog);

            log.info("操作日志记录成功: 操作人={}, 方法={}, 耗时={}ms",
                    operateEmpId, methodName, costTime);
        } catch (Exception e) {
            log.error("记录操作日志失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 截断字符串到指定长度
     */
    private String truncateString(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...(截断)";
    }

    /**
     * 获取当前操作人ID
     * 支持多种获取方式
     */
    private Integer getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }
}