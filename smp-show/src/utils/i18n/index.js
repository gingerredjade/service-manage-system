// 多国语言国际化----用的框架版，没有使用这个，后面可以删除

import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)

// 注册i18n实例并引入语言文件，文件格式等下解析
const i18n = new VueI18n({
  locale: 'zh_cn',
  messages: {
    'zh_cn': require('@/assets/custom/languages/zh_cn.json'),
    'en_us': require('@/assets/custom/languages/en_us.json')
  }
})

export default i18n
