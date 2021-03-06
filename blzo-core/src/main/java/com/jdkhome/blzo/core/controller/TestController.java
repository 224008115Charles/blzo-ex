package com.jdkhome.blzo.core.controller;

import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.common.constants.ErrorMsg;
import com.jdkhome.blzo.ex.risk.annotation.Risk;
import com.jdkhome.blzo.ex.usignin.annotation.CurrentUser;
import com.jdkhome.blzo.ex.usignin.annotation.UserMaybeSignin;
import com.jdkhome.blzo.ex.utils.constants.RegularExpression;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.version.annotation.MinVersion;
import com.jdkhome.blzo.ex.version.annotation.Version;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * author link.ji
 * createTime 下午6:55 2018/6/19
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * @api {post} /api/test/aaa [测试]测试接口
     * @apiName apiTestAaa
     * @apiGroup TestController
     * @apiDescription 测试接口
     * @apiParam {String} email email地址
     * @apiSuccessExample {json} Success-response:
     * HTTP/1.1 0 OK
     * <p>
     * {
     * "code": 200,
     * "message": "success",
     * "data": {...}
     * }
     * @apiErrorExample {json} Error-response:
     * HTTP/1.1 error
     * <p>
     * {
     * "code":xxx,
     * "message":"xxx"
     * "data":{...}
     * }
     */
    @Data
    class TestParams {
        @NotBlank(message = ErrorMsg.EMAIL_NOT_NULL)
        @Pattern(regexp = RegularExpression.REGEX_EMAIL, message = ErrorMsg.FUCK)
        String email;
    }

    @Risk(period = 5L, count = 10, time = 3L) // 5秒内，该接口请求达到10次 限制3秒 该设置对所有节点有效 依赖于@Api注解
    @UserMaybeSignin // 用户可能登录 如果用户登录，则能够获取到用户Id
    @MinVersion(value = "1.5.0", max = "2.0.0") // 版本控制
    @Api("测试用接口") // 日志
    @RequestMapping(value = "/aaa", method = RequestMethod.POST)
    public ApiResponse apiTestAaa(@Valid TestParams params, BindingResult validResult,
                                  @Version String version, @CurrentUser Integer userId) {

        return ApiResponse.success(version);
    }
}
