package com.example.task2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.*;

@WebServlet(name = "ParseJsonServlet", value = "/parsejson.do")
@MultipartConfig(location="D:\\tools\\temp\\",fileSizeThreshold=1024)
public class ParseJsonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        获取文件内容
        Part p=request.getPart("json");
        String path="c:\\temp";
        File f=new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
        p.write(path+"\\javascore.json");
//        解析JSON
        String s="";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("c:\\temp\\javascore.json"), "utf-8"));
            while (reader.ready()) {
                String temp;
                temp=reader.readLine();  //读取一行
                s=s+temp;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        reader.close();
       s= s.replaceAll("\\s*", "");
        JSONArray objects= JSON.parseArray(s);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        out.println("<head>");
        out.println("<script type=\"text/javascript\">");
        out.println("function test1(){\n" +
                "    document.getElementById(\"flag\").value=\"1\"\n" +
                "   }\n");
        out.println("function test2(){\n" +
                "    document.getElementById(\"flag\").value=\"0\"\n" +
                "   }\n");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        //        输出表格
        out.println("<form action=\"excel.do\" method=\"post\">");
        out.println("<input type=\"hidden\" id=\"tableInfo\" name=\"tableInfo\" value="+s+">\n");
        out.println("<input type=\"hidden\" id=\"flag\" name=\"flag\" value=\"0\">\n");
        out.println("<table id=\"at\" align='center' border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
        out.println("<caption>学生成绩列表</caption>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>序号</th><th>学号</th><th>姓名</th><th>课程名</th><th>成绩</th>");
        out.println("</tr>");
        out.println("</thead>");
        for(int i=0;i<objects.size();i++){
            JSONObject object=(JSONObject) objects.get(i);
            String stuid=object.getString("stuid");
            String name=object.getString("name");
            String courseName=object.getString("courseName");
            String score=object.getString("score");
            out.println("<tr>");
            out.println("<td>"+(i+1)+"<input type=\"checkbox\" name=\"onepick\" value=\""+(i+1)+"\t"+stuid+"\t"+name+"\t"+courseName+"\t"+score+"\">"+"</td>");
            out.println("<td>"+stuid+"</td>");
            out.println("<td>"+name+"</td>");
            out.println("<td>"+courseName+"</td>");
            out.println("<td>"+score+"</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println(" <div  align=\"center\"><input type=\"submit\" value=\"导出全部\" onclick=\"test2()\"/></div>");
        out.println(" <div  align=\"center\"><input type=\"submit\" value=\"导出部分\" onclick=\"test1()\"/></div>");
        out.println("</form>");
        out.println("</body>");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);
    }
}
