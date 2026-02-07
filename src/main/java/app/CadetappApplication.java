package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cloudinary.Cloudinary;

import app.config.CloudinaryConfig;

@SpringBootApplication
@EnableScheduling
public class CadetappApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CadetappApplication.class, args);
    }

}