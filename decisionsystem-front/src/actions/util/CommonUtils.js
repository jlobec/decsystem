export default class CommonUtils {
  static isEmptyObj = obj => {
    for (var key in obj) {
      if (obj.hasOwnProperty(key)) return false;
    }
    return true;
  };

  static isEmptyArray = arr => {
    return !arr || !arr.length;
  };

  static stripTrailingSlash = str => {
    return str.endsWith("/") ? str.slice(0, -1) : str;
  };
}
