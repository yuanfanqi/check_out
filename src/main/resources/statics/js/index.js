$(function() {
	// 展开折叠菜单
	$("#toggleMenuBtn").on("click", function() {
		$(this).parent("li").toggleClass("active");
	});
	// 退出登录
	$("#logoutBtn").on("click", function() {
		parent.layer.confirm("确认退出吗?", {
			btn : [ '是', '否' ]
		}, function() {
			location.href = "/sys/logout";
		}, function() {
			return;
		});
	});
	// 当点击刷新缓存时
	$("#refreshBtn").on("click", function() {
		$.ajax({
			type : "POST",
			url : "/sys/refresh",
			success : function(result) {
				if (result.status == true) {
					top.location.reload();
				} else {
					parent.layer.confirm("刷新缓存失败,重新登录试试?", {
						btn : [ '是', '否' ]
					}, function() {
						location.href = "/sys/logout";
					}, function() {
						return;
					});
				}
			}
		});
	});

	// 修改密码
	$("#updatePassword")
			.on(
					"click",
					function() {
						layer
								.open({
									type : 1,
									title : "修改密码",
									area : [ '600px', '270px' ],
									shadeClose : false,
									content : jQuery("#passwordLayer"),
									btn : [ '修改', '取消' ],
									btn1 : function(index) {
										var reg1 = new RegExp(
												'(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
										var $username = $("#username").text();
										var $password = $("#password").val();
										var $newpassword = $("#newPassword")
												.val();
										var latter = $newpassword.split("");
										var $firstLatter = $newpassword
												.substring(0, 2);
										if (reg1.test($newpassword) == false) {
											console.log(false);
											layer
													.msg("新密码长度必须为8-30位之间，不能以数字开头，且必须包含数字特殊符号和英文字符，请重新输入");
											$("#newPassword").focus();
											return;
										} else if (!isNaN($firstLatter)) {
											layer.alert("新密码不能以数字开头");
											$("#newPassword").focus();
											return;
										} else if ($newpassword == $username) {
											layer.alert("用户名和密码不能相同");
											$("#newPassword").focus();
											return;
										} else if ($password == $newpassword) {
											layer.alert("新密码不能和旧密码相同");
											$("#newPassword").focus();
											return;
										} else if (isEmpty($password)) {
											layer.alert("旧密码不能为空");
											$("#password").focus();
											return;
										} else {
											var index;
											$
													.ajax({
														type : "POST",
														url : "/sys/user/password",
														data : {
															"username" : $username,
															"password" : $password,
															"newpassword" : $newpassword
														},
														dataType : "json",
														success : function(
																result) {
															if (result.status == true) {
																layer
																		.close(index);
																layer
																		.alert(
																				result.msg,
																				function(
																						index) {
																					location
																							.reload();
																				});
															} else if (result.status == false) {
																layer
																		.alert(result.msg);
															} else {
																layer
																		.alert(result.msg);
															}
														},
														error : function() {
															parent.layer
																	.msg("执行操作过程中出现错误");
														},
														beforeSend : function() {
															index = parent.layer
																	.load(
																			1,
																			{
																				shade : 0.5
																			});
														},
														complete : function() {
															parent.layer
																	.close(index);
														}
													});
										}
									}
								});
					})
	// function 大括号
});

// 点击菜单改变样式
function toggleMenuClassFx(obj) {
	if ($(obj).hasClass("active")) {
		$(obj).removeClass("active");
	} else {
		$(obj).addClass("active");
		$(obj).siblings().removeClass("active");
	}
	$("#currentPathName").text($(obj).find("span").text());
}

function toggleCategoryClassFx(obj) {
	$(obj).addClass("active");
	$(obj).siblings().removeClass("active");
}

// 判断是否为空
function isEmpty(v) {
	switch (typeof v) {
	case 'undefined':
		return true;
	case 'string':
		if (v.replace(/(^[ \t\n\r]*)|([ \t\n\r]*$)/g, '').length == 0)
			return true;
		break;
	case 'boolean':
		if (!v)
			return true;
		break;
	case 'number':
		if (0 === v || isNaN(v))
			return true;
		break;
	case 'object':
		if (null === v || v.length === 0)
			return true;
		for ( var i in v) {
			return false;
		}
		return true;
	}
	return false;
}