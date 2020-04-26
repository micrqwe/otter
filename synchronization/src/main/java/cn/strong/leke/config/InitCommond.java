package cn.strong.leke.config;

import cn.strong.leke.model.TableKey;
import cn.strong.leke.service.ThreadCurrentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shaowenxing@cnstrong.cn
 * @since 13:59
 */
@Component
public class InitCommond implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(InitCommond.class);

    @Autowired
    private ThreadCurrentService insertService;
    @Value("${jdbc.key.file}")
    private String file;
    private ObjectMapper o = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {
        if (file == null) {
            file = System.getProperty("user.dir") + File.separator + "meta.dat";
        }
        logger.info("当前的文件记录地址:{}", file);
        insertService.setFile(file);
        File f = new File(file);
        if (!f.exists()) {
            f.createNewFile();
//            insertService.setL(0L);
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = null;
        while ((line = br.readLine()) != null) {
            TableKey tableKey = o.readValue(line, TableKey.class);
            AtomicLong atomicLong = new AtomicLong(tableKey.getId());
            insertService.getKeySize().put(tableKey.getTable(), atomicLong);
            br.close();//关闭文件
            return;
        }
    }
}
