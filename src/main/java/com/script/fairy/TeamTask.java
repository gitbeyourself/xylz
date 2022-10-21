package com.script.fairy;

import com.script.framework.AtFairyImpl;
import com.script.framework.TaskContent;
import com.script.opencvapi.AtFairyConfig;
import com.script.opencvapi.FindResult;
import com.script.opencvapi.LtLog;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/22 0022.
 */

public class TeamTask extends TaskContent {
    AtFairyImpl mFairy;
    AtFairyImpl mFairy1;
    FindResult result;
    FindResult result1;
    Util util;
    static List<String> taskList = new ArrayList<>();
    static boolean createTeam=false,need=false;//自己创建队伍,参与物品的分配
    static int teamNum=3;
        static long followTime=3*60*1000,recruitTime=2*60*1000;
    public TeamTask(AtFairyImpl atFairy) throws Exception {
        mFairy = atFairy;
        util = new Util(mFairy);
        mFairy1 = atFairy;
    }

    //副本任务
    public void copy()throws Exception{
        new TeamTask(mFairy){
            @Override
            public void create() throws Exception {
                //初始化所有参数
                createTeam=false;need=false;teamNum=3;
                saodang=0;createTeam=false;need=false;
                followTime=3*60*1000;
                recruitTime=2*60*1000;

                //是否使用扫荡
                if(!AtFairyConfig.getOption("saodang").equals("")){
                    saodang=Integer.parseInt(AtFairyConfig.getOption("saodang"));
                }

                //带队或者跟队
                if(AtFairyConfig.getOption("createTeam").equals("1")){
                    createTeam=true;
                }

                //分配
                if(AtFairyConfig.getOption("need").equals("1")){
                    need=true;
                }

                //组队人数(队伍里满几人时进入副本，最少3人)
                if(!AtFairyConfig.getOption("teamNum").equals("")){
                    String teamNumStr=AtFairyConfig.getOption("teamNum");
                    teamNum=Integer.parseInt(teamNumStr);
                }

                //拉跟战间隔(自己是队长时，每隔几分钟拉一次跟战)
                if(!AtFairyConfig.getOption("followTime").equals("")){
                    ControlSplit split = strSplit(AtFairyConfig.getOption("followTime"));
                    if(split.choice==1){
                        followTime=split.timeMillis;
                    }
                }

                //招募间隔(自己是队长时，每隔几分钟招募一次)
                if(!AtFairyConfig.getOption("recruitTime").equals("")){
                    ControlSplit split = strSplit(AtFairyConfig.getOption("recruitTime"));
                    if(split.choice==1){
                        recruitTime=split.timeMillis;
                    }
                }

                //日常
                //魔族分舵
                if(AtFairyConfig.getOption("msfd").equals("1")){
                    taskList.add("msfd");
                }
                //湖心遗迹
                if(AtFairyConfig.getOption("hxyj").equals("1")){
                    taskList.add("hxyj");
                }
                //河谷之森
                if(AtFairyConfig.getOption("hgzs").equals("1")){
                    taskList.add("hgzs");
                }
                //毁灭遗迹
                if(AtFairyConfig.getOption("hmyj").equals("1")){
                    taskList.add("hmyj");
                }
                //万劫宫前
                if(AtFairyConfig.getOption("wjgq").equals("1")){
                    taskList.add("wjgq");
                }


                //挑战
                //黑风渊
                if(AtFairyConfig.getOption("hfy").equals("1")){
                    taskList.add("hfy");
                }
                //再战毁灭山脉
                if(AtFairyConfig.getOption("zzhmsm").equals("1")){
                    taskList.add("zzhmsm");
                }
                //卢家堡地宫
                if(AtFairyConfig.getOption("ljbdg").equals("1")){
                    taskList.add("ljbdg");
                }

                //混元图录
                for(int i=1;i<=3;i++){
                    for(int n=1;n<=4;n++){
                        if(AtFairyConfig.getOption("hy"+i+"-"+n).equals("1")){
                            taskList.add("hy"+i+"-"+n);
                        }
                    }
                }

                if(taskList.size()==0){
                    LtLog.e(mFairy.getLineInfo("没有勾选副本任务，多人副本结束"));
                    setTaskEnd();
                    return;
                }
            }

            @Override
            public void content_0() throws Exception {
                if(taskList.size()==0){
                    LtLog.e(mFairy.getLineInfo("所有副本任务已完成，多人副本结束"));
                    setTaskEnd();
                    return;
                }
                timekeepInit("init goal overtime");
                //日常副本
                //魔族分舵
                if(taskList.get(0).equals("msfd")){
                    daily1();
                    taskList.remove(0);
                    return;
                }
                //湖心遗迹
                if(taskList.get(0).equals("hxyj")){
                    daily1();
                    taskList.remove(0);
                    return;
                }
                //河谷之森
                if(taskList.get(0).equals("hgzs")){
                    daily1();
                    taskList.remove(0);
                    return;
                }
                //毁灭遗迹
                if(taskList.get(0).equals("hmyj")){
                    daily1();
                    taskList.remove(0);
                    return;
                }
                //万劫宫
                if(taskList.get(0).equals("wjgq")){
                    daily1();
                    taskList.remove(0);
                    return;
                }


                //挑战
                //黑风渊
                if(taskList.get(0).equals("hfy")){
                    challenge1();
                    taskList.remove(0);
                    return;
                }
                //河谷之森
                if(taskList.get(0).equals("zzhmsm")){
                    challenge1();
                    taskList.remove(0);
                    return;
                }

                //卢家堡地宫
                if(taskList.get(0).equals("ljbdg")){
                    challenge1();
                    taskList.remove(0);
                    return;
                }

                //混元图录
                if(taskList.get(0).contains("hy")){
                    hunyuanlu1();
                    taskList.remove(0);
                    return;
                }
            }
        }.taskContent(mFairy,"副本任务");
    }

