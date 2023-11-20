package com.mesabrook.ib.apimodels.account;

import java.util.Date;

import com.mesabrook.ib.apimodels.mesasys.User;

public class DebitCard {
	public long DebitCardID;
	public long AccountID;
	public Account Account;
	public String CardNumber;
	public Date IssuedTime;
	public long UserIDIssuedBy;
	public User UserIssuedBy;
}
