package com.qfedu.controller;

import com.qfedu.common.JsonResult;
import com.qfedu.entity.User;
import com.qfedu.service.UserService;
import com.qfedu.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;


//@Controller
//@ResponseBody
@RestController//等同于上面两个的结合
public class UserController {

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "/login.do",method = RequestMethod.POST)//和下面写法相同
    @PostMapping("/login.do")
    public JsonResult login(String code, String password, HttpSession session){
/*        System.out.println(code.getClass().toString());
        System.out.println("code"+code);*/
        try{
            User user = userService.login(code, password);
            session.setAttribute(StrUtils.LOGIN_INFO,user);

            return new JsonResult(0,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1,e.getMessage());
        }
    }

    @RequestMapping("/balance.do")
    public JsonResult balance(HttpSession session){
        User user = (User)session.getAttribute(StrUtils.LOGIN_INFO);
        System.out.println("user:"+user);
        if (user==null){
            return new JsonResult(1,"还未登录");
        }
        Double balance = userService.findBalance(user.getBankCode());
        return new JsonResult(0,balance);
    }


}
