import java.util.List;

public interface EstrategiaFiltragem {
    public void setArgs(List<String> args);

    public boolean seleciona(Produto produto);
}
