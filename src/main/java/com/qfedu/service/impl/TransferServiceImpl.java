package com.qfedu.service.impl;

import com.qfedu.dao.TransferDao;
import com.qfedu.dao.UserDao;
import com.qfedu.entity.Transfer;
import com.qfedu.entity.User;
import com.qfedu.service.TransferService;
import com.qfedu.vo.TransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    @Resource(name = "transferDao")
    private TransferDao transferDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void transfer(String sourceCode, String descCode, Double money) {

        User sourceUser = userDao.findByCode(sourceCode);

        User descUser = userDao.findByCode(descCode);
        if (sourceCode.equals(descCode)) {
            throw new RuntimeException("转账卡号不能相同");
        }

        if (descUser == null) {
            throw new RuntimeException("转账卡号输入有误");
        }
        if (sourceUser.getBalance() < money) {
            throw new RuntimeException("余额不足");
        }
        /*减钱*/
        Transfer sTransfer = new Transfer();
        sTransfer.setUid(sourceUser.getUid());
        sTransfer.setMoney(0 - money);
        sTransfer.setBalance(sourceUser.getBalance() - money);
        /*在原数据库减钱*/
        sourceUser.setBalance(money);
        userDao.decreaseFromMoney(sourceUser);
        /*在记录表记录*/
        transferDao.add(sTransfer);

        /*加钱*/
        Transfer dTransfer = new Transfer();
        dTransfer.setUid(descUser.getUid());
        dTransfer.setMoney(money);

        dTransfer.setBalance(descUser.getBalance() + money);
        /*在原数据库加钱*/
        descUser.setBalance(money);
        userDao.increaseToMoney(descUser);
        /*在记录表记录*/
        transferDao.add(dTransfer);
    }
    @Override
    public List<TransferVo> findAllTransferInfo(Integer uid, Date beginTime,Date endTime){
        List<TransferVo> list = transferDao.findAll(uid, beginTime, endTime);
        return list;
    }
}
