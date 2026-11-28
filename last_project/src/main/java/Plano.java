public abstract class Plano {
    private String nome;
    private double valor;

    public Plano(String nome, double valor) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: Nome do plano inválido");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Erro: Valor do plano inválido");
        }
        this.nome = nome;
        this.valor = valor;
    }

    public double calcularMensalidade(){
        return getValor();
    }

    public abstract double calcularViagem(Veiculo veiculo, Long minutos);

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }
}
