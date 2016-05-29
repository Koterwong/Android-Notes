##RxJava使用场景

1、线程并发和线程调度

```Java
Observable.just(true)
        .map(new Func1<Boolean, Boolean>() {
            @Override public Boolean call(Boolean aBoolean) {
                //do some long operation
                return false;
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getSubscribe());

private Action1<Boolean> getSubscribe() {
        return new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
				//只关心onNext()事件
            }
        };
    }
```

2、buffer变换操作符，将事件缓冲，然后再发射Observable序列。（例如Button点击次数）

- 指定Observable发射多少次，达到指定次数之后再发射给Subscriber。
- 指定时间内在发射一次Obserable序列。

```Java
RxView.clickEvents(btn)
        .map(new Func1<ViewClickEvent, Integer>() {
            @Override public Integer call(ViewClickEvent viewClickEvent) {
                return 1;
            }
        })
        .buffer(2,TimeUnit.SECONDS)
        .subscribe(new Action1<List<Integer>>() {
            @Override public void call(List<Integer> integers) {
                //这里演示buffer每隔一段时间个连个发射一个列表的Observable。
            }
        });
```

3、debounce过滤操作符：过滤掉由Observable发射的速率过快的数据。如果在一个指定的时间间隔过去了仍旧没有发射一个， 那么它将发射最后的那个。

- 输入框搜索，在指定事件内才收到字体的变化。如果是监听EditText的内容变化事件，每次变化都请求网络，将造成资源的浪费。

```Java
RxTextView.textChangeEvents(editText)
        .debounce(500, TimeUnit.SECONDS)// default Scheduler is Computation
        .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
            @Override public Boolean call(TextViewTextChangeEvent
                                                  textViewTextChangeEvent) {
                //过滤掉没有变化的内容，false不发射。
              	return ;
            }
        })
        .subscribe(new Action1<TextViewTextChangeEvent>() {
            @Override public void call(TextViewTextChangeEvent
                                               textViewTextChangeEvent) {
                Log.e(TAG,textViewTextChangeEvent.text().toString());
            }
        });
```

4、interval周期操作。不需要该操作的时候取消订阅。

- 和service搭配后台更新数据

```Java
CompositeSubscription cs = new CompositeSubscription();
Subscription subscribe = Observable.interval(0, 2000, TimeUnit.MILLISECONDS)
        .map(new Func1<Long, String>() {
            @Override public String call(Long aLong) {
                return aLong + "time";
            }
        })
        .take(8)  //周期发射数据的次数
        .doOnSubscribe(new Action0() {
            @Override public void call() {
                Log.e(TAG, "Start-----MainThread");
            }
        })
        .subscribe(new Action1<String>() {
            @Override public void call(String s) {
                Log.e(TAG, "Next-----NoMainThread");
            }
        });
cs.add(subscribe);
cs.unsubscribe();
```

5、RxJava代替Timer做定时任务。

- 只推荐做一次的任务，即xx时间后做oo操作，循环任务用interval。

```Java
Observable.timer(2000, TimeUnit.MILLISECONDS)
        .subscribe(new Action1<Long>() {
            @Override public void call(Long aLong) {
                Log.d(TAG, "2秒后执行");
            }
        });
```

6、RxJava使用combineLatest做注册界面。

- combineLatest将多个Observable发射的数据，使用一个函数结合每个Observable发射的数据，发射新的数据。
- 第一次发射数据时只有当每个Observable都发射了一个数据才会发射数据。之后任何一个Observable发射了数据都会使用一个函数结合后重新发射数据。zip只有多个Obserable都发射数据才会发射数据。

```Java
Observable<CharSequence> emailChangeObservable =
        RxTextView.textChanges(_email).skip(1);
Observable<CharSequence> passwordChangeObservable =
        RxTextView.textChanges(_password).skip(1);
Observable.combineLatest(
        emailChangeObservable,
        passwordChangeObservable,
        new Func2<CharSequence, CharSequence, Boolean>() {
            @Override public Boolean call(
              	//定义发射数据的函数。
                    CharSequence newEmail,
                    CharSequence newPassword) {
                boolean emailValid = !isEmpty(newEmail) &&
                        EMAIL_ADDRESS.matcher(newEmail).matches();
                if (!emailValid) {
                    _email.setError("Invalid Email!");
                }

                boolean passValid = !isEmpty(newPassword) && newPassword.length() > 8;
                if (!passValid) {
                    _password.setError("Invalid Password!");
                }
                return emailValid&&passValid;
            }
        })
        .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
                Log.d(TAG, "输入正确："+aBoolean);
            }
        });
```

7、使用merge合并两个Observable，例如两组数据一组来自本地，另一组来自网络。

- merge会将多Obserable合并成一个新的Obserable然后将数据一一进行发射。

```Java
Observable.merge(getCachedData(),getFreshData())
        .subscribe(new Subscriber<String>() {
            @Override public void onCompleted() {
                Log.d(TAG, "done loading all data");
            }

            @Override public void onError(Throwable e) {
                Log.d(TAG, "something wrong");
            }

            @Override public void onNext(String s) {
                Log.d(TAG, "收到的数据"+s);
            }
        });
```

8、