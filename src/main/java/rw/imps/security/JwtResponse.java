package rw.imps.security;



import rw.imps.domain.Account;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	public Account acc;
	public JwtResponse(String jwttoken,Account acc) {
		this.jwttoken = jwttoken;
		this.acc = acc;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	public Account getUser() {
		return acc;
	}
}