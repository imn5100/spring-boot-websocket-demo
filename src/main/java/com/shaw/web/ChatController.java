//package com.shaw.web;
//
//import com.shaw.model.User;
//import com.shaw.websocket.assembly.ParticipantRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.annotation.SubscribeMapping;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.nio.file.AccessDeniedException;
//import java.util.Collection;
//
///**
// * Created by shaw on 2017/2/22 0022.
// */
//@Controller
//public class ChatController {
//    @Autowired
//    private ParticipantRepository participantRepository;
//
//    @RequestMapping(value = "/chat", method = RequestMethod.GET)
//    public String chatPage(HttpServletRequest request, Model model) throws AccessDeniedException {
//        if (request.getSession().getAttribute("user") == null) {
//            throw new AccessDeniedException("login please");
//        }
//        User user = (User) request.getSession().getAttribute("user");
//        model.addAttribute("username", user.getUsername());
//        return "chat";
//    }
//
//    @SubscribeMapping("/chat.participants")
//    public Collection<User> retrieveParticipants() {
//        return participantRepository.getActiveSessions().values();
//    }
//}
