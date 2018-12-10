package com.luffy.tencentimlib.helper;


import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;

import java.util.List;
import java.util.Observable;

/**
 * Created by lvlufei on 2018/5/23
 *
 * @desc 消息通知事件，上层界面可以订阅此事件
 */
public class MessageEvent extends Observable implements TIMMessageListener {

    private volatile static MessageEvent instance;

    private MessageEvent() {
        //注册消息监听器
        TIMManager.getInstance().addMessageListener(this);
    }

    public static MessageEvent getInstance() {
        if (instance == null) {
            synchronized (MessageEvent.class) {
                if (instance == null) {
                    instance = new MessageEvent();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        for (TIMMessage item : list) {
            setChanged();
            notifyObservers(item);
        }
        return false;
    }

    /**
     * 主动通知新消息
     */
    public void onNewMessage(TIMMessage message) {
        setChanged();
        notifyObservers(message);
    }
}
