package com.lpth.webLaptop.config;

// Annotation đánh dấu class cấu hình Spring
import org.springframework.context.annotation.Configuration;

// Dùng để cấu hình Spring MVC (resource, view, interceptor, ...)
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig
 * - Cấu hình cách Spring MVC phục vụ tài nguyên tĩnh (static resources)
 * - Dùng cho ảnh upload, css, js, banner, ...
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Thư mục lưu ảnh sản phẩm (đường dẫn vật lý trên máy)
     * "file:" cho biết đây là đường dẫn ngoài classpath
     */
    private static final String UPLOAD_DIR =
            "file:src/main/resources/static/images/maytinh/";

    /**
     * Thư mục lưu ảnh slide / banner upload
     */
    private static final String UPLOAD_SLIDE_DIR =
            "file:src/main/resources/static/uploads/";

    /**
     * Cấu hình ánh xạ URL → thư mục tài nguyên
     * Spring sẽ gọi method này khi khởi tạo ứng dụng
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Khi truy cập URL: /images/maytinh/**
        // → Spring sẽ tìm file tương ứng trong thư mục:
        // src/main/resources/static/images/maytinh/
        registry.addResourceHandler("/images/maytinh/**")
                .addResourceLocations(UPLOAD_DIR);

        // Khi truy cập URL: /uploads/**
        // → Spring sẽ tìm file trong:
        // src/main/resources/static/uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(UPLOAD_SLIDE_DIR);

        // Cấu hình tài nguyên tĩnh mặc định
        // Bao gồm: css, js, images có sẵn trong project
        // Tương ứng thư mục: src/main/resources/static/
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
