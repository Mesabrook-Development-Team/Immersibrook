package com.mesabrook.ib.apimodels.company;

import java.math.BigDecimal;
import java.util.UUID;

public class Register {
	public Long RegisterID;
	public Long LocationID;
	public String Name;
	public UUID Identifier;
	public BigDecimal CurrentTaxRate;
	public RegisterStatus CurrentStatus;
}
