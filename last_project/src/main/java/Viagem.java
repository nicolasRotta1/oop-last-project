import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class Viagem {

    private final int codigo;
    private static int contadorViagem = 0;
    private final LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    private final Usuario usuario;
    private final Veiculo veiculo;

    private final Estacao estacaoPartida;
    private Estacao estacaoChegada;

    private double valorTotal;


    Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoPartida){

        if(usuario == null){
            throw new IllegalArgumentException("Usuário não pode ser null para inicar uma viagem.");
        }

        if(veiculo == null){
            throw new IllegalArgumentException("Veiculo não pode ser null para inicar uma viagem.");
        }

        if(estacaoPartida == null){
            throw new IllegalArgumentException("Estação de partida não pode ser null para inicar uma viagem.");
        }

        if (!estacaoPartida.possuiVeiculo(veiculo.getCodigo())) {
            throw new IllegalArgumentException("A estação de partida não possui este veículo!");
        }

        estacaoPartida.removerVeiculo(veiculo);

        this.usuario = usuario;
        this.veiculo = veiculo;
        this.estacaoPartida = estacaoPartida;
        dataInicio = LocalDateTime.now();

        contadorViagem++;
        codigo = contadorViagem;

    }

    public boolean encerrarViagem(Estacao estacaoChegada){

        if(!isEmAndamento()){
            throw new IllegalArgumentException("Esta viagem já foi encerrada!");
        }

        if(estacaoChegada == null){
            throw new IllegalArgumentException("Estacao de chegada não pode ser null para encerrar uma viagem.");
        }

        // esse vai verificar se tem vaga na estacaoChegada
        if(!estacaoChegada.possuiVagaDisponivel()){
            throw new IndexOutOfBoundsException("A estação de chegada não tem vagas disponíveis!");
        }

        LocalDateTime dataFim = LocalDateTime.now();

        if(dataFim.isBefore(dataInicio)){
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }

        // faz o calculo para so depois atribuir a variavel
        double calcularValorTotal = calcularValorTotal(
                Duration.between(dataInicio, dataFim).toMinutes());

        // adiciona o veiculo e ja faz a validacao
        if(!estacaoChegada.estacionarVeiculo(veiculo)){
            throw new IllegalStateException("Não foi possívle adicionar o veículo à estação de chegada!");
        }

        // Metodo para consumir bateria do veiculo
        veiculo.consumirEnergia(Duration.between(dataInicio, dataFim).toMinutes());
        if (calcularValorTotal > 0) {
            usuario.getCarteira().debitar(calcularValorTotal);
        }

        this.valorTotal = calcularValorTotal;
        this.dataFim = dataFim;
        this.estacaoChegada = estacaoChegada;

        return true;
    }

    private double calcularValorTotal(long minutos) {
        return BigDecimal.valueOf(usuario.getPlano().calcularViagem(veiculo, minutos))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public long calcularDuracao() {

        if (isEmAndamento()) {
            throw new IllegalStateException("Não é possível calcular a duração de uma viagem em andamento!");
        }

        return Duration.between(dataInicio, dataFim).toMinutes();
    }

    public int getCodigo() {
        return codigo;
    }

    public static int getContadorViagem() {
        return contadorViagem;
    }

    // returna true se a dataFim == null
    public boolean isEmAndamento() {
        return dataFim == null;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Estacao getEstacaoPartida() {
        return estacaoPartida;
    }

    public Estacao getEstacaoChegada() {
        return estacaoChegada;
    }

    // verifica se a viagem ainda esta em andamento antes de retornar o valorTotal
    public double getValorTotal(){

        if(isEmAndamento()){
            throw new IllegalStateException("O valor total ainda não pode ser calculado!");
        }

        return valorTotal;
    }
}
