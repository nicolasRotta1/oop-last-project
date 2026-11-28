import Enums.StatusVeiculo;
import java.util.ArrayList;

public class Usuario {
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private Carteira carteira;
    private Plano plano;
    private ArrayList<Viagem> viagens;

    public Usuario(String cpf, String nome, String email, String telefone, Plano plano) {
        if (plano == null) {
            throw new IllegalArgumentException("Erro: Plano não pode ser nulo");
        }
        if (cpf == null) {
            throw new IllegalArgumentException("Erro: CPF não pode ser nulo");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Erro: Nome não pode ser vazio");
        }
        if (email != null && !email.isBlank() && !email.contains("@")) {
            throw new IllegalArgumentException("Erro: E-mail inválido");
        }
        if (!telefoneValido(telefone)) {
            throw new IllegalArgumentException("Erro: Telefone inválido");
        }

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.carteira = new Carteira();
        this.plano = plano;
        this.viagens = new ArrayList<>();
    }

    private static boolean telefoneValido(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            return true;
        }
        if (!telefone.matches("[0-9() .-]+")) {
            return false;
        }
        int quantidadeDigitos = telefone.replaceAll("\\D", "").length();
        return quantidadeDigitos == 10 || quantidadeDigitos == 11;
    }

    public boolean mudarPlano(Plano plano) {
        if(plano == this.plano){
            return false;
        }
        if (plano.getValor() > carteira.getSaldo()){
            return false;
        }
        if (plano.getValor() > 0) {
            if (!carteira.debitar(plano.getValor())) {
                return false;
            }
        }
        setPlano(plano);
        return true;
    }
    

    public boolean possuiViagemEmAndamento(){
        for (Viagem viagem : viagens) {
            if (viagem.isEmAndamento()) {
                return true;
            }
        }
        return false;
    }

    public boolean iniciarViagem(Veiculo veiculo, Estacao estacaoPartida) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo para iniciar uma viagem.");
        }
        if (estacaoPartida == null) {
            throw new IllegalArgumentException("Estação de partida não pode ser nula para iniciar uma viagem.");
        }
        if (carteira.getSaldo() < 5
                || possuiViagemEmAndamento()
                || veiculo.getStatus() != StatusVeiculo.DISPONIVEL
                || (veiculo instanceof Eletrico eletrico && eletrico.getCargaBateria() < 20)) {
            return false;
        }

        Viagem viagem = new Viagem(this, veiculo, estacaoPartida);
        addViagem(viagem);
        return true;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        if (plano == null) {
            throw new IllegalArgumentException("Erro: Plano não pode ser nulo");
        }
        this.plano = plano;
    }

    public ArrayList<Viagem> getViagens() {
        return viagens;
    }

    public void addViagem(Viagem viagem) {
        if(viagem == null){
            throw new IllegalArgumentException("Erro: Viagem inválida");
        }
        this.viagens.add(viagem);
    }
}
