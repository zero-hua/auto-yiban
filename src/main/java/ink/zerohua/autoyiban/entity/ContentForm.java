package ink.zerohua.autoyiban.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @program: auto-yiban
 * @author: zerohua
 * @create: 2020-10-17 09:54
 **/
@Data
@NoArgsConstructor
@Entity
@Table
public class ContentForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	/**
	 * 操作类型
	 * 1 Update
	 * 2 Create
	 */
	@Column
	String operationType;

	/**
	 * 是否在校
	 * 0 不在
	 * 1 在
	 */
	@Column
	private String sfzx;

	/**
	 * 地址编码
	 */
	@Column
	private String jzdShengdm;

	@Column
	private String jzdShidm;

	@Column
	private String jzdXiandm;

	/**
	 * 现居地址
	 */
	@Column
	private String jzdDz;

	/**
	 * 老家
	 */
	@Column
	private String jzdDz2;

	/**
	 * 电话
	 */
	@Column
	private String lxdh;

	/**
	 * 呼吸道感染
	 */
	@Column
	private String grInd;

	/**
	 * 接触可能患者
	 */
	@Column
	private String jcInd;

	/**
	 * 家人情况
	 */
	@Column
	private String jtqkdm;

	/**
	 * 家人详情
	 */
	@Column
	private String jtqkXx;

	/**
	 * 本人情况
	 */
	@Column
	private String brqkdm;

	/**
	 * 本人/家人是否去过中高风险区域
	 */
	@Column
	private String qwhbInd;

	/**
	 * 去中高风险区域详情
	 */
	@Column
	private String jchbrXx1;

	/**
	 * 本人/家人是否接触过中高风险区域人员
	 */
	@Column
	private String jchbrInd;

	/**
	 * 接触详情
	 */
	@Column
	private String jchbrXx2;
	/**
	 * 旅行经历
	 */
	@Column
	private String lxjlInd;

	/**
	 * 详情
	 */
	@Column
	private String lxjlXx;

	/**
	 * 体温
	 */
	@Column
	private String tw;

	/**
	 * 备注
	 */
	@Column
	private String bz;

	/**
	 * 标识码
	 */
	@Column
	private String dm;
}
