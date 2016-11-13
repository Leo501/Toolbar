package com.leo.sleep.component.manager;

import com.leo.sleep.modules.ui.MainApplicataion;
import com.litesuits.orm.BuildConfig;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.List;

/**
 * 封装了一个数据库基本操作类
 * 有统一的对外接口
 * 当选择其他第三方数据库时，可以替换。
 * 而不用去修改其它地方。
 * Created by leo70 on 2016/11/6.
 */

public enum DataBaseOrm {

    INSTANCE;
    private LiteOrm liteOrm;
    DataBaseOrm() {
        DataBaseConfig config=new DataBaseConfig(MainApplicataion.appContext);
        config.dbName="dbOfApplication";
        config.dbVersion=1;
        config.debugged= BuildConfig.DEBUG;
        liteOrm=LiteOrm.newSingleInstance(config);
    }

    //用于特殊的需求
    public LiteOrm getLiteOrm() {
        return liteOrm;
    }

    /**
     * 插入一个元素
     * @param o
     */
    public <T> void save(T o){
        if (null==o){
            return;
        }
        liteOrm.save(o);
    }

    /**
     * 保存一组数组
     * @param collection
     * @param <T>
     */
    public <T> void save(List<T> collection){
        if (null==collection||collection.size()==0){
            return;
        }
        liteOrm.save(collection);
    }

    /**
     * 用id来查询
     * @param id
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T queryById(int id,Class<T> tClass){

        return liteOrm.queryById(id,tClass);
    }

    /**
     * 查询  某字段 等于 Value的值
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> List<T> queryByWhere(Class<T> cla, String field, Object[] value) {
        return liteOrm.query(new QueryBuilder(cla).where(field + "=?", value));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public <T> List<T> queryByWhereLength(Class<T> cla, String field, Object[] value, int start, int length) {
        return liteOrm.query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
    }
    /**
     *查询该表所有元素
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> queryAll(Class<T> tClass) {
        if (tClass == null) {
            return null;
        }
        return liteOrm.query(tClass);
    }

    /**
     * 更新一条数据
     */
    public void upDate(Object object){
        if (null==object){
            return;
        }
        liteOrm.update(object);
    }

    /**
     * 更新一个表的数据
     * @param tClass
     * @param <T>
     */
    public <T> void upDate(Class<T> tClass){
        if (tClass == null) {
            return ;
        }
        liteOrm.update(tClass);
    }

    /**
     * 删除一个数据
     * @param t
     * @param <T>
     */
    public <T> void  delete(T t){
        if (null==t){
            return;
        }
        liteOrm.delete(t);
    }

    /**
     * 删除集合中的数据
     * @param list
     * @param <T>
     */
    public <T> void deleteList( List<T> list ){
        if(null==list||list.size()==0){
            return;
        }
        liteOrm.delete( list ) ;
    }

    /**
     * 删除一个表
     * @param tClass
     * @param <T>
     */
    public <T> void delete(Class<T> tClass) {
        if (tClass == null) {
            return;
        }
        liteOrm.delete(tClass);
    }






}
