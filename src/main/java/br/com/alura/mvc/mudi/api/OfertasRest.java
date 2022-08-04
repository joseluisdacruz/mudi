package br.com.alura.mvc.mudi.api;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("api/ofertas")
public class OfertasRest {
	
	private final PedidoRepository pedidoRepository;

	public OfertasRest(PedidoRepository pedidoRepository) {
		super();
		this.pedidoRepository = pedidoRepository;
	}

	@PostMapping
	public Oferta criarNovaOferta(RequisicaoNovaOferta requisicao) {
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicao.getPedidoId());
		if(pedidoBuscado.isEmpty()) {
			return null;
		}
		Pedido pedido = pedidoBuscado.get();
		Oferta nova = requisicao.toOferta();
		nova.setPedido(pedido);
		pedido.getOfertas().add(nova);
		pedidoRepository.save(pedido);
		
		return nova;
	}
}
