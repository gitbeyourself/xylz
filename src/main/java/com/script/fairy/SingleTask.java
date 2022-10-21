package com.script.fairy;
import com.script.framework.AtFairyImpl;
import com.script.framework.TaskContent;
import com.script.opencvapi.AtFairyConfig;
import com.script.opencvapi.FindResult;
import com.script.opencvapi.LtLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class SingleTask extends TaskContent {
    AtFairyImpl mFairy;
    FindResult result;
    FindResult result1;
    Util util;
    List taskList=null;
    long daze=0;
    public SingleTask(AtFairyImpl atFairy) throws Exception {
        mFairy = atFairy;
        util = new Util(mFairy);
    }

    public void setTaskName(int taskContentNum) throws Exception {
        super.setTaskName(taskContentNum);
        mFairy.condit();
    }

    //新手指引
    //新手指引 至星辰塔5层 或者龙山历练
    //取完名字和捏完脸后开启脚本
    public void novice() throws Exception {
        new SingleTask(mFairy){
            int overTime=0;
            @Override
            public void create() throws Exception {
                super.create();
                setTaskName(0);
            }
            int colorNum=0;
            String change="";
            @Override
            public void content_0() throws Exception {
                result=mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"打开任务栏",1000);

                result = mFairy.findPic(890,3,1275,235,new String[]{"close.png","close3.png"});
                if(result.sim>0.8){
                    result1=mFairy.findPic(new String []{"free.png","zuoqi.png","bring home.png"});
                    if(result1.sim<0.8f){
                        mFairy.onTap(0.8f,result,"single_关闭",500);
                    }
                }

                result=mFairy.findPic("word cancel.png");
                mFairy.onTap(0.8f,result,"取消",500);

                result=mFairy.findPic(714,196,1074,463,"fight btn.png");
                mFairy.onTap(0.8f,result,"互动 按钮 战斗",200);

                //互动事件
                colorNum=mFairy.getColorNum("1109,481,1171,537","255,255,255",0.9f,0);
                if(colorNum>60&&mFairy.findPic("dont.png").sim>0.8){
                    mFairy.onTap(1109,481,1171,537,"互动事件",500);
                }

                //判断是否在战斗
                result1=mFairy.findPic("zhanli.png");
                result=mFairy.findPic("task list.png");
                if(result1.sim>0.7f||result.sim>0.8f){
                    //匹配扛起 查看 采集 激活 施法等事件
                    colorNum=mFairy.getColorNum("839,301,886,350","244,246,244",0.9f,0);
                    if(colorNum>100){
                        mFairy.onTap(862,311,876,335,"扛起 查看 采集 激活 驯服 调查等",5000);
                    }

                    result=mFairy.findPic("ing.png");
                    if(result.sim>0.8f){
                        mFairy.sleep(5000);
                    }
                }

                result=mFairy.findPic("auto ing.png");
                if(result.sim<0.8f){
                    mFairy.onTap(59,151,105,174,"auto 主线指引1",200);
                    mFairy.onTap(64,183,124,206,"auto 主线指引2",1500);
                }else{
                    mFairy.sleep(1000);
                    change=mFairy.change(1236,152,1263,167);
                    if(change.equals("0,0")){
                        overTime++;
                        if(overTime==5){
                            mFairy.onTap(59,151,105,174,"static 主线指引1",200);
                            mFairy.onTap(64,183,124,206,"static 主线指引2",1500);
                            overTime=0;
                        }
                    }
                }

                result=mFairy.findPic("start.png");
                mFairy.onTap(0.8f,result,"开始",100);

                result=mFairy.findPic("tai.png");
                mFairy.onTap(0.8f,result,"弹窗 对话 太棒了",100);

                result=mFairy.findPic("guanbi.png");
                mFairy.onTap(0.8f,result,"点击任意区域关闭",100);

                result=mFairy.findPic("close1.png");
                mFairy.onTap(0.8f,result,"关闭地图",100);

                result=mFairy.findPic(908,452,1109,536,"auto wear.png");
                mFairy.onTap(0.8f,result,"捡到装备，自动穿上或自动使用",100);

                result=mFairy.findPic("bring home.png");
                mFairy.onTap(0.8f,result,"宠物 带它回家",100);

                result=mFairy.findPic(928,331,1002,510,"dialogue.png");
                mFairy.onTap(0.8f,result,"回复NPC",100);

                result=mFairy.findPic(989,589,1280,720,"go.png");
                mFairy.onTap(0.8f,result,"接受任务 前往",100);

                result=mFairy.findPic("sheji2.png");
                mFairy.onTap(0.8f,result,"互动 按钮 射击",100);

                result=mFairy.findPic("apply for.png");
                mFairy.onTap(0.8f,result,"家族 一键申请",100);

                result=mFairy.findPic(757,383,943,551,"bird tip.png");
                mFairy.onTap(0.8f,result,1225,274,1253,301,"猫头鹰 提示",100);

                result=mFairy.findPic("take back.png");
                mFairy.onTap(0.8f,result,"收回列表",100);

                //取消打招呼 加Q群 改名指引
                result=mFairy.findPic(662,295,756,346,new String []{"zu.png","ming.png","Q.png"});
                mFairy.onTap(0.8f,result,521,402,566,426,"家族事件",100);

                result=mFairy.findPic("leave1.png");
                mFairy.onTap(0.8f,result,"挑战成功或失败 离开",100);

                result=mFairy.findPic("free.png");
                mFairy.onTap(0.8f,result,"问询 首次免费",100);

                //驯服坐骑
                result=mFairy.findPic("buhuo.png");
                if(result.sim>0.8){
                    LtLog.e(mFairy.getLineInfo("进去捕获坐骑"));
                    setTaskName(1);
                    return;
                }

                //射击匪徒
                result=mFairy.findPic("sheji.png");
                if (result.sim>0.8){
                    mFairy.sleep(500);
                    mFairy.ranSwipe(637,358,400,420,500,100);
                    setTaskName(2);
                    return;
                }

                //星辰塔第一层
                result=mFairy.findPic("xingchen.png");
                if(result.sim>0.8f){
                    LtLog.e("进入星辰塔挑战");
                    setTaskName(3);
                    return;
                }

                result=mFairy.findPic("word guide lsll open.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,1237,30,1246,43,"任务结束",1000);
                    setTaskEnd();
                    return;
                }
            }

            //捕获坐骑
            @Override
            public void content_1() throws Exception {
                result=mFairy.findPic(989,589,1280,720,"go.png");
                mFairy.onTap(0.8f,result,"接受任务 前往",500);

                result = mFairy.findPic(700,340,951,508,"affirm.png");
                mFairy.onTap(0.8f,result,"驯服_确认",500);

                result=mFairy.findPic("leave.png");
                mFairy.onTap(0.8f,result,"离开副本",500);

                result = mFairy.findPic(700,340,951,508,"affirm.png");
                mFairy.onTap(0.8f,result,"确认",500);

                result=mFairy.findPic("chat continue1.png");
                mFairy.onTap(0.8f,result,"驯服_几秒后自动跳过",500);

                result=mFairy.findPic("guanbi.png");
                mFairy.onTap(0.8f,result,"点击任意区域关闭",500);

                result=mFairy.findPic("word get.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,1214,9,1270,64,"驯服坐骑——关闭",500);
                    setTaskName(0);
                    return;
                }
            }

            //冷琼山 击败匪徒
            @Override
            public void content_2() throws Exception {
                mFairy.sleep(500);
                //向右
                for(int i=0;i<5;i++){
                    mFairy.ranSwipe(637,358,737,358,1000,100);
                    result=mFairy.findPic("sheji.png");
                    if(result.sim>0.8){
                        mFairy.onTap(0.8f,result,"右 射击匪徒",500);
                    }else{
                        setTaskName(0);
                        LtLog.e("射击匪徒结束");
                    }
                }
                //向左
                for(int i=0;i<5;i++){
                    mFairy.ranSwipe(637,358,537,358,1000,100);
                    result=mFairy.findPic("sheji.png");
                    if(result.sim>0.8){
                        mFairy.onTap(0.8f,result,"左 射击匪徒",100);
                    }else{
                        setTaskName(0);
                        LtLog.e("射击匪徒结束");
                    }
                }
            }

            //新手 星辰塔
            @Override
            public void content_3() throws Exception {
                result=mFairy.findPic("continue.png");
                mFairy.onTap(0.8f,result,"继续挑战",500);

                result=mFairy.findPic("auto fight.png");
                mFairy.onTap(0.8f,result,"自动作战",500);

                result=mFairy.findPic("leave1.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,"离开星辰塔挑战",500);
                    LtLog.e(mFairy.getLineInfo("新手指引完成"));
                    setTaskEnd();
                }
            }
        }.taskContent(mFairy,"一键40级");
    }

    int overTime=0,picCount=0;
    String change="";

    //日常任务
    public void daily()throws Exception{
        new SingleTask(mFairy){
            @Override
            public void create() throws Exception {
                taskList=new ArrayList<String>();
                //清理背包
                if(AtFairyConfig.getOption("clear").equals("1")){
                    taskList.add("clear");
                }
                //日常主线
                if(AtFairyConfig.getOption("main").equals("1")){
                    taskList.add("main");
                }
                //星辰塔
                if(AtFairyConfig.getOption("xct").equals("1")){
                    taskList.add("xct");
                }
                //通天秘境
                if(AtFairyConfig.getOption("ttmj").equals("1")){
                    taskList.add("ttmj");
                }
                //龙山历练
                if(AtFairyConfig.getOption("lsll").equals("1")){
                    taskList.add("lsll");
                }
                //魔祖宝藏
                if(AtFairyConfig.getOption("mzbz").equals("1")){
                    taskList.add("mzbz");
                }

                //魔族宝藏类型
                if(!AtFairyConfig.getOption("treasure_type").equals("")){
                    if(AtFairyConfig.getOption("treasure_type").equals("1"))taskList.add("mzbz");
                    if(AtFairyConfig.getOption("treasure_type").equals("2"))taskList.add("daily_mzbz_update");
                }

                //科举乡试
                if(AtFairyConfig.getOption("kjxs").equals("1")){
                    taskList.add("kjxs");
                }
                //隐形密探
                if(AtFairyConfig.getOption("yxmt").equals("1")){
                    taskList.add("yxmt");
                }

                //赠送礼物
                if(!AtFairyConfig.getOption("sendObj").equals("") && !AtFairyConfig.getOption("sendObj").equals("0")){
                    taskList.add("zslw");
                }
                //本源争夺战
                if(AtFairyConfig.getOption("7592").equals("1")){
                    taskList.add("7592");
                }
                //雕像参拜
                if(AtFairyConfig.getOption("dxcb").equals("1")){
                    taskList.add("dxcb");
                }

                //家族签到
                if(AtFairyConfig.getOption("jzqd").equals("1")){
                    taskList.add("jzqd");
                }

                //家族捐献
                if(AtFairyConfig.getOption("jzjx").equals("1")){
                    taskList.add("jzjx");
                }

                util.back_city();
                setTaskName(0);
            }

            @Override
            public void content_0() throws Exception {
                if(taskList.size()==0){
                    LtLog.e(mFairy.getLineInfo("所有日常完成，没有任务了"));
                    setTaskEnd();
                    return;
                }
                overTime=0;
                daze=0;
                //清理背包
                if(taskList.get(0).equals("clear")){
                    daily_clear();
                    taskList.remove(0);
                    return;
                }

                //星辰塔
                if(taskList.get(0).equals("xct")){
                    daily_xct();
                    taskList.remove(0);
                    return;
                }
                //ttmj
                if(taskList.get(0).equals("ttmj")){
                    ttmj();
                    taskList.remove(0);
                    return;
                }
                //龙山历练
                if(taskList.get(0).equals("lsll")){
                    daily_lsll();
                    taskList.remove(0);
                    return;
                }
                //魔族宝藏
                if(taskList.get(0).equals("mzbz")){
                    daily_mzbz();
                    taskList.remove(0);
                    return;
                }
                //魔族宝藏
                if(taskList.get(0).equals("daily_mzbz_update")){
                    daily_mzbz_update();
                    taskList.remove(0);
                    return;
                }
                //科举乡试
                if(taskList.get(0).equals("kjxs")){
                    daily_kjxs();
                    taskList.remove(0);
                    return;
                }
                //隐形密探
                if(taskList.get(0).equals("yxmt")){
                    szmt();
                    taskList.remove(0);
                    return;
                }
                //赠送礼物
                if(taskList.get(0).equals("zslw")){
                    daily_zslw1();
                    taskList.remove(0);
                    return;
                }
                //本源争夺战
                if(taskList.get(0).equals("7592")){
                    szzyzd();
                    taskList.remove(0);
                    return;
                }

                //雕像参拜
                if(taskList.get(0).equals("dxcb")){
                    daily_dxcb();
                    taskList.remove(0);
                    return;
                }
                //家族签到
                if(taskList.get(0).equals("jzqd")){
                    daily_jzqd();
                    taskList.remove(0);
                    return;
                }
                //家族捐献
                if(taskList.get(0).equals("jzjx")){
                    daily_jzjx();
                    taskList.remove(0);
                    return;
                }
            }

        }.taskContent(mFairy,"日常任务");
    }

    //星辰塔
    public void daily_xct() throws  Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                setTaskName(2);
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);
//                result=mFairy.findPic(46,183,244,394,"single.png");
//                mFairy.onTap(0.7f,result,"除虫行动活动指引",Sleep);
//                if (result.sim>0.7f){
//                    setTaskName(3);return;
//                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 3, 160,214, 160,346, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single xingchenta.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                result=mFairy.findPic("saodang.png");
                mFairy.onTap(0.8f,result,"前往扫荡",1000);

                result=mFairy.findPic(1032,500,1122,548,"go.png");
                mFairy.onTap(0.8f,result,"前往",1000);

                result=mFairy.findPic("yijiansaodang.png");
                mFairy.onTap(0.8f,result,"一键扫荡",1000);


                result=mFairy.findPic(900,597,990,642,"start.png");
                if(result.sim>0.8f){
                    daze=util.initDaze();
                    mFairy.onTap(0.8f,result,"开始挑战",1000);
                }

                result=mFairy.findPic("affirm.png");
                mFairy.onTap(0.8f,result,"确定开始",1000);

                result=mFairy.findPic(703,585,1166,681,"continue.png");
                mFairy.onTap(0.8f,result,"继续挑战",1000);

                //发呆
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8){
                    util.initDaze();
                    change=mFairy.change(1213,362,1227,377);
                    if(change.equals("0,0")){
                        result=mFairy.findPic("auto.png");
                        mFairy.onTap(0.8f,result,"开始自动作战",1000);
                    }
                }

                result=mFairy.findPic("word btn max floor.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,1217,68,1236,88,"已达最高层，任务结束",1000);
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic(700,583,776,628,"leave1.png");
                if(result.sim>0.8) {
                    mFairy.onTap(0.8f,result,"星辰塔完成",500);
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic("word reset.png");
                if(result.sim>0.8) {
                    mFairy.onTap(1216,68,1237,86,"挑战次数不足，结束任务",1000);
                    setTaskEnd();
                    return;
                }
                if(daze>20){
                    LtLog.e(mFairy.getLineInfo("星辰塔超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }
        }.taskContent(mFairy,"星辰塔");
    }

    //龙山历练
    public void daily_lsll() throws  Exception{
        new SingleTask(mFairy) {
            int clearNum=1;//计次清理了几次背包(2次以上结束)
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);

                result=mFairy.findPic(49,184,251,386,new String[]{"word task lsll.png","word task lsll1.png"});
                mFairy.onTap(0.7f,result,"龙山历练指引",Sleep);
                if (result.sim>0.7f){
                    util.initDaze();
                    setTaskName(3);return;
                }
                mFairy.taskSlid(err, new int[]{1, 2, 4, 6}, 6, 125,384,136,200, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single longshanlilian.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                //已经接取任务
                result=mFairy.findPic("shangxian.png");
                if(result.sim>0.8){
                    LtLog.e(mFairy.getLineInfo("接取任务超过上限"));
                    util.close();
                }

                result=mFairy.findPic("word dailyInterface.png");
                mFairy.onTap(0.8f,result,1219,68,1236,87,"关闭日常界面",1000);

                result=mFairy.findPic("word back already full.png");
                if(result.sim>0.8){
                    LtLog.e(mFairy.getLineInfo("背包已满，清理后完成"));
                    util.clear1();
                    if(clearNum++>=2){
                        LtLog.e(mFairy.getLineInfo("背包已满，无法再清理，结束任务"));
                        setTaskEnd();
                        return;
                    }
                    setTaskName(0);
                    return;
                }

                if(daze>=10){
                    setTaskName(0);
                    LtLog.e(mFairy.getLineInfo("龙山历练超时，重置任务"));
                    return;
                }

                result=mFairy.findPic(new String[]{"pic conjure.png"});
                mFairy.onTap(0.8f,result,"施法",6000);

                result=mFairy.findPic("mubiao.png");
                if(result.sim>0.8f){
                    result1=mFairy.findPic(new String[]{"word dong.png","word zhong.png"});
                    if(result1.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("龙山历练寻路中.."));
                        mFairy.sleep(1000);
                    }
                    result1=mFairy.findPic("auto ing.png");
                    if(result1.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("龙山自动战斗.."));
                        mFairy.sleep(1000);
                    }
                    if(result1.sim<0.8f){
                        mFairy.onTap(0.8f,result,result.x+108,result.y-15,result.x+109,result.y-14,"目标指引1",200);
                        mFairy.onTap(0.8f,result,result.x+108,result.y-5,result.x+109,result.y-4,"目标指引2",2000);
                    }
                }

                result=mFairy.findPic(37,487,1267,565,"start1.png");
                mFairy.onTap(0.8f,result,"开启任务",1000);

                result=mFairy.findPic(611,395,642,468,"affirm.png");
                mFairy.onTap(0.8f,result,"点击确认",500);

                result=mFairy.findPic("use.png");
                mFairy.onTap(0.8f,result,"自动使用",500);

                result=mFairy.findPic("finish.png");
                mFairy.onTap(0.8f,result,"完成任务",500);

                result=mFairy.findPic("accept.png");
                mFairy.onTap(0.8f,result,"接受任务",500);

                result=mFairy.findPic(928,165,1002,510,"dialogue.png");
                result1=mFairy.findPic(1225,665,1269,713,"chat continue.png");
                if(result.sim>0.8f||result1.sim>0.8){
                    util.initDaze();
                    mFairy.onTap(0.75f,result,"回复NPC",500);
                }

                //打造武器
                result=mFairy.findPic("zao.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word warm.png");
                    mFairy.onTap(0.8f,result,"加热",1000);

                    result=mFairy.findPic("word make.png");
                    mFairy.onTap(0.8f,result,"打造",200);
                    mFairy.onTap(0.8f,result,"打造",200);
                    mFairy.onTap(0.8f,result,"打造",200);
                }

                result=mFairy.findPic("mi.png");
                if(result.sim>0.8f){
                    draw=0;y=0;
                    setTaskName(5);
                    return;
                }

                result=mFairy.findPic("word up.png");
                mFairy.onTap(0.8f,result,1240,675,1245,680,"亲密度提升",500);

                result=mFairy.findPic("word close.png");
                mFairy.onTap(0.8f,result,"关闭",500);

                result=mFairy.findPic(new String[]{"lsll.png"});
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,603,398,672,423,"龙山历练大环赠礼",100);
                    LtLog.e("龙山历练完成");
                    setTaskName(0);
                    setTaskEnd();
                    return;
                }
            }

            int draw=0,index=0;//翻牌的次数
            int y=0;
            public void content_4()throws Exception{
                //连连看界面 写完后我自己都看不懂了
                result=mFairy.findPic("mi.png");
                if(result.sim>0.8f){
                    //单数 x固定不变
                    //复数 y>x 后一张牌大于前一张牌
                    //index指第几张牌
                    index=0;
                    for(int i=1;i<=4;i++){
                        for(int n=1;n<=4;n++){
                            index++;
                            result=mFairy.findPic(281+(n-1)*163,92+(i-1)*158,348+(n-1)*163,158+(i-1)*158,"pic LinkGame.png");
                            if(result.sim>0.7f){
                                //单数
                                if(draw%2==0){
                                    if(index==8){
                                        mFairy.onTap(0.7f,result,822,257,833,269,"翻牌 单",3000);
                                    }else{
                                        mFairy.onTap(0.7f,result,"翻牌 单",3000);
                                    }
                                    draw++;
                                }else{
                                    //复数
                                    if(index>y){
                                        if(index==8){
                                            mFairy.onTap(0.7f,result,822,257,833,269,"翻牌 复",3000);
                                        }else{
                                            mFairy.onTap(0.7f,result,"翻牌 复",3000);
                                        }
                                        draw++;
                                        y=index;
                                    }else{
                                        continue;
                                    }
                                    //大于16 从第一张牌开始
                                    if(y>=16){
                                        y=0;
                                    }
                                }
                                return;
                            }
                        }
                    }
                }else{
                    setTaskName(3);
                    LtLog.e(mFairy.getLineInfo("龙山解密完成"));
                    return;
                }
            }

            List<FindResult> picList=null;
            public void content_5()throws Exception{
                result=mFairy.findPic("mi.png");
                if(result.sim>0.8f){
                    try{
                        //单数 x固定不变
                        //复数 y>x 后一张牌大于前一张牌
                        //index指第几张牌
                        index=0;
                        picList=new ArrayList<FindResult>();
                        for(int i=1;i<=4;i++){
                            for(int n=1;n<=4;n++){
                                index++;
                                result=mFairy.findPic(281+(n-1)*163,92+(i-1)*158,348+(n-1)*163,158+(i-1)*158,"pic LinkGame.png");
                                if(result.sim>0.7f){
                                    picList.add(result);
                                }
                            }
                        }
                        int size=picList.size();
                        if(size%2==0){
                            //单牌
                            result=picList.get(0);
                            mFairy.onTap(0.7f,result,result.x+40,result.y+40,result.x+41,result.y+41,"单牌",2000);

                        }else{
                            //双牌
                            if(y>=size){
                                y=0;
                            }
                            result=picList.get(y);
                            mFairy.onTap(0.7f,result,result.x+40,result.y+40,result.x+41,result.y+41,"双牌",2000);
                            y++;
                        }
                    }catch(Exception e){
                        LtLog.e(mFairy.getLineInfo("捕获到异常"));
                        LtLog.e(mFairy.getLineInfo(e.getMessage()));
                    }

                }else{
                    setTaskName(3);
                    LtLog.e(mFairy.getLineInfo("龙山解密完成"));
                    return;
                }
            }
            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic(611,395,642,468,"affirm.png");
                mFairy.onTap(0.8f,result,"点击确认",500);

                result=mFairy.findPic(611,379,676,457,"word close.png");
                mFairy.onTap(0.8f,result,"点击关闭",500);
                /*try{
                    result=mFairy.findMultiColor(57,143,85,175,0.9f,"101,216,221",
                            "0|5|100,212,217&0|11|102,214,218&0|14|104,211,215&4|0|98,220,227&8|0|93,231,239&12|0|104,204,207&4|7|103,209,213&4|14|107,202,204&13|14|107,201,204");
                    mFairy.onTap(0.85f,result,"切换到任务页",1000);
                }catch (Exception e){
                    LtLog.e(mFairy.getLineInfo("获取出错"));
                    LtLog.e(mFairy.getLineInfo(e.getMessage()));
                }*/
                super.inOperation();
            }
        }.taskContent(mFairy,"龙山历练");
    }

    //魔祖宝藏
    public void daily_mzbz() throws  Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在副本中，"));
                    setTaskName(3);
                    daze=util.initDaze();
                    return;
                }
                setTaskName(2);
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);

