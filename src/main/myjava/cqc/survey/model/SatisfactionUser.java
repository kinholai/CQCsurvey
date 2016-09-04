package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "satisfaction_user", schema = "")
@SuppressWarnings("serial")
public class SatisfactionUser implements Serializable
{
	private String id;;
	
	private String username;
	
	private String realname;
	
	private String mobile;
	
	private String office;
	
	private String email;
	
	private String enterpriseName;
	
	private String enterpriseAddress;
	
	private String password;
	
	private String roleId;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "username", nullable = false, length = 24)
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	@Column(name = "realname", nullable = true, length = 24)
	public String getRealname()
	{
		return realname;
	}

	public void setRealname(String realname)
	{
		this.realname = realname;
	}

	@Column(name = "mobile", nullable = true, length = 16)
	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	@Column(name = "office", nullable = true, length = 16)
	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}

	@Column(name = "email", nullable = true, length = 50)
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Column(name = "enterprise_name", nullable = true, length = 36)
	public String getEnterpriseName()
	{
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName)
	{
		this.enterpriseName = enterpriseName;
	}

	@Column(name = "enterprise_address", nullable = true, length = 50)
	public String getEnterpriseAddress()
	{
		return enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress)
	{
		this.enterpriseAddress = enterpriseAddress;
	}

	@Column(name = "password", nullable = false, length = 180)
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Column(name = "role_id", nullable = false, length = 32)
	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}
	
	
}
