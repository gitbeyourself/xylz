package com.script.fairy;

import com.script.framework.AtFairyImpl;
import com.script.framework.TaskContent;
import com.script.opencvapi.AtFairyConfig;
import com.script.opencvapi.FindResult;
import com.script.opencvapi.LtLog;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class TimingActivity extends TaskContent {
    AtFairyImpl mFairy;
    FindResult result;
    LimitLessTask timingActivity;
        Util util;

    public TimingActivity(AtFairyImpl atFairy) throws Exception {
        mFairy = atFairy;
        util = new Util(mFairy);
    }

    //清理邮件
    public void hang_email()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            int clear=0;
            @Override
            public void content_1() throws Exception {
                result=mFairy.findPic("pic shejiao.png");
                mFairy.onTap(0.8f,result,"打开社交",500);

                result=mFairy.findPic("email.png");
                mFairy.onTap(0.8f,result,"打开邮箱",500);

                result=mFairy.findPic("a key get.png");
                mFairy.onTap(0.8f,result,"一键领取",1000);

                result=mFairy.findPic("a key del.png");
                result1=mFairy.findPic("none email.png");
                if(result1.sim<0.8){
                    mFairy.onTap(0.8f,result,"一键清理",1000);
                }

                result=mFairy.findPic(527,15,618,120,"word back already full.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("背包已满，清理背包后在清理邮件"));
                    hang_clear();
                    if(clear++>=2){
                        setTaskEnd();
                    }
                }

                result=mFairy.findPic("none email.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,1217,68,1234,85,"关闭邮箱界面",1000);
                    LtLog.e(mFairy.getLineInfo(0.8f,result,"领取邮件完成"));
                    setTaskEnd();
                    return;
                }
                overtime(20,0);
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("email delete.png");
                mFairy.onTap(0.8f,result,723,412,736,424,"确定删除所有已读附件",1000);
                super.inOperation();
            }
        }.taskContent(mFairy,"清理邮件");
    }

    //远征收获
    public void hang_expedition()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void create() throws Exception {
                setTaskName(1);
            }

            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                overtime(30,0);
                result = mFairy.findPic(new String[]{"list.png","list1.png","list3.png"});
                mFairy.onTap(0.8f,result,"打开列表",2000);

                result=mFairy.findPic(1089,526,1161,563,"word none open.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo(0.8f,result1,"社交功能暂未开放"));
                    expedition=false;
                    setTaskEnd();
                }

                result=mFairy.findPic("yuanzheng.png");
                mFairy.onTap(0.8f,result,"打开远征",2000);

                result=mFairy.findPic("yuanzheng task.png");
                result1=mFairy.findPic(1257,122,1280,149,"red tip.png");
                if(result.sim>0.8&&result1.sim<0.8){
                    result=mFairy.findPic("word dispatch interface.png");
                    mFairy.onTap(0.8f,result,1148,69,1159,80,"关闭派遣界面",1000);
                    LtLog.e("远征任务完成");
                    util.close();
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic("yuanzheng jinxing.png");
                if(result.sim>0.8){
                    result=mFairy.findPic(790,81,814,106,"red tip.png");
                    if(result.sim>0.8){
                        mFairy.onTap(0.8f,result,result.x-95,result.y+20,result.x-94,result.y+21,"进行中 点击红点提示",200);
                    }else{
                        result=mFairy.findPic(595,82,617,105,"red tip.png");
                        mFairy.onTap(0.8f,result,result.x-95,result.y+20,result.x-94,result.y+21,"可用 点击红点提示",200);

                        result=mFairy.findPic("yuanzheng keyong.png");
                        int colorNum=mFairy.getColorNum("461,90,570,130","255,255,255",0.9f,0);
                        if(result.sim>0.8&&colorNum>100){
                            mFairy.onTap(0.8f,result,656,192,676,213,"点击打开派遣详情",500);
                        }
                    }
                }
                result=mFairy.findPic("dispatch.png");
                mFairy.onTap(0.8f,result,"远征 一键派遣",500);

                result=mFairy.findPic("word suicong.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("没有合适的随从，结束任务"));
                    mFairy.onTap(0.8f,result,1145,65,1162,81,"关闭派遣界面",1000);
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic(971,591,1043,639,"start.png");
                mFairy.onTap(0.8f,result,"远征 开始任务",500);

                result=mFairy.findPic(900,147,958,575,"yuanzheng wan.png");
                mFairy.onTap(0.8f,result,"点击远征已完成",500);

                result=mFairy.findPic(574,583,656,635,"lingqu.png");
                mFairy.onTap(0.8f,result,"领取远征奖励",500);
            }
        }.taskContent(mFairy,"收获远征");
    }

    //免费购买
    public void hang_free()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }
            @Override
            public void content_1() throws Exception {
                overtime(15,0);
                result=mFairy.findPic("shop.png");
                mFairy.onTap(0.8f,result,"点击打开商城",1000);

                result=mFairy.findPic("free1.png");
                mFairy.onTap(0.8f,result,"选中免费商品",1000);

                result=mFairy.findPic("");
                if(result.sim>0.8f){
                    if(overtime(10,0));
                }

                //选中福利后购买
                result=mFairy.findPic("word welfare.png");
                if(result.sim>0.8){
                    result=mFairy.findPic("word buy.png");
                    mFairy.onTap(0.8f,result,"立即购买",1000);
                }

                //已经完成
                result=mFairy.findPic("wan.png");
                if(result.sim>0.8){
                    LtLog.e(mFairy.getLineInfo("免费购买完成"));
                    setTaskEnd();
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic(611,288,713,334,"word 0yuanbao.png");
                mFairy.onTap(0.8f,result,712,408,742,426,"0元宝购买确认",1000);

                result=mFairy.findPic("word hint already full.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,626,407,639,420,"背包已满，无法购买，结束任务",1000);
                    setTaskEnd();
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"免费购买");
    }

    //每日礼包
    public void hang_mrlb()throws Exception{
        new LimitLessTask(mFairy){
            int i=0;
            int task=1;
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(task);
            }
            //每日礼包
            @Override
            public void content_1() throws Exception{
                result = mFairy.findPic("open award.png");
                mFairy.onTap(0.8f, result, "打开奖励列表", 2000);

                result=mFairy.findPic(699,15,1000,135,"pic welfare.png");
                mFairy.onTap(0.8f,result,"打开福利界面",3000);

                result=mFairy.findPic("pic welfare interface.png");
                mFairy.onTap(0.8f,result,212,145,218,151,"进入每日礼包",2000);

                result=mFairy.findPic("word gift bag.png");
                result1=mFairy.findPic(221,168,246,195,"red tip.png");
                if(result.sim>0.8f){
                    if(result1.sim>0.85f){
                        result=mFairy.findPic("pic mrlb box.png");
                        mFairy.onTap(0.8f,result,"点击领取奖励",1000);
                    }else{
                        task=2;
                        setTaskName(0);
                        LtLog.e(mFairy.getLineInfo("每日礼包领取完成"));
                        return;
                    }

                }else{
                    LtLog.e(mFairy.getLineInfo("无每日礼包领取完成"));
                    task=2;
                    setTaskName(0);
                    return;
                }


                overtime(15,0);
            }

            //商城免费购买
            public void content_2()throws Exception{
                overtime(15,0);
                result=mFairy.findPic(803,17,1145,267,"shop.png");
                mFairy.onTap(0.8f,result,"点击打开商城",1000);

                result=mFairy.findPic("free1.png");
                mFairy.onTap(0.8f,result,"选中免费商品",1000);
                if(result.sim<0.8f){
                    i++;
                }
                result=mFairy.findPic("pic shop.png");
                if(result.sim>0.8f){
                    if(overtime(10,0)){
                        LtLog.e(mFairy.getLineInfo("免费购买完成"));
                        setTaskName(0);
                        task=3;
                    }
                }

                //选中福利后购买
                result=mFairy.findPic("word welfare.png");
                if(result.sim>0.8){
                    result=mFairy.findPic("word buy.png");
                    mFairy.onTap(0.8f,result,"立即购买",1000);
                }else{
                    i++;
                }

                //已经完成
                result=mFairy.findPic("wan.png");
                if(result.sim>0.8){
                    LtLog.e(mFairy.getLineInfo("免费购买完成"));
                    setTaskEnd();
                    return;
                }
                if(i>=5){
                    LtLog.e(mFairy.getLineInfo("没有福利"));
                    setTaskEnd();
                    return;
                }
            }

            //浏览奖励
            @Override
            public void content_3()throws Exception{
                result=mFairy.findPic(new String[]{"list.png","list1.png","list3.png"});
                mFairy.onTap(0.8f,result,"展开列表",3000);

                result=mFairy.findPic("pic Library Box.png");
                mFairy.onTap(0.8f,result,"进入藏书阁",2000);

                result=mFairy.findPic("word hide interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word theme.png");
                    mFairy.onTap(0.8f,result,"进入主题帖",1000);
                    if(overtime(15,0)){
                        mFairy.onTap(1224,31,1249,55,"关闭藏书阁",1000);
                        setTaskEnd();
                        return;
                    }

                }else{
                    overtime(15,0);
                }

                result=mFairy.findPic("word btn get.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,"点击领取",1000);
                    LtLog.e(mFairy.getLineInfo("1W领取完成"));
                    setTaskEnd();
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("word gift bag1.png");
                if(picCount(0.8f,result,"每日礼包")>40){
                    mFairy.killUserGame();
                    LtLog.e(mFairy.getLineInfo("每日礼包卡40，重启游戏"));
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"每日礼包");
    }

    //在线奖励
    public void hang_award()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                result = mFairy.findPic("open award.png");
                mFairy.onTap(0.8f, result, "打开奖励列表", 1000);

                result = mFairy.findPic(624,9,1078,79,"huodong.png");
                mFairy.onTap(0.8f, result, "打开活动列表", 1000);

                result = mFairy.findPic(132, 88, 239, 634,"line award.png");
                mFairy.onTap(0.8f, result, "切换在线领奖分页", 1000);

                result = mFairy.findPic(132, 88, 239, 634, "line award1.png");
                if (result.sim > 0.8) {
                    result = mFairy.findPic(result.x + 140, result.y - 24, result.x + 161, result.y - 5, "red tip.png");
                    if (result.sim < 0.8) {
                        LtLog.e(mFairy.getLineInfo("在线奖励领取完成"));
                        setTaskEnd();
                        return;
                    }

                    result = mFairy.findPic(1022, 305, 1075, 638, "lingqu.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f, result, result.x + 26, result.y + 15, result.x + 27, result.y + 16, "领取在线奖励", 1000);
                    }else{
                        result=mFairy.findPic("word 120.png");
                        if(result.sim>0.8f){
                            mFairy.ranSwipe(1000, 316, 1000, 630, 1000, 1500);
                            LtLog.e(mFairy.getLineInfo("向上滑动一次寻找"));
                        }else{
                            mFairy.ranSwipe(1000, 630, 1000, 316, 1000, 1500);
                            LtLog.e(mFairy.getLineInfo("向下滑动一次寻找"));
                        }
                    }

                    result=mFairy.findPic(611,395,642,468,"affirm.png");
                    mFairy.onTap(0.8f,result,"确认领取在线奖励",1000);
                }
                overtime(40,0);
            }
        }.taskContent(mFairy,"在线奖励");
    }

    //每日签到
    public void hang_sign()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            public void content_1() throws Exception{
                result = mFairy.findPic("open award.png");
                mFairy.onTap(0.8f, result, "打开奖励列表", 2000);

//                result=mFairy.findPic(699,15,1045,173"pic welfare.png");
                result=mFairy.findPic(699,15,1000,135,"pic welfare.png");
                mFairy.onTap(0.8f,result,"打开福利界面",3000);

                result=mFairy.findPic(58,247,129,326,"word sign.png");
                mFairy.onTap(0.8f,result,"进入每日签到",2000);

                result=mFairy.findPic(1211,81,1259,133,"close4.png");
                picCount=picCount(0.8f,result,"sign close");
                if(result.sim>0.8f){
                    if(picCount==1){
                        mFairy.ranSwipe(110,110,110,383,1000,1500);
                        LtLog.e(mFairy.getLineInfo("每日签到，初始化滑动"));
                    }
                    if(picCount>1){
                        result=mFairy.findPic(15,93,82,174,"sign.png");
                        mFairy.onTap(0.8f,result,"切换到签到分页",2000);

                        result=mFairy.findPic("word every.png");
                        if(result.sim>0.8f){
                            setTaskName(2);
                        }
                    }
                }

                result=mFairy.findPic("word sign interface.png");
                result1=mFairy.findPic(1211,85,1258,129,"close4.png");
                int picCount=picCount(0.8f,result,"sign interface");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("签到界面"));
                    if(result1.sim<0.8f){
                        err=0;
                        if(picCount>=30){
                            LtLog.e(mFairy.getLineInfo("签到界面界面卡30，重启游戏"));
                            mFairy.killUserGame();
                        }
                    }
                }
                overtime(5,0);
            }

            @Override
            public void content_2() throws Exception {
                overtime(8,0);
                result=mFairy.findPic("word sign interface.png");
                result1=mFairy.findPic(1211,85,1258,129,"close4.png");
                int picCount=picCount(0.8f,result,"sign interface");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("签到界面"));
                    if(result1.sim<0.8f){
                        err=0;
                        if(picCount>=30){
                            LtLog.e(mFairy.getLineInfo("签到界面界面卡30，重启游戏"));
                            mFairy.killUserGame();
                        }
                    }
                }
                result=mFairy.findPic(15,93,82,174,"sign.png");
                if(result.sim>0.8f){
                    result1=mFairy.findPic(result.x+199,result.y-33,result.x+227,result.y-6,"red tip.png");
                    if(result1.sim<0.8f){
                        LtLog.e(mFairy.getLineInfo("每日签到完成"));
                        setTaskEnd();
                        return;
                    }

                    for (int i=1;i<=6;i++){
                        result=mFairy.findPic(349+(i-1)*161,257,387+(i-1)*161,359,"pic already sign.png");
                        if(result.sim<0.8f){
                            mFairy.onTap(0.1f,result,"点击签到",2000);
                            return;
                        }
                    }

                    for (int i=1;i<=6;i++){
                        result=mFairy.findPic(349+(i-1)*161,418,387+(i-1)*161,520,"pic already sign.png");
                        if(result.sim<0.8f){
                            mFairy.onTap(0.1f,result,"点击签到",2000);
                            return;
                        }
                    }
                }
            }
        }.taskContent(mFairy,"每日签到");
    }

    //资源追回
    public void hang_zyzh()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            public void content_1() throws Exception{
                result = mFairy.findPic("open award.png");
                mFairy.onTap(0.8f, result, "打开奖励列表", 2000);

                result=mFairy.findPic(699,15,1000,135,"pic welfare.png");
                mFairy.onTap(0.8f,result,"打开福利界面",3000);

//                result=mFairy.findPic("pic welfare interface.png");
//                mFairy.onTap(0.8f,result,882,218,896,232,"进入资源追回",2000);

                result=mFairy.findPic(34,184,1155,571,"ziyuan.png");
                mFairy.onTap(0.8f,result,"进入资源追回",2000);

                result=mFairy.findPic(6,81,254,652,"pic welfare interface2.png");
                mFairy.onTap(0.8f,result,"进入资源追回",2000);

                result=mFairy.findPic("word perfect.png");
                mFairy.onTap(0.8f,result,551,92,573,108,"切换普通追回分页",2000);

                result=mFairy.findPic("word common.png");
                mFairy.onTap(0.8f,result,"普通全追回",2000);

                result=mFairy.findPic("pic question.png");
                int picCount=picCount(0.8f,result,"zyzh interface");
                if(picCount>=30){
                    LtLog.e(mFairy.getLineInfo("资源界面卡30，重启游戏"));
                    mFairy.killUserGame();
                }
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("资源追回界面"));
                    result=mFairy.findPic(1211,85,1258,129,"close4.png");
                    if(result.sim<0.8f)err=0;

                    result=mFairy.findPic("word take back.png");
                    if(result.sim<0.8f){
                        LtLog.e(mFairy.getLineInfo("资源普通追回完成"));
                        setTaskEnd();
                        return;
                    }
                }
                overtime(20,0);
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("word hint common.png");
                mFairy.onTap(0.8f,result,"普通全追回",1000);

                result=mFairy.findPic("word hint bangyin.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,533,439,556,459,"绑银不足，无法追回，结束任务",1000);
                    setTaskEnd();
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"资源追回");
    }

    //奋起直追
    public void fqzz()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            public void content_1() throws Exception{
                result = mFairy.findPic("open award.png");
                mFairy.onTap(0.8f, result, "打开奖励列表", 2000);

                result=mFairy.findPic(699,15,1000,135,"pic welfare.png");
                mFairy.onTap(0.8f,result,"打开福利界面",3000);

//                result=mFairy.findPic("pic welfare interface.png");
//                mFairy.onTap(0.8f,result,882,218,896,232,"进入资源追回",2000);

                result=mFairy.findPic(34,184,1155,571,"fenqi.png");
                mFairy.onTap(0.8f,result,"奋起直追",2000);

                result=mFairy.findPic(317,379,1133,508,"fqlingqu.png");
                mFairy.onTap(0.8f,result,"领取",2000);

                result=mFairy.findPic(357,519,1193,665,"red2.png");
                mFairy.onTap(0.8f,result,result.x-30,result.y+26,result.x+29,result.y+27,"领取宝箱",2000);

//                result=mFairy.findPic("pic question.png");
//                int picCount=picCount(0.8f,result,"zyzh interface");
//                if(picCount>=30){
//                    LtLog.e(mFairy.getLineInfo("资源界面卡30，重启游戏"));
//                    mFairy.killUserGame();
//                }
//                if(result.sim>0.8f){
//                    LtLog.e(mFairy.getLineInfo("资源追回界面"));
//                    result=mFairy.findPic(1211,85,1258,129,"close4.png");
//                    if(result.sim<0.8f)err=0;
//
//                    result=mFairy.findPic("word take back.png");
//                    if(result.sim<0.8f){
//                        LtLog.e(mFairy.getLineInfo("资源普通追回完成"));
//                        setTaskEnd();
//                        return;
//                    }
//                }
                overtime(20,0);
                if (err == 18){
                    setTaskEnd();return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                super.inOperation();
            }
        }.taskContent(mFairy,"奋起直追");
    }

    //召唤宠物
    public void hang_call()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            boolean fangsheng=false;
            @Override
            public void content_1() throws Exception {
                if(!call){
                    setTaskEnd();
                    return;
                }
                if(!fangsheng){
                    overtime(20,0);
                    result=mFairy.findPic(525,313,655,351,"pet full.png");
                    if(result.sim>0.8){
                        mFairy.onTap(0.8f,result,726,436,746,453,"背包已满，放生宠物",300);
                        fangsheng=true;
                        return;
                    }

                    result = mFairy.findPic(1199,246,1275,326,new String[]{"list.png","list1.png","list3.png"});
                    mFairy.onTap(0.7f,result,"打开列表",2000);

                    result = mFairy.findPic(876,71,1272,175,"pet.png");
                    mFairy.onTap(0.8f,result,"打开宠物",2000);

                    //判断在召唤页面
                    result = mFairy.findPic("call pet.png");
                    result1=mFairy.findPic("zhao.png");
                    if(result.sim>0.8&&result1.sim<0.8){
                        mFairy.onTap(0.8f,result,"切换召唤宠物分页",500);
                    }else if(result.sim>0.8&&result1.sim>0.8){
                        //没有红点了
                        result=mFairy.findPic(1242,340,1280,373,"red tip.png");
                        if(result.sim<0.8f){
                            result=mFairy.findPic(152,571,357,684,"call once.png");
                            result1=mFairy.findPic(679,570,884,678,"call once.png");
                            if(result.sim > 0.8f || result1.sim > 0.8f){
                                //没有免费字
                                result=mFairy.findPic(190,637,881,676,"call pet free.png");
                                if(result.sim < 0.8){
                                    LtLog.e(mFairy.getLineInfo("召唤达到上限"));
                                    call=false;
                                }else{
                                    mFairy.onTap(0.8f,result,result.x,result.y-35,result.x+10,result.y-25,"切换召唤宠物分页",500);
                                }
                            }
                            util.close();
                            LtLog.e(mFairy.getLineInfo("召唤完成"));
                            setTaskEnd();
                            return;
                        }
                    }

                    result = mFairy.findPic(777,642,814,667,"call pet free.png");
                    if(result.sim<0.8){
                        result = mFairy.findPic("call pet free.png");
                    }
                    mFairy.onTap(0.9f,result,result.x,result.y-30,result.x+1,result.y-29,"免费召唤宠物一次",2000);

                    result=mFairy.findPic("call success.png");
                    mFairy.onTap(0.8f,result,1221,26,1250,53,"召唤成功",1000);
                }else{
                    result=mFairy.findPic("pet select.png");
                    if(result.sim>0.8){
                        if(mFairy.findPic("pet select1.png").sim<0.8&&mFairy.findPic("pet fangsheng.png").sim<0.8){
                            mFairy.onTap(0.8f,result,"自动选择放生宠物",200);
                        }else{
                            mFairy.onTap(711,417,723,432,"确认自动选择放生宠物",200);
                        }
                    }

                    result=mFairy.findPic(701,430,737,468,"affirm.png");
                    mFairy.onTap(0.8f,result,"放生,确定",200);

                    result=mFairy.findPic("pet fangsheng.png");
                    mFairy.onTap(0.8f,result,1067,626,1080,640,"放生宠物",1000);

                    result=mFairy.findPic(new String []{"huodele.png","unselect.png"});
                    if(result.sim>0.8){
                        fangsheng=false;
                    }
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("word pet package.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,536,445,558,458,"宠物背包已满，取消放生",1000);
                    util.close();
                    call=false;
                    fangsheng=true;
                    setTaskEnd();
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"召唤宠物");
    }

    //清理背包
    public int hang_clear()throws Exception{
        int state=util.clear1();
        return state;
    }

    //家族种菜收菜
    public void timing_jzzc()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void create() throws Exception {
            }

            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
                return;
            }

            @Override
            public void content_1() throws Exception {
                result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                if(result.sim<0.8f){
                    //打开家族 前往农场 选择土地
                    result = mFairy.findPic(new String[]{"list.png","list1.png","list3.png"});
                    mFairy.onTap(0.8f,result,"打开列表",1000);
                }

                //没有加入家族
                result=mFairy.findPic(904,609,955,637,"word qiecuo.png");
                result1=mFairy.findPic(997,607,1060,634,new String[]{"word chuansong ta.png","word chuansong ta1.png"});
                if(result.sim>0.8f&&result1.sim<0.8f){
                    //没有加入家族，结束任务
                    LtLog.e(mFairy.getLineInfo("没有家族，结束任务"));
                    result=mFairy.findPic("take back.png");
                    mFairy.onTap(0.8f,result,"收回列表",1000);
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic(1141,379,1211,440,"family.png");
                mFairy.onTap(0.8f,result,"进入家族",1000);

                result1=mFairy.findPic("word farm interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(185,143,1113,521,"word farm me.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,result.x+10,result.y+10,result.x+11,result.y+11,"家族农场 选中我的地块",1000);
                    }else{
                        result=mFairy.findPic("word family idle.png");
                        mFairy.onTap(0.8f,result,"家族农场 选中闲置地块",1000);
                    }
                }

                //植物养护界面
                result=mFairy.findPic("word plant interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word btn harvest.png");
                    mFairy.onTap(0.8f,result,"收获",1000);

                    result=mFairy.findPic("pic farm add.png");
                    mFairy.onTap(0.8f,result,"添加植物",1000);
                }

                result=mFairy.findPic("word prop.png");
                mFairy.onTap(0.8f,result,76,113,89,128,"选择一个种子",1000);

                result=mFairy.findPic("word family welfare interface.png");
                if(result.sim>0.8f&&result1.sim<0.8f){
                    result=mFairy.findPic(210,96,272,666,"word farm.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(result.x+829,result.y+26,result.x+844,result.y+41,"打开农场",1000);
                        return;
                    }else{
                        LtLog.e(mFairy.getLineInfo("向上滑动一下"));
                        //往上滑动
                        mFairy.ranSwipe(422,560,422,360,1000,1500);
                    }
                }else{
                    result=mFairy.findPic("pic family welfare.png");
                    mFairy.onTap(0.8f,result,"切换家族福利分页",1000);
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("word hint plot.png");
                mFairy.onTap(0.8f,result,717,439,733,453,"前往地块",3000);
                super.inOperation();
            }
        }.taskContent(mFairy,"家族种菜收菜");
    }

    //异闻
    public void timing_yiwen()throws Exception{
        new LimitLessTask(mFairy){
            int end=0;
            long fadTime=0;
            @Override
            public void create() throws Exception {
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动战斗",1000);
                setTaskName(1);
            }
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
                util.initDaze();
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,"退出副本",1000);
                    setTaskEnd();
                }
                return;
            }
            //1：点击异闻 进到3
            //2：切换异闻分页 未处理消息:有(点击进到3 )，没有(点击 见闻到4)
            //3：信件来往界面 往上滑至到出现 请选择 选择偏移点击 第一个(前往或答复) 答复完立即关闭信件来往界面
            //4：执行异闻

            //寻找异闻
            @Override
            public void content_1() throws Exception {
                if(fadTime>=25){
                    setTaskName(0);
                    LtLog.e(mFairy.getLineInfo("异闻任务超时结束"));
                    return;
                }

                result=mFairy.findPic(new String[]{"list.png","list1.png"});
                if(result.sim>0.7f){
                    result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                    if(result.sim>0.8f){
                        return;
                    }

                    result=mFairy.findPic("pic yiwen chat.png");
                    if(result.sim>0.8f){
                        util.initDaze();
                        mFairy.onTap(0.8f,result,"异闻",1000);
                        return;
                    }

                    //信件来往界面
                    result=mFairy.findPic("word xinjian interface.png");
                    if(result.sim>0.8f){
                        result=mFairy.findPic(678,105,722,503,"word please.png");
                        if(result.sim>0.8f){
                            mFairy.onTap(0.8f,result,result.x+6,result.y+52,result.x+11,result.y+60,"立即前往，或答复",4000);
                            mFairy.onTap(0.8f,result,1086,98,1101,116,"关闭信件来往界面",1000);
                            util.initDaze();
                        }
                        return;
                    }


                    /*try{
                        //切换见闻分页
                        result=mFairy.findMultiColor(133,145,160,173,0.9f,"75,190,199",
                                "4|0|84,213,222&8|0|84,213,222&12|0|90,230,239&0|6|76,191,201&6|6|83,211,220&12|6|84,213,222&1|14|84,213,223&8|14|83,213,222&14|13|84,214,223");
                        mFairy.onTap(0.85f,result,"切换见闻分页",1000);

                        if(fadTime>5){
                            result=mFairy.findMultiColor(133,145,160,173,0.9f,"194,201,205",
                                    "5|0|219,226,230&9|0|219,226,230&12|0|236,244,247&0|6|196,203,207&6|6|217,224,228&12|6|219,226,230&0|9|196,203,207&6|9|224,232,235&2|13|218,225,229");
                            mFairy.onTap(0.85f,result,95,206,103,213,"见闻指引1",1000);

                            if(result.sim<0.85f){
                                result=mFairy.findMultiColor(71,181,97,290,0.9f,"204,237,229",
                                        "1|-5|204,237,229&4|-11|207,238,231&17|-24|206,240,233&22|-25|209,239,233&32|-30|209,239,234&45|-46|203,237,237&25|-48|210,241,234&-1|-44|210,242,235&-11|-65|214,247,245");
                                mFairy.onTap(0.85f,result,95,206,103,213,"见闻指引2",1000);
                            }
                        }
                    }catch(Exception e){
                        LtLog.e(mFairy.getLineInfo("获取出错"));
                        LtLog.e(mFairy.getLineInfo(e.getMessage()));
                    }*/

                    result=mFairy.findPic("mubiao.png");
                    if(result.sim>0.7){
                        end=0;
                        if(fadTime>5){
                            mFairy.onTap(0.7f,result,71,208,81,222,"目标指引",1000);
                        }
                    }

                    //查看有没完成
                    result=mFairy.findPic("dui.png");
                    result1=mFairy.findPic(new String[]{"pic jw red tip.png","word xinjian interface.png"});
                    if(result.sim>0.8f){
                        if(result1.sim>0.8f){
                            end=0;
                        }else{
                            mFairy.sleep(300);
                            if(end++>=10){
                                LtLog.e(mFairy.getLineInfo("异闻任务完成"));
                                setTaskEnd();
                                return;
                            }
                            LtLog.e(mFairy.getLineInfo("end="+end));
                        }
                    }

                    result=mFairy.findPic(43,265,108,492,new String[]{"pic yiwen enter.png","fu_goal.png"});
                    mFairy.onTap(0.8f,result,"异闻 进入",1000);

                    result=mFairy.findPic(43,265,108,492,"pic yiwen enter1.png");
                    mFairy.onTap(0.8f,result,"异闻 进入",1000);

                    result=mFairy.findPic(43,265,108,492,"pic guide chat.png");
                    mFairy.onTap(0.8f,result,"异闻 传送 前往",1000);
                }
            }

            @Override
            public void inOperation() throws Exception {
                fadTime=util.dazeTime();

                //超时处理，潜心修炼
                result=mFairy.findPic("word experience.png");
                if(result.sim>0.8f)fadTime=10;

                result=mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"打开任务列表页",1000);

                result=mFairy.findPic("task list1.png");
                mFairy.onTap(0.8f,result,"切换到任务列表",1000);

                result=mFairy.findPic(600,360,680,470,new String[]{"affirm.png","affirm1.png"});
                mFairy.onTap(0.8f,result,"点击确认",500);

                //原地复活需要2w绑银
                result=mFairy.findPic("fuhuo.png");
                if(result.sim>0.8){
                    LtLog.e("被野怪或其他玩家打死，选择复活方式后，继续任务");
                    if(rebuild){
                        mFairy.onTap(0.8f,result,768,359,790,380,"原地复活",500);
                    }else{
                        mFairy.onTap(0.8f,result,489,351,504,365,"复活点复活",500);
                        init=true;
                        setTaskName(0);
                    }
                    return;
                }

                result=mFairy.findPic(611,360,645,468,"affirm.png");
                mFairy.onTap(0.8f,result,"点击确认",500);

                result=mFairy.findPic(660,303,790,346,"clear.png");
                if(result.sim>0.8){
                    result=mFairy.findPic(510,394,578,440,"cancel.png");
                    mFairy.onTap(0.8f,result,"确定",1000);
                    if(clear){
                        int state=timingActivity.hang_clear();
                        if(state==1)setTaskName(0);
                        timekeepInit("clear");
                    }
                    return;
                }

                result=mFairy.findPic("pack full.png");
                if(result.sim>0.8){
                    LtLog.e(mFairy.getLineInfo("背包已满,选择清理"));
                    mFairy.onTap(0.8f,result,519,404,571,429,"选中不分解",100);
                    if(clear){
                        int state=timingActivity.hang_clear();
                        if(state==1)setTaskName(0);
                        timekeepInit("clear");
                        return;
                    }
                }

                result=mFairy.findPic(382,249,477,328,"pic hint.png");
                if(result.sim>0.8f){
                    //确定:
                    result=mFairy.findPic(new String[]{"word hint rebuild.png","word hint chuansong.png","word hint genzhan.png","word hint copy.png"});
                    if(result.sim>0.8f){
                        result=mFairy.findPic(696,345,759,480,"affirm.png");
                        mFairy.onTap(0.8f,result,"确定",1000);
                        return;
                    }

                    //取消
                    result=mFairy.findPic(500,345,603,480,"cancel.png");
                    mFairy.onTap(0.8f,result,"取消",500);

                    result=mFairy.findPic("fu_refuse.png");
                    mFairy.onTap(0.8f,result,"拒绝",500);
                }

                result=mFairy.findPic(908,452,1109,536,"auto wear.png");
                mFairy.onTap(0.8f,result,"捡到装备，自动使用",500);
            }
        }.taskContent(mFairy,"异闻");
    }

    //天愚赐福
    public void timing_tycf()throws Exception{
        new LimitLessTask(mFairy){
            int error=0;
            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                result = mFairy.findPic("open award.png");
                mFairy.onTap(0.8f, result, "打开奖励列表", 2000);
                
                result=mFairy.findPic(699,15,1000,135,"pic welfare.png");
                mFairy.onTap(0.8f,result,"打开福利界面",3000);

                result=mFairy.findPic(607,278,660,327,"word welfare tian.png");
                mFairy.onTap(0.8f,result,"进入天愚赐福",1000);

                result=mFairy.findPic(734,394,797,443,"word welfare fu.png");
                mFairy.onTap(0.8f,result,"进入天愚赐福",1000);

                result=mFairy.findPic("pic tycf interface.png");
                int picCount=picCount(0.8f,result,"tycf interface");
                if(picCount>=30){
                    LtLog.e(mFairy.getLineInfo("天愚赐福界面卡30，重启游戏"));
                    mFairy.killUserGame();
                }
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("天愚赐福界面"));
                    err=0;
                    mFairy.onTap(0.8f,result,961,197,973,209,"中午-领取奖励",2000);
                    mFairy.onTap(0.8f,result,960,492,976,507,"傍晚-领取奖励",2000);

                    result=mFairy.findPic(1211,85,1258,129,"close4.png");
                    result1=mFairy.findPic(217,564,248,598,"red tip.png");
                    if(result.sim>0.8f&&result1.sim<0.8f){
                        LtLog.e(mFairy.getLineInfo("天愚赐福完成"));
                        util.close();
                        setTaskEnd();
                        return;
                    }
                }
                overtime(10,0);
            }
        }.taskContent(mFairy,"天愚赐福");
    }

    //3v3排位赛 10:00-23:50
    public void timing_3v3()throws Exception{
        new LimitLessTask(mFairy){
            long dazeTime=0;
            boolean stop=false;
            long mathInter=0;
            @Override
            public void create() throws Exception {
                if(AtFairyConfig.getOption("stop").equals("1")){
                    stop=true;
                }
                if(!AtFairyConfig.getOption("mathInter").equals("2") && !AtFairyConfig.getOption("mathInter").equals("")){
                    mathInter =strSplit(AtFairyConfig.getOption("mathInter")).timeMillis;
                }
            }

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
                int ret =util.mission("single 3v3.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            @Override
            public void content_3() throws Exception {
                result=mFairy.findPic("word title 3v3.png");
                if(result.sim>0.8f)util.initDaze();

                result=mFairy.findPic("word jing.png");
                if(result.sim>0.8f){
                    util.initDaze();
                    result=mFairy.findPic("word 3v3.png");
                    mFairy.onTap(0.8f,result,"切换3v3排位赛分页",500);

                    result=mFairy.findPic(876,615,941,656,"pipei.png");
                    mFairy.onTap(0.8f,result,"个人匹配",1000);
                    if(result.sim>0.8f)timekeepInit("match");

                    //正在匹配
                    result=mFairy.findPic(1013,614,1074,655,"exit.png");
                    if(result.sim>0.8f&&mathInter!=0){
                        boolean timekeep = timekeep(0, mathInter, "match");
                        if(timekeep){
                            LtLog.e(mFairy.getLineInfo("匹配超时，任务结束"));
                            mFairy.onTap(0.8f,result,"退出匹配",1000);
                            setTaskEnd();
                        }
                    }
                }

                result=mFairy.findPic("word join.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                result=mFairy.findPic("word 3v3 interface.png");
                if(result.sim>0.8f){
                    util.initDaze();
                    //战斗阶段
                    result1=mFairy.findPic("word zhan.png");
                    int picCount=picCount(0.8f,result,"fight");
                    if(picCount==1){
                        result=mFairy.findPic(422,131,486,149,"pic blood line.png");
                        if(result.sim<0.8f){
                            timekeepInit("fight");
                            for(int i=1;i<=2;i++){
                                mFairy.touchDown(0,192,581);
                                mFairy.touchMove(0,218,422,200);
                                mFairy.sleep(1000);
                                mFairy.touchUp(0);
                                LtLog.e(mFairy.getLineInfo("初始化移动"+i));
                            }
                        }
                    }

                    if(result1.sim>0.8f){
                        result=mFairy.findPic(422,131,486,149,"pic blood line.png");
                        if(result.sim>0.8f){
                            mFairy.onTap(0.8f,result,1012,665,1024,677,"释放技能1",500);
                            mFairy.onTap(0.8f,result,1001,568,1010,579,"释放技能2",500);
                            mFairy.onTap(0.8f,result,1035,479,1045,491,"释放技能3",500);
                            mFairy.onTap(0.8f,result,1126,442,1137,453,"释放技能4",500);
                            mFairy.onTap(0.8f,result,1219,455,1230,466,"释放技能5",500);
                            mFairy.onTap(1125,564,1136,577,"普通攻击",200);
                        }else{
                            mFairy.ranSwipe(192,581,212,407,500,500);
                            mFairy.onTap(1125,564,1136,577,"行走 普通攻击",200);
                        }
                    }
                }

                result=mFairy.findPic("word see.png");
                mFairy.onTap(0.8f,result,"进入观战模式",500);

                result=mFairy.findPic("word examine.png");
                mFairy.onTap(0.8f,result,1240,136,1256,148,"关闭",1000);

                if(dazeTime>=20){
                    LtLog.e(mFairy.getLineInfo("3v3排位超时，重置任务"));
                    setTaskName(0);
                    return;
                }

                result=mFairy.findPic("word victory.png");
                if(result.sim>0.8f&&stop){
                    result=mFairy.findPic(985,496,1095,558,"exit.png");
                    mFairy.onTap(0.8f,result,"退出战场",1000);

                    if(stop){
                        LtLog.e(mFairy.getLineInfo("胜利一场，结束任务"));
                        setTaskEnd();
                        return;
                    }
                }

                result=mFairy.findPic(985,496,1095,558,"exit.png");
                mFairy.onTap(0.8f,result,"退出战场",1000);
            }

            @Override
            public void inOperation() throws Exception {
                dazeTime=util.dazeTime();

                result=mFairy.findPic(909,220,986,257,"cancel.png");
                mFairy.onTap(0.8f,result,"取消加入语音",1000);

                result=mFairy.findPic("word join combat.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                result=mFairy.findPic("word hint already in.png");
                mFairy.onTap(0.8f,result,719,411,735,425,"已经在队伍",1000);

                result=mFairy.findPic(920,460,1027,499,"word use.png");
                mFairy.onTap(0.8f,result,"自动使用",1000);

                result=mFairy.findPic(469,179,569,254,"pic award.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(605,375,668,499,"affirm.png");
                    mFairy.onTap(0.8f,result,"领取奖励",1000);
                }

                super.inOperation();
            }
        }.taskContent(mFairy,"3v3排位赛");
    }

    //剑指王者 9:00-23:50
    public void timing_jzwz()throws Exception{
        new LimitLessTask(mFairy){
            long dazeTime=0;
            boolean stop=false;
            long mathInter=0;
            int num = 0;
            @Override
            public void create() throws Exception {
                if(AtFairyConfig.getOption("jzwz").equals("1")){
                    stop=true;
                }
               /* if(!AtFairyConfig.getOption("mathInter").equals("2")){
                    mathInter =strSplit(AtFairyConfig.getOption("mathInter")).timeMillis;
                }*/
            }

            public void content_0() throws Exception {
                util.close();
                setTaskName(2);
            }

            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single jianzhiwangzhe.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            @Override
            public void content_3() throws Exception {
                result=mFairy.findPic("word jzwz interface.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word btn jzwz match.png");
                    util.onTap(0.8f,result,"个人匹配",1000);

                    result=mFairy.findPic("word btn jzwz exit match.png");
                    if(result.sim>0.8f){
                        dazeTime=util.initDaze();
                        LtLog.e(mFairy.getLineInfo("匹配中。。"));
                    }
                }

                result=mFairy.findPic(512,201,618,232,"word hint jzwz wait.png");
                if(result.sim>0.8f){
                    dazeTime=util.initDaze();
                    LtLog.e(mFairy.getLineInfo("等待其他玩家。。"));
                }

                result=mFairy.findPic(639,365,1079,539,new String[]{"word join.png","word join1.png"});
                util.onTap(0.8f,result,"加入战斗",1000);

                result=mFairy.findPic("word 3v3 interface.png");
                if(result.sim>0.8f){

                }

                result=mFairy.findPic("word jzwz ready.png");
                if(result.sim>0.8f){
                    dazeTime=util.initDaze();
                    LtLog.e(mFairy.getLineInfo("副本 - 准备中."));
                    return;
                }

                result=mFairy.findPic("word jzwz see.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("死亡 - 观战中。"));
                    dazeTime=util.initDaze();
                    return;
                }


                //战斗阶段
                result1=mFairy.findPic("word zhan.png");
//                    int picCount=picCount(0.8f,result1,"fight");
//                    if(picCount==2){
//                        result=mFairy.findPic(422,131,486,149,"pic blood line.png");
//                        if(result.sim<0.8f){
//                            timekeepInit("fight");
//                            for(int i=1;i<=2;i++){
//                                mFairy.touchDown(0,192,581);
//                                mFairy.touchMove(0,218,422,200);
//                                mFairy.sleep(1000);
//                                mFairy.touchUp(0);
//                                LtLog.e(mFairy.getLineInfo("初始化移动"+i));
//                            }
//                        }
//                    }
                if(result1.sim>0.8f){
                    dazeTime=util.initDaze();
                    result=mFairy.findPic(375,128,547,194,new String[]{"pic blood line.png","bool.png"});
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("有敌人"));
                        mFairy.onTap(0.8f,result,1012,665,1024,677,"释放技能1",500);
                        mFairy.onTap(0.8f,result,1001,568,1010,579,"释放技能2",500);
                        mFairy.onTap(0.8f,result,1035,479,1045,491,"释放技能3",500);
                        mFairy.onTap(0.8f,result,1126,442,1137,453,"释放技能4",500);
                        mFairy.onTap(0.8f,result,1219,455,1230,466,"释放技能5",500);
                        mFairy.onTap(1125,564,1136,577,"普通攻击",200);
                    }else{
                        LtLog.e(mFairy.getLineInfo("没有敌人，移动一下"));
                        num++;
                        if (num <= 2){
                            mFairy.ranSwipe(179,597,168,495,3000,1500);
                            mFairy.onTap(1125,564,1136,577,"行走 普通攻击",Sleep);
                        }else {
                            mFairy.ranSwipe(179,597,168,495,500,500);
                            mFairy.onTap(1125,564,1136,577,"行走 普通攻击",Sleep);
                        }
                    }
                }

                result=mFairy.findPic("word see.png");
                mFairy.onTap(0.8f,result,"进入观战模式",500);

                result=mFairy.findPic("word examine.png");
                mFairy.onTap(0.8f,result,1240,136,1256,148,"关闭",1000);

                result=mFairy.findPic("word btn jzwz exit match.png");
                if(dazeTime>20 && result.sim<0.8f){
                    LtLog.e(mFairy.getLineInfo("剑指王者超时，重置任务"));
                    setTaskName(0);
                    return;
                }

                result=mFairy.findPic("word victory.png");
                if(result.sim>0.8f&&stop){
                    result=mFairy.findPic(985,496,1095,558,"exit.png");
                    mFairy.onTap(0.8f,result,"退出战场",1000);

                    if(stop){
                        LtLog.e(mFairy.getLineInfo("胜利一场，结束任务"));
                        setTaskEnd();
                        return;
                    }
                }

                result=mFairy.findPic(985,496,1095,558,"exit.png");
                mFairy.onTap(0.8f,result,"退出战场",1000);

                result=mFairy.findPic(365,187,998,548,"affirm.png");
                mFairy.onTap(0.8f,result,"确认领取在线奖励",1000);
            }

            @Override
            public void inOperation() throws Exception {
                dazeTime=util.dazeTime();

                result=mFairy.findPic(909,220,986,257,"cancel.png");
                util.onTap(0.8f,result,"取消加入语音",1000);

                result=mFairy.findPic("word join combat.png");
                util.onTap(0.8f,result,"加入战斗",1000);

                result=mFairy.findPic("word hint apply.png");
                mFairy.onTap(0.8f,result,722,414,743,426,"已经在队伍，自己报名",1000);

                result=mFairy.findPic(920,460,1027,499,"word use.png");
                mFairy.onTap(0.8f,result,"自动使用",1000);

                result=mFairy.findPic(469,179,569,254,"pic award.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(605,375,668,499,"affirm.png");
                    mFairy.onTap(0.8f,result,"领取奖励",1000);
                }

                super.inOperation();
            }
        }.taskContent(mFairy,"剑指王者");
    }

    //骁骑争锋
    public void timing_xqzf()throws Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                daze=util.initDaze();
                result=mFairy.findPic(new String[]{"leave.png","world map.png"});
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在活动中"));
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
                int ret =util.mission("limit xiaqizhengfeng.png",0);
                if (ret==1){
                    mFairy.initDaze();
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
                //区域地图中
                result=mFairy.findPic("world map.png");
                if(result.sim>0.8f){
                    //通过颜色数量判断
                    int colorNum=mFairy.getColorNum(537,510,638,601,0.99f,0,"71,138,204");
                    int colorNum1=mFairy.getColorNum(721,531,806,576,0.99f,0,"71,138,204");
                    if(colorNum>=50||colorNum1>=50){
                        mFairy.onTap(820,288,821,289,"前往坐标一",500);
                        daze=util.initDaze();
                    }
                    LtLog.e(mFairy.getLineInfo("一 colorNum="+colorNum+"colorNum1"+colorNum1));

                    colorNum=mFairy.getColorNum(598,300,732,365,0.99f,0,"71,138,204");
                    if(colorNum>=50){
                        mFairy.onTap(679,142,680,143,"前往坐标二",500);
                        daze=util.initDaze();
                    }
                    LtLog.e(mFairy.getLineInfo("二 colorNum="+colorNum));

                    colorNum=mFairy.getColorNum(690,136,820,180,0.99f,0,"71,138,204");
                    if(colorNum>=50){
                        mFairy.onTap(468,380,469,381,"前往坐标三",500);
                        daze=util.initDaze();
                    }
                    LtLog.e(mFairy.getLineInfo("三 colorNum="+colorNum));

                    colorNum=mFairy.getColorNum(466,235,512,381,0.99f,0,"71,138,204");
                    if(colorNum>=50){
                        mFairy.onTap(777,551,778,552,"前往坐标四",500);
                        daze=util.initDaze();
                    }
                    LtLog.e(mFairy.getLineInfo("四 colorNum="+colorNum));

                    if(daze>45){
                        mFairy.onTap(468,380,469,381,"超时，前往坐标三",500);
                    }
                }else{
                    result=mFairy.findPic("word result.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,"退出比赛结果",500);
                        setTaskEnd();
                        return;
                    }

                    //没到终点，活动不会结束
                    result=mFairy.findPic(48,284,98,487,"pic fight.png");
                    mFairy.onTap(0.8f,result,"参加活动",1000);

                    result=mFairy.findPic("word join.png");
                    mFairy.onTap(0.8f,result,"加入战斗",1000);

                    result1=mFairy.findPic("leave.png");
                    if(result1.sim>0.8f){
                        result=mFairy.findPic("map down.png");
                        mFairy.onTap(0.8f,result,1168,92,1185,107,"打开区域地图",500);
                    }
                }

                if(daze>50){
                    setTaskName(0);
                    LtLog.e(mFairy.getLineInfo("任务超时，重置任务"));
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("word hint transfer.png");
                mFairy.onTap(0.8f,result,715,406,729,423,"确认传送",1000);

                result=mFairy.findPic(600,360,680,470,new String[]{"affirm.png","affirm1.png"});
                mFairy.onTap(0.8f,result,"点击确认",500);

                result=mFairy.findPic(500,345,603,480,"cancel.png");
                mFairy.onTap(0.8f,result,"取消",500);
                super.inOperation();
            }
        }.taskContent(mFairy,"骁骑争锋");
    }

    //真灵演武
    public void timing_zlyw()throws Exception{
        new LimitLessTask(mFairy){
            long dazeTime=0;

            @Override
            public void create() throws Exception {
                setTaskName(0);
            }

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
                int ret =util.mission("single zhenlingyanwu.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            @Override
            public void content_3() throws Exception {
                //进入副本 点击自动战斗
                //成功点击退出
                result=mFairy.findPic("word more.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(469,179,569,254,"pic award.png");
                    if(result.sim<0.8f){
                        LtLog.e(mFairy.getLineInfo("真灵演武没有挑战次数，任务结束"));
                        setTaskEnd();
                        return;
                    }
                }

                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("auto.png");
                    mFairy.onTap(0.8f,result,"点击自动战斗",500);
                }

                result=mFairy.findPic(598,590,683,646,"exit.png");
                util.onTap(0.8f,result,"退出",1000);

                result=mFairy.findPic("word jing.png");
                if(result.sim>0.8f){
                    util.initDaze();
                    result=mFairy.findPic("word zlyw.png");
                    mFairy.onTap(0.8f,result,"切换真灵演武分页",500);

                    result=mFairy.findPic("word challenge.png");
                    result1=mFairy.findPic(382,249,477,328,"pic hint.png");
                    if(result.sim>0.8f&&result1.sim<0.8f){
                        mFairy.onTap(0.8f,result,"挑战",500);
                    }
                }

                if(dazeTime>20){
                    setTaskName(0);
                    LtLog.e(mFairy.getLineInfo("真灵演武发呆超时，重置任务"));
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                dazeTime=util.dazeTime();
                result=mFairy.findPic(469,179,569,254,"pic award.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic(605,375,668,499,"affirm.png");
                    mFairy.onTap(0.8f,result,"领取奖励",1000);
                }

                result=mFairy.findPic(382,249,477,328,"pic hint.png");
                if(result.sim>0.8f){
                    //挑战
                    result=mFairy.findPic(460,300,700,364,new String[]{"word hint challenge.png","word hint zhanli.png"});
                    if(result.sim>0.8f){
                        //确定:
                        result=mFairy.findPic(696,345,759,480,"affirm.png");
                        mFairy.onTap(0.8f,result,"确定",3500);
                        return;
                    }
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"真武演练");
    }

    //无穷斗界
    public void timint_wqdj()throws Exception{
        new LimitLessTask(mFairy){
            boolean wqInfinite=false;//无限打
            long dazeTime=0;
            @Override
            public void create() throws Exception {
                if(AtFairyConfig.getOption("wqInfinite").equals("1")){
                    wqInfinite=true;
                }
            }

            @Override
            public void content_0() throws Exception {
                int ret=util.mission("limit wqdj.png",0);
                if (ret==1){
                    util.initDaze();
                    setTaskName(1);return;
                }else {
                    setTaskEnd();return;
                }
            }

            @Override
            public void content_1() throws Exception {
                //0:00-4:00 6:00-23:59
                int hour=mFairy.dateHour();
                if(hour>=4&&hour<6){
                    LtLog.e(mFairy.getLineInfo("不在无穷斗界时间，结束任务"));
                    setTaskName(2);
                    return;
                }

                //竞技场界面
                result=mFairy.findPic("word jing.png");
                if(result.sim>0.8f){
                    util.initDaze();
                    result=mFairy.findPic("word wqdj.png");
                    mFairy.onTap(0.8f,result,"切换无穷斗界分页",500);

                    result=mFairy.findPic(876,615,941,656,"pipei.png");
                    mFairy.onTap(0.8f,result,"个人匹配",1000);
                }

                result=mFairy.findPic(839,421,912,469,"word join.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                result=mFairy.findPic("word btn fight.png");
                mFairy.onTap(0.8f,result,"继续战斗",1000);

                //在副本界面
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    util.initDaze();

                    result=mFairy.findPic("auto.png");
                    mFairy.onTap(0.8f,result,"开启自动战斗",1000);

                    if(dazeTime>=30){
                        LtLog.e(mFairy.getLineInfo("副本中发呆"));
                        result=mFairy.findPic("leave.png");
                        mFairy.onTap(0.8f,result,1173,91,1185,99,"打开地图",2000);
                        mFairy.onTap(0.8f,result,587,351,595,358,"去打架",2000);
                        mFairy.onTap(0.8f,result,1224,40,1234,50,"关闭",2000);

                    }
//                    //不在寻路
//                    result=mFairy.findPic(new String[]{"word find.png","word way.png"});
//                    result1=mFairy.findPic(422,131,486,149,"pic blood line.png");
//                    if(result.sim<0.8f||result1.sim>0.8f){
//                        //安全区
//                        result=mFairy.findPic("word area safe.png");
//                        if(result.sim>0.6f){
//                            //打开地图前往打开地图
//                            result=mFairy.findPic("map down.png");
//                            mFairy.onTap(0.8f,result,1168,92,1185,107,"打开区域地图",1000);
//                        }
//
//                        //战争区
//                        result=mFairy.findPic("word area fight.png");
//                        if(result.sim>0.6f){
//                            result=mFairy.findPic("auto.png");
//                            mFairy.onTap(0.8f,result,"开启自动战斗",1000);
//                        }
//                    }
                }

                //0:00-4:00 6:00-23:00
                //不是无限打 满400血精退出
                if(!wqInfinite){
                    result=mFairy.findPic("word goal liesha.png");
                    if(result.sim>0.8f){
                        util.initDaze();
                        LtLog.e(mFairy.getLineInfo("已满400血精，准备退出"));
                        setTaskName(2);
                        return;
                    }
                }


                //前往坐标703,407位置
                result=mFairy.findPic("world map.png");
                if(result.sim>0.8f){
                    mFairy.onTap(703,407,703+1,407+1,"定位前往1",1000);
                    mFairy.onTap(703,407,703+1,407+1,"定位前往2",1000);

                    result = mFairy.findPic(890,3,1275,235,"close3.png");
                    mFairy.onTap(0.8f,result,"关闭区域地图",1000);
                }

                if(dazeTime>=30){
                    LtLog.e(mFairy.getLineInfo("无穷斗界任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void content_2() throws Exception {
                result=mFairy.findPic("leave.png");
                mFairy.onTap(0.8f,result,"退出副本",1000);

                result=mFairy.findPic("richang.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("无穷斗界任务结束"));
                    setTaskEnd();
                    return;
                }

                if(dazeTime>60){
                    setTaskName(1);
                    LtLog.e(mFairy.getLineInfo("退队超时，返回content1"));
                }
            }

            @Override
            public void inOperation() throws Exception {
                dazeTime=util.dazeTime();
                result=mFairy.findPic(592,326,651,368,"fu_exit.png");
                mFairy.onTap(0.8f,result,714,437,728,457,"退出副本",1000);

                result=mFairy.findPic("word join combat.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                //死亡后复活
                result=mFairy.findPic("fuhuo1.png");
                result1=mFairy.findPic("fuhuo.png");
                if(result.sim>0.8f||result1.sim>0.8f){
                    mFairy.onTap(489,351,504,365,"复活点复活",2000);
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"无穷斗界");
    }

    //古界争夺战
    public void timint_gjzdz()throws Exception{
        new LimitLessTask(mFairy){
            long dazeTime=0;
            int not_fight=0;
            boolean stop=false;

            @Override
            public void create() throws Exception {
                if(AtFairyConfig.getOption("gj_stop").equals("1")){
                    stop=true;
                }
            }

            @Override
            public void content_0() throws Exception {
                util.close();
                not_fight=0;
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f) {
                    LtLog.e(mFairy.getLineInfo("在副本中"));
                    setTaskName(1);
                    dazeTime=util.initDaze();
                    return;
                }

                int ret=util.mission("limit gjzdz.png",0);
                if (ret==1){
                    setTaskName(1);
                    dazeTime=util.initDaze();
                }else {
                    setTaskEnd();
                }
            }

            String camp="";
            @Override
            public void content_1() throws Exception {
                int picCount;
                //15:00-23:30
                int hour=mFairy.dateHour();
                if(hour<15){
                    LtLog.e(mFairy.getLineInfo("不在无穷斗界时间，结束任务"));
                    util.close();
                    setTaskEnd();
                    return;
                }

                //竞技场界面
                result=mFairy.findPic("word jing.png");
                if(result.sim>0.8f){
                    dazeTime=util.initDaze();

                    result=mFairy.findPic(876,615,941,656,"pipei.png");
                    mFairy.onTap(0.8f,result,"个人匹配",1000);

                    result=mFairy.findPic("word btn exit match.png");
                    picCount=picCount(0.8f,result,"exit match");
                    if(picCount!=0&&picCount%5==0)mFairy.onTap(1241,678,1242,679,"匹配中",1000);
                }

                result=mFairy.findPic(839,421,912,469,"word join.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                result=mFairy.findPic("word btn fight.png");
                mFairy.onTap(0.8f,result,"继续战斗",1000);

                //在副本界面
                result=mFairy.findPic("leave.png");
                picCount=picCount(0.8f,result,"leave");
                if(picCount!=0&&picCount%8==0)mFairy.onTap(1241,678,1242,679,"在副本中",1000);
                if(result.sim>0.8f){
                    dazeTime=util.initDaze();
                    result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                    if(result.sim<0.8f){
                        result=mFairy.findPic("auto.png");
                        mFairy.onTap(0.8f,result,"开启自动战斗",1000);
                    }

                    camp="";
                    result1=mFairy.findPic("word we yellow.png");
                    if(result1.sim>0.8f)camp="yellow";

                    result1=mFairy.findPic("word we pink.png");
                    if(result1.sim>0.8f)camp="pink";

//                    result=mFairy.findPic("auto ing.png");
//                    result1=mFairy.findPic("pic blood line.png");
//                    if(result.sim>0.8f){
//                        if(result1.sim<0.8f){
//                            if(not_fight!=0&&not_fight%4==0)LtLog.e(mFairy.getLineInfo("不战斗计次="+not_fight));
//                            if(++not_fight>15){
//                                mFairy.onTap(1181,121,1188,128,camp+"阵容，不战斗超时，打开地图",1000);
//                                not_fight=0;
//                            }
//                        }else not_fight=0;
//                    }else not_fight=0;

                    result1=mFairy.findPic(1227,3,1279,38,"map down.png");
                    result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                    if(result.sim>0.8f || result1.sim >0.8f){
                        //黄色阵容
                        if(camp.equals("yellow")){
                            result=mFairy.findPic(45,175,220,361,new String[]{"pic gjzdz flag gray.png","pic gjzdz flag pink.png"});
                        }
                        //粉色阵容
                        if(camp.equals("pink")){
                            result=mFairy.findPic(45,175,220,361,new String[]{"pic gjzdz flag gray.png","pic gjzdz flag yellow.png"});
                        }
                        //没识别到阵容
                        if(camp.equals("")||result.sim<0.8f){
                            mFairy.onTap(194,261,200,266,"到战争区",1000);
                            mFairy.onTap(194,261,200,266,"到战争区",1000);
                        }else{
                            mFairy.onTap(0.8f,result,camp+"阵容，到战争区",1000);
                            mFairy.onTap(0.8f,result,camp+"阵容，到战争区",1000);
                        }
                    }
                }

//                //地图界面
//                result1=mFairy.findPic("world map.png");
//                if(result1.sim>0.8f){
//                    //黄色阵容
//                    if(camp.equals("yellow")){
//                        result=mFairy.findPic(445,168,848,541,new String[]{"pic gjzdz flag gray.png","pic gjzdz flag pink.png"});
//                    }
//                    //粉色阵容
//                    if(camp.equals("pink")){
//                        result=mFairy.findPic(445,168,848,541,new String[]{"pic gjzdz flag gray.png","pic gjzdz flag yellow.png"});
//                    }
//                    //没识别到阵容
//                    if(camp.equals("")||result.sim<0.8f){
//                        mFairy.onTap(859,356,863,361,"到战争区",1000);
//                        mFairy.onTap(859,356,863,361,"到战争区",1000);
//                    }else{
//                        mFairy.onTap(0.8f,result,result.x+43,result.y-11,result.x+44,result.y-10,camp+"阵容，到战争区",1000);
//                        mFairy.onTap(0.8f,result,result.x+43,result.y+34,result.x+44,result.y+35,camp+"阵容，到战争区",1000);
//                    }
//                    mFairy.onTap(0.8f,result1,1226,43,1234,53,"关闭地图",1000);
//                }

                result=mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f,result,"打开任务栏",1000);

                result=mFairy.findPic("dui1.png");
                mFairy.onTap(0.8f,result,16,194,22,200,"切换副本分页",1000);

                result=mFairy.findPic("word gjzdz success.png");
                mFairy.onTap(0.8f,result,1042,516,1051,528,"立即退出",1000);
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("胜利一场"));
                    if(stop){
                        LtLog.e(mFairy.getLineInfo("古界争夺战结束"));
                        setTaskEnd();
                    }else{
                        LtLog.e(mFairy.getLineInfo("执行下一场"));
                        setTaskName(0);
                    }
                    return;
                }

                result=mFairy.findPic("word gjzdz failure.png");
                mFairy.onTap(0.8f,result,1042,516,1051,528,"立即退出",1000);
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("失败一场，古界争夺重新匹配"));
                    setTaskName(0);
                    return;
                }

                if(dazeTime>=30){
                    LtLog.e(mFairy.getLineInfo("无穷斗界任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                dazeTime=util.dazeTime();
                result=mFairy.findPic(592,326,651,368,"fu_exit.png");
                mFairy.onTap(0.8f,result,714,437,728,457,"退出副本",1000);

                result=mFairy.findPic("word hint zhanpao.png");
                mFairy.onTap(0.8f,result,719,409,733,416,"切换战袍",1000);

                result=mFairy.findPic("word join combat.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                //死亡后复活
//                result=mFairy.findPic(660,446,714,479,"fuhuo1.png");
//                result1=mFairy.findPic(652,179,711,223,"fuhuo.png");
//                if(result.sim>0.8f||result1.sim>0.8f){
//                    mFairy.onTap(634,337,643,346,"复活点复活",2000);
//                    return;
//                }
                super.inOperation();
            }
        }.taskContent(mFairy,"古界争夺战");
    }

    //七星海
    public void timint_qxh1() throws Exception{
        new LimitLessTask(mFairy){
            long daze=0;
            @Override
            public void content_0() throws Exception {
                int ret=util.mission("limit qxh.png",0);
                if (ret==1){
                    daze=util.initDaze();
                    setTaskName(1);return;
                }else {
                    setTaskEnd();return;
                }
            }

            @Override
            public void content_1() throws Exception {
                //12:15-12:45 18:15-18:45 20:45-21:15
                int hour=mFairy.dateHour();
                int minute = mFairy.dateMinute();
                if(daze>=30){
                    LtLog.e(mFairy.getLineInfo("七星海任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }

                if(hour!=12&&hour!=18&&hour!=20&&hour!=21){
                    LtLog.e(mFairy.getLineInfo("不在活动时间 点"));
                    setTaskName(0);
                    return;
                }

                if((hour==12&&minute>45)||(hour==18&&minute>45)||(hour==20&&minute<45)||(hour==21&&minute>15)){
                    LtLog.e(mFairy.getLineInfo("不在活动时间 分"));
                    setTaskName(0);
                    return;
                }

                //竞技场界面
                result=mFairy.findPic("word jing.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word btn exit match.png");
                    if(result.sim>0.8f) daze=util.initDaze();
                    LtLog.e(mFairy.getLineInfo("匹配中"));

                    result=mFairy.findPic(876,615,941,656,"pipei.png");
                    mFairy.onTap(0.8f,result,"个人匹配",1000);
                }

                result=mFairy.findPic("word join.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                //在副本界面
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    daze=util.initDaze();
                    result=mFairy.findPic("auto.png");
                    mFairy.onTap(0.8f,result,"开启自动战斗",1000);
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic("word join combat.png");
                mFairy.onTap(0.8f,result,"加入战斗",1000);

                result=mFairy.findPic(588,329,653,370,"fu_exit.png");
                mFairy.onTap(0.8f,result,729,446,732,450,"退出副本",1000);

                result=mFairy.findPic("word hint dont match.png");
                if(result.sim>0.8){
                    mFairy.onTap(0.8f,result,630,409,639,423,"参加过无法继续,任务结束",1000);
                    setTaskEnd();
                    return;
                }

                result=mFairy.findPic("word qxh zhanbao.png");
                mFairy.onTap(0.8f,result,633,430,639,437,"七星海战报",1000);

                result=mFairy.findPic("word hint zhanpao.png");
                mFairy.onTap(0.8f,result,719,409,733,416,"切换战袍",1000);

                daze=util.dazeTime();

                //死亡后复活
                result=mFairy.findPic(661,444,715,482,"fuhuo1.png");
                result1=mFairy.findPic(561,181,635,226,"fuhuo.png");
                if(result.sim>0.8f||result1.sim>0.8f){
                    mFairy.onTap(635,334,643,342,"复活点复活",2000);
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"七星海");
    }

    //云泽汤苑
    public void timing_yzty()throws Exception{
        new SingleTask(mFairy) {
            public void content_0() throws Exception {
                util.close();
                result=mFairy.findPic(new String []{"pic basin.png","pic basin target.png"});
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在活动中"));
                    setTaskName(3);
                    daze=util.initDaze();
                    return;
                }
                setTaskName(2);
            }

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

            public void content_2() throws Exception {
                //取消自动作战，不然会打断传送
                result=mFairy.findPic("auto ing.png");
                mFairy.onTap(0.8f,result,"停止自动作战",500);
                //寻找任务并且前往
                int ret =util.mission("single yunzetangyuan.png",0);
                if (ret==1){
                    daze=util.initDaze();
                    setTaskName(3);return;
                }else {
                    setTaskEnd();return;
                }
            }

            public void content_3() throws Exception {
                int hour=mFairy.dateHour();
                result=mFairy.findPic("word dailyInterface.png");
                mFairy.onTap(0.8f,result,1219,68,1236,87,"关闭日常界面",1000);

                result=mFairy.findPic("word btn hot spring.png");
                mFairy.onTap(0.8f,result,477,210,532,490,"选择一个区",1000);
                mFairy.onTap(0.8f,result,"进入温泉",1000);
                if(result.sim>0.8f)daze=util.initDaze();

                //在云泽汤苑界面
                result=mFairy.findPic("pic sit.png");
                if(result.sim>0.8f){
                    try{
                        List<FindResult> resultList = mFairy.findPic(420, 487, 850, 563, 0.8f, "pic yzty tickets.png");
                        if(resultList.size()!=0){
                            result=resultList.get(resultList.size()-1);//获取品质最高的酒
                            mFairy.onTap(0.8f,result,"选择门票",1000);
                            daze=util.initDaze();
                        }
                    }catch (Exception e){
                        LtLog.e(mFairy.getLineInfo("捕获到异常"));
                        LtLog.e(mFairy.getLineInfo(e.getMessage()));
                    }

                    result=mFairy.findPic(new String []{"pic basin.png","pic basin target.png"});
                    picCount=picCount(0.8f,result,"basin target");
                    if(picCount%5==0)mFairy.onTap(0.8f,result,"到达温泉点，泡温泉",1000);
                    if(result.sim>0.8f)daze=util.initDaze();

                    if(daze>15){
                        LtLog.e(mFairy.getLineInfo("温泉结束"));
                        setTaskEnd();
                        return;
                    }
                }

                if(daze>30){
                    LtLog.e(mFairy.getLineInfo("任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }

                //19:00-4:00
                if(hour>3&&hour<19){
                    setTaskEnd();
                    LtLog.e(mFairy.getLineInfo("云泽汤苑活动时间结束，任务结束"));
                    return;
                }
            }
        }.taskContent(mFairy,"云泽汤苑");
    }

    //家族喝酒
    public void timing_jzhj()throws Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                result=mFairy.findPic("family drink.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在活动中"));
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
                int ret =util.mission(new String[]{"single jiazuhejiu.png","single jiazuhejiu1.png"},0);
                if (ret==1){
                    daze=util.initDaze();
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
                int hour=mFairy.dateHour();
                int minute= mFairy.dateMinute();

                result=mFairy.findPic("word dailyInterface.png");
                mFairy.onTap(0.8f,result,1219,68,1236,87,"关闭日常界面",1000);


                List<FindResult> resultList = mFairy.findPic(461, 487, 820, 563, 0.8f, "drink.png");
                if(resultList.size()!=0){
                    result=resultList.get(resultList.size()-1);//获取品质最高的酒
                }
                picCount=picCount(0.8f,result,"drink");
                if(picCount<=3){
                    mFairy.onTap(0.8f,result,"点击选择喝酒",500);
                }

                result=mFairy.findPic(new String []{"family drink.png","target.png"});
                picCount=picCount(0.8f,result,"drink target");
                if(picCount%5==0)mFairy.onTap(0.8f,result,"到达喝酒地点,家族喝酒任务",1000);
                if(result.sim>0.8f)daze=util.initDaze();

                if(daze>30){
                    LtLog.e(mFairy.getLineInfo("任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }

                //八点结束
                if(hour>=20){
                    setTaskEnd();
                    LtLog.e(mFairy.getLineInfo("家族喝酒活动时间结束，任务结束"));
                    return;
                }
            }
        }.taskContent(mFairy,"家族喝酒");
    }

    //家族答题
    public void timing_jzdt()throws Exception{
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
                int ret =util.mission("single jiazudati.png",0);
                if (ret==1){
                    daze=util.initDaze();
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
                int hour=mFairy.dateHour();
                int minute= mFairy.dateMinute();

                result=mFairy.findPic("pen.png");
                picCount=picCount(0.8f,result,"pen");
                if(picCount<=3){
                    mFairy.onTap(0.8f,result,"打开答题界面",1000);
                    if(result.sim>0.8f)daze=util.initDaze();
                }

                //家族答题全选C
                result=mFairy.findPic(1210,340,1272,396,"lxdt_c.png");
                picCount=picCount(0.8f,result,"answer_C");
                if(picCount<=3){
                    mFairy.onTap(0.8f,result,"家族答题选C",1000);
                    if(result.sim>0.8f)daze=util.initDaze();
                }


                result=mFairy.findPic("word chou.png");
                picCount=picCount(0.8f,result,"luck draw");
                if(picCount<=3){
                    mFairy.onTap(0.8f,result,"开始抽奖",1000);
                }else{
                    mFairy.onTap(0.8f,result,957,132,972,146,"关闭抽奖页面",1000);
                }

                result=mFairy.findPic("word jzdt count down.png");
                LtLog.e(mFairy.getLineInfo(0.8f,result,"家族答题倒计时 等待中"));
                if(result.sim>0.8f)daze=util.initDaze();

                result=mFairy.findPic("answer over.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word lucky draw.png");
                    if(result.sim<0.8f){
                        setTaskEnd();
                        LtLog.e(mFairy.getLineInfo("家族答题结束"));
                        return;
                    }
                }

                if(minute>=47){
                    setTaskEnd();
                    LtLog.e(mFairy.getLineInfo("家族答题活动时间结束，任务结束"));
                    return;
                }

                if(daze>30){
                    LtLog.e(mFairy.getLineInfo("任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                super.inOperation();
            }
        }.taskContent(mFairy,"家族答题");
    }

    //家族喝酒和答题
    public void timing_hj_dt()throws Exception{
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
                int ret =util.mission("single jiazuhejiu.png",0);
                if (ret==1){
                    daze=util.initDaze();
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
                int hour=mFairy.dateHour();
                int minute= mFairy.dateMinute();

                result=mFairy.findPic("word dailyInterface.png");
                mFairy.onTap(0.8f,result,1219,68,1236,87,"关闭日常界面",1000);

                result=mFairy.findPic(new String []{"family drink.png","target.png"});
                picCount=picCount(0.8f,result,"drink target");
                if(picCount!=0&&picCount%5==0)mFairy.onTap(0.8f,result,"到达喝酒地点,家族喝酒任务",1000);
                if(result.sim>0.8f)daze=util.initDaze();

                result=mFairy.findPic("pen.png");
                picCount=picCount(0.8f,result,"pen");
                if(picCount<=3){
                    mFairy.onTap(0.8f,result,"打开答题界面",1000);
                    if(result.sim>0.8f)daze=util.initDaze();
                }

                //家族答题全选C
                result=mFairy.findPic(1210,340,1272,396,"lxdt_c.png");
                picCount=picCount(0.8f,result,"answer_C");
                if(picCount<=3){
                    mFairy.onTap(0.8f,result,"家族答题选C",1000);
                    if(result.sim>0.8f)daze=util.initDaze();
                }

                result=mFairy.findPic("word chou.png");
                picCount=picCount(0.8f,result,"luck draw");
                if(picCount<=3){
                    mFairy.onTap(0.8f,result,"开始抽奖",1000);
                }else{
                    mFairy.onTap(0.8f,result,957,132,972,146,"关闭抽奖页面",1000);
                }

                result=mFairy.findPic("word jzdt count down.png");
                LtLog.e(mFairy.getLineInfo(0.8f,result,"家族答题倒计时 等待中"));
                if(result.sim>0.8f)daze=util.initDaze();

                result=mFairy.findPic("answer over.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("word lucky draw.png");
                    if(result.sim<0.8f){
                        setTaskEnd();
                        LtLog.e(mFairy.getLineInfo("家族答题结束"));
                        return;
                    }
                }

                List<FindResult> resultList = mFairy.findPic(461, 487, 820, 563, 0.8f, "drink.png");
                if(resultList.size()!=0){
                    result=resultList.get(resultList.size()-1);//获取品质最高的酒
                }
                picCount=picCount(0.8f,result,"drink");
                if(picCount!=0&&picCount%3==0){
                    mFairy.onTap(0.8f,result,"点击选择喝酒",1000);
                }

                result=mFairy.findPic(new String []{"family drink.png","target.png"});
                picCount=picCount(0.8f,result,"drink target");
                if(picCount%5==0)mFairy.onTap(0.8f,result,"到达喝酒地点,家族喝酒任务",1000);
                if(result.sim>0.8f)daze=util.initDaze();

                //八点结束
                if(hour>=20){
                    setTaskEnd();
                    LtLog.e(mFairy.getLineInfo("家族喝酒活动时间结束，任务结束"));
                    return;
                }

                if(daze>30){
                    LtLog.e(mFairy.getLineInfo("任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                super.inOperation();
            }
        }.taskContent(mFairy,"家族喝酒和答题");
    }

    //全服限时首领
    public void timing_xssl()throws Exception{
        new SingleTask(mFairy) {
            /**
             * 调用工具类初始化接任务
             * @throws Exception
             */
            public void content_0() throws Exception {
                util.close();
                result=mFairy.findPic("pic inspire.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("在活动中"));
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
                int ret =util.mission("limit limit boss.png",0);
                if (ret==1){
                    mFairy.initDaze();
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
                int hour=mFairy.dateHour();
                int minute= mFairy.dateMinute();

                //鼓舞图标 在副本中
                result=mFairy.findPic("pic inspire.png");
                if(result.sim>0.8f){
                    daze=util.initDaze();
                    result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                    if(result.sim<0.8f){
                        result=mFairy.findPic("auto ing.png");
                        LtLog.e(mFairy.getLineInfo(0.8f,result,"自动战斗中"));

                        result=mFairy.findPic("auto.png");
                        mFairy.onTap(0.8f,result,"开启自动作战",500);
                    }
                }

                result=mFairy.findPic(560,181,640,223,"fuhuo.png");
                mFairy.onTap(0.8f,result,632,339,652,358,"复活点复活",500);

                result=mFairy.findPic("chengyuan.png");
                if(result.sim>0.8){
                    result = mFairy.findPic(890,3,1275,235,new String[]{"close.png","close3.png"});
                    mFairy.onTap(0.8f,result,"查看战绩",500);
                }

                if(hour==19&&minute>=30){
                    LtLog.e(mFairy.getLineInfo("全服限时首领活动时间结束，结束任务"));
                    setTaskEnd();
                    return;
                }

                if(daze>15){
                    LtLog.e(mFairy.getLineInfo("全服限时首领超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic(594,378,680,438,"leave1.png");
                if(result.sim>0.8f){
                    mFairy.onTap(0.8f,result,"挑战时间结束,点击离开",200);
                    setTaskEnd();
                    return;
                }
                super.inOperation();
            }
        }.taskContent(mFairy,"全服限时首领");
    }

    //装备强化
    public void timing_equip()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void create() throws Exception {
                setTaskName(1);
            }

            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                result = mFairy.findPic(new String[]{"list.png","list1.png","list3.png"});
                mFairy.onTap(0.8f,result,"打开列表",2000);

                result=mFairy.findPic("equip pic.png");
                mFairy.onTap(0.8f,result,"打开装备界面",2000);

                result=mFairy.findPic(685,395,773,437,"go.png");
                mFairy.onTap(0.8f,result,538,409,549,420,"材料不够,取消前往",200);

                result1=mFairy.findPic("word strong interface.png");
                result=mFairy.findPic("equip pic1.png");
                if(result.sim>0.8){
                    if(result1.sim<0.8f){
                        mFairy.onTap(0.8f,result,"切换装备强化分页",1000);
                    }

                    List<FindResult> list =
                            mFairy.findPic(134, 84, 283, 590, 0.8f, "red tip.png");
                    if(list.size()!=0){
                        int ran=new Random().nextInt(list.size());
                        result=list.get(ran);
                        mFairy.onTap(0.8f,result,"选中要强化的装备"+ran,500);

                        if(result.sim>0.8){
                            result=mFairy.findPic("qianghua.png");
                            mFairy.onTap(0.8f,result,"强化装备",500);
                        }
                    }

                    result=mFairy.findPic(1256,119,1280,143,"red tip.png");
                    if(result.sim<0.8f){
                        LtLog.e(mFairy.getLineInfo("装备强化结束"));
                        util.close();
                        setTaskEnd();
                        return;
                    }
                }else{
                    overtime(10,0);
                }
            }

            @Override
            public void inOperation() throws Exception {
                super.inOperation();
            }
        }.taskContent(mFairy,"装备强化");
    }

    //技能升级
    public void timing_skill()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void create() throws Exception {
                setTaskName(1);
            }

            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                result = mFairy.findPic(new String[]{"list.png","list1.png","list3.png"});
                mFairy.onTap(0.8f,result,"打开列表",2000);

                result=mFairy.findPic("skill pic.png");
                mFairy.onTap(0.8f,result,"打开技能界面",2000);

                result=mFairy.findPic("word skill interface.png");
                if(result.sim<0.8f){
                    result=mFairy.findPic("skill pic1.png");
                    mFairy.onTap(0.8f,result,"切换技能分页",1000);
                }

                result=mFairy.findPic("a key.png");
                result1=mFairy.findPic(434,288,480,327,"tui.png");
                if(result.sim>0.8&&result1.sim<0.8){
                    mFairy.onTap(0.8f,result,"一键升级",1000);
                }

                result=mFairy.findPic("lilian.png");
                mFairy.onTap(0.8f,result,537,440,556,454,"历练不足",200);

                result=mFairy.findPic("skill pic1.png");
                result1=mFairy.findPic(1255,127,1279,154,"red tip.png");
                if(result.sim>0.8){
                    if(result1.sim<0.8){
                        LtLog.e(mFairy.getLineInfo("技能升级结束"));
                        util.close();
                        setTaskEnd();
                        return;
                    }
                }else{
                    overtime(15,0);
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic(434,288,480,327,"tui.png");
                mFairy.onTap(0.8f,result,714,409,734,421,"使用推荐加点",200);
                super.inOperation();
            }
        }.taskContent(mFairy,"技能升级");
    }

    //宝石升级
    public void timing_baoshi()throws Exception{
        new LimitLessTask(mFairy){
            @Override
            public void create() throws Exception {
                setTaskName(1);
            }

            @Override
            public void content_0() throws Exception {
                util.close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                result = mFairy.findPic(new String[]{"list.png","list1.png","list3.png"});
                mFairy.onTap(0.8f,result,"打开列表",2000);

                result=mFairy.findPic("equip pic.png");
                mFairy.onTap(0.8f,result,"打开装备界面",2000);

                result=mFairy.findPic("word baoshi interface.png");
                if(result.sim>0.8f) {

/*                    try{
                        //自动选择的红点
                        result=mFairy.findMultiColor(964,592,989,617,0.9f,"206,69,69",
                                "2|0|205,68,68&4|0|211,64,64&6|0|211,64,64&-1|2|217,66,67&2|2|212,66,66&5|2|212,66,65&8|2|212,71,70&0|4|215,72,72&3|4|210,68,67");
                        mFairy.onTap(0.85f,result,896,625,904,633,"自动选择",1000);
                        mFairy.onTap(0.85f,result,1075,633,1080,639,"升级",1000);
                        if(result.sim>0.85f)return;
                        //可升级
                        //宝石的红点
                        result=mFairy.findMultiColor(410,371,437,597,0.9f,"206,69,69",
                                "2|0|205,68,68&4|0|211,64,64&6|0|211,64,64&-1|2|217,66,67&2|2|212,66,66&5|2|212,66,65&8|2|212,71,70&0|4|215,72,72&3|4|210,68,67");
                        mFairy.onTap(0.85f,result,"切换可升级宝石",1000);
                        if(result.sim>0.85f)return;

                        result=mFairy.findMultiColor(82,140,267,655,0.9f,"17,255,238",
                                "10|0|17,255,238&1|8|96,245,232&10|10|85,255,238&20|1|46,241,227&20|6|64,251,234&26|0|34,238,221&26|11|107,226,226&34|2|31,252,235&39|0|33,242,225");
                        mFairy.onTap(0.85f,result,"选中可升级的宝石",1000);
                        if(result.sim<0.85){
                            mFairy.onTap(1224,75,1229,84,"没有可升级宝石，任务结束",1000);
                            setTaskEnd();
                            return;
                        }

                    }catch (Exception e){
                        LtLog.e(mFairy.getLineInfo("获取出错"));
                        LtLog.e(mFairy.getLineInfo(e.getMessage()));
                    }*/
                }else{
                    result=mFairy.findPic("pic change baoshi.png");
                    mFairy.onTap(0.8f,result,"切换宝石分页",1000);
                    overtime(10,0);
                }
            }

            @Override
            public void inOperation() throws Exception {
                super.inOperation();
            }
        }.taskContent(mFairy,"宝石升级");
    }

    //捕获
    public void timing_capture()throws Exception{
        new LimitLessTask(mFairy){
            long daze=0;
            @Override
            public void content_0() throws Exception {
                daze=util.initDaze();
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    setTaskName(1);
                    return;
                }
                util.close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                result=mFairy.findPic("caidan.png");
                mFairy.onTap(0.7f,result,"打开菜单界面",3000);

                result=mFairy.findPic(863,41,1280,647,"pic horse.png");
                mFairy.onTap(0.8f,result,"打开坐骑界面",2000);

                result=mFairy.findPic("pic capture.png");
                mFairy.onTap(0.8f,result,"打开捕获界面",2000);

                //捕获坐骑界面
                result=mFairy.findPic("word mount interface.png");
                if(result.sim>0.8f) {
                    result=mFairy.findPic(1112,611,1181,662,"go.png");
                    mFairy.onTap(0.8f,result,"前往",1000);
                    if(result.sim>0.8f)daze=util.initDaze();

                    result=mFairy.findPic("word capture new.png");
                    if(result.sim>0.8f){
                        mFairy.onTap(0.8f,result,1233,31,1248,42,"免费捕获完成",1000);
                        setTaskEnd();
                        return;
                    }
                }

                result=mFairy.findPic(536,41,628,121,"word back already full.png");
                mFairy.onTap(0.8f,result,"背包已满，无法捕获，退出副本",1000);
                mFairy.onTap(0.8f,result,725,446,735,454,"退出副本",1000);

                //在副本中
                result=mFairy.findPic("leave.png");
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("捕获副本中"));
                    daze=util.initDaze();

                    result=mFairy.findPic("word capture tame.png");
                    mFairy.onTap(0.8f,result,"驯服",2000);

                    result=mFairy.findPic("pic capture hit.png");
                    int picCount=picCount(0.9f,result,"btn capture");
                    if(picCount!=0&&picCount%15==0) mFairy.onTap(0.9f,result,"点击鞭打",2000);
                }else{
                  /*  result=mFairy.findPic(1187,179,1274,335,new String[]{"list.png","list1.png","list2.png"});
                    mFairy.onTap(0.85f,result,"打开列表",2000);*/
                    result=mFairy.findPic("caidan.png");
                    mFairy.onTap(0.8f,result,"打开菜单界面",2000);
                }

                result=mFairy.findPic(939,456,1024,502,"word use.png");
                mFairy.onTap(0.8f,result,"自动使用",1000);

                result=mFairy.findPic(575,534,673,583,"leave1.png");
                mFairy.onTap(0.8f,result,"离开",1000);

                if(daze>30){
                    LtLog.e(mFairy.getLineInfo("任务超时，重置任务"));
                    setTaskName(0);
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                daze=util.dazeTime();
                result=mFairy.findPic(new String[]{"word hint secret area.png","buhuo2.png"});
                mFairy.onTap(0.8f,result,735,375,744,384,"进入秘境",1000);

                result=mFairy.findPic(580,327,666,373,"fu_exit.png");
                mFairy.onTap(0.8f,result,725,446,735,454,"退出副本",1000);
                super.inOperation();
            }
        }.taskContent(mFairy,"捕获");
    }

    //队长拉跟战
    public void timing_follow() throws Exception{
        //判断是不是队长
        result=mFairy.findPic("dui.png");
        mFairy.onTap(0.8f,result,"切换组队分页",1000);

        result=mFairy.findPic("fu_header.png");
        if(result.sim>0.8f){
            result=mFairy.findPic(125,421,256,446,"word genzhan.png");
            mFairy.onTap(0.7f,result,"发起跟战",1000);
        }
    }
}
