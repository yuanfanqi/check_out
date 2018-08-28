package com.checkOut.utils;

import java.util.ArrayList;
import java.util.List;

import com.checkOut.common.model.sys.SysPermission;

/**
 * Created with IntelliJ IDEA.
 * 菜单树构造帮助类
 * User: Administrator
 * Date: 2017/3/26 0026
 * Time: 0:05
 * To change this template use File | Settings | File Templates.
 */
public class M {

    public static List<SysPermission> allSysPermissons;

    /**
     * 根据节点对象查询该节点的子节点集合
     *
     * @param sysPermission
     * @return
     */
    private static List<SysPermission> getChildMenus(SysPermission sysPermission) {
        List<SysPermission> childMenus = null;
        Integer id = sysPermission.getId();

        if (H.isNotBlank(allSysPermissons)) {
            childMenus = new ArrayList<>();
            for (SysPermission permission : allSysPermissons) {
                if (id.equals(permission.getParentId())) {
                    childMenus.add(permission);
                }
            }
        }
        return childMenus;
    }

    /**
     * 递归构造节点树
     *
     * @param sysPermission
     */
    private static void buildMenuTree(SysPermission sysPermission) {
        List<SysPermission> childMenus = null;
        if (H.isNotBlank(sysPermission)) {
            childMenus = getChildMenus(sysPermission);
            if (H.isNotBlank(childMenus)) {
                sysPermission.setChilds(childMenus);
                for (SysPermission permission : childMenus) {
                    if(H.isNotBlank(permission)){
                        buildMenuTree(permission);
                    }
                }
            }
        }
    }

    /**
     * 传入所有的节点生成最终的节点树
     * @param menus
     * @return
     */
    public static SysPermission makeMenuTree(List<SysPermission> menus) {
        allSysPermissons = menus;
        SysPermission result = new SysPermission();

        List<SysPermission> firstLevelMenuList = new ArrayList<>();

        for (SysPermission menu : allSysPermissons) {
            Integer parentId = menu.getParentId();
            if(parentId==0){
                firstLevelMenuList.add(menu);
                List<SysPermission> childMenus = getChildMenus(menu);
                if(H.isNotBlank(childMenus)){
                    menu.setChilds(childMenus);
                    buildMenuTree(menu);
                }
            }
        }
        result.setChilds(firstLevelMenuList);
        return result;
    }
}
