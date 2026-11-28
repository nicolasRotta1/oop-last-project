import Enums.StatusVeiculo;

public abstract class Eletrico extends Veiculo{

    private static final int CARGA_MINIMA_VIAGEM = 20;

    private final Bateria bateria;

    public Eletrico(String modelo) {
        super(modelo);
        this.bateria = new Bateria();
    }

    // Percentual de bateria consumido a cada minuto de uso
    public abstract int consumoPorMinuto();

    // Validacao de se a bateria tem ao menos 20% da carga antes de iniciar a viagem
    @Override
    public void utilizarVeiculo(){
        if(bateria.getCarga() < CARGA_MINIMA_VIAGEM){
            throw new IllegalStateException("O veículo precisa de pelo menos "
                    + CARGA_MINIMA_VIAGEM + "% de carga para ser utilizado!");
        }

        super.utilizarVeiculo();
    }

    // Chamado ao terminar a viagem
    public void consumirBateria(int minutos){
        if(minutos < 1){
            throw new IllegalArgumentException("Os minutos de uso devem ser maiores que zero!");
        }

        bateria.utilizarCarga(minutos * consumoPorMinuto());
    }

    @Override
    public void consumirEnergia(long minutos) {
        if (minutos < 1) {
            return;
        }

        int consumo = consumoPorMinuto();
        if (consumo <= 0) {
            throw new IllegalStateException("O consumo de bateria por minuto deve ser maior que zero!");
        }

        int cargaAtual = bateria.getCarga();
        long minutosParaEsgotar = (cargaAtual + (long) consumo - 1) / consumo;
        if (minutos >= minutosParaEsgotar) {
            bateria.utilizarCarga(cargaAtual);
            return;
        }
        consumirBateria((int) minutos);
    }

    public void recarregar(){
        if(getStatus() == StatusVeiculo.EM_USO){
            throw new IllegalStateException("Um veículo em uso não pode ser recarregado!");
        }

        bateria.recarregarBateria();
    }

    public int getCargaBateria() {
        return bateria.getCarga();
    }

    public Bateria getBateria() {
        return bateria;
    }

}
