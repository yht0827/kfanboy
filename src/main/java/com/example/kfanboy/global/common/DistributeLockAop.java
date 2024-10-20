package com.example.kfanboy.global.common;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.example.kfanboy.global.common.annotation.DistributeLock;
import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.global.util.CustomExpressionParser;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class DistributeLockAop {
	private final RedissonClient redissonClient;
	private final AopTransaction aopForTransaction;
	private static final String REDISSON_LOCK_PREFIX = "LOCK:";

	@Around("@annotation(com.example.kfanboy.global.common.annotation.DistributeLock)")
	public Object distributeLock(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);

		String key = REDISSON_LOCK_PREFIX + CustomExpressionParser.parseKey(signature.getParameterNames(),
			joinPoint.getArgs(), distributeLock.key());

		RLock rLock = redissonClient.getLock(key);
		try {
			boolean acquireLock = rLock.tryLock(distributeLock.waitTime(), distributeLock.leaseTime(),
				distributeLock.timeUnit()); // 락 획득 시도
			if (!acquireLock) {
				return false;
			}
			return aopForTransaction.proceed(joinPoint);
		} catch (Exception e) {
			throw new CustomException(ErrorMessage.LOCK_ERROR);
		} finally {
			rLock.unlock(); // 종료 시 락 해제
		}
	}
}
