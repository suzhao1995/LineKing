package ${basePackage}.web;
import ${basePackage}.core.ResponseBean;
import ${basePackage}.core.ResponseBean;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* @author wanghang
* @Description description
* @create
*/
@Slf4j
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    /**
    * 添加
    * @param ${modelNameLowerCamel}
    * @author wanghang
    * @return
    */
    @PostMapping("/add")
    public ResponseBean add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        try {
            ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
            log.info("description-添加-成功");
        } catch (Exception e) {
            log.error("description-添加-失败",e);
            return ResponseBean.errorResult("添加失败！");
        }
        return ResponseBean.successResult();
    }

    /**
    * 删除
    * @param ${modelNameLowerCamel}Id
    * @author wanghang
    * @return
    */
    @GetMapping("/delete")
    public ResponseBean delete(@RequestParam String ${modelNameLowerCamel}Id) {
        try {
            ${modelNameLowerCamel}Service.deleteById(${modelNameLowerCamel}Id);
            log.info("description-删除-成功");
        } catch (Exception e) {
            log.error("description-删除-失败",e);
            return ResponseBean.errorResult("删除失败！");
        }
        return ResponseBean.successResult();
    }

    /**
    * 修改
    * @param ${modelNameLowerCamel}
    * @author wanghang
    * @return
    */
    @PostMapping("/update")
    public ResponseBean update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        try {
            ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
            log.info("description-修改-成功");
        } catch (Exception e) {
            log.error("description-修改-失败",e);
            return ResponseBean.errorResult("修改失败！");
        }
        return ResponseBean.successResult();
    }

    /**
    * 数据回显
    * @param ${modelNameLowerCamel}Id
    * @author wanghang
    * @return
    */
    @GetMapping("/detail")
    public ResponseBean detail(@RequestParam String ${modelNameLowerCamel}Id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(${modelNameLowerCamel}Id);
        log.info("description-数据回显-成功");
        return ResponseBean.successResult(${modelNameLowerCamel});
    }

    /**
    * 分页查询
    * @param ${modelNameLowerCamel}
    * @author wanghang
    * @return
    */
    @PostMapping("/list")
    public ResponseBean list(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {

        PageInfo<${modelNameUpperCamel}> pageInfo = PageHelper.startPage(${modelNameLowerCamel}).doSelectPageInfo(() -> ${modelNameLowerCamel}Service.findAll(${modelNameLowerCamel}));
        log.info("description-分页查询-成功");
        return ResponseBean.successResult(pageInfo);
    }
}
