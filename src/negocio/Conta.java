package negocio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Conta {

    private String nomeCliente;
    private String numeroConta;
    private float saldoContaCorrente;
    private float saldoContaPoupanca;
    private String[] vetorConta;

    final String ARQUIVOCONTAS = "C:\\Users\\Milo\\Documents\\NetBeansProjects\\BancoDK\\contas.txt";

    public static void escreverArquivo(Scanner arquivoEntrada, String path, String conteudo) {
        BufferedWriter buffEscrita = null;
        try {
            buffEscrita = new BufferedWriter(new FileWriter(path));
            buffEscrita.append(conteudo);
            buffEscrita.close();

        } catch (IOException e) {
            System.out.println("Erro de escrita:" + e.getMessage());
        }
    }

    public static Scanner abreArquivoContas(String arquivoContas) {
        Scanner arquivoEntrada = null;
        try {
            arquivoEntrada = new Scanner(new File(arquivoContas));
        } catch (FileNotFoundException e) {
            System.err.printf("Erro na abertura do arquivo: %s\n",
                    e.getMessage());
        }
        return arquivoEntrada;
    }

    public static void lerArquivo(Scanner arquivoEntrada) {
        try {
            while (arquivoEntrada.hasNext()) {
                System.out.println(arquivoEntrada.next());
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro na leitura do arquivo:" + e.getMessage());
        }
    }

    public static void fecharAquivo(Scanner arquivoEntrada) {
        if (arquivoEntrada != null) {
            arquivoEntrada.close();
        }
    }

    public void incluirCliente() {
        Scanner entrada = abreArquivoContas(ARQUIVOCONTAS);
        String temporaria = "";
        if ((getSaldoContaCorrente() > 0) && (getSaldoContaPoupanca() > 0)) {
            try {
                while (entrada.hasNextLine()) {
                    temporaria += entrada.nextLine() + System.getProperty("line.separator");
                    if (temporaria.contains(getNumeroConta())) {
                        System.out.println("Conta já existe!");
                        break;
                    }
                }
                if (!entrada.hasNextLine()) {
                    temporaria += getNumeroConta() + " " + getNomeCliente() + " " + getSaldoContaCorrente() + " " + getSaldoContaPoupanca();
                    escreverArquivo(entrada.reset(), ARQUIVOCONTAS, temporaria);
                    System.out.println("Conta incluída com sucesso.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Erro na leitura do arquivo:" + e.getMessage());
            }
        } else {
            System.out.println("Digite saldos diferentes de 0.");
        }
    }

    public void alterarConta() {
        Scanner entrada = abreArquivoContas(ARQUIVOCONTAS);
        String temporaria = "";
        String tempLine;
        boolean naoExisteConta = true;
        try {
            while (entrada.hasNextLine()) {
                tempLine = entrada.nextLine();
                if (tempLine.contains(getNumeroConta())) {
                    temporaria += getNumeroConta() + " " + getNomeCliente() + " " + getSaldoContaCorrente() + " " + getSaldoContaPoupanca() + System.getProperty("line.separator");
                    System.out.println("Conta encontrada! Conta alterada com sucesso!");
                    naoExisteConta = false;
                } else {
                    temporaria += tempLine + System.getProperty("line.separator");
                }
            }
            if (naoExisteConta == true) {
                System.out.println("Conta não existe! Confira seus dados!");
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro na leitura do arquivo:" + e.getMessage());
        }
        escreverArquivo(entrada.reset(), ARQUIVOCONTAS, temporaria);
    }

    public void excluirConta() {
        Scanner entrada = abreArquivoContas(ARQUIVOCONTAS);
        String temporaria = ""; 
        String tempLine;
        try {
            while (entrada.hasNextLine()) {
                tempLine = entrada.nextLine();
                if (tempLine.contains(getNumeroConta())) {

                    setVetorConta(tempLine.split(" "));

                    setSaldoContaCorrente(Float.parseFloat(getVetorConta()[2]));
                    setSaldoContaPoupanca(Float.parseFloat(getVetorConta()[3]));

                    if ((getSaldoContaCorrente() == 0) && (getSaldoContaPoupanca() == 0)) {
                        System.out.println("Conta encontrada e excluída com sucesso!");
                    } else {
                        //Conta com saldo diferente de 0, não vai exlcuir.
                        System.out.println("Você ainda possui saldo na conta!");
                        //Escreve dados da conta, pois ela não pode ser excluída
                        temporaria += tempLine + System.getProperty("line.separator");
                    }
                } else {
                    temporaria += tempLine + System.getProperty("line.separator");
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro na leitura do arquivo:" + e.getMessage());
        }
        escreverArquivo(entrada.reset(), ARQUIVOCONTAS, temporaria);
    }

    public void listaSaldoNegativo() {
        Scanner entrada = abreArquivoContas(ARQUIVOCONTAS);
        String tempLine;
        boolean contasPositvo = true;
        try {
            while (entrada.hasNextLine()) {
                tempLine = entrada.nextLine();
                setVetorConta(tempLine.split(" "));
                // vetor conta verifica somente os saldos
                setSaldoContaCorrente(Float.parseFloat(getVetorConta()[2]));

                if (getSaldoContaCorrente() < 0) {
                    System.out.println("Contas com saldo negativo: " + tempLine);
                    contasPositvo = false;
                }
            }
            if(contasPositvo == true){
                System.out.println("Não existe conta com saldo negativo!");
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro na leitura do arquivo:" + e.getMessage());
        }
    }

    public void listarSaldosAcimaDoValor(float busca) {
        Scanner entrada = abreArquivoContas(ARQUIVOCONTAS);
        String tempLine;
        boolean saldoMenor = true;
        try {
            while (entrada.hasNextLine()) {
                tempLine = entrada.nextLine();
                setVetorConta(tempLine.split(" "));
                // vetor conta verifica somente os saldos
                setSaldoContaCorrente(Float.parseFloat(getVetorConta()[2]));

                if (getSaldoContaCorrente() > busca) {
                    System.out.println(busca + tempLine);
                    saldoMenor = false;
                }
            }
            if(saldoMenor == true){
                System.out.println("Não existe conta com o valor acima do digitado.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro na leitura do arquivo:" + e.getMessage());
        }
    }

    public void maioresSaldosContaCorrente() {
        Scanner entrada = abreArquivoContas(ARQUIVOCONTAS);
        String tempLine;
        String conta1 = "";
        String conta2 = "";
        String conta3 = "";
        String contaTemp = "";
        float saldo1 = 0;
        float saldo2 = 0;
        float saldo3 = 0;
        float temp = 0;
        
        try {
            while (entrada.hasNextLine()) {
                tempLine = entrada.nextLine();
                setVetorConta(tempLine.split(" "));
                
                // vetor conta verifica somente os saldos
                setSaldoContaCorrente(Float.parseFloat(getVetorConta()[2]));
                                
                if (getSaldoContaCorrente() > saldo1) {
                    contaTemp = conta1;
                    conta1 = tempLine;
                    temp = saldo1;
                    saldo1 = getSaldoContaCorrente();
                    tempLine = contaTemp;
                    
                    if(temp > saldo2){
                        contaTemp = conta2;
                        conta2 = tempLine;
                        temp = saldo2;
                        saldo2 = getSaldoContaCorrente();
                        tempLine = contaTemp;
                    
                    }else if(temp > saldo3){
                        contaTemp = conta3;
                        conta3 = tempLine;
                        temp = saldo3;
                        saldo3 = getSaldoContaCorrente();
                        tempLine = contaTemp;
                    }
                    
                }else if(getSaldoContaCorrente() > saldo2){
                    conta2 = tempLine;
                    temp = saldo2;
                    saldo2 = getSaldoContaCorrente();
                    
                    if(temp > saldo3){
                        contaTemp = conta3;
                        conta3 = tempLine;
                        temp = saldo3;
                        saldo3 = getSaldoContaCorrente();
                        tempLine = contaTemp;
                    }    
                }else if(getSaldoContaCorrente() > saldo3){
                    conta3 = tempLine;
                    temp = saldo3;
                    saldo3 = getSaldoContaCorrente();
                }
                
                
            }
            System.out.printf("Os três maiores saldos: \n  %s \n %s \n %s", conta1, conta2, conta3);
            
        } catch (IllegalStateException e) {
            System.out.println("Erro na leitura do arquivo:" + e.getMessage());
        }
    }

    public void listarTodasContas() {
        Scanner entrada = abreArquivoContas(ARQUIVOCONTAS);
        try {
            while (entrada.hasNextLine()) {
                System.out.println(entrada.nextLine());
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro na leitura do arquivo:" + e.getMessage());
        }
    }

    public void sairPrograma(Scanner entrada) {
        fecharAquivo(entrada);
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public float getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    public void setSaldoContaCorrente(float saldoContaCorrente) {
        this.saldoContaCorrente = saldoContaCorrente;
    }

    public float getSaldoContaPoupanca() {
        return saldoContaPoupanca;
    }

    public void setSaldoContaPoupanca(float saldoContaPoupanca) {
        this.saldoContaPoupanca = saldoContaPoupanca;
    }

    public String[] getVetorConta() {
        return vetorConta;
    }

    public void setVetorConta(String[] vetorConta) {
        this.vetorConta = vetorConta;
    }
    
}
