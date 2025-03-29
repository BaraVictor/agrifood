package com.italy.agrifood.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

  @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
  public String handleError(HttpServletRequest request, Model model) {
    Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (statusCode != null) {
      int status = Integer.parseInt(statusCode.toString());
      model.addAttribute("status", status);

      if (status == HttpStatus.FORBIDDEN.value()) {
        model.addAttribute("error", "403 - Forbidden");
        model.addAttribute("message", "You don’t have permission to access this page.");
      } else if (status == HttpStatus.NOT_FOUND.value()) {
        model.addAttribute("error", "404 - Not Found");
        model.addAttribute("message", "The page you’re looking for doesn’t exist.");
      } else if (status == HttpStatus.METHOD_NOT_ALLOWED.value()) {
        model.addAttribute("error", "405 - Method Not Allowed");
        model.addAttribute("message", "The request method is not supported for this resource.");
      } else {
        model.addAttribute("error", "Unexpected Error");
        model.addAttribute("message", "Something went wrong. Please try again later.");
      }
    }
    return "error"; // Custom error page
  }
}
