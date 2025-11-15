package br.com.pedidos.api.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {
	
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	
	//cria a exchange
	// Cria a exchange (ponto de entrada das mensagens).
	// Aqui usamos Fanout, que envia a mesma mensagem para todas as filas ligadas.
	@Bean
	public Exchange pedidosExchange() {
		return new FanoutExchange(exchangeName);
	}
	
	//retorna um objeto rabbitadmin com a conectionfactory, o spring faz tudo automatico. cria as exchange, cria filas e cria ligações automaticamente
	// Cria o RabbitAdmin. Ele é responsável por registrar automaticamente:
	// - exchanges
	// - filas (se forem criadas via Bean)
	// - ligações (bindings)
	// Tudo isso baseado nos Beans presentes na aplicação.
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	
	//converte o objeto para json, para enviar para o rabbitmq
	// Converte objetos Java <-> JSON ao enviar mensagens pelo RabbitMQ.
	// Sem esse conversor, a mensagem iria como byte puro.
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	//responsável por enviar a mensagem / enviar a mensagens para o exchange
	// Responsável por enviar mensagens para o RabbitMQ.
	// O messageConverter permite enviar objetos sem ter que transformar manualmente em JSON.
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory); 
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}
	
	
	//escuta aplicação. e inicializa o rabbitadmin quando a aplicação subir.
	// Quando a aplicação terminar de subir, executa o rabbitAdmin.initialize(),
	// garantindo que tudo será criado no RabbitMQ antes do envio das mensagens.
	@Bean
	public ApplicationListener<ApplicationReadyEvent> aplicaApplicationReadyEventApplicationListener(RabbitAdmin rabbitAdmin) {
	//	return new ApplicationListener<ApplicationReadyEvent>() {
			
	//		@Override
	//		public void onApplicationEvent(ApplicationReadyEvent event) {
	//			rabbitAdmin.initialize();
	//		}
	//	};
	//
	
		return event -> rabbitAdmin.initialize();
	
	}
	
}
