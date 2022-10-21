package com.script.fairy;
import com.script.framework.AtFairyImpl;
import com.script.framework.TaskContent;
import com.script.opencvapi.AtFairyConfig;
import com.script.opencvapi.FindResult;
import com.script.opencvapi.LtLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/30 0030.
 */
@SuppressWarnings("all")
public class LimitLessTask extends TaskContent {
    AtFairyImpl mFairy;
    FindResult result;
    FindResult result1;
    Util util;
    int picCount=0;
    TimingActivity timingActivity;
    public LimitLessTask(AtFairyImpl atFairy) throws Exception {
        mFairy = atFairy;
        util = new Util(mFairy);
        timingActivity=new TimingActivity(mFairy);
    }
    static boolean rebuild=false,peace=false,clear=false,call=false,init=false,tycf1=false,tycf=true,xrxs=false,
            expedition=false,follow=false,skill=false,equip=false,yiwen=false,wuqiong=false,qxh=false,yzty=false;
    boolean jzhj=false,jzdt=false,limitBoss=false,zlyw=false,xqzf=false,match3=false,gjzdz=false,jzwz=false;
    static int tower=0,place=0,coord_x=0,coord_y=0,rebuildCount=0;//挂机地点,坐标x,坐标y,复活次数
    static List<Integer> rebuildList=null;
    static Map<String,Integer> rebuildMap =null;
    long clearInter=10*60,expeditionInter=60*60,followInter=10*60,skillInter=30*60,equipInter=30*60,yiwenInter=30*60;
    static boolean openMap=true;//是否打开地图
    //初始化地图信息
    HashMap <Integer,String> map=null;
    public void intMap(){
        map=new HashMap();
        map.put(1,"dxy");//大雪原
        map.put(2,"hwj");//寒雾界
        map.put(3,"lqs");//冷琼山
        map.put(4,"hmsm");//毁灭山脉
        map.put(5,"yhd");//永恒殿
        map.put(6,"hfj");//黑风界
        map.put(7,"ymj");//炎魔界
        map.put(8,"ytj");//云天界
        map.put(9,"mwh");//迷雾海
        map.put(10,"qhj");//清河郡
        map.put(11,"ysc");//仪水城
        map.put(12,"lsy");//落神渊
        map.put(13,"sxj");//神墟界
        map.put(14,"yxc");//陨仙池
        map.put(15,"dyj");//铎羽郡

        map.put(16,"hwj:2");//寒雾界 二层
        map.put(17,"hwj:3");//寒雾界 三层
        map.put(18,"hwj:4");//寒雾界 四层
        map.put(19,"hwj:5");//寒雾界 五层

        map.put(20,"ytj:2");//云天界 二层
        map.put(21,"ytj:3");//云天界 三层
        map.put(22,"ytj:4");//云天界 四层
        map.put(23,"ytj:5");//云天界 五层

        map.put(24,"lsy:2");//落神渊 二层
        map.put(25,"lsy:3");//落神渊 三层
        map.put(26,"lsy:4");//落神渊 四层
        map.put(27,"lsy:5");//落神渊 五层

        map.put(28,"yhd:2");//永恒殿 二层
        map.put(29,"yhd:3");//永恒殿 三层
        map.put(30,"yhd:4");//永恒殿 四层
        map.put(31,"yhd:5");//永恒殿 五层

        map.put(32,"mwh:2");//迷雾海 二层
        map.put(33,"mwh:3");//迷雾海 三层
        map.put(34,"mwh:4");//迷雾海 四层
        map.put(35,"mwh:5");//迷雾海 五层

        map.put(36,"yxc:2");//陨仙池 二层
        map.put(37,"yxc:3");//陨仙池 三层
        map.put(38,"yxc:4");//陨仙池 四层
        map.put(39,"yxc:5");//陨仙池 五层

        map.put(40,"xlsx:1");//修罗圣墟 一层
        map.put(41,"xlsx:2");//修罗圣墟 二层
        map.put(42,"xlsx:3");//修罗圣墟 三层
        map.put(43,"xlsx:4");//修罗圣墟 四层
        map.put(44,"xlsx:5");//修罗圣墟 五层

        map.put(45,"hzsl:1");//火炤试炼 一层
        map.put(46,"hzsl:2");//火炤试炼 二层
        map.put(47,"hzsl:3");//火炤试炼 三层
        map.put(48,"hzsl:4");//火炤试炼 四层
        map.put(49,"hzsl:5");//火炤试炼 五层

        map.put(50,"ys:1");//异世火炤试炼 一层
        map.put(51,"ys:2");//异世火炤试炼 二层
        map.put(52,"ys:3");//异世火炤试炼 三层
        map.put(53,"ys:4");//异世火炤试炼 四层
        map.put(54,"ys:5");//异世火炤试炼 五层

        map.put(55,"wxsd:1");//无相神殿 一层
        map.put(56,"wxsd:2");//无相神殿 二层
        map.put(57,"wxsd:3");//无相神殿 三层
        map.put(58,"wxsd:4");//无相神殿 四层
        map.put(59,"wxsd:5");//无相神殿 五层

        map.put(60,"ys:1");//异世无相神殿 一层
        map.put(61,"ys:2");//异世无相神殿 二层
        map.put(62,"ys:3");//异世无相神殿 三层
        map.put(63,"ys:4");//异世无相神殿 四层
        map.put(64,"ys:5");//异世无相神殿 五层

        map.put(65,"qyd:1");//联盟地宫 一层
        map.put(66,"qyd:2");//联盟地宫 二层

        map.put(67,"qyd:1");//青云岛 一层
        map.put(68,"qyd:2");//青云岛 二层
        map.put(69,"qyd:3");//青云岛 三层
        map.put(70,"qyd:4");//青云岛 四层
        map.put(71,"qyd:5");//青云岛 五层

        map.put(72,"ptf:1");//磐天峰 一层
        map.put(73,"ptf:2");//磐天峰 二层
        map.put(74,"ptf:3");//磐天峰 三层
        map.put(75,"ptf:4");//磐天峰 四层
        map.put(76,"ptf:5");//磐天峰 五层

    }

    @Override
    public String toString() {
        return "LimitLessTask{" +
                "  jzhj=" + jzhj +
                ", jzdt=" + jzdt +
                ", limitBoss=" + limitBoss +
                ", zlyw=" + zlyw +
                ", xqzf=" + xqzf +
                ", match3=" + match3 +
                ", jzwz=" + jzwz +
                ", gjzdz=" + gjzdz +
                ", clearInter=" + clearInter +
                ", expeditionInter=" + expeditionInter +
                ", followInter=" + followInter +
                ", skillInter=" + skillInter +
                ", equipInter=" + equipInter +
                ", yiwenInter=" + yiwenInter +
                ", clear=" + clear +
                ", call=" + call +
                ", tycf1=" + tycf1 +
                ", xrxs=" + xrxs +
                ", expedition=" + expedition +
                ", follow=" + follow +
                ", skill=" + skill +
                ", equip=" + equip +
                ", yiwen=" + yiwen +
                '}';
    }

