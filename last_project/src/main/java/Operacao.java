import java.time.LocalDateTime;
import java.util.UUID;

public class Operacao {
    private final UUID id;
    private final LocalDateTime data;
    private final double valor;
    private final boolean entrada;
    private final double saldoApos;

    public Operacao(double valor, boolean entrada, double saldoApos) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Erro: Valor da operação inválido");
        }
        this.id = UUID.randomUUID();
        this.data = LocalDateTime.now();
        this.valor = valor;
        this.entrada = entrada;
        this.saldoApos = saldoApos;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public double getSaldoApos() {
        return saldoApos;
    }
}
