public class PlanoAvulso extends Plano{

    public PlanoAvulso() {
        super("Avulso", 0);
    }

    @Override
    public double calcularViagem(Veiculo veiculo, Long minutos) {
        return veiculo.taxaDesbloqueio() + veiculo.valorMinuto() * minutos;
    }
}
