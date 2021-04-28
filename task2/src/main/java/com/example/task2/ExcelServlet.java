package com.example.task2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ExcelServlet", value = "/excel.do")
public class ExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    输出excel表格
        response.setHeader("Content-Encoding", "gb2312");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + java.net.URLEncoder.encode("学生成绩.xls", "UTF-8"));
        response.setContentType("application/vnd.ms-excel;charset=gb2312");

        PrintWriter out=response.getWriter();
        request.setCharacterEncoding("UTF-8");
        String s=request.getParameter("tableInfo");
        JSONArray objects= JSON.parseArray(s);
        out.println("序号\t学号\t姓名\t课程名\t成绩");
        String flag=request.getParameter("flag");
        if(flag.equals("0")) {
            for (int i = 0; i < objects.size(); i++) {
                JSONObject object = (JSONObject) objects.get(i);
                String stuid = object.getString("stuid");
                String name = object.getString("name");
                String courseName = object.getString("courseName");
                String score = object.getString("score");
                out.print((i + 1) + "\t");
                out.print(stuid + "\t");
                out.print(name + "\t");
                out.print(courseName + "\t");
                out.println(score);
            }
        }else{
            String []t=request.getParameterValues("onepick");
            for(int i=0;i<t.length;i++){
                out.println(t[i]);
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);
    }
}
