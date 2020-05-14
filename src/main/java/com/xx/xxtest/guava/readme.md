https://github.com/google/guava/wiki

### Enums.getIfPresent(Class<T> enumClass, String value)
1. checkNotNull 参数判空
2. ref = Enums.getEnumConstants(enumClass).get(value)
    - ref 为 WeakReference
    - getEnumConstants 返回 Map<String, WeakReference>，获取过程 synchronized 加锁
    - 内部通过 enumConstantCache 进行缓存，类型为 WeakHashMap, key 为 Enum 类型的 class，value 类型为 Map<String, WeakReference>
    - 如果没有这个枚举类型，就 populateCache(enumClass) 得到该枚举所有值的 Map 添加到 enumConstantCache
3. ref == null ? Optional.<T>absent() : Optional.of(enumClass.cast(ref.get()));
    - absent() 返回 Optional 的子类 Absent，of 返回子类 Present，都重写了 or 等方法

### Stopwatch
- 非线程安全
- isRunning，elapsedNanos，startTick 等几个成员变量来控制流程以及获取信息

## cache
cache<k,v> 为接口，AbstractCache 为抽象类实现

AbstractCache 实现了一些批量操作，如 getAllPresent，putAll 等，其他方法抛出 UnsupportedOperationException 让子类去实现，
其内部还定义了 StatsCounter 状态统计接口，并提供了简单实现 SimpleStatsCounter 

使用 Builder 模式进行对象创建，好处是可设置需要的参数即可，以及 build() 时进行参数关系校验，
recordStats() 表示要进行记录统计数据（Builder 本来绑定了一个不做任何操作的 StatsCounter，调用这个方法后，绑定到一个 SimpleStatsCounter 中）

具体数据存储是通过 LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> 进行的。