//                result=mFairy.findPic(5,201,217,366,"word huodong deal worm action.png");
//                mFairy.onTap(0.7f,result,"除虫行动活动指引",Sleep);
//                if (result.sim>0.7f){
//                    setTaskName(3);return;
//                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 3, 160,214, 160,346, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single mozubaozang.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                result=mFairy.findPic("get goods.png");
                mFairy.onTap(0.8f,result,629,404,638,414,"获得物品",500);

                result=mFairy.findPic(615,112,655,146,"bird tip.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,1081,9,1096,26,"指引打开随身背包，重置任务",500);
                    setTaskName(0);
                    return;
                }

                //背包界面
                result=mFairy.findPic(new String[]{"pack1.png","pack2.png"});
                if(result.sim>0.8f){
                    LtLog.e("****背包里面****");
                    result=mFairy.findPic(639,118,1168,417,new String[]{"cangbaotu.png","cbtu.png"});
                    mFairy.onTap(0.8f,result,"背包_选中藏宝图",1000);
//                    picCount=picCount(0.8f,result,"Treasure");
//                    if(picCount==1){
//
//                    }

                    result=mFairy.findPic(660,161,1157,354,"use.png");
                    mFairy.onTap(0.8f,result,"背包_使用藏宝图",1000);
                }

                //副本界面
                result=mFairy.findPic("fuben.png");
                if(result.sim>0.8){
                    if(daze>6){
                        mFairy.onTap(132,272,146,287,"点击副本指引",500);
                    }
                }

                result=mFairy.findPic(698,534,769,582,"leave1.png");
                mFairy.onTap(0.8f,result,"副本挑战成功,离开",500);

                result=mFairy.findPic("word to strong.png");
                if(result.sim>0.8){
                    LtLog.e("没有藏宝图,任务结束");
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic(910,458,1046,498,"use1.png");
                mFairy.onTap(0.8f,result,"立即使用",1000);

                //超时处理
                if(daze>15){
                    LtLog.e(mFairy.getLineInfo("魔族宝藏超时，重置任务前往"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                super.inOperation();
            }
        }.taskContent(mFairy,"魔祖宝藏");
    }

    //魔祖宝藏  升级版
    public void daily_mzbz_update() throws  Exception{
        new SingleTask(mFairy) {
            int ran=0;//在背包界面滑动的次数
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                ran=0;
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在副本中，"));
                    setTaskName(2);
                    daze=util.initDaze();
                    return;
                }
                setTaskName(1);
            }

            public void content_1()throws Exception{
                daze=util.initDaze();
                overtime(20,0);
                result = mFairy.findPic("pack.png");
                mFairy.onTap(0.8f, result, "打开背包", 2000);

                //新手引导
                result=mFairy.findPic(615,112,655,146,"bird tip.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,1081,9,1096,26,"指引打开随身背包，重置任务",500);
                    setTaskName(0);
                    return;
                }

                //背包界面
                result=mFairy.findPic("pack1.png");
                if (result.sim> 0.8){
                    result=mFairy.findPic(362,205,431,281,"pic package treasure map select.png");
                    mFairy.onTap(0.8f,result,846,219,858,231,"已选中藏宝图，点击使用",1000);
                    if(result.sim>0.8f){
                        setTaskName(2);
                        return;
                    }
                    //没有选中藏宝图
                    else{
                        result=mFairy.findPic(658,142,1152,589,"cangbaotu.png");
                        mFairy.onTap(0.8f,result,"背包_选中藏宝图",1000);
                        if (result.sim < 0.9f) {
                            //向上滑动
                            mFairy.ranSwipe(912, 543, 912, 150, 500, 1500l,0);
                            result=mFairy.findPic(670,142,741,236,"pic packet lock.png");
                            if(result.sim>0.8f){
                                mFairy.onTap(1218,70,1234,84,"清理背包，滑到底部，清理完成，关闭背包界面",1000);
                                setTaskEnd();
                                return;
                            }
                            if(ran++>=5){
                                mFairy.onTap(1218,70,1234,84,"没有藏宝图，关闭背包界面",1000);
                                setTaskEnd();
                                return;
                            }
                            LtLog.e(mFairy.getLineInfo("向上滑动一次寻找="+ran));
                        }
                    }
                }
            }

            public void content_2() throws Exception {
                result=mFairy.findPic("get goods.png");
                mFairy.onTap(0.8f,result,629,404,638,414,"获得物品",500);

                result=mFairy.findPic("pack1.png");
                mFairy.onTap(0.8f,result,1221,71,1232,83,"关闭背包",1000);

                //副本界面
                result=mFairy.findPic("fuben.png");
                if(result.sim>0.8){
                    if(daze>6){
                        mFairy.onTap(132,272,146,287,"点击副本指引",500);
                    }
                }

                result=mFairy.findPic(698,534,769,582,"leave1.png");
                mFairy.onTap(0.8f,result,"副本挑战成功,离开",500);

                result=mFairy.findPic("word to strong.png");
                if(result.sim>0.8){
                    LtLog.e("没有藏宝图,任务结束");
                    setTaskEnd();
                    return;
                }

                //超时处理
                if(daze>20){
                    LtLog.e(mFairy.getLineInfo("魔族宝藏超时，重置任务前往"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("pack full.png");
                if(result.sim>0.8) {
                    LtLog.e(mFairy.getLineInfo("背包已满,选择清理"));
                    mFairy.onTap(0.8f, result, 519, 404, 571, 429, "选中不分解", 1000);
                    util.clear1();
                    ran=0;
                    setTaskName(1);
                    return;
                }

                result=mFairy.findPic(942,384,1012,442,"cangbaotu.png");
                mFairy.onTap(0.8f,result,966,476,974,485,"藏宝图，立即使用",1000);
                if(result.sim>0.8f){
                    setTaskName(2);
                    daze=util.initDaze();
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"魔祖宝藏 升级版 ");
    }

    //科举乡试
    public void daily_kjxs() throws  Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                setTaskName(2);
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);
//                result=mFairy.findPic(5,201,217,366,"word huodong deal worm action.png");
//                mFairy.onTap(0.7f,result,"除虫行动活动指引",Sleep);
//                if (result.sim>0.7f){
//                    setTaskName(3);return;
//                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 3, 160,214, 160,346, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single kejuxiangshi.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                result=mFairy.findPic("safety area.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f, result, "前往安全区", 1000);
                    setTaskName(0);
                    return;
                }

                if(daze>10){
                    mFairy.getLineInfo("科举乡试发呆超时，重置任务");
                    setTaskName(1);
                }

                //开始答题
                result=mFairy.findPic(902,532,966,581,"start.png");
                mFairy.onTap(0.8f, result, "开始答题", 100);

                result=mFairy.findPic("keju.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,531,315,552,335, "答题中 所有选择C", 250);
                    util.initDaze();
                }

                result=mFairy.findPic("exit.png");
                mFairy.onTap(0.8f, result, "答题完成", 1000);

                result=mFairy.findPic("xiangshiwancheng.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f, result,615,394,659,421,"完成奖励", 1000);
                    LtLog.e("科举乡试完成");
                    setTaskEnd();
                    return;
                }
            }
        }.taskContent(mFairy,"科举乡试");
    }

    //赠送礼物
    public void daily_zslw1() throws  Exception{
        new SingleTask(mFairy) {
            String sendObj="";//选择送礼物给谁
            int find=0;//查找了计次(3次以上没找到结束)
            public void create()throws Exception{
                if(!AtFairyConfig.getOption("sendObj").equals("")){
                    sendObj=AtFairyConfig.getOption("sendObj");
                }
            }

            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                setTaskName(2);
                find=0;
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);

//                result=mFairy.findPic(5,201,217,366,"word huodong deal worm action.png");
//                mFairy.onTap(0.7f,result,"除虫行动活动指引",Sleep);
//                if (result.sim>0.7f){
//                    setTaskName(3);return;
//                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 3, 160,214, 160,346, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single zengsongliwu.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                //超时处理
                if(daze>10){
                    LtLog.e(mFairy.getLineInfo("赠送礼物发呆超时，重置任务"));
                    setTaskName(0);
                    return;
                }

                result=mFairy.findPic("word dailyInterface.png");
                mFairy.onTap(0.8f,result,1219,68,1236,87,"关闭日常界面",1000);

                result=mFairy.findPic(601,396,677,438,"affirm.png");
                mFairy.onTap(0.8f,result,"确认",200);

                result=mFairy.findPic("accept1.png");
                mFairy.onTap(0.8f,result,"接受任务",1000);

                result=mFairy.findPic("finish2.png");
                mFairy.onTap(0.8f,result,"完成任务",1000);

                result = mFairy.findPic( "yiwenlu.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(10,344,1267,476,"word send "+sendObj+".png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,"选择送礼物的对象",1000);
                    }else{
                        //向右滑动 0 1 2
                        if(find++>=3){
                            LtLog.e(mFairy.getLineInfo("没有该对象，赠送礼物结束"));
                            setTaskEnd();
                            return;
                        }
                        LtLog.e(mFairy.getLineInfo("寻找送礼对象"));
                        mFairy.ranSwipe(1000,512,500,512,500,1500l,0);
                        util.initDaze();
                    }
                }

                result = mFairy.findPic("visit.png");
                mFairy.onTap(0.8f,result,"前往拜访",1000);

                result = mFairy.findPic(183,174,289,554,"send.png");
                mFairy.onTap(0.8f,result,"送礼物",1000);

                result = mFairy.findPic("send1.png");
                picCount=picCount(0.8f,result,"send");
                if(picCount<=2){
                    mFairy.onTap(0.8f,result,"立即赠送",1000);
                }else{
                    mFairy.onTap(1212,171,1229,188,"赠送完成",1000);
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic("send2.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,1215,175,1229,188,"礼物不足或者赠送完成",1000);
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic("xueying.png");
                result1 = mFairy.findPic("send1.png");
                if (result.sim > 0.8f&& result1.sim < 0.8f){
                    LtLog.e("没有赠送按钮结束");
                    setTaskEnd();return;
                }
            }
        }.taskContent(mFairy,"赠送礼物");
    }

    //清理背包
    public void daily_clear()throws Exception{
        util.clear1();
    }

    //家族签到
    public void daily_jzqd() throws  Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                daze=util.initDaze();
                result=mFairy.findPic("word haodashi.png");
                if(result.sim>0.8f){
                    setTaskName(3);
                    return;
                }
                setTaskName(2);
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);

