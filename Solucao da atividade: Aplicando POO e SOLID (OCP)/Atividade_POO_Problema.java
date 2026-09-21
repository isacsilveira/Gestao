import java.util.ArrayList;
import java.util.List;

/**
 * Atividade de revisão — Conceitos fundamentais de Orientação a Objetos
 * Solução completa desenvolvida para a disciplina de GCVA.
 */

// ===================== PARTE 3 — Interface e Composição =====================

// TODO (3.1): crie uma interface IConsole com três métodos: void ligar(),
// double calcularPreco() e String getNome().
interface IConsole {
    void ligar();
    double calcularPreco();
    String getNome();
}

// TODO (3.2): crie uma classe DadosConsole com os atributos privados nome
// e precoBase, um construtor que os receba, e getters para os dois.
class DadosConsole {
    private String nome;
    private double precoBase;

    public DadosConsole(String nome, double precoBase) {
        this.nome = nome;
        this.precoBase = precoBase;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoBase() {
        return precoBase;
    }
}

// TODO (3.3): crie as classes Nintendo e Playstation implementando IConsole.
// Composição: "ter um" DadosConsole em vez de repetir atributos.
class Nintendo implements IConsole {
    private DadosConsole dados;

    public Nintendo(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    // TODO (3.4): Lógica específica da Nintendo
    @Override
    public void ligar() {
        System.out.println("Nintendo ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.10; // 10% de acréscimo
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }
}

// TODO (3.3) e (4.1): Playstation implementa IConsole.
// O atributo 'dados' é protected para que a subclasse PlaystationPortatil possa acessá-lo.
class Playstation implements IConsole {
    protected DadosConsole dados;

    public Playstation(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    // TODO (3.4): Lógica específica do Playstation
    @Override
    public void ligar() {
        System.out.println("Playstation ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.20; // 20% de acréscimo
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }
}

// ===================== PARTE 4 — Herança =====================

// TODO (4.1): PlaystationPortatil como subclasse de Playstation.
class PlaystationPortatil extends Playstation {

    public PlaystationPortatil(String nome, double precoBase) {
        super(nome, precoBase);
    }

    // TODO (4.2): Sobrescreva (@Override) o método ligar()
    @Override
    public void ligar() {
        System.out.println("Playstation Portátil ligado.");
    }

    // TODO (4.3): Sobrescreva (@Override) o método calcularPreco() com percentual de 15%
    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.15;
    }
}

// ===================== PARTE 5 — Polimorfismo e Extensibilidade (OCP) =====================

// TODO (5.4): crie uma nova classe Xbox implementando IConsole (percentual de 18%),
// SEM alterar nenhuma linha da classe Loja.
class Xbox implements IConsole {
    private DadosConsole dados;

    public Xbox(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    @Override
    public void ligar() {
        System.out.println("Xbox ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.18; // 18% de acréscimo
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }
}

// Classe Loja reescrita para aceitar o contrato IConsole
class Loja {

    // TODO (5.1): reescreva venderConsole() para receber um IConsole
    public void venderConsole(IConsole console) {
        console.ligar();
        double precoFinal = console.calcularPreco();
        System.out.println(console.getNome() + " -> Preço final: R$ " + precoFinal);
    }

    // TODO (5.2): crie venderVarios(List<IConsole> consoles)
    public void venderVarios(List<IConsole> consoles) {
        for (IConsole console : consoles) {
            venderConsole(console);
        }
    }

    // TODO (5.3): crie calcularFaturamentoTotal(List<IConsole> consoles)
    public double calcularFaturamentoTotal(List<IConsole> consoles) {
        double faturamentoTotal = 0.0;
        for (IConsole console : consoles) {
            faturamentoTotal += console.calcularPreco();
        }
        return faturamentoTotal;
    }
}

// Classe Principal de Execução
public class Atividade_POO_Problema {

    public static void main(String[] args) {

        // TODO (2.2) e (5.5): Criando instâncias com os construtores específicos
        IConsole nintendo = new Nintendo("Nintendo Switch", 2000);
        IConsole playstation = new Playstation("Playstation 5", 3000);
        IConsole portatil = new PlaystationPortatil("Playstation Portátil", 2500);

        Loja loja = new Loja();

        // Criando a lista inicial de consoles
        List<IConsole> consoles = new ArrayList<>();
        consoles.add(nintendo);
        consoles.add(playstation);
        consoles.add(portatil);

        System.out.println("--- PRIMERA RODADA DE VENDAS ---");
        loja.venderVarios(consoles);
        System.out.println("Faturamento Total: R$ " + loja.calcularFaturamentoTotal(consoles));

        System.out.println("\n--- ADICIONANDO O XBOX (DEMONSTRAÇÃO OCP) ---");
        // TODO (5.5): Adicionando Xbox à lista e chamando os métodos novamente
        IConsole xbox = new Xbox("Xbox Series X", 3500);
        consoles.add(xbox);

        loja.venderVarios(consoles);
        System.out.println("Faturamento Total Atualizado: R$ " + loja.calcularFaturamentoTotal(consoles));
    }
}

/* ============================================================================
 * RESPOSTAS DAS QUESTÕES DE REFLEXÃO E DESAFIO FINAL (INCLUÍDAS NO CÓDIGO)
 * ============================================================================
 *
 * REFLEXÃO PARTE 3:
 * Usar DadosConsole (Composição) evita a duplicação dos campos 'nome' e 'precoBase'
 * sem criar uma acoplamento rígido através de herança múltipla ou desnecessária. 
 * Permite reuso de código com alta flexibilidade ("favoreça composição em vez de herança").
 *
 * REFLEXÃO PARTE 4:
 * PlaystationPortatil sobrescreve os métodos ligar() e calcularPreco() apenas para 
 * customizar/especializar o cálculo e a mensagem, mantendo a promessa da interface/superclasse.
 * Isso RESPEITA o Princípio de Substituição de Liskov (LSP).
 * Já lançar UnsupportedOperationException (como em jogarDisco()) VIOLA o LSP, pois a
 * subclasse se recusa a cumprir o contrato e quebra o polimorfismo.
 *
 * DESAFIO FINAL (PARTE 5):
 * Nenhuma linha precisou ser alterada na classe Loja para o Xbox funcionar.
 * Isso demonstra o Princípio Aberto/Fechado (OCP - Open/Closed Principle) do SOLID: 
 * o código está ABERTO para extensão (novos consoles), mas FECHADO para modificação 
 * (as regras da Loja permanecem intocadas).
 * ============================================================================
 */