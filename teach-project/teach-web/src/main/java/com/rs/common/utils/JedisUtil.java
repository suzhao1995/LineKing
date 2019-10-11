package com.rs.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;



/**
* JedisUtil.java
* @Description:redis工具类
* @author: suzhao
* @date: 2019年9月16日 下午3:18:46
* @version: V1.0
*/
public class JedisUtil{
	
	private static Logger logger = Logger.getLogger(JedisUtil.class);
	
	private static JedisPool pool = null;
	
	
	public static Set<String> getKeys(){
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			value = jedis.keys("USER_INFO*");
		} catch (Exception e) {
			logger.error("getKeys {} = {"+value+"}",e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String set(String key, String value, int cacheSeconds) {
		String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "0";
        } finally {
            returnResource(jedis);
        }
        return result;
    }
	
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.get(key);
				value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public static Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
				logger.info("getMap {"+key+"} = {"+value+"}");
			}
		} catch (Exception e) {
			logger.error("getMap {"+key+"} = {"+value+"}",e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public static Map<String, Object> getObjectMap(String key) {
		Map<String, Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = new HashMap<String,Object>();
				Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
				for (Map.Entry<byte[], byte[]> e : map.entrySet()){
					value.put(StringUtils.toString(e.getKey()), toObject(e.getValue()));
				}
				logger.info("getObjectMap {"+key+"} = {"+value+"}");
			}
		} catch (Exception e) {
			logger.error("getObjectMap {"+key+"} = {"+value+"}",e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.hmset(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.info("setMap {"+key+"} = {"+value+"}");
		} catch (Exception e) {
			logger.error("setMap {"+key+"} = {"+value+"}",e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
			for (Map.Entry<String, Object> e : value.entrySet()){
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.info("setObjectMap {"+key+"} = {"+value+"}");
		} catch (Exception e) {
			logger.error("setObjectMap {"+key+"} = {"+value+"}",e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static long setObjectList(String key, List<?> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(getBytesKey(key));
			}
			for (Object o : value){
				result = jedis.rpush(getBytesKey(key), toBytes(o));
			}
			if (cacheSeconds != 0) {
				jedis.expire(getBytesKey(key), cacheSeconds);
			}
			logger.info("--------setObjectList {"+key+"} = {"+value+"} -----------");
		} catch (Exception e) {
			logger.error("--------setObjectList {"+key+"} = {"+value+"} -----------",e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public static List<?> getObjectList(String key) {
		List<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
				value = new ArrayList<Object>();
				for (byte[] bs : list){
					value.add(toObject(bs));
				}
				logger.info("getObjectList {"+key+"} = {"+value+"}");
			}
		} catch (Exception e) {
			logger.error("getObjectList {"+key+"} = {"+value+"}",e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public static long del(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)){
				result = jedis.del(key);
				logger.info("del {"+key+"}");
			}else{
				logger.info("del {"+key+"} not exists");
			}
		} catch (Exception e) {
			logger.error("del {"+key+"}",e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public static long delObject(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))){
				result = jedis.del(getBytesKey(key));
				logger.info("delObject {"+key+"}");
			}else{
				logger.info("delObject {"+key+"} not exists");
			}
		} catch (Exception e) {
			logger.error("delObject {"+key+"}",e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public static boolean exists(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(key);
			logger.info("exists {"+key+"}");
		} catch (Exception e) {
			logger.error("exists {"+key+"}",e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public static boolean existsObject(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(getBytesKey(key));
			logger.info("existsObject {"+key+"}");
		} catch (Exception e) {
			logger.error("existsObject {"+key+"}",e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取byte[]类型Key
	 * @param object
	 * @return
	 */
	public static byte[] getBytesKey(Object object){
		if(object instanceof String){
    		return StringUtils.getBytes((String)object);
    	}else{
    		return ObjectUtils.serialize(object);
    	}
	}
	
	/**
	 * Object转换byte[]类型
	 * @param object
	 * @return
	 */
	public static byte[] toBytes(Object object){
    	return ObjectUtils.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * @param bytes
	 * @return
	 */
	public static Object toObject(byte[] bytes){
		return ObjectUtils.unserialize(bytes);
	}
	
	/**
	 * 获取资源
	 * @return
	 * @throws JedisException
	 */
	public static Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			if(pool == null){
				ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-redis.xml");
				pool = (JedisPool)context.getBean("jedisPool");
			}
			jedis = pool.getResource();
		} catch (JedisException e) {
			logger.error("getResource.", e);
			returnBrokenResource(jedis);
			throw e;
		}
		return jedis;
	}

	
	/**
     * 返还到连接池
     * @param pool
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }
    
    /**
	 * 归还资源
	 * @param jedis
	 */
	public static void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			pool.returnBrokenResource(jedis);
		}
	}
	
}