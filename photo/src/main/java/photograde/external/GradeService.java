
package photograde.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="grade", url="${api.grade.url}")
public interface GradeService {

    @RequestMapping(method= RequestMethod.GET, path="/grades")
    public void gradeCancel(@RequestBody Grade grade);

}
