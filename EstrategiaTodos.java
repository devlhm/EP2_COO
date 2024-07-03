import java.util.List;

public class EstrategiaTodos implements EstrategiaFiltragem {
    @Override
    public void setArgs(List<String> args) {}

    @Override
    public boolean seleciona(Produto produto) { return true; }
}
