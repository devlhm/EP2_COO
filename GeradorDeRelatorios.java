import java.io.PrintWriter;
import java.io.IOException;

import java.util.*;

// TODO: acrescentar os adicionais

public class GeradorDeRelatorios {

	public static final String ALG_INSERTIONSORT = "insertion";
	public static final String ALG_QUICKSORT = "quick";

	public static final String CRIT_DESC_CRESC = "descricao_c";
	public static final String CRIT_PRECO_CRESC = "preco_c";
	public static final String CRIT_ESTOQUE_CRESC = "estoque_c";
	public static final String CRIT_DESC_DEC = "descricao_d";
	public static final String CRIT_PRECO_DEC = "preco_d";
	public static final String CRIT_ESTOQUE_DEC = "estoque_d";

	public static final String FILTRO_TODOS = "todos";
	public static final String FILTRO_ESTOQUE_MENOR_OU_IGUAL_A = "estoque_menor_igual";
	public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual";
	public static final String FILTRO_INTERVALO_PRECO = "intervalo_preco";
	public static final String FILTRO_DESCRICAO = "descricao";

	// operador bit a bit "ou" pode ser usado para combinar mais de
	// um estilo de formatacao simultaneamente (veja como no main)
	public static final int FORMATO_PADRAO = 0b0000;
	public static final int FORMATO_NEGRITO = 0b0001;
	public static final int FORMATO_ITALICO = 0b0010;

	private List<Produto> produtos;
	private int format_flags;

	private EstrategiaOrdenacao estrategiaOrdenacao;
	private EstrategiaFiltragem estrategiaFiltragem;
	private List<String> argsFiltro;


	public GeradorDeRelatorios(List<Produto> produtos, EstrategiaOrdenacao estrategiaOrdenacao,
							   EstrategiaFiltragem estrategiaFiltragem, List<String> argsFiltro) {

		//TODO: rapaz ta certo isso?
		this.produtos = produtos;

		this.argsFiltro = argsFiltro;
		this.estrategiaOrdenacao = estrategiaOrdenacao;
		this.estrategiaFiltragem = estrategiaFiltragem;
	}

	private void ordena(int ini, int fim) {
		estrategiaOrdenacao.ordena(ini, fim, produtos);
	}

	public void debug() {

		System.out.println("Gerando relatório para array contendo " + produtos.size() + " produto(s)");
		System.out.println("parametros filtro = '" + argsFiltro.toString() + "'");
	}

