package com.marcoswolf.repairflow.ui.controller.reparo;

import com.marcoswolf.repairflow.business.cliente.ClienteConsultaService;
import com.marcoswolf.repairflow.business.equipamento.EquipamentoConsultaService;
import com.marcoswolf.repairflow.business.statusReparo.StatusReparoConsultaService;
import com.marcoswolf.repairflow.infrastructure.entities.*;
import com.marcoswolf.repairflow.infrastructure.entities.*;
import com.marcoswolf.repairflow.ui.handler.pecaPagamento.validator.PecaPagamentoSalvarValidator;
import com.marcoswolf.repairflow.ui.handler.reparo.action.ReparoExcluirAction;
import com.marcoswolf.repairflow.ui.handler.reparo.dto.ReparoFormData;
import com.marcoswolf.repairflow.ui.handler.reparo.action.ReparoSalvarAction;
import com.marcoswolf.repairflow.ui.interfaces.DataReceiver;
import com.marcoswolf.repairflow.ui.navigation.ViewNavigator;
import com.marcoswolf.repairflow.ui.tables.PecaPagamentoTableView;
import com.marcoswolf.repairflow.ui.utils.*;
import com.marcoswolf.repairflow.ui.utils.ComboBoxUtils;
import com.marcoswolf.repairflow.ui.utils.MaskUtils;
import com.marcoswolf.repairflow.ui.utils.ParseUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReparoFormController implements DataReceiver<Reparo> {
    private final ViewNavigator navigator;

    private final ClienteConsultaService clienteConsultaService;
    private final EquipamentoConsultaService equipamentoConsultaService;
    private final StatusReparoConsultaService statusReparoConsultaService;
    private final PecaPagamentoSalvarValidator pecaValidator;

    private final ReparoSalvarAction salvarAction;
    private final ReparoExcluirAction excluirAction;

    private Reparo novoReparo;
    private final ObservableList<PecaPagamento> pecas = FXCollections.observableArrayList();

    @FXML private AnchorPane rootPane;
    @FXML private Label lblTitulo;
    @FXML private Button btnExcluir;
    @FXML private ComboBox<Cliente> comboCliente;
    @FXML private ComboBox<Equipamento> comboEquipamento;
    @FXML private ComboBox<StatusReparo> comboStatus;
    @FXML private DatePicker dateEntrada, dateSaida, datePagamento;
    @FXML private TextArea txtDescricaoProblema, txtServicoExecutado;
    @FXML private TextField txtOrdemServico, txtTipo, txtNumeroSerie, txtValorServico, txtDesconto, txtValorTotal;
    @FXML private TextField txtPecaDescricao, txtPecaQuantidade, txtPecaValorUnitario;
    @FXML private TableView<PecaPagamento> tabela;
    @FXML private TableColumn<PecaPagamento, String> colDescricao;
    @FXML private TableColumn<PecaPagamento, Integer> colQuantidade;
    @FXML private TableColumn<PecaPagamento, String> colValorUnitario;
    @FXML private TableColumn<PecaPagamento, String> colValorTotalLinha;
    @FXML private TableColumn<PecaPagamento, Void> colRemover;

    private static final String GERENCIAR_PATH = "/fxml/reparo/reparo-gerenciar.fxml";

    @FXML
    public void initialize() {
        configurarCampos();
        configurarTabela();
        configurarRecalculo();
        alimentarCombos();
        configurarListeners();
    }

    private void configurarCampos() {
        TextFieldUtils.aplicarLimite(txtOrdemServico, 12);
        MaskUtils.aplicarMascaraData(dateEntrada);
        MaskUtils.aplicarMascaraData(dateSaida);
        MaskUtils.aplicarMascaraData(datePagamento);
        MaskUtils.aplicarMascaraMonetaria(txtValorServico);
        MaskUtils.aplicarMascaraMonetaria(txtDesconto);
        MaskUtils.aplicarMascaraMonetaria(txtPecaValorUnitario);
        txtValorTotal.setEditable(false);
    }

    private void configurarTabela() {
        PecaPagamentoTableView.configurarTabela(
                tabela,             // TableView<PecaPagamento>
                colDescricao,       // TableColumn<PecaPagamento, String>
                colQuantidade,      // TableColumn<PecaPagamento, Integer>
                colValorUnitario,   // TableColumn<PecaPagamento, String>
                colValorTotalLinha, // TableColumn<PecaPagamento, String>
                colRemover,         // TableColumn<PecaPagamento, Void>
                pecas,              // ObservableList<PecaPagamento>
                p -> recalcularTotal() // ação de remover
        );
    }

    private void configurarRecalculo() {
        txtValorServico.textProperty().addListener((obs, oldV, newV) -> recalcularTotal());
        txtDesconto.textProperty().addListener((obs, oldV, newV) -> recalcularTotal());
    }

    private void alimentarCombos() {
        ComboBoxUtils.carregarCombo(comboCliente, clienteConsultaService.listarTodos(), Cliente::getNome, this::clientePlaceholder);
        ComboBoxUtils.carregarCombo(comboEquipamento, FXCollections.observableArrayList(), eq -> eq.getModelo() != null ? eq.getModelo() : "Sem modelo", this::equipamentoPlaceholder);
        comboEquipamento.setDisable(true);
        ComboBoxUtils.carregarCombo(comboStatus, statusReparoConsultaService.listarTodos(), StatusReparo::getNome, this::statusPlaceholder);
    }

    private Cliente clientePlaceholder() {
        return placeholder(new Cliente(), "nome", "Selecione");
    }

    private Equipamento equipamentoPlaceholder() {
        return placeholder(new Equipamento(), "modelo", "Selecione");
    }

    private StatusReparo statusPlaceholder() {
        return placeholder(new StatusReparo(), "nome", "Selecione");
    }

    private <T> T placeholder(T obj, String field, String value) {
        try {
            var f = obj.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception ignored) {}
        return obj;
    }

    private void configurarListeners() {
        comboCliente.valueProperty().addListener((obs, old, novo) -> atualizarComboEquipamento(novo));
        comboEquipamento.valueProperty().addListener((obs, old, novo) -> txtNumeroSerie.setText(novo != null ? novo.getNumeroSerie() : ""));
        comboEquipamento.valueProperty().addListener((obs, old, novo) -> txtTipo.setText(novo != null ? novo.getTipoEquipamento().getNome() : ""));
    }

    private void atualizarComboEquipamento(Cliente cliente) {
        comboEquipamento.getItems().clear();
        if (cliente == null || cliente.getId() == 0) {
            comboEquipamento.setDisable(true);
            return;
        }
        var equipamentos = equipamentoConsultaService.listarPorClienteId(cliente.getId());
        comboEquipamento.setDisable(equipamentos.isEmpty());
        ComboBoxUtils.carregarCombo(comboEquipamento, equipamentos, Equipamento::getModelo, this::equipamentoPlaceholder);
    }

    @FXML
    private void adicionarPeca() {
        String descricao = txtPecaDescricao.getText();
        Integer quantidade = ParseUtils.parseInteger(txtPecaQuantidade);
        BigDecimal valorUnit = ParseUtils.parseBigDecimal(txtPecaValorUnitario);

        try {
            pecaValidator.validar(descricao, quantidade, valorUnit);

            PecaPagamento nova = new PecaPagamento();
            nova.setNome(descricao);
            nova.setQuantidade(quantidade);
            nova.setValor(valorUnit);
            pecas.add(nova);
            limparCamposPeca();
            tabela.refresh();
            recalcularTotal();
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        }
    }

    private void limparCamposPeca() {
        txtPecaDescricao.clear();
        txtPecaQuantidade.clear();
        txtPecaValorUnitario.clear();
    }

    private void recalcularTotal() {
        BigDecimal valorServico = ParseUtils.parseValorBR(txtValorServico.getText());
        BigDecimal desconto = ParseUtils.parseValorBR(txtDesconto.getText());

        BigDecimal totalPecas = pecas.stream()
                .map(PecaPagamento::getTotalLinha)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = valorServico.add(totalPecas).subtract(desconto);

        txtValorTotal.setText(ParseUtils.formatarValorBR(total));
    }

    @Override
    public void setData(Reparo reparo) {
        this.novoReparo = reparo;
        preencherFormulario(reparo);
    }

    private void preencherFormulario(Reparo reparo) {
        if (reparo == null) {
            lblTitulo.setText("Cadastrar Reparo");
            btnExcluir.setVisible(false);
            limparFormulario();
            return;
        }

        lblTitulo.setText("Editar Reparo");
        btnExcluir.setVisible(true);

        txtOrdemServico.setText(reparo.getOrdemServico());

        var cliente = reparo.getEquipamento().getCliente();
        comboCliente.setValue(cliente);

        var equipamentos = equipamentoConsultaService.listarPorClienteId(cliente.getId());
        comboEquipamento.setItems(FXCollections.observableArrayList(equipamentos));

        comboEquipamento.setValue(reparo.getEquipamento());
        comboStatus.setValue(reparo.getStatus());

        dateEntrada.setValue(reparo.getDataEntrada());
        dateSaida.setValue(reparo.getDataSaida());
        datePagamento.setValue(reparo.getPagamento().getDataPagamento());

        txtDescricaoProblema.setText(reparo.getDescricaoProblema());
        txtServicoExecutado.setText(reparo.getServicoExecutado());

        txtValorServico.setText(String.valueOf(reparo.getPagamento().getValorServico()));
        txtDesconto.setText(String.valueOf(reparo.getPagamento().getDesconto()));

        pecas.setAll(reparo.getPagamento().getPecas());
        recalcularTotal();
    }

    private void limparFormulario() {
        txtOrdemServico.clear();;
        comboCliente.getSelectionModel().clearSelection();
        comboEquipamento.getItems().clear();
        comboStatus.getSelectionModel().selectFirst();
        dateEntrada.setValue(LocalDate.now());
        dateSaida.setValue(null);
        datePagamento.setValue(null);
        txtTipo.clear();
        txtNumeroSerie.clear();
        txtDescricaoProblema.clear();
        txtServicoExecutado.clear();
        txtValorServico.clear();
        txtDesconto.clear();
        txtValorTotal.clear();
        pecas.clear();
    }

    @FXML
    private void salvar() {
        var data = new ReparoFormData(
                txtOrdemServico.getText(),
                comboEquipamento.getValue(),
                dateEntrada.getValue(),
                dateSaida.getValue(),
                txtDescricaoProblema.getText(),
                txtServicoExecutado.getText(),
                comboStatus.getValue(),
                ParseUtils.parseBigDecimal(txtValorServico),
                ParseUtils.parseBigDecimal(txtDesconto),
                datePagamento.getValue(),
                pecas
        );

        boolean sucesso = salvarAction.execute(novoReparo, data);
        if (sucesso) voltar();
    }

    @FXML
    private void excluir() {
        boolean sucesso = excluirAction.execute(novoReparo, null);
        if (sucesso) voltar();
    }

    @FXML
    private void voltar() {
        navigator.openViewRootPane(GERENCIAR_PATH, rootPane, null);
    }
}