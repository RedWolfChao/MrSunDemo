/**
 * 打印日志
 * @param TAG
 * @param MSG
 */
function logI(TAG, MSG) {
    console.group(TAG);
    console.log(MSG);
}

/**
 * 判断对应对象是不是空
 * @param OBJ
 * @returns {boolean}
 */
function isEmpty(OBJ) {
    return OBJ === undefined || OBJ === ""
}