	public void geraRelatorio(String arquivoSaida) throws IOException {

		debug();

		ordena(0, produtos.size() - 1);

		PrintWriter out = new PrintWriter(arquivoSaida);

		out.println("<!DOCTYPE html><html>");
		out.println("<head><title>Relatorio de produtos</title></head>");
		out.println("<body>");
		out.println("Relatorio de Produtos:");
		out.println("<ul>");

		int count = 0;

		for(Produto p : produtos) {
			if (estrategiaFiltragem.seleciona(p)) {
				out.print("<li>");

				if ((format_flags & FORMATO_ITALICO) > 0)
					p = new ProdutoItalico(p);

				if ((format_flags & FORMATO_NEGRITO) > 0)
					p = new ProdutoNegrito(p);

				out.print(p.formataParaImpressao());

				out.println("</li>");
				count++;
			}
		}

		out.println("</ul>");
		out.println(count + " produtos listados, de um total de " + produtos.size() + ".");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	public static List<Produto> carregaProdutos() {
		List<Produto> lista = new LinkedList<>();
		Collections.addAll(lista, new Produto[]{

				new ProdutoPadrao(1, "O Hobbit", "Livros", 2, 34.90),
				new ProdutoPadrao(2, "Notebook Core i7", "Informatica", 5, 1999.90),
				new ProdutoPadrao(3, "Resident Evil 4", "Games", 7, 79.90),
				new ProdutoPadrao(4, "iPhone", "Telefonia", 8, 4999.90),
				new ProdutoPadrao(5, "Calculo I", "Livros", 20, 55.00),
				new ProdutoPadrao(6, "Power Glove", "Games", 3, 499.90),
				new ProdutoPadrao(7, "Microsoft HoloLens", "Informatica", 1, 19900.00),
				new ProdutoPadrao(8, "OpenGL Programming Guide", "Livros", 4, 89.90),
				new ProdutoPadrao(9, "Vectrex", "Games", 1, 799.90),
				new ProdutoPadrao(10, "Carregador iPhone", "Telefonia", 15, 499.90),
				new ProdutoPadrao(11, "Introduction to Algorithms", "Livros", 7, 315.00),
				new ProdutoPadrao(12, "Daytona USA (Arcade)", "Games", 1, 12000.00),
				new ProdutoPadrao(13, "Neuromancer", "Livros", 5, 45.00),
				new ProdutoPadrao(14, "Nokia 3100", "Telefonia", 4, 249.99),
				new ProdutoPadrao(15, "Oculus Rift", "Games", 1, 3600.00),
				new ProdutoPadrao(16, "Trackball Logitech", "Informatica", 1, 250.00),
				new ProdutoPadrao(17, "After Burner II (Arcade)", "Games", 2, 8900.0),
				new ProdutoPadrao(18, "Assembly for Dummies", "Livros", 30, 129.90),
				new ProdutoPadrao(19, "iPhone (usado)", "Telefonia", 3, 3999.90),
				new ProdutoPadrao(20, "Game Programming Patterns", "Livros", 1, 299.90),
				new ProdutoPadrao(21, "Playstation 2", "Games", 10, 499.90),
				new ProdutoPadrao(22, "Carregador Nokia", "Telefonia", 14, 89.00),
				new ProdutoPadrao(23, "Placa Aceleradora Voodoo 2", "Informatica", 4, 189.00),
				new ProdutoPadrao(24, "Stunts", "Games", 3, 19.90),
				new ProdutoPadrao(25, "Carregador Generico", "Telefonia", 9, 30.00),
				new ProdutoPadrao(26, "Monitor VGA 14 polegadas", "Informatica", 2, 199.90),
				new ProdutoPadrao(27, "Nokia N-Gage", "Telefonia", 9, 699.00),
				new ProdutoPadrao(28, "Disquetes Maxell 5.25 polegadas (caixa com 10 unidades)", "Informatica", 23,
						49.00),
				new ProdutoPadrao(29, "Alone in The Dark", "Games", 11, 59.00),
				new ProdutoPadrao(30, "The Art of Computer Programming Vol. 1", "Livros", 3, 240.00),
				new ProdutoPadrao(31, "The Art of Computer Programming Vol. 2", "Livros", 2, 200.00),
				new ProdutoPadrao(32, "The Art of Computer Programming Vol. 3", "Livros", 4, 270.00)
		});

		return lista;
	}

	private static void imprimeInstrucoes() {
		System.out.println("Uso:");
		System.out.println("\tjava " + GeradorDeRelatorios.class.getName()
				+ "<arquivo de entrada> <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetros de filtragem>");
		System.out.println("Onde:");
		System.out.println("\tarquivo de entrada: nome do arquivo de entrada");
		System.out.println("\talgoritmo: 'quick' ou 'insertion'");
		System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c' ou 'preco_d' ou 'descricao_d' ou 'estoque_d'");
		System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual' ou 'intervalo_preco' ou 'descricao'");
		System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem");
		System.out.println();
		System.exit(1);
	}

	public static void main(String[] args) {
		if (args.length < 4)
			imprimeInstrucoes();

		String opcao_algoritmo = args[0];
		String opcao_criterio_ord = args[1];
		String opcao_criterio_filtro = args[2];
		List<String> argumentos_filtro = new ArrayList<String>();

		if(args.length > 3) Collections.addAll(argumentos_filtro, Arrays.copyOfRange(args, 3, args.length));

		EstrategiaOrdenacao estrategiaOrdenacao = getEstrategiaOrdenacao(opcao_criterio_ord, opcao_algoritmo);
		EstrategiaFiltragem estrategiaFiltragem = getEstrategiaFiltragem(opcao_criterio_filtro, argumentos_filtro);

		GeradorDeRelatorios gdr = new GeradorDeRelatorios(carregaProdutos(),
				estrategiaOrdenacao,
				estrategiaFiltragem,
				argumentos_filtro);

		try {
			gdr.geraRelatorio("saida.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static EstrategiaFiltragem getEstrategiaFiltragem(String opcao_criterio_filtro, List<String> argumentos_filtro) {
		EstrategiaFiltragem estrategia = switch (opcao_criterio_filtro) {
			case FILTRO_TODOS -> new EstrategiaTodos();
			case FILTRO_CATEGORIA_IGUAL_A -> new EstrategiaCategoria();
			case FILTRO_ESTOQUE_MENOR_OU_IGUAL_A -> new EstrategiaEstoqueMenorIgual();
			case FILTRO_INTERVALO_PRECO -> new EstrategiaIntervaloPreco();
			case FILTRO_DESCRICAO -> new EstrategiaDescricao();

			default -> throw new RuntimeException("Filtro invalido!");
		};

		if (!argumentos_filtro.isEmpty())
			estrategia.setArgs(argumentos_filtro);

		return estrategia;
	}

	private static EstrategiaOrdenacao getEstrategiaOrdenacao(String opcao_criterio_ord, String opcao_algoritmo) {
		EstrategiaCriterio estrategiaCriterioOrd = switch (opcao_criterio_ord) {
			case CRIT_DESC_CRESC -> new EstrategiaDescCres();
			case CRIT_PRECO_CRESC -> new EstrategiaPrecoCres();
			case CRIT_ESTOQUE_CRESC -> new EstrategiaEstoqueCres();
			case CRIT_DESC_DEC -> new EstrategiaDescDec();
			case CRIT_PRECO_DEC -> new EstrategiaPrecoDec();
			case CRIT_ESTOQUE_DEC -> new EstrategiaEstoqueDec();
			default -> throw new RuntimeException("Criterio de ordenacao invalido!");
		};

		EstrategiaOrdenacao estrategiaOrdenacao = switch (opcao_algoritmo) {
			case ALG_INSERTIONSORT -> new EstrategiaInsertionSort();
			case ALG_QUICKSORT -> new EstrategiaQuicksort();
			default -> throw new RuntimeException("Algoritmo de ordenacao invalido!");
		};

		estrategiaOrdenacao.setEstrategiaCriterio(estrategiaCriterioOrd);
		return estrategiaOrdenacao;
	}
}
