package scriptbot.calc_area;

public class Orcamento {

    private String data_orcamento;
    private String data_agendamento;
    private String data_solicitada;
    private String numero;
    private Integer largura;
    private Integer comprimento;
    private Integer metragem;
    private String ambiente;
    private String cor;
    private String observacao;

    public String getData_orcamento() {
        return data_orcamento;
    }

    public void setData_orcamento(String data_orcamento) {
        this.data_orcamento = data_orcamento;
    }

    public String getData_agendamento() {
        return data_agendamento;
    }

    public void setData_agendamento(String data_agendamento) {
        this.data_agendamento = data_agendamento;
    }

    public String getData_solicitada() {
        return data_solicitada;
    }

    public void setData_solicitada(String data_solicitada) {
        this.data_solicitada = data_solicitada;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getLargura() {
        return largura;
    }

    public void setLargura(Integer largura) {
        this.largura = largura;
    }

    public Integer getComprimento() {
        return comprimento;
    }

    public void setComprimento(Integer comprimento) {
        this.comprimento = comprimento;
    }

    public Integer getMetragem() {
        return metragem;
    }

    public void setMetragem(Integer metragem) {
        this.metragem = metragem;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
