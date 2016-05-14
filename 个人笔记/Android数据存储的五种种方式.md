##  Android数据存储的五种种方式

> ###  1. Shared Preferences

* `getSharedPreferences()` 第一个参数传入文件名
* `getPreferences()`在Activity中获取，文件名为xxxActivicity.xml

> ### 2. 内部存储 files目录下的文件 

#### Context提供的打开流的两个方法：

- `openFileOutput()`和 `openFileInput()`
- 写入方法`write()`,读取方法`read()`。操作完成别忘记关闭流`close()`
- 实例：

```Java
String FILENAME = "hello_file";
String string = "hello world!";

FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
fos.write(string.getBytes());
fos.close();
```

#### 手动的创建流读取，Context同样提供了获取目录的方法

- `getFilesDir()`
- `getCacheDir()`

#### 其它有用的方法

- `getDir()`在/data/data/<包名>/目录下，创建或打开一个目录。
- `deleteFile()` 删除`files`目录下一个文件，不能删除目录。
- `fileList`返回`files`文件下的文件数组。
- `openRawResource()`打开R.raw.xxx下的资源文件。

> ###  3. 外部存储（File文件存储） 

- `Environment.getExternalStorageState()`   获取SD卡状态


- `Environment.getExternalStorageDirectory()`获取SD卡根目录

> ### 4. SQLite

SQLite的数据类型： integer，real浮点型，text 文本类型，blob 二进制类型

- SQLiteOpenHelper:数据库的创建个更新。

```Java
public MyOpenHelper(
  		Context context, 
      	String name, 
      	CursorFactory factory, 
      	int version){
  	//构造方法
}
//数据库被创建时调用，如果数据库已经存在就不再调用该方法。通常完成建表操作
public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table person (
              _id integer primary key autoincrement, 
              name char(10), 
              phone char(20), 
              money integer(20))");
}
//数据库升级时调用：onUpgrade方法。
//如果创建的表已经存在，就会报错。需要重新创建。
public void onUpgrade(SQLiteDatabase db, 
                      int oldVersion, int newVersion) {
		db.execSQL("drop table if exists Book");
		db.execSQL("drop table if exists Category");
		onCreate(db);
}

```

- SQLiteDataBase

```Java
MyOpenHelper oh = new MyOpenHelper(getContext(), "person.db", null, 1);
	SQLiteDatabase db = oh.getWritableDatabase();
	getWritableDatabase()//打开或创建可读写的数据库
	getReadableDatabase()//打开或创建，在磁盘空间不足时打开只读数据库，否则打开可读写数据库。而getWritableDatabase()方法会出现异常。

```

- 增删改查

```Java
//执行SQL语句实现增删改查
//插入
db.execSQL("insert into person (name, phone, money) 
           values (?, ?, ?);", new Object[]{"张三", 15987461, 75000});
//查询
Cursor cs = db.rawQuery("select _id, name, money from person where 	
           name = ?;", new String[]{"张三"});
//使用api实现增删改查
//插入
ContentValues cv = new ContentValues();
cv.put("name", "刘能");
cv.put("phone", 1651646);
cv.put("money", 3500);
//返回值是该行的主键，如果出错返回-1
long i = db.insert("person", null, cv);
//删除，返回值是删除的行数
int i = db.delete("person", "_id = ? and name = ?", 
                  new String[]{"1", "张三"});
//修改
ContentValues cv = new ContentValues();
cv.put("money", 25000);
int i = db.update("person", cv, "name = ?", new String[]{"赵四"});
//查询
//arg1:要查询的字段，arg2：查询条件，arg3:填充查询条件的占位符
Cursor cs = db.query("person", new String[]{"name", "money"}, 
            "name = ?", new String[]{"张三"}, null, null, null);
while(cs.moveToNext()){
}
//事务api
try {
    	//开启事务
    	db.beginTransaction();
    	...........
    	//设置事务执行成功
    	db.setTransactionSuccessful();
} finally{
   	//关闭事务
   	//如果此时已经设置事务执行成功，则sql语句生效，否则不生效
   	db.endTransaction();
}

```

## 跨程序间的数据共享ContentProvider

ContentProvider是Android不同程序间标准的数据共享方式。

Sharedpreference的共享方式在android4.2中已经被废弃。（安全性和隐私）

Android系统的ContentProvider提供把私有数据共享给其他应用。

#### 使用步骤：

