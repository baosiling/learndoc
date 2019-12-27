### 构造方法
![](静态常量.png)
![](Fields.png)
```
    int threshold;
    final float loadFactor;
```

最常用的是下面两个
```
    Map<String,String> map = new HashMap<>(16);
    Map<String,String> map = new HashMap<>();

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }
```
这两个方法 赋值了初始容量initialCapacity、负载因子loadFactor
这里调用了一个方法来计算容量cap的大小
```
    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
```
第一次 向右移1位再与自己或运算，相当于把自身从左起前2位变为1了，后续操作依次将自身从左起4位，8位，16位，32位变为1，
所以即使hashmap是最大容量MAXIMUM_CAPACITY，经过这些操作，也会将自身所有有效位变为1，最后return n+1,就是2的整数幂。

cap-1 的目的是如果cap本身是2的整数幂能保证返回结果就是它自身。否则会结果会*2

以上是构造方法的逻辑，**就是确定数组的初始容量大小以及负载因子，但是并没有初始化Node<K,V>[] table。**
### PUT
put的内容比较多，逻辑相对复杂 重写AbstractMap的put方法
```
    public V put(K key, V value)

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict)
```
put执行时首先会计算key的hash值，可以看到是用key对象的hashCode进行计算得到的
-   key.hashCode();一般情况我们使用的key，大都是字符串，可以看一下String类的hashCode()方法，得到的是一个int值（hashCode就是为每个对象分配一个整数，相同的对象数值一样，不同的对象数值不一样，这里的一样与否很多时候取决于自己的需求，所以大部分情况我们需要为自定义的类重写hashCode，Object的hashCode方法实际是调用本地方法，返回一个虚拟地址，这种大部分都满足不了要求，所以需要重写hashCode方法。）
-   因为能力有限，hashCode的算法不深究了，总之是在性能和hash效果之间权衡。
-   来看HashMap的hash计算方法 (h = key.hashCode()) ^ (h >>> 16)
因为h是个int值32位的，无符号右移16位，然后与自身异或=》先看h<216次幂，右移16位后 都变为0 0与任何数异或都不变 ，所以小于216次幂的key，都是自身的hashCode
再看h>216次幂，右移16位后，低16位都变为0，高16位转移到低16位，再与自身异或，这样的效果就是 **自己的高半区和低半区做异或，就是为了混合原始哈希码的高位和低位，以此来加大低位的随机性**
-   计算出了hashCode 再往下看 真正的put方法
-   


### GET
### REMOVE
### 遍历

### 参考资料
>https://www.cnblogs.com/lycroseup/p/7344321.html
https://blog.csdn.net/anxpp/article/details/51234835
https://blog.csdn.net/qq_38182963/article/details/78940047
https://www.cnblogs.com/loading4/p/6239441.html
https://my.oschina.net/u/232911/blog/2872356

