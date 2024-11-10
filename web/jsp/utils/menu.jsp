<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/11/10
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
var menus = [{"backMenu":[{"child":[{"appFrontIcon":"cuIcon-brand","buttons":["新增","查看","修改","删除"],"menu":"学员","menuJump":"列表","tableName":"xueyuan"}],"menu":"学员管理"},{"child":[{"appFrontIcon":"cuIcon-full","buttons":["新增","查看","修改","删除","查看评论"],"menu":"驾校教练","menuJump":"列表","tableName":"jiaxiaojiaolian"}],"menu":"驾校教练管理"},{"child":[{"appFrontIcon":"cuIcon-copy","buttons":["新增","查看","修改","删除","查看评论"],"menu":"驾校信息","menuJump":"列表","tableName":"jiaxiaoxinxi"}],"menu":"驾校信息管理"},{"child":[{"appFrontIcon":"cuIcon-explore","buttons":["新增","查看","修改","删除","预约"],"menu":"驾校车辆","menuJump":"列表","tableName":"jiaxiaocheliang"}],"menu":"驾校车辆管理"},{"child":[{"appFrontIcon":"cuIcon-list","buttons":["查看","修改","删除"],"menu":"驾校报名","menuJump":"列表","tableName":"jiaxiaobaoming"}],"menu":"驾校报名管理"},{"child":[{"appFrontIcon":"cuIcon-camera","buttons":["查看","修改","删除"],"menu":"车辆预约","menuJump":"列表","tableName":"cheliangyuyue"}],"menu":"车辆预约管理"},{"child":[{"appFrontIcon":"cuIcon-pay","buttons":["新增","查看","修改","删除"],"menu":"驾校考试","menuJump":"列表","tableName":"jiaxiaokaoshi"}],"menu":"驾校考试管理"},{"child":[{"appFrontIcon":"cuIcon-taxi","buttons":["查看","修改","删除"],"menu":"考试预约","menuJump":"列表","tableName":"kaoshiyuyue"}],"menu":"考试预约管理"},{"child":[{"appFrontIcon":"cuIcon-skin","buttons":["查看","修改","删除"],"menu":"学员成绩","menuJump":"列表","tableName":"xueyuanchengji"}],"menu":"学员成绩管理"},{"child":[{"appFrontIcon":"cuIcon-news","buttons":["新增","查看","修改","删除"],"menu":"驾校公告","tableName":"news"},{"appFrontIcon":"cuIcon-time","buttons":["查看","修改","删除"],"menu":"轮播图管理","tableName":"config"}],"menu":"系统管理"}],"frontMenu":[{"child":[{"appFrontIcon":"cuIcon-flashlightopen","buttons":["查看","查看评论"],"menu":"驾校教练列表","menuJump":"列表","tableName":"jiaxiaojiaolian"}],"menu":"驾校教练模块"},{"child":[{"appFrontIcon":"cuIcon-addressbook","buttons":["查看","在线报名"],"menu":"驾校信息列表","menuJump":"列表","tableName":"jiaxiaoxinxi"}],"menu":"驾校信息模块"},{"child":[{"appFrontIcon":"cuIcon-discover","buttons":["查看","预约"],"menu":"驾校车辆列表","menuJump":"列表","tableName":"jiaxiaocheliang"}],"menu":"驾校车辆模块"},{"child":[{"appFrontIcon":"cuIcon-copy","buttons":["查看","预约"],"menu":"驾校考试列表","menuJump":"列表","tableName":"jiaxiaokaoshi"}],"menu":"驾校考试模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"否","hasFrontRegister":"否","roleName":"管理员","tableName":"users"},{"backMenu":[{"child":[{"appFrontIcon":"cuIcon-list","buttons":["查看","删除","支付"],"menu":"驾校报名","menuJump":"列表","tableName":"jiaxiaobaoming"}],"menu":"驾校报名管理"},{"child":[{"appFrontIcon":"cuIcon-camera","buttons":["查看","删除"],"menu":"车辆预约","menuJump":"列表","tableName":"cheliangyuyue"}],"menu":"车辆预约管理"},{"child":[{"appFrontIcon":"cuIcon-taxi","buttons":["查看","删除"],"menu":"考试预约","menuJump":"列表","tableName":"kaoshiyuyue"}],"menu":"考试预约管理"},{"child":[{"appFrontIcon":"cuIcon-skin","buttons":["查看"],"menu":"学员成绩","menuJump":"列表","tableName":"xueyuanchengji"}],"menu":"学员成绩管理"}],"frontMenu":[{"child":[{"appFrontIcon":"cuIcon-flashlightopen","buttons":["查看","查看评论"],"menu":"驾校教练列表","menuJump":"列表","tableName":"jiaxiaojiaolian"}],"menu":"驾校教练模块"},{"child":[{"appFrontIcon":"cuIcon-addressbook","buttons":["查看","在线报名"],"menu":"驾校信息列表","menuJump":"列表","tableName":"jiaxiaoxinxi"}],"menu":"驾校信息模块"},{"child":[{"appFrontIcon":"cuIcon-discover","buttons":["查看","预约"],"menu":"驾校车辆列表","menuJump":"列表","tableName":"jiaxiaocheliang"}],"menu":"驾校车辆模块"},{"child":[{"appFrontIcon":"cuIcon-copy","buttons":["查看","预约"],"menu":"驾校考试列表","menuJump":"列表","tableName":"jiaxiaokaoshi"}],"menu":"驾校考试模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"是","hasFrontRegister":"是","roleName":"学员","tableName":"xueyuan"},{"backMenu":[{"child":[{"appFrontIcon":"cuIcon-list","buttons":["查看"],"menu":"驾校报名","menuJump":"列表","tableName":"jiaxiaobaoming"}],"menu":"驾校报名管理"},{"child":[{"appFrontIcon":"cuIcon-camera","buttons":["查看"],"menu":"车辆预约","menuJump":"列表","tableName":"cheliangyuyue"}],"menu":"车辆预约管理"},{"child":[{"appFrontIcon":"cuIcon-taxi","buttons":["查看","成绩"],"menu":"考试预约","menuJump":"列表","tableName":"kaoshiyuyue"}],"menu":"考试预约管理"},{"child":[{"appFrontIcon":"cuIcon-skin","buttons":["查看","修改","删除"],"menu":"学员成绩","menuJump":"列表","tableName":"xueyuanchengji"}],"menu":"学员成绩管理"}],"frontMenu":[{"child":[{"appFrontIcon":"cuIcon-flashlightopen","buttons":["查看","查看评论"],"menu":"驾校教练列表","menuJump":"列表","tableName":"jiaxiaojiaolian"}],"menu":"驾校教练模块"},{"child":[{"appFrontIcon":"cuIcon-addressbook","buttons":["查看","在线报名"],"menu":"驾校信息列表","menuJump":"列表","tableName":"jiaxiaoxinxi"}],"menu":"驾校信息模块"},{"child":[{"appFrontIcon":"cuIcon-discover","buttons":["查看","预约"],"menu":"驾校车辆列表","menuJump":"列表","tableName":"jiaxiaocheliang"}],"menu":"驾校车辆模块"},{"child":[{"appFrontIcon":"cuIcon-copy","buttons":["查看","预约"],"menu":"驾校考试列表","menuJump":"列表","tableName":"jiaxiaokaoshi"}],"menu":"驾校考试模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"否","hasFrontRegister":"否","roleName":"驾校教练","tableName":"jiaxiaojiaolian"}];
<%--
[
    {
        "backMenu": [
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-brand",
                        "buttons": [
                            "新增",
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "学员",
                        "menuJump": "列表",
                        "tableName": "xueyuan"
                    }
                ],
                "menu": "学员管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-full",
                        "buttons": [
                            "新增",
                            "查看",
                            "修改",
                            "删除",
                            "查看评论"
                        ],
                        "menu": "驾校教练",
                        "menuJump": "列表",
                        "tableName": "jiaxiaojiaolian"
                    }
                ],
                "menu": "驾校教练管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-copy",
                        "buttons": [
                            "新增",
                            "查看",
                            "修改",
                            "删除",
                            "查看评论"
                        ],
                        "menu": "驾校信息",
                        "menuJump": "列表",
                        "tableName": "jiaxiaoxinxi"
                    }
                ],
                "menu": "驾校信息管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-explore",
                        "buttons": [
                            "新增",
                            "查看",
                            "修改",
                            "删除",
                            "预约"
                        ],
                        "menu": "驾校车辆",
                        "menuJump": "列表",
                        "tableName": "jiaxiaocheliang"
                    }
                ],
                "menu": "驾校车辆管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-list",
                        "buttons": [
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "驾校报名",
                        "menuJump": "列表",
                        "tableName": "jiaxiaobaoming"
                    }
                ],
                "menu": "驾校报名管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-camera",
                        "buttons": [
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "车辆预约",
                        "menuJump": "列表",
                        "tableName": "cheliangyuyue"
                    }
                ],
                "menu": "车辆预约管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-pay",
                        "buttons": [
                            "新增",
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "驾校考试",
                        "menuJump": "列表",
                        "tableName": "jiaxiaokaoshi"
                    }
                ],
                "menu": "驾校考试管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-taxi",
                        "buttons": [
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "考试预约",
                        "menuJump": "列表",
                        "tableName": "kaoshiyuyue"
                    }
                ],
                "menu": "考试预约管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-skin",
                        "buttons": [
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "学员成绩",
                        "menuJump": "列表",
                        "tableName": "xueyuanchengji"
                    }
                ],
                "menu": "学员成绩管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-news",
                        "buttons": [
                            "新增",
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "驾校公告",
                        "tableName": "news"
                    },
                    {
                        "appFrontIcon": "cuIcon-time",
                        "buttons": [
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "轮播图管理",
                        "tableName": "config"
                    }
                ],
                "menu": "系统管理"
            }
        ],
        "frontMenu": [
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-flashlightopen",
                        "buttons": [
                            "查看",
                            "查看评论"
                        ],
                        "menu": "驾校教练列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaojiaolian"
                    }
                ],
                "menu": "驾校教练模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-addressbook",
                        "buttons": [
                            "查看",
                            "在线报名"
                        ],
                        "menu": "驾校信息列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaoxinxi"
                    }
                ],
                "menu": "驾校信息模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-discover",
                        "buttons": [
                            "查看",
                            "预约"
                        ],
                        "menu": "驾校车辆列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaocheliang"
                    }
                ],
                "menu": "驾校车辆模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-copy",
                        "buttons": [
                            "查看",
                            "预约"
                        ],
                        "menu": "驾校考试列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaokaoshi"
                    }
                ],
                "menu": "驾校考试模块"
            }
        ],
        "hasBackLogin": "是",
        "hasBackRegister": "否",
        "hasFrontLogin": "否",
        "hasFrontRegister": "否",
        "roleName": "管理员",
        "tableName": "users"
    },
    {
        "backMenu": [
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-list",
                        "buttons": [
                            "查看",
                            "删除",
                            "支付"
                        ],
                        "menu": "驾校报名",
                        "menuJump": "列表",
                        "tableName": "jiaxiaobaoming"
                    }
                ],
                "menu": "驾校报名管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-camera",
                        "buttons": [
                            "查看",
                            "删除"
                        ],
                        "menu": "车辆预约",
                        "menuJump": "列表",
                        "tableName": "cheliangyuyue"
                    }
                ],
                "menu": "车辆预约管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-taxi",
                        "buttons": [
                            "查看",
                            "删除"
                        ],
                        "menu": "考试预约",
                        "menuJump": "列表",
                        "tableName": "kaoshiyuyue"
                    }
                ],
                "menu": "考试预约管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-skin",
                        "buttons": [
                            "查看"
                        ],
                        "menu": "学员成绩",
                        "menuJump": "列表",
                        "tableName": "xueyuanchengji"
                    }
                ],
                "menu": "学员成绩管理"
            }
        ],
        "frontMenu": [
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-flashlightopen",
                        "buttons": [
                            "查看",
                            "查看评论"
                        ],
                        "menu": "驾校教练列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaojiaolian"
                    }
                ],
                "menu": "驾校教练模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-addressbook",
                        "buttons": [
                            "查看",
                            "在线报名"
                        ],
                        "menu": "驾校信息列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaoxinxi"
                    }
                ],
                "menu": "驾校信息模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-discover",
                        "buttons": [
                            "查看",
                            "预约"
                        ],
                        "menu": "驾校车辆列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaocheliang"
                    }
                ],
                "menu": "驾校车辆模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-copy",
                        "buttons": [
                            "查看",
                            "预约"
                        ],
                        "menu": "驾校考试列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaokaoshi"
                    }
                ],
                "menu": "驾校考试模块"
            }
        ],
        "hasBackLogin": "是",
        "hasBackRegister": "否",
        "hasFrontLogin": "是",
        "hasFrontRegister": "是",
        "roleName": "学员",
        "tableName": "xueyuan"
    },
    {
        "backMenu": [
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-list",
                        "buttons": [
                            "查看"
                        ],
                        "menu": "驾校报名",
                        "menuJump": "列表",
                        "tableName": "jiaxiaobaoming"
                    }
                ],
                "menu": "驾校报名管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-camera",
                        "buttons": [
                            "查看"
                        ],
                        "menu": "车辆预约",
                        "menuJump": "列表",
                        "tableName": "cheliangyuyue"
                    }
                ],
                "menu": "车辆预约管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-taxi",
                        "buttons": [
                            "查看",
                            "成绩"
                        ],
                        "menu": "考试预约",
                        "menuJump": "列表",
                        "tableName": "kaoshiyuyue"
                    }
                ],
                "menu": "考试预约管理"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-skin",
                        "buttons": [
                            "查看",
                            "修改",
                            "删除"
                        ],
                        "menu": "学员成绩",
                        "menuJump": "列表",
                        "tableName": "xueyuanchengji"
                    }
                ],
                "menu": "学员成绩管理"
            }
        ],
        "frontMenu": [
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-flashlightopen",
                        "buttons": [
                            "查看",
                            "查看评论"
                        ],
                        "menu": "驾校教练列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaojiaolian"
                    }
                ],
                "menu": "驾校教练模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-addressbook",
                        "buttons": [
                            "查看",
                            "在线报名"
                        ],
                        "menu": "驾校信息列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaoxinxi"
                    }
                ],
                "menu": "驾校信息模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-discover",
                        "buttons": [
                            "查看",
                            "预约"
                        ],
                        "menu": "驾校车辆列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaocheliang"
                    }
                ],
                "menu": "驾校车辆模块"
            },
            {
                "child": [
                    {
                        "appFrontIcon": "cuIcon-copy",
                        "buttons": [
                            "查看",
                            "预约"
                        ],
                        "menu": "驾校考试列表",
                        "menuJump": "列表",
                        "tableName": "jiaxiaokaoshi"
                    }
                ],
                "menu": "驾校考试模块"
            }
        ],
        "hasBackLogin": "是",
        "hasBackRegister": "否",
        "hasFrontLogin": "否",
        "hasFrontRegister": "否",
        "roleName": "驾校教练",
        "tableName": "jiaxiaojiaolian"
    }
]
--%>
var hasMessage = '';
