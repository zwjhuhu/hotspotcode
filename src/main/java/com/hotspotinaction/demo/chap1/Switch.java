package com.hotspotinaction.demo.chap1;

/**
 * 程序示例：switch语句中使用String
 * 
 * @author Chen Tao
 * 
 */
public class Switch {

	public static void main(String[] args) {
		Switch tool = new Switch();
		System.out.println(tool.getBankIdByName("CCB"));
	}

	/**
	 * switch语句中使用String
	 * 
	 * @param bankName
	 * @return
	 */
	public String getBankIdByName(String bankName) {
		String bankId = "";
		switch (bankName) {
			case "ICBC" :
				bankId = "B00001";
				break;
			case "ABC" :
				bankId = "B00002";
				break;
			case "CCB" :
				bankId = "B00003";
				break;
			case "BOC" :
				bankId = "B00004";
				break;
			default :
				bankId = "UNVALID";
		}

		return bankId;
	}

}