//                result=mFairy.findPic(5,201,217,366,"word huodong deal worm action.png");
//                mFairy.onTap(0.7f,result,"除虫行动活动指引",Sleep);
//                if (result.sim>0.7f){
//                    setTaskName(3);return;
//                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 3, 160,214, 160,346, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single jiazuqiandao.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                if(daze>15){
                    LtLog.e(mFairy.getLineInfo("家族签到超时，重置任务"));
                    setTaskName(0);
                    return;
                }
                result=mFairy.findPic("hao.png");
                mFairy.onTap(0.8f,result,"剧情提示",500);

                result=mFairy.findPic("word haodashi.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(172,390,310,568,"family sign in.png");
                    mFairy.onTap(0.8f,result,"点击签到",500);
                }

                result=mFairy.findPic("fu.png");
                mFairy.onTap(0.8f,result,"祈福",500);

                result=mFairy.findPic(576,582,647,621,"lingqu.png");
                mFairy.onTap(0.8f,result,"领取奖励",500);

                result=mFairy.findPic("share.png");
                mFairy.onTap(0.8f,result,933,246,965,279,"家族签到完成",500);

                result=mFairy.findPic("buji.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,636,406,640,412,"领取家族补给箱",500);
                    setTaskEnd();
                    return;
                }
            }
        }.taskContent(mFairy,"家族签到");
    }

    //家族捐献
    public void daily_jzjx() throws  Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                setTaskName(2);
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);