    //初始化坐标
    public void initCoord(int gmx,int gmy){
        //计算坐标
        switch (place){
            case 1:
                //大雪山(182,416)
                coord_x=(int)(gmx*1.2031+gmy*0.0009+262.9419);
                coord_y=(int)(gmx*0.0029+gmy*-1.2079+711.638);
                break;
            case 2:
                //寒雾界(172,199)
                coord_x=(int)(gmx*2.006+gmy*-0.0091+352.2828);
                coord_y=(int)(gmx*0.0103+gmy*-2.0027+667.0822);
                break;
            case 3:
                //冷琼山 (169,214)
                coord_x=(int)(gmx*1.3211+gmy*-0.0018+312.8003);
                coord_y=(int)(gmx*-0.0001+gmy*-1.3253+656.317);
                break;
            case 4:
                //毁灭山脉(175,213)
                coord_x=(int)(gmx*2.0+gmy*-0.0+244.0);
                coord_y=(int)(gmx*-0.0+gmy*-2.0+814.0);
                break;

            case 5:
                //永恒殿(198,199)
                coord_x=(int)(gmx*2.0+gmy*0.0+244.0);
                coord_y=(int)(gmx*-0.0043+gmy*-2.0226+820.5596);
                break;

            case 6:
                //黑风界 (374,310)
                coord_x=(int)(gmx*1.372+gmy*0.0094+297.4489);
                coord_y=(int)(gmx*-0.1101+gmy*-1.7969+844.0053);
                break;
            case 7:
                //炎魔界(392,155)
                coord_x=(int)(gmx*1.3408+gmy*-0.0062+310.5745);
                coord_y=(int)(gmx*0.015+gmy*-1.3456+683.8156);
                break;
            case 8:
                //云天界一层(207,222)
                coord_x=(int)(gmx*1.577+gmy*-0.0047+321.6638);
                coord_y=(int)(gmx*0.0035+gmy*-1.5939+715.4224);
                break;
            case 9:
                //迷雾海一层(100,93)
                coord_x=(int)(gmx*3.2972+gmy*-0.0252+314.8884);
                coord_y=(int)(gmx*0.0425+gmy*-3.2893+672.8412);
                break;
            case 10:
                //清河郡(337,85)
                coord_x=(int)(gmx*1.2728+gmy*0.0004+332.926);
                coord_y=(int)(gmx*0.0109+gmy*-1.2369+663.4966);
                break;
            case 11:
                //仪水城(220,87)
                coord_x=(int)(gmx*2.7515+gmy*0.005+228.2461);
                coord_y=(int)(gmx*-0.0219+gmy*-2.6941+733.2023);
                break;
            case 12:
                //落神渊一层(243,161)
                coord_x=(int)(gmx*1.3237+gmy*-0.0047+324.5448);
                coord_y=(int)(gmx*-0.0044+gmy*-1.3328+682.6698);
                break;
            case 13:
                //神墟界 (360,437)
                coord_x=(int)(gmx*1.279+gmy*0.0021+309.103);
                coord_y=(int)(gmx*0.0184+gmy*-1.2774+671.7117);
                break;
            case 14:
                //陨仙池(150,261)
                coord_x=(int)(gmx*2.1535+gmy*-0.0017+319.7236);
                coord_y=(int)(gmx*-0.0055+gmy*-2.1512+684.8098);
                break;
            case 15:
                //铎羽郡(333,244)
                coord_x=(int)(gmx*1.4452+gmy*-0.0008+278.3803);
                coord_y=(int)(gmx*0.0101+gmy*-1.4399+712.8072);
                break;
            case 16:
                //寒雾界二层
                coord_x=(int)(gmx*-0.0048+gmy*1.9965+333.5247);
                coord_y=(int)(gmx*2.0048+gmy*0.0035+67.4753);
                break;
            case 17:
                //寒雾界三层
                coord_x=(int)(gmx*-1.9977+gmy*-0.0148+932.1701);
                coord_y=(int)(gmx*0.0023+gmy*1.9852+50.1701);
                break;
            case 18:
                //寒雾界四层
                coord_x=(int)(gmx*0.0+gmy*-2.0+950.0);
                coord_y=(int)(gmx*-1.9459+gmy*0.0073+639.8783);
                break;
            case 19:
                //寒雾界五层
                coord_x=(int)(gmx*2.1777+gmy*-0.0065+290.104);
                coord_y=(int)(gmx*0.0103+gmy*-2.1635+725.8437);
                break;
            case 20:
                //云天界二层
                coord_x=(int)(gmx*1.5352+gmy*0.0263+327.4537);
                coord_y=(int)(gmx*0.0128+gmy*-1.5676+709.72);
                break;
            case 21:
                //云天界三层
                coord_x=(int)(gmx*-0.0221+gmy*1.5885+288.7812);
                coord_y=(int)(gmx*1.5804+gmy*0.0074+34.981);
                break;
            case 22:
                //云天界四层
                coord_x=(int)(gmx*-1.5605+gmy*0.0011+958.9428);
                coord_y=(int)(gmx*0.0307+gmy*1.6006+-4.405);
                break;
            case 23:
                //云天界五层
                coord_x=(int)(gmx*0.0244+gmy*-1.5759+988.9162);
                coord_y=(int)(gmx*-1.5825+gmy*-0.0002+679.8992);
                break;
            case 24:
                //落神渊二层
                coord_x=(int)(gmx*-0.0026+gmy*1.3054+325.6962);
                coord_y=(int)(gmx*1.3329+gmy*0.0069+36.6002);
                break;
            case 25:
                //落神渊三层
                coord_x=(int)(gmx*-1.308+gmy*0.0325+947.0372);
                coord_y=(int)(gmx*-0.0026+gmy*1.3221+38.0778);
                break;
            case 26:
                //落神渊四层
                coord_x=(int)(gmx*-0.011+gmy*-1.3192+964.2151);
                coord_y=(int)(gmx*-1.3216+gmy*0.0355+666.5294);
                break;
            case 27:
                //落神渊五层
                coord_x=(int)(gmx*2.7636+gmy*0.0135+294.6493);
                coord_y=(int)(gmx*-0.0025+gmy*-2.7762+751.2936);
                break;
            case 28:
                //永恒殿二层
                coord_x=(int)(gmx*0.0257+gmy*1.9954+181.9444);
                coord_y=(int)(gmx*1.9743+gmy*0.0046+-35.9444);
                break;
            case 29:
                //永恒殿三层
                coord_x=(int)(gmx*-2.0316+gmy*0.0028+1043.6528);
                coord_y=(int)(gmx*0.0316+gmy*1.9972+-102.6528);
                break;
            case 30:
                //永恒殿四层
                coord_x=(int)(gmx*-0.0192+gmy*-2.0045+1102.086);
                coord_y=(int)(gmx*-2.0192+gmy*-0.0045+759.086);
                break;
            case 31:
                //永恒殿五层
                coord_x=(int)(gmx*3.6166+gmy*-0.0112+191.9676);
                coord_y=(int)(gmx*0.0191+gmy*-3.6283+817.2703);
                break;
            case 32:
                //迷雾海二层
                coord_x=(int)(gmx*3.2889+gmy*-0.0222+317.4667);
                coord_y=(int)(gmx*-0.002+gmy*-3.2586+673.7939);
                break;
            case 33:
                //迷雾海三层
                coord_x=(int)(gmx*3.3045+gmy*0.0729+305.2959);
                coord_y=(int)(gmx*-0.024+gmy*-3.3156+681.4837);
                break;
            case 34:
                //迷雾海四层
                coord_x=(int)(gmx*3.2989+gmy*0.0693+305.8016);
                coord_y=(int)(gmx*0.0082+gmy*-3.3322+680.3628);
                break;
            case 35:
                //迷雾海五层
                coord_x=(int)(gmx*3.3194+gmy*-0.022+315.0567);
                coord_y=(int)(gmx*-0.0013+gmy*-3.3296+736.2597);
                break;
            case 36:
                //陨仙池二层
                coord_x=(int)(gmx*0.0098+gmy*2.1583+311.6742);
                coord_y=(int)(gmx*2.141+gmy*-0.0042+40.0274);
                break;
            case 37:
                //陨仙池三层
                coord_x=(int)(gmx*-2.1615+gmy*-0.0179+966.5235);
                coord_y=(int)(gmx*-0.01+gmy*2.1509+33.751);
                break;
            case 38:
                //陨仙池四层
                coord_x=(int)(gmx*0.011+gmy*-2.1488+965.9201);
                coord_y=(int)(gmx*-2.1846+gmy*-0.0083+684.0882);
                break;
            case 39:
                //陨仙池五层
                coord_x=(int)(gmx*2.2217+gmy*0.0163+363.0369);
                coord_y=(int)(gmx*-0.0079+gmy*-2.228+623.3359);
                break;
            case 40:
                //修罗圣墟一层
                coord_x=(int)(gmx *2.1342+gmy *0.0061+340.5676);
                coord_y=(int)(gmx *0.0074+gmy *-2.1951+733.8541);
                break;
            case 41:
                //修罗圣墟二层
                coord_x=(int)(gmx *0.0034+gmy *2.1415+272.2617);
                coord_y=(int)(gmx *2.1212+gmy *0.0124+58.2098);
                break;
            case 42:
                //修罗圣墟三层
                coord_x=(int)(gmx *-2.1369+gmy *0.0076+939.3306);
                coord_y=(int)(gmx *-0.0142+gmy *2.1451+-8.7807);
                break;
            case 43:
                //修罗圣墟四层
                coord_x=(int)(gmx *-0.0123+gmy *-2.1327+1009.9367);
                coord_y=(int)(gmx *-2.136+gmy *-0.0054+658.9631);
                break;
            case 44:
                //修罗圣墟五层
                coord_x=(int)(gmx *2.7125+gmy *0.0146+239.3604);
                coord_y=(int)(gmx *0.0008+gmy *-2.7447+772.2755);
                break;
            case 45:
                //火炤试炼一层
                coord_x=(int)(gmx *3.001+gmy *-0.0358+232.0007);
                coord_y=(int )(gmx *0.0197+gmy *-2.9966+617.4426);
                break;
            case 46:
                //火炤试炼二层
                coord_x=(int)(gmx *-0.0025+gmy *2.9542+383.151);
                coord_y=(int )(gmx *2.9588+gmy *-0.0451+-41.3552);
                break;
            case 47:
                //火炤试炼三层
                coord_x=(int)(gmx *-2.9776+gmy *-0.0136+1049.9603);
                coord_y=(int )(gmx *0.0708+gmy *3.0249+84.7831);
                break;
            case 48:
                //火炤试炼四层
                coord_x=(int)(gmx *-0.0179+gmy *-2.9778+904.21);
                coord_y=(int )(gmx *-3.0089+gmy *0.0111+769.105);
                break;
            case 49:
                //火炤试炼五层 171,225
                coord_x=(int)(gmx *2.6009+gmy *-0.0034+177.9586);
                coord_y=(int )(gmx *0.019+gmy *-2.6001+676.1894);
                break;
            case 50:
                //异世火炤试炼一层
                coord_x=(int)(gmx *3.001+gmy *-0.0358+232.0007);
                coord_y=(int )(gmx *0.0197+gmy *-2.9966+617.4426);
                break;
            case 51:
                //异世火炤试炼二层
                coord_x=(int)(gmx *-0.0025+gmy *2.9542+383.151);
                coord_y=(int )(gmx *2.9588+gmy *-0.0451+-41.3552);
                break;
            case 52:
                //异世火炤试炼三层
                coord_x=(int)(gmx *0.0332+gmy *2.9991+374.1564);
                coord_y=(int )(gmx *3.1295+gmy *0.2106+-81.2464);
                break;
            case 53:
                //异世火炤试炼四层
                coord_x=(int)(gmx *-0.0272+gmy *2.9873+383.7809);
                coord_y=(int )(gmx *2.9708+gmy *0.0102+-48.5204);
                break;
            case 54:
                //异世火炤试炼五层
                coord_x=(int)(gmx *-0.0172+gmy *2.6136+319.7421);
                coord_y=(int )(gmx *2.6+gmy *0.0098+-105.3827);
                break;
            case 55:
                //无相神殿一层
                coord_x=(int)(gmx *2.0+gmy *-0.0+244.0);
                coord_y=(int )(gmx *-0.0025+gmy *-2.0299+822.9604);
                break;
            case 56:
                //无相神殿二层
                coord_x=(int)(gmx *0.0225+gmy *1.9951+180.694);
                coord_y=(int )(gmx *2.0225+gmy *-0.0049+-42.306);
                break;
            case 57:
                //无相神殿三层
                coord_x=(int)(gmx *-2.0+gmy *-0.0+1038.0);
                coord_y=(int )(gmx *0.0045+gmy *2.0061+-101.4446);
                break;
            case 58:
                //无相神殿四层
                coord_x=(int)(gmx *0.0111+gmy *-2.022+1099.9237);
                coord_y=(int )(gmx *-2.0+gmy *0.0+755.0);
                break;
            case 59:
                //无相神殿五层
                coord_x=(int)(gmx *3.6558+gmy *0.0053+185.7775);
                coord_y=(int )(gmx *-0.0133+gmy *-3.6521+826.1482);
                break;

            case 60:
                //异世无相神殿一层
                coord_x=(int)(gmx *2.0209+gmy *0.0019+238.8775  );
                coord_y=(int )(gmx *-0.011+gmy *-2.009+818.662);
                break;
            case 61:
                //异世无相神殿二层
                coord_x=(int)(gmx *0.0066+gmy *2.0199+177.6821);
                coord_y=(int )(gmx *2.0209+gmy *0.0025+-44.3046);
                break;
            case 62:
                //异世无相神殿三层
                coord_x=(int)(gmx *0.0066+gmy *2.0199+177.6821);
                coord_y=(int )(gmx *2.0209+gmy *0.0025+-44.3046);
                break;
            case 63:
                //异世无相神殿四层
                coord_x=(int)(gmx *0.0066+gmy *2.0199+177.6821);
                coord_y=(int )(gmx *2.0209+gmy *0.0025+-44.3046);
                break;
            case 64:
                //异世无相神殿五层
                coord_x=(int)(gmx *0.0158+gmy *3.6454+173.0193);
                coord_y=(int )(gmx *3.6571+gmy *0.0271+-100.5792);
                break;

            case 65:
                //联盟地宫一层     联盟地宫地图就是目前最高等级地图如 原来事 120级的 现在是 130级的青云岛 再出高等级新地图 地宫地图也会改
                coord_x=(int)(gmx *2.1261+gmy *-0.0098+344.6238);
                coord_y=(int )(gmx *0.0142+gmy *-2.1311+722.9836);
                break;
            case 66:
                //联盟地宫二层 最高等级地图
                coord_x=(int)(gmx *-2.1427+gmy *-0.0093+943.2758);
                coord_y=(int )(gmx *0.0099+gmy *2.146+-12.8876);
                break;

            case 67:
                //130级青云岛一层
                coord_x=(int)(gmx *2.1261+gmy *-0.0098+344.6238);
                coord_y=(int )(gmx *0.0142+gmy *-2.1311+722.9836);
                break;
            case 68:
                //130级青云岛二层
                coord_x=(int)(gmx *-0.0046+gmy *2.1302+275.0827);
                coord_y=(int )(gmx *2.1422+gmy *-0.0032+57.7096);
                break;
            case 69:
                //130级青云岛三层
                coord_x=(int)(gmx *-2.1419+gmy *-0.0047+941.7754);
                coord_y=(int )(gmx *-0.0102+gmy *2.1395+-8.5136);
                break;
            case 70:
                //130级青云岛四层
                coord_x=(int)(gmx *0.0024+gmy *-2.1435+1009.4262);
                coord_y=(int )(gmx *-2.1408+gmy *-0.0029+659.6259);
                break;

            case 71:
                //130级青云岛五层
                coord_x=(int)(gmx *2.716+gmy *-0.0132+242.6952);
                coord_y=(int )(gmx *0.014+gmy *-2.7204+766.1859);
                break;

            case 72:
                //140级磐天峰一层
                coord_x=(int)(gmx *1.8521+gmy *0.0042+348.8247);
                coord_y=(int )(gmx *0.0023+gmy *-1.847+667.734);
                break;

            case 73:
                //140级磐天峰二层
                coord_x=(int)(gmx *0.0026+gmy *1.8584+329.2437);
                coord_y=(int )(gmx *1.8474+gmy *0.0035+66.2161);
                break;

            case 74:
                //140级磐天峰3层
                coord_x=(int)(gmx *-1.8608+gmy *0.0017+933.9462);
                coord_y=(int )(gmx *0.0023+gmy *1.8499+47.9286);
                break;

            case 75:
                //140级磐天峰4层
                coord_x=(int)(gmx *0.0019+gmy *-1.8488+950.4133);
                coord_y=(int )(gmx *-1.8563+gmy *0.0021+650.4361);
                break;

            case 76:
                //130级青云岛五层 == 第一层
                coord_x=(int)(gmx *1.8521+gmy *0.0042+348.8247);
                coord_y=(int )(gmx *0.0023+gmy *-1.847+667.734);
                break;
        }
    }