```Java
1. 自定义内容提供者，继承ContentProvider抽象类，重写增删改查方法。
	public Uri insert(Uri uri, ContentValues values) {
        db.insert("person", null, values);
        return uri;
    }
         …  …	
2. 在AndroidManifest文件中定义内容提供者的标签，注意必须要有authorities属性，这是内容提供者的主机名，功能类似地址。还可以设置访该 提供者需要的权限
	<provider android:name=
      		"com.koter.contentprovider.MycontentProvider"
         android:authorities="com.koter.myprovider"
         android:exported="true">
	/provider>
3. 其他应用访问该ContentProvider
	ContentResolver cr = getContentResolver();
	ContentValues cv = new ContentValues();
	… … 
	Uri是你要访问的协议名+主机名。内容提供者的协议名是content：//
	cr.insert(Uri.parse("content：//com.koter.myprovider"),cv);

```

#### UriMatcher (Uri匹配器)

```Java
1. 在内容提供者中指定要匹配的Uri
	UriMatcher um = new UriMatcher(UriMatcher.NO_MATCH);
	{  //在代码块中初始化匹配项。 arg0：主机名 arg1：访问路径 age3：返回码 
		um.addURI("com.itheima.person", "person", PERSON_CODE);
     	um.addURI("com.itheima.person", "company", COMPANY_CODE);
      	//#号可以代表任意数字。可以参考访问系统指定的联系人
     	um.addURI("com.itheima.person", "person/#", 
                  QUERY_ONE_PERSON_CODE);
	}
2. 通过传递的Uri进行不同的操作	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if(um.match(uri) == PERSON_CODE){
			db.insert("person", null, values);
		}
		else if(um.match(uri) == COMPANY_CODE){
			db.insert("company", null, values);
		}
		else{
			throw new IllegalArgumentException();
		}
		return uri;
	}
	//如果Uri携带数据，取出携带的数字，并进行相应的操作。例如查询指定联系人，传入联系人位置。
	long id = ContentUris.parseId(uri);
```

#### 获取系统的联系人

- 联系人数据库contacts2.db

```Java
raw_contacts表：
		contact_id字段：联系人id。
data表：联系人信息表，一个联系人信息对应多行。
		根据raw_contacts表查询的contact_id字段查询该表。对应该表的raw_contact_id字段。
		data1字段 ：包含了联系人的具体信息
		mimetype_id字段：描述信息是属于什么类型  Email name 等。是外键
mimetypes表：
		mimetype字段 ：定义了联系人信息类型

```

- 获取联系人表信息之后，查询联系人信息

```Java
1) 查询raw_contacts表，拿到contacts_id。
	ContentResolver cr = getContentResolver();
	Cursor cursor = cr.query(Uri.parse                          				("content://com.android.contacts/raw_contacts")
			, new String[]{"contact_id"}, null, null, null);	
2) 根据contact_id查询data表的data1，mimetype(/data 自动查询)字段，获取联系人信息。
 	while(cursor.moveToNext()){
	//获取查询到的联系人id，作为查询条件
	String contactId = cursor.getString(0);
	//查询data表，返回mimetype，data1字段信息。根据mimetype解析data信息类型
	//data表中没有mimetype，实际上查的是data_view视图。
	Cursor cursorData =cr.query(Uri.parse
            ("content://com.android.contacts/data"), 
			new String[]{"data1", "mimetype"}, "raw_contact_id = ?", 
			new String[]{contactId}, null);
	//将联系人信息封装到JavaBean对象。
	Contact contact = new Contact();
	while(cursorData.moveToNext()){
		String data1 = cursorData.getString(0);
		String mimetype = cursorData.getString(1);
		if("vnd.android.cursor.item/email_v2".equals(mimetype)){
			contact.setEmail(data1);
		}
		else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
			contact.setPhone(data1);
		}
		else if("vnd.android.cursor.item/name".equals(mimetype)){
			contact.setName(data1);
		}
	}
```

#### 获取系统短信

只需要关注sms表的4个字段

- body：短信内容
- address:短信的发件人或收件人号码（跟你聊天那哥们的号码）
- date：短信时间
- type：1为收到，2为发送

```Java
ContentResolver resolver = getContentResolver();
//1. 查询系统短信表。获取address,data日期，type1、2，body字段信息。
Cursor cursor = resolver.query(Uri.parse("content://sms"), new String[]{"address", 
				"date", "type", "body"}, null, null, null);
while(cursor.moveToNext()){
		String address = cursor.getString(0);
		long date = cursor.getLong(1);
		int type = cursor.getInt(2);
		String body = cursor.getString(3);
		System.out.println(address + ";" + date + ";" + type + ";" + body);
}
```

