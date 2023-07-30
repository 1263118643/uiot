//package com.uiot.uiotsendnorth.utils;
//
//import org.assertj.core.util.Sets;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.SessionCallback;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import redis.clients.jedis.JedisCommands;
//import redis.clients.jedis.ScanParams;
//import redis.clients.jedis.ScanResult;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
///**
// * Redis工具类
// */
//@SuppressWarnings("unchecked")
//@Component
//public final class RedisUtil<E> {
//
//	private RedisTemplate redisTemplate;
//
//	/**
//	 * Component注入其他bean为null,所以在创建是赋值吧
//	 */
//	@Autowired
//	public RedisUtil(RedisTemplate redisTemplate) {
//		this.redisTemplate = redisTemplate;
//	}
//
//	public RedisUtil() {
//		this.redisTemplate = SpringUtil.getBean("redisTemplate");
//	}
//	// =============================common============================
//	/**
//	 *
//	 * 指定缓存失效时间
//	 *
//	 * @param key  键
//	 * @param time 时间(秒)
//	 * @return
//	 */
//	public boolean expire(String key, long time) {
//		try {
//			if (time > 0) {
//				redisTemplate.expire(key, time, TimeUnit.SECONDS);
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 根据key 获取过期时间
//	 *
//	 * @param key 键 不能为null
//	 * @return 时间(秒) 返回0代表为永久有效
//	 */
//	public long getExpire(String key) {
//		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
//	}
//
//	/**
//	 *
//	 * 判断key是否存在
//	 *
//	 * @param key 键
//	 * @return true 存在 false不存在
//	 */
//	public boolean hasKey(String key) {
//		try {
//			return redisTemplate.hasKey(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * 删除缓存
//	 * @param key 可以传一个值 或多个
//	 */
//	public void del(String... key) {
//		if (key != null && key.length > 0) {
//			if (key.length == 1) {
//				redisTemplate.delete(key[0]);
//			} else {
//				redisTemplate.delete(CollectionUtils.arrayToList(key));
//			}
//		}
//	}
//
//	// ============================String=============================
//	/**
//	 * 普通缓存获取
//	 * @param key 键
//	 * @return 值
//	 *
//	 */
//	public Object get(String key) {
//		return key == null ? null : redisTemplate.opsForValue().get(key);
//	}
//
//	/**
//	 * 普通缓存放入
//	 * @param key   键
//	 * @param value 值
//	 * @return true成功 false失败
//	 */
//	public boolean set(String key, Object value) {
//		try {
//			redisTemplate.opsForValue().set(key, value);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * 普通缓存放入并设置时间
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
//	 * @return true成功 false 失败
//	 *
//	 */
//	public boolean set(String key, Object value, long time) {
//		try {
//			if (time > 0) {
//				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//			} else {
//				set(key, value);
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * 递增
//	 * @param key   键
//	 * @param delta 要增加几(大于0)
//	 * @return
//	 *
//	 */
//	public long incr(String key, long delta) {
//		if (delta < 0) {
//			throw new RuntimeException("递增因子必须大于0");
//		}
//		return redisTemplate.opsForValue().increment(key, delta);
//	}
//
//	/**
//	 * 递减
//	 * @param key   键
//	 * @param delta 要减少几(小于0)
//	 * @return
//	 *
//	 */
//	public long decr(String key, long delta) {
//		if (delta < 0) {
//			throw new RuntimeException("递减因子必须大于0");
//		}
//		return redisTemplate.opsForValue().increment(key, -delta);
//	}
//
//	/**
//	 * ================================Map=================================
//	 * HashGet
//	 * @param key  键 不能为null
//	 * @param item 项 不能为null
//	 * @return 值
//	 *
//	 */
//	public Object hget(String key, String item) {
//		return redisTemplate.opsForHash().get(key, item);
//	}
//
//	/**
//	 *
//	 * 获取hashKey对应的所有键值
//	 *
//	 * @param key 键
//	 * @return 对应的多个键值
//	 *
//	 */
//	public Map<Object, Object> hmget(String key) {
//		return redisTemplate.opsForHash().entries(key);
//	}
//
//	/**
//	 *
//	 * HashSet
//	 *
//	 * @param key 键
//	 * @param map 对应多个键值
//	 * @return true 成功 false 失败
//	 *
//	 */
//	public boolean hmset(String key, Map<String, Object> map) {
//		try {
//			redisTemplate.opsForHash().putAll(key, map);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * HashSet 并设置时间
//	 *
//	 * @param key  键
//	 * @param map  对应多个键值
//	 * @param time 时间(秒)
//	 * @return true成功 false失败
//	 *
//	 */
//	public boolean hmset(String key, Map<String, Object> map, long time) {
//		try {
//			redisTemplate.opsForHash().putAll(key, map);
//			if (time > 0) {
//				expire(key, time);
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 向一张hash表中放入数据,如果不存在将创建
//	 *
//	 * @param key   键
//	 * @param item  项
//	 * @param value 值
//	 * @return true 成功 false失败
//	 *
//	 */
//	public boolean hset(String key, String item, Object value) {
//		try {
//			redisTemplate.opsForHash().put(key, item, value);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 向一张hash表中放入数据,如果不存在将创建
//	 *
//	 * @param key   键
//	 * @param item  项
//	 * @param value 值
//	 * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
//	 * @return true 成功 false失败
//	 *
//	 */
//	public boolean hset(String key, String item, Object value, long time) {
//		try {
//			redisTemplate.opsForHash().put(key, item, value);
//			if (time > 0) {
//				expire(key, time);
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 删除hash表中的值
//	 *
//	 * @param key  键 不能为null
//	 * @param item 项 可以使多个 不能为null
//	 *
//	 */
//	public void hdel(String key, Object... item) {
//		redisTemplate.opsForHash().delete(key, item);
//	}
//
//	/**
//	 *
//	 * 判断hash表中是否有该项的值
//	 *
//	 * @param key  键 不能为null
//	 * @param item 项 不能为null
//	 * @return true 存在 false不存在
//	 *
//	 */
//	public boolean hHasKey(String key, String item) {
//		return redisTemplate.opsForHash().hasKey(key, item);
//	}
//
//	/**
//	 *
//	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
//	 *
//	 * @param key  键
//	 * @param item 项
//	 * @param by   要增加几(大于0)
//	 * @return
//	 *
//	 */
//	public double hincr(String key, String item, double by) {
//		return redisTemplate.opsForHash().increment(key, item, by);
//	}
//
//	/**
//	 *
//	 * hash递减
//	 *
//	 * @param key  键
//	 * @param item 项
//	 * @param by   要减少记(小于0)
//	 * @return
//	 *
//	 */
//	public double hdecr(String key, String item, double by) {
//		return redisTemplate.opsForHash().increment(key, item, -by);
//	}
//
//	// ============================set=============================
//	/**
//	 *
//	 * 根据key获取Set中的所有值
//	 *
//	 * @param key 键
//	 * @return
//	 *
//	 */
//	public Set<Object> sGet(String key) {
//		try {
//			System.out.println(redisTemplate.opsForSet().members(key));
//			return redisTemplate.opsForSet().members(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 *
//	 * 根据value从一个set中查询,是否存在
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @return true 存在 false不存在
//	 *
//	 */
//	public boolean sHasKey(String key, Object value) {
//		try {
//			return redisTemplate.opsForSet().isMember(key, value);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 将数据放入set缓存
//	 *
//	 * @param key    键
//	 * @param values 值 可以是多个
//	 * @return 成功个数
//	 *
//	 */
//	public long sSet(String key, Object... values) {
//		try {
//			return redisTemplate.opsForSet().add(key, values);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	/**
//	 *
//	 * 将set数据放入缓存
//	 *
//	 * @param key    键
//	 * @param time   时间(秒)
//	 * @param values 值 可以是多个
//	 * @return 成功个数
//	 *
//	 */
//	public long sSetAndTime(String key, long time, Object... values) {
//		try {
//			Long count = redisTemplate.opsForSet().add(key, values);
//			if (time > 0)
//				expire(key, time);
//			return count;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	/**
//	 *
//	 * 获取set缓存的长度
//	 *
//	 * @param key 键
//	 * @return
//	 *
//	 */
//	public long sGetSetSize(String key) {
//		try {
//			return redisTemplate.opsForSet().size(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	/**
//	 *
//	 * 移除值为value的
//	 *
//	 * @param key    键
//	 * @param values 值 可以是多个
//	 * @return 移除的个数
//	 *
//	 */
//	public long setRemove(String key, Object... values) {
//		try {
//			Long count = redisTemplate.opsForSet().remove(key, values);
//			return count;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	// ===============================list=================================
//	/**
//	 *
//	 * 获取list缓存的内容
//	 *
//	 * @param key   键
//	 * @param start 开始
//	 * @param end   结束 0 到 -1代表所有值
//	 * @return
//	 *
//	 */
//	public List<Object> lGet(String key, long start, long end) {
//		try {
//			return redisTemplate.opsForList().range(key, start, end);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 *
//	 * 获取list缓存的长度
//	 *
//	 * @param key 键
//	 * @return
//	 *
//	 */
//	public long lGetListSize(String key) {
//		try {
//			return redisTemplate.opsForList().size(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	/**
//	 *
//	 * 通过索引 获取list中的值
//	 *
//	 * @param key   键
//	 * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
//	 * @return
//	 *
//	 */
//	public Object lGetIndex(String key, long index) {
//		try {
//			return redisTemplate.opsForList().index(key, index);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 *
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @return
//	 *
//	 */
//	public boolean lSet(String key, Object value) {
//		try {
//			redisTemplate.opsForList().rightPush(key, value);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒)
//	 * @return
//	 *
//	 */
//	public boolean lSet(String key, Object value, long time) {
//		try {
//			redisTemplate.opsForList().rightPush(key, value);
//			if (time > 0)
//				expire(key, time);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @return
//	 *
//	 */
//	public boolean lSet(String key, List<Object> value) {
//		try {
//			redisTemplate.opsForList().rightPushAll(key, value);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒)
//	 * @return
//	 *
//	 */
//	public boolean lSet(String key, List<Object> value, long time) {
//		try {
//			redisTemplate.opsForList().rightPushAll(key, value);
//			if (time > 0)
//				expire(key, time);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 根据索引修改list中的某条数据
//	 *
//	 * @param key   键
//	 * @param index 索引
//	 * @param value 值
//	 * @return
//	 *
//	 */
//	public boolean lUpdateIndex(String key, long index, Object value) {
//		try {
//			redisTemplate.opsForList().set(key, index, value);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * 移除N个值为value
//	 *
//	 * @param key   键
//	 * @param count 移除多少个
//	 * @param value 值
//	 * @return 移除的个数
//	 *
//	 */
//	public long lRemove(String key, long count, Object value) {
//		try {
//			Long remove = redisTemplate.opsForList().remove(key, count, value);
//			return remove;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	/**
//	 * 获取所有的key
//	 * @param key
//	 * @return
//	 */
//	public Set getkeys(String key){
//		try {
//			Set<String> keys = redisTemplate.keys(key);
//			return keys;
//		}catch (Exception e){
//			e.printStackTrace();
//			return null;
//		}
//	}
//	/**
//	 * 取值 - <brpop：阻塞式> - 推荐使用
//	 * @param key 键
//	 * @param timeout 超时时间
//	 * @param timeUnit 给定单元粒度的时间段
//	 *                 TimeUnit.DAYS          //天
//	 *                 TimeUnit.HOURS         //小时
//	 *                 TimeUnit.MINUTES       //分钟
//	 *                 TimeUnit.SECONDS       //秒
//	 *                 TimeUnit.MILLISECONDS  //毫秒
//	 * @return
//	 */
//	public Object brpop(String key, long timeout, TimeUnit timeUnit) {
//		try {
//			return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	/**
//	 * 使用set pipline方法存储set数据
//	 */
////	public void addPipline(String typeName,List<T> paramsList) {
////		long start = System.currentTimeMillis();
////		redisTemplate.executePipelined(new SessionCallback<Object>() {
////			@Override
////			public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
////				Iterator<T> iterator = paramsList.iterator();
////				while (iterator.hasNext()) {
////					T t = iterator.next();
////					Class tClass1 = t.getClass();
////					tClass1 = tClass1.getSuperclass();
////					Method m1 = null;
////					try {
////						m1 = tClass1.getDeclaredMethod("getDeviceId");
////						Method m2 = tClass1.getDeclaredMethod("getDeviceName");
////						String deviceId = (String) m1.invoke(t);
////						String deviceName = (String) m2.invoke(t);
////						String value = deviceId + ":" + deviceName;
////						operations.opsForSet().add((K)(typeName), (V)(value));
////					} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
////						e.printStackTrace();
////					}
////				}
////				System.err.println(typeName+"执行redis任务消耗了 ：" + (System.currentTimeMillis() - start) + "毫秒");
////				return null;
////			}
////		});
////	}
//
////	public void addPipline(String typeName,String value) {
////		long start = System.currentTimeMillis();
////		redisTemplate.executePipelined(new SessionCallback<Object>() {
////			@Override
////			public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
////				operations.opsForSet().add((K)(typeName), (V)(value));
////				return null;
////			}
////		});
////	}
//	public void addPipline(String typeName,Set<Object> values) {
//		try{
//			if(values!=null){
//				redisTemplate.executePipelined(new SessionCallback<Object>() {
//					@Override
//					public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
//						for(Object value:values){
//							operations.opsForSet().add((K)(typeName), (V)(value));
//						}
//						return null;
//					}
//				});
//			}
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 使用 set 删除指令
//	 */
//	public void deletePipline(String typeName, Set<Object> oldDevices){
//		try {
//			redisTemplate.executePipelined(new SessionCallback<Object>() {
//				@Override
//				public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
//					for (Object oldDevice: oldDevices) {
//						operations.opsForSet().remove((K) typeName, (V) oldDevice);
//					}
//					return null;
//				}
//			});
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 使用非阻塞获取set集合
//	 * @param key
//	 * @return
//	 */
//	public Set<String> getSScan(String key){
//		Set<String> devices = new HashSet<>();
//	 redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
//			Set<String> keys = Sets.newHashSet();
//			JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//			ScanParams scanParams = new ScanParams();
//			scanParams.count(1000);
//			String cursor = "0";
//			do{
//				ScanResult<String> scanResult = commands.sscan(key,cursor,scanParams);
//				cursor = scanResult.getStringCursor();
//				List<String> memberList = scanResult.getResult();
//				devices.addAll(memberList);
//			} while(!"0".equals(cursor));
//			return null;
//		});
//	 return devices;
//	}
//
//	/**
//	 * 使用set集合去重
//	 */
////	public List<T> filterSet(List<T> paramsList){
////		Class tClass = paramsList.get(0).getClass();
////		String typeName = tClass.getSimpleName();
////		List<T> filterList = new ArrayList<>(paramsList);
////		Iterator<T> iterator = filterList.iterator();
////		/**
////		 * 进行对比
////		 */
////		Set<String> oldDevices = this.getSScan(typeName);
////		Set<String> newDevice = new HashSet<>();
////		while (iterator.hasNext()) {
////			T t = iterator.next();
////			Class tClass1 = t.getClass();
////			tClass1 = tClass1.getSuperclass();
////			Method m1 = null;
////			try {
////				m1 = tClass1.getDeclaredMethod("getDeviceId");
////				Method m2 = tClass1.getDeclaredMethod("getDeviceName");
////				String deviceId = (String) m1.invoke(t);
////				String deviceName = (String) m2.invoke(t);
////				String value = deviceId + ":" + deviceName;
////				if(oldDevices.contains(value)){
////					iterator.remove();
////					oldDevices.remove(value);
////				}
////				newDevice.add(value);
////			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
////				e.printStackTrace();
////			}
////		}
////		//创建设备
////		//this.addPipline(typeName, filterList);
////		this.deletePipline(typeName,oldDevices);
////		return filterList;
////	}
//}
//
