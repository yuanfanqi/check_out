package com.checkOut.common.enums;
/**
 * 菜单类型
 * @author 作者 E-mail:
 * @date 创建时间：2017年9月12日 下午6:04:08
 * @version 1.0
 * @since 
 */
public enum PermissionType {

    /**
     * 目录
     */
    CATEGORY(Short.parseShort("0")),
    /**
     * 菜单
     */
    MENU(Short.parseShort("1")),
    /**
     * 权限
     */
    PERMISSION(Short.parseShort("2"));

    private Short value;

    PermissionType(Short value) {
        this.value = value;
	}

	public Short getValue() {
		return value;
	}

	public void setValue(Short value) {
		this.value = value;
	}
	
}
