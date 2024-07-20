package com.example.demo.service;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/* SSEEmitter per restituire gli eventi,CompletableFuture per gestire il risultato di un'operazione asincrona,@Async per far capire a Spring che debba essere eseguito in un thread separato.
 * 
 * 
 * */
@Service
public class AsyncService {

	private final ConcurrentHashMap<String, CompletableFuture<String>> operationCache = new ConcurrentHashMap<>();

	//@Async("customAsyncExecutor")
	public CompletableFuture<String> performAsyncOperation(String operationId) {
		CompletableFuture<String> future = operationCache.computeIfAbsent(operationId, this::createNewOperation)
				.handle((result, ex) -> { // gestisce il risultato di un'operazione asincrona,che sia andata bene o male.
					if (ex == null) {
						operationCache.remove(operationId);
						return result;
					} else {
						String errorMessage;
						if (ex instanceof CancellationException) {
							errorMessage = "Cancellazione avvenuta con successo";
						} else { // Errore d'altro tipo:ad esempio,spegnimento del server.
							errorMessage = "Si è verificato un errore: " + ex.getMessage();
						}
						System.out.println(errorMessage);
						// Restituisce un messaggio di errore in caso di fallimento
						return errorMessage;
					}
				});
		System.out.println(future);
		return future;
	}

	//@Async("customAsyncExecutor")
	private CompletableFuture<String> createNewOperation(String operationId) {
		// Crea un nuovo CompletableFuture e inizia l'operazione asincrona
		return CompletableFuture.supplyAsync(() -> { //supplyAsync restituisce un valore diverso da void,runAsync ritorna void.
			try {
				Thread.sleep(5000); // Simula un ritardo
			} catch (InterruptedException e) {
				System.out.println("Operazione interrotta");
				Thread.currentThread().interrupt();
			}
			return "Risultato di prova ottenuto per l'operazione: " + operationId;
		});
	}

	//@Async("customAsyncExecutor")
	public void eseguiInserimentoLungo(SseEmitter emitter) {
		CompletableFuture.runAsync(() -> {
			try {
				for (int i = 0; i <= 5; i++) {
					// Simula un'operazione di inserimento lunga
					// ...
					Thread.sleep(1000); // Attesa di 1 secondo per simulare il progresso
					emitter.send(SseEmitter.event().name("progress").data(i + "%"));
				}
				emitter.send(SseEmitter.event().name("complete").data("Evento completato"));
				System.out.println("Evento completato");
				emitter.complete();
			} catch (Exception e) {
				System.out.println("Evento con errori");
				emitter.completeWithError(e);
			}
		});
	}

	public String clearOperation(String operationId) {
		CompletableFuture<?> future = operationCache.get(operationId);
		if (future != null && !future.isDone()) {
			operationCache.remove(operationId);
			future.cancel(true);
			return "Cancellazione avvenuta con successo";
		} else {
			if (future == null) { // Se non esiste in cache
				System.out.println("Nessuna richiesta attiva");
				return "Nessun inserimento è attivo";
			} else {
				System.out.println("Il CompletableFuture è già terminato.");
				return "Inserimento già terminato";
			}
		}
	}

	/*
	 * //Metodo che consente di andare a inserire tanti valori,e andarne a vedere il
	 * progresso.
	 * 
	 * @Async("customAsyncExecutor") public CompletableFuture<Void>
	 * eseguiInserimentoConSaveAll(List<Entità> entitàList, SseEmitter emitter) {
	 * return CompletableFuture.runAsync(() -> { try { for (int i = 0; i <
	 * entitàList.size(); i++) { // Simula un'operazione di inserimento lunga Entità
	 * entità = entitàList.get(i); repository.save(entità); // Salva ogni entità
	 * individualmente int percentuale = ((i + 1) * 100) / entitàList.size();
	 * emitter.send(SseEmitter.event().name("progress").data(percentuale + "%"));
	 * Thread.sleep(1000); // Attesa di 1 secondo per simulare il progresso }
	 * emitter.complete(); } catch (Exception e) { emitter.completeWithError(e); }
	 * }); }
	 */

}
