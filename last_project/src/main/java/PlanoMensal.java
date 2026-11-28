public class PlanoMensal extends Plano{

    public PlanoMensal() {
        super("Mensal", 40);
    }

    @Override
    public double calcularViagem(Veiculo veiculo, Long minutos) {
        // Retorna 0 se o calculo der menor que 0
        double minutoPagar = Math.max(0, minutos - 15);                                                                  
        return  veiculo.valorMinuto() * minutoPagar;
    }
}