    //日常副本
    public void daily1()throws Exception{
        new TeamTask(mFairy){
            String taskName="";
            int saodngCount=0;
            //初始化 副本
            @Override
            public void content_0() throws Exception {
                mFairy.sleep(3000);

                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在副本中"));
                    taskName=taskList.get(0);
                    setTaskName(4);
                    return;
                }

                util.close();

                //1.无队伍 2.队长 3.队员
                int teamState=util.teamState();
                if(createTeam){
                    if(teamState==3)util.exitTeam();//队员离队
                }else{
                    if(teamState==2)util.exitTeam();//队长离队

                    if(teamState==3){
                        taskName=taskList.get(0);
                        setTaskName(3);
                        return;
                    }
                }
                taskName=taskList.get(0);
                timekeepInit("not fight");
                setTaskName(1);
                return;
            }
            //查找副本有没完成
            @Override
            public void content_1() throws Exception {
                result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                if(result.sim>0.8f){
                    mFairy.sleep(1000);
                    LtLog.e(mFairy.getLineInfo("寻路中.."));
                    return;
                }
                if(overtime(20,0))LtLog.e(mFairy.getLineInfo("日常副本_content寻找副本超时，重置任务"));

                result=mFairy.findPic(new String[]{"platform.png","fu_goal.png"});//组队平台
                if(result.sim>0.8f){
                    timekeepInit("init goal overtime");
                    setTaskName(2);
                    return;
                }

                result=mFairy.findPic("richang.png");
                mFairy.onTap(0.8f,result,"打开日常",1000);

                result=mFairy.findPic("pic richang interface.png");
                if(result.sim>0.8f){
                    //领取活跃奖励
                    result=mFairy.findPic(258,578,1114,605,"red tip.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,"领取奖励",1000);
                        return;
                    }

                    result=mFairy.findPic("get goods.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,629,404,638,414,"获得物品",1000);
                        return;
                    }
                }

                result=mFairy.findPic("pic challenge.png");
                mFairy.onTap(0.8f,result,"切换挑战分页",1000);

                //展开日常副本
                result=mFairy.findPic("word daily copy.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(result.x+110,result.y-6,result.x+191,result.y+30,"pic down.png");
                    mFairy.onTap(0.8f,result,"展开日常",1000);
                }

                //扫荡界面
                result=mFairy.findPic("word saodang interface.png");
                if(result.sim>0.8f){
                   if(saodang==0){
                        mFairy.onTap(0.8f,result,1022,148,1031,161,"关闭扫荡",1000);
                    }else if(saodang==1){
                        result=mFairy.findPic(371,251,421,293,"pic saodang selected.png");
                    }else if(saodang==2){
                        result=mFairy.findPic(369,364,422,417,"pic saodang selected.png");
                    }
                   if(result.sim>=0.8f){
                       mFairy.onTap(0.8f,result,728,512,738,524,"确定扫荡",2000);
                       if(++saodngCount>=3){
                           LtLog.e(mFairy.getLineInfo("扫荡4次，扫荡令不够"));
                           saodang=0;
                       }
                   }else {
                       mFairy.onTap(389,266+(saodang-1)*119,400,276+(saodang-1)*119,"选择"+saodang+"方式",1000);
                   }
                   return;
                }


                //选中副本
                result=mFairy.findPic("word "+taskName+" interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(new String[]{"word zero.png","word zero1.png"});
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo(taskName+"每日结算次数达到上限"));
                        setTaskEnd();
                        return;
                    }else{
                        result=mFairy.findPic("word start saodang.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("未开启扫荡功能"));
                            saodang=0;
                        }

                        //扫荡
                        if(saodang!=0){
                            result=mFairy.findPic("word saodang.png");
                            mFairy.onTap(0.8f,result,"扫荡",1000);
                            return;
                        }

                        mFairy.onTap(1226,67,1235,78,"找到"+taskName+"副本，前往组队",1000);
                        timekeepInit("init goal overtime");
                        setTaskName(2);
                        return;
                    }
                }else{
                    result=mFairy.findPic(136,167,250,471,"word "+taskName+".png");
                    mFairy.onTap(0.8f,result,"选中"+taskName+"副本",1000);
                }
            }

            boolean changeGoal=false;
            //创建队伍 更改目标
            @Override
            public void content_2() throws Exception {
                //进入副本中
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("进入副本，执行副本"));
                    setTaskName(4);
                    return;
                }

                //超时处理
                result=mFairy.findPic("platform.png");
                result1=mFairy.findPic("word copy.png");
                if(result.sim<0.8f&&result1.sim<0.8){
                    if(timekeep(0,3*60*1000,"init goal overtime")){
                        LtLog.e(mFairy.getLineInfo("更换目标超时"));
                        setTaskName(0);
                        return;
                    }
                }else{
                    timekeepInit("init goal overtime");
                }

                //打开组队平台
                result=mFairy.findPic("dui.png");
                mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                result=mFairy.findPic("dui1.png");
                mFairy.onTap(0.8f,result,"打开组队平台",1000);

                //选择对应副本
                result=mFairy.findPic("platform.png");
                int picCount=picCount(0.8f,result,"platform");
                if(picCount==1){
                    util.initTeamGoal(1,"richangfuben",taskName,"");
                }

                if(picCount>1) {
                    if (createTeam) {
                        //创建队伍
                        result = mFairy.findPic(715, 507, 780, 553, "chuangjian.png");
                        if (result.sim < 0.8f) {
                            result = mFairy.findPic("chuangjian.png");
                        }
                        mFairy.onTap(0.8f, result, "创建队伍", 2000);
                        int picCount1 = picCount(0.8f, result, "create team");
                        if (picCount1 >= 5) {
                            LtLog.e(mFairy.getLineInfo("无法创建队伍"));
                            setTaskEnd();
                            return;
                        }
                    } else {
                        //匹配跟随
                        result = mFairy.findPic("fu_header.png");
                        if (result.sim > 0.8) {
                            LtLog.e(mFairy.getLineInfo("匹配出错，重新开始组队"));
                            setTaskName(0);
                            return;
                        }

                        //等级不足
                        result=mFairy.findPic(595,14,682,117,"word level none.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo(taskName+"等级不够，结束当前副本"));
                            taskList.remove(0);
                            setTaskEnd();
                            return;
                        }

                        //不是队长
                        result=mFairy.findPic(553,13,642,117,"word not header.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("不是队长，无法编辑地图，跟随队长作战"));
                            setTaskName(3);
                            return;
                        }

                        result=mFairy.findPic(716,63,807,124,"word hint dont apply for.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("无法申请，重置任务"));
                            setTaskName(0);
                            return;
                        }

                        result = mFairy.findPic("fu_pipei.png");
                        result1 = mFairy.findPic("auto1.png");
                        if (result.sim > 0.8 && result1.sim > 0.8) {
                            mFairy.onTap(0.8f, result, "自动匹配", 500);
                        } else {
                            result = mFairy.findPic("refresh.png");
                            mFairy.onTap(0.8f, result, "刷新", 500);
                        }

                        result = mFairy.findPic("shenqing.png");
                        mFairy.onTap(0.8f, result, "一键申请", 500);
                    }
                }

                if(changeGoal){
                    result=mFairy.findPic("word edit interface.png");
                    if(result.sim>0.8f){
                        util.initTeamGoal(2,"richangfuben",taskName,"");

                        result=mFairy.findPic("word edit.png");
                        mFairy.onTap(0.8f,result,"编辑队伍",1000);
                        int picCount1 = picCount(0.8f, result, "edit team");
                        if (picCount1 >= 5) {
                            LtLog.e(mFairy.getLineInfo("无法编辑队伍"));
                            setTaskEnd();
                            return;
                        }
                    }
                }


                //组队平台 目标正确进入 3 招募阶段
                result=mFairy.findPic("word team goal.png");
                if(result.sim>0.8f){
                    result1=mFairy.findPic("word goal "+taskName+".png");
                    if(result1.sim>0.8f){
                        //目标正确
                        LtLog.e(mFairy.getLineInfo("创建队伍完成，进入招募阶段"));
                        changeGoal=false;
                        setTaskName(3);
                        return;
                    }else{
                        //目标不正确
                        LtLog.e(mFairy.getLineInfo("队伍队伍目标不正确，更改目标"));
                        if(!createTeam){
                            setTaskName(4);
                            LtLog.e(mFairy.getLineInfo("跟随队长，执行副本"));
                            return;
                        }
                        mFairy.onTap(400,46,418,63,"打开编辑界面",1000);
                        changeGoal=true;
                        return;
                    }
                }
            }

            //等待招募完成 进入副本 入队申请
            public void content_3() throws Exception{
                result=mFairy.findPic("word edit.png");
                mFairy.onTap(0.8f,result,"编辑队伍",1000);

                result=mFairy.findPic("dui.png");
                mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                result1=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("进入副本中，执行副本"));
                    setTaskName(4);
                    return;
                }

                if(createTeam){
                    if (timekeep(1,recruitTime,"recruit")){
                        LtLog.e(mFairy.getLineInfo("--------招募一下"));
                        util.recruit();
                        timekeepInit("recruit");
                    }

                    //队伍人数，人数满后前往副本，1分钟不来的踢出队伍
                    result=mFairy.findPic("word team goal.png");
                    if(result.sim>0.8f){
                        //判断人数
                        switch(teamNum){
                            case 3:
                                result=mFairy.findPic(42,354,106,417,"pic invite.png");
                                break;
                            case 4:
                                result=mFairy.findPic(44,437,110,507,"pic invite.png");
                                break;
                            case 5:
                                result=mFairy.findPic(42,523,110,587,"pic invite.png");
                                break;
                        }
                        //队伍组满人数，发起跟随，前往副本
                        if(result.sim<0.8f){
                            //关闭组队平台，点击前往
                            mFairy.onTap(1240,675,1245,680,"关闭组队平台",1000);
                        }
                    }

                    result=mFairy.findPic("fu_go1.png");
                    result1=mFairy.findPic(342,542,1034,675,"fu_jinru.png");
                    if(result.sim>0.8f&&result1.sim<0.8f){
                        mFairy.onTap(0.8f,result,"前往副本所在地",1000);
                    }

                    result=mFairy.findPic("fu_goal.png");
                    mFairy.onTap(0.8f,result,"进入副本详情",1000);

                    result=mFairy.findPic("fu_dui.png");
                    mFairy.onTap(0.8f,result,"打开申请列表",1000);

                    result=mFairy.findPic("ruduishenqing.png");
                    if(result.sim>0.8){
                        result=mFairy.findPic(895,213,968,537,"agree.png");
                        if(result.sim>0.8){
                            mFairy.onTap(0.8f,result,"同意入队申请",500);
                        }else{
                            mFairy.onTap(1013,142,1040,168,"关闭申请列表",500);
                        }
                    }

                    //30秒召集一次队伍
                    if(timekeep(1,30*1000,"team gather")){
                        result=mFairy.findPic(825,611,885,650,"fu_zhaoji1.png");
                        mFairy.onTap(0.8f,result,"召集队伍",1000);

                        result=mFairy.findPic(663,587,729,633,"fu_zhaoji1.png");
                        mFairy.onTap(0.8f,result,"队伍召集",1000);
                        timekeepInit("team gather");
                    }

                    //十秒进一次副本
                    if(timekeep(1,10*1000,"enter copy")){
                        //进入副本
                        result=mFairy.findPic(342,542,1034,675,"fu_jinru.png");
                        result1=mFairy.findPic("defeat.png");
                        if(result.sim>0.8&&result1.sim<0.8f){
                            mFairy.onTap(0.8f,result,"进入副本",1000);
                        }
                        timekeepInit("enter copy");
                    }

                    result1=mFairy.findPic("defeat.png");
                    if(result1.sim>0.8f){
                        //踢出不在线 距离较远的
                        if(timekeep(0,60*1000,"not on line")){
                            result=mFairy.findPic(501,261,613,540,new String []{"offline.png","fu_too far.png","count not enough.png"});
                            mFairy.onTap(0.8f,result,result.x+364,result.y+13,result.x+365,result.y+14,"踢出不在线",500);
                        }else{
                            mFairy.onTap(966,179,981,191,"关闭组队失败页面",500);
                        }
                    }
                }//带队

                else{
                //匹配跟随
                    //被t出队伍/成为队长，重新匹配
                    result=mFairy.findPic("word zudui.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                        setTaskName(0);
                        return;
                    }

                    result1=mFairy.findPic("fu_header.png");
                    if(result1.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                        exit_team();
                        setTaskName(0);
                        return;
                    }

                    result=mFairy.findPic("shenqing.png");
                    mFairy.onTap(0.8f,result,"一键申请",500);

                    result=mFairy.findPic("word team goal.png");
                    mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                    result=mFairy.findPic("dui.png");
                    mFairy.onTap(0.8f,result,"切换跟队分页",1000);

                    result=mFairy.findPic(new String[]{"gen.png","zhan.png"});
                    if(result.sim<0.8f){
                        result=mFairy.findPic("fu_genzhan.png");
                        mFairy.onTap(0.8f,result,"跟战",1000);
                    }

                    boolean not_fight=timekeep(0,5*60*1000,"not fight");
                    if(not_fight){
                        LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                        util.exitTeam();
                        setTaskName(0);
                        return;
                    }
                }//跟队
            }

            int fightOver=0;//不在战斗计次
            int step=0, open=0;//河谷之森 打开笼子步骤 和 第几个笼子
            //执行副本阶段
            @Override
            public void content_4() throws Exception {
                //带队
                if(createTeam){
                    //魔神分舵
                    if(taskName.equals("msfd")){
                        result=mFairy.findPic("word strong.png");
                        mFairy.onTap(0.8f,result,1217,69,1236,85,"关闭误点界面",1000);

                        //副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic("auto.png");
                            mFairy.onTap(0.8f,result,"自动战斗",500);

                            result=mFairy.findPic("auto ing.png");
                            if(result.sim>0.8f){
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});
                                if(result1.sim<0.8f){
                                    if(fightOver++>=3){
                                        result=mFairy.findPic("dui.png");
                                        mFairy.onTap(0.8f,result,107,316,125,331,"魔神分舵指引",1000);
                                        fightOver=0;
                                    }
                                }else{
                                    fightOver=0;
                                }
                            }
                        }
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        if(result.sim<0.75f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    //湖心遗迹
                    if(taskName.equals("hxyj")){
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic("pic conjure.png");
                            if(result.sim>0.8f){
                                mFairy.onTap(0.8f,result,"开启遗迹冰魄",5000);
                                return;
                            }

                            //不在战斗，寻路下不自动战斗
                            result=mFairy.findPic("auto.png");
                            result1=mFairy.findPic(new String[]{"word find.png","word way.png"});
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                mFairy.onTap(0.8f,result,"自动作战",500);
                            }

                            //自动战斗，但是打的不是boss，点击指引
                            result=mFairy.findPic("auto ing.png");
                            result1=mFairy.findPic("pic hxyj boss.png");
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                result=mFairy.findPic("word copy goal.png");
                                mFairy.onTap(0.8f,result,66,207,87,225,"目标指引",1000);
                            }
                        }
                        //游戏熄屏 不被分配挡住
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic(new String[]{"fu_fenpei.png","world map.png"});
                        if(result.sim<0.75f&&result1.sim<0.8f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    //河谷之森
                    if(taskName.equals("hgzs")){
                        result=mFairy.findPic("word 2.5D.png");
                        mFairy.onTap(0.8f,result,"切换3D视角",500);

                        int x=0,y=0;
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic(833,290,895,359,"pic conjure.png");
                            mFairy.onTap(0.8f,result,"打开",2000);

                            //不在战斗 自动寻路不点击 点击自动战斗
                            result=mFairy.findPic("auto.png");
                            result1=mFairy.findPic(new String[]{"word find.png","word way.png"});
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                mFairy.onTap(0.8f,result,"自动作战",500);
                            }

                            /**
                             * 打开三个笼子
                             * 如果左侧任务栏发现笼子 并且不在自动寻路(点击前往三个笼子 打开地图(并且制定坐标)) 关闭地图后 前往指引
                             */
                            result=mFairy.findPic(new String[]{"word cage.png","word cage1.png"});
                            if(result.sim>0.75f){
                                int time=0;
                                result=mFairy.findPic("dui.png");
                                if(result.sim>0.8f){
                                    result=mFairy.findPic("pic hgzs time 2.png");
                                    time=result.sim>0.8f?2:1;
                                    LtLog.e(mFairy.getLineInfo("第"+time+"处笼子"));
                                }

                                //第一处的 打开笼子
                                if(time==1){
                                    //没有正在打开 和 自动寻路
                                    result=mFairy.findPic(new String[]{"word find.png","word way.png","pic transfer.png"});
                                    if(result1.sim<0.8f){
                                        if(step==0){
                                            result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                            result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                            if(result.sim>0.8f || result1.sim >0.8f){
                                                mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                                            }
                                            x=426;y=440;
                                            step=1;
                                        }
                                        else if(step==1){
                                            result=mFairy.findPic("word copy goal.png");
                                            mFairy.onTap(0.8f,result,66,207,87,225,"打开笼子，目标指引",2000);
                                            if(result.sim>0.8f)step=2;
                                        }
                                        else if(step==2) {
                                            result1 = mFairy.findPic(new String[]{"word find.png", "word way.png"});
                                            if (result1.sim < 0.8f) {
                                                mFairy.sleep(1000);
                                                //第一处的笼子
                                                switch (open) {
                                                    case 0:
                                                        mFairy.ranSwipe(190,579,41,470,3000,1000l,0);
                                                        open = 1;
                                                        break;
                                                    case 1:
                                                        mFairy.onTap(524, 310, 538, 322, "打开笼子2", 1000);
                                                        mFairy.onTap(524, 310, 538, 322, "打开笼子2", 3000);
                                                        open = 2;
                                                        break;
                                                    case 2:
                                                        mFairy.onTap(849, 318, 860, 328, "打开笼子3", 1000);
                                                        mFairy.onTap(849, 318, 860, 328, "打开笼子3", 3000);
                                                        open = 0;
                                                        break;
                                                }
                                                step=0;
                                            }
                                        }
                                    }
                                }//第一处

                                //第二处的 打开笼子
                                if(time==2){
                                    result=mFairy.findPic(new String[]{"word find.png","word way.png","pic transfer.png"});//自动寻路
                                    if(result.sim<0.8f){
                                        switch (open){
                                            case 0:
                                                x=817;y=180;
                                                open=1;
                                                break;
                                            case 1:
                                                x=847;y=220;
                                                open=2;
                                                break;
                                            case 2:
                                                x=845;y=238;
                                                open=0;
                                                break;
                                        }
                                        result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                        result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                        if(result.sim>0.8f || result1.sim >0.8f){
                                            mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                                        }
                                    }
                                }//第二处
                            }//打开笼子

                            result=mFairy.findPic("auto ing.png");//自动战斗
                            if(result.sim>0.8f){
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});//血条 和 boss的头像
                                if(result1.sim>0.8f){
                                    fightOver=0;
                                }else{
                                    if(fightOver++>=5){
                                        result=mFairy.findPic("word copy goal.png");
                                        mFairy.onTap(0.8f,result,66,207,87,225,"目标指引",2000);
                                        fightOver=0;
                                    }
                                }
                            }//自动战斗
                        }//在副本里面

                        result=mFairy.findPic("world map.png");//地图 去指定坐标
                        mFairy.onTap(0.8f,result,x,y,x+1,y+1,"定位前往1",1000);
                        mFairy.onTap(0.8f,result,x,y,x+1,y+1,"定位前往2",1000);
                        mFairy.onTap(0.8f,result,1225,42,1237,54,"关闭区域地图",1000);

                        //熄屏点击
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic("world map.png");
                        if(result.sim<0.75f&&result1.sim<0.7f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }

                        result=mFairy.findPic(955,525,1019,571,"pet select.png");
                        mFairy.onTap(0.8f,result,"选择宠物",1000);

                        result=mFairy.findPic(601,548,672,587,"affirm.png");
                        mFairy.onTap(0.8f,result,"确定选择宠物",500);
                    }

                    //毁灭遗迹
                    if(taskName.equals("hmyj")){
                        int x=0,y=0;
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            //自动战斗，但没有目标，点击指引
                            result=mFairy.findPic("auto ing.png");
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                //选中目标战斗中
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});
                                if(result1.sim>0.8f){
                                    fightOver=0;
                                }else{
                                    if(fightOver++>=3){
                                        result=mFairy.findPic("word copy goal.png");
                                        mFairy.onTap(0.8f,result,66,207,87,225,"目标指引",2000);
                                    }
                                }
                            }

                            //不在自动寻路中
                            result1=mFairy.findPic(new String[]{"word find.png","word way.png"});
                            if(result1.sim<0.8f){
                                //自动作战
                                result=mFairy.findPic("auto.png");
                                mFairy.onTap(0.8f,result,"自动作战",1000);

                                //选择一条通路
                                result=mFairy.findPic("word goal way.png");
                                if(result.sim>0.8f){
                                    result1=mFairy.findPic("pic open way.png");
                                    mFairy.onTap(0.8f,result1,"打开通路",4000);
                                    if(result1.sim<0.8f){
                                        result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                        result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                        if(result.sim>0.8f || result1.sim >0.8f) {
                                            mFairy.onTap(0.8f, result, 1168, 92, 1185, 107, "打开区域地图", 1500);
                                            x = 718;
                                            y = 367;
                                        }
                                    }
                                }

                                //打开所有宝箱
                                result=mFairy.findPic("word hmyj box.png");
                                result1=mFairy.findPic("pic conjure.png");
                                if(result.sim>0.8f&&result1.sim<0.8f){
                                    if(open++>6){
                                        open=1;
                                    }
                                    result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                    result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                    if(result.sim>0.8f || result1.sim >0.8f) {
                                        mFairy.onTap(1168, 92, 1185, 107, "打开区域地图", 1500);
                                    }
                                    switch (open){
                                        case 1:
                                            x=708;y=345;
                                            break;
                                        case 2:
                                            x=693;y=345;
                                            break;
                                        case 3:
                                            x=708;y=366;
                                            break;
                                        case 4:
                                            x=693;y=366;
                                            break;
                                        case 5:
                                            x=708;y=390;
                                            break;
                                        case 6:
                                            x=693;y=390;
                                            break;
                                    }
                                }

                                //对话神秘人
                                result=mFairy.findPic("word hmyj dialogue.png");
                                if(result.sim>0.8f){
                                    mFairy.ranSwipe(879,206,1105,206,500,1000l,0);
                                    mFairy.onTap(0.8f,result,630,374,636,381,"对话神秘人",1000);

                                    result=mFairy.findPic(877,215,946,331,new String[]{"word heilao.png","word longshan.png","word lao.png"});
                                    mFairy.onTap(0.8f,result,"选中黑老",1000);
                                }
                                result=mFairy.findPic(53,389,100,515,"fu_goal.png");
                                mFairy.onTap(0.8f,result,"回答神秘人",1000);


                                //定点到通路
                                result=mFairy.findPic("world map.png");
                                if(result.sim>0.8){
                                    mFairy.onTap(x,y,x+1,y+1,"定位前往1",1000);
                                    mFairy.onTap(x,y,x+1,y+1,"定位前往2",1000);

                                    result = mFairy.findPic(890,3,1275,235,new String[]{"close.png","close3.png"});
                                    mFairy.onTap(0.8f,result,"关闭区域地图",200);
                                }
                            }
                        }

                        //游戏熄屏 不被分配挡住
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic(new String []{"fu_fenpei.png","world map.png"});
                        if(result.sim<0.75f&&result1.sim<0.8f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }

                        result=mFairy.findPic("pic conjure.png");
                        mFairy.onTap(0.8f,result,"打开宝箱",5000);
                    }

                    //万劫宫
                    if(taskName.equals("wjgq")){

                        long dat = mFairy.mMatTime(1196,149,63,22,0.9f);
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){

                            //不在战斗，寻路下不自动战斗
                            result=mFairy.findPic("auto.png");
                            result1=mFairy.findPic(new String[]{"word find.png","word way.png"});
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                mFairy.onTap(0.8f,result,"自动作战",500);
                            }

                            //自动战斗，但是打的不是boss，点击指引
