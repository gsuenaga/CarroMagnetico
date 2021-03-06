package ar.edu.udemm.springboot.services.serialComm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialPortReader implements SerialPortEventListener {

	private final Logger logger = LoggerFactory.getLogger(SerialPortReader.class);

	private CommService commService;

	List<String> medicionesArray = new ArrayList<String>();

	private SerialPort serialPort;

	private String temp;

	private int cuenta;

	public SerialPortReader(SerialPort serialPort, CommService commService) {
		super();
		this.serialPort = serialPort;
		this.commService = commService;
		this.temp = "";
		this.cuenta = 0;
	}


	public void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR()) {// If data is available
			int amount = event.getEventValue();
			String buffer;

			try {
				buffer = serialPort.readString();// Bytes(amount);
				if (amount > 0) {

					if (this.cuenta++ == 0) {
						getFormattedValue(buffer);
					} else {
						try {
							medicionesArray = new ArrayList<String>(medicionesArray);
							medicionesArray.addAll(getFormattedValue(buffer));
						} catch (Exception e) {
							e.printStackTrace();
						}
						this.cuenta = 0;
					}

					if (medicionesArray.size() > 4) {
						this.commService.addMedicion(medicionesArray);
						medicionesArray.clear();
					}
					logger.info(buffer);
				}
			} catch (SerialPortException e) {
				logger.error("Fallo lectura port", e);
			}
			/*
			 * if (event.getEventValue() == 10) {// Check bytes count in the input buffer //
			 * Read data, if 10 bytes available try { byte buffer[] =
			 * serialPort.readBytes(10); System.out.println(buffer); } catch
			 * (SerialPortException ex) { System.out.println(ex); } }
			 */
		} else if (event.isCTS()) {// If CTS line has changed state
			if (event.getEventValue() == 1) {// If line is ON
				logger.info("CTS - ON");
			} else {
				logger.info("CTS - OFF");
			}
		} else if (event.isDSR()) {/// If DSR line has changed state
			if (event.getEventValue() == 1) {// If line is ON
				logger.info("DSR - ON");
			} else {
				logger.info("DSR - OFF");
			}
		}
	}

	private List<String> getFormattedValue(String buffer) {
		List<String> list = new ArrayList<String>();
		int occuranceF = StringUtils.countOccurrencesOf(buffer, "F");
		int occuranceT = StringUtils.countOccurrencesOf(buffer, "T");
		if (occuranceF == occuranceT || this.temp.length() > 0) {
			this.temp = this.temp + buffer;

			int ocurranceA = StringUtils.countOccurrencesOf(this.temp, "T");
			if (ocurranceA == 5) {
				String[] arr = this.temp.replaceAll("F", "").replaceAll("T", "").replaceAll("\\n", "").split("\\r");
				list = Arrays.asList(arr);
				this.temp = "";
			}

		} else {
			this.temp = this.temp + buffer;
		}
		return list;
	}

}