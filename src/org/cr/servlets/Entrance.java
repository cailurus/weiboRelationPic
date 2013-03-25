package org.cr.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;

/**
 * Servlet implementation class Entrance
 */
@WebServlet("/Entrance")
public class Entrance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Entrance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String redurl = null;
		try {
			redurl = new Oauth().authorize("code");
		
			//build cookie
			Map<String, String> cookies = new HashMap<String, String>();
			cookies.put("SUE", "es%3D004db0f1171abd8fdde0ceefe259599b%26ev%3Dv1%26es2%3Da847d36f78ce1331dbc7953c1a8ef5fc%26rs0%3D2yKRq%252BuJztKQ4QrQWD701iHI%252BcFY%252BHfiNMiOAcznIp3Z2rET%252FpQV7%252FhY4zzKonKy8Hdj%252FU39xBYqFOlvL2auk2G1mI7B%252BPuOboOpYH4LL71OpuNc%252F%252FdJ0c98J9iILEsrFbyCSKpubZIyxMqUYKeG%252BTfmv0XZ2iYG6%252F1BCPIDn5w%253D%26rv%3D0");
			cookies.put("SUP", "cv%3D1%26bt%3D1364203550%26et%3D1364205350%26d%3Dc909%26i%3D957b%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D1057297283%26user%3D19910101cr%26ag%3D2%26name%3D19910101cr%2540sina.com%26nick%3D19910101cr%26fmp%3D%26lcp%3D2012-01-01%252021%253A36%253A15");
			cookies.put("SUS", "SID-1057297283-1364203550-XD-tn908-74f781d41f954b78b3b173d7217c88a7");

			// cookies.put("", "");

			Document doc = Jsoup.connect(redurl).cookies(cookies).timeout(3000)
					.post();
			// String title = doc.title();
			String baseuri = doc.baseUri();
			// 从定向至sina auth确认
			String code = baseuri.split("=")[1];
			System.out.println("\n code=" + code);
			// response.sendRedirect(redurl);
			request.getSession().setAttribute("code", code);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().println("Error occured, see logs:\n"+e.getError());
		}
		request.getContextPath();
		// 转去 输入uid界面
		response.sendRedirect(request.getContextPath()+"/RelationPath");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