//                result=mFairy.findPic(5,201,217,366,"word huodong deal worm action.png");
//                mFairy.onTap(0.7f,result,"除虫行动活动指引",Sleep);
//                if (result.sim>0.7f){
//                    setTaskName(3);return;
//                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 3, 160,214, 160,346, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single jiazujuanxian.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                if(daze>10){
                    LtLog.e(mFairy.getLineInfo("家族捐献超时，重置任务"));
                    setTaskName(0);
                    return;
                }
                result=mFairy.findPic("gongxian.png");
                mFairy.onTap(0.8f,result,713,439,726,449,"确认提示",500);

                result=mFairy.findPic("juanxian.png");
                if(result.sim>0.8f){
                    util.initDaze();
                    mFairy.onTap(0.8f,result,"低级捐献",1000);
                }

                result=mFairy.findPic("finish1.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,1137,55,1169,90,"家族捐献完成",500);
                    setTaskEnd();
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("word hint bangyin.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,533,439,556,459,"绑银不足，无法捐献，结束任务",1000);
                    setTaskEnd();
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"家族捐献");
    }

    //雕像参拜
    public void daily_dxcb()throws  Exception{
        new SingleTask(mFairy) {
            public void content_0() throws Exception {
                util.close();
                visitIndex=1;
                setTaskName(2);
            }

            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single diaoxiangcanbai.png",0);
                if (ret==1){
                    mFairy.initDaze();
                    visitIndex=1;
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            int visitIndex=1,group=0;//第几个雕像(一共有7个位置，不是每个位置都有雕像)，第几轮(执行两轮，两轮后不管是否参拜完三个都结束)
            boolean initBacak=false;//是否回到起点
            public void content_3() throws Exception {
                if(daze>50){
                    setTaskName(0);
                    LtLog.e(mFairy.getLineInfo("参拜雕像超时，重置任务"));
                    return;
                }
                int x1=0,y1=0,x2=0,y2=0;
                if(visitIndex>=1&&visitIndex<=3){
                    y1=256;x2=750;y2=200;
                }
                switch(visitIndex){
                    case 1:
                        x1=397;
                        break;
                    case 2:
                        x1=380;
                        break;
                    case 3:
                        x1=363;
                        break;
                    case 4:
                        x1=396;y1=240;x2=385;y2=164;
                        break;
                    case 5:
                        x1=380;y1=239;x2=471;y2=195;
                        break;
                    case 6:
                        x1=364;y1=239;x2=509;y2=211;
                        break;
                    case 7:
                        x1=346;y1=238;x2=451;y2=451;
                        break;
                }
                x1=(int)(x1 *1.3323+y1 *0.0098+309.8233);
                y1=(int)(x1 *-0.0+y1 *-1.3333+686.6667);
                result=mFairy.findPic("map down.png");
                result1=mFairy.findPic("word place xdc.png");
                if(result.sim>0.9f&&result1.sim<0.70f){
                    LtLog.e(mFairy.getLineInfo("不在夏都城，重新启动任务"));
                    setTaskName(0);
                    return;
                }

                result=mFairy.findPic("word 2.5D.png");
                mFairy.onTap(0.8f,result,"切换3D视角",500);

                result=mFairy.findPic(611,395,642,468,"affirm.png");
                mFairy.onTap(0.8f,result,"点击确认",100);

                result=mFairy.findPic(496,387,606,538,"cancel.png");
                mFairy.onTap(0.8f,result,"取消",1000);


                result= mFairy.findPic(942,0,1209,291,new String[]{"close.png","close4.png","close3.png","close6.png"});
                mFairy.onTap(0.8f,result,"雕像参拜 关闭",1000);

                result= mFairy.findPic(1209,0,1280,291,new String[]{"close.png","close4.png","close3.png","close6.png"});
                result1=mFairy.findPic("world map.png");
                if(result.sim>0.8f&&result1.sim<0.8f){
                    mFairy.onTap(0.8f,result,"雕像参拜 关闭",1000);
                }

                result=mFairy.findPic("word activity hint.png");
                mFairy.onTap(0.8f,result,967,172,986,194,"关闭活动提醒",1000);

                //有地图 点击坐标前往
                result=mFairy.findPic("world map.png");
                result1=mFairy.findPic(408,95,449,142,"close left.png");
                if(result.sim>0.8&&result1.sim<0.8f){
                    if(initBacak){
                        mFairy.onTap(881,360,882,361,"回到起始点",500);
                        mFairy.onTap(881,360,882,361,"回到起始点",500);
                        initBacak= false;
                    }else{
                        visitIndex++;
                        mFairy.onTap(x1,y1,x1+1,y1+1,"前往雕像"+visitIndex,500);
                        mFairy.onTap(x1,y1,x1+1,y1+1,"前往雕像"+visitIndex,500);
                        initBacak= true;
                    }
                    result = mFairy.findPic(890,3,1275,235,new String[]{"close.png","close3.png"});
                    mFairy.onTap(0.8f,result,"关闭区域地图",2000);
                }

                //参拜完7个
                if(visitIndex>7){
                    setTaskName(0);
                    LtLog.e(mFairy.getLineInfo("参拜完7个，回任务栏查看完成状态"));
                    //1 2
                    if(++group>=2){
                        LtLog.e(mFairy.getLineInfo("雕像数量不够，无法完成任务"));
                        setTaskEnd();
                        return;
                    }
                    return;
                }

                //不在寻路
                result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                if(result.sim<0.8f){
                    result=mFairy.findPic(408,95,449,142,"close left.png");
                    result1=mFairy.findPic("word visit.png");

                    //有左边的展开
                    if(result.sim>0.8f){
                        if(result1.sim>0.8f){
                            mFairy.onTap(0.8f,result1,"参拜雕像"+visitIndex,500);
                        }else{
                            mFairy.onTap(0.8f,result,"关闭左侧页面",500);
                        }
                    }else{
                        mFairy.onTap(x2,y2,x2+1,y2+1,"选择雕像参拜",1000);

                        result=mFairy.findPic("map down.png");
                        mFairy.onTap(0.8f,result,1168,92,1185,107,"打开区域地图",1000);
                        return;
                    }
                }
            }
        }.taskContent(mFairy,"雕像参拜");
    }

    //隐形密探
    public void szmt() throws Exception {
        new SingleTask(mFairy) {
            @Override
            public void content_0() throws Exception {
                util.close();
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task3.png");
                mFairy.onTap(0.8f,result,"切换任务栏",Sleep);

                result = mFairy.findPic("lianmeng.png");
                mFairy.onTap(0.8f,result,"联盟栏",Sleep);

//                //联盟
//                result=mFairy.findPic(49,178,179,390,"word task yxmt.png");
//                mFairy.onTap(0.7f,result,"隐形密探指引",1000);
//                if(result.sim>0.7f){
//                    daze=util.initDaze();
//                    setTaskName(3);
//                    return;
//                }

                setTaskName(1);return;
            }

            int mtCount = 0;

            public void content_1() throws Exception {
                long dazeTime = mFairy.mMatTime(1189,151,76,23, 0.9f);
                LtLog.e(mFairy.getLineInfo("发呆时间=" + dazeTime));

                result = mFairy.findPic("task3.png");
                mFairy.onTap(0.8f,result,"切换任务栏",Sleep);

                result = mFairy.findPic("lianmeng.png");
                mFairy.onTap(0.7f,result,"联盟栏",Sleep);



                result = mFairy.findPic(40,163,269,271, new String[]{"qbzz.png","qbzz2.png"});
                mFairy.onTap(0.7f, result, "左侧隐形密探", Sleep);
                LtLog.e("***"+result.sim);
                if (result.sim >= 0.7f) {
                   // result1 = mFairy.findPic(47,261,215,399, "qingbao.png");
                    if (result.sim > 0.7f) {
                        LtLog.e(mFairy.getLineInfo("密探前往"));
                        if (mtCount == 0) {//116,227   234,311,240,314   235,334,241,337
                            mFairy.onTap(0.7f, result, result.x + 127, result.y + 85, result.x + 124, result.y + 87, "第1个前往", Sleep);
                        }
                        if (mtCount == 1) {
                            mFairy.onTap(0.7f, result, result.x + 127, result.y + 108, result.x + 128, result.y + 111, "第2个前往", Sleep);
                        }
                        if (mtCount == 2) {
                            mFairy.onTap(0.7f, result, result.x + 127, result.y + 132, result.x + 128, result.y + 135, "第3个前往", Sleep);
                        }
                        if (mtCount == 3) {
                            mFairy.onTap(0.7f, result, result.x + 117, result.y + 156, result.x + 128, result.y + 159, "第4个前往", Sleep);
                        }
                    }
                }else {
                    mFairy.ranSwipe(66,316,71,203,1000,1500);
                }

                if (dazeTime > 10) {
                    mFairy.initMatTime();
                    mtCount++;
                    if (mtCount >= 4) {
                        mtCount = 0;
                    }
                    mFairy.initMatTime();
                    result1 = mFairy.findPic("meng.png");
                    result = mFairy.findPic(43,171,230,421,"qbzz.png");
                    LtLog.e("*-****+"+result1.sim);
                    if (result.sim < 0.7f && result1.sim>0.8f) {
                        LtLog.e(mFairy.getLineInfo("左侧没有密探结束"));
                        setTaskEnd();
                        return;
                    }else {
                        setTaskName(0);return;
                    }
                }

                result=mFairy.findPic("word yxmt get.png");
                util.onTap(0.8f,result,"接取隐形密探任务",1000);

                result=mFairy.findPic("word yxmt hand in.png");
                util.onTap(0.8f,result,"交付隐形密探任务",1000);

                result=mFairy.findPic("cutting.png");
                util.onTap(0.8f,result,"打听情报",1000);

                result=mFairy.findPic("yes.png");
                util.onTap(0.8f,result,"确认",1000);

                result = mFairy.findPic( "death.png");
                mFairy.onTap(0.8f, result, "复活", Sleep);

            }
        }.taskContent(mFairy, "隐形密探任务中");
    }

    //本源争夺
    public void szzyzd() throws Exception {
        new SingleTask(mFairy) {
            @Override
            public void inOperation() throws Exception {
                super.inOperation();
                result = mFairy.findPic("jiaofu.png");
                mFairy.onTap(0.8f, result, "交付资源争夺任务", Sleep);
                if (result.sim > 0.8f) {
                    if (AtFairyConfig.getOption("szzy").equals("1")) {
                        stopCount++;
                        if (stopCount >= 3) {
                            LtLog.e(mFairy.getLineInfo("完成3次结束"));
                            setTaskEnd();
                            return;
                        }
                    }
                }
                result = mFairy.findPic("hand.png");
                mFairy.onTap(0.7f, result, "采集资源", Sleep);
                if (result.sim > 0.7f) {
//                    result1 = mFairy.findPic("wfdq.png");
//                    if (result1.sim > 0.8f) {
//                        zyCount++;
//                        if (zyCount > 4) {
//                            zyCount = 0;
//                        }
//                        LtLog.e(mFairy.getLineInfo("无法采集"));
//                    } else {
//                        Thread.sleep(10000);
//                    }
                    Thread.sleep(10000);
                    //mFairy.initMatTime();
                }
                result=mFairy.findPic("yes.png");
                mFairy.onTap(0.8f,result,"确认",1000);

                result = mFairy.findPic( "death.png");
                mFairy.onTap(0.8f, result, "复活", Sleep);
                if (result.sim > 0.8f) {
                    zyCount++;
                    if (zyCount > 4) {
                        zyCount = 0;
                    }
                }
            }

            public void content_0() throws Exception {
                util.close();
                setTaskName(1);return;
            }

            int zyCount = 0, stopCount = 0;

            public void content_1() throws Exception {
                result = mFairy.findPic("task3.png");
                mFairy.onTap(0.8f,result,"切换任务栏",Sleep);

                result = mFairy.findPic("lianmeng.png");
                mFairy.onTap(0.7f,result,"联盟栏",2000);

                result1 = mFairy.findPic(41,164,271,377,"byzd.png");
                if (picCountS(0.7f, result1, "没有资源争夺") > 30) {
                    LtLog.e(mFairy.getLineInfo("没有资源争夺结束"));
                    setTaskEnd();
                    return;
                }// 130,198  248,290,254,296            121 185       238 281
                mFairy.onTap(0.7f, result1,  result1.x+118,result1.y+92,result1.x+124,result1.y+98,"左侧资源争夺", 5000);
                if (result1.sim <0.7f){
                    mFairy.ranSwipe(71,203,66,316,1000,1500);
                }


                result = mFairy.findPic(36,320,256,393,"byzd.png");
                if(result.sim>0.7f){
                    LtLog.e(mFairy.getLineInfo("资源争夺往上滑"));
                    mFairy.ranSwipe(124,372,125,247,500,1000l,1);
                    return;
                }


                    result = mFairy.findPic("benyuan.png");
                if (result.sim > 0.8f) {
                    LtLog.e(mFairy.getLineInfo("本源界面"));
                    if (zyCount == 0) {
                        result = mFairy.findPic(929,140,1096,206, new String[]{"guard.png","baphu.png"});
                        if (result.sim < 0.8f) {
                            mFairy.onTap(999,168,1010,178, "前往第一个", Sleep);
                            setTaskName(2);
                            return;
                        } else {
                            zyCount++;
                        }
                    }
                    if (zyCount == 1) {
                        result = mFairy.findPic(940,230,1091,292, new String[]{"guard.png","baphu.png"});
                        if (result.sim < 0.8f) {
                            mFairy.onTap(1010,260,1020,267, "前往第2个", Sleep);
                            setTaskName(2);
                            return;
                        } else {
                            zyCount++;
                        }
                    }
                    if (zyCount == 2) {
                        result = mFairy.findPic(935,311,1094,383, new String[]{"guard.png","baphu.png"});
                        if (result.sim < 0.8f) {
                            mFairy.onTap(992,338,1002,347, "前往第3个", Sleep);
                            setTaskName(2);
                            return;
                        } else {
                            zyCount++;
                        }
                    }
                    if (zyCount == 3) {
                        result = mFairy.findPic(931,400,1094,463, new String[]{"guard.png","baphu.png"});
                        if (result.sim < 0.8f) {
                            mFairy.onTap(989,426,1003,435, "前往第4个", Sleep);
                            setTaskName(2);
                            return;
                        } else {
                            zyCount++;
                        }
                    }
                    if (zyCount == 4) {
                        result = mFairy.findPic(931,481,1093,553, new String[]{"guard.png","baphu.png"});
                        if (result.sim < 0.8f) {
                            mFairy.onTap(998,515,1014,526, "前往第5个", Sleep);
                            setTaskName(2);
                            return;
                        } else {
                            zyCount = 0;
                        }
                    }
                }
            }

            public void content_2() throws Exception {
                long dazeTime = mFairy.mMatTime(1192,152,79,19 , 0.9f);
                LtLog.e(mFairy.getLineInfo("发呆时间=" + dazeTime));
                if (dazeTime > 5) {
                    mFairy.initMatTime();
                    result=mFairy.findPic("pic yjzl enter.png");
                    mFairy.onTap(0.8f,result,1173,95,1182,104,"打开地图",2000);
                    result=mFairy.findPic(542,282,779,403,"peoper.png");
                    mFairy.onTap(0.8f,result,result.x+1,result.y+14,result.x+2,result.y+16,"前往挖晶石",2000);
                    mFairy.onTap(0.8f,result,1225,40,1237,53,"关闭",2000);
                   // mFairy.ranSwipe(192,589, 111,502, 4000, (long) 1000, 2);
                    setTaskName(1);
                    return;
                }
            }
        }.taskContent(mFairy, "资源争夺中");
    }

    //通天秘境
    public void ttmj() throws  Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                setTaskName(2);
            }

            /**
             * 如果任务已经接取，不需要继续执行工具类中找任务方法，直接开始setTaskName(3)
             * @throws Exception
             */
            public void content_1() throws Exception {
                if (overtime(8, 2)) return;
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"显示任务",Sleep);

                result = mFairy.findPic("task lan.png");
                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);
