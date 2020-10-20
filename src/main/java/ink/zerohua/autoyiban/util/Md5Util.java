package ink.zerohua.autoyiban.util;


import org.springframework.util.DigestUtils;

/**
 * @program: auto-yiban
 * @author: zerohua
 * @create: 2020-10-17 11:25
 **/
public class Md5Util {

	public static String getMd5Value(String originCode) {
		return DigestUtils.md5DigestAsHex(originCode.getBytes());
	}

}
