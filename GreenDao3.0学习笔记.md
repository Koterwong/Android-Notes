### GreenDao数据库Orm框架。

在Android开发中，GreenDao这款ORM框架的数据存取性能已经得道了广大认可，GreenDao官网也列出了它相比其他Orm框架的优势。它的出现避免我们在使用数据库时，编写Sql语句等需要繁琐的工作。

Orm：对象映射关系（Object Relational Mapping），它的思想就是将关系数据库中的表映射成对象，以对象的形式展示数据，让我们以面向对象的思想实现数据库的操作。

GreenDao的优点：轻量级、快速、内存开销小、简单一用的api、性能高度优化、操作简单、支持get\update\delete等操作。

---

GreenDao最新版本为3.0，相比2.X在使用上有了很大的变化，当然既然学习的话也就从最新版本开始咯。

### GreenDao的使用

GreenDao3.0官网详细教程。[官网教程](http://greenrobot.org/greendao/documentation/updating-to-greendao-3-and-annotations/)

##### 1、GreenDao3.0的Gradle配置。

```
apply plugin: 'org.greenrobot.greendao' //GreenDao插件

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.0.0'
    }
}

//配置GreenDao代码生成路径
greendao {
    //数据库schema版本号，迁移等操作会用到
    schemaVersion 1
    //通过gradle插件生成的数据库相关文件的包名，默认为你的entity所在的包名
    daoPackage 'me.koterwong.greendaodemo.dao'
    //这就是我们上面说到的自定义生成数据库文件的目录了，可以将生成的文件放到我们的java目录中，而不是build中，这样就不用额外的设置资源目录了
    targetGenDir 'src/main/java'
}

dependencies {
    //配置GreenDao
    compile 'org.greenrobot:greendao:3.0.1'  //库
    compile 'org.greenrobot:greendao-generator:3.0.0' //代码生成器
}
```

##### 2、定义entity实体类

```java
@Entity
public class User {
    @Id
    private Long id;  //id
    private String name;
    @Transient
    private int tempUsageCount; // not persisted
}
```

##### 3‘、生成高效代码

相比2.x，3.0在生成带上更加的简单，只需要下载gradle相关插件即可。接下来使用就和2.x的使用差别不大了。

##### 4、简单的封装

```java
public class DaoManager {

    public static final String TAG = DaoManager.class.getSimpleName();

    /**
     * 数据库名
     */
    private static final String DB_NAME = "my_db.sqlite";

    private static DaoManager manager;
    private static DaoMaster daoMaster;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoSession daoSession;
    private Context context;

    private DaoManager(Context context) {
        this.context = context;
    }

    public static DaoManager getInstance(Context context) {
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (manager == null) {
                    manager = new DaoManager(context);
                }
            }
        }
        return manager;
    }

    /**
     * 判断数据库是否存在，如果没有就创建。
     *
     * @return 一个DaoMaster就代表着一个数据库的连接
     */
    public DaoMaster getDaoMaster() {

        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 完成数据的添加、删除、修改、查询的操作
     *
     * @return DaoSession完成对Entity的基本操作和Dao操作类
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 设置Debug开启，默认关闭
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭数据库连接,数据库开启的时候，使用完毕了必须要关闭
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    public void closeDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }
}

```

封装数据的操作。

```java
public class DaoUtils<Entity> {
    private static final String TAG = "DaoUtils";
    private DaoManager manager;
    public DaoUtils(Context context) {
        manager = DaoManager.getInstance(context);
    }

    /**
     * 插入操作
     *
     * @param entity entity
     * @return boolean
     */
    public boolean insertEntity(Entity entity) {
        boolean flag = false;
        flag = manager.getDaoSession().insert(entity) != -1;
        return flag;
    }

    /**
     * 插入多条数据
     *
     * @param entities entities
     * @return boolean
     */
    public boolean insertMulitEntity(final List<Entity> entities) {
        boolean flag = false;
        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override public void run() {
                    for (Entity e : entities) {
                        manager.getDaoSession().insertOrReplace(e);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新操作
     *
     * @param entity entity
     * @return boolean
     */
    public boolean updateEntity(Entity entity) {
        boolean flag = false;
        try {
            manager.getDaoSession().update(entity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除操作
     *
     * @param entity entity
     * @return boolean
     */
    public boolean deleteEntity(Entity entity) {
        boolean flag = false;
        try {
            manager.getDaoSession().delete(entity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @param entity entityClass
     * @return boolean
     */
    public boolean deleteAll(Entity entity) {
        boolean flag = false;
        try {
            manager.getDaoSession().deleteAll(entity.getClass());
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return List<Entity>
     */
    @SuppressWarnings("unchecked")
    public List<Entity> listAll(Entity entity) {
        return (List<Entity>) manager.getDaoSession().loadAll(entity.getClass());
    }

    /**
     * 查询主键
     *
     * @param key key
     * @return Entity
     */
    public Entity quetyOneByKey(Entity entity, Long key) {
        return (Entity) manager.getDaoSession().load(entity.getClass(), key);
    }
}

```