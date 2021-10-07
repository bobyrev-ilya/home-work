package com.sbrf.reboot.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(name = "main", value = "/hello")
public class MainServlet extends HttpServlet {
    private static final String NAME_PATH = "name";
    private AtomicInteger countVisit = new AtomicInteger(0);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter(NAME_PATH);

        if (!Objects.isNull(name)) {
            name = name.isEmpty() ? "Anon" : req.getParameter(NAME_PATH);
            countVisit.getAndAdd(1);
            printResponse(resp, String.format("Привет, %s. Количество посещений страницы: %d", name, countVisit.get()));
        } else resp.sendError(404, "Invalid params");
    }

    private void printResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(message);
        printWriter.close();
    }
}
