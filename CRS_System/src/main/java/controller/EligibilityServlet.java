package controller;

import dao.EligibilityDAO;
import model.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/checkEligibility")
public class EligibilityServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EligibilityDAO dao = new EligibilityDAO();

        List<Student> eligibleList = dao.getEligibleStudents();
        List<Student> ineligibleList = dao.getIneligibleStudents();

        System.out.println("Eligible students count: " + eligibleList.size());
        System.out.println("Ineligible students count: " + ineligibleList.size());
        System.out.println("NEW VERSION1.0 RUNNING");
        
        request.setAttribute("eligibleStudents", eligibleList);
        request.setAttribute("students", ineligibleList);

        RequestDispatcher rd = request.getRequestDispatcher("eligibility.jsp");
        rd.forward(request, response);
    }
}