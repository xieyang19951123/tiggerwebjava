package io.renren.modules.tigger.controller;

import io.renren.modules.tigger.service.OrganStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tigger/organstudent")
public class OrganStudentController {

    @Autowired
    private OrganStudentService organStudentService;
}
