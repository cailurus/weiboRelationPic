package org.cr.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.cr.bean.Graphdata;
import org.cr.bean.Links;
import org.cr.bean.Node;
import org.cr.bean.RelationPathBean;

import com.alibaba.fastjson.JSON;



import weibo4j.Friendships;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.Paging;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

/**
 * Servlet implementation class RelationPath
 */
@WebServlet("/RelationPath")
public class RelationPath extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelationPath() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().print("wa haha \n code = "+request.getSession().getAttribute("code"));
		
		//当然也可以把uid写在界面里，这里写死！！！
		String printedUid = "123";
		String code = (String) request.getSession().getAttribute("code");
		
		printedUid = request.getParameter("push");
//		System.out.println(printedUid);
		
		//begin 抓取 
		Oauth oauth = new Oauth();
		AccessToken accessToken = null;
		try {
			accessToken = oauth.getAccessTokenByCode(code);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 获取此uid centerUser信息
		Users users = new Users();
		users.client.setToken(accessToken.getAccessToken());
		User cenUser = null;
		try {
			cenUser = users.showUserById(printedUid);
		} catch (WeiboException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Friendships friendships = new Friendships();
		friendships.client.setToken(accessToken.getAccessToken());
		
		UserWapper userWapper = null;
		try {																	//超过150 debug暂停 或者该程序 没执行一次 sleep xxx s
			userWapper = friendships.getFriendsBilateral(printedUid, 0,new Paging(1, 150));
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		
		//第一层的users
		List<User>lv1Users = userWapper.getUsers();
		HashMap<User, ArrayList<User>> lv2BeanMap = new HashMap<User, ArrayList<User>>();
		lv1UserBeans = lv1Users;
		
		//根据第一层的 互粉 list 获得第二层
		for (User u1 : lv1Users) {
			userWapper = null;
			try {
				//获取第二层的时候 获取所有
				userWapper = friendships.getFriendsBilateral(u1.getId(), 0,new Paging(1, 200));
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			List<User>lv2Users = userWapper.getUsers();
			
			//仅获取lv2User中在lv1UserBeans中存在的user
			ArrayList<User> currentU1Children = new ArrayList<User>();
			for(User u2 : lv2Users){
				if(isUserExist(u2)){
					//获取要修改的节点
					User currentUser = getChildFromList(lv1UserBeans,u2);
					currentU1Children.add(currentUser);
				}	
			}
			
			lv2BeanMap.put(u1, currentU1Children);
		}
		
		//过滤掉重复的线段
		for(User u : lv2BeanMap.keySet()){
			//如果新的map里没有重复线段则加之
			//对于没有child的线段 lists的size为0
			ArrayList<User> tmplists = new ArrayList<User>();
			for (User eachU : lv2BeanMap.get(u)) {
				if (!islv2SingleBeanMapExist(u,eachU)) {
					tmplists.add(eachU);
				}
			}
			lv2SingleBeanMap.put(u, tmplists);
		}
		
		// 组树形结构
		for(User u : lv1UserBeans){
			//有child的user，为了将2层child画在1层child的周围
			if (lv2SingleBeanMap.get(u).size() != 0) {
				relationlists.add(new RelationPathBean(printedUid,printedUid,u.getId(),1+""));
				System.out.println("first : " + "(" + cenUser.getName() + ","	+ u.getName() + ")");
				for (User u2 : lv2SingleBeanMap.get(u)) {
					relationlists.add(new RelationPathBean(printedUid,u.getId(),u2.getId(),2+""));
					System.out.println("second : " + "(" + u.getName() + ","	+ u2.getName() + ")");
				}
			} 
		}
		
		// 再画单独的 
		for (User u : lv1UserBeans) {
			if (lv2SingleBeanMap.get(u).size() == 0) {
				relationlists.add(new RelationPathBean(printedUid,printedUid,u.getId(),1+""));
				System.out.println("third : " + "(" + cenUser.getName() + ","+ u.getName() + ")");

			}
		}
		
		// 组一个uid，user的map 为后面使用   uid,User
		HashMap<String, User> globleUserMap = new HashMap<String, User>();
		// 加中点user
		globleUserMap.put(cenUser.getId(), cenUser);
		for(User u: lv1UserBeans){
//			if (globleUserMap.get(u.get))
			globleUserMap.put(u.getId(), u);
		}
		
		
		/**
		 * 
		 *  组前台数据部分
		 *  前台的结构： 
		 *  
"links": [{
    "source": "0",
    "target": "1",
    "value": "2"
  }, {
    "source": "2",
    "target": "3",
    "value": "1"
  }, {
    "source": "2",
    "target": "4",
    "value": "1"
  }, {
    "source": "5",
    "target": "6",
    "value": "2"
  }, {
    "source": "7",
    "target": "8",
    "value": "2"
  },
  
  ---------------------------------
  
   "nodes": [{
    "count": 5,
    "group": "2",
    "name": "尸体在线"
  }, {
    "count": 2,
    "group": "2",
    "name": "笑对天下1314"
  }, {
    "count": 15,
    "group": "1",
    "name": "00逝水无痕"
  }, {
    "count": 4,
    "group": "2",
    "name": "--aihisie"
  }, {
    "count": 4,
    "group": "2",
    "name": "手机用户2398208493"
  }
		 * 
		 * */
		
		//顺序映射   组 user 和 id Map (不重复的)
		HashMap<Integer, User> userMap = new HashMap<Integer, User>();
		//提出所有uid 放进map映射
		int point = 0;
		for(RelationPathBean r : relationlists){
			if(!isUserMapExist(r.getSourceuid(),userMap)){
				userMap.put(point, globleUserMap.get(r.getSourceuid()));
				point++;
			}
			if(!isUserMapExist(r.getTargetuid(),userMap)){
				userMap.put(point, globleUserMap.get(r.getTargetuid()));
				point++;
			} 
		}
		List<Node> nodes = new ArrayList<Node>();
		List<Links> links = new ArrayList<Links>();
		
		//组 nodes
		for (int i = 0; i < userMap.size(); i++) {
			User u = userMap.get(i);
			// if(u == null)continue;
			if (cenUser.getId().equals(u.getId())) {
				nodes.add(new Node(u.getName(), "1", 15));
			} else { // 并获取次 user 的 "影响力"
				nodes.add(new Node(u.getName(), "2", getAppearenceCount(
						u.getId(), relationlists)));
				// test
				System.out.println("\n" + u.getName());
				System.out.println(getAppearenceCount(u.getId(), relationlists));
			}
		}
		
		//组links
		for(RelationPathBean r3 : relationlists){
			links.add(new Links(getPositionByMap(r3.getSourceuid(),userMap),getPositionByMap(r3.getTargetuid(),userMap),r3.getDeep()));
		}
		
		String jsonString = JSON.toJSONString(new Graphdata(nodes, links));
		System.out.println(jsonString);
		response.setCharacterEncoding("utf-8");   
		response.setContentType("text/html; charset=utf-8");  
		response.getWriter().print(jsonString);
	}

	
	private static List<User> lv1UserBeans = new ArrayList<User>();
	private static HashMap<User, ArrayList<User>> lv2SingleBeanMap = new HashMap<User, ArrayList<User>>();
	//存放不重复的路径
	private List<RelationPathBean> relationlists = new ArrayList<RelationPathBean>();

	//由 uid 从map里获取String型 位置
	private String getPositionByMap(String uid,HashMap<Integer, User> map){
		for(int i=0;i<map.size();i++){
			try {
				if(map.get(i).getId().equals(uid)){
					return i+"";
				}
			} catch (Exception e) {
				return "0";
			}
		}
		return "0";
	}
	
	private static int getAppearenceCount(String uid,List<RelationPathBean> relists){
		int count = 0;
		for(RelationPathBean r3 : relists){
			if(uid.equals(r3.getSourceuid()) || uid.equals(r3.getTargetuid())){
				count++;
			}
		}
		return count;
	}
	
	// 查看map中是否重复
	private static boolean isUserMapExist(String uid,HashMap<Integer, User> userMap){
		for(Integer i : userMap.keySet()){
			//如果uid为null的话re false
			if(userMap.get(i) == null || userMap.get(i).getId() == null){
				User u = new User();
				u.setId(getRandomUid());
				u.setName("");
				userMap.put(i, u);
			}
			//已存在 re true
			if(userMap.get(i).getId().equals(uid)){
				return true;
			}
		}
		return false;
	}
	//得到10为不重复的uid
	private static String getRandomUid(){
		String uuid = RandomStringUtils.random(10, new char[]{'0','1','2','3','4','5','6','7','8','9'});
		return uuid;
	}
	
	private static boolean isUserExist(User u){
		for(User user : lv1UserBeans){
			if(user.getId().equals(u.getId())){
				return true;
			}
		}
		return false;
	}
	
	private static User getChildFromList(List<User> children,User u){
		for(User user : children){
			if(user.getId().equals(u.getId())){
				return user;
			}
		}
		return null;
	}
	
	private boolean islv2SingleBeanMapExist(User u1,User u2){
		if (lv2SingleBeanMap.get(u1) != null) {
			//是否存在 (A,B)
			for (User eachU : lv2SingleBeanMap.get(u1)) {
				if (u2.getId().equals(eachU.getId()))
					return true;
			}
		}
		if (lv2SingleBeanMap.get(u2) != null) {
			// 是否存在 (B,A)
			for (User eachU : lv2SingleBeanMap.get(u2)) {
				if (u1.getId().equals(eachU.getId()))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getOutputStream().print("jsp");
		request.getRequestDispatcher("relat.jsp").forward(request, response);
		
	}

}
