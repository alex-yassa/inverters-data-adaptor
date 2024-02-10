package pl.twerd.invertersdatareceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvertersDataReceiverApplication {

	public static void main(String[] args) {
        /*ApplicationContext context = */
        SpringApplication.run(InvertersDataReceiverApplication.class, args);

//        BsiService bsiService = context.getBean(BsiService.class);
//		UserDto userDto = bsiService.sendTestData();
//        System.out.println(userDto);
	}

}
