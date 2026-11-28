public class PlanoUniversitario extends Plano{

    public PlanoUniversitario(){
        super("Universitario", 25);
    }

    @Override
    public double calcularViagem(Veiculo veiculo, Long minutos) {
        double valor = veiculo.taxaDesbloqueio() + veiculo.valorMinuto() * minutos;
        if (!veiculo.aceitaDesconto()) {
            return valor;
        }
        return valor * 0.50;
    }
}
