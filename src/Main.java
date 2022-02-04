package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * Funciona como ponto de partida para o programa e contém um menu que irá utilizar para consultar os diferentes pontos do enunciado.
     */
    public static void main(String[] args) {
        int num;
        Scanner res = new Scanner(System.in);

        while(true){
            System.out.print("Alterou os dados nos ficheiros texto? ");
            String resp = res.nextLine();
            if(resp.equalsIgnoreCase("sim")){
                atualizaGrps(lerFichGrpsInvs(),lerFichInvs(),lerFichPubs());
                break;
            }
            else if(resp.equalsIgnoreCase("nao")){
                System.out.print("AVISO: Se tiver atualizado os ficheiros de texto a informação não será igual à que está nos ficheiros de objetos\n\n");
                break;
            }
            else{
                System.out.print("Introduza uma resposta válida. ");
            }
        }

        Menu menu = new Menu(lerFichObjetos());
        System.out.print("Bem vindo ao CISUC!\n");
        System.out.print(" 1 - Apresentar os indicadores gerais do CISUC:\n");
        System.out.print(" 2 - Listar as publicações de um grupo de investigação, dos últimos 5 anos, organizadas por ano,por tipo de publicaçãoe por fator de impacto\n");
        System.out.print(" 3 - Listar os membros de um grupo de investigação agrupados por categoria\n");
        System.out.print(" 4 - Listar as publicações de um investigador agrupadas por ano, tipo de publicações fator de impacto\n");
        System.out.print(" 5 - Listar todos os grupos de investigação e apresentar informação à cerca deles\n");
        System.out.print(" 0 - Sair\n");
        while(true) {
            System.out.print("\nIndique o número da operação que pretende realizar: ");
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                if (num == 1) {
                    menu.s1();
                } else if (num == 2) {
                    menu.s2();
                } else if (num == 3) {
                    menu.s3();
                } else if (num == 4) {
                    menu.s4();
                } else if (num == 5) {
                    menu.s5();
                } else if (num == 0) {
                    System.exit(0);
                } else {
                    System.out.print("Número inválido\n");
                }
            }
            else{
                System.out.print("Por favor introduza um número\n");
            }
            System.out.print("\nContinuando no CISUC..\n");
            System.out.print(" 1 - Apresentar os indicadores gerais do CISUC:\n");
            System.out.print(" 2 - Listar as publicações de um grupo de investigação, dos últimos 5 anos, organizadas por ano,por tipo de publicaçãoe por fator de impacto\n");
            System.out.print(" 3 - Listar os membros de um grupo de investigação agrupados por categoria\n");
            System.out.print(" 4 - Listar as publicações de um investigador agrupadas por ano, tipo de publicações fator de impacto\n");
            System.out.print(" 5 - Listar todos os grupos de investigação e apresentar informação à cerca deles\n");
            System.out.print(" 0 - Sair\n");
        }

    }

    /** Retorna um ArrayList do tipo GrupoInvest com informação dos Grupos de Investigação presentes no ficheiro: GruposInvs.txt
     * Não recebe qualquer tipo de parâmetro.
     * Lê o que está no ficheiro de texto referente aos grupos de investigação(GruposInvs.txt) e
     * cria um ArrayList do tipo GrupoInvest.
     * No final retorna esse ArrayList.
     * @return ArrayList do tipo GrupoInvest
     */
    public static ArrayList<GrupoInvest> lerFichGrpsInvs(){
        String nomeGrps = "GruposInvs.txt";

        File f = new File(nomeGrps);

        ArrayList<GrupoInvest> grupos=new ArrayList<>();
        if(f.exists() && f.isFile()){
            try{
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String line;

                int linha=1;

                while((line=br.readLine())!=null){
                    if(!line.isEmpty()){
                        String [] grp=line.split("\\|");
                        GrupoInvest grpInv= new GrupoInvest(grp[0],grp[1]);
                        //System.out.print(info[0]+ info[1]+"\n");
                        String [] leader=grp[2].split("-");
                        MembEfet chefe=new MembEfet(leader[0],leader[1],grpInv,parseErro(leader[2],linha),parseErro(leader[3],linha));
                        grpInv.setMembEfet(chefe);
                        grpInv.addInvest(chefe);
                        grupos.add(grpInv);
                    }
                }
            }
            catch (FileNotFoundException ex){
                System.out.print("Erro a abrir o ficheiro de texto\n");
            }
            catch (IOException ex){
                System.out.print("Erro a ler o ficheiro de texto\n");
            }
        }
        else{
            System.out.print("Ficheiro não existe\n");
        }
        return grupos;
    }

    /** Retorna um ArrayList do tipo Investigador com a informação dos Investigadores presentes no ficheiro: Invs.txt
     * Não recebe qualquer tipo de parâmetro.
     * Lê o que está no ficheiro de texto referente aos investigadores(Invs.txt) e cria um ArrayList do tipo Investigador.
     * No final retorna esse ArrayList.
     * @return ArrayList do tipo Investigador
     */
    public static ArrayList<Investigador> lerFichInvs(){
        String nomeGrps = "Invs.txt";

        ArrayList<Investigador> invs= new ArrayList<>();

        File f = new File(nomeGrps);

        if(f.exists() && f.isFile()){
            try{
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String line;

                int linha=1;

                while((line=br.readLine())!=null){
                    if(!line.isEmpty()){
                        String [] inv =line.split("-");
                        if(inv.length==5){
                            GrupoInvest grp= procGrp(lerFichGrpsInvs(), inv[2]);
                            MembEfet membEfet = new MembEfet(inv[0],inv[1],grp,parseErro(inv[3],linha),parseErro(inv[4],linha));
                            invs.add(membEfet);
                        }
                    }
                    linha++;
                }

                fr = new FileReader(f);
                br = new BufferedReader(fr);
                linha=1;

                while((line=br.readLine())!=null){
                    if(!line.isEmpty()){
                        String [] inv =line.split("-");
                        if(inv.length==6){
                            GrupoInvest grp= procGrp(lerFichGrpsInvs(), inv[2]);
                            String [] d = inv[4].split(",");
                            Data data = new Data(parseErro(d[0],linha), parseErro(d[1],linha),parseErro(d[2],linha));
                            if(grp!=null) {
                                Investigador tutor = procInv(invs, inv[5]);
                                if(tutor!=null) {
                                    Estudante estudante = new Estudante(inv[0], inv[1], grp, inv[3], data, tutor);
                                    invs.add(estudante);
                                }
                                else{
                                    System.out.print("Estudante invalido na linha "+linha);
                                }
                            }
                            else{
                                System.out.print("Estudante invalido na linha "+linha);
                            }
                        }
                    }
                    linha++;
                }

                fr.close();
                br.close();
            }
            catch (FileNotFoundException ex){
                System.out.print("Erro a abrir o ficheiro de texto\n");
            }
            catch (IOException ex){
                System.out.print("Erro a ler o ficheiro de texto\n");
            }
        }
        else{
            System.out.print("Ficheiro não existe\n");
        }
        return invs;
    }

    /** Retorna um ArrayList do tipo Publicacao com a informação das Publicações presentes no ficheiro: Pubs.txt
     * Não recebe qualquer tipo de parâmetro.
     * Lê o que está no ficheiro de texto referente às publicações(Pubs.txt) e cria um ArrayList do tipo Publicacao.
     * No final retorna esse ArrayList.
     * @return ArrayList do tipo Publicacao
     */
    public static ArrayList<Publicacao> lerFichPubs(){
        String nomeGrps = "Pubs.txt";

        ArrayList<Publicacao> pubs= new ArrayList<>();

        File f = new File(nomeGrps);

        if(f.exists() && f.isFile()){
            try{
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String line;

                int linha=1;

                while((line=br.readLine())!=null){
                    if(!line.isEmpty()){
                        String [] pub = line.split("-");
                        if(pub[3].equalsIgnoreCase("Artigo de Conferencia")){
                            String [] d = pub[8].split(",");
                            Data data = new Data(parseErro(d[0],linha), parseErro(d[1],linha), parseErro(d[2],linha));
                            ArtigoConferencia ac = new ArtigoConferencia(pub[0],pub[2].split(","),parseErro(pub[4],linha),parseErro(pub[5],linha),pub[6],new Conferencia(pub[7],data,pub[9]));
                            pubs.add(ac);
                            for(String inv:pub[1].split(",")){
                                Investigador aut=procInv(lerFichInvs(),inv);
                                ac.addAutores(aut);
                            }
                        }
                        else if(pub[3].equalsIgnoreCase("Artigo de Revista")){
                            String [] d = pub[8].split(",");
                            Data data = new Data(parseErro(d[0],linha), parseErro(d[1],linha), parseErro(d[2],linha));
                            ArtigoRevista ar = new ArtigoRevista(pub[0],pub[2].split(","),parseErro(pub[4],linha),parseErro(pub[5],linha),pub[6],new Revista(pub[7],data,parseErro(pub[9],linha)));
                            pubs.add(ar);
                            for(String inv:pub[1].split(",")){
                                Investigador aut=procInv(lerFichInvs(),inv);
                                ar.addAutores(aut);
                            }
                        }
                        else if(pub[3].equalsIgnoreCase("Livro")){
                            Livro l = new Livro(pub[0],pub[2].split(","),parseErro(pub[4],linha),parseErro(pub[5],linha),pub[6],new InfoLivro(pub[7],parseErro(pub[8],linha)));
                            pubs.add(l);
                            for(String inv:pub[1].split(",")){
                                Investigador aut=procInv(lerFichInvs(),inv);
                                l.addAutores(aut);
                            }
                        }
                        else if(pub[3].equalsIgnoreCase("Capitulo de Livro")){
                            String [] p = pub[10].split(",");
                            int [] pags = new int[] {parseErro(p[0],linha),parseErro(p[1],linha)};
                            CapituloLivro cl = new CapituloLivro(pub[0],pub[2].split(","),parseErro(pub[4],linha),parseErro(pub[5],linha),pub[6],new InfoLivro(pub[7],parseErro(pub[8],linha)),pub[9],pags[0],pags[1]);
                            pubs.add(cl);
                            for(String inv:pub[1].split(",")){
                                Investigador aut=procInv(lerFichInvs(),inv);
                                cl.addAutores(aut);
                            }
                        }
                        else if(pub[3].equalsIgnoreCase("Livro de Conferencias")){
                            LivroConferencia lc = new LivroConferencia(pub[0],pub[2].split(","),parseErro(pub[4],linha),parseErro(pub[5],linha),pub[6],new InfoLivro(pub[7],parseErro(pub[8],linha)),pub[9],parseErro(pub[10],linha));
                            pubs.add(lc);
                            for(String inv:pub[1].split(",")){
                                Investigador aut=procInv(lerFichInvs(),inv);
                                lc.addAutores(aut);
                            }
                        }
                        else{
                            System.out.print("Publicação Inválida");
                        }

                    }
                }
            }
            catch (FileNotFoundException ex){
                System.out.print("Erro a abrir o ficheiro de texto\n");
            }
            catch (IOException ex){
                System.out.print("Erro a ler o ficheiro de texto\n");
            }
        }
        else{
            System.out.print("Ficheiro não existe\n");
        }
        //System.out.print(pubs.size());
        return pubs;
    }

    /** Cria um objeto CISUC apartir dos inputs e escreve esse objeto num ficheiro de objetos(CISUC.txt).
     * Recebe um ArrayList de GrupoInvest, outro de Investigador e outro de Publicacao.
     * @param grps ArrayList do tipo GrupoInvest a escrever
     * @param invs ArrayList do tipo Investigador a escrever
     * @param pubs ArrayList do tipo Publicacao a escrever
     */
    public static void escreverFichObjetos(ArrayList<GrupoInvest> grps, ArrayList<Investigador> invs, ArrayList<Publicacao> pubs){
        String nome="CISUC.txt";

        try{
            FileOutputStream f = new FileOutputStream(new File(nome));
            ObjectOutputStream fos = new ObjectOutputStream(f);

            fos.writeObject(new CISUC(grps,invs,pubs));

            fos.close();
            f.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Ficheiro não encontrado");
        }
        catch (IOException e){
            System.out.println("Erro ao inicializar");
        }
    }

    /** Retorna um objeto do tipo CISUC com a informação à cerca dos Grupos de Investigadores, seus Investigadores e as suas Publicações presente no ficheiro de Objetos: CISUC.txt
     * Não recebe qualquer tipo de parâmetro.
     * Apenas serve para ler o que está no ficheiro de objetos(CISUC.txt), neste caso um objeto do tipo CISUC.
     * @return Um objeto CISUC
     */
    public static CISUC lerFichObjetos(){
        String nome="CISUC.txt";
        CISUC cisuc = null;

        try{
            FileInputStream f = new FileInputStream(new File(nome));
            ObjectInputStream fos = new ObjectInputStream(f);

            cisuc = (CISUC) fos.readObject();
        }
        catch (EOFException ignored){
        }
        catch(FileNotFoundException e){
            System.out.println("AVISO: Ficheiro de Objetos não encontrado, a informação transmitida virá dos ficheiros de texto\n");
            atualizaGrps(lerFichGrpsInvs(),lerFichInvs(), lerFichPubs());
            cisuc=lerFichObjetos();
        }
        catch (IOException e){
            System.out.println("Erro ao inicializar");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(cisuc==null){
            System.out.print("Ficheiro vazio, dados serão lidos dos ficheiros de texto\n");
            atualizaGrps(lerFichGrpsInvs(),lerFichInvs(), lerFichPubs());
            cisuc=lerFichObjetos();
        }

        return cisuc;
    }

    /** Função usada para procurar um Investigador através do seu nome num determinado ArrayList do tipo Investigador.
     * Se não encontrar o investigador devolve null e é apresentada uma mensagem de aviso a dizer que não foi
     * encontrado.
     * Recebe um ArrayList de Investigadores e o nome desse Investigador que pretendemos procurar.
     * @param invs ArrayList do tipo Investigador onde procurar
     * @param nome Nome do Investigador que se pretende procurar
     * @return Investigador que se pretende encontrar
     */
    public static Investigador procInv(ArrayList<Investigador> invs,String nome){
        for(Investigador inv: invs){
            if(inv.nome.equalsIgnoreCase(nome)){
                return inv;
            }
        }
        System.out.print("Investigador não encontrado\n");

        return null;
    }

    /** Função usada para procurar um GrupoInvest através do seu acronimo num determinado ArrayList do tipo GrupoInvest.
     * Se não encontrar o GrupoInvest devolve null e é apresentada uma mensagem de aviso a dizer que não foi
     * encontrado.
     * Recebe um ArrayList de GrupoInvest e o acronimo do grupo que pretendemos procurar.
     * @param grupos ArrayList do tipo GrupoInvest onde procurar
     * @param acronimo Acronimo do grupo que se pretende encontrar
     * @return GrupoInvest que se pretende encontrar
     */
    public static GrupoInvest procGrp(ArrayList<GrupoInvest> grupos,String acronimo){
        for(GrupoInvest grupoInvest: grupos){
            if(grupoInvest.getAcronimo().equalsIgnoreCase(acronimo)){
                return grupoInvest;
            }
        }
        System.out.print("Grupo de investigação não encontrado\n");

        return null;
    }

    /** Tenta converter a String lida em inteiro e se não for possvel da return de 0 e apresenta uma mensagem
     * de erro.
     * Recebe uma String e uma respetiva linha, visto que esta função é suposta funcionar com
     * ficheiro a linha será a linha onde estará a tal String.
     * @param t String a ser convertida em inteiro
     * @param linha Linha onde se encontra a string
     * @return Inteiro obtido após a conversão da string
     */
    public static int parseErro(String t, int linha){
        try{
            return Integer.parseInt(t);
        }
        catch (NumberFormatException e){
            System.out.printf("Erro a converter o número na %dª linha\n, será retornado 0", linha);
            return 0;
        }
    }

    /** Esta funçao irá colocar cada investigador no seu devido grupo, no final irá escrever num
     * ficheiro de objetos(CISUC.txt) os ArrayLists recebido com a diferença que cada GrupoInvest
     * têm agora membros no seu ArrayList de Investigadores.
     * Recebe um ArrayList de GrupoInvest, outro de Investigador e outro de Publicacao.
     * @param grps ArrayList do tipo GrupoInvest
     * @param invs ArrayList do tipo Investigador
     * @param pubs ArrayList do tipo Publicacao
     */
    public static void atualizaGrps(ArrayList<GrupoInvest> grps, ArrayList<Investigador> invs, ArrayList<Publicacao> pubs){
        for(GrupoInvest grp: grps){
            for(Investigador inv: invs){
                if(grp.compareTo(inv.getGrupoInvest())){
                    grp.addInvest(inv);
                }
            }
            invs.add(grp.getMembEfet());
        }
        escreverFichObjetos(grps,invs,pubs);
    }
}