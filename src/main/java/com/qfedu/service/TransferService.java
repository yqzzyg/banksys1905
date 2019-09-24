package com.qfedu.service;

import com.qfedu.vo.TransferVo;

import java.util.Date;
import java.util.List;

public interface TransferService {

    public void transfer(String sourceCode,String descCode,Double money);
    public List<TransferVo> findAllTransferInfo(Integer uid, Date beginTime, Date endTime);
}

