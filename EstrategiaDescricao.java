import java.util.List;

public class EstrategiaDescricao implements EstrategiaFiltragem {
    private String arg;

    @Override
    public void setArgs(List<String> args) {
        this.arg = args.get(0);
    }

    @Override
    public boolean seleciona(Produto produto) {
        return produto.getDescricao().contains(arg);
    }
}
