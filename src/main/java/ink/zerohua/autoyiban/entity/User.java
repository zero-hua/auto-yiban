package ink.zerohua.autoyiban.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author zerohua
 * @desc 用户实体
 * @date 2020-10-17 03:06
 * @logs[0] 2020-10-17 03:06 zerohua 创建了User.java文件
 */
@Data
@NoArgsConstructor
@Table
@Entity
public class User {

	public User(String yibanAccount, String password) {
		this.yibanAccount = yibanAccount;
		this.password = password;
	}

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 易班账号
	 */
	@NotNull
	@Column
	private String yibanAccount;

	/**
	 * 易班密码
	 */
	@NotNull
	@Column
	private String password;

	/**
	 * 易班前端采取MD5加密传输
	 */
	@Column
	private String med5Val;

}
