package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}

	public void processInvoice(CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60.00; // transforma os minutos em fração de horas
		
		double basicPayment;
		if (hours <= 12.0) {
			basicPayment =  pricePerHour * Math.ceil(hours); //Math.ceil para arredondar pra cima.
		}
		else {
			
			basicPayment = pricePerDay * Math.ceil(hours/ 24.0); //Para transformar horas em dias.
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax)); 
	
	}
	
	
}
