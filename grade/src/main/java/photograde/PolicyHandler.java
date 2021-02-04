package photograde;

import photograde.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    GradeRepository gradeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPhotoRegistered_Grade(@Payload PhotoRegistered photoRegistered){

        if(photoRegistered.isMe()){
            //java.util.Optional<Grade> gradeOptional = gradeRepository.findById(photoRegistered.getGradeId());
            //Grade grade = gradeOptional.get();
            Grade grade= new Grade();
            grade.setGradeId(photoRegistered.getId());
            grade.setStatus("Grade Registered");

            gradeRepository.save(grade);
            System.out.println("##### listener  : " + photoRegistered.toJson());

        }
    }

}
