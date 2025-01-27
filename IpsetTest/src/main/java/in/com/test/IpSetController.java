package in.com.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipsetApi")
public class IpSetController {

    @Autowired
    private IpSetServiceImpl ipSetService;

    @PostMapping
    public ResponseEntity<String> createIpSet(@RequestBody IpSet ipSet) {
        return ipSetService.createIpSet(ipSet);
    }
}