package org.github.dingdangdog.controller;

import org.github.dingdangdog.utils.FileUtilOm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * say hello
 *
 * @author DingDangDog
 * @since 2022/10/4 11:13
 */
@RestController
@RequestMapping("/say")
public class SayController {

    @GetMapping("/hello")
    public String hello(String name) {
        System.out.println(name);
        FileUtilOm.saveFile("/tmp/", name, name);
        return "Hello " + name + " !";
    }
}
