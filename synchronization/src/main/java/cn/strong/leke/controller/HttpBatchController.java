package cn.strong.leke.controller;

import cn.strong.leke.logic.TableCustomSycLogic;
import cn.strong.leke.service.ThreadCurrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author shaowenxing@cnstrong.cn
 * @since 9:48
 */
@RestController
public class HttpBatchController {
    @Autowired
    private TableCustomSycLogic threadCurrentService;

    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    @RequestMapping("sysBatchDatabase")
    public String synchronizationDatabase(
//            String url, String username, String password,
                                          String targetUrl, String targetUsername, String targetPassword, String table, int size, int sleep) {
        if (atomicBoolean.get()) {
            return "禁止重复多次执行";
        }
        atomicBoolean.set(true);
        new Thread(() -> {
            boolean boo = threadCurrentService.synchronizationDataSource( targetUrl, targetUsername, targetPassword, table, 0, size, sleep);
            atomicBoolean.set(false);
        }).start();
        return "ok";
    }

}
