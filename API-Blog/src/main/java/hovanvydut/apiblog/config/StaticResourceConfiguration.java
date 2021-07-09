package hovanvydut.apiblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path resourceDir = Paths.get("./src/main/resources/");
        String resourcePath = resourceDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:" + resourcePath + "/");
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        registry.addResourceHandler("/assets/" + dirName + "/**").addResourceLocations("file:/" + uploadPath + "/");
    }
}
