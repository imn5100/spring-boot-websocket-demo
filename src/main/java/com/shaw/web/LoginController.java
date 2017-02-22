//package com.shaw.web;
//
//import com.shaw.model.User;
//import com.shaw.websocket.assembly.ParticipantRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
///**
// * Created by shaw on 2017/2/14 0014.
// */
//@Controller
//@EnableAutoConfiguration
//public class LoginController {
//    @RequestMapping(value = "/")
//    public String index() throws ServletException {
////        user.setTime(new Date());
////        httpRequest.getSession().setAttribute("user", user);
////        messagingTemplate.convertAndSend(LOGIN, user);
////        if (participantRepository.getActiveSessions().containsKey(httpRequest.getSession().getId())) {
////            messagingTemplate.convertAndSend(LOGOUT, participantRepository.getActiveSessions().get(httpRequest.getSession().getId()));
////        }
////        participantRepository.add(httpRequest.getSession().getId(), user);
//        return "index";
//    }
//}
//
