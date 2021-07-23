/* localStorage直接封装存储token */
import storage from "../utils/storage";

const ID_TOKEN_KEY = "id_token";
const ID_TOKEN_EXPIRED_TIME = "30";

export const getToken = () => {
  // 原生localStorage
  // return window.localStorage.getItem(ID_TOKEN_KEY);

  // 封装的localStorage
  return storage.get(ID_TOKEN_KEY);
};

export const saveToken = token => {
  // 原生localStorage
  // window.localStorage.setItem(ID_TOKEN_KEY, token);

  // 封装的localStorage
  return storage.set(ID_TOKEN_KEY, token, ID_TOKEN_EXPIRED_TIME);
};

export const destroyToken = () => {
  // 原生localStorage
  // window.localStorage.removeItem(ID_TOKEN_KEY);

  // 封装的localStorage
  storage.remove(ID_TOKEN_KEY);
};

export default { getToken, saveToken, destroyToken };
