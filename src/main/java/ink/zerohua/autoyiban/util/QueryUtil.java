package ink.zerohua.autoyiban.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: auto-yiban
 * @author: zerohua
 * @create: 2020-10-17 03:27
 **/
public class QueryUtil {

	public static String getRequestURL(HttpServletRequest request) {
		return String.valueOf(request.getRequestURL());
	}
}
