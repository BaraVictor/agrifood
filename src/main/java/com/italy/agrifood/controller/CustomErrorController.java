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

      switch (status) {
        case 400:
          model.addAttribute("error", "400 - Bad Request");
          model.addAttribute("message", "Your request contains incorrect syntax or cannot be fulfilled.");
          break;
        case 401:
          model.addAttribute("error", "401 - Unauthorized");
          model.addAttribute("message", "You need to log in to access this resource.");
          break;
        case 403:
          model.addAttribute("error", "403 - Forbidden");
          model.addAttribute("message", "You don’t have permission to access this page.");
          break;
        case 404:
          model.addAttribute("error", "404 - Not Found");
          model.addAttribute("message", "The page you’re looking for doesn’t exist.");
          break;
        case 405:
          model.addAttribute("error", "405 - Method Not Allowed");
          model.addAttribute("message", "The request method is not supported for this resource.");
          break;
        case 408:
          model.addAttribute("error", "408 - Request Timeout");
          model.addAttribute("message", "Your request took too long to process. Please try again.");
          break;
        case 500:
          model.addAttribute("error", "500 - Internal Server Error");
          model.addAttribute("message", "An unexpected error occurred on the server.");
          break;
        case 502:
          model.addAttribute("error", "502 - Bad Gateway");
          model.addAttribute("message", "The server received an invalid response from the upstream server.");
          break;
        case 503:
          model.addAttribute("error", "503 - Service Unavailable");
          model.addAttribute("message", "The service is temporarily unavailable. Please try again later.");
          break;
        case 504:
          model.addAttribute("error", "504 - Gateway Timeout");
          model.addAttribute("message", "The server is taking too long to respond. Try again later.");
          break;
        default:
          model.addAttribute("error", "Unexpected Error");
          model.addAttribute("message", "Something went wrong. Please try again later.");
          break;
      }
    }
    return "error"; // Custom error page
  }
}