package com.marcoswolf.crm.reparos.business.tipoEquipamento;

import com.marcoswolf.crm.reparos.business.cliente.filtro.strategy.*;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.TipoEquipamentoFiltro;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.strategy.FiltroNome;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.strategy.TipoEquipamentoFiltroStrategy;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class TipoEquipamentoService implements ITipoEquipamentoConsultaService, ITipoEquipamentoComandoService {
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

    // Create
    public void salvarTipoEquipamento(TipoEquipamento tipoEquipamento) {
        tipoEquipamentoRepository.saveAndFlush(tipoEquipamento);
    }

    // Read
    public List<TipoEquipamento> listarTodos() {
        return tipoEquipamentoRepository.findAll();
    }

    // Update
    public TipoEquipamento atualizarTipoEquipamento(Long id, TipoEquipamento novoTipoEquipamento) {
        var tipoEquipamento = tipoEquipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de equipamento não encontrado."));

        tipoEquipamento.setNome(novoTipoEquipamento.getNome());

        return tipoEquipamentoRepository.saveAndFlush(tipoEquipamento);
    }

    // Delete
    public void deletarTipoEquipamento(Long id) {
        var tipoEquipamento = tipoEquipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de equipamento não encontrado."));

        boolean possuiEquipamento = !equipamentoRepository.findByTipoEquipamento_Id(id).isEmpty();

        if (possuiEquipamento) {
            throw new RuntimeException("Não é possível excluir o tipo de equipamento: existe equipamento associado.");
        }

        tipoEquipamentoRepository.delete(tipoEquipamento);
    }
}