    //野外挂机
    public void hang()throws Exception{
        new LimitLessTask(mFairy){
            int restart=0;//零点重启 0不执行 1执行 2执行完成
            List taskList=null;
            ControlSplit back =null;
            @Override
            public void create() throws Exception {
                //初始化所有参数
                taskList=new ArrayList();
                rebuildList=new ArrayList();
                rebuildMap =new HashMap();
                rebuild=false;peace=false;clear=false;call=false;init=false;tycf1=false;tycf=true;xrxs=false;yzty=false;
                expedition=false;follow=false;skill=false;equip=false;yiwen=false;wuqiong=false;qxh=false;gjzdz=false;jzwz=false;
                rebuildCount=0;
                if (!AtFairyConfig.getOption("back").equals("")) {
                    back = strSplit(AtFairyConfig.getOption("back"));
                    LtLog.e("*------*"+back.timeMillis+"*----*"+back.choice);
                }
                //每日签到
                if(AtFairyConfig.getOption("sign").equals("1")){
                    taskList.add("sign");
                }

                //每日邮件
                if(AtFairyConfig.getOption("email").equals("1")){
                    taskList.add("email");
                }

                //免费购买5000绑银
                if(AtFairyConfig.getOption("free buy").equals("1")){
                    taskList.add("free buy");
                }

                //领取在线奖励
                if(AtFairyConfig.getOption("award").equals("1")){
                    taskList.add("award");
                }

                //坐骑捕获
                if(AtFairyConfig.getOption("capture").equals("1")){
                    taskList.add("capture");
                }

                //资源追回
                if(AtFairyConfig.getOption("take back").equals("1")){
                    taskList.add("take back");
                }
                //奋起直追
                if(AtFairyConfig.getOption("fqzz").equals("1")){
                    taskList.add("fqzz");
                }

                //每日礼包
                if(AtFairyConfig.getOption("mrlb").equals("1")){
                    taskList.add("mrlb");
                }


                //血刃悬赏
                if(AtFairyConfig.getOption("xrxs").equals("1")){
                    taskList.add("xrxs");
                }

                //天愚赐福
                if(AtFairyConfig.getOption("tycf").equals("1")){
                    tycf1=true;
                }

                //骁骑争锋
                if(AtFairyConfig.getOption("xqzf").equals("1")){
                    xqzf=true;
                }

                //真灵演武
                if(AtFairyConfig.getOption("zlyw").equals("1")){
                    zlyw=true;
                }

                //3v3排位赛
                if(AtFairyConfig.getOption("5277").equals("1")){
                    match3=true;
                }

                //剑指王者
                if(AtFairyConfig.getOption("6986").equals("1")){
                    jzwz=true;
                }

                //古界争夺战
                if(AtFairyConfig.getOption("6309").equals("1")){
                    gjzdz=true;
                }

                //无穷斗界
                if(AtFairyConfig.getOption("5741").equals("1")){
                    wuqiong=true;
                }
                //七星海
                if(AtFairyConfig.getOption("qxh").equals("1")){
                    LtLog.e("七星海判断是否勾选"+AtFairyConfig.getOption("qxh"));
                    qxh=true;
                }
                //全服限时首领
                if(AtFairyConfig.getOption("limitBoss").equals("1")){
                    limitBoss=true;
                }

                //家族喝酒
                if(AtFairyConfig.getOption("yzty").equals("1")){
                    yzty=true;
                }

                //家族喝酒
                if(AtFairyConfig.getOption("jzhj").equals("1")){
                    jzhj=true;
                }

                //家族答题
                if(AtFairyConfig.getOption("jzdt").equals("1")){
                    jzdt=true;
                }

                //选择地图
                if(!AtFairyConfig.getOption("place").equals("")){
                    place=Integer.parseInt(AtFairyConfig.getOption("place"));
                }

                ControlSplit split ;
                //清理背包
                if(!AtFairyConfig.getOption("clearInter").equals("")){
                    split = strSplit(AtFairyConfig.getOption("clearInter"));
                    if(split.choice==1){
                        clear=true;
                        //清理背包间隔
                        clearInter =split.timeMillis;
                    }
                }

                //远征
                if(!AtFairyConfig.getOption("expeditionInter").equals("")){
                    split = strSplit(AtFairyConfig.getOption("expeditionInter"));
                    if(split.choice==1){
                        expedition=true;
                        //每日远征间隔
                        expeditionInter=split.timeMillis;
                    }
                }


                //技能升级
                if(!AtFairyConfig.getOption("skillInter").equals("")){
                    split = strSplit(AtFairyConfig.getOption("skillInter"));
                    if(split.choice==1){
                        skill=true;
                        //技能强化间隔
                        skillInter=split.timeMillis;
                    }
                }


                //装备强化
                if(!AtFairyConfig.getOption("equipInter").equals("")){
                    split = strSplit(AtFairyConfig.getOption("equipInter"));
                    if(split.choice==1){
                        equip=true;
                        //装备强化间隔
                        equipInter=split.timeMillis;
                    }
                }

                //异闻
                if(!AtFairyConfig.getOption("yiwenInter").equals("")){
                    split = strSplit(AtFairyConfig.getOption("yiwenInter"));
                    if(split.choice==1){
                        yiwen=true;
                        //异闻间隔
                        yiwenInter=split.timeMillis;
                    }
                }

                //队长拉跟战
                if(!AtFairyConfig.getOption("followInter").equals("")){
                    split = strSplit(AtFairyConfig.getOption("followInter"));
                    if(split.choice==1){
                        follow=true;
                        //每日远征间隔
                        followInter=split.timeMillis;
                    }
                }

                //召唤宠物
                if(AtFairyConfig.getOption("call").equals("1")){
                    call=true;
                }

                //是否原地复活
                if(AtFairyConfig.getOption("rebuild").equals("1")){
                    rebuild=true;
                }

                //零点重启
                if(!AtFairyConfig.getOption("restart").equals("1")){
                    restart=1;
                }

                //和平模式
                if(AtFairyConfig.getOption("peace").equals("1")){
                    peace=true;
                }

                //循环获取
                //hangPlace挂机选项 place地图 tower传送塔 coord坐标
                for(int i=1;i<=3;i++){
                    if(AtFairyConfig.getOption("hangPlace"+i).equals("1")){
                        //传送塔
                        if(!AtFairyConfig.getOption("tower"+i).equals("")){
                            rebuildMap.put("tower"+i,Integer.parseInt(AtFairyConfig.getOption("tower"+i)));
                        }
                        //刷野地图
                        if(!AtFairyConfig.getOption("place"+i).equals("")){
                            rebuildMap.put("place"+i,Integer.parseInt(AtFairyConfig.getOption("place"+i)));
                        }
                        //坐标
                        if(!AtFairyConfig.getOption("coord"+i).equals("")){
                            String coord=AtFairyConfig.getOption("coord"+i);
                            String coords []=coord.split(",");
                            rebuildMap.put("coord_x"+i,Integer.parseInt(coords[0]));
                            rebuildMap.put("coord_y"+i,Integer.parseInt(coords[1]));
                        }
                        if(rebuildMap.get("tower"+i)!=null&&rebuildMap.get("place"+i)!=null&&rebuildMap.get("coord_x"+i)!=null&&rebuildMap.get("coord_y"+i)!=null){
                            if(rebuildMap.get("place"+i)!=0&& rebuildMap.get("coord_x"+i)!=0&& rebuildMap.get("coord_y"+i)!=0){
                                rebuildList.add(i);
                            }
                        }
                    }
                }

                if(rebuildList.size()!=0){
                    int rebuild=rebuildList.get(0);
                    tower=rebuildMap.get("tower"+rebuild);
                    place=rebuildMap.get("place"+rebuild);
                    int x=rebuildMap.get("coord_x"+rebuild);
                    int y=rebuildMap.get("coord_y"+rebuild);
                    initCoord(x,y);//初始化坐标
                }else{
                    tower=0;
                }

                intMap();//初始化地图
                LtLog.e(mFairy.getLineInfo("create完成="+this.toString()));
            }

            @Override
            public void content_0() throws Exception {
                util.close();
                openMap=true;
                init=false;
                limitless();
                if(taskList.size()!=0){
                    setTaskName(1);
                }else{
                    setTaskName(2);
                }
            }

            //领奖之类的任务
            @Override
            public void content_1() throws Exception {
                if(taskList.size()==0){
                    LtLog.e(mFairy.getLineInfo("所有任务已经完成"));
                    setTaskName(0);
                    return;
                }

                //每日签到
                if(taskList.get(0).equals("sign")){
                    timingActivity.hang_sign();
                    taskList.remove(0);
                    return;
                }

                //领取邮件
                if(taskList.get(0).equals("email")){
                    timingActivity.hang_email();
                    taskList.remove(0);
                    return;
                }


                //免费领5000绑银
                if(taskList.get(0).equals("free buy")){
                    timingActivity.hang_free();
                    taskList.remove(0);
                    return;
                }

                //领取在线奖励
                if(taskList.get(0).equals("award")){
                    timingActivity.hang_award();
                    taskList.remove(0);
                    return;
                }

                //坐骑捕获
                if(taskList.get(0).equals("capture")){
                    timingActivity.timing_capture();
                    taskList.remove(0);
                    return;
                }

                //资源追回
                if(taskList.get(0).equals("take back")){
                    timingActivity.hang_zyzh();
                    taskList.remove(0);
                    return;
                }
                //奋起直追
                if(taskList.get(0).equals("fqzz")){
                    timingActivity.fqzz();
                    taskList.remove(0);
                    return;
                }
                //每日礼包
                if(taskList.get(0).equals("mrlb")){
                    timingActivity.hang_mrlb();
                    taskList.remove(0);
                    return;
                }

                //血刃悬赏
                if(taskList.get(0).equals("xrxs")){
                    limitLess_xrxs();
                    taskList.remove(0);
                    return;
                }
            }

            //地图 选中要前往的地方
            @Override
            public void content_2() throws Exception {
                result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                result1=mFairy.findPic(new String[]{"pic load.png","pic transfer.png"});
                if(result.sim>0.8f)LtLog.e(mFairy.getLineInfo("寻路中.."));
                if(result1.sim>0.8f)LtLog.e(mFairy.getLineInfo("传送中.."));
                if(result.sim<0.8f&&result1.sim<0.8f){
                    overtime(30,0);
                }else{
                    err=0;
                    return;
                }

                if(place==0){
                    setTaskName(4);
                    LtLog.e("不进入地图,直接挂机");
                    return;
                }

                //先判断一次当前地图对不对
                /*result=mFairy.findMultiColor(1243,12,1263,27,0.95f,"240,246,245",
                        "1|0|245,252,251&2|0|244,249,249&3|0|243,246,246&4|0|240,243,244&5|0|241,244,245&7|0|249,249,248&6|1|252,252,252&5|1|253,253,252&4|1|253,253,252");*/
                result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                if(result.sim > 0.8f){
                    //判断地图对不对
                    String placeName=map.get(place).split(":")[0];
                    LtLog.e("*------------*"+placeName);
                    //云天界
                    if(place>=20&&place<=23)result1=mFairy.findPic(1080,4,1166,31,"word place "+placeName+".png");
                    else result1=mFairy.findPic("word place "+placeName+".png");
                   // result1=mFairy.findPic("word place ytj.png");
                    if(result1.sim>0.75f){
                        setTaskName(3);
                        LtLog.e(mFairy.getLineInfo("到达对应地图"));
                        return;
                    }//地图一样
                }

                //异世地图 特别处理
                if((place>=50&&place<=54)||(place>=60&&place<=64)){
                    int goalFloor=0;
                    if(place>=50&&place<=54)goalFloor=place-49;//异世火炤试炼
                    if(place>=60&&place<=64)goalFloor=place-59;//异世无相神殿
                    result=mFairy.findPic("auto ing.png");
                    mFairy.onTap(0.8f,result,"停止自动攻击",1000);

                    result=mFairy.findPic("word ysyj.png");
                    mFairy.onTap(0.8f,result,"进入异世遗迹",1000);

                    result=mFairy.findPic("word place yshzsl.png");
                    if(result.sim>0.8f)setTaskName(4);

                    //异世遗迹界面
                    result=mFairy.findPic("word ysyj interface.png");
                    if(result.sim>0.8f){
                        result=mFairy.findPic("word ysyj floor "+goalFloor+".png");
                        if(result.sim>0.95){
                            result=mFairy.findPic("word btn go battleground.png");
                            mFairy.onTap(0.8f,result,"前往战场",2000);
                        }else{
                            result=mFairy.findPic("pic ysyj open.png");
                            if(result.sim>0.8f){
                                mFairy.onTap(0.8f,result,998,315+(goalFloor-1)*36,1006,324+(goalFloor-1)*36,"进入异世"+goalFloor+"层",1000);
                            }else{
                                mFairy.onTap(1089,504,1099,515,"展开层数",1000);
                            }
                        }
                    }
                    return;
                }

                //联盟地宫地图
                if(place==65||place==66){
                    result=mFairy.findPic("pic yjzl enter.png");
                    mFairy.onTap(0.8f,result,"进入异界逐鹿",2000);

                    result=mFairy.findPic(647,572,930,704,"yijie.png");
                    mFairy.onTap(0.8f,result,"进入异界逐鹿",2000);

                    result=mFairy.findPic("pic yjzl world.png");
                    mFairy.onTap(0.8f,result,"切换世界分页",1000);

                    result=mFairy.findPic("pic yjzl flag.png");
                    if(result.sim>0.8f){
                        result=mFairy.findPic(318,171,363,223,"word league me.png");
                        mFairy.onTap(0.8f,result,272,209,288,256,"盘龙地宫",2500);

                        result=mFairy.findPic(248,352,305,397,"word league me.png");
                        mFairy.onTap(0.8f,result,425,467,449,531,"九鼎地宫",2500);

                        result=mFairy.findPic(624,91,690,138,"word league me.png");
                        mFairy.onTap(0.8f,result,775,202,796,255,"莽荒地宫",2500);

                        result=mFairy.findPic(705,441,770,486,"word league me.png");
                        mFairy.onTap(0.8f,result,868,471,892,530,"星辰地宫",2500);

                        result=mFairy.findPic(878,236,950,282,"word league me.png");
                        mFairy.onTap(0.8f,result,1043,330,1070,393,"薪火地宫",2500);
                    }

                    result=mFairy.findPic("word btn back server.png");
//                    result1=mFairy.findPic(1114,112,1244,173,"ceng.png");
                    if(result.sim>0.8f){
                        LtLog.e("1111");
                        setTaskName(3);
                        return;
                    }
                    return;
                }

                //本服的地图，不使用传送塔，地图前往
                if(tower==0){
                    if(openMap){
                        try {
                            /*result=mFairy.findMultiColor(1243,12,1263,27,0.95f,"240,246,245",
                                    "1|0|245,252,251&2|0|244,249,249&3|0|243,246,246&4|0|240,243,244&5|0|241,244,245&7|0|249,249,248&6|1|252,252,252&5|1|253,253,252&4|1|253,253,252");*/
                            result=mFairy.findPic(1225,3,1279,40,new String[]{"DT.png","DT1.png"});
                            mFairy.onTap(0.8f,result,1168,92,1185,107,"打开区域地图",500);
                            if(picCount(0.8f,result,"map down")>=10)util.back_city();
                        }catch (Exception e){
                            LtLog.e(mFairy.getLineInfo("获取出错"));
                            LtLog.e(mFairy.getLineInfo(e.getMessage()));
                        }
                    }

                    result=mFairy.findPic("world map.png");
                    mFairy.onTap(0.8f,result,"打开世界地图",500);

                    result=mFairy.findPic("word yijie map interface.png");
                    if(result.sim>0.8f){
                        LtLog.e(mFairy.getLineInfo("在联盟地宫，回城"));
                        util.close();
                        util.back_city();
                        return;
                    }

                    //世界地图界 要去的挂机地
                    result=mFairy.findPic("map.png");
                    if(result.sim>0.8){
                        switch (place){
                            case 1:
                                result=mFairy.findPic("hang_daxueyuan.png");
                                mFairy.onTap(0.8f,result,"进入大雪原",200);
                                break;
                            case 2:
                                result=mFairy.findPic("hang_hanwujie.png");
                                mFairy.onTap(0.8f,result,"进入寒雾界",200);
                                break;
                            case 3:
                                result=mFairy.findPic("hang_lengqiongshan.png");
                                mFairy.onTap(0.8f,result,"进入冷琼山",200);
                                break;

                            case 4:
                                result=mFairy.findPic("hang_huimieshanmai.png");
                                mFairy.onTap(0.8f,result,"进入毁灭山脉",200);
                                break;

                            case 5:
                                result=mFairy.findPic("hang_yonghengdian.png");
                                mFairy.onTap(0.8f,result,"进入永恒殿",200);
                                break;

                            case 6:
                                result=mFairy.findPic("hang_heifnegjie.png");
                                mFairy.onTap(0.8f,result,"进入黑风界",200);
                                break;
                            case 7:
                                result=mFairy.findPic("hang_yanmojie.png");
                                mFairy.onTap(0.8f,result,"进入炎魔界",200);
                                break;
                            case 8:
                                result=mFairy.findPic("hang_yuntianjie.png");
                                mFairy.onTap(0.8f,result,"进入云天界",200);
                                break;

                            case 9:
                                result=mFairy.findPic("hang_miwuhai.png");
                                mFairy.onTap(0.8f,result,"进入迷雾海",200);
                                break;
                            case 10:
                                result=mFairy.findPic("hang_qinghejun.png");
                                mFairy.onTap(0.8f,result,"进入清河郡",200);
                                break;
                            case 11:
                                result=mFairy.findPic("hang_yishuicheng.png");
                                mFairy.onTap(0.8f,result,"进入仪水城",200);
                                break;
                            case 12:
                                result=mFairy.findPic("hang_luoshenyuan.png");
                                mFairy.onTap(0.8f,result,"进入落神渊",200);
                                break;
                            case 13:
                                result=mFairy.findPic("hang_shenxujie.png");
                                mFairy.onTap(0.8f,result,"进入神墟界",200);
                                break;
                            case 14:
                                result=mFairy.findPic("hang_yunxianchi.png");
                                mFairy.onTap(0.8f,result,"进入陨仙池",200);
                                break;
                            case 15:
                                result=mFairy.findPic("hang_duoyujun.png");
                                mFairy.onTap(0.8f,result,"进入铎羽郡",200);
                                break;
                        }
                        //地宫
                        //寒雾界
                        if(place>=16&&place<=19){
                            result=mFairy.findPic("hang_hanwujie.png");
                            mFairy.onTap(0.8f,result,"进入寒雾界",2000);
                        }
                        //云天界
                        if(place>=20&&place<=23){
                            result=mFairy.findPic("hang_yuntianjie.png");
                            mFairy.onTap(0.8f,result,"进入云天界",2000);
                        }
                        //落神渊
                        if(place>=24&&place<=27){
                            result=mFairy.findPic("hang_luoshenyuan.png");
                            mFairy.onTap(0.8f,result,"进入落神渊",2000);
                        }
                        //永恒殿
                        if(place>=28&&place<=31){
                            result=mFairy.findPic("hang_yonghengdian.png");
                            mFairy.onTap(0.8f,result,"进入永恒殿",2000);
                        }
                        //迷雾海
                        if(place>=32&&place<=35){
                            result=mFairy.findPic("hang_miwuhai.png");
                            mFairy.onTap(0.8f,result,"进入迷雾海",2000);
                        }
                        //迷雾海
                        if(place>=36&&place<=39){
                            result=mFairy.findPic("hang_yunxianchi.png");
                            mFairy.onTap(0.8f,result,"进入陨仙池",2000);
                        }
                        //修罗圣墟
                        if(place>=40&&place<=44){
                            result=mFairy.findPic("hang_xiuluoshengxu.png");
                            mFairy.onTap(0.8f,result,"进入修罗圣墟",2000);
                        }
                        //火炤炼狱
                        if(place>=45&&place<=49){
                            result=mFairy.findPic(new String[]{"hang_huozhaolianyu.png","hang_huozhaolianyu1.png"});
                            mFairy.onTap(0.8f,result,"进入火炤炼狱",2000);
                        }
                        //无相神殿
                        if(place>=55&&place<=59){
                            result=mFairy.findPic("hang_wuxiangshendian.png");
                            mFairy.onTap(0.8f,result,"进入无相神殿",2000);
                        }
                        //青云岛
                        if(place>=67&&place<=71){
                            result=mFairy.findPic("qingyundao.png");
                            mFairy.onTap(0.8f,result,"进入青云岛",2000);
                        }

                        //青云岛
                        if(place>=72&&place<=76){
                            result=mFairy.findPic("pantianfeng.png");
                            mFairy.onTap(0.8f,result,"进入磐天峰",2000);
                        }
                    }

                    //等级不够,无法入内,不前往地图
                    result=mFairy.findPic("word can enter.png");
                    if(result.sim>0.8){
                        place=0;
                        setTaskName(0);
                        LtLog.e(mFairy.getLineInfo(0.8f,result,"等级不够，无法前往"));
                        return;
                    }

                    result=mFairy.findPic(545,295,649,345,"qianwang.png");
                    if (result.sim>0.8){
                        mFairy.onTap(719,408,729,420,"确定前往",2000);
                        openMap=true;
                        mFairy.sleep(5000);
                        setTaskName(3);
                        return;
                    }
                } else{
                    //使用传送塔前往
                    result=mFairy.findPic(new String[]{"word find.png","word way.png"});
                    result1=mFairy.findPic(new String[]{"pic load.png","pic transfer.png"});
                    if(result.sim<0.8f&&result1.sim<0.8f){
                        result = mFairy.findPic(new String[]{"list.png","list1.png","list3.png"});
                        mFairy.onTap(0.7f,result,"打开列表",1000);

                        result=mFairy.findPic(904,609,955,637,"word qiecuo.png");
                        result1=mFairy.findPic(997,607,1060,634,new String[]{"word chuansong ta.png","word chuansong ta1.png"});
                        if(result.sim>0.8f){
                            if(result1.sim>0.8f){
                                mFairy.onTap(0.8f,result1,"传送塔",1000);
                            }else{
                                tower=0;
                                LtLog.e(mFairy.getLineInfo("没有家族传送塔"));
                                setTaskName(0);
                                return;
                            }
                        }

                        result=mFairy.findPic("word hint chuansong.png");
                        mFairy.onTap(0.8f,result,720,407,731,420,"传送到传送塔",3000);

                        //家族传送塔界面 点击传送
                        result=mFairy.findPic("word tower.png");
                        if(result.sim>0.8f){
                            if(tower==1){
                                mFairy.onTap(0.8f,result,403,489,409,495,"玄水传送塔",3000);
                            }
                            if(tower==2){
                                mFairy.onTap(0.8f,result,882,486,887,493,"赤炎传送塔",3000);
                            }
                            LtLog.e("333333");
                            setTaskName(3);
                            return;
                        }
                    }
                }
            }

            int currentFloor=0,goalFloor=0;
            //判断是不是当前地图，判断是不是相对于楼层
            //选择地图
            public void content_3() throws Exception{
                overtime(90,0);
                result=mFairy.findPic(new String[]{"pic load.png","pic transfer.png"});
                if(result.sim>0.8f){
                    LtLog.e(mFairy.getLineInfo("地图传送中"));
                    err=0;
                }

                //使用传送塔
                if(tower!=0){
                    result=mFairy.findPic("word tower.png");
                    int picCount=picCount(0.8f,result,"tower");
                    if(result.sim>0.8f){
                        err=0;
                        if(tower==1){
                            mFairy.onTap(0.8f,result,403,489,409,495,"玄水传送塔",3000);
                        }
                        if(tower==2){
                            mFairy.onTap(0.8f,result,882,486,887,493,"赤炎传送塔",3000);
                        }
                        result1=mFairy.findPic("word not have.png");
                        if(result1.sim>0.8f){
                            LtLog.e(mFairy.getLineInfo("家族没有该传送塔"));
                            mFairy.onTap(0.8f,result,1019,150,1034,164,"关闭家族传送塔",1000);
                            tower=0;
                            setTaskName(0);
                            return;
                        }
                        if(picCount>=5){
                            mFairy.onTap(0.8f,result,1019,150,1034,164,"关闭家族传送塔",1000);
                            LtLog.e(mFairy.getLineInfo("等级不够，或者没有该传送塔"));
                            tower=0;
                            setTaskName(0);
                            return;
                        }
                        return;
                    }
                }

                //主界面 判断当 前地图和 当前楼层
                //currentFloor当前楼层 goalFloor要前往的目标楼层
                String placeName=map.get(place).split(":")[0];
                /*result=mFairy.findMultiColor(1243,12,1263,27,0.95f,"240,246,245",
                        "1|0|245,252,251&2|0|244,249,249&3|0|243,246,246&4|0|240,243,244&5|0|241,244,245&7|0|249,249,248&6|1|252,252,252&5|1|253,253,252&4|1|253,253,252");*/
                result1=mFairy.findPic(1187,3,1277,40,"map down.png");
                if(result1.sim>0.9f){
                    //判断地图对不对
                    if(place>=20&&place<=23){
                        //云天界
                        result1=mFairy.findPic(1080,2,1276,40,"word place "+placeName+".png");
                    }else if (place>=65&&place<=66){
                        //result1=mFairy.findPic(1114,112,1244,173,"ceng.png");
                    }else {
                        result1=mFairy.findPic(1080,2,1276,40,"word place "+placeName+".png");
                    }
                    LtLog.e("相似度"+placeName+result1.sim);
                    if(result1.sim < 0.75f){
                        LtLog.e(mFairy.getLineInfo("不是对应地图，重新前往，"+result1.toString()));
                        if(tower!=0)tower=0;
                        setTaskName(0);
                        return;
                    }

                    //地宫地图
                    if(place>=16&&place!=40){
                        goalFloor=Integer.parseInt(map.get(place).split(":")[1]);
                    }else{
                        //不是地宫地图
                        LtLog.e(mFairy.getLineInfo("到达地图点"));
                        setTaskName(4);
                        return;
                    }

                    int max=0;
                    FindResult results []=new FindResult [5];
                    for(int i=1;i<=5;i++){
                        //层数
                        if(place>=20&&place<=23){
                            //云天界
                            result=mFairy.findPic(1138,4,1210,34,new String[]{"word floor"+i+".png","word floor"+i+"1.png"});
                        }else if((place>=41&&place<=49)||(place>=55&&place<=59)){
                            //修罗圣墟 火炤试炼 无相神殿 四位数
                            result=mFairy.findPic(1172,3,1219,35,"word floor"+i+".png");
                        }else if((place>=50&&place<=54)||(place>=60&&place<=64)){
                            //异世火炤试炼 五位数
                            result=mFairy.findPic(1193,4,1237,32,"word floor"+i+".png");
                        }else if(place>=65&&place<=66){
                            //联盟地宫 六位数
                            result=mFairy.findPic(1182,3,1227,32,"word floor"+i+".png");
                        }else if (place >=67 && place <=71){
                            result=mFairy.findPic(1158,1,1216,34,"word floor"+i+".png");
                        } else{
                            result=mFairy.findPic(1161,5,1206,32,"word floor"+i+".png");
                        }
                        results[i-1]=result;
                    }
                    for(int i=0;i<=3;i++){
                        if(results[i+1].sim>results[max].sim){
                            max=i+1;
                        }
                    }
                    if(results[max].sim>0.8f){
                        currentFloor=max+1;
                        LtLog.e(mFairy.getLineInfo("当前楼层"+currentFloor));

                        if(((place>=50&&place<=54)||((place>=60&&place<=64)))&&(currentFloor!=goalFloor)){
                            LtLog.e(mFairy.getLineInfo("异世界楼层不对"));

                            result=mFairy.findPic("auto ing.png");
                            mFairy.onTap(0.8f,result,"停止自动攻击",1000);

                            result=mFairy.findPic("word ysyj.png");
                            mFairy.onTap(0.8f,result,"进入异世遗迹",1000);
                            setTaskName(2);
                            return;
                        }
                        mFairy.onTap(1168,92,1185,107,"打开区域地图",1000);
                    }
                }

                //地图界面
                result=mFairy.findPic("world map.png");
                picCount=picCount(0.8f,result,"world map.png");
                if(picCount!=0&&picCount%5==0)LtLog.e(mFairy.getLineInfo("地图界面"));
                if(result.sim>0.8f){
                    err=0;
                    //当前楼层==目标楼层
                    if(currentFloor==goalFloor){
                        LtLog.e(mFairy.getLineInfo("到达对应楼层"));
                        timekeepInit("定位坐标");
                        setTaskName(4);
                        return;
                    }
                    //当前楼层>目标楼层 下楼
                    if(currentFloor>goalFloor){
                        go(2,placeName,currentFloor);
                    }
                    //当前楼层<目标楼层 上楼
                    if(currentFloor<goalFloor){
                        go(1,placeName,currentFloor);
                    }
                }
            }

            //到达刷图地点
            @Override
            public void content_4() throws Exception {
                if(place==0){//没有选择挂机地点
                    openMap=false;
                }
                //还没到挂机地点
                if(openMap){
                    result=mFairy.findPic(new String[]{"pic load.png","pic transfer.png"});
                    if(result.sim<0.8f)overtime(15,0);

                   /* result=mFairy.findMultiColor(1243,12,1263,27,0.95f,"240,246,245",
                            "1|0|245,252,251&2|0|244,249,249&3|0|243,246,246&4|0|240,243,244&5|0|241,244,245&7|0|249,249,248&6|1|252,252,252&5|1|253,253,252&4|1|253,253,252");*/
                    result1=mFairy.findPic("map down.png");
                    if(result1.sim>0.9f){
                        mFairy.onTap(1168,92,1185,107,"打开区域地图",1500);
                    }
                    result=mFairy.findPic("world map.png");
                    if(result.sim>0.8 && place!=65&&place!=66){
                        mFairy.tap((int) coord_x, (int) coord_y);
                        LtLog.e(mFairy.getLineInfo("前往 坐标x=" + (int) coord_x + ",y=" + (int) coord_y));

//                        mFairy.onTap(coord_x,coord_y,coord_x+1,coord_y+1,"点击屏幕坐标前往1",1000);
//                        mFairy.onTap(coord_x,coord_y,coord_x+1,coord_y+1,"点击屏幕坐标前往2",1000);

                        result=mFairy.findPic(890,3,1275,235,new String[]{"close.png","close2.png"});
                        mFairy.onTap(0.8f,result,"关闭区域地图",1000);
                        openMap=false;
                        timekeepInit("back");
                    }else if (result.sim>0.8f){
                        result=mFairy.findPic("pic open guide.png");
                        mFairy.onTap(0.8f,result,"打开传送指引",1000);

                        result=mFairy.findPic(4,32,114,251,"guaiwu.png");
                        mFairy.onTap(0.8f,result,"打开怪物",1000);

                        result=mFairy.findPic(4,32,114,251,"guaiwu2.png");
                        LtLog.e("------"+result.sim);
                        if (result.sim > 0.8f){
                            mFairy.onTap(0.8f,result,334,159,339,167,"去挂机点",5000);
                            mFairy.onTap(0.8f,result,1223,41,1237,53,"关闭",2000);
                            mFairy.onTap(0.8f,result,1223,41,1237,53,"关闭",1000);
                            openMap=false;
                            timekeepInit("back");
                        }

                    }
                }else{
                    //和平模式
                    if(peace){
                        result=mFairy.findPic("peace.png");
                        if(result.sim<0.7){
                            mFairy.onTap(173,78,177,85,"展开",1000);
                            mFairy.onTap(490,100,493,104,"和平模式",1000);
                        }
                    }

                    //到达挂机地点
                    result=mFairy.findPic(new String []{"zi.png","way.png"});
                    if(result.sim<0.8){
                        result=mFairy.findPic("accept1.png");
                        mFairy.onTap(0.8f,result,"接受任务",1000);

                        result=mFairy.findPic("finish2.png");
                        mFairy.onTap(0.8f,result,"完成任务",1000);

                        result=mFairy.findPic("auto.png");
                        mFairy.onTap(0.8f,result,"开始自动作战",200);

                        result=mFairy.findPic("auto ing.png");
                        int picCount=picCount(0.8f,result,"auto ing");
                        if(picCount(0.8f,result,"auto ing")%4==0)LtLog.e(mFairy.getLineInfo("自动战斗中"));


                        result=mFairy.findPic(941,13,1270,213,"close3.png");
                        mFairy.onTap(0.8f,result,"野外挂机 content4 关闭",1000);

                        result=mFairy.findPic(640,81,747,112,"word hint enough space.png");
                        mFairy.onTap(0.8f,result,1126,572,1134,581,"没有足够的空间",1000);

                        result=mFairy.findPic(new String[]{"list.png","list1.png"});
                        if(result.sim<0.75f){
                            mFairy.onTap(1240,675,1245,680,"timing_熄屏点击",500);
                        }

                        if(place!=0){
                            //判断 是不是相对应的地图
                           /* try {
                                result=mFairy.findMultiColor(1243,12,1263,27,0.95f,"240,246,245",
                                        "1|0|245,252,251&2|0|244,249,249&3|0|243,246,246&4|0|240,243,244&5|0|241,244,245&7|0|249,249,248&6|1|252,252,252&5|1|253,253,252&4|1|253,253,252");
                            }catch (Exception e){
                                LtLog.e(mFairy.getLineInfo("获取出错"));
                                LtLog.e(mFairy.getLineInfo(e.getMessage()));
                            }*/
                            result = mFairy.findPic(831,3,1023,91,new String[]{"richang.png","richang2.png"});
                            if(result.sim>0.9f){
                                String placeName=map.get(place).split(":")[0];
                                //判断地图是否正确
                                if(place>=20&&place<=23){
                                    //云天界
                                    result=mFairy.findPic(1080,4,1166,31,"word place "+placeName+".png");
                                }else if(place>=65&&place<=66){
                                    LtLog.e("联盟地宫中。。。");
                                }else {
                                    result=mFairy.findPic(1086,4,1236,32,"word place "+placeName+".png");
                                }
                                result1=mFairy.findPic("pic shop.png");
                                if(result.sim<0.7f&&result1.sim<0.8f){
                                    setTaskName(0);
                                    LtLog.e(mFairy.getLineInfo(placeName+"="+result.toString()));
                                    LtLog.e(mFairy.getLineInfo("地图不正确，重新前往"));
                                    return;
                                }
                            }
                        }

                        //血刃悬赏
                        if(xrxs){
                            //背包已满
                            result=mFairy.findPic("word back already full.png");
                            if(result.sim>0.8){
                                LtLog.e(mFairy.getLineInfo("背包已满，清理后完成"));
                                int state;
                                if(clear){
                                    state =util.clear1();
                                }else{
                                    state =util.clear1();
                                }
                                if(state==1){
                                    setTaskName(0);
                                }
                                timekeepInit("clear");
                                return;
                            }

                            //寻找血刃悬赏 任务
                            result=mFairy.findPic("dui.png");
                            if(result.sim>0.8f){
                                result = mFairy.findPic(new String[]{"task lan.png","renwu.png"});
                                mFairy.onTap(0.75f,result,"显示任务l栏",Sleep);

                                result = mFairy.findPic("task bug1.png");
                                result1 = mFairy.findPic("lian bug.png");
                                if (result.sim >0.8f && result1.sim >0.8f){
                                    mFairy.onTap(0.75f,result,150,152,160,159,"任务栏卡bug点击见闻",Sleep);
                                }

                                result=mFairy.findPic(49,184,251,386,"word task xrxs.png");
                                if(result.sim>0.7f){
                                    result=mFairy.findPic(result.x-38,result.y+19,result.x+8,result.y+52,new String[]{"word answer.png","word every day.png"});
                                    mFairy.onTap(0.7f,result,"回复神帝，接取任务",2000);
                                }
                            }

                            result=mFairy.findPic(47,357,166,393,"word task xrxs.png");
                            if(result.sim>0.8f){
                                LtLog.e(mFairy.getLineInfo("血刃悬赏 上滑"));
                                mFairy.ranSwipe(125,384,136,200, 1000, 1500);
                            }
                        }
                        limitless();
                    }
                }
                long dazeTime = mFairy.mMatTime(1188,153,81,18, 0.9f);
                LtLog.e(mFairy.getLineInfo("发呆时间=" + dazeTime));
                if (dazeTime > 30) {
                    mFairy.initMatTime();
                    for (int i = 0; i < 20; i++) {
                        mFairy.condit();
                        if (i == 19) {
                            LtLog.e(mFairy.getLineInfo("发呆30秒了重置"));
                            setTaskName(3);
                            return;
                        }
                        Thread.sleep(1000);
                    }
                }
                if (back != null && back.choice == 1 && timekeep(0, back.timeMillis, "定位坐标")) {
                    LtLog.e(mFairy.getLineInfo("定位坐标一次"));
                    setTaskName(3);//每隔多少分定位坐标一次
                    return;
                }
            }

            //爬楼层
            public void go(int direction,String placeName,int currentFloor)throws Exception{
                //云天界单独处理
                if(placeName.equals("ytj")&&currentFloor==1){
                    int goalFloor =Integer.parseInt(map.get(place).split(":")[1]);
                    result=mFairy.findPic("world map.png");
                    if(result.sim>0.8f){
                        result=mFairy.findPic("pic open guide.png");
                        mFairy.onTap(0.8f,result,"打开传送指引",1000);

                        result=mFairy.findPic("word chuansong interface.png");
                        if(result.sim>0.9f){
                            result=mFairy.findPic(301,48,352,371,"word direction "+goalFloor+".png");
                            mFairy.onTap(0.8f,result,"前往"+placeName+goalFloor+"层",1000);
                        }
                    }
                    return;
                }

                int toFloor=currentFloor+(direction==1?+1:-1);
                result=mFairy.findPic("world map.png");
                if(result.sim>0.8f){
                    result=mFairy.findPic("pic open guide.png");
                    mFairy.onTap(0.8f,result,"打开传送指引",1000);

                    result=mFairy.findPic("word chuansong interface.png");
                    if(result.sim>0.9f){
                        //修罗圣墟 火炤试炼 无相神殿 位置不同 单独处理
                        if((place>=40&&place<=49)||(place>=54&&place<=59)){
                            result=mFairy.findPic(312,49,360,207,"word direction "+toFloor+".png");
                        }else if(place==65||place==66){
                            result=mFairy.findPic(332,50,394,285,"word direction "+toFloor+".png");
                        }else{
                            result=mFairy.findPic(298,49,350,207,"word direction "+toFloor+".png");
                        }

                        if(result.sim>0.8f){
                            mFairy.onTap(0.8f,result,"前往"+placeName+toFloor+"层",1000);
                        }else{
                            mFairy.onTap(247,156,264,178,"没找到"+placeName+toFloor+"层，默认点击",1000);
                        }
                    }
                }
            }
        }.taskContent(mFairy,"野外挂机");
    }

