package com.script.fairy;

import com.script.framework.AtFairyImpl;
import com.script.framework.TaskContent;
import com.script.opencvapi.AtFairyConfig;
import com.script.opencvapi.FindResult;
import com.script.opencvapi.LtLog;
import java.util.List;

/**
 * Created by Administrator on 2019/3/22 0022.
 */
@SuppressWarnings("all")
public class Util extends TaskContent {
    AtFairyImpl mFairy;
    FindResult result;
    FindResult result1;
    static int state = 0;
    static long fadTime = 0L;

    public Util(AtFairyImpl atFairy) throws Exception {
        mFairy = atFairy;
    }

    public void close() throws Exception {
        new Util(mFairy) {
            int closeNum = 5;

            @Override
            public void content_0() throws Exception {
                result = mFairy.findPic("fuhuo.png");
                if (result.sim > 0.8) {
                    LtLog.e(mFairy.getLineInfo("玩家死亡，关闭终止"));
                    setTaskEnd();
                    return;
                }
                if (--closeNum <= 0) {
                    LtLog.e(mFairy.getLineInfo("关闭结束"));
                    setTaskEnd();
                    return;
                } else {
                    LtLog.e(mFairy.getLineInfo("closeNum=" + closeNum));
                }

                //洞宇法盘
                result = mFairy.findPic("close7.png");
                mFairy.onTap(0.8f, result, "关闭", 1000);

                result = mFairy.findPic("close_sjmb.png");
                mFairy.onTap(0.8f, result, "关闭_三界秘宝", 1000);

                //新活动的关闭
                result = mFairy.findPic("close8.png");
                mFairy.onTap(0.8f, result, "关闭", 1000);

                //浑源归来
                result = mFairy.findPic("close5.png");
                mFairy.onTap(0.8f, result, "关闭", 1000);

                //确定和取消 的对话框
                result = mFairy.findPic(362, 240, 477, 335, "pic hint.png");
                if (result.sim > 0.8f) {
                    closeNum = 5;
                    //确定:
                    result = mFairy.findPic(new String[]{"word hint share.png"});
                    if (result.sim > 0.8f) {
                        result = mFairy.findPic(696, 345, 759, 480, "affirm.png");
                        mFairy.onTap(0.8f, result, "确定", 1000);
                        return;
                    }

                    //取消
                    result1 = mFairy.findPic(500, 345, 603, 480, "cancel.png");
                    mFairy.onTap(0.8f, result1, "取消", 1000);

                    if (result.sim < 0.8f && result1.sim < 0.8f) {
                        mFairy.onTap(537, 435, 543, 440, "取消行动1", 1000);
                        mFairy.onTap(520, 382, 525, 386, "取消行动2", 1000);
                    }
                }

                result = mFairy.findPic(496, 387, 606, 538, "cancel.png");
                mFairy.onTap(0.8f, result, "取消", 1000);

                result = mFairy.findPic(600, 360, 680, 470, new String[]{"affirm.png", "affirm1.png"});
                mFairy.onTap(0.8f, result, "点击确认", 500);

                result = mFairy.findPic(496, 387, 606, 538, "word btn exit1.png");
                mFairy.onTap(0.8f, result, "退出", 1000);

                result = mFairy.findPic(865, 0, 1280, 293, "close3.png");
                mFairy.onTap(0.8f, result, "关闭", 1500);

                try {
                    List<FindResult> resultList = mFairy.findPic(865, 0, 1280, 293, 0.8f, "close3.png");
                    if (resultList.size() != 0) {
                        closeNum = 5;
                        for (int i = 0; i < resultList.size(); i++) {
                            mFairy.onTap(0.8f, resultList.get(i), "util_list 关闭" + i, 1500);
                        }
                    }
                } catch (Exception e) {
                    LtLog.e(mFairy.getLineInfo("捕获到异常"));
                    LtLog.e(mFairy.getLineInfo(e.getMessage()));
                }

                result = mFairy.findPic(1042, 305, 1076, 340, "mz close.png");
                mFairy.onTap(0.8f, result, "藏宝图 关闭", 1000);

                result = mFairy.findPic(342, 97, 466, 152, "close left.png");
                mFairy.onTap(0.8f, result, "关闭左边选项列表", 1000);

                result = mFairy.findPic("take back.png");
                mFairy.onTap(0.8f, result, "收回列表", 1000);

                if (closeNum == 1) {
                    mFairy.onTap(1240, 675, 1245, 680, "util_点击", 200);
                }
            }
        }.taskContent(mFairy, "关闭");
    }

    int teamState = 0;

