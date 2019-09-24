package com.qfedu.controller;

import com.qfedu.common.JsonResult;
import com.qfedu.entity.User;
import com.qfedu.service.TransferService;
import com.qfedu.utils.StrUtils;
import com.qfedu.vo.TransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class TranferController {

    @Autowired
    private TransferService transferService;

    @RequestMapping("/transfer.do")
    public JsonResult tranfer(String otherCode, Double money, HttpSession session) {

        User user = (User) session.getAttribute(StrUtils.LOGIN_INFO);
        if (user == null) {
            throw new RuntimeException("还未登录");
        }
        transferService.transfer(user.getBankCode(), otherCode, money);

        return new JsonResult(0, "转账成功");
    }

    /*    *//*
     * @ExceptionHandler修饰处理异常的方法
     * 注解的参数表示要处理的异常，注解修饰的方法，要是有异常作为参数
     * *//*
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception e){
        return new JsonResult(1,e.getMessage());
    }*/

    @RequestMapping("transferinfo.do")
    public JsonResult listTransferInfo(@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime, HttpSession session) {

        User user = (User) session.getAttribute(StrUtils.LOGIN_INFO);
        if (user == null) {
            return new JsonResult(1, "还未登录");
        }
        List<TransferVo> list = transferService.findAllTransferInfo(user.getUid(), beginTime, endTime);
        return new JsonResult(0, list);

    }

}
