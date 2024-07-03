import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.FileNameMap;
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

	public static List<Produto> carregaProdutos(String arq_entrada) {
		List<Produto> lista = new ArrayList<>();
        try (Scanner in = new Scanner(new File(arq_entrada))) {
			in.nextLine();
			while(in.hasNextLine()) {
				String linha = in.nextLine();
				String[] dados = linha.split(", ");

				int id = Integer.parseInt(dados[0]);
				String desc = dados[1];
				String categoria = dados[2];
				int qtd = Integer.parseInt(dados[3]);
				double preco = Double.parseDouble(dados[4]);
				boolean negrito = Boolean.parseBoolean(dados[5]);
				boolean italico = Boolean.parseBoolean(dados[6]);
				String cor = dados[7];

				Produto produto = new ProdutoPadrao(id, desc, categoria, qtd, preco);

				if(negrito) produto = new ProdutoNegrito(produto);
				if(italico) produto = new ProdutoItalico(produto);
				if(!cor.isEmpty()) produto = new ProdutoCor(produto, cor);

				lista.add(produto);
			}
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo de entrada nao encontrado!");
        }

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

		String arq_entrada = args[0];
		String opcao_algoritmo = args[1];
		String opcao_criterio_ord = args[2];
		String opcao_criterio_filtro = args[3];
		List<String> argumentos_filtro = new ArrayList<String>();

		if(args.length > 4) Collections.addAll(argumentos_filtro, Arrays.copyOfRange(args, 4, args.length));

		EstrategiaOrdenacao estrategiaOrdenacao = getEstrategiaOrdenacao(opcao_criterio_ord, opcao_algoritmo);
		EstrategiaFiltragem estrategiaFiltragem = getEstrategiaFiltragem(opcao_criterio_filtro, argumentos_filtro);

		GeradorDeRelatorios gdr = new GeradorDeRelatorios(carregaProdutos(arq_entrada),
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
