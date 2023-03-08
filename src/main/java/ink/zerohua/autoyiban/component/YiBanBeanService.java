package ink.zerohua.autoyiban.component;

import com.alibaba.fastjson.JSONObject;
import ink.zerohua.autoyiban.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: auto-yiban
 * @author: zerohua
 * @create: 2020-10-17 12:03
 **/
@Service(value = "yibanService")
@Slf4j
public class YiBanBeanService {

	private static final String LOGIN_URL = "http://www.huas.edu.cn:319/website/login";

	private static final String CLOCK_IN_URL = "http://www.huas.edu.cn:319/wap/menu/student/temp/zzdk";

	private static final String INFO_URL = "http://www.huas.edu.cn:319/content/student/temp/zzdk/lastone";

	private CloseableHttpClient closeableHttpClient;

	public YiBanBeanService() {
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
				//System.out.println(result + "---->" + "密码为: " + user.getMed5Val());
				Boolean error = jsonObject.getBoolean("error");
				if ( error != null) {
					return false;
				} else {
					//如果是其他状态码则做其他处理
					return false;
				}
			}
		}catch(IOException e) {
			log.error("发生了一些错误在line 64...");
			return false;
		}catch (Exception e) {
			log.error("发生了一些错误在line 68...");
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
				//System.out.println(result);
				jsonObject = JSONObject.parseObject(result);
			}
		} catch (IOException e) {
			log.error("发生了一些错误在line 81...");
		} catch (Exception e) {
			log.error("发生了一些错误在line 86...");
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
			log.error("发生了一些错误在line 105...");
		}catch (Exception e) {
			log.error("发生了一些错误在line 107...");
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
				add(new BasicNameValuePair("uname", user.getYibanAccount()));
				add(new BasicNameValuePair("pd_mm", user.getMed5Val()));
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
				add(new BasicNameValuePair("dkdz", (String) jsonObject.get("dkdz")));
				add(new BasicNameValuePair("sfzx", "1"));//是否离校
				add(new BasicNameValuePair("dkly", "baidu"));
				add(new BasicNameValuePair("dkd", (String) jsonObject.get("dkd")));
				add(new BasicNameValuePair("jzdValue", ""));
				add(new BasicNameValuePair("jzdSheng.dm", (String) jsonObject.getJSONObject("jzdSheng").get("dm")));
				add(new BasicNameValuePair("jzdShi.dm", (String) jsonObject.getJSONObject("jzdShi").get("dm")));
				add(new BasicNameValuePair("jzdXian.dm", (String) jsonObject.getJSONObject("jzdXian").get("dm")));
				add(new BasicNameValuePair("jzdDz", (String) jsonObject.get("jzdDz")));
				add(new BasicNameValuePair("jzdDz2", (String) jsonObject.get("jzdDz2")));
				add(new BasicNameValuePair("lxdh", (String) jsonObject.get("lxdh")));
				add(new BasicNameValuePair("twM.dm", "01"));
				add(new BasicNameValuePair("tw1", "[35.0~37.2]正常"));
				add(new BasicNameValuePair("yczk.dm", "01"));
				add(new BasicNameValuePair("yczk1", "无症状"));
				add(new BasicNameValuePair("brStzk.dm", "01"));
				add(new BasicNameValuePair("brStzk1", "身体健康、无异常"));
				add(new BasicNameValuePair("brJccry.dm", "01"));
				add(new BasicNameValuePair("brJccry1", "未接触传染源"));
				add(new BasicNameValuePair("jrStzk.dm", "01"));
				add(new BasicNameValuePair("jrStzk1", "身体健康、无异常"));
				add(new BasicNameValuePair("jrJccry.dm", "01"));
				add(new BasicNameValuePair("jrJccry1", "未接触传染源"));

				add(new BasicNameValuePair("tw", jsonObject.get("tw") == null ? "37" : jsonObject.get("tw").toString() ));
				add(new BasicNameValuePair("jkm", (String) jsonObject.get("jkm")));
				add(new BasicNameValuePair("jkm1", (String) jsonObject.get("jkm1")));
				add(new BasicNameValuePair("xcm", (String) jsonObject.get("xcm")));
				add(new BasicNameValuePair("xcm1", (String) jsonObject.get("xcm1")));
				add(new BasicNameValuePair("xgym", (String) jsonObject.get("xgym")));
				add(new BasicNameValuePair("xgym1", (String) jsonObject.get("xgym1")));
				add(new BasicNameValuePair("hsjc", (String) jsonObject.get("hsjc")));
				add(new BasicNameValuePair("hsjc1", (String) jsonObject.get("hsjc1")));
				add(new BasicNameValuePair("bz", (String) jsonObject.get("bz")));
				//每天第一次打卡，该值为空
				add(new BasicNameValuePair("dm", ""));
			}
		};
	}
}