    //判断有没到活动时间点
    public void limitless()throws Exception{
        //限时任务
        int hour=mFairy.dateHour();
        int minute= mFairy.dateMinute();
LtLog.e("查看限时任务！");
        //限时首领
        if(limitBoss){
            //19:20-19:30开启活动
            if(hour==19&&(minute>=20&&minute<=30)){
                timingActivity.timing_xssl();
                limitBoss=false;
                setTaskName(0);
                return;
            }
        }

        //家族答题
        if(jzdt){
            //19:35-19:45开启活动
            if(hour==19&&(minute>=35&&minute<=45)){
                timingActivity.timing_jzdt();
                jzdt=false;
                if(!jzhj){
                    setTaskName(0);
                }
                return;
            }
        }

        //家族喝酒
        if(jzhj){
            //19:30-19:50开启活动
            if(hour==19&&(minute>=30&&minute<=59)){
                timingActivity.timing_jzhj();
                jzhj=false;
                setTaskName(0);
                return;
            }
        }

        //家族喝酒
        if(yzty){
            //19:00-4:00开启活动
            if(hour>=19||hour<4){
                timingActivity.timing_yzty();
                yzty=false;
                setTaskName(0);
                return;
            }
        }

        //骁骑争锋
        if(xqzf){
            //12:00-12:15 18:00-18:15 20:30-20:45
            if((hour==12&&minute>=0&&minute<=15)||(hour==18&&minute>=0&&minute<=15)||(hour==20&&minute>=30&&minute<=45)){
                timingActivity.timing_xqzf();
                xqzf=false;
                setTaskName(0);
                return;
            }
        }

        //3v3排位赛
        if(match3){
            //10:00-23:50
            if(hour>=10&&hour<=23){
                timingActivity.timing_3v3();
                match3=false;
                setTaskName(0);
                return;
            }
        }

        //剑指王者
        if(jzwz){
            //9:00-23:50
            if(hour>=9&&hour<=23){
                timingActivity.timing_jzwz();
                jzwz=false;
                setTaskName(0);
                return;
            }
        }

        //无穷斗界
        if(wuqiong){
            //0:00-4:00 6:00-23:59
            if((hour>=0&&hour<4)||(hour>=6&&hour<=23)){
                timingActivity.timint_wqdj();
                wuqiong=false;
                setTaskName(0);
                return;
            }
        }

        //七星海
        if(qxh){
            //12:15-12:45 18:15-18:45 20:45-21:15
            if((hour==12&&minute>=15&&minute<=45)||(hour==18&&minute>=15&&minute<=45)||((hour==20&&minute>=45)||(hour==21&&minute<=15))){
                timingActivity.timint_qxh1();
                qxh=false;
                setTaskName(0);
                return;
            }
        }

        //真灵演武
        if(zlyw){
            //10:00-23:59
            if(hour>=10&&hour<=23){
                timingActivity.timing_zlyw();
                zlyw=false;
                setTaskName(0);
                return;
            }
        }

        //古界争夺战
        if(gjzdz){
            //15:00-23:30
            if(hour>=15&&hour<=23){
                timingActivity.timint_gjzdz();
                gjzdz=false;
                setTaskName(0);
                return;
            }
        }

        //天愚赐福
        if(tycf1){
            if(hour==11||hour==17)tycf=true;
            if(tycf){
                if((hour>=12&&hour<=13)||(hour>=18&&hour<=19)){
                    timingActivity.timing_tycf();
                    tycf=false;
                    return;
                }
            }
        }

        //召唤宠物
        if(call){
            boolean timekeep=timekeep(1,6*60*1000,"call");
            if(timekeep){
                timingActivity.hang_call();
                timekeepInit("call");
                return;
            }
        }

        //清理背包
        if(clear){
            boolean timekeep=timekeep(1,clearInter,"clear");
            if(timekeep){
                int state=util.clear1();
                if(state==1){
                    setTaskName(0);
                }
                timekeepInit("clear");
                return;
            }
        }

        //队长拉跟战
        if(follow){
            boolean timekeep=timekeep(1,followInter,"follow");
            if(timekeep){
                timingActivity.timing_follow();
                timekeepInit("follow");
                return;
            }
        }

        //每日远征
        if(expedition){
            boolean timekeep=timekeep(1,expeditionInter,"expedition");
            if(timekeep){
                timingActivity.hang_expedition();
                timekeepInit("expedition");
                return;
            }
        }

        //技能升级
        if(skill){
            boolean timekeep=timekeep(1,skillInter,"skill");
            if(timekeep){
                timingActivity.timing_skill();
                timekeepInit("skill");
                return;
            }
        }

        //装备强化
        if(equip){
            boolean timekeep=timekeep(1,equipInter,"equip");
            if(timekeep){
                timingActivity.timing_equip();
                timekeepInit("equip");
                return;
            }
        }

        //异闻
        if(yiwen){
            boolean timekeep=timekeep(1,yiwenInter,"yiwen");
            if(timekeep){
                timingActivity.timing_yiwen();
                timekeepInit("yiwen");
                setTaskName(0);
                return;
            }
        }

        //15分钟回一次原点
        if(timekeep(0,15*60*1000,"back")){
            openMap=true;
        }
    }

