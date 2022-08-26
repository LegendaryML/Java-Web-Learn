/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package pers.ecommerce.gulimall.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.ecommerce.gulimall.common.exception.ErrorCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应数据
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Data
@ApiModel(value = "响应")
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编码：0表示成功，其他值表示失败
     */
    @ApiModelProperty(value = "编码：0表示成功，其他值表示失败")
    private int code = 0;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String msg = "success";

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result<T> ok(T data) {

        this.setData(data);
        return this;
    }

    public boolean success() {

        return code == 0;
    }

    public Result<T> error() {

        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code) {

        this.code = code;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(String msg) {

        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
        return this;
    }

    public Result<T> error(int code, String msg) {

        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(int code, String msg, T data) {

        this.code = code;
        this.msg = msg;
        this.data = data;

        return this;
    }
}
