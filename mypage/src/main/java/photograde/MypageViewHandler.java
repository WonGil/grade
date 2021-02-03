package photograde;

import photograde.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPhotoRegistered_then_CREATE_1 (@Payload PhotoRegistered photoRegistered) {
        try {
            if (photoRegistered.isMe()) {
                // view 객체 생성
                Mypage mypage  = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setPhotoNm(photoRegistered.getPhotoNm());
                mypage.setUser(photoRegistered.getCookingMethod());
                mypage.setCamera(photoRegistered.getCamera());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointAwarded_then_UPDATE_1(@Payload PointAwarded pointAwarded) {
        try {
            if (pointAwarded.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByGradeId(pointAwarded.getGradeId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setGradeId(pointAwarded.getId());
                    mypage.setStatus(pointAwarded.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointCanceled_then_UPDATE_2(@Payload PointCanceled pointCanceled) {
        try {
            if (pointCanceled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByGradeId(pointCanceled.getGradeId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setGradeId(pointCanceled.getId());
                    mypage.setStatus(pointCanceled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenGraded_then_UPDATE_3(@Payload Graded graded) {
        try {
            if (graded.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByGradeId(graded.getGradeId());
                for (Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setGradeId(graded.getId());
                    mypage.setStatus(graded.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
