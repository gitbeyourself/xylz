package com.script.fairy;

import android.os.Build;

import com.script.content.ScProxy;
import com.script.framework.AtFairyImpl;
import com.script.framework.TaskContent;
import com.script.opencvapi.AtFairyConfig;
import com.script.opencvapi.FindResult;
import com.script.opencvapi.LtLog;
import com.script.opencvapi.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.script.opencvapi.AtFairy2.TASK_STATE_FINISH;


/**
 * Created by Administrator on 2019/1/24 0024.
         */
public class TaskMain {
     AtFairyImpl mFairy;
     Util util;
     TeamTask teamTask;
     SingleTask singleTask;
     LimitLessTask limitlessTask;
     TimingActivity timingActivity;
     FindResult result;
     public  TaskMain (AtFairyImpl atFairy) throws Exception {
         mFairy = atFairy;
         mFairy.setGameName("雪鹰领主");
         mFairy.setGameVersion(446);
        // LtLog.e("我是线上版本");
         init();
         util= new Util(mFairy);
         teamTask=new TeamTask(mFairy);
         singleTask=new SingleTask(mFairy);
         timingActivity=new TimingActivity(mFairy);
         limitlessTask=new LimitLessTask(mFairy);
         Thread.sleep(4000);
         mFairy.initMatTime();
    }

     public void main() throws Exception {

         if(!AtFairyConfig.getOption("task_id").equals("")){
             task_id = Integer.parseInt(AtFairyConfig.getOption("task_id"));
         }

         ScProxy.profiler().startWithUserTag("");
         switch (task_id) {
             case 1643://新手指引
                 singleTask.novice();
                 break;
             case 1645://日常任务
                 singleTask.daily();
                 break;
             case 1647://副本任务
                 result=mFairy.findPic("leave.png");
                 if (result.sim > 0.8f){

                 }else {
                     util.back_city();
                 }
                 teamTask.copy();
                 break;
             case 1649://野外挂机
                 limitlessTask.limitless();
                 limitlessTask.hang();
                 break;
             case 1955://线上测试
                 singleTask.lineTest();
                break;
             case 100://测试
                 //singleTask.szmt();

                 singleTask.szzyzd();
                break;

            //以下是测试渠道
             case 2368://新手指引
                 singleTask.novice();
                 break;
             case 2370://日常任务
                 singleTask.daily();
                 break;
             case 2372://副本任务
                 util.back_city();
                 teamTask.copy();
                 break;
             case 2374://野外挂机
                 limitlessTask.hang();
                 break;
         }

         mFairy.finish(AtFairyConfig.getTaskID(), TASK_STATE_FINISH);
         Thread.sleep(2000);
    }

    private int  task_id;
    public void  init() throws Exception {
        task_id = 0;
        try {
            JSONObject optionJson = new JSONObject(AtFairyConfig.getUserTaskConfig());
            LtLog.e(mFairy.getLineInfo("选项列表" + optionJson));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!AtFairyConfig.getOption("task_id").equals("")) {
            task_id = Integer.parseInt(AtFairyConfig.getOption("task_id"));
        }
    }
}
