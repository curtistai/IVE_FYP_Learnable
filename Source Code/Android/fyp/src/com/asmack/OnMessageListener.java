package com.asmack;

import org.jivesoftware.smack.packet.Message;

import android.view.View;
import android.widget.ListView;

/**
 * @author zhanghaitao
 * @date 2011-7-5
 * @version 1.0
 */
/*
 * version其中OnContactStateListener是一个事件接口，代码如下：
 */
public interface OnMessageListener {
	/*
	 * @return 处理接收到的消息
	 */
	public void processMessage(Message message);

}
