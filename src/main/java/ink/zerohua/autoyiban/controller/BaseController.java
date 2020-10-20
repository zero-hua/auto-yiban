package ink.zerohua.autoyiban.controller;

import ink.zerohua.autoyiban.entity.User;
import ink.zerohua.autoyiban.util.Constant;
import ink.zerohua.autoyiban.util.QueryUtil;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zerohua
 * @desc 公共controller
 * @date 2020-10-17 02:53
 * @logs[0] 2020-10-17 02:53 zerohua 创建了BaseController.java文件
 */
public class BaseController {

    protected static final String ERROR_MSG_KEY = "errorMsg";

    /** 原始的request */
    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /** 原始的response */
    public HttpServletResponse getResponse(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /** 封装为Map的session attributes */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /** ① 获取保存在Session中的用户对象 */
    public User getSessionUser() {
        return (User) getRequest().getSession().getAttribute(
                Constant.SESSION_USER);
    }

    /** ②将用户对象保存到Session中  */
    public void setSessionUser(User user) {
        getRequest().getSession().setAttribute(Constant.SESSION_USER, user);
    }

    /** ③ 获取基于应用程序的url绝对路径   */
    public final String getAppbaseUrl(String url) {
        Assert.hasLength(url, "url不能为空");
        Assert.isTrue(url.startsWith("/"), "必须以/打头");
        return getRequest().getContextPath() + url;
    }

    /** 封装为Map的getRequest()uest parameters */
    public Map getParameters() {
        return getRequest().getParameterMap();
    }

    /** 封装为Map的session attributes */
    protected Map getSessionMap() {
        return (Map)getRequest().getSession();
    }

    /** 原始的ServletContext */
    public ServletContext getServletContext() {
        return getRequest().getSession().getServletContext();
    }

    /** 设置session的最大生命周期  maxInactiveInterval的单位为秒 */
    public void getSessionMaxInactiveInterval(int maxInactiveInterval) {
        getRequest().getSession().setMaxInactiveInterval(maxInactiveInterval);
    }

    /** 记录当前页面作为返回地址 */
    public void setReferUrl() {
        String currUrl = QueryUtil.getRequestURL(getRequest());
        getSessionMap().put(Constant.REFER_URL, currUrl);
    }
    public String getReferUrl() {
        return (String) getSessionMap().get(Constant.REFER_URL);
    }

    /** 获取客户端IP地址 */
    public String getClientAddress() {
        String address = getRequest().getHeader("X-Forwarded-For");
        if (address != null) {
            return address;
        } else {
            address = getRequest().getRemoteAddr();
        }
        return address;
    }

    /**
     * 获得请求参数
     * @return
     */
    public HashMap<String, Object> getParamsToMap() {
        Map<String, String[]> parameterMap = getRequest().getParameterMap();
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey().toString();
            String[] value = parameterMap.get(key);
            map.put(key, value[0]);
        }
        return map;
    }

    /**
     * 设置分页控件
     * @return
     */
    public Map<String, Object> setPage(Map<String, Object> pageMap) {
        if(pageMap != null && pageMap.containsKey("page") && pageMap.containsKey("rows")){
            int pageSize = 0;
            int rowsNum = 0;
            String page = pageMap.get("page").toString();
            String rows = pageMap.get("rows").toString();
            if (page != null && rows != null) {
                pageSize = Integer.parseInt(rows) * (Integer.parseInt(page) - 1);
                rowsNum = Integer.parseInt(rows);
                pageMap.put("page", pageSize);
                pageMap.put("rows", rowsNum);
            }
        }
        return pageMap;
    }

}