//                            result=mFairy.findPic("auto ing.png");
                            result1=mFairy.findPic(new String[]{"pic hxyj boss.png","yao.png"});
                            if(result1.sim<0.8f){
                                result=mFairy.findPic("word copy goal.png");
                                mFairy.onTap(0.8f,result,66,207,87,225,"没有目标了点一下目标指引",1000);
                            }

                            if (dat >= 15){
                                mFairy.initMatTime();
                                result=mFairy.findPic("mo.png");
                                mFairy.onTap(0.8f,result,935,460,940,464,"三魔小妖",1000);
                            }

                        }

                        //游戏熄屏 不被分配挡住
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic(new String[]{"fu_fenpei.png","world map.png"});
                        if(result.sim<0.75f&&result1.sim<0.8f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    result=mFairy.findPic("dui1.png");//队伍分页
                    boolean timekeep=timekeep(1,followTime,"follow");
                    if(result.sim>0.8){
                        //队伍分页
                        if(timekeep){
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                        mFairy.onTap(14,214,27,229,"切换副本分页",1000);
                    }

                    result=mFairy.findPic("dui.png");//副本分页
                    if(result.sim>0.8f){
                        //副本分页
                        if(timekeep){
                            mFairy.onTap(13,324,22,334,"切换队伍分页",2000);
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                    }

                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    if(result.sim>0.7f){
                        result=mFairy.findPic("leave.png");
                        if(result.sim<0.7f){
                            if(timekeep(1,45*1000,"main interface")){
                                LtLog.e(mFairy.getLineInfo("大厅界面超时，重置任务"));
                                setTaskName(0);
                            }
                        }else{
                            timekeepInit("main interface");
                        }
                    }
                }//带队

                else{
                    result=mFairy.findPic("leave.png");//在副本中
                    if(result.sim>0.8f){
                        timekeepInit("not fight");

                        result=mFairy.findPic("pic conjure.png");
                        mFairy.onTap(0.8f,result,"开启遗迹冰魄/宝箱",5000);

                        result=mFairy.findPic("dui.png");
                        mFairy.onTap(0.8f,result,"切换队伍分页",1000);
                    }else{
                        //被t出队伍/成为队长，重新匹配
                        result1=mFairy.findPic("fu_header.png");
                        if(result1.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                            exit_team();
                            setTaskName(0);
                            return;
                        }

                        result=mFairy.findPic("word zudui.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                            setTaskName(0);
                            return;
                        }

                        boolean not_fight=timekeep(0,5*60*1000,"not fight");
                        if(not_fight){
                            LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                            util.exitTeam();
                            setTaskName(0);
                            return;
                        }
                    }

                    result=mFairy.findPic("word team goal.png");
                    mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                    //不在跟战，点击跟战
                    result=mFairy.findPic(new String[]{"dong.png","zhan.png"});
                    if(result.sim<0.8f){
                        result=mFairy.findPic("fu_genzhan.png");
                        mFairy.onTap(0.8f,result,"点击跟战",1000);
                    }

                    //游戏熄屏 不被分配挡住
                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    result1=mFairy.findPic("fu_fenpei.png");
                    if(result.sim<0.75f&&result1.sim<0.8f){
                        mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                    }

                }//跟队

                result=mFairy.findPic("fuhuo pic.png");
                mFairy.onTap(0.8f,result,635,341,645,352,"立即原地满血复活",500);

                //副本结束
                result1=mFairy.findPic(700,532,768,583,"leave1.png");
                if(result1.sim>0.8f){
                    //成功
                    result=mFairy.findPic("pic success.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本成功"));
                        setTaskEnd();
                    }
                    //失败
                    result=mFairy.findPic("pic failure.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本失败"));
                    }
                    mFairy.onTap(0.8f,result1,taskName+"副本结束，点击离开",1000);
                    setTaskName(0);
                    return;
                }

                //一键需求
                result=mFairy.findPic("fu_fenpei.png");
                if(result.sim>0.8){
                    if(need){
                        result=mFairy.findPic("fu_need.png");
                        if(result.sim>0.8f){
                            mFairy.onTap(0.8f,result,"获得分配,一键需求",500);

                            result=mFairy.findPic("pic allocation back.png");
                            mFairy.onTap(0.8f,result,"收回分配页",500);
                        }
                    }else{
                        result=mFairy.findPic("pic allocation back.png");
                        mFairy.onTap(0.8f,result,"获得分配,放弃参与分配",500);
                    }
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("fu_couqi.png");
                mFairy.onTap(0.8f,result,717,405,732,420,"人已凑齐,发起跟随",500);

                result=mFairy.findPic("word hint recharge.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,550,419,554,422,"元宝不足,取消充值",1000);
                    saodang=0;
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"日常副本"+ taskList.get(0));
    }

    //挑战副本
    public void challenge1() throws Exception{
        new TeamTask(mFairy){
            String taskName="";
            //初始化阶段
            @Override
            public void content_0() throws Exception {
                util.close();
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在副本中"));
                    taskName=taskList.get(0);
                    setTaskName(4);
                    return;
                }
                //1.无队伍 2.队长 3.队员
                int teamState=util.teamState();
                if(createTeam){
                    if(teamState==3)util.exitTeam();//队员离队
                }else{
                    if(teamState==2)util.exitTeam();//队长离队

                    if(teamState==3){
                        taskName=taskList.get(0);
                        setTaskName(3);
                        return;
                    }
                }
                taskName=taskList.get(0);
                timekeepInit("not fight");
                setTaskName(1);
                return;
            }

            //寻找副本阶段
            @Override
            public void content_1() throws Exception { result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                if(result.sim>0.8f){
                    mFairy.sleep(1000);
                    LtLog.e(mFairy.getLineInfo("寻路中.."));
                    return;
                }
                if(overtime(20,0))LtLog.e(mFairy.getLineInfo("日常副本_content寻找副本超时，初始化"));

                result=mFairy.findPic(new String[]{"platform.png","fu_goal.png"});//组队平台
                if(result.sim>0.8f){
                    timekeepInit("init goal overtime");
                    setTaskName(2);
                    return;
                }

                result=mFairy.findPic("richang.png");
                mFairy.onTap(0.8f,result,"打开日常",1000);

                result=mFairy.findPic("pic richang interface.png");
                if(result.sim>0.8f){
                    //领取活跃奖励
                    result=mFairy.findPic(258,578,1114,605,"red tip.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,"领取奖励",1000);
                        return;
                    }

                    result=mFairy.findPic("get goods.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,629,404,638,414,"获得物品",1000);
                        return;
                    }
                }

                result=mFairy.findPic("pic challenge.png");
                mFairy.onTap(0.8f,result,"切换挑战分页",1000);

//                result=mFairy.findPic(133,426,253,476,"word challenge copy.png");
//                mFairy.onTap(0.8f,result,"切换挑战副本分页",1000);
                result=mFairy.findPic(74,84,309,666,"xingchen2.png");
                mFairy.onTap(0.8f,result,"切换挑战副本分页",1000);
                //展开挑战副本
                result=mFairy.findPic(133,426,253,476,"word challenge copy.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(result.x+110,result.y-6,result.x+191,result.y+30,"pic down.png");
                    mFairy.onTap(0.8f,result,"展开挑战副本",1000);
                }

                //选中副本
                result=mFairy.findPic("word "+taskName+" interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word zero.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo(taskName+"每日进入次数已经达到上限"));
                        setTaskEnd();
                        return;
                    }else{
                        mFairy.onTap(1226,67,1235,78,"找到"+taskName+"副本，前往组队",1000);
                        timekeepInit("init goal overtime");
                        setTaskName(2);
                        return;
                    }
                }else{
                    result=mFairy.findPic(119,242,269,417,"word "+taskName+".png");
                    mFairy.onTap(0.8f,result,"选中"+taskName+"副本",1000);
                }
            }

            boolean changeGoal=false;
            //创建队伍 更改目标
            @Override
            public void content_2() throws Exception {
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("进入副本，执行副本"));
                    setTaskName(4);
                    return;
                }

                result=mFairy.findPic("platform.png");
                result1=mFairy.findPic("word copy.png");
                if(result.sim<0.8f&&result1.sim<0.8){
                    if(timekeep(0,3*60*1000,"init goal overtime")){
                        LtLog.e(mFairy.getLineInfo("更换目标超时"));
                        setTaskName(0);
                        return;
                    }
                }else{
                    timekeepInit("init goal overtime");
                }
                result=mFairy.findPic("dui.png");
                mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                result=mFairy.findPic("dui1.png");
                mFairy.onTap(0.8f,result,"打开组队平台",1000);

                //选择对应副本
                result=mFairy.findPic("platform.png");
                int picCount=picCount(0.8f,result,"platform");
                if(picCount==1){
                    util.initTeamGoal(1,"word challenge copy",taskName,"");
                }

                if(picCount>1) {
                    if (createTeam) {
                        //创建队伍
                        result = mFairy.findPic(715, 507, 780, 553, "chuangjian.png");
                        if (result.sim < 0.8f) {
                            result = mFairy.findPic("chuangjian.png");
                        }
                        mFairy.onTap(0.8f, result, "创建队伍", 2000);
                        int picCount1 = picCount(0.8f, result, "create team");
                        if (picCount1 >= 5) {
                            LtLog.e(mFairy.getLineInfo("无法创建队伍"));
                            setTaskEnd();
                            return;
                        }
                    } else {
                        //匹配跟随
                        result = mFairy.findPic("fu_header.png");
                        if (result.sim > 0.8) {
                            LtLog.e(mFairy.getLineInfo("匹配出错，重新开始组队"));
                            setTaskName(0);
                            return;
                        }

                        //等级不足
                        result=mFairy.findPic(595,14,682,117,"word level none.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo(taskName+"等级不够，结束当前副本"));
                            taskList.remove(0);
                            setTaskEnd();
                            return;
                        }

                        //不是队长
                        result=mFairy.findPic(553,13,642,117,"word not header.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("不是队长，无法编辑地图，跟随队长作战"));
                            setTaskName(3);
                            return;
                        }

                        result = mFairy.findPic("fu_pipei.png");
                        result1 = mFairy.findPic("auto1.png");
                        if (result.sim > 0.8 && result1.sim > 0.8) {
                            mFairy.onTap(0.8f, result, "自动匹配", 500);
                        } else {
                            result = mFairy.findPic("refresh.png");
                            mFairy.onTap(0.8f, result, "刷新", 500);
                        }

                        result = mFairy.findPic("shenqing.png");
                        mFairy.onTap(0.8f, result, "一键申请", 500);
                    }
                }

                if(changeGoal){
                    result=mFairy.findPic("word edit interface.png");
                    if(result.sim>0.8f){
                        util.initTeamGoal(2,"word challenge copy",taskName,"");

                        result=mFairy.findPic(714,510,791,551,"word edit.png");
                        mFairy.onTap(0.8f,result,"编辑队伍",1000);
                        int picCount1 = picCount(0.8f, result, "edit team");
                        if (picCount1 >= 5) {
                            LtLog.e(mFairy.getLineInfo("无法编辑队伍"));
                            setTaskEnd();
                            return;
                        }
                    }
                }

                //组队平台 目标正确进入 3 招募阶段
                result=mFairy.findPic("word team goal.png");
                if(result.sim>0.8f){
                    result1=mFairy.findPic("word goal "+taskName+".png");
                    if(result1.sim>0.8f){
                        //目标正确
                        LtLog.e(mFairy.getLineInfo("创建队伍完成，进入招募阶段"));
                        changeGoal=false;
                        setTaskName(3);
                        return;
                    }else{
                        //目标不正确
                        LtLog.e(mFairy.getLineInfo("队伍队伍目标不正确，更改目标"));
                        if(!createTeam){
                            setTaskName(4);
                            LtLog.e(mFairy.getLineInfo("跟随队长，执行副本"));
                            return;
                        }
                        mFairy.onTap(400,46,418,63,"打开编辑界面",1000);
                        changeGoal=true;
                        return;
                    }
                }
            }

            //等待招募完成 进入副本 入队申请
            public void content_3() throws Exception{
                result=mFairy.findPic("word edit.png");
                mFairy.onTap(0.8f,result,"编辑队伍",1000);

                result=mFairy.findPic("dui.png");
                mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                result=mFairy.findPic(909,220,986,257,"cancel.png");
                result1=mFairy.findPic("leave.png");
                if(result.sim>0.8f||result1.sim>0.8f){
                    mFairy.onTap(0.8f,result,"取消加入语音",1000);
                    LtLog.e(mFairy.getLineInfo("进入副本中，执行副本"));
                    setTaskName(4);
                    return;
                }

                if(createTeam){
                    if (timekeep(1,recruitTime,"recruit")){
                        LtLog.e(mFairy.getLineInfo("--------招募一下"));
                        util.recruit();
                        timekeepInit("recruit");
                    }

                    //队伍人数，人数满后前往副本，1分钟不来的踢出队伍
                    result=mFairy.findPic("word team goal.png");
                    if(result.sim>0.8f){
                        //判断人数
                        switch(teamNum){
                            case 3:
                                result=mFairy.findPic(42,354,106,417,"pic invite.png");
                                break;
                            case 4:
                                result=mFairy.findPic(44,437,110,507,"pic invite.png");
                                break;
                            case 5:
                                result=mFairy.findPic(42,523,110,587,"pic invite.png");
                                break;
                        }
                        //队伍组满人数，发起跟随，前往副本
                        if(result.sim<0.8f){
                            //关闭组队平台，点击前往
                            mFairy.onTap(1240,675,1245,680,"关闭组队平台",1000);
                        }
                    }

                    result=mFairy.findPic("fu_go1.png");
                    result1=mFairy.findPic(342,542,1034,675,"fu_jinru.png");
                    if(result.sim>0.8f&&result1.sim<0.8f){
                        mFairy.onTap(0.8f,result,"前往副本所在地",1000);
                    }

                    result=mFairy.findPic("fu_goal.png");
                    mFairy.onTap(0.8f,result,"进入副本详情",1000);

                    result=mFairy.findPic("fu_dui.png");
                    mFairy.onTap(0.8f,result,"打开申请列表",1000);


                    result=mFairy.findPic("ruduishenqing.png");
                    if(result.sim>0.8){
                        result=mFairy.findPic(895,213,968,537,"agree.png");
                        if(result.sim>0.8){
                            mFairy.onTap(0.8f,result,"同意入队申请",500);
                        }else{
                            mFairy.onTap(1013,142,1040,168,"关闭申请列表",500);
                        }
                    }

                    //30秒召集一次队伍
                    if(timekeep(1,30*1000,"team gather")){
                        result=mFairy.findPic(825,611,885,650,"fu_zhaoji1.png");
                        mFairy.onTap(0.8f,result,"召集队伍",1000);

                        result=mFairy.findPic(663,587,729,633,"fu_zhaoji1.png");
                        mFairy.onTap(0.8f,result,"队伍召集",1000);
                    }

                    //十秒进一次副本
                    if(timekeep(1,10*1000,"enter copy")){
                        //进入副本
                        result=mFairy.findPic(342,542,1034,675,"fu_jinru.png");
                        result1=mFairy.findPic("defeat.png");
                        if(result.sim>0.8&&result1.sim<0.8f){
                            mFairy.onTap(0.8f,result,"进入副本",1000);
                        }
                    }

                    //进入副本失败
                    result1=mFairy.findPic("defeat.png");
                    if(result1.sim>0.8f){
                        //踢出不在线 距离较远的
                        if(timekeep(0,60*1000,"not on line")){
                            result=mFairy.findPic(501,261,613,540,new String []{"offline.png","fu_too far.png","count not enough.png"});
                            mFairy.onTap(0.8f,result,result.x+364,result.y+13,result.x+365,result.y+14,"踢出不在线",500);
                        }else{
                            mFairy.onTap(966,179,981,191,"关闭组队失败页面",500);
                        }
                    }
                }else{
                //跟队
                    //被t出队伍/成为队长，重新匹配
                    result=mFairy.findPic("word zudui.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                        setTaskName(0);
                        return;
                    }

                    result1=mFairy.findPic("fu_header.png");
                    if(result1.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                        exit_team();
                        setTaskName(0);
                        return;
                    }

                    result=mFairy.findPic("shenqing.png");
                    mFairy.onTap(0.8f,result,"一键申请",500);

                    result=mFairy.findPic("word team goal.png");
                    mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                    result=mFairy.findPic("dui.png");
                    mFairy.onTap(0.8f,result,"切换跟队分页",1000);

                    result=mFairy.findPic(new String[]{"gen.png","zhan.png"});
                    if(result.sim<0.8f){
                        result=mFairy.findPic("fu_genzhan.png");
                        mFairy.onTap(0.8f,result,"跟战",1000);
                    }

                    boolean not_fight=timekeep(0,5*60*1000,"not fight");
                    if(not_fight){
                        LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                        util.exitTeam();
                        setTaskName(0);
                        return;
                    }
                }
            }

            int fightOver=0;
            //执行副本阶段
            @Override
            public void content_4() throws Exception {
                //带队
                if(createTeam){
                    //黑风渊
                    if(taskName.equals("hfy")){
                        //副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic("auto.png");
                            mFairy.onTap(0.8f,result,"自动战斗",500);

                            result=mFairy.findPic("auto ing.png");
                            if(result.sim>0.8f){
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});
                                if(result1.sim<0.8f){
                                    mFairy.sleep(500);
                                    if(fightOver++>=3){
                                        result=mFairy.findPic("dui.png");
                                        mFairy.onTap(0.8f,result,107,316,125,331,"黑风渊指引",1000);
                                        fightOver=0;
                                    }
                                }else{
                                    fightOver=0;
                                }
                            }
                        }

                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        if(result.sim<0.75f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    //再战毁灭山脉
                    if(taskName.equals("zzhmsm")){
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic("auto.png");
                            mFairy.onTap(0.8f,result,"自动战斗",500);

                            //如果打的不是boss 点击指引
                            result=mFairy.findPic("auto ing.png");
                            if(result.sim>0.8f){
                                result1=mFairy.findPic("pic hxyj boss.png");
                                if(result1.sim<0.8f){
                                    if(fightOver++>=3){
                                        result=mFairy.findPic("dui.png");
                                        mFairy.onTap(0.8f,result,93,207,105,219,"再战毁灭山脉指引",1000);
                                        fightOver=0;
                                    }
                                }else{
                                    fightOver=0;
                                }
                            }
                        }
                        //游戏熄屏 不被分配挡住
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic("fu_fenpei.png");
                        if(result.sim<0.75f&&result1.sim<0.8f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    //卢家堡地宫
                    if(taskName.equals("ljbdg")){
                        //熄屏点击
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic("word team goal.png");
                            mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                            //不在战斗 开启自动战斗
                            result=mFairy.findPic("auto.png");
                            mFairy.onTap(0.8f,result,"开启自动作战",1000);

                            //没有攻击对象 点击指引 连续三次
                            result=mFairy.findPic("auto ing.png");
                            if(result.sim>0.8f){
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});
                                if(result1.sim<0.8f){
                                    mFairy.sleep(500);
                                    if(fightOver++>3){
                                        result=mFairy.findPic("dui.png");
                                        mFairy.onTap(0.8f,result,107,316,125,331,"卢家堡地宫指引",1000);
                                        fightOver=0;
                                    }
                                }else{
                                    fightOver=0;
                                }
                            }
                        }

                        //游戏熄屏 不被分配挡住
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic("fu_fenpei.png");
                        if(result.sim<0.75f&&result1.sim<0.8f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    result=mFairy.findPic("dui.png");//副本分页
                    result1=mFairy.findPic("dui1.png");//队伍分页
                    boolean timekeep=timekeep(1,followTime,"follow");
                    if(result1.sim>0.8){
                        //队伍分页
                        if(timekeep){
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                        mFairy.onTap(14,214,27,229,"切换副本分页",1000);
                    }
                    if(result.sim>0.8f){
                        //副本分页
                        if(timekeep){
                            mFairy.onTap(13,324,22,334,"切换队伍分页",2000);
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                    }

                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    result1=mFairy.findPic("leave.png");
                    if(result.sim>0.7f){
                        if(result1.sim<0.7f){
                            if(timekeep(1,45*1000,"main interface")){
                                LtLog.e(mFairy.getLineInfo("大厅界面超时，重置任务"));
                                setTaskName(0);
                            }
                        }else{
                            timekeepInit("main interface");
                        }
                    }
                }else{
                    //跟队
                    //在副本中
                    result=mFairy.findPic("leave.png");
                    if(result.sim>0.8f){
                        timekeepInit("not fight");

                        result=mFairy.findPic("pic conjure.png");
                        mFairy.onTap(0.8f,result,"开启遗迹冰魄/宝箱",5000);

                        result=mFairy.findPic("dui.png");
                        mFairy.onTap(0.8f,result,"切换队伍分页",1000);
                    }else{
                        //被t出队伍/成为队长，重新匹配
                        result1=mFairy.findPic("fu_header.png");
                        if(result1.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                            exit_team();
                            setTaskName(0);
                            return;
                        }


                        result=mFairy.findPic("word zudui.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                            setTaskName(0);
                            return;
                        }

                        boolean not_fight=timekeep(0,5*60*1000,"not fight");
                        if(not_fight){
                            LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                            util.exitTeam();
                            setTaskName(0);
                            return;
                        }
                    }
                    result=mFairy.findPic("word team goal.png");
                    mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                    //游戏熄屏 不被分配挡住
                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    result1=mFairy.findPic("fu_fenpei.png");
                    if(result.sim<0.75f&&result1.sim<0.8f){
                        mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                    }
                }

                //不在跟战，点击跟战
                result=mFairy.findPic(new String[]{"dong.png","zhan.png"});
                if(result.sim<0.8f){
                    result=mFairy.findPic("fu_genzhan.png");
                    mFairy.onTap(0.8f,result,"点击跟战",1000);
                }

                result=mFairy.findPic("fuhuo pic.png");
                mFairy.onTap(0.8f,result,635,341,645,352,"立即原地满血复活",500);

                //副本结束
                result1=mFairy.findPic(700,532,768,583,"leave1.png");
                if(result1.sim>0.8f){
                    //成功
                    result=mFairy.findPic("pic success.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本成功"));
                        setTaskEnd();
                    }
                    //失败
                    result=mFairy.findPic("pic failure.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本失败"));
                    }
                    mFairy.onTap(0.8f,result1,taskName+"副本结束，点击离开",1000);
                    setTaskName(0);
                    return;
                }

                //一键需求
                result=mFairy.findPic("fu_fenpei.png");
                if(result.sim>0.8){
                    if(need){
                        result=mFairy.findPic("fu_need.png");
                        if(result.sim>0.8f){
                            mFairy.onTap(0.8f,result,"获得分配,一键需求",500);

                            result=mFairy.findPic("pic allocation back.png");
                            mFairy.onTap(0.8f,result,"收回分配页",500);
                        }
                    }else{
                        result=mFairy.findPic("pic allocation back.png");
                        mFairy.onTap(0.8f,result,"获得分配,放弃参与分配",500);
                    }
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("fu_couqi.png");
                mFairy.onTap(0.8f,result,717,405,732,420,"人已凑齐,发起跟随",500);

                result=mFairy.findPic(436,280,504,336,"word teammate.png");
                mFairy.onTap(0.8f,result,703,405,737,421,"战力不足，继续挑战",500);
                super.inOperation();
            }
        }.taskContent(mFairy,"挑战副本"+ taskList.get(0));
    }

    //混元图录
    public void hunyuanlu1()throws Exception{
        new TeamTask(mFairy){
            String taskName="";
            int hard=1;
            int section=0;
            int level=1;
            //获取章节和关卡
            @Override
            public void create() throws Exception {
                //超凡难度
                if(AtFairyConfig.getOption("hard").equals("2")){
                    hard=2;
                }

                //hy1-1
                taskName=taskList.get(0);
                section=Integer.parseInt(taskName.substring(2,3));
                level=Integer.parseInt(taskName.substring(4));

                if(hard==1){
                    taskName="xingchen";
                }else{
                    taskName="chaofan";
                }
            }

            //初始化阶段
            @Override
            public void content_0() throws Exception {
                util.close();
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在副本中"));
                    taskName=taskList.get(0);
                    setTaskName(4);
                    return;
                }
                //1.无队伍 2.队长 3.队员
                int teamState=util.teamState();
                if(createTeam){
                    if(teamState==3)util.exitTeam();//队员离队
                }else{
                    if(teamState==2)util.exitTeam();//队长离队

                    if(teamState==3){
                        taskName=taskList.get(0);
                        setTaskName(3);
                        return;
                    }
                }
                taskName=taskList.get(0);
                timekeepInit("not fight");
                setTaskName(1);
                return;
            }

            //寻找副本阶段
            @Override
            public void content_1() throws Exception {
                result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("content1 寻找副本 寻路中。。"));
                    mFairy.sleep(1000);
                    return;
                }

                if(overtime(20,0))LtLog.e(mFairy.getLineInfo("挑战副本_content寻找副本超时，初始化"));
                result=mFairy.findPic("richang.png");
                mFairy.onTap(0.8f,result,"打开日常",1000);

                result=mFairy.findPic("pic richang interface.png");
                if(result.sim>0.8f){
                    //领取活跃奖励
                    result=mFairy.findPic(258,578,1114,605,"red tip.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,"领取奖励",1000);
                        return;
                    }

                    result=mFairy.findPic("get goods.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,629,404,638,414,"获得物品",1000);
                        return;
                    }
                }

                result=mFairy.findPic("pic challenge.png");
                mFairy.onTap(0.8f,result,"切换挑战分页",1000);

                result=mFairy.findPic(128,405,257,627,"word hunyuantulu.png");
                mFairy.onTap(0.8f,result,"切换混元图录分页",1000);

                //展开混元图录
                result=mFairy.findPic(134,260,254,301,"word hunyuantulu.png");
                if(result.sim>0.8f){
                    mFairy.findPic(result.x+110,result.y-6,result.x+191,result.y+30,"pic down.png");
                    mFairy.onTap(0.8f,result,"混元图录",1000);
                }

                //选中副本
                result=mFairy.findPic("word "+taskName+" interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(1010,339,1040,373,"word zero.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo(taskName+"本周进入次数已经达到上限"));
                        setTaskEnd();
                        return;
                    }else{
                        mFairy.onTap(1226,67,1235,78,"找到"+taskName+"副本，前往组队",1000);
                        timekeepInit("init goal overtime");
                        setTaskName(2);
                        return;
                    }
                }else{
                    result=mFairy.findPic(109,315,185,449,"word "+taskName+".png");
                    mFairy.onTap(0.8f,result,"选中"+taskName+"副本",1000);
                }

            }

            boolean changeGoal=false;
            //创建队伍 更改目标
            @Override
            public void content_2() throws Exception {
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("进入副本，执行副本"));
                    setTaskName(4);
                    return;
                }

                result=mFairy.findPic("dui.png");
                mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                result=mFairy.findPic("dui1.png");
                mFairy.onTap(0.8f,result,"打开组队平台",1000);

                //选择对应副本
                result=mFairy.findPic("platform.png");
                int picCount=picCount(0.8f,result,"platform");
                if(picCount==1){
                    if(createTeam){
                        util.initTeamGoal(1,"word copy hytl",taskName,section+"-"+level);
                    }else{
                        util.initTeamGoal(1,"word copy hytl",taskName,"");
                    }
                }

                if(picCount>1) {
                    if (createTeam) {
                        //创建队伍
                        result = mFairy.findPic(715, 507, 780, 553, "chuangjian.png");
                        if (result.sim < 0.8f) {
                            result = mFairy.findPic("chuangjian.png");
                        }
                        mFairy.onTap(0.8f, result, "创建队伍", 2000);
                        int picCount1 = picCount(0.8f, result, "create team");
                        if (picCount1 >= 5) {
                            LtLog.e(mFairy.getLineInfo("无法创建队伍"));
                            setTaskEnd();
                            return;
                        }
                    } else {
                        //匹配跟随
                        result = mFairy.findPic("fu_header.png");
                        if (result.sim > 0.8) {
                            LtLog.e(mFairy.getLineInfo("匹配出错，重新开始组队"));
                            setTaskName(0);
                            return;
                        }

                        //等级不足
                        result=mFairy.findPic(595,14,682,117,"word level none.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo(taskName+"等级不够，结束当前副本"));
                            taskList.remove(0);
                            setTaskEnd();
                            return;
                        }

                        //不是队长
                        result=mFairy.findPic(553,13,642,117,"word not header.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("不是队长，无法编辑地图，跟随队长作战"));
                            setTaskName(3);
                            return;
                        }

                        result = mFairy.findPic("fu_pipei.png");
                        result1 = mFairy.findPic("auto1.png");
                        if (result.sim > 0.8 && result1.sim > 0.8) {
                            mFairy.onTap(0.8f, result, "自动匹配", 500);
                        } else {
                            result = mFairy.findPic("refresh.png");
                            mFairy.onTap(0.8f, result, "刷新", 500);
                        }

                        result = mFairy.findPic("shenqing.png");
                        mFairy.onTap(0.8f, result, "一键申请", 500);
                    }
                }

                if(changeGoal){
                    result=mFairy.findPic("word edit interface.png");
                    if(result.sim>0.8f){
                        util.initTeamGoal(2,"word copy hytl",taskName,section+"-"+level);

                        result=mFairy.findPic(714,510,791,551,"word edit.png");
                        mFairy.onTap(0.8f,result,"编辑队伍",1000);
                        int picCount1 = picCount(0.8f, result, "edit team");
                        if (picCount1 >= 5) {
                            LtLog.e(mFairy.getLineInfo("无法编辑队伍"));
                            setTaskEnd();
                            return;
                        }
                    }
                }


                //组队平台 目标正确进入 3 招募阶段
                result=mFairy.findPic("word team goal.png");
                if(result.sim>0.8f){
                    result1=mFairy.findPic("word goal "+taskName+".png");
                    FindResult result2=mFairy.findPic("word section "+section+".png");
                    FindResult result13=mFairy.findPic(308,39,341,72,"word level "+level+".png");
                    if((createTeam&&result1.sim>0.8f&&result2.sim>0.8f&&result13.sim>0.8f)||(!createTeam&&result1.sim>0.8f)){
                        //目标正确
                        LtLog.e(mFairy.getLineInfo("创建队伍完成，进入招募阶段"));
                        changeGoal=false;
                        setTaskName(3);
                        return;
                    }else{
                        //目标不正确
                        LtLog.e(mFairy.getLineInfo("队伍队伍目标不正确，更改目标"));
                        if(!createTeam){
                            setTaskName(4);
                            LtLog.e(mFairy.getLineInfo("跟随队长，执行副本"));
                            return;
                        }
                        mFairy.onTap(400,46,418,63,"打开编辑界面",1000);
                        changeGoal=true;
                        return;
                    }
                }
            }

            //等待招募完成 进入副本 入队申请
            @Override
            public void content_3() throws Exception {
                result=mFairy.findPic("word edit.png");
                mFairy.onTap(0.8f,result,"编辑队伍",1000);

                result=mFairy.findPic("dui.png");
                mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                result=mFairy.findPic(909,220,986,257,"cancel.png");
                result1=mFairy.findPic("leave.png");
                if(result.sim>0.8f||result1.sim>0.8f){
                    mFairy.onTap(0.8f,result,"取消加入语音",1000);
                    LtLog.e(mFairy.getLineInfo("进入副本中，执行副本"));
                    setTaskName(4);
                    return;
                }

                if(createTeam){
                    if (timekeep(1,recruitTime,"recruit")){
                        LtLog.e(mFairy.getLineInfo("--------招募一下"));
                        util.recruit();
                        timekeepInit("recruit");
                    }

                    //队伍人数，人数满后前往副本，1分钟不来的踢出队伍
                    result=mFairy.findPic("word team goal.png");
                    if(result.sim>0.8f){
                        //判断人数
                        switch(teamNum){
                            case 3:
                                result=mFairy.findPic(42,354,106,417,"pic invite.png");
                                break;
                            case 4:
                                result=mFairy.findPic(44,437,110,507,"pic invite.png");
                                break;
                            case 5:
                                result=mFairy.findPic(42,523,110,587,"pic invite.png");
                                break;
                        }
                        //队伍组满人数，发起跟随，前往副本
                        if(result.sim<0.8f){
                            //关闭组队平台，点击前往
                            mFairy.onTap(1240,675,1245,680,"关闭组队平台",1000);
                        }
                    }

                    result=mFairy.findPic("fu_go1.png");
                    result1=mFairy.findPic(342,542,1034,675,"fu_jinru.png");
                    if(result.sim>0.8f&&result1.sim<0.8f){
                        mFairy.onTap(0.8f,result,"前往副本所在地",1000);
                    }

                    result=mFairy.findPic("fu_goal.png");
                    mFairy.onTap(0.8f,result,"进入副本详情",1000);

                    result=mFairy.findPic("fu_dui.png");
                    mFairy.onTap(0.8f,result,"打开申请列表",1000);


                    result=mFairy.findPic("ruduishenqing.png");
                    if(result.sim>0.8){
                        result=mFairy.findPic(895,213,968,537,"agree.png");
                        if(result.sim>0.8){
                            mFairy.onTap(0.8f,result,"同意入队申请",500);
                        }else{
                            mFairy.onTap(1013,142,1040,168,"关闭申请列表",500);
                        }
                    }

                    //30秒召集一次队伍
                    if(timekeep(1,30*1000,"team gather")){
                        result=mFairy.findPic(825,611,885,650,"fu_zhaoji1.png");
                        mFairy.onTap(0.8f,result,"召集队伍",1000);

                        result=mFairy.findPic(663,587,729,633,"fu_zhaoji1.png");
                        mFairy.onTap(0.8f,result,"队伍召集",1000);
                    }

                    //十秒进一次副本
                    if(timekeep(1,10*1000,"enter copy")){
                        //进入副本
                        result=mFairy.findPic(342,542,1034,675,"word challenge.png");
                        result1=mFairy.findPic("defeat.png");
                        if(result.sim>0.8&&result1.sim<0.8f){
                            mFairy.onTap(0.8f,result,"进入副本",1000);
                        }
                    }

                    result1=mFairy.findPic("defeat.png");
                    if(result1.sim>0.8f){
                        //踢出不在线 距离较远的
                        if(timekeep(0,60*1000,"not on line")){
                            result=mFairy.findPic(501,261,613,540,new String []{"offline.png","fu_too far.png","count not enough.png"});
                            mFairy.onTap(0.8f,result,result.x+364,result.y+13,result.x+365,result.y+14,"踢出不在线",500);
                        }else{
                            mFairy.onTap(966,179,981,191,"关闭组队失败页面",500);
                        }
                    }
                }else{
                    //匹配跟随
                    //被t出队伍/成为队长，重新匹配
                    result=mFairy.findPic("word zudui.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                        setTaskName(0);
                        return;
                    }

                    result1=mFairy.findPic("fu_header.png");
                    if(result1.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                        exit_team();
                        setTaskName(0);
                        return;
                    }

                    result=mFairy.findPic("shenqing.png");
                    mFairy.onTap(0.8f,result,"一键申请",500);

                    result=mFairy.findPic("word team goal.png");
                    mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                    result=mFairy.findPic("dui.png");
                    mFairy.onTap(0.8f,result,"切换跟队分页",1000);

                    result=mFairy.findPic(new String[]{"gen.png","zhan.png"});
                    if(result.sim<0.8f){
                        result=mFairy.findPic("fu_genzhan.png");
                        mFairy.onTap(0.8f,result,"跟战",1000);
                    }

                    boolean not_fight=timekeep(0,5*60*1000,"not fight");
                    if(not_fight){
                        LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                        util.exitTeam();
                        setTaskName(0);
                        return;
                    }
                }
            }

            int fightOver=0;
            //执行副本阶段
            @Override
            public void content_4() throws Exception {
                //带队
                if(createTeam){
                    //熄屏点击
                    result=mFairy.findPic("leave.png");
                    if(result.sim>0.8f){
                        result=mFairy.findPic("word team goal.png");
                        mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                        //不在战斗 开启自动战斗
                        result=mFairy.findPic("auto.png");
                        mFairy.onTap(0.8f,result,"开启自动作战",1000);

                        //没有攻击对象 点击指引 连续三次
                        result=mFairy.findPic("auto ing.png");
                        if(result.sim>0.8f){
                            result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});
                            if(result1.sim<0.8f){
                                mFairy.sleep(500);
                                if(fightOver++>3){
                                    result=mFairy.findPic("dui.png");
                                    mFairy.onTap(0.8f,result,107,316,125,331,"混元图录指引",1000);
                                    fightOver=0;
                                }
                            }else{
                                fightOver=0;
                            }
                        }
                    }

                    //游戏熄屏 不被分配挡住
                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    result1=mFairy.findPic("fu_fenpei.png");
                    if(result.sim<0.75f&&result1.sim<0.8f){
                        mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                    }

                    result=mFairy.findPic("dui.png");//副本分页
                    result1=mFairy.findPic("dui1.png");//队伍分页
                    boolean timekeep=timekeep(1,followTime,"follow");
                    if(result1.sim>0.8){
                        //队伍分页
                        if(timekeep){
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                        mFairy.onTap(14,214,27,229,"切换副本分页",1000);
                    }
                    if(result.sim>0.8f){
                        //副本分页
                        if(timekeep){
                            mFairy.onTap(13,324,22,334,"切换队伍分页",2000);
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                    }
                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    result1=mFairy.findPic("leave.png");
                    if(result.sim>0.7f){
                        if(result1.sim<0.7f){
                            if(timekeep(1,45*1000,"main interface")){
                                LtLog.e(mFairy.getLineInfo("大厅界面超时，重置任务"));
                                setTaskName(0);
                            }
                        }else{
                            timekeepInit("main interface");
                        }
                    }
                }else{
                    result=mFairy.findPic("leave.png");
                    if(result.sim>0.8f){
                        timekeepInit("not fight");

                        result=mFairy.findPic("pic conjure.png");
                        mFairy.onTap(0.8f,result,"开启遗迹冰魄/宝箱",5000);

                        result=mFairy.findPic("dui.png");
                        mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                    }else{
                        //被t出队伍/成为队长，重新匹配
                        result=mFairy.findPic("word zudui.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                            setTaskName(0);
                            return;
                        }

                        result1=mFairy.findPic("fu_header.png");
                        if(result1.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                            exit_team();
                            setTaskName(0);
                            return;
                        }

                        boolean not_fight=timekeep(0,5*60*1000,"not fight");
                        if(not_fight){
                            LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                            util.exitTeam();
                            setTaskName(0);
                            return;
                        }
                    }
                    result=mFairy.findPic("word team goal.png");
                    mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                    //游戏熄屏 不被分配挡住
                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    result1=mFairy.findPic("fu_fenpei.png");
                    if(result.sim<0.75f&&result1.sim<0.8f){
                        mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                    }
                }
                result=mFairy.findPic("pic eye.png");
                mFairy.onTap(0.8f,result,"进入观战模式",500);

                result=mFairy.findPic("fuhuo pic.png");
                mFairy.onTap(0.8f,result,635,341,645,352,"立即原地满血复活",200);

                //副本结束
                result1=mFairy.findPic(700,532,768,583,"leave1.png");
                if(result1.sim>0.8f){
                    //成功
                    result=mFairy.findPic("pic success.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本成功"));
                        setTaskEnd();
                    }
                    //失败
                    result=mFairy.findPic("pic failure.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本失败"));
                    }
                    mFairy.onTap(0.8f,result1,taskName+"副本结束，点击离开",1000);
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic(382,249,477,328,"pic hint.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(new String []{"word dissatisfy.png"});
                    if (result.sim>0.8f){
                        result=mFairy.findPic(698,396,756,465,"affirm.png");
                        mFairy.onTap(0.8f,result,"确定",500);

                        result=mFairy.findPic(695,426,762,471,"agree.png");
                        mFairy.onTap(0.8f,result,"同意",500);
                        return;
                    }
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"混元图录"+ taskList.get(0));
    }

    //日常副本-一条龙
    public void daily_dragon()throws Exception{
        new TeamTask(mFairy){
            List<String> list=new ArrayList();
            String taskName="";
            int saodngCount=0;
            long daze=0;
            //初始化 副本
            @Override
            public void content_0() throws Exception {
                util.close();
                taskName=list.get(0);

                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在副本中"));
                    setTaskName(4);
                    return;
                }
                //1.无队伍 2.队长 3.队员
                int teamState=util.teamState();
                if(createTeam){
                    if(teamState==3)util.exitTeam();//队员离队
                }else{
                    if(teamState==2)util.exitTeam();//队长离队

                    if(teamState==3){
                        setTaskName(3);
                        return;
                    }
                }
                timekeepInit("not fight");
                setTaskName(1);
                return;
            }
            //查找副本的完成情况

            @Override
            public void content_1() throws Exception {
                result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                if(result.sim>0.8f){
                    mFairy.sleep(1000);
                    LtLog.e(mFairy.getLineInfo("寻路中.."));
                    return;
                }
                if(overtime(20,0))LtLog.e(mFairy.getLineInfo("日常副本_content寻找副本超时，初始化"));

                result=mFairy.findPic(new String[]{"platform.png","fu_goal.png"});//组队平台
                if(result.sim>0.8f){
                    timekeepInit("init goal overtime");
                    setTaskName(2);
                    return;
                }

                result=mFairy.findPic("richang.png");
                mFairy.onTap(0.8f,result,"打开日常",1000);

                result=mFairy.findPic("pic richang interface.png");
                if(result.sim>0.8f){
                    //领取活跃奖励
                    result=mFairy.findPic(258,578,1114,605,"red tip.png");
                    mFairy.onTap(0.8f,result,"领取奖励",1000);
                    if(result.sim>0.8f)return;

                    result=mFairy.findPic("get goods.png");
                    mFairy.onTap(0.8f,result,629,404,638,414,"获得物品",1000);
                    if(result.sim>0.8f)return;
                }

                result=mFairy.findPic("pic challenge.png");
                mFairy.onTap(0.8f,result,"切换挑战分页",1000);

                //展开日常副本
                result=mFairy.findPic("word daily copy.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(result.x+110,result.y-6,result.x+191,result.y+30,"pic down.png");
                    mFairy.onTap(0.8f,result,"展开日常",1000);
                }

                //扫荡界面
                result=mFairy.findPic("word saodang interface.png");
                if(result.sim>0.8f){
                    if(saodang==0){
                        mFairy.onTap(0.8f,result,1022,148,1031,161,"关闭扫荡",1000);
                    }else if(saodang==1){
                        result=mFairy.findPic(371,251,421,293,"pic saodang selected.png");
                    }else if(saodang==2){
                        result=mFairy.findPic(369,364,422,417,"pic saodang selected.png");
                    }
                    if(result.sim>=0.8f){
                        mFairy.onTap(0.8f,result,728,512,738,524,"确定扫荡",2000);
                        if(++saodngCount>=3){
                            LtLog.e(mFairy.getLineInfo("扫荡4次，扫荡令不够"));
                            saodang=0;
                        }
                    }else {
                        mFairy.onTap(389,266+(saodang-1)*119,400,276+(saodang-1)*119,"选择"+saodang+"方式",1000);
                    }
                    return;
                }


                //选中副本
                result=mFairy.findPic("word "+taskName+" interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(new String[]{"word zero.png","word zero1.png"});
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo(taskName+"每日结算次数达到上限"));
                        setTaskEnd();
                        return;
                    }else{
                        result=mFairy.findPic("word start saodang.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("未开启扫荡功能"));
                            saodang=0;
                        }

                        //扫荡
                        if(saodang!=0){
                            result=mFairy.findPic("word saodang.png");
                            mFairy.onTap(0.8f,result,"扫荡",1000);
                            return;
                        }

                        mFairy.onTap(1226,67,1235,78,"找到"+taskName+"副本，前往组队",1000);
                        timekeepInit("init goal overtime");
                        setTaskName(2);
                        return;
                    }
                }else{
                    result=mFairy.findPic(136,167,250,401,"word "+taskName+".png");
                    mFairy.onTap(0.8f,result,"选中"+taskName+"副本",1000);
                }
            }

            boolean changeGoal=false;
            //创建队伍&&更改目标
            @Override
            public void content_2() throws Exception {
                //进入副本中
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("进入副本，执行副本"));
                    setTaskName(4);
                    return;
                }

                result=mFairy.findPic(new String[]{"dui.png","dui1.png"});
                mFairy.onTap(0.8f,result,"进入队伍",1000);

                result=mFairy.findPic("word team goal.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word goal a dragon.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("目标正确，前往招募"));
                        setTaskName(3);
                        return;
                    }else{
                        mFairy.onTap(420,68,449,74,"打开编辑队伍界面",1000);
                        util.initTeamGoal(2,"richangfuben","a dragon","");//更换目标
                    }
                }
            }

            //等待招募完成 进入副本 入队申请
            public void content_3() throws Exception{
                result=mFairy.findPic(new String[]{"dui.png","dui1.png"});
                mFairy.onTap(0.8f,result,"进入队伍详情",1000);

                result=mFairy.findPic("");

                //队伍详情
                result=mFairy.findPic("word team goal.png");
                if(result.sim>0.8f){
                    if(createTeam){
                        result=mFairy.findPic("word goal a dragon.png");
                        if(result.sim<0.8f){
                            mFairy.onTap(420,68,449,74,"编辑队伍",1000);
                            util.initTeamGoal(0,"","",null);
                        }
                    }
                }
            }

            @Override
            public void content_4() throws Exception{

            }

            int fightOver=0;//不在战斗计次
            int step=0, open=0;//河谷之森 打开笼子步骤 和 第几个笼子
            //执行副本阶段
            @Override
            public void content_5() throws Exception {
                //带队
                if(createTeam){
                    daze=util.dazeTime();
                    //魔神分舵
                    if(taskName.equals("msfd")){
                        result=mFairy.findPic("word strong.png");
                        mFairy.onTap(0.8f,result,1217,69,1236,85,"关闭误点界面",1000);

                        //副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic("auto.png");
                            mFairy.onTap(0.8f,result,"自动战斗",500);

                            result=mFairy.findPic("auto ing.png");
                            if(result.sim>0.8f){
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});
                                if(result1.sim<0.8f){
                                    if(fightOver++>=3){
                                        result=mFairy.findPic("dui.png");
                                        mFairy.onTap(0.8f,result,107,316,125,331,"魔神分舵指引",1000);
                                        fightOver=0;
                                    }
                                }else{
                                    fightOver=0;
                                }
                            }
                        }
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        if(result.sim<0.75f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    //湖心遗迹
                    if(taskName.equals("hxyj")){
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic("pic conjure.png");
                            if(result.sim>0.8f){
                                mFairy.onTap(0.8f,result,"开启遗迹冰魄",5000);
                                return;
                            }

                            //不在战斗，寻路下不自动战斗
                            result=mFairy.findPic("auto.png");
                            result1=mFairy.findPic(new String[]{"word find.png","word way.png"});
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                mFairy.onTap(0.8f,result,"自动作战",500);
                            }

                            //自动战斗，但是打的不是boss，点击指引
                            result=mFairy.findPic("auto ing.png");
                            result1=mFairy.findPic("pic hxyj boss.png");
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                result=mFairy.findPic("word copy goal.png");
                                mFairy.onTap(0.8f,result,66,207,87,225,"目标指引",1000);
                            }
                        }
                        //游戏熄屏 不被分配挡住
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic(new String[]{"fu_fenpei.png","world map.png"});
                        if(result.sim<0.75f&&result1.sim<0.8f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }
                    }

                    //河谷之森
                    if(taskName.equals("hgzs")){
                        result=mFairy.findPic("word 2.5D.png");
                        mFairy.onTap(0.8f,result,"切换3D视角",500);

                        int x=0,y=0;
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            result=mFairy.findPic(833,290,895,359,"pic conjure.png");
                            mFairy.onTap(0.8f,result,"打开",2000);

                            //不在战斗 自动寻路不点击 点击自动战斗
                            result=mFairy.findPic("auto.png");
                            result1=mFairy.findPic(new String[]{"word find.png","word way.png"});
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                mFairy.onTap(0.8f,result,"自动作战",500);
                            }

                            /**
                             * 打开三个笼子
                             * 如果左侧任务栏发现笼子 并且不在自动寻路(点击前往三个笼子 打开地图(并且制定坐标)) 关闭地图后 前往指引
                             */
                            result=mFairy.findPic(new String[]{"word cage.png","word cage1.png"});
                            if(result.sim>0.75f){
                                int time=0;
                                result=mFairy.findPic("dui.png");
                                if(result.sim>0.8f){
                                    result=mFairy.findPic("pic hgzs time 2.png");
                                    time=result.sim>0.8f?2:1;
                                    LtLog.e(mFairy.getLineInfo("第"+time+"处笼子"));
                                }

                                //第一处的 打开笼子
                                if(time==3){
                                    //没有正在打开 和 自动寻路
                                    result=mFairy.findPic(new String[]{"word find.png","word way.png","pic transfer.png"});
                                    if(result1.sim<0.8f){
                                        if(step==0){

                                            result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                            result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                            if(result.sim>0.8f || result1.sim >0.8f){
                                                mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                                            }
                                            x=426;y=440;
                                            step=1;
                                        }
                                        else if(step==1){
                                            result=mFairy.findPic("word copy goal.png");
                                            mFairy.onTap(0.8f,result,66,207,87,225,"打开笼子，目标指引",2000);
                                            if(result.sim>0.8f)step=2;
                                        }
                                        else if(step==2) {
                                            result1 = mFairy.findPic(new String[]{"word find.png", "word way.png"});
                                            if (result1.sim < 0.8f) {
                                                //第一处的笼子
                                                switch (open) {
                                                    case 0:
                                                        mFairy.ranSwipe(191, 586, 35, 575, 500, 2000);
                                                        mFairy.onTap(322, 320, 332, 330, "打开笼子1", 2000);
                                                        mFairy.onTap(362, 194, 373, 205, "打开笼子1", 2000);
                                                        open = 1;
                                                        break;
                                                    case 1:
                                                        mFairy.onTap(524, 310, 538, 322, "打开笼子2", 2000);
                                                        mFairy.onTap(709,294,715,299, "打开笼子2", 2000);
                                                        mFairy.onTap(561, 206, 571, 215, "打开笼子2", 2000);
                                                        open = 2;
                                                        break;
                                                    case 2:
                                                        mFairy.onTap(849, 318, 860, 328, "打开笼子3", 2000);
                                                        mFairy.onTap(1007, 325, 1017, 336, "打开笼子3", 2000);
                                                        open = 0;
                                                        break;
                                                }
                                                step=0;
                                            }
                                        }
                                    }
                                }//第一处

                                //第一处的 打开笼子
                                if(time==1){
                                    //没有正在打开 和 自动寻路
                                    result=mFairy.findPic(new String[]{"word find.png","word way.png","pic transfer.png"});
                                    if(result1.sim<0.8f){
                                        if(step==0){
                                            result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                            result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                            if(result.sim>0.8f || result1.sim >0.8f){
                                                mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                                            }
                                            x=426;y=440;
                                            step=1;
                                        }
                                        else if(step==1){
                                            result=mFairy.findPic("word copy goal.png");
                                            mFairy.onTap(0.8f,result,66,207,87,225,"打开笼子，目标指引",2000);
                                            if(result.sim>0.8f)step=2;
                                        }
                                        else if(step==2) {
                                            result1 = mFairy.findPic(new String[]{"word find.png", "word way.png"});
                                            if (result1.sim < 0.8f) {
                                                mFairy.sleep(1000);
                                                //第一处的笼子
                                                switch (open) {
                                                    case 0:
                                                        mFairy.ranSwipe(191, 586, 35, 575, 500, 2000);
                                                        mFairy.onTap(322, 320, 332, 330, "打开笼子1", 1000);
                                                        mFairy.onTap(322, 320, 332, 330, "打开笼子1", 3000);
                                                        open = 1;
                                                        break;
                                                    case 1:
                                                        mFairy.onTap(524, 310, 538, 322, "打开笼子2", 1000);
                                                        mFairy.onTap(524, 310, 538, 322, "打开笼子2", 3000);
                                                        open = 2;
                                                        break;
                                                    case 2:
                                                        mFairy.onTap(849, 318, 860, 328, "打开笼子3", 1000);
                                                        mFairy.onTap(849, 318, 860, 328, "打开笼子3", 3000);
                                                        open = 0;
                                                        break;
                                                }
                                                step=0;
                                            }
                                        }
                                    }
                                }//第一处

                                //第二处的 打开笼子
                                if(time==2){
                                    result=mFairy.findPic(new String[]{"word find.png","word way.png","pic transfer.png"});//自动寻路
                                    if(result.sim<0.8f){
                                        switch (open){
                                            case 0:
                                                x=817;y=180;
                                                open=1;
                                                break;
                                            case 1:
                                                x=847;y=220;
                                                open=2;
                                                break;
                                            case 2:
                                                x=845;y=238;
                                                open=0;
                                                break;
                                        }
                                        result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                        result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                        if(result.sim>0.8f || result1.sim >0.8f){
                                            mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                                        }
                                    }
                                }//第二处
                            }//打开笼子

                            result=mFairy.findPic("auto ing.png");//自动战斗
                            if(result.sim>0.8f){
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});//血条 和 boss
                                if(result1.sim>0.8f){
                                    fightOver=0;
                                }else{
                                    if(fightOver++>=5){
                                        result=mFairy.findPic("word copy goal.png");
                                        mFairy.onTap(0.8f,result,66,207,87,225,"目标指引",2000);
                                        fightOver=0;
                                    }
                                }
                            }//自动战斗
                        }//在副本里面

                        result=mFairy.findPic("world map.png");//地图 去指定坐标
                        mFairy.onTap(0.8f,result,x,y,x+1,y+1,"定位前往1",1000);
                        mFairy.onTap(0.8f,result,x,y,x+1,y+1,"定位前往2",1000);
                        mFairy.onTap(0.8f,result,1225,42,1237,54,"关闭区域地图",1000);

                        //熄屏点击
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic("world map.png");
                        if(result.sim<0.75f&&result1.sim<0.7f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }

                        result=mFairy.findPic(955,525,1019,571,"pet select.png");
                        mFairy.onTap(0.8f,result,"选择宠物",1000);

                        result=mFairy.findPic(601,548,672,587,"affirm.png");
                        mFairy.onTap(0.8f,result,"确定选择宠物",500);
                    }

                    //毁灭遗迹
                    if(taskName.equals("hmyj")){
                        int x=0,y=0;
                        //在副本中
                        result=mFairy.findPic("leave.png");
                        if(result.sim>0.8f){
                            //自动战斗，但没有目标，点击指引
                            result=mFairy.findPic("auto ing.png");
                            if(result.sim>0.8f&&result1.sim<0.8f){
                                //选中目标战斗中
                                result1=mFairy.findPic(new String[]{"pic blood line.png","pic hxyj boss.png"});
                                if(result1.sim>0.8f){
                                    fightOver=0;
                                }else{
                                    if(fightOver++>=3){
                                        result=mFairy.findPic("word copy goal.png");
                                        mFairy.onTap(0.8f,result,66,207,87,225,"目标指引",2000);
                                    }
                                }
                            }

                            //不在自动寻路中
                            result1=mFairy.findPic(new String[]{"word find.png","word way.png"});
                            if(result1.sim<0.8f){
                                //自动作战
                                result=mFairy.findPic("auto.png");
                                mFairy.onTap(0.8f,result,"自动作战",1000);

                                //选择一条通路
                                result=mFairy.findPic("word goal way.png");
                                if(result.sim>0.8f){
                                    result1=mFairy.findPic("pic open way.png");
                                    mFairy.onTap(0.8f,result1,"打开通路",4000);
                                    if(result1.sim<0.8f){
                                        result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                        result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                        if(result.sim>0.8f || result1.sim >0.8f){
                                            mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                                        }
                                        x=718;y=367;
                                    }
                                }

                                //打开所有宝箱
                                result=mFairy.findPic("word hmyj box.png");
                                result1=mFairy.findPic("pic conjure.png");
                                if(result.sim>0.8f&&result1.sim<0.8f){
                                    if(open++>6){
                                        open=1;
                                    }
                                    result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                                    result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                                    if(result.sim>0.8f || result1.sim >0.8f){
                                        mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                                    }
                                    switch (open){
                                        case 1:
                                            x=708;y=345;
                                            break;
                                        case 2:
                                            x=693;y=345;
                                            break;
                                        case 3:
                                            x=708;y=366;
                                            break;
                                        case 4:
                                            x=693;y=366;
                                            break;
                                        case 5:
                                            x=708;y=390;
                                            break;
                                        case 6:
                                            x=693;y=390;
                                            break;
                                    }
                                }

                                //对话神秘人
                                result=mFairy.findPic("word hmyj dialogue.png");
                                if(result.sim>0.8f){
                                    mFairy.ranSwipe(879,206,1105,206,500,1000l,0);
                                    mFairy.onTap(0.8f,result,630,374,636,381,"对话神秘人",1000);

                                    result=mFairy.findPic(877,215,946,331,new String[]{"word heilao.png","word longshan.png","word lao.png"});
                                    mFairy.onTap(0.8f,result,"选中黑老",1000);
                                }
                                result=mFairy.findPic(53,389,100,515,"fu_goal.png");
                                mFairy.onTap(0.8f,result,"回答神秘人",1000);


                                //定点到通路
                                result=mFairy.findPic("world map.png");
                                if(result.sim>0.8){
                                    mFairy.onTap(x,y,x+1,y+1,"定位前往1",1000);
                                    mFairy.onTap(x,y,x+1,y+1,"定位前往2",1000);

                                    result = mFairy.findPic(890,3,1275,235,new String[]{"close.png","close3.png"});
                                    mFairy.onTap(0.8f,result,"关闭区域地图",200);
                                }
                            }
                        }

                        //游戏熄屏 不被分配挡住
                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        result1=mFairy.findPic(new String []{"fu_fenpei.png","world map.png"});
                        if(result.sim<0.75f&&result1.sim<0.8f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }

                        result=mFairy.findPic("pic conjure.png");
                        mFairy.onTap(0.8f,result,"打开宝箱",5000);
                    }

                    result=mFairy.findPic("dui1.png");//队伍分页
                    boolean timekeep=timekeep(1,followTime,"follow");
                    if(result.sim>0.8){
                        //队伍分页
                        if(timekeep){
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                        mFairy.onTap(14,214,27,229,"切换副本分页",1000);
                    }

                    result=mFairy.findPic("dui.png");//副本分页
                    if(result.sim>0.8f){
                        //副本分页
                        if(timekeep){
                            mFairy.onTap(13,324,22,334,"切换队伍分页",2000);
                            mFairy.onTap(210,410,222,418,"发起跟站",1000);
                            timekeepInit("follow");
                        }
                    }

                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    if(result.sim>0.7f){
                        result=mFairy.findPic("leave.png");
                        if(result.sim<0.7f){
                            if(timekeep(1,45*1000,"main interface")){
                                LtLog.e(mFairy.getLineInfo("大厅界面超时，重置任务"));
                                setTaskName(0);
                            }
                        }else{
                            timekeepInit("main interface");
                        }
                    }
                }//带队

                else{
                    result=mFairy.findPic("leave.png");//在副本中
                    if(result.sim>0.8f){
                        timekeepInit("not fight");

                        result=mFairy.findPic("pic conjure.png");
                        mFairy.onTap(0.8f,result,"开启遗迹冰魄/宝箱",5000);

                        result=mFairy.findPic("dui.png");
                        mFairy.onTap(0.8f,result,"切换队伍分页",1000);
                    }else{
                        //被t出队伍/成为队长，重新匹配
                        result1=mFairy.findPic("fu_header.png");
                        if(result1.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                            exit_team();
                            setTaskName(0);
                            return;
                        }

                        result=mFairy.findPic("word zudui.png");
                        if(result.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                            setTaskName(0);
                            return;
                        }

                        boolean not_fight=timekeep(0,5*60*1000,"not fight");
                        if(not_fight){
                            LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                            util.exitTeam();
                            setTaskName(0);
                            return;
                        }
                    }

                    result=mFairy.findPic("word team goal.png");
                    mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                    //不在跟战，点击跟战
                    result=mFairy.findPic(new String[]{"dong.png","zhan.png"});
                    if(result.sim<0.8f){
                        result=mFairy.findPic("fu_genzhan.png");
                        mFairy.onTap(0.8f,result,"点击跟战",1000);
                    }

                    //游戏熄屏 不被分配挡住
                    result=mFairy.findPic(new String[]{"list.png","list1.png"});
                    result1=mFairy.findPic("fu_fenpei.png");
                    if(result.sim<0.75f&&result1.sim<0.8f){
                        mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                    }

                    if(daze>25){
                        LtLog.e(mFairy.getLineInfo("任务超时，重置任务"));
                        setTaskName(0);
                        return;
                    }
                }//跟队

                result=mFairy.findPic("fuhuo pic.png");
                mFairy.onTap(0.8f,result,635,341,645,352,"立即原地满血复活",500);

                //副本结束
                result1=mFairy.findPic(700,532,768,583,"leave1.png");
                if(result1.sim>0.8f){
                    //成功
                    result=mFairy.findPic("pic success.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本成功"));
                        setTaskEnd();
                    }
                    //失败
                    result=mFairy.findPic("pic failure.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("副本失败"));
                    }
                    mFairy.onTap(0.8f,result1,taskName+"副本结束，点击离开",1000);
                    setTaskName(0);
                    return;
                }

                //一键需求
                result=mFairy.findPic("fu_fenpei.png");
                if(result.sim>0.8){
                    if(need){
                        result=mFairy.findPic("fu_need.png");
                        if(result.sim>0.8f){
                            mFairy.onTap(0.8f,result,"获得分配,一键需求",500);

                            result=mFairy.findPic("pic allocation back.png");
                            mFairy.onTap(0.8f,result,"收回分配页",500);
                        }
                    }else{
                        result=mFairy.findPic("pic allocation back.png");
                        mFairy.onTap(0.8f,result,"获得分配,放弃参与分配",500);
                    }
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f)daze=util.initDaze();


                result=mFairy.findPic("fu_couqi.png");
                mFairy.onTap(0.8f,result,717,405,732,420,"人已凑齐,发起跟随",500);

                result=mFairy.findPic("word hint recharge.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,550,419,554,422    ,"元宝不足,取消充值",1000);
                    saodang=0;
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"日常副本-一条龙");
    }

