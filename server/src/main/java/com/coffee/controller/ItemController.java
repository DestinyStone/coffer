package com.coffee.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coffee.common.api.R;
import com.coffee.common.exception.ServiceException;
import com.coffee.entity.pojo.Item;
import com.coffee.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = file.getInputStream();
            byte[] buffer = new byte[in.available()];
            in.read(buffer);

            String upload = System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes" + File.separator + "static" + File.separator + "upload";
            upload += File.separator + file.getOriginalFilename();
            out = new FileOutputStream(upload);
            out.write(buffer);
            return R.data("/api/upload/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new ServiceException("文件上传失败");
    }

    @RequestMapping("/list")
    public R list(Item item) {

        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(item.getType()), Item::getType, item.getType());

        List<Item> list = itemService.list(wrapper);
        return R.data(list);
    }

    @RequestMapping("/update/{id}")
    public R update(@PathVariable("id") Integer id,  @RequestBody Item item) {
        item.setItemId(id);
        itemService.updateById(item);
        return R.success("操作成功");
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam("id") Integer id) {
        itemService.removeById(id);
        return R.success("操作成功");
    }

    @RequestMapping("/save")
    public R save(@RequestBody Item item) {
        item.setItemId(null);
        itemService.save(item);
        return R.success("操作成功");
    }

    @RequestMapping("/top3")
    @ResponseBody
    public R top3() {
        List<Item> collect = itemService.list().stream().limit(3).collect(Collectors.toList());
        return R.data(collect);
    }

    @RequestMapping("/id")
    public R id(@RequestParam("id") Integer id) {
        Item item = itemService.getById(id);
        return R.data(item);
    }
}