    //接取血刃悬赏 任务
    public void limitLess_xrxs() throws Exception{
        new SingleTask(mFairy) {
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

                result=mFairy.findPic(49,184,251,386,new String[]{"word task xrxs.png","xueren1.png"});
                if (result.sim>0.7f){
                    LtLog.e(mFairy.getLineInfo("找到任务"));
                    setTaskName(3);
                    return;
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
                int ret =util.mission("single xuerenxuanshang.png",0);
                if (ret==1){
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

                result=mFairy.findPic("family t a.png");
                mFairy.onTap(0.8f,result,1027,148,1034,158,"关闭家族传送塔",1000);
                //关闭日常界面
                result=mFairy.findPic("word dailyInterface.png");
                if(result.sim>0.8f){
                    result1=mFairy.findPic("accept1.png");
                    if(result1.sim>0.8f){
                        mFairy.onTap(0.8f,result1,"接受任务",1000);
                    }else{
                        mFairy.onTap(0.8f,result,1219,68,1236,87,"关闭日常界面",1000);
                    }
                }

                result=mFairy.findPic(49,184,251,386,new String[]{"word task xrxs.png","xueren1.png"});
                if(result.sim<0.8f){
                    LtLog.e(mFairy.getLineInfo("血刃悬赏 上滑"));
                    mFairy.ranSwipe(125,384,136,200, 1000, 1500);
                }

                result=mFairy.findPic("dui.png");
                if(result.sim>0.8f){
                    //寻找血刃悬赏 任务
                    result=mFairy.findPic(43,175,267,398,new String[]{"word task xrxs.png","xueren1.png"});
                    if(result.sim>0.7f){
                        result=mFairy.findPic(result.x-38,result.y+19,result.x+8,result.y+52,new String[]{"word every day.png"});
                        mFairy.onTap(0.7f,result,"接取任务",2000);
                    }else{
                        LtLog.e(mFairy.getLineInfo("血刃悬赏寻找任务出错，重新启动任务"));
                        setTaskName(0);
                        return;
                    }
                }

                result=mFairy.findPic("accept1.png");
                mFairy.onTap(0.8f,result,"接受任务",1000);

                result=mFairy.findPic("finish2.png");
                mFairy.onTap(0.8f,result,"完成任务",1000);

                result=mFairy.findPic(49,184,251,386,new String[]{"word task xrxs.png","xueren1.png"});
                if (result.sim>0.7f){
                    result=mFairy.findPic(result.x-38,result.y+19,result.x+8,result.y+52,new String[]{"word struck down.png","word answer.png"});
                    if(result.sim>0.8f){
                        xrxs=true;
                        setTaskEnd();
                        return;
                    }
                }
            }

            @Override
            public void inOperation() throws Exception {
                result=mFairy.findPic(920,460,1027,499,"word use.png");
                mFairy.onTap(0.8f,result,"自动使用",1000);

                result=mFairy.findPic(605,395,645,470,"affirm.png");
                mFairy.onTap(0.8f,result,"点击确认",1000);
                super.inOperation();
            }
        }.taskContent(mFairy,"血刃悬赏");
    }

    @Override
    public void inOperation() throws Exception {
        super.inOperation();
        result=mFairy.findPic("pic open task.png");
        mFairy.onTap(0.8f,result,"打开任务列表页",1000);

        result=mFairy.findPic("task list1.png");
        mFairy.onTap(0.8f,result,"切换到任务列表",1000);
/*
        try{
            result=mFairy.findMultiColor(57,143,85,175,0.9f,"88,214,220",
                    "0|4|86,194,196&0|8|88,196,197&0|13|86,190,190&4|-1|89,209,212&8|-1|91,228,235&12|-1|87,181,180&8|2|84,194,197&8|9|87,197,199&6|13|83,174,172");
            mFairy.onTap(0.85f,result,"切换到任务页",1000);
        }catch (Exception e){
            LtLog.e(mFairy.getLineInfo("获取出错"));
            LtLog.e(mFairy.getLineInfo(e.getMessage()));
        }*/

        result=mFairy.findPic("word hint kuafu.png");
        if(result.sim>0.8f){
            mFairy.onTap(628,414,635,422,"跨服状态，回城",1000);
            util.back_city();
            return;
        }

        result=mFairy.findPic(470,53,565,143,"word hint kuafu1.png");
        if(result.sim>0.8f){
            //点击日常/远征/技能/天愚赐福/派遣按钮/竞技场 界面发现跨服，点击回城
            result1=mFairy.findPic(new String[]{"word dailyInterface.png","word finish yuanzheng.png","word skill interface.png","pic tycf interface.png","dispatch.png","word jing.png"});
            if(result1.sim>0.8f){
                LtLog.e(mFairy.getLineInfo("跨服状态，无法执行，先回城"));
                util.back_city();
                return;
            }
        }

        //原地复活需要2w绑银
        result=mFairy.findPic("fuhuo.png");
        int picCount=picCount(0.8f,result,"rebuild");
        if(result.sim>0.8){
            LtLog.e("被野怪或其他玩家打死，选择复活方式后，继续任务");

            result=mFairy.findPic("word hint bangyin.png");
            if(result.sim>0.8f){
                LtLog.e(mFairy.getLineInfo("绑银不足，复活点复活"));
                mFairy.onTap(0.8f,result,531,445,546,453,"取消获取绑银",1000);
                mFairy.onTap(0.8f,result,489,351,504,365,"复活点复活",500);
                init=true;
                setTaskName(0);
                return;
            }
            if(rebuild){
                mFairy.onTap(768,359,790,380,"原地复活",500);
            }else{
                mFairy.onTap(489,351,504,365,"复活点复活",500);
                init=true;
                if(picCount==1){
                    rebuildCount++;
                    int size=rebuildList.size();
                    if(size!=0){
                        int rebuild;
                        if(rebuildCount<size){
                            rebuild=rebuildCount;
                        }else{
                            rebuild=rebuildCount%size;
                        }
                        LtLog.e(mFairy.getLineInfo("切换到复活方式"+(rebuild+1)));
                        rebuild=rebuildList.get(rebuild);
                        tower=rebuildMap.get("tower"+rebuild);
                        place=rebuildMap.get("place"+rebuild);
                        int x=rebuildMap.get("coord_x"+rebuild);
                        int y=rebuildMap.get("coord_y"+rebuild);
                        initCoord(x,y);//初始化坐标
                    }else{
                        tower=0;
                    }
                }
                setTaskName(0);
            }
            return;
        }

        result=mFairy.findPic(700,507,794,553,"word buy.png");
        mFairy.onTap(0.8f,result,"点击购买",1000);

        result=mFairy.findPic(600,360,680,470,new String[]{"affirm.png","affirm1.png"});
        mFairy.onTap(0.8f,result,"点击确认",500);

        result=mFairy.findPic(660,303,790,346,"clear.png");
        if(result.sim>0.8){
            result=mFairy.findPic(510,394,578,440,"cancel.png");
            mFairy.onTap(0.8f,result,"确定",1000);
            if(false){
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
            if(false){
                int state=timingActivity.hang_clear();
                if(state==1)setTaskName(0);
                timekeepInit("clear");
            }
            return;
        }

        result=mFairy.findPic(362,240,477,335,"pic hint.png");
        if(result.sim>0.8f){
            //不点击
            result=mFairy.findPic(new String[]{"xiaducheng.png","word go.png"});
            result1=mFairy.findPic(545,295,649,345,"qianwang.png");
            if(result.sim>0.8f||result1.sim>0.8f)return;

            //确定:
            result=mFairy.findPic(new String[]{"word 0yuanbao.png","word hint rebuild.png","word hint chuansong.png","word hint genzhan.png"});
            if(result.sim>0.8f){
                result=mFairy.findPic(696,345,759,480,"affirm.png");
                mFairy.onTap(0.8f,result,"确定",1000);
                return;
            }

            //取消
            result=mFairy.findPic(500,345,603,480,"cancel.png");
            mFairy.onTap(0.8f,result,"取消",1000);

            result1=mFairy.findPic("fu_refuse.png");
            mFairy.onTap(0.8f,result1,"拒绝",500);

            if(result.sim >0.8f && result1.sim<0.8f){
                mFairy.onTap(537,435,543,440,"取消",1000);
            }
        }

        result=mFairy.findPic(908,452,1109,536,"auto wear.png");
        mFairy.onTap(0.8f,result,"捡到装备",500);
    }
}
