import java.util.List;

public class EstrategiaIntervaloPreco implements EstrategiaFiltragem {
	private final double[] args = new double[2];

	@Override
	public void setArgs(List<String> args) {
		this.args[0] = Double.parseDouble(args.get(0));
		this.args[1] = Double.parseDouble(args.get(1));
	}

	@Override
	public boolean seleciona(Produto produto) {
		double preco = produto.getPreco();
		return preco >= args[0] && preco <= args[1];
	}
}
