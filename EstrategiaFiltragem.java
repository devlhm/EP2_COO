import java.util.List;

public interface EstrategiaFiltragem {
    void setArgs(List<String> args);

    boolean seleciona(Produto produto);
}
