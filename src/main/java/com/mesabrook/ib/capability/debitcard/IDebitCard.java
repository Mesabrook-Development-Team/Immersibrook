package com.mesabrook.ib.capability.debitcard;

public interface IDebitCard {
	/**
	 * Implementers should only allow this to be set ONCE!
	 */
	void setCardNumber(String cardNumber);
	String getCardNumber();
	
	public static class Impl implements IDebitCard
	{
		private String cardNumber;		
		@Override
		public void setCardNumber(String cardNumber) {
			if (this.cardNumber != null && this.cardNumber != "")
			{
				return;
			}
			
			this.cardNumber = cardNumber;
		}
		
		@Override
		public String getCardNumber() {
			if (cardNumber == null)
			{
				return "";
			}
			
			return cardNumber;
		}
	}
}
