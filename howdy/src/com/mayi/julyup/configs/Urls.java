package com.mayi.julyup.configs;

public class Urls {

    public static final String BASIC_URL = "http://115.28.93.193/fsc/dataExchange.do";
    public static final String Login_URL = BASIC_URL + "?object=im-system-login_login";//2.2.1用户登录
    public static final String LogOut_URL=BASIC_URL+"?object=im-system-login_loginOff";//2.2.2用户注销
    public static final String ModifyPwd_URL=BASIC_URL+"?object=im-system-user_changePasswordByLoginName";  //2.2.3修改密码
    
    public static final String UploadTask_Init_URL = BASIC_URL + "?object=im-system-task_init";  //2.3.1上传任务初始化
    public static final String Upload_UnFinish_URL=BASIC_URL+"?object=im-system-task_queryForApp";//2.3.2上传任务查询（未提交审核）
    public static final String Upload_Finish_URL=BASIC_URL+"?object=im-system-task_queryForApp2";//2.3.3上传任务图片查询（已提交审核）
    public static final String pic_search=BASIC_URL+"?object=im-system-image_queryForApp";//2.3.4上传任务图片查询
    public static final String UploadImage_URL=BASIC_URL+"?object=im-system-image_multiUploadForApp";//2.3.5图片上传
    public static final String DeleterImage_URL=BASIC_URL+"?object=im-system-image_delForApp";//2.3.6图片删除
    public static final String UploadTaskCheck_URL=BASIC_URL+"?object=im-system-image_submitForApp";//2.3.7上传任务提交审核
    
    public static final String FilesDir_Year_URL=BASIC_URL+"?object=im-system-user-file_getYear";//2.4.1文件目录年
    public static final String FilesDir_Month_URL=BASIC_URL+"?object=im-system-user-file_getMonth";//2.4.2文件目录月
    public static final String GetFiles_URL=BASIC_URL+"?object=im-system-user-file_getFiles";//2.4.3发布文件查询
    
    public static final String Get_System_Message=BASIC_URL+"?object=im-system-message_getNewMessage";//3.2.5系统公告查询
    public static final String Get_Task_Year=BASIC_URL+"?object=im-system-task_getYearForApp";//3.3.8 上传任务年
    public static final String Get_Task_Year2=BASIC_URL+"?object=im-system-task_getYearForApp2";//3.3.8 上传任务年
    
    public static final String Request_PIC=BASIC_URL+"?object=im-system-task_queryForApp3";//3.3.10 上传任务查询
    public static final String Get_Task_Year3=BASIC_URL+"?object=im-system-task_getYearForApp3";//3.3.11上传任务年
}