//////////////////////////////////////////////////////////
    static String taskName="";
    static boolean  changeGoal=false;
    static int picCount_platform=0,saodang=0;

    //初始化副本
    @Override
    public void content_0() throws Exception {
        util.close();
        result=mFairy.findPic("dui.png");
        mFairy.onTap(0.8f,result,"切换组队分页",1500);

        //在队伍中
        result=mFairy.findPic(58,396,242,430,"fu_lidui.png");
        if(result.sim>0.8f){
            result1=mFairy.findPic("fu_header.png");
            //带队
            if(createTeam){
                //不是队长
                if(result1.sim<0.8f){
                    mFairy.onTap(0.8f,result,"离队",1000);
                    return;
                }
            }else{
            //跟队
                //是队长
                if(result1.sim>0.8f){
                    mFairy.onTap(0.8f,result,"离队",1000);
                    return;
                }
                taskName=taskList.get(0);
                setTaskName(3);
                return;
            }
        }
        timekeepInit("not fight");
        setTaskName(1);
    }

    //查看完成情况
    @Override
    public void content_1() throws Exception {
        result=mFairy.findPic(new String[]{"word find.png","word way.png"});
        if(result.sim>0.8f){
            mFairy.sleep(1000);
            LtLog.e(mFairy.getLineInfo("寻路中.."));
            return;
        }
        if(overtime(20,0))LtLog.e(mFairy.getLineInfo("日常副本_content寻找副本超时，初始化"));

        result=mFairy.findPic(new String[]{"platform.png","fu_goal.png"});//组队平台
        if(result.sim>0.8f){
            timekeepInit("init goal overtime");
            setTaskName(2);
            return;
        }

        result=mFairy.findPic("richang.png");
        mFairy.onTap(0.8f,result,"打开日常",1000);

        result=mFairy.findPic("pic richang interface.png");
        if(result.sim>0.8f){
            //领取活跃奖励
            result=mFairy.findPic(258,578,1114,605,"red tip.png");
            if(result.sim>0.8f){
                mFairy.onTap(0.8f,result,"领取奖励",1000);
                return;
            }

            result=mFairy.findPic("get goods.png");
            if(result.sim>0.8f){
                mFairy.onTap(0.8f,result,629,404,638,414,"获得物品",1000);
                return;
            }
        }

        result=mFairy.findPic("pic challenge.png");
        mFairy.onTap(0.8f,result,"切换挑战分页",1000);

        //选中副本
        result=mFairy.findPic("word "+taskName+" interface.png");
        if(result.sim>0.8f){
            result=mFairy.findPic("word zero.png");
            if(result.sim>0.8f){
                LtLog.e(mFairy.getLineInfo(taskName+"每日结算次数达到上限"));
                setTaskEnd();
                return;
            }else{
                mFairy.onTap(1226,67,1235,78,"找到"+taskName+"副本，前往组队",1000);
                timekeepInit("init goal overtime");
                setTaskName(2);
                return;
            }
        }else{
            result=mFairy.findPic(136,167,250,401,"word "+taskName+".png");
            mFairy.onTap(0.8f,result,"选中"+taskName+"副本",1000);
        }
    }

    //创建队伍 或者更改目标
    @Override
    public void content_2() throws Exception {
        //进入副本中
        result=mFairy.findPic("leave.png");
        if(result.sim>0.8f){
            LtLog.e(mFairy.getLineInfo("进入副本，执行副本"));
            setTaskName(4);
            return;
        }

        //超时处理
        result=mFairy.findPic("platform.png");
        result1=mFairy.findPic("word copy.png");
        if(result.sim<0.8f&&result1.sim<0.8){
            if(timekeep(0,3*60*1000,"init goal overtime")){
                LtLog.e(mFairy.getLineInfo("更换目标超时"));
                setTaskName(0);
                return;
            }
        }else{
            timekeepInit("init goal overtime");
        }

        //打开组队平台
        result=mFairy.findPic("dui.png");
        mFairy.onTap(0.8f,result,"切换队伍分页",1000);

        result=mFairy.findPic("dui1.png");
        mFairy.onTap(0.8f,result,"打开组队平台",1000);

        if(picCount_platform>1) {
            if (createTeam) {
                //创建队伍
                result = mFairy.findPic(715, 507, 780, 553, "chuangjian.png");
                if (result.sim < 0.8f) {
                    result = mFairy.findPic("chuangjian.png");
                }
                mFairy.onTap(0.8f, result, "创建队伍", 2000);
                int picCount1 = picCount(0.8f, result, "create team");
                if (picCount1 >= 5) {
                    LtLog.e(mFairy.getLineInfo("无法创建队伍"));
                    setTaskEnd();
                    return;
                }
            } else {
                //匹配跟随
                result = mFairy.findPic("fu_header.png");
                if (result.sim > 0.8) {
                    LtLog.e(mFairy.getLineInfo("匹配出错，重新开始组队"));
                    setTaskName(0);
                    return;
                }

                //等级不足
                result=mFairy.findPic(595,14,682,117,"word level none.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo(taskName+"等级不够，结束当前副本"));
                    taskList.remove(0);
                    setTaskEnd();
                    return;
                }

                result = mFairy.findPic("fu_pipei.png");
                result1 = mFairy.findPic("auto1.png");
                if (result.sim > 0.8 && result1.sim > 0.8) {
                    mFairy.onTap(0.8f, result, "自动匹配", 500);
                } else {
                    result = mFairy.findPic("refresh.png");
                    mFairy.onTap(0.8f, result, "刷新", 500);
                }

                result = mFairy.findPic("shenqing.png");
                mFairy.onTap(0.8f, result, "一键申请", 500);
            }
        }

        if(changeGoal){
            result=mFairy.findPic("word edit interface.png");
            if(result.sim>0.8f){
                util.initTeamGoal(2,"richangfuben",taskName,"");

                result=mFairy.findPic("word edit.png");
                mFairy.onTap(0.8f,result,"编辑队伍",1000);
                int picCount1 = picCount(0.8f, result, "edit team");
                if (picCount1 >= 5) {
                    LtLog.e(mFairy.getLineInfo("无法编辑队伍"));
                    setTaskEnd();
                    return;
                }
            }
        }

        //组队平台 目标正确进入 3 招募阶段
        result=mFairy.findPic("word team goal.png");
        if(result.sim>0.8f){
            if(createTeam){
                //带队
                result1=mFairy.findPic("word goal "+taskName+".png");
                if(result1.sim>0.8f){
                    //目标正确
                    LtLog.e(mFairy.getLineInfo("创建队伍完成，进入招募阶段"));
                    changeGoal=false;
                    setTaskName(3);
                    return;
                }else{
                    //目标不正确
                    LtLog.e(mFairy.getLineInfo("队伍队伍目标不正确，更改目标"));
                    if(!createTeam){
                        setTaskName(4);
                        LtLog.e(mFairy.getLineInfo("跟随队长，执行副本"));
                        return;
                    }
                    mFairy.onTap(400,46,418,63,"打开编辑界面",1000);
                    changeGoal=true;
                    return;
                }
            }else{
                //跟队
                mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台界面，进入招募阶段",1000);
                setTaskName(3);
                return;
            }
        }
    }

    //等待招募
    @Override
    public void content_3() throws Exception {
        result=mFairy.findPic("word edit.png");
        mFairy.onTap(0.8f,result,"编辑队伍",1000);

        result=mFairy.findPic("dui.png");
        mFairy.onTap(0.8f,result,"切换队伍分页",1000);

        result=mFairy.findPic("leave.png");
        if(result.sim>0.8f){
            LtLog.e(mFairy.getLineInfo("进入副本中，执行副本"));
            setTaskName(4);
            return;
        }

        if(createTeam){
            if (timekeep(1,recruitTime,"recruit")){
                LtLog.e(mFairy.getLineInfo("--------招募一下"));
                util.recruit();
                timekeepInit("recruit");
            }


            //队伍人数，人数满后前往副本，1分钟不来的踢出队伍
            result=mFairy.findPic("word team goal.png");
            if(result.sim>0.8f){
                //判断人数
                switch(teamNum){
                    case 3:
                        result=mFairy.findPic(42,354,106,417,"pic invite.png");
                        break;
                    case 4:
                        result=mFairy.findPic(44,437,110,507,"pic invite.png");
                        break;
                    case 5:
                        result=mFairy.findPic(42,523,110,587,"pic invite.png");
                        break;
                }
                //队伍组满人数，发起跟随，前往副本
                if(result.sim<0.8f){
                    //关闭组队平台，点击前往
                    mFairy.onTap(1240,675,1245,680,"关闭组队平台",1000);
                }
            }

            result=mFairy.findPic("fu_go1.png");
            result1=mFairy.findPic(342,542,1034,675,"fu_jinru.png");
            if(result.sim>0.8f&&result1.sim<0.8f){
                mFairy.onTap(0.8f,result,"前往副本所在地",1000);
            }

            result=mFairy.findPic("fu_goal.png");
            mFairy.onTap(0.8f,result,"进入副本详情",1000);

            result=mFairy.findPic("fu_dui.png");
            mFairy.onTap(0.8f,result,"打开申请列表",1000);

            result=mFairy.findPic("ruduishenqing.png");
            if(result.sim>0.8){
                result=mFairy.findPic(895,213,968,537,"agree.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,"同意入队申请",500);
                }else{
                    mFairy.onTap(1013,142,1040,168,"关闭申请列表",500);
                }
            }

            //30秒召集一次队伍
            if(timekeep(1,30*1000,"team gather")){
                result=mFairy.findPic(825,611,885,650,"fu_zhaoji1.png");
                mFairy.onTap(0.8f,result,"召集队伍",1000);

                result=mFairy.findPic(663,587,729,633,"fu_zhaoji1.png");
                mFairy.onTap(0.8f,result,"队伍召集",1000);
            }

            //10秒进一次副本
            if(timekeep(1,10*1000,"enter copy")){
                //进入副本
                result=mFairy.findPic("defeat.png");
                if(result.sim<0.8f){
                    result=mFairy.findPic(342,542,1034,675,"fu_jinru.png");
                    mFairy.onTap(0.8f,result,"进入副本",1000);

                    result=mFairy.findPic(1053,606,1125,652,"word challenge.png");
                    mFairy.onTap(0.8f,result,"挑战副本",1000);
                }
            }

            result1=mFairy.findPic("defeat.png");
            if(result1.sim>0.8f){
                //踢出不在线 距离较远的
                if(timekeep(0,60*1000,"not on line")){
                    result=mFairy.findPic(501,261,613,540,new String []{"offline.png","fu_too far.png","count not enough.png"});
                    mFairy.onTap(0.8f,result,result.x+364,result.y+13,result.x+365,result.y+14,"踢出不在线",500);
                }else{
                    mFairy.onTap(966,179,981,191,"关闭组队失败页面",500);
                }
            }
        }else{
        //匹配跟随
            //被t出队伍，重新匹配
            result=mFairy.findPic("word zudui.png");
            if(result.sim>0.8f){
                LtLog.e(mFairy.getLineInfo("不在队伍中，重新去组队"));
                setTaskName(0);
                return;
            }

            //成为队长
            result1=mFairy.findPic("fu_header.png");
            if(result1.sim>0.8f){
                LtLog.e(mFairy.getLineInfo(0.8f,result1,"成为队长，重新组队"));
                exit_team();
                setTaskName(0);
                return;
            }

            result=mFairy.findPic("word team goal.png");
            mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

            result=mFairy.findPic("dui.png");
            mFairy.onTap(0.8f,result,"切换跟队分页",1000);

            result=mFairy.findPic(new String[]{"gen.png","zhan.png"});
            if(result.sim<0.8f){
                result=mFairy.findPic("fu_genzhan.png");
                mFairy.onTap(0.8f,result,"跟战",1000);
            }

            //五分钟不进副本，超时处理
            boolean not_fight=timekeep(0,5*60*1000,"not fight");
            if(not_fight){
                LtLog.e(mFairy.getLineInfo("超过五分钟没有进副本，重新组队"));
                util.exitTeam();
                setTaskName(0);
                return;
            }
        }
    }

    //执行副本
    @Override
    public void content_4() throws Exception {
        //跟队
        if(!createTeam){
            //在副本中
            result=mFairy.findPic("leave.png");
            if(result.sim>0.8f){
                //不在跟战，点击跟战
                result=mFairy.findPic(new String[]{"dong.png","zhan.png"});
                if(result.sim<0.8f){
                    result=mFairy.findPic("fu_genzhan.png");
                    mFairy.onTap(0.8f,result,"点击跟战",1000);
                }

                result=mFairy.findPic("word copy goal.png");
                if(result.sim<0.8f){
                    result=mFairy.findPic("dui.png");
                    mFairy.onTap(0.8f,result,"切换跟队分页",1000);
                }

                //游戏熄屏 不被分配挡住
                result=mFairy.findPic(new String[]{"list.png","list1.png"});
                result1=mFairy.findPic("fu_fenpei.png");
                if(result.sim<0.75f&&result1.sim<0.8f){
                    mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                }
            }
        }

        result=mFairy.findPic("pic eye.png");
        mFairy.onTap(0.8f,result,"进入观战模式",500);

        result=mFairy.findPic("fuhuo pic.png");
        mFairy.onTap(0.8f,result,635,341,645,352,"立即原地满血复活",500);

        //副本结束
        result1=mFairy.findPic(700,532,768,583,"leave1.png");
        if(result1.sim>0.8f){
            //成功
            result=mFairy.findPic("pic success.png");
            if(result.sim>0.8f){
                LtLog.e(mFairy.getLineInfo("副本成功"));
                setTaskEnd();
            }
            //失败
            result=mFairy.findPic("pic failure.png");
            if(result.sim>0.8f){
                LtLog.e(mFairy.getLineInfo("副本失败"));
            }
            mFairy.onTap(0.8f,result1,taskName+"副本结束，点击离开",1000);
            setTaskName(0);
            return;
        }
    }

    @Override
    public void inOperation() throws Exception {
        result=mFairy.findPic("word invite.png");
        mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭邀请界面",1000);

        result=mFairy.findPic("word to strong interface.png");
        mFairy.onTap(0.8f,result,1222,75,1232,83,"关闭增强界面",1000);

        result=mFairy.findPic(920,460,1027,499,"word use.png");
        mFairy.onTap(0.8f,result,"自动使用",1000);

        result=mFairy.findPic(600,360,680,470,new String[]{"affirm.png","affirm1.png"});
        mFairy.onTap(0.8f,result,"点击确认",500);

        result=mFairy.findPic("pic open task.png");
        mFairy.onTap(0.8f,result,"打开任务列表页",1000);

        result=mFairy.findPic(909,220,986,257,"cancel.png");
        mFairy.onTap(0.8f,result,"取消加入语音",1000);

        result=mFairy.findPic(382,249,477,328,"pic hint.png");
        if(result.sim>0.8f){
            //队伍跟换目标
            result=mFairy.findPic(583,301,653,350,"word hint change.png");
            if(result.sim>0.8f){
                if(createTeam){
                    mFairy.onTap(0.8f,result,539,413,548,424,"退出队伍",500);
                    setTaskName(0);
                    return;
                }else{
                    mFairy.onTap(0.8f,result,724,410,736,422,"同意",500);
                }
            }


            //确定 凑齐人发起跟随，退出队伍，响应队长召集/跟战
            result=mFairy.findPic(new String []{"fu_couqi.png","exit1.png","fu_zhaoji.png","word hint enter.png","word teammate.png"});
            result1=mFairy.findPic(434,307,831,360,new String[]{"word hint genzhan.png","word hint ti.png"});
            if (result.sim>0.8f||result1.sim>0.8f){
                result=mFairy.findPic(696,345,759,480,"affirm.png");
                mFairy.onTap(0.8f,result,"确定",1000);

                result=mFairy.findPic(695,426,762,471,"agree1.png");
                mFairy.onTap(0.8f,result,"同意",1000);
                return;
            }

            //取消
            result=mFairy.findPic(500,345,603,480,"cancel.png");
            mFairy.onTap(0.8f,result,"取消",1000);

            result1=mFairy.findPic("fu_refuse.png");
            mFairy.onTap(0.8f,result1,"拒绝",500);

            if(result.sim<0.8f&&result1.sim<0.8f){
                mFairy.onTap(537,435,543,440,"取消",1000);
            }
        }
    }

    //离开副本
    public void exit_team()throws Exception{
        result=mFairy.findPic("leave.png");
        mFairy.onTap(0.8f,result,"离开副本",1000);
        mFairy.onTap(0.8f,result,715,442,738,458,"离开副本-确定",1000);
    }

    //副本-跟随测试
    public void team_follow()throws Exception{
        new TeamTask(mFairy){
            //初始化 副本
            @Override
            public void content_0() throws Exception {
                result=mFairy.findPic("dui.png");
                mFairy.onTap(0.8f,result,"切换队伍分页",1000);

                result=mFairy.findPic("word team goal.png");
                mFairy.onTap(0.8f,result,1240,675,1245,680,"关闭组队平台",1000);

                //不在跟战，点击跟战
                result=mFairy.findPic(new String[]{"dong.png","zhan.png"});
                if(result.sim<0.8f){
                    result=mFairy.findPic("fu_genzhan.png");
                    mFairy.onTap(0.8f,result,"点击跟战",1000);
                }

                //游戏熄屏 不被分配挡住
                result=mFairy.findPic(new String[]{"list.png","leave.png","fu_fenpei.png"});
                if(result.sim<0.7f) mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);

                result=mFairy.findPic("pic eye.png");
                mFairy.onTap(0.8f,result,"进入观战模式",500);

                result=mFairy.findPic("fuhuo pic.png");
                mFairy.onTap(0.8f,result,635,341,645,352,"立即原地满血复活",500);

                //副本结束
                result1=mFairy.findPic(700,532,768,583,"leave1.png");
                if(result1.sim>0.8f){
                    //成功
                    result=mFairy.findPic("pic success.png");
                    LtLog.e(mFairy.getLineInfo(0.8f,result,"副本成功"));

                    //失败
                    result=mFairy.findPic("pic failure.png");
                    LtLog.e(mFairy.getLineInfo(0.8f,result,"副本失败"));
                    mFairy.onTap(0.8f,result1,"副本结束，点击离开",1000);
                }

                result=mFairy.findPic("word to strong interface.png");
                mFairy.onTap(0.8f,result,1222,75,1232,83,"关闭增强界面",1000);
            }
        }.taskContent(mFairy,"副本跟随测试");
    }
}
