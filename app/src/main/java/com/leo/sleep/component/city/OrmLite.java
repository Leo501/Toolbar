package com.leo.sleep.component.city;

import com.leo.sleep.BuildConfig;
import com.leo.sleep.base.BaseApplication;
import com.leo.sleep.component.table.CityORM;
import com.leo.sleep.utils.LogUtil;
import com.leo.sleep.utils.RxUtils;
import com.litesuits.orm.LiteOrm;

import rx.Observable;

/**
 * Created by leo70 on 2016/10/5.
 */

public class OrmLite {

    //可以改成单例。
    static LiteOrm liteOrm;

    public OrmLite() {
        if (liteOrm==null){
            liteOrm=LiteOrm.newSingleInstance(BaseApplication.getAppContext(),Constant.ORM_NAME);
        }
        liteOrm.setDebugged(BuildConfig.DEBUG);
    }

    public static LiteOrm getInstance(){
        getOrmHolder();
        return liteOrm;
    }

    public static <T> void OrmTest(Class<T> t){
        Observable.from(getInstance().query(t))
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<T>() {
                    @Override
                    public void onNext(T t) {
                        if (t instanceof CityORM){
                            LogUtil.w(((CityORM)t).getName());
                        }
                    }
                });
    }

    private static OrmLite getOrmHolder() {
        return OrmHolder.instance;
    }

    private static class OrmHolder{
        private static final OrmLite instance=new OrmLite();
    }





}
