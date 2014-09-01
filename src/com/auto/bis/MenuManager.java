package com.auto.bis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auto.pojo.AccessToken;
import com.auto.pojo.Button;
import com.auto.pojo.CommonButton;
import com.auto.pojo.ComplexButton;
import com.auto.pojo.Menu;
import com.auto.pojo.ViewButton;
import com.auto.util.WeixinUtil;

public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
	//本地执行menu修改命令
    public static void main(String[] args) {  
        // 第三方用户唯一凭证  
        String appId = "wx1deac355c85a2e85";  
        // 第三方用户唯一凭证密钥  
        String appSecret = "59c0f9a6dd469d8bc6f8ff0a950f9238";  
  
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
  
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("菜单创建成功！");  
            else  
                log.info("菜单创建失败，错误码：" + result);  
        }  
    }  
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
//        CommonButton btn11 = new CommonButton();  
//        btn11.setName("天气预报");  
//        btn11.setType("click");  
//        btn11.setKey("11");  
  
    	ViewButton btn11 = new ViewButton();  
        btn11.setName("品牌历史");  
        btn11.setType("view");  
        btn11.setUrl("http://yyyyf.sinaapp.com/about4wx.html");  
  
//        CommonButton btn12 = new CommonButton();  
//        btn12.setName("最新优惠");  
//        btn12.setType("click");  
//        btn12.setKey("12"); 
//        
        ViewButton btn12 = new ViewButton();  
        btn12.setName("当月推荐");  
        btn12.setType("view");  
        btn12.setUrl("http://wd.koudai.com/item.html?itemID=21169354&p=-1"); 
  
//        CommonButton btn14 = new CommonButton();  
//        btn14.setName("自助客服");  
//        btn14.setType("click");  
//        btn14.setKey("14");  
          
        ViewButton btn21 = new ViewButton();  
        btn21.setName("申请推广码");  
        btn21.setType("view");  
        btn21.setUrl("http://yyyyf.sinaapp.com/agent_apply.html");
  
        ViewButton btn22 = new ViewButton();  
        btn22.setName("查询推广码");  
        btn22.setType("view");  
        btn22.setUrl("http://yyyyf.sinaapp.com/agent_query.html");  
        
  
//        CommonButton btn22 = new CommonButton();  
//        btn22.setName("往期精华");  
//        btn22.setType("click");  
//        btn22.setKey("22");  
  
//        CommonButton btn23 = new CommonButton();  
//        btn23.setName("美女电台");  
//        btn23.setType("click");  
//        btn23.setKey("23");  
//  
//        CommonButton btn24 = new CommonButton();  
//        btn24.setName("人脸识别");  
//        btn24.setType("click");  
//        btn24.setKey("24");  
//  
//        CommonButton btn25 = new CommonButton();  
//        btn25.setName("聊天唠嗑");  
//        btn25.setType("click");  
//        btn25.setKey("25");  
//  
//        CommonButton btn31 = new CommonButton();  
//        btn31.setName("Q友圈");  
//        btn31.setType("click");  
//        btn31.setKey("31");  
//  
//        CommonButton btn33 = new CommonButton();  
//        btn33.setName("幽默笑话");  
//        btn33.setType("click");  
//        btn33.setKey("33");  
//          
//        CommonButton btn34 = new CommonButton();  
//        btn34.setName("用户反馈");  
//        btn34.setType("click");  
//        btn34.setKey("34");  
//          
//        CommonButton btn35 = new CommonButton();  
//        btn35.setName("关于我们");  
//        btn35.setType("click");  
//        btn35.setKey("35");  
          
//        ViewButton btn32 = new ViewButton();  
//        btn32.setName("使用帮助");  
//        btn32.setType("view");  
//        btn32.setUrl("http://www.idcvisa.com/help/");  
  
//        ComplexButton mainBtn1 = new ComplexButton();  
//        mainBtn1.setName("微商城");  
//        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15 });  
        ViewButton btn1 = new ViewButton();  
        btn1.setName("微商城");  
        btn1.setType("view");  
        btn1.setUrl("http://wd.koudai.com/?userid=692450"); 
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("新闻中心");  
        mainBtn2.setSub_button(new Button[] { btn11, btn12 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("我要赚钱");  
        mainBtn3.setSub_button(new Button[] { btn21, btn22 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { btn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}
