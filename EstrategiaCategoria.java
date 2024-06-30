public class EstrategiaCategoria implements EstrategiaFiltragem {
    private String arg;

    @Override
    public void setArg(String arg) {
        this.arg = arg;
    }

    @Override
    public boolean seleciona(Produto produto) {
        return arg.equals(produto.getCategoria());
    }
}
