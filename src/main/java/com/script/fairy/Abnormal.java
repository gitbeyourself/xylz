package com.script.fairy;

import android.content.Context;
import com.script.framework.AtFairyImpl;
import com.script.framework.TaskContent;
import com.script.opencvapi.AtFairyConfig;
import com.script.opencvapi.FindResult;
import com.script.opencvapi.LtLog;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2019/3/22 0022.
 */

public class Abnormal  {
    AtFairyImpl mFairy;
    FindResult result;
    FindResult result1;
    Util util;
    int task_id;
    boolean restart=false;
    public Abnormal(AtFairyImpl atFairy) throws Exception {
        mFairy = atFairy;
        util=new Util(mFairy);
        task_id = Integer.parseInt(AtFairyConfig.getOption("task_id"));
        if(AtFairyConfig.getOption("restart").equals("1")){
            restart=true;
        }
    }

    public String CMD(String[] mString) {
        Context mContext = mFairy.getContext();
        Runtime cmd = Runtime.getRuntime();
        String inline = "";
        String header = "";
        try {
            Process p = cmd.exec(mString);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((inline = br.readLine()) != null) {
                String mStr = inline;
                if ( mStr.contains("CPU%")) {
                    header = mStr;
                    if(sb.length() > 0) {

                        sb.delete(0, sb.length());
                    }
                    continue;
                    // LtLog.i("haha123: " + mFairy.getLineInfo("============================") + mStr);
                }



                if(mStr.contains(mContext.getPackageName())){
//                    sb.append(inline);
                    LtLog.i("CPU USAGE-INFO-HEADER : "  + header);
                    LtLog.i("CPU USAGE-INFO-BODY   : "  + mStr);
                }

//                }
            }
            inline = br.readLine();
            br.close();
            p.waitFor();

        } catch (Exception e) {

        }
        return inline;
    }


    //全局处理
    public void erro() throws Exception {
       // LtLog.e( "-----------"+CMD(new String[]{"top"/*, "-m", "10", "-n", "1"*/})+"---------------");
        if(task_id==1643){
            result= mFairy.findPic(  923,7,1280,293,new String[]{"close2.png","close4.png","close5.png"});
            if(result.sim>0.8f){
                result1=mFairy.findPic(  new String []{"free.png","zuoqi.png","bring home.png"});
                if(result1.sim<0.8f){
                    mFairy.onTap(0.8f,result,"error_关闭活动弹窗",500);
                }
            }
        }

        if(restart){
            int hour=mFairy.dateHour();
            int minute= mFairy.dateMinute();
            if(hour==5&&minute==0){
                mFairy.restart();
                LtLog.e(mFairy.getLineInfo("重启任务"));
                return;
            }
        }


        result=mFairy.findPic(  "word not exit.png");
        mFairy.onTap(0.8f,result,"error_暂不退出",500);

        result=mFairy.findPic(  "jiantou.png");
        mFairy.onTap(0.8f,result,"error_关闭聊天频道",500);

        result=mFairy.findPic(  new String[]{"word select channel.png","word select channel1.png"});
        mFairy.onTap(0.8f,result,result.x+53,result.y+97,result.x+73,result.y+117,"error_关闭渠道",1000);

        result=mFairy.findPic(  new String[]{"pic activity exit.png","pic activity exit1.png"});
        mFairy.onTap(0.8f,result,"error_误点活动",1000);

        result=mFairy.findPic(  "word know.png");
        mFairy.onTap(0.8f,result,"error_我知道了",500);

        result=mFairy.findPic(  "word none find.png");
        mFairy.onTap(0.8f,result,509,439,523,453,"error_未探",500);

        result=mFairy.findPic(  "word btn start.png");
        mFairy.onTap(0.8f,result,"error_启动游戏",1000);

        result=mFairy.findPic(  "word notice.png");
        mFairy.onTap(0.8f,result,1149,69,1159,81,"error_关闭公告",1000);

        result=mFairy.findPic(  "word hint image.png");
        mFairy.onTap(0.8f,result,715,406,743,430,"error_以这个形象进入",1000);

        result=mFairy.findPic(403,432,903,587, new String[]{"word btn enter.png","word btn enter1.png"});
        mFairy.onTap(0.8f,result,"error_进入游戏",1000);

        result=mFairy.findPic(  "word btn next step.png");
        mFairy.onTap(0.8f,result,"error_下一步",1000);

        result=mFairy.findPic(  "any close.png");
        mFairy.onTap(0.75f,result,"error_任意区域关闭",500);

        result=mFairy.findPic(  1225,665,1269,713,"chat continue.png");
        mFairy.onTap(0.8f,result,"error_几秒后自动跳过",200);
        if(result.sim>0.8f)util.initDaze();

        result=mFairy.findPic(  "chat continue1.png");
        mFairy.onTap(0.8f,result,"error_几秒后自动跳过",200);
        if(result.sim>0.8f)util.initDaze();

        result = mFairy.findPic(  1221, 0,1280, 51, "chat continue2.png");
        mFairy.onTap(0.8f,result,"error_跳过对话",200);

        result=mFairy.findPic(  new String[]{"word ren.png","word no like.png"});
        mFairy.onTap(0.8f,result,"error_忍气吞声，不喜欢",500);

        result=mFairy.findPic(  673,318,827,347,"qinmi.png");
        mFairy.onTap(0.8f,result,705,434,756,463,"error_确认跳过 不获得亲密度",100);

        result=mFairy.findPic(  "word hint have latest version.png");
        mFairy.onTap(0.8f,result,635,429,646,440,"error_有新版本，确定下载",1000);

        result = mFairy.findPic("new sure.png");
        mFairy.onTap(0.8f, result, "err新版qq隐私政策同意", 1000);

        result = mFairy.findPic("new authorization.png");
        mFairy.onTap(0.8f, result, "err新版qq授权", 1000);

        result = mFairy.findPic(new String[]{"new login.png","new login2.png","new login3.png"});
        mFairy.onTap(0.8f, result, "err新版qq登陆", 1000);
    }
}
