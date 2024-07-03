import java.util.List;

public class EstrategiaEstoqueMenorIgual implements EstrategiaFiltragem {
	private int arg;

	@Override
	public void setArgs(List<String> args) {
		this.arg = Integer.parseInt(args.get(0));
	}

	@Override
	public boolean seleciona(Produto produto) {
		return produto.getQtdEstoque() <= arg;
	}
}
