import store from '@/store'
/**
 * 权限判断逻辑
 *  此为权限文件，提供权限判断方法hasPermission,
 *  传入当前组件绑定的权限标识perms,判断权限标识是否存在于store中保存的用户权限标识集合中.
 *
 * 判断用户是否拥有操作权限
 * 根据传入的权限标识，查看是否存在用户权限标识集合
 * @param perms
 */
export function hasPermission (perms) {
  let hasPermission = false
  let permissions = store.state.user.perms
  if (permissions && permissions.length > 0) {
    for(let i=0, len=permissions.length; i<len; i++) {
      if(permissions[i] === perms) {
        hasPermission = true;
        break
      }
    }
  }

  return hasPermission
}
