package com.test;

import org.junit.Test;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JiGuangPushUtil {

	  //���������ֱ���д�������masterSecret��appKey
    private static JPushClient jPushClient=new JPushClient("masterSecret","appKey");

    /**
     * ֪ͨ����
     * ��ע�����ͷ�ʽ��Ϊ��ʱ�����͵�ֵҲ����Ϊ�գ����ͷ�ʽΪ��ʱ������ֵ����Ҫ��
     * @param type ���ͷ�ʽ��1����tag����ǩ���ͣ�2����alias����������
     * @param value ���͵ı�ǩ�����ֵ
     * @param alert ���͵�����
     */
    private static void pushNotice(String type,String value,String alert){
        Builder builder= PushPayload.newBuilder();
        builder.setPlatform(Platform.all());//���ý��ܵ�ƽ̨��allΪ����ƽ̨��������׿��ios����΢���
        //��������û������ߡ�������Ϣ�����ʱ��
        Options options=Options.sendno();
        options.setTimeToLive(86400l);    //����Ϊ86400Ϊ����һ�죬���������Ĭ��Ҳ�Ǳ���һ��
        builder.setOptions(options);
        //�������ͷ�ʽ
        if(type.equals("alias")){
            builder.setAudience(Audience.alias(value));//���ݱ�������
        }else if(type.equals("tag")){
            builder.setAudience(Audience.tag(value));//���ݱ�ǩ����
        }else{
            builder.setAudience(Audience.all());//Audience����Ϊall��˵�����ù㲥��ʽ���ͣ������û������Խ��յ�
        }
        //����Ϊ����֪ͨ�ķ�ʽ������Ϣ
        builder.setNotification(Notification.alert(alert));
        PushPayload pushPayload=builder.build();
        try{
            //�������ͣ�ʵ�����;�����һ��
            PushResult pushResult=jPushClient.sendPush(pushPayload);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * �Զ�����Ϣ����
     * ��ע�����ͷ�ʽ��Ϊ��ʱ�����͵�ֵҲ����Ϊ�գ����ͷ�ʽΪ��ʱ������ֵ����Ҫ��
     * @param type ���ͷ�ʽ��1����tag����ǩ���ͣ�2����alias����������
     * @param value ���͵ı�ǩ�����ֵ
     * @param alert ���͵�����
     */
    private static void pushMsg(String type, String value,String alert){
        Builder builder= PushPayload.newBuilder();
        builder.setPlatform(Platform.all());//���ý��ܵ�ƽ̨
        if(type.equals("alias")){
            builder.setAudience(Audience.alias(value));//��������
        }else if(type.equals("tag")){
            builder.setAudience(Audience.tag(value));//��ǩ����
        }else{
            builder.setAudience(Audience.all());//Audience����Ϊall��˵�����ù㲥��ʽ���ͣ������û������Խ��յ�
        }
        Message.Builder newBuilder=Message.newBuilder();
        newBuilder.setMsgContent(alert);//��Ϣ����
        Message message=newBuilder.build();
        builder.setMessage(message);
        PushPayload pushPayload=builder.build();
        try{
            PushResult pushResult=jPushClient.sendPush(pushPayload);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public static void main(String[] args){
        //����ǩΪkefu���û�������Ϣ����
        JiGuangPushUtil.pushNotice("tag","kefu","�����µ������뼰ʱ����");
    }
}
