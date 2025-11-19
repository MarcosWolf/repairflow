package com.marcoswolf.crm.reparos.business.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEquipamentoService implements TipoEquipamentoConsultaService, TipoEquipamentoComandoService {
    private final TipoEquipamentoRepository tipoEquipamentoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final ReparoRepository reparoRepository;
    private final ClienteRepository clienteRepository;

    public TipoEquipamentoService(TipoEquipamentoRepository tipoEquipamentoRepository, EquipamentoRepository equipamentoRepository, ReparoRepository reparoRepository, ClienteRepository clienteRepository) {
        this.tipoEquipamentoRepository = tipoEquipamentoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.reparoRepository = reparoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<TipoEquipamento> listarTodos() {
        List<TipoEquipamento> tipos = tipoEquipamentoRepository.findAll();

        return tipos;
    }

    public List<TipoEquipamento> listarTodosTabela() {
        List<TipoEquipamento> tipos = tipoEquipamentoRepository.findAll();

        tipos.forEach(t -> {
            t.setTotalClientes(contarClientesPorTipo(t.getId()));
            t.setTotalEquipamentos(contarEquipamentosPorTipo(t.getId()));
            t.setTotalReparos(contarReparosPorTipo(t.getId()));
        });

        return tipos;
    }

    public Optional<TipoEquipamento> buscarPorId(Long id) {
        return tipoEquipamentoRepository.findById(id);
    }

    public Long contarClientesPorTipo(Long tipoId) {
        return clienteRepository.countByTipoEquipamentoId(tipoId);
    }

    public Long contarEquipamentosPorTipo(Long tipoId) {
        return equipamentoRepository.countByTipoEquipamentoId(tipoId);
    }

    public Long contarReparosPorTipo(Long tipoId) {
        return reparoRepository.countByTipoEquipamentoId(tipoId);
    }

    public void salvar(TipoEquipamento tipoEquipamento) {
        tipoEquipamentoRepository.saveAndFlush(tipoEquipamento);
    }

    public void deletar(Long id) {
        var tipoEquipamento = tipoEquipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de equipamento não encontrado."));

        tipoEquipamentoRepository.delete(tipoEquipamento);
    }
}