    public int teamState() throws Exception {
        teamState = 0;//1、无队伍 2、队长 3、队员
        new Util(mFairy) {
            @Override
            public void content_0() throws Exception {
                close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                overtime(10, 0);
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f, result, "打开任务栏", 1000);

                result = mFairy.findPic("dui.png");
                mFairy.onTap(0.8f, result, "切换队伍分页", 1000);

                //没有队伍
                result = mFairy.findPic("pic team create.png");
                if (result.sim > 0.8f) {
                    LtLog.e(mFairy.getLineInfo("没有加入队伍"));
                    teamState = 1;
                    setTaskEnd();
                    return;
                }

                //有队伍
                result = mFairy.findPic(58, 396, 242, 430, "fu_lidui.png");
                if (result.sim > 0.8f) {
                    //是队长
                    result = mFairy.findPic("fu_header.png");
                    if (result.sim > 0.8f) {
                        LtLog.e(mFairy.getLineInfo("是队长"));
                        Util.this.teamState = 2;
                    } else {
                        LtLog.e(mFairy.getLineInfo("是队员"));
                        Util.this.teamState = 3;
                    }
                    setTaskEnd();
                    return;
                }
            }
        }.taskContent(mFairy, "检测队伍状态");
        return teamState;
    }

    public void exitTeam() throws Exception {
        new Util(mFairy) {
            @Override
            public void content_0() throws Exception {
                close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                overtime(10, 0);
                result = mFairy.findPic("pic open task.png");
                mFairy.onTap(0.8f, result, "打开任务栏", 1000);

                result = mFairy.findPic("dui.png");
                mFairy.onTap(0.8f, result, "切换队伍分页", 1000);

                //没有队伍
                result = mFairy.findPic("pic team create.png");
                if (result.sim > 0.8f) {
                    LtLog.e(mFairy.getLineInfo("退出队伍完成"));
                    setTaskEnd();
                    return;
                }

                //有队伍
                result = mFairy.findPic(58, 396, 242, 430, "fu_lidui.png");
                mFairy.onTap(0.8f, result, "离开队伍", 1000);
                mFairy.onTap(0.8f, result, 724, 412, 733, 420, "确定退出队伍", 1000);
            }
        }.taskContent(mFairy, "退出队伍");
    }

    //发起招募
    public void recruit() throws Exception {
        new Util(mFairy) {
            int recruit_type = 1;

            public void content_0() throws Exception {
                //recruit_type=1;
                close();
                setTaskName(1);
            }

            @Override
            public void content_1() throws Exception {
                mFairy.sleep(1000);
                result = mFairy.findPic("leave.png");
                if (result.sim > 0.8f) {
                    LtLog.e(mFairy.getLineInfo("在副本中不用招募"));
                    setTaskEnd();
                    return;
                }

                result = mFairy.findPic("word copy.png");
                mFairy.onTap(0.8f, result, 1240, 675, 1245, 680, "关闭副本界面", 1000);

                result = mFairy.findPic("word invite.png");
                mFairy.onTap(0.8f, result, 1240, 675, 1245, 680, "关闭邀请界面", 1000);

                result = mFairy.findPic("word hunyuan interface.png");
                mFairy.onTap(0.8f, result, 1222, 70, 1233, 84, "关闭混元图录界面", 1000);

                result = mFairy.findPic("dui.png");
                mFairy.onTap(0.8f, result, "切换队伍分页", 1000);

                result = mFairy.findPic("dui1.png");
                mFairy.onTap(0.8f, result, "打开组队平台", 1000);

                switch (recruit_type) {
                    //招募频道
                    case 1:
                        result = mFairy.findPic("pic join platform.png");
                        if (result.sim > 0.8) {
                            result = mFairy.findPic("word recruit.png");
                            mFairy.onTap(0.8f, result, "招募队员", 1000);
                            mFairy.onTap(0.8f, result, 233, 585, 248, 590, "招募频道", 1000);
                        }
                        recruit_type++;
//                        result=mFairy.findPic(183,577,245,702,"word recruit zhaomu.png");
//                        if(result.sim>0.8f){
//                            recruit_type++;
//                            mFairy.onTap(0.8f,result,"招募频道",500);
//                        }
                        break;
                    //家族频道
                    case 2:
                        result = mFairy.findPic("pic join platform.png");
                        if (result.sim > 0.8) {
                            result = mFairy.findPic("word recruit.png");
                            mFairy.onTap(0.8f, result, "招募队员", 1000);
                            mFairy.onTap(0.8f, result, 227, 628, 239, 637, "家族频道", 1000);
                        }
                        recruit_type++;
//                        result=mFairy.findPic(183,577,245,702,"word recruit family.png");
//                        if(result.sim>0.8f){
//                            recruit_type++;
//                            mFairy.onTap(0.8f,result,"家族频道",500);
//                        }
                        break;
                    //附近频道
                    case 3:
                        result = mFairy.findPic("pic join platform.png");
                        if (result.sim > 0.8) {
                            result = mFairy.findPic("word recruit.png");
                            mFairy.onTap(0.8f, result, "招募队员", 1000);
                            mFairy.onTap(0.8f, result, 244, 674, 258, 683, "附近频道", 1000);
                        }
                        recruit_type++;
//                        result=mFairy.findPic(183,577,245,702,"word recruit nearby.png");
//                        if(result.sim>0.8f){
//
//                            mFairy.onTap(0.8f,result,"附近频道",500);
//                        }
                        break;
                }
                LtLog.e("*----*" + recruit_type);
                if (recruit_type >= 4) {
                    LtLog.e(mFairy.getLineInfo("招募完成"));
                    setTaskEnd();
                    return;
                }
                overtime(20, 0);
            }
        }.taskContent(mFairy, "招募队员");
    }

    //发呆超时
    public long dazeTime() throws Exception {
        long pastTime = fadTime;
        if (pastTime != 0L) {
            //自动寻路中
            result = mFairy.findPic(new String[]{"word find.png", "word way.png"});
            if (result.sim > 0.8) {
                LtLog.e(mFairy.getLineInfo("自动寻路中"));
                initDaze();
                mFairy.initMatTime();
                return 0L;
            }
            //传送
            result = mFairy.findPic(new String[]{"pic load.png", "pic transfer.png"});
            if (result.sim > 0.8f) {
                LtLog.e(mFairy.getLineInfo("地图传送中"));
                mFairy.initMatTime();
                initDaze();
                return 0L;
            }
            //自动作战 不打怪
            result = mFairy.findPic("auto ing.png");
            result1 = mFairy.findPic(new String[]{"pic blood line.png", "pic hxyj boss.png"});
            if (result.sim > 0.8f && result1.sim > 0.8) {
                LtLog.e(mFairy.getLineInfo("自动战斗中"));
                initDaze();
                return 0L;
            }

            long dazeTime = System.currentTimeMillis() / 1000L;//获取现在时间
            dazeTime = dazeTime - pastTime;//现在-之前
            LtLog.e(mFairy.getLineInfo("发呆时间dazeTime=" + dazeTime));
            return dazeTime;
        } else {
            fadTime = System.currentTimeMillis() / 1000L;//获取当前时间
            return 0L;
        }
    }

    //点击并发呆清零
    public void onTap(float sim, FindResult result, String string, long time) throws Exception {
        mFairy.onTap(sim, result, string, time);
        if (result.sim > sim) initDaze();
    }

    //点击并发呆清零
    public void onTap(float sim, FindResult result, int x1, int y1, int x2, int y2, String string, long time) throws Exception {
        mFairy.onTap(sim, result, x1, y1, x2, y2, string, time);
        if (result.sim > sim) initDaze();
    }

    //重启游戏
    public void restartGame() throws Exception {
        new Util(mFairy) {
            //杀死游戏进程
            @Override
            public void content_0() throws Exception {
                mFairy.sleep(3000);
                LtLog.e(mFairy.getLineInfo("杀死游戏进程"));
                mFairy.killUserGame();
                setTaskName(1);
            }

            //重新登录游戏
            @Override
            public void content_1() throws Exception {
                result = mFairy.findPic("word tap.png");
                mFairy.onTap(0.8f, result, "点击跳过", 2000);

                result = mFairy.findPic(1069, 5, 1276, 144, "close3.png");
                mFairy.onTap(0.8f, result, "关闭", 1000);

                result = mFairy.findPic("word btn enter1.png");
                if (result.sim > 0.8f) {
                    mFairy.onTap(0.8f, result, "进入游戏", 2000);
                    setTaskName(2);
                    return;
                }
            }

            //回归任务
            @Override
            public void content_2() throws Exception {
                close();
                LtLog.e(mFairy.getLineInfo("重启游戏完成"));
                setTaskEnd();
                return;
            }
        }.taskContent(mFairy, "重启游戏");
    }

    //初始化发呆
    public long initDaze() throws Exception {
        fadTime = 0L;
        return 0l;
    }

    //回城
    public void back_city() throws Exception {
        new Util(mFairy) {
            long dazeTime = 0;

            @Override
            public void content_0() throws Exception {
                close();
                result = mFairy.findPic("leave.png");
                mFairy.onTap(0.8f, result, "离开副本", 1000);
                mFairy.onTap(0.8f, result, 715, 410, 739, 424, "离开副本-确定", 1000);
                setTaskName(1);
                dazeTime = initDaze();
            }

            @Override
            public void content_1() throws Exception {
                try {
                    result1 = mFairy.findPic("word place xdc.png");
                    if (result1.sim > 0.85f) {
                        LtLog.e(mFairy.getLineInfo("回城结束"));
                        setTaskEnd();
                        return;
                    }
                } catch (Exception e) {
                    LtLog.e(mFairy.getLineInfo("获取出错"));
                    LtLog.e(mFairy.getLineInfo(e.getMessage()));
                }

                result = mFairy.findPic("fuhuo.png");
                mFairy.onTap(0.8f, result, 489, 351, 504, 365, "复活点复活", 1000);

                result = mFairy.findPic("word btn back server.png");
                if (result.sim > 0.8f) {
                    result1 = mFairy.findPic(new String[]{"word find.png", "word way.png"});
                    if (result1.sim < 0.8) mFairy.onTap(0.8f, result, "回到本服", 1000);
                    result1 = mFairy.findPic(422,26,866,194,"zxqw.png");
                    if (result1.sim > 0.8){
                        result = mFairy.findPic(new String[]{"list.png", "list1.png"});
                        mFairy.onTap(0.8f, result, "展开列表", 1000);

                        result = mFairy.findPic(new String[]{"back home.png", "back home1.png"});
                        mFairy.onTap(0.8f, result, "回城", 3000);

                        result = mFairy.findPic(516, 279, 761, 366, "xiaducheng.png");
                        if (result.sim > 0.8f) {
                            mFairy.onTap(0.8f, result, 725, 411, 735, 419, "返回夏都城确定", 1000);

                            mFairy.initMatTime();
                            LtLog.e(mFairy.getLineInfo("回城中"));
                            setTaskEnd();
                            return;

                        }
                    }
                    return;
                }

                result = mFairy.findPic("word dailyInterface.png");
                mFairy.onTap(0.8f, result, 1219, 68, 1236, 87, "关闭日常界面", 1000);

                result = mFairy.findPic(1198, 251, 1276, 332, new String[]{"list.png", "list1.png"});
                mFairy.onTap(0.7f, result, "展开列表", 4000);

                result = mFairy.findPic(new String[]{"back home.png", "back home1.png"});
                mFairy.onTap(0.8f, result, "回城", 5000);

                //不在传送
                result = mFairy.findPic(422,26,866,194,new String[]{"chuansong1.png", "chuansong2.png"});
                result1 = mFairy.findPic("zxqw.png");
                if (result.sim < 0.8f | result1.sim> 0.8f) {
                    result = mFairy.findPic("leave.png");
                    mFairy.onTap(0.8f, result, "退出副本", 1000);

                    result = mFairy.findPic(new String[]{"list.png", "list1.png"});
                    mFairy.onTap(0.8f, result, "展开列表", 1000);

                    result = mFairy.findPic(new String[]{"back home.png", "back home1.png"});
                    mFairy.onTap(0.8f, result, "回城", 3000);

                    result = mFairy.findPic(516, 279, 761, 366, "xiaducheng.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, 725, 411, 735, 419, "返回夏都城确定", 1000);

                        mFairy.initMatTime();
                        LtLog.e(mFairy.getLineInfo("回城中"));
                        setTaskEnd();
                        return;

                    }
                } else {
                    LtLog.e(mFairy.getLineInfo("传送中。。。"));
                    dazeTime = initDaze();
                }

                if (dazeTime > 30) {
                    setTaskName(0);
                    LtLog.e(mFairy.getLineInfo("回城超时，重置任务"));
                    return;
                }
            }

            @Override
            public void inOperation() throws Exception {
                dazeTime = dazeTime();
                result = mFairy.findPic("word hint copy.png");
                mFairy.onTap(0.8f, result, 722, 256, 737, 270, "确定退出副本", 1000);

            }
        }.taskContent(mFairy, "回城");
    }


    //可以使用的宝箱(can clear) 绑银 绑元 演武奖励(clear8)，就是一些可以打开的箱子
    String canClear[] = new String[]{"bangding.png", "bangding1.png", "can clear.png", "can clear1.png", "can clear2.png", "can clear3.png", "can clear4.png",
            "can clear5.png", "can clear6.png", "can clear7.png", "can clear8.png"};

    public int clear1() throws Exception {
        state = 0;
        new Util(mFairy) {
            boolean autoClear = true, select = false, rebuild = false;

            @Override
            public void create() throws Exception {
                //是否原地复活
                if (AtFairyConfig.getOption("rebuild").equals("1")) {
                    rebuild = true;
                }
                setTaskName(0);
            }

            @Override
            public void content_0() throws Exception {
                close();
                if (autoClear) {
                    //自动分解
                    setTaskName(1);
                } else {
                    setTaskName(2);
                }
            }

            @Override
            public void content_1() throws Exception {
                result = mFairy.findPic("pack.png");
                mFairy.onTap(0.8f, result, "打开背包", 2000);

                //自动选择分解
                result = mFairy.findPic(930,592,1161,675,"resolve.png");
                mFairy.onTap(0.8f, result, "点击分解", 1000);

                //自动选择分解
                result = mFairy.findPic(734,589,1002,686,"zdxz.png");
                mFairy.onTap(0.8f, result, "自动选择", 1000);

                //选择分解的品质
                result = mFairy.findPic("select1.png");
                if (result.sim > 0.8) {
                    result = mFairy.findPic(706, 417, 741, 447, "affirm.png");
                    mFairy.onTap(0.7f, result, "确认选择", 1000);
                    select = true;
                }

                result = mFairy.findPic(346, 613, 413, 660, "resolve.png");
                if (result.sim > 0.8 && select) {
                    mFairy.onTap(0.8f, result, "确认分解,分解完成", 1000);
                    autoClear = false;
                    setTaskName(2);
                }
                overtime(20, 0);
            }

            int ran = 0;

            @Override
            public void content_2() throws Exception {
                overtime(25, 0);

                result1 = mFairy.findPic("pack return.png");
                mFairy.onTap(0.8f, result1, "点击返回背包详情", 1000);

                result = mFairy.findPic("pack.png");
                mFairy.onTap(0.8f, result, "打开背包", 2000);

                //背包界面
                result = mFairy.findPic("pack1.png");
                if (result.sim > 0.8 && result1.sim < 0.8) {
                    //使用
                    FindResult result3 = mFairy.findPic(814, 95, 896, 306, "shi.png");
                    mFairy.onTap(0.8f, result3, "使用", 500);

                    //批量
                    FindResult result4 = mFairy.findPic(new String[]{"batch.png", "batch1.png"});
                    mFairy.onTap(0.75f, result4, 839, 417, 857, 430, "选择最大", 500);
                    mFairy.onTap(0.75f, result4, 625, 486, 640, 498, "批量使用", 500);

                    //达到每日上限
                    result1 = mFairy.findPic(625, 16, 720, 110, "word hint pack upper limit.png");
                    if (result1.sim > 0.75f) {
                        LtLog.e(mFairy.getLineInfo("每日次数已达上限，向上滑动"));
                        mFairy.onTap(0.75f, result4, 971, 178, 985, 190, "关闭批量使用", 1000);
                        mFairy.ranSwipe(912, 543, 912, 150, 500, 1500);
                    }

                    result1 = mFairy.findPic(610, 16, 700, 110, "word hint already limit.png");
                    if (result1.sim > 0.75f) {
                        LtLog.e(mFairy.getLineInfo("次数已达限制，向上滑动"));
                        mFairy.onTap(0.75f, result4, 971, 178, 985, 190, "关闭批量使用", 1000);
                        mFairy.ranSwipe(912, 543, 912, 150, 500, 1500);
                    }

                    //等级不够
                    result1 = mFairy.findPic(605, 33, 667, 111, "word bu fuhe.png");
                    if (result1.sim > 0.75f) {
                        LtLog.e(mFairy.getLineInfo("等级不符合，向上滑动"));
                        mFairy.ranSwipe(912, 543, 912, 150, 500, 2500l, 0);
                    }

                    //使用和批量都不存在
                    if (result3.sim < 0.8f && result4.sim < 0.8f) {
                        for (String picName : canClear) {
                            result = mFairy.findPic(676, 163, 1136, 546, picName);
                            if (result.sim > 0.9f) {
                                mFairy.onTap(0.85f, result, "选中可使用道具" + picName, 500);
                                return;
                            }
                        }
                        if (result.sim < 0.9f) {
                            //向上滑动
                            mFairy.ranSwipe(912, 543, 912, 150, 500, 1500l, 0);

                            result = mFairy.findPic(670, 142, 741, 236, "pic packet lock.png");
                            if (result.sim > 0.8f) {
                                mFairy.onTap(1218, 70, 1234, 84, "清理背包，滑到底部，清理完成，关闭背包界面", 1000);
                                setTaskEnd();
                                return;
                            }

                            if (ran++ >= 5) {
                                mFairy.onTap(1218, 70, 1234, 84, "清理背包完成，关闭背包界面", 1000);
                                setTaskEnd();
                                return;
                            }
                            LtLog.e(mFairy.getLineInfo("向上滑动一次寻找=" + ran));
                        }
                    }
                }
            }

            @Override
            public void inOperation() throws Exception {
                result = mFairy.findPic("word remind.png");
                mFairy.onTap(0.8f, result, 971, 175, 985, 192, "关闭活动提醒", 500);

                result = mFairy.findPic("fuhuo.png");
                if (result.sim > 0.8) {
                    state = 1;
                    LtLog.e("被野怪或其他玩家打死，选择复活方式后，继续任务");
                    if (rebuild) {
                        mFairy.onTap(0.8f, result, 768, 359, 790, 380, "原地复活", 200);
                    } else {
                        mFairy.onTap(0.8f, result, 489, 351, 504, 365, "复活点复活", 200);
                    }
                    setTaskEnd();
                }

                result = mFairy.findPic(362, 240, 477, 335, "pic hint.png");
                if (result.sim > 0.8f) {
                    result = mFairy.findPic(555, 319, 694, 347, "zise.png");
                    mFairy.onTap(0.8f, result, "继续分解", 500);

                    //取消
                    result = mFairy.findPic(510, 394, 578, 465, "cancel.png");
                    mFairy.onTap(0.8f, result, "取消", 1000);

                    result = mFairy.findPic(508, 433, 579, 471, "fu_refuse.png");
                    mFairy.onTap(0.8f, result, "拒绝", 500);
                }

                result = mFairy.findPic(602, 382, 668, 454, "affirm.png");
                mFairy.onTap(0.8f, result, "确定", 500);
                super.inOperation();
            }
        }.taskContent(mFairy, "清理背包");
        return state;
    }

    //更改队伍目标
    //type:1、创建队伍 2、更换目标
    //section 一级目标(日常副本 挑战副本 混元图录)
    //taskName 二级目标(魔神分舵 河谷之森 黑风渊 再战毁灭山脉 )
    //levelInfo 混元图录的层具体关卡
    public int initTeamGoal(final int type, final String section, final String taskName, final String levelInfo) throws Exception {
        final String goal = type == 1 ? "创建队伍" : "更换目标";
        new Util(mFairy) {
            @Override
            public void content_0() throws Exception {
                state = 0;
                setTaskName(1);
            }

            //收起列表
            @Override
            public void content_1() throws Exception {
                //创建队伍
                if (type == 1) {
                    result = mFairy.findPic("platform.png");
                    if (result.sim > 0.8f) {
                        result = mFairy.findPic(364, 77, 408, 598, new String[]{"pic up.png", "up1.png"});
                        if (result.sim > 0.8f) {
                            mFairy.onTap(0.8f, result, "收起展开的列表", 1000);
                            return;
                        }
                        setTaskName(2);
                    }
                }//创建队伍

                //更改目标
                if (type == 2) {
                    result = mFairy.findPic("word edit interface.png");
                    if (result.sim > 0.8f) {
                        result = mFairy.findPic(486, 164, 536, 557, new String[]{"pic up.png", "up1.png"});
                        if (result.sim > 0.8f) {
                            mFairy.onTap(0.8f, result, "收起展开的列表", 1000);
                        }
                        setTaskName(2);
                    }
                }//更改目标

                //超时处理
                if (overtime(10, 0)) {
                    state = 0;
                    LtLog.e(mFairy.getLineInfo("收起列表超时"));
                    setTaskEnd();
                }//超时处理
            }

            @Override
            public void content_2() throws Exception {
                //创建队伍
                if (type == 1) {
                    result = mFairy.findPic("platform.png");
                    if (result.sim > 0.8f) {
                        result = mFairy.findPic(226, 80, 372, 631, new String[]{section + ".png", section + "1.png"});
                        if (result.sim > 0.8f) {
                            result1 = mFairy.findPic(result.x + 106, result.y, result.x + 157, result.y + 30, new String[]{"pic up.png", "pic up1.png"});
                            if (result1.sim < 0.8f)
                                mFairy.onTap(0.8f, result, "展开" + section, 1000);
                        }

                        //混元图录
                        if (section.contains("hytl")) {
                            result = mFairy.findPic(194, 509, 230, 627, "word copy " + taskName + ".png");
                            if (result.sim > 0.8f) {
                                mFairy.onTap(0.8f, result, "选中" + taskName, 1000);
                                if (!levelInfo.equals("") && levelInfo != null) {
                                    mFairy.onTap(0.8f, result, 1012, 604, 1030, 619, "创建队伍", 1000);
                                    setTaskName(3);
                                    return;
                                } else {
                                    state = 1;
                                    setTaskEnd();
                                    return;
                                }
                            }
                        } else {
                            result = mFairy.findPic(235, 165, 368, 622, "word copy " + taskName + ".png");
                            if (result.sim > 0.8f) {
                                mFairy.onTap(0.8f, result, "选中" + taskName, 1000);
                                state = 1;
                                setTaskEnd();
                            }
                        }
                    }
                }//创建队伍

                //更改队伍目标
                if (type == 2) {
                    result = mFairy.findPic("word edit interface.png");
                    if (result.sim > 0.8f) {
                        result = mFairy.findPic(351, 162, 498, 554, new String[]{section + ".png", section + "1.png"});
                        mFairy.onTap(0.8f, result, "展开" + section, 1000);

                        //混元图录
                        if (section.contains("hytl")) {
                            result = mFairy.findPic(318, 428, 357, 558, "word copy " + taskName + ".png");
                            if (result.sim > 0.8f) {
                                mFairy.onTap(0.8f, result, "选中" + taskName, 1000);
                                if (!levelInfo.equals("")) {
                                    mFairy.onTap(0.8f, result, 1012, 604, 1030, 619, "创建队伍", 1000);
                                    setTaskName(3);
                                    return;
                                } else {
                                    state = 1;
                                    setTaskEnd();
                                    return;
                                }
                            }
                        } else {
                            result = mFairy.findPic(351, 162, 498, 554, "word copy " + taskName + ".png");
                            if (result.sim > 0.8f) {
                                mFairy.onTap(0.8f, result, "选中" + taskName, 1000);
                                state = 1;
                                setTaskEnd();
                            }
                        }
                    }
                }//更该目标

                //超时处理
                if (overtime(10, 0)) {
                    state = 0;
                    LtLog.e(mFairy.getLineInfo(goal + "超时"));
                    setTaskEnd();
                }//超时处理
            }

            @Override
            public void content_3() throws Exception {
                if (section.contains("hytl")) {
                    if (type == 1) {
                        result = mFairy.findPic("platform.png");
                        if (result.sim > 0.8f) {
                            result = mFairy.findPic("word hy" + levelInfo + ".png");
                            if (result.sim > 0.8f) {
                                mFairy.onTap(0.8f, result, "选中" + levelInfo, 1000);
                                state = 1;
                                setTaskEnd();
                                return;
                            } else {
                                mFairy.onTap(930, 173, 946, 187, "展开更多关卡", 1000);
                            }
                        }
                    }

                    if (type == 2) {
                        result = mFairy.findPic("word edit interface.png");
                        if (result.sim > 0.8f) {
                            result = mFairy.findPic("word hy" + levelInfo + ".png");
                            if (result.sim > 0.8f) {
                                mFairy.onTap(0.8f, result, "选中" + levelInfo, 1000);
                                state = 1;
                                setTaskEnd();
                                return;
                            } else {
                                mFairy.onTap(930, 173, 946, 187, "展开更多关卡", 1000);
                            }
                        }
                    }
                }
                if (overtime(10, 0)) {
                    state = 0;
                    LtLog.e(mFairy.getLineInfo(goal + "混元图录查找关卡超时"));
                    setTaskEnd();
                    return;
                }
            }
        }.taskContent(mFairy, goal);
        return state;
    }

    int tastState = 0;//查找任务的结果(0:不能去执行、未开启、 已经完成  1:没有完成，前往执行)

    public int mission(final String str, final int option) throws Exception {
        new Util(mFairy) {
            int findtask = 0;

            /**
             * content_0流程控制
             * @throws Exception
             */
            public void content_0() throws Exception {

                if (findtask >= 1) {
                    LtLog.e("没有这个任务");
                    Util.this.tastState = 0;
                    setTaskEnd();
                    return;
                }

                result = mFairy.findPic("word dailyInterface.png");
                LtLog.e(mFairy.getLineInfo(0.8f, result, "日常界面"));
                if (result.sim > 0.8f) {
                    setTaskName(1);
                    return;
                }
                close();
                setTaskName(1);
            }

            /**
             * 打开日常并且寻找任务
             */
            public void content_1() throws Exception {
                if (overtime(8, 0)) return;
                result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                mFairy.onTap(0.8f, result, "日常", Sleep);

                result = mFairy.findPic("word dailyInterface.png");
                LtLog.e(mFairy.getLineInfo(0.85f, result, "日常界面"));
                if (result.sim > 0.8f) {
                    Thread.sleep(1000);
                    mFairy.condit();
                    //领取活跃奖励
                    result = mFairy.findPic(258, 578, 1114, 605, "red tip.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, "领取奖励", 1000);
                        return;
                    }

                    result = mFairy.findPic("get goods.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, 629, 404, 638, 414, "获得物品", 1000);
                        return;
                    }

                    result = mFairy.findPic(149, 153, 607, 522, str);
                    if (result.sim > 0.7f) {

                    } else {
                        if (option == 1) {
                            mFairy.onTap(1209, 135, 1225, 150, "日常", Sleep);
                        }
                        if (option == 2) {
                            mFairy.onTap(1212, 236, 1227, 252, "冒险", Sleep);
                        }
                    }
                    findtask++;
                    setTaskName(2);
                }
            }

            /***
             * str对应任务名称，周到并判断任务是否完成
             * @throws Exception
             */
            public void content_2() throws Exception {
                if (overtime(16, 0)) return;//计次并跳转
                result1 = mFairy.findPic(149, 153, 607, 522, str);
                LtLog.e(mFairy.getLineInfo(0.1f, result1, str));
                if (result1.sim > 0.7f) {
                    LtLog.e("找到任务");

                    result = mFairy.findPic(result1.x + 146, result1.y + 22, result1.x + 245, result1.y + 75, "yiwancheng.png");
                    if (result.sim > 0.7f) {
                        mFairy.onTap(0.7f, result, 1223, 72, 1232, 82, "完成", 1000);
                        Util.this.tastState = 0;
                        setTaskEnd();
                        return;
                    }

                    result = mFairy.findPic(result1.x + 168, result1.y + 9, result1.x + 219, result1.y + 43, "word task open.png");
                    if (result.sim > 0.7f) {
                        mFairy.onTap(0.7f, result, 1223, 72, 1232, 82, "未开启", 1000);
                        Util.this.tastState = 0;
                        setTaskEnd();
                        return;
                    }

                    result = mFairy.findPic(result1.x + 154, result1.y + 27, result1.x + 220, result1.y + 73, "word task already past.png");
                    if (result.sim > 0.7f) {
                        mFairy.onTap(0.7f, result, 1223, 72, 1232, 82, "已过期", 1000);
                        Util.this.tastState = 0;
                        setTaskEnd();
                        return;
                    }

                    mFairy.onTap(0.7f, result1, str, Sleep);
                    mFairy.onTap(0.7f, result1, 1062, 515, 1081, 534, "前往", 2000);
                    Util.this.tastState = 1;
                    setTaskEnd();
                    return;
                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6, 8, 10, 12, 14, 16}, 2, 400, 510, 400, 210, 1000, 2000);
            }

            @Override
            public void inOperation() throws Exception {
                result = mFairy.findPic(611, 395, 642, 468, "affirm.png");
                mFairy.onTap(0.8f, result, "点击确认", 500);
            }
        }.taskContent(mFairy, "找任务中");
        return tastState;
    }

    public int mission(final String str[], final int option) throws Exception {
        new Util(mFairy) {
            int findtask = 0;

            /**
             * content_0流程控制
             * @throws Exception
             */
            public void content_0() throws Exception {
                result = mFairy.findPic("word dailyInterface.png");
                LtLog.e(mFairy.getLineInfo(0.8f, result, "日常界面"));
                if (result.sim > 0.8f) {
                    if (findtask >= 2) {
                        LtLog.e("没有这个任务");
                        Util.this.tastState = 0;
                        setTaskEnd();
                        return;
                    }
                    setTaskName(1);
                    return;
                }
                close();
                setTaskName(1);
            }

            /**
             * 打开日常并且寻找任务
             */
            public void content_1() throws Exception {
                if (overtime(8, 0)) return;
                result = mFairy.findPic("richang.png");
                mFairy.onTap(0.8f, result, "日常", Sleep);

                result = mFairy.findPic("word dailyInterface.png");
                LtLog.e(mFairy.getLineInfo(0.85f, result, "日常界面"));
                if (result.sim > 0.8f) {
                    Thread.sleep(1500);
                    mFairy.condit();
                    //领取活跃奖励
                    result = mFairy.findPic(258, 578, 1114, 605, "red tip.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, "领取奖励", 1000);
                        return;
                    }

                    result = mFairy.findPic("get goods.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, 629, 404, 638, 414, "获得物品", 1000);
                        return;
                    }

                    result = mFairy.findPic(149, 153, 607, 522, str);
                    if (result.sim > 0.8f) {

                    } else {
                        if (option == 1) {
                            mFairy.onTap(1209, 135, 1225, 150, "日常", Sleep);
                        }
                        if (option == 2) {
                            mFairy.onTap(1212, 236, 1227, 252, "冒险", Sleep);
                        }
                    }
                    findtask++;
                    setTaskName(2);
                }
            }

            /***
             * str对应任务名称，周到并判断任务是否完成
             * @throws Exception
             */
            public void content_2() throws Exception {
                if (overtime(8, 0)) return;//计次并跳转
                result1 = mFairy.findPic(149, 153, 607, 522, str);
                LtLog.e(mFairy.getLineInfo(0.1f, result1, str[0]));
                if (result1.sim > 0.86f) {
                    LtLog.e("找到任务");
                    result = mFairy.findPic(result1.x + 146, result1.y + 22, result1.x + 245, result1.y + 75, "yiwancheng.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, 1223, 72, 1232, 82, "完成", 1000);
                        Util.this.tastState = 0;
                        setTaskEnd();
                        return;
                    }

                    result = mFairy.findPic(result1.x + 168, result1.y + 9, result1.x + 219, result1.y + 43, "word task open.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, 1223, 72, 1232, 82, "未开启", 1000);
                        Util.this.tastState = 0;
                        setTaskEnd();
                        return;
                    }

                    result = mFairy.findPic(result1.x + 154, result1.y + 27, result1.x + 220, result1.y + 73, "word task already past.png");
                    if (result.sim > 0.8f) {
                        mFairy.onTap(0.8f, result, 1223, 72, 1232, 82, "已过期", 1000);
                        Util.this.tastState = 0;
                        setTaskEnd();
                        return;
                    }


                    if (result.sim < 0.8f) {
                        mFairy.onTap(0.8f, result1, str[0], Sleep);
                        mFairy.onTap(0.8f, result1, 1062, 515, 1081, 534, "前往", 2000);
                        Util.this.tastState = 1;
                        setTaskEnd();
                        return;
                    }
                }
                mFairy.taskSlid(err, new int[]{0, 2, 4, 6}, 2, 400, 510, 400, 210, 1000, 2000);
            }

            @Override
            public void inOperation() throws Exception {
                result = mFairy.findPic(611, 395, 642, 468, "affirm.png");
                mFairy.onTap(0.8f, result, "点击确认", 500);
            }
        }.taskContent(mFairy, "找任务中");
        return tastState;
    }
}
