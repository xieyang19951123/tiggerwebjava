package io.renren.modules.tigger.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.awt.print.PrinterGraphics;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:22
 */
@Data
@TableName("ps_shop_user")
public class ShopUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String psUsername;
	/**
	 * 
	 */
	private String psPassword;
	/**
	 * 
	 */
	private Integer point;
	/**
	 * 
	 */
	private String recommend;
	/**
	 * 
	 */
	private Integer parentId;
	/**
	 * 
	 */
	private BigDecimal balance;

	/**
	 * 创建时间
	 */
	private Date createTime;

	@TableLogic(value = "1",delval ="0")
	private Integer showStatus;

	private Integer shopVip;

	private Integer userRock;


	private Integer userType;
}