//                result=mFairy.findPic(46,183,244,394,"single.png");
//                mFairy.onTap(0.7f,result,"除虫行动活动指引",Sleep);
//                if (result.sim>0.7f){
//                    setTaskName(3);return;
//                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 3, 160,214, 160,346, 1000, 1500);
            }

            /**
             * 寻找任务并且前往
             * @throws Exception
             */
            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("ttmj.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            /**
             * 任务执行过程
             * @throws Exception
             */
            public void content_3() throws Exception {
                result=mFairy.findPic("saodang.png");
                mFairy.onTap(0.8f,result,"前往扫荡",1000);

             /*   result=mFairy.findPic(1032,500,1122,548,"go.png");
                mFairy.onTap(0.8f,result,"前往",1000);

                result=mFairy.findPic("yijiansaodang.png");
                mFairy.onTap(0.8f,result,"一键扫荡",1000);*/

                result=mFairy.findPic(942,614,1136,693,"start.png");
                if(result.sim>0.8f){
                    daze=util.initDaze();
                    mFairy.onTap(0.8f,result,"开始挑战",1000);
                }

                result=mFairy.findPic(601,552,1100,673,"continue.png");
                mFairy.onTap(0.8f,result,"继续挑战",1000);

                //发呆
                result=mFairy.findPic("leave2.png");
                if(result.sim>0.8){
                    result=mFairy.findPic("auto.png");
                    mFairy.onTap(0.8f,result,"开始自动作战",1000);
                }

                result=mFairy.findPic("zuigao.png");
                if(result.sim>0.8f){
                   // mFairy.onTap(0.8f,result,1217,68,1236,88,"已达最高层，任务结束",1000);
                    LtLog.e("最高层结束");
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic("zaici.png");
                mFairy.onTap(0.8f,result,"失败再次挑战",1000);
               /* result=mFairy.findPic("zuigao.png");
                if(result.sim>0.8f) {

                    setTaskEnd();
                    return;
                }*/

                result=mFairy.findPic(new String[]{"zaici voer.png","num.png"});
                if(result.sim>0.8) {
                    LtLog.e("没次数了 结束");
                    //mFairy.onTap(1216,68,1237,86,"挑战次数不足，结束任务",1000);
                    setTaskEnd();
                    return;
                }
                if(daze>20){
                    LtLog.e(mFairy.getLineInfo("通天秘境超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }
        }.taskContent(mFairy,"通天秘境");
    }

    boolean rebuild=false;//原地复活
    @Override
    public void inOperation() throws Exception {
        daze=util.dazeTime();
        result=mFairy.findPic("fuhuo.png");
        if(result.sim>0.8){
            LtLog.e("被野怪或其他玩家打死，选择复活方式后，继续任务");
            if(rebuild){
                mFairy.onTap(0.8f,result,768,359,790,380,"原地复活",500);
            }else{
                mFairy.onTap(0.8f,result,489,351,504,365,"复活点复活",500);
            }
            setTaskName(0);
            return;
        }

        result=mFairy.findPic("finish.png");
        mFairy.onTap(0.8f,result,"完成任务",1000);

        result=mFairy.findPic("accept.png");
        mFairy.onTap(0.8f,result,"接受任务",1000);

        result=mFairy.findPic(362,240,477,335,"pic hint.png");
        if(result.sim>0.8f){
            //确定 复活
            result=mFairy.findPic(new String[]{"word hint rebuild.png"});
            if(result.sim>0.8f){
                result=mFairy.findPic(696,345,759,480,"affirm.png");
                mFairy.onTap(0.8f,result,"确定",1000);
            }

            result=mFairy.findPic(500,345,603,480,"cancel.png");
            mFairy.onTap(0.8f,result,"取消",500);
        }

        result=mFairy.findPic("pic open task.png");
        mFairy.onTap(0.8f,result,"打开任务列表页",1000);

        result=mFairy.findPic("task list1.png");
        mFairy.onTap(0.8f,result,"切换到任务列表",1000);

        result=mFairy.findPic(908,452,1109,536,"auto wear.png");
        mFairy.onTap(0.8f,result,"捡到装备，或者自动使用",1000);

        //自动寻路中
        result=mFairy.findPic(new String[]{"word find.png","word way.png"});
        if(result.sim>0.8){
            LtLog.e(mFairy.getLineInfo("自动寻路中"));
            mFairy.initMatTime();
            err=0;
        }
        //传送
        result=mFairy.findPic(new String[]{"pic load.png","pic transfer.png"});
        if(result.sim>0.8f){
            LtLog.e(mFairy.getLineInfo("地图传送中"));
            mFairy.initMatTime();
            err=0;
        }
    }

    //线上测试
    void lineTest() throws Exception{
        new SingleTask(mFairy){
            @Override
            public void create() throws Exception {
                taskList=new ArrayList();
                if(AtFairyConfig.getOption("killGame").equals("1")){
                    taskList.add("killGame");
                }
                if(AtFairyConfig.getOption("restart").equals("1")){
                    taskList.add("restart");
                }
                if(AtFairyConfig.getOption("restartGame").equals("1")){
                    taskList.add("restartGame");
                }
                if(AtFairyConfig.getOption("baoshi").equals("1")){
                    taskList.add("baoshi");
                }
                if(AtFairyConfig.getOption("team_follow").equals("1")){
                    taskList.add("team_follow");
                }
                setTaskName(0);
            }

            @Override
            public void content_0() throws Exception {
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                if(taskList.size()==0){
                    LtLog.e(mFairy.getLineInfo("没有测试任务了"));
                    setTaskEnd();
                    return;
                }
                if(taskList.get(0).equals("killGame")){
                    LtLog.e(mFairy.getLineInfo("1秒后杀死游戏"));
                    mFairy.sleep(1000);
                    mFairy.killUserGame();
                    taskList.remove(0);
                    return;
                }

                if(taskList.get(0).equals("restart")){
                    LtLog.e(mFairy.getLineInfo("1秒后重启脚本"));
                    mFairy.sleep(1000);
                    mFairy.restart();
                    taskList.remove(0);
                    return;
                }

                if(taskList.get(0).equals("restartGame")){
                    LtLog.e(mFairy.getLineInfo("1秒后重启游戏"));
                    mFairy.sleep(1000);
                    util.restartGame();
                    taskList.remove(0);
                    return;
                }

                if(taskList.get(0).equals("team_follow")){
                    mFairy.sleep(1000);
                    new TeamTask(mFairy).team_follow();
                    return;
                }

                if(taskList.get(0).equals("baoshi")){
                    new TimingActivity(mFairy).timing_baoshi();
                    taskList.remove(0);
                    return;
                }
            }
        }.taskContent(mFairy,"测试");
    }
}