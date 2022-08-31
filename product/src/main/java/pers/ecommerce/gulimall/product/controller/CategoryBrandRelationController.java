package pers.ecommerce.gulimall.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.ecommerce.gulimall.common.annotation.LogOperation;
import pers.ecommerce.gulimall.common.constant.Constant;
import pers.ecommerce.gulimall.common.page.PageData;
import pers.ecommerce.gulimall.common.utils.ExcelUtils;
import pers.ecommerce.gulimall.common.utils.Result;
import pers.ecommerce.gulimall.common.validator.AssertUtils;
import pers.ecommerce.gulimall.common.validator.ValidatorUtils;
import pers.ecommerce.gulimall.common.validator.group.DefaultGroup;
import pers.ecommerce.gulimall.common.validator.group.UpdateGroup;
import pers.ecommerce.gulimall.product.dto.CategoryBrandRelationDTO;
import pers.ecommerce.gulimall.product.excel.CategoryBrandRelationExcel;
import pers.ecommerce.gulimall.product.service.CategoryBrandRelationService;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author AzraelZJ 929780652@qq.com
 * @since 1.0.0 2022-07-13
 */
@RestController
@RequestMapping("product/categorybrandrelation")
@Api(tags = "品牌分类关联")
public class CategoryBrandRelationController {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("product:categorybrandrelation:page")
    public Result<PageData<CategoryBrandRelationDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {

        PageData<CategoryBrandRelationDTO> page = categoryBrandRelationService.page(params);

        return new Result<PageData<CategoryBrandRelationDTO>>().ok(page);
    }

    /**
     * 查询品牌的关联分类
     *
     * @param params 查询参数，brandId
     * @return 关联分类列表
     */
    @GetMapping("category/list")
    @RequiresPermissions("product:categorybrandrelation:list")
    public Result<List<CategoryBrandRelationDTO>> list(@RequestParam Map<String, Object> params) {

        List<CategoryBrandRelationDTO> list = categoryBrandRelationService.list(params);

        return new Result<List<CategoryBrandRelationDTO>>().ok(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("product:categorybrandrelation:info")
    public Result<CategoryBrandRelationDTO> get(@PathVariable("id") Long id) {

        CategoryBrandRelationDTO data = categoryBrandRelationService.get(id);

        return new Result<CategoryBrandRelationDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("product:categorybrandrelation:save")
    public Result<CategoryBrandRelationDTO> save(@RequestBody CategoryBrandRelationDTO categoryBrandRelationDTO) {

        // 效验数据
        // ValidatorUtils.validateEntity(categoryBrandRelationDTO, AddGroup.class, DefaultGroup.class);

        categoryBrandRelationService.saveDetail(categoryBrandRelationDTO);

        return new Result<>();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("product:categorybrandrelation:update")
    public Result<CategoryBrandRelationDTO> update(@RequestBody CategoryBrandRelationDTO categoryBrandRelationDTO) {

        // 效验数据
        ValidatorUtils.validateEntity(categoryBrandRelationDTO, UpdateGroup.class, DefaultGroup.class);

        categoryBrandRelationService.update(categoryBrandRelationDTO);

        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("product:categorybrandrelation:delete")
    public Result<Long[]> delete(@RequestBody Long[] ids) {

        // 效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        categoryBrandRelationService.delete(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("product:categorybrandrelation:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {

        List<CategoryBrandRelationDTO> list = categoryBrandRelationService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, CategoryBrandRelationExcel.class);
    }
}