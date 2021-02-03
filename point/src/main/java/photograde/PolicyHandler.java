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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverGradeCanceled_(@Payload GradeCanceled gradeCanceled){

        if(gradeCanceled.isMe()){
            System.out.println("##### listener  : " + gradeCanceled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverGraded_(@Payload Graded graded){

        if(graded.isMe()){
            Point point = new Point();
            point.setGradeId(graded.getId());
            point.setStatus("You got Point");
            //point.setStatus(" Delivery Status is " + System.getenv("STATUS"));

            pointRepository.save(point);
            System.out.println("##### listener  : " + graded.toJson());
        }
    }

}
