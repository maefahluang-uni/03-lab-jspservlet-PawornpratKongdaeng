package th.mfu;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//TODO: add webservlet to "/calbmi"
public class BMICalculatorServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters from the request: "weight" and "height"
        String weightParam = request.getParameter("weight");
        String heightParam = request.getParameter("height");

        // Check if either parameter is missing or empty
        if (weightParam == null || weightParam.isEmpty() || heightParam == null || heightParam.isEmpty()) {
            // You may want to handle the error here, e.g., by displaying a message or redirecting to an error page.
            response.getWriter().println("Please provide valid weight and height parameters.");
            return;
        }

        try {
            // Convert parameters to numerical values
            double weight = Double.parseDouble(weightParam);
            double height = Double.parseDouble(heightParam);

            // Calculate BMI
            double bmi = calculateBMI(weight, height);

            // Determine the body type from BMI (you can customize this logic)
            String bodyType = determineBodyType(bmi);

            // Add BMI and body type to the request's attributes
            request.setAttribute("bmi", bmi);
            request.setAttribute("bodyType", bodyType);

            // Forward to a JSP for rendering
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Handle invalid input format
            response.getWriter().println("Invalid weight or height format. Please provide valid numerical values.");
        }
    }

    private double calculateBMI(double weight, double height) {
        // Calculate BMI (simplified formula)
        return weight / (height * height);
    }

    private String determineBodyType(double bmi) {
        // Customize this logic based on your criteria
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Normal Weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}