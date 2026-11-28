public class PatineteEletrico extends Eletrico {
    private static final double TAXA_DESBLOQUEIO = 3.00;
    private static final double VALOR_MINUTO = 0.40;
    private static final int CONSUMO_POR_MINUTO = 2;

    public PatineteEletrico(String modelo) {
        super(modelo);
    }

    @Override
    public double taxaDesbloqueio() { return TAXA_DESBLOQUEIO; }

    @Override
    public double valorMinuto() { return VALOR_MINUTO; }

    @Override
    public int consumoPorMinuto() { return CONSUMO_POR_MINUTO; }

    @Override
    public boolean aceitaDesconto() {
        return false;
    }
}