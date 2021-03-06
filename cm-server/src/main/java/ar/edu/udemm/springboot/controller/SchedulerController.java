package ar.edu.udemm.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import ar.edu.udemm.springboot.services.serialComm.CommService;
import ar.edu.udemm.springboot.services.serialComm.Medicion;

@EnableScheduling
@Controller
public class SchedulerController {

	private final Logger logger = LoggerFactory.getLogger(SchedulerController.class);

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private CommService commService;

	@Value("${app.scheduler.tiempo.refresco.milisegundos}")
	private final long cicloDemora = 500;

	@Scheduled(fixedRate = cicloDemora)
	public void greeting() throws InterruptedException {
		if ("Conectado".equals(commService.getEstado())) {
//			List<Medicion> resultado = commService.getMediciones();

			Medicion resultado = commService.getMedicion();
			if (resultado != null) {
				this.template.convertAndSend("/topic/hi", resultado);
				commService.clearMedicion();
				logger.info("Enviado : " + resultado.toString());
			}
		}
	}
}