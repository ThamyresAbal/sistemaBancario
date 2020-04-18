package bancodk;

import java.util.Scanner;
import negocio.Conta;
//o leandro esteve aqui 

public class TestaBancoDK {

    public static void main(String[] args) {
        //leitura
        Scanner ler = new Scanner(System.in);
        final int FLAG = 0;
        int menu = -1;

        Conta contasDK = new Conta();
        //menu principal
        while (menu != FLAG) {
            System.out.println("Selecione uma opção: ");
            System.out.println("1- Inclusão de cliente.");
            System.out.println("2- Alteração de cliente.");
            System.out.println("3- Exclusão de cliente.");
            System.out.println("4- Relatórios gerenciais.");
            System.out.println("0- Sair");
            menu = ler.nextInt();
            if ((menu >= FLAG) && (menu <= 4)) {
                switch (menu) {
                    case 1:
                        System.out.println("Digite o número da conta:");
                        contasDK.setNumeroConta(ler.next());
                        System.out.println("Digite o nome do cliente:");
                        contasDK.setNomeCliente(ler.next());
                        System.out.println("Digite o saldo da conta corrente:");
                        contasDK.setSaldoContaCorrente(ler.nextFloat());
                        System.out.println("Digite1 o saldo da conta poupança:");
                        contasDK.setSaldoContaPoupanca(ler.nextFloat());

                        //chamada do método de incluir cliente;
                        contasDK.incluirCliente();
                        break;
                    case 2:
                        System.out.println("Digite o número da conta para alterar:");
                        contasDK.setNumeroConta(ler.next());
                        System.out.println("Digite as seguintes informações: ");
                        System.out.println("Primeiro nome do cliente:");
                        contasDK.setNomeCliente(ler.next());
                        System.out.println("Saldo da conta corrente:");
                        contasDK.setSaldoContaCorrente(ler.nextFloat());
                        System.out.println("Saldo da conta poupança:");
                        contasDK.setSaldoContaPoupanca(ler.nextFloat());

                        //chamada do método de alterar dados da conta selecionado
                        contasDK.alterarConta();
                        break;
                    case 3:
                        System.out.println("Digite o número da conta para excluir:");
                        contasDK.setNumeroConta(ler.next());

                        //chamada do método de excluir conta selecionada
                        contasDK.excluirConta();
                        break;
                    case 4:

                        int menuRelatorio = -1;
                        System.out.println("1- Lista de clientes com saldo negativo.");
                        System.out.println("2- lista de clientes com saldo acima de um valor.");
                        System.out.println("3- Lista de todas as contas.");
                        System.out.println("4- Três maiores saldos de conta corrente");
                        menuRelatorio = ler.nextInt();
                        if ((menuRelatorio >= FLAG) && (menuRelatorio <= 4)) {
                            switch (menuRelatorio) {
                                case 1:
                                    contasDK.listaSaldoNegativo();
                                    break;
                                case 2:
                                    System.out.println("Digite o valor de pesquisa:");
                                    contasDK.listarSaldosAcimaDoValor(ler.nextFloat());
                                    break;
                                case 3:
                                    contasDK.listarTodasContas();
                                    break;
                                case 4:
                                    contasDK.maioresSaldosContaCorrente();
                            }
                        } else {
                            System.out.println("Opção inválida!");
                        }
                }
            } else {
                System.out.println("Opção inválida!");
            }
        }
    }
}
