package com.cmx.chatserver;

import com.cmx.chatserver.es.service.MessageLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServerApplicationTests {

	@Autowired
	private MessageLogService messageLogService;

	@Test
	public void contextLoads() {
//		messageLogService.addLog("离离原上草");
//		messageLogService.addLog("一岁一枯荣");
//		messageLogService.addLog("野火烧不尽");
//		messageLogService.addLog("春风吹又生");

		messageLogService.search();

	}

}
