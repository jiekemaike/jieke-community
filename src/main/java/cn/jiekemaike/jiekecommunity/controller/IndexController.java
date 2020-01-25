package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.PaginationDTO;
import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.mapper.QuestionMapper;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "2")Integer size){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null)
            for (Cookie cookie : cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user!=null)
                        request.getSession().setAttribute("user",user);
                    break;
                }
            }
        PaginationDTO paginationDTO = questionService.listPage(page, size);
        model.addAttribute("pages",paginationDTO);
        return "index";
    }

}
