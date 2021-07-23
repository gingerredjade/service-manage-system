/* localStorage封装 - 支持设置缓存过期 */

import moment from "moment";
moment().format();

let storage = {

  /**
   * 设置缓存
   * @param key 保存键
   * @param value 保存内容(localStorage不能保存Object对象,需要使用stringify进行转换)
   * @param expired 失效时间(单位: 分钟)
   */
  set (key, value, expired) {
    /*
    * 定义source临时对象,临时存储key value,赋值后加入到localStorage
    * */
    let source = { key: key, value: value};

    /*
    * now 获取当前时间
    * */
    const now = Date.now();

    /*
    * 1分钟计算: (1000 * 60)
    * 计算失效总分钟: (1000 * 60 * expired)
    * 计算最大存储时间: now + 失效总分钟
    * */
    if (expired) {
      source.value = JSON.stringify({ data: value, expired: now + (1000 * 60 * expired) });
    } else {
      source.value = JSON.stringify({ data: value, expired: now + (1000 * 60) });
    }
    localStorage.setItem(source.key, source.value);
  },
  /**
   * 获取缓存
   * @param key 键
   */
  get (key) {
    const now = Date.now();
    let source = { key: key, value: null };

    /*
    * 获取localStorage存储信息,赋值给source对象
    * */
    let valStr = localStorage.getItem(source.key);
    if (valStr) {
      source.value = JSON.parse(localStorage.getItem(source.key));
    }

    /*
    * 如果key有效,判断当前时间是否超过失效时间
    * 超过失效时间,删除存储内容
    * */
    if (source.value) {
      if (now > source.value.expired) {
        // 超过失效时间,删除储存的内容
        //console.log("缓存的"+key+"当前已失效")
        this.remove(source.key);
        //return;
      } else {
        return source.value.data;
      }
    }
  },
  /**
   * 移出缓存
   * @param key 键
   */
  remove (key) {
    localStorage.removeItem(key);
  },
  /**
   * 移出全部缓存
   */
  clear() {
    localStorage.clear();
  },
  /**
   * 获取失效时间
   * @param key 键
   */
  expiredtime (key) {
    const now = Date.now();
    let source = { key: key, value: null };

    /*
    * 从缓存中取出信息
    * */
    source.value = JSON.parse(localStorage.getItem(source.key));

    /* 判断key是否失效
    *  moment.diff计算两个时间的差值
    *   moment(endTime).diff(moment(startTime), 'seconds')
    *   开始时间和结束结束时间的时间差,以“天”为单位;endTime、startTime都是毫秒数
    *  */
    if (source.value) {
      // 获取失效时间
      let expired = source.value.expired;
      // 测试写一个OK？
      source.value.expired = source.value.expired = moment(expired).diff(moment(now), 'seconds');
      return source.value.expired;
    } else {
      //return;
    }
  }

}

/* export default 用于导出函数,类中只能定义一个,其他类通过此对象可调用该类所有方法 */
export default storage;
