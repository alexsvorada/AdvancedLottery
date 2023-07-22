package com.gmail.ianlim224.advancedlottery.commands;

public enum Permissions {
	DEFAULT("advancedlottery.use"), ADMIN("advancedlottery.admin");
	
	private String permission;
	
	Permissions(String permission) {
		this.permission = permission;
	}
	
	public String getName() {
		return permission;
	};
}
