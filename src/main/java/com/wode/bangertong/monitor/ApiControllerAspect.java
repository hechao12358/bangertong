//package com.wode.bangertong.monitor;
//
//import com.wode.bangertong.constants.RedisContants;
//import com.wode.bangertong.common.entity.User;
//import com.wode.bangertong.exception.NoPermissionException;
//import com.wode.bangertong.exception.PermissionException;
//import com.wode.bangertong.common.model.Result;
//import com.wode.bangertong.utils.JsonUtil;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Date;
//
///*
// * 定义切面执行的优先级，数字越低，优先级越高
// * 在切入点之前执行：按order值有小到大的顺序执行
// * 在切入点之后执行：按order值由大到小的顺序执行
// */
////@Slf4j
//@Aspect
//@Component
//public class ApiControllerAspect
//{
//    @Resource
//    RedisTemplate redisTemplate;
//
//    private static Log log = LogFactory.getLog(ApiControllerAspect.class);
//
//
//    private ThreadLocal<Long> time = new ThreadLocal<>();
//
//    @Pointcut("execution(* com.wode.bangertong.controller..*(..)) ")
//    public void controllerMethodPointcut() {}
//
//    @Pointcut("execution(* com.wode.bangertong.controller..*(..))")
//    public void controllerExceptionPointcut() {}
//
//
//    @Before("controllerMethodPointcut()")
//    public void doBefore(JoinPoint joinPoint) {
//
//        time.set(System.currentTimeMillis()); //记录执行开始时间
//
//        ServletRequestAttributes attributes =
//            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        String url = request.getServletPath();
//        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
//        Method method = sign.getMethod();
//
//        String token = request.getHeader("Authorization");
//        User user = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);
//
////        验证是否登录
//        if((null == token || null == user)
//                && !url.equals("/bangertong/user/api/miniprogramQuickLogin")
//                && !url.equals("/bangertong/user/api/getUserPhonenumber")
//                && !url.equals("/bangertong/htmlPage/list")){
//                throw new PermissionException();
//
//        }
//
//        // 记录下请求内容
//        log.info("URL : " + request.getRequestURL().toString());
//        log.info("HTTP_METHOD : " + request.getMethod());
//        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//
//    }
//
//    @AfterReturning(value = "controllerMethodPointcut()", returning = "returnVal")
//    public void doAfterReturning(JoinPoint joinPoint, Object returnVal){
//        log.info(joinPoint.getSignature().getName() + ": return :" + JsonUtil.TO_JSON(returnVal));
//        log.info("time-consuming:"+ (System.currentTimeMillis() - time.get()) / 1000);
//        // 处理完请求，返回内容
//        log.info("WebLogAspect.doAfterReturning()");
//    }
//
//    @Around("controllerExceptionPointcut()")
//    public Object handle(ProceedingJoinPoint joinPoint){
//        Object result = null;
//        try {
//            result = joinPoint.proceed();
//        }catch (PermissionException npe){
//            log.error("exception :", npe);
//            //PermissionException token异常
//            return new Result(Result.RESULT_NOT_LOGIN, "用户未登录", new Date().getTime());
//        }catch (NoPermissionException npe){
//            log.error("exception :", npe);
//            //NoPermissionException token异常
//            return new Result(Result.RESULT_FAIL, "暂无权限", new Date().getTime());
//        } catch (Exception npe){
//            log.error("exception :", npe);
//            //NullParamException 并不仅仅是传递的参数，也有可能是token为空
//            return new Result(Result.RESULT_PARAMETER_ERROR, "请求出错！", new Date().getTime());
//
//        } catch (Throwable npe){
//            log.error("exception :", npe);
//            return new Result(Result.RESULT_ERROR, "请求异常！", new Date().getTime());
//        }
//        return result;
//    }
//}
