
## Enums.getIfPresent(Class<T> enumClass, String value)
1.checkNotNull 参数判空
2.ref = Enums.getEnumConstants(enumClass).get(value)
    - ref 为 WeakReference
    - getEnumConstants 返回 Map<String, WeakReference>，获取过程 synchronized 加锁
    - 内部通过 enumConstantCache 进行缓存，类型为 WeakHashMap, key 为 Enum 类型的 class，value 类型为 Map<String, WeakReference>
    - 如果没有这个枚举类型，就 populateCache(enumClass) 得到该枚举所有值的 Map 添加到 enumConstantCache
3. ref == null ? Optional.<T>absent() : Optional.of(enumClass.cast(ref.get()));
    - absent() 返回 Optional 的子类 Absent，of 返回子类 Present，都重写了 or 等方法
