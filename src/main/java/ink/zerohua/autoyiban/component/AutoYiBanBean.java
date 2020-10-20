package ink.zerohua.autoyiban.component;

import com.alibaba.fastjson.JSONObject;
import ink.zerohua.autoyiban.entity.User;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: auto-yiban
 * @author: zerohua
 * @create: 2020-10-17 12:03
 **/
@Component
public class AutoYiBanBean {

	private static final String LOGIN_URL = "http://xggl.hnie.edu.cn/website/login";

	private static final String CLOCK_IN_URL = "http://xggl.hnie.edu.cn/content/student/temp/zzdk";

	private static final String INFO_URL = "http://xggl.hnie.edu.cn/content/student/temp/zzdk/lastone";

	private CloseableHttpClient closeableHttpClient;

	public AutoYiBanBean() {
		this.closeableHttpClient = HttpClients.createDefault();
	}

	public boolean login(User user) {
		HttpPost httpPost = new HttpPost(LOGIN_URL);
		String result = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(getLoginPair(user)));

			//客户端执行httpGet方法，返回响应
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);

			//得到服务响应状态码
			if(closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				//得到响应实体
				result = EntityUtils.toString (closeableHttpResponse.getEntity(), "UTF-8");
				JSONObject jsonObject = JSONObject.parseObject(result);
				Boolean error = jsonObject.getBoolean("error");
				if ( error != null) {
					return false;
				}
			} else{
				//如果是其他状态码则做其他处理
				return false;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public JSONObject getClockInfo(User user) {
		login(user);
		JSONObject jsonObject = null;
		HttpGet httpGet = new HttpGet(INFO_URL);
		httpGet.setHeader("Accept", "*/*" );
		try {
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(closeableHttpResponse.getEntity());
				jsonObject = JSONObject.parseObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public boolean clockIn(User user) {
		HttpPost httpPost = new HttpPost(CLOCK_IN_URL);
		JSONObject jsonObject = this.getClockInfo(user);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(getPair(jsonObject), "UTF-8"));
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString (closeableHttpResponse.getEntity(), "UTF-8");
				JSONObject info  = JSONObject.parseObject(result);
				return true;
			}else {
				return false;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 这个蠢死了
	 * @return
	 */
	public List<BasicNameValuePair> getLoginPair(User user) {
		return new ArrayList<BasicNameValuePair>(){
			{
				add(new BasicNameValuePair("username", user.getYibanAccount()));
				add(new BasicNameValuePair("password", user.getMed5Val()));
			}
		};
	}

	/**
	 * 这个很蠢！！！！！！
	 * @return
	 */
	public List<BasicNameValuePair> getPair(JSONObject jsonObject) {
		return new ArrayList<BasicNameValuePair>(){
			{
				add(new BasicNameValuePair("operationType", "Create"));
				add(new BasicNameValuePair("sfzx", "1"));
				add(new BasicNameValuePair("jzdSheng.dm", (String) jsonObject.getJSONObject("jzdSheng").get("dm")));
				add(new BasicNameValuePair("jzdShi.dm", (String) jsonObject.getJSONObject("jzdShi").get("dm")));
				add(new BasicNameValuePair("jzdXian.dm", (String) jsonObject.getJSONObject("jzdXian").get("dm")));
				add(new BasicNameValuePair("jzdDz", (String) jsonObject.get("jzdDz")));
				add(new BasicNameValuePair("jzdDz2", (String) jsonObject.get("jzdDz2")));
				add(new BasicNameValuePair("lxdh", (String) jsonObject.get("lxdh")));
				add(new BasicNameValuePair("grInd", "0"));
				add(new BasicNameValuePair("jcInd", "0"));
				add(new BasicNameValuePair("jtqk.dm", "4"));
				add(new BasicNameValuePair("jtqkXx", null));
				add(new BasicNameValuePair("brqk.dm", "4"));
				add(new BasicNameValuePair("qwhbInd", "0"));
				add(new BasicNameValuePair("jchbrXx", null));
				add(new BasicNameValuePair("jchbrInd", "0"));
				add(new BasicNameValuePair("jchbrXx", null));
				add(new BasicNameValuePair("lxjlInd", "0"));
				add(new BasicNameValuePair("lxjlXx", null));
				add(new BasicNameValuePair("tw", jsonObject.get("tw") == null ? "37" : jsonObject.get("tw").toString() ));
				add(new BasicNameValuePair("bz", (String) jsonObject.get("bz")));
				//每天第一次打卡，该值为空
				add(new BasicNameValuePair("dm", ""));
			}
		};
	}
}
