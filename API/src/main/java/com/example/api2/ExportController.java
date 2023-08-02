package com.example.api2;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private UserRepo userRepository;

    @GetMapping("/users")
    @ResponseBody
    public void exportUsers(HttpServletResponse response) throws IOException {
        List<User> userList = (List<User>) userRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");

        CSVWriter csvWriter = new CSVWriter(response.getWriter());
        csvWriter.writeNext(new String[]{"ID", "CardNumber", "StaticCvv","SessionNumber","LocationLat","LocationLon","Fingerprint"});

        for (User user : userList) {
            csvWriter.writeNext(new String[]{String.valueOf(user.getId()), user.getCardNumber(), String.valueOf(user.getStaticCvv()), String.valueOf(user.getSessionNumber()),user.getLocationLat(),user.getLocationLon(),String.valueOf(1)});
        }

        csvWriter.close();
    }
}
