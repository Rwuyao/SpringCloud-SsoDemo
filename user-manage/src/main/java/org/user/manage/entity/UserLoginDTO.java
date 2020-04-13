package org.user.manage.entity;

public class UserLoginDTO {
	private JWT jwt;
    private BaseUser user;
    
    
	public UserLoginDTO() {
		super();
	}
	public UserLoginDTO(JWT jwt, BaseUser user) {
		super();
		this.jwt = jwt;
		this.user = user;
	}
	public JWT getJwt() {
		return jwt;
	}
	public void setJwt(JWT jwt) {
		this.jwt = jwt;
	}
	public BaseUser getUser() {
		return user;
	}
	public void setUser(BaseUser user) {
		this.user = user;
	}
    
    
}
