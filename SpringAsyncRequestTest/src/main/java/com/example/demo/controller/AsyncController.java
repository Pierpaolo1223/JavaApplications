package com.example.demo.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.service.AsyncService;

@RestController
@RequestMapping("/test")
public class AsyncController {

	public final String OPERATION_ID = "1";

	@Autowired
	private AsyncService asyncService;

	@GetMapping("/{operation_Id}")
	public CompletableFuture<String> performOperation(@PathVariable String operation_Id) {
		return asyncService.performAsyncOperation(operation_Id);
	}

	@DeleteMapping("/annulla/{operation_Id}")
	public String clearOperation(@PathVariable String operation_Id) {
		return asyncService.clearOperation(operation_Id);
	}

	@GetMapping("/inserimento")
	public SseEmitter avviaInserimento() {
		SseEmitter emitter = new SseEmitter(-1L); //Emette sempre valori.
		asyncService.eseguiInserimentoLungo(emitter);
		return emitter;
	}

}