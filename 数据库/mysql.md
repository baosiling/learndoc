# 1.mysql为什么强制自增主键、

因为可以避免mysql页分裂，
InnoDB中，主索引文件上直接存放该行数据，称为聚簇索引。次索引指向对主键的引用；
MyISAM中，主索引和次索引，都指向物理行(磁盘位置)；
注意: 对InnoDB来说
1: 主键索引既存储索引值，又在叶子中存储行的数据;2: 如果没有定义主键，则会使用非空的UNIQUE键做主键 ; 
如果没有非空的UNIQUE键，则系统生成一个6字节的rowid做主键;聚簇索引中，N行形成一个页。
如果碰到不规则数据插入时，会造成频繁的页分裂(因为索引要排好序)，插入速度比较慢。
所以聚簇索引的主键值应尽量是连续增长的值，而不是随机值(不要用随机字符串或UUID)，否则会造成大量的页分裂与页移动。
故对于InnoDB的主键，尽量用整型，而且是递增的整型。这样在存储/查询上都是非常高效的。
https://www.jianshu.com/p/1203fd140cc2

# 2.mysql是怎么保证事务的、

持久性：redo log 
每commit时 都会生成一个redolog，buffer->磁盘 fsync刷新 一秒一次

原子性：undo log （MVCC）
执行前，需要生成undo log 逻辑log，记录的是操作的逆向操作
有问题就用此log回滚

一致性： redo log + undo log

隔离性：锁（共享锁，排它锁）

https://www.cnblogs.com/jianzh5/p/11643151.html
https://zhuanlan.zhihu.com/p/90187667

# 3.limit 20000，20 加载很慢怎么解决、

加载慢是因为 没有利用到索引，一直扫描了很多行，但只保留最后20行，
所以可以增加索引 where条件，select * from （，，，，）a left join student on a.id=student.id 

根据id 来限制
id>20000 && id <20020

限制条件

https://juejin.im/post/5db658faf265da4d500f8386

# 4.B-树 B+树

# 5.隔离级别
Read uncommit 脏读（未提交的数据会被读到）
read committed 不可重复读（新增，更新数据的内容会被读到）
repeatable read 幻读 （新增的数据会被读到）
serailizable （事务串行化）

mysql默认的事务隔离级别是 repeatable read