package pl.algorit.restfulservices.restutils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    String code;
    String message;
}
