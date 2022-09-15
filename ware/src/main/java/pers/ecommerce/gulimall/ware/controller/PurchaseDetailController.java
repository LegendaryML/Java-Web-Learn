package pers.ecommerce.gulimall.ware.controller;

import pers.ecommerce.gulimall.common.annotation.LogOperation;
import pers.ecommerce.gulimall.common.constant.Constant;
import pers.ecommerce.gulimall.common.page.PageData;
import pers.ecommerce.gulimall.common.utils.ExcelUtils;
import pers.ecommerce.gulimall.common.utils.Result;
import pers.ecommerce.gulimall.common.validator.AssertUtils;
import pers.ecommerce.gulimall.common.validator.ValidatorUtils;
import pers.ecommerce.gulimall.common.validator.group.AddGroup;
import pers.ecommerce.gulimall.common.validator.group.DefaultGroup;
import pers.ecommerce.gulimall.common.validator.group.UpdateGroup;
import pers.ecommerce.gulimall.ware.dto.PurchaseDetailDTO;
import pers.ecommerce.gulimall.ware.excel.PurchaseDetailExcel;
import pers.ecommerce.gulimall.ware.service.PurchaseDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 采购详细信息
 *
 * @author AzraelZJ 929780652@qq.com
 * @since 1.0.0 2022-07-13
 */
@RestController
@RequestMapping("ware/purchasedetail")
@Api(tags = "采购详细信息" )
public class PurchaseDetailController {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int" ),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int" ),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String" ),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String" )
    })
    @RequiresPermissions("ware:purchasedetail:page")
    public Result<PageData<PurchaseDetailDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {

        PageData<PurchaseDetailDTO> page = purchaseDetailService.page(params);

        return new Result<PageData<PurchaseDetailDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("ware:purchasedetail:info")
    public Result<PurchaseDetailDTO> get(@PathVariable("id") Long id) {

            PurchaseDetailDTO data = purchaseDetailService.getAttr(id);

        return new Result<PurchaseDetailDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("ware:purchasedetail:save")
    public Result save(@RequestBody PurchaseDetailDTO dto) {

        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

            purchaseDetailService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("ware:purchasedetail:update")
    public Result update(@RequestBody PurchaseDetailDTO dto) {

        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

            purchaseDetailService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("ware:purchasedetail:delete")
    public Result delete(@RequestBody Long[] ids) {

        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

            purchaseDetailService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("ware:purchasedetail:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {

        List<PurchaseDetailDTO> list = purchaseDetailService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, PurchaseDetailExcel.class);
    }
}