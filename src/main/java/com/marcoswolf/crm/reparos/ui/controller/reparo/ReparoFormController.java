package com.marcoswolf.crm.reparos.ui.controller.reparo;

import com.marcoswolf.crm.reparos.business.cliente.ClienteConsultaService;
import com.marcoswolf.crm.reparos.business.equipamento.EquipamentoConsultaService;
import com.marcoswolf.crm.reparos.business.statusReparo.StatusReparoConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.*;
import com.marcoswolf.crm.reparos.ui.handler.pecaPagamento.validator.PecaPagamentoSalvarValidator;
import com.marcoswolf.crm.reparos.ui.handler.reparo.action.ReparoExcluirAction;
import com.marcoswolf.crm.reparos.ui.handler.reparo.dto.ReparoFormData;
import com.marcoswolf.crm.reparos.ui.handler.reparo.action.ReparoSalvarAction;
import com.marcoswolf.crm.reparos.ui.interfaces.DataReceiver;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.tables.PecaPagamentoTableView;
import com.marcoswolf.crm.reparos.ui.utils.ComboBoxUtils;
import com.marcoswolf.crm.reparos.ui.utils.ParseUtils;
import com.marcoswolf.crm.reparos.ui.utils.TextFieldUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TextField txtNumeroSerie, txtValorServico, txtDesconto, txtValorTotal;
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
        TextFieldUtils.aplicarMascaraNumerica(txtValorServico);
        TextFieldUtils.aplicarMascaraNumerica(txtDesconto);
        txtValorTotal.setEditable(false);
    }

    private void configurarTabela() {
        colDescricao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValorUnitario.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValorFormatado()));
        colValorTotalLinha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTotalLinhaFormatado()));
        colRemover.setCellFactory(PecaPagamentoTableView.criarBotaoRemover(pecas, p -> recalcularTotal()));
        tabela.setItems(pecas);
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
        BigDecimal valorServico = ParseUtils.parseBigDecimal(txtValorServico);
        BigDecimal desconto = ParseUtils.parseBigDecimal(txtDesconto);
        BigDecimal totalPecas = pecas.stream().map(PecaPagamento::getTotalLinha).reduce(BigDecimal.ZERO, BigDecimal::add);
        txtValorTotal.setText(valorServico.add(totalPecas).subtract(desconto).toString());
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
        comboCliente.getSelectionModel().clearSelection();
        comboEquipamento.getItems().clear();
        comboStatus.getSelectionModel().selectFirst();
        dateEntrada.setValue(LocalDate.now());
        dateSaida.setValue(null);
        datePagamento.setValue(null);
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