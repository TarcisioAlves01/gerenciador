package br.edu.ifce.gerenciador.servlet;

import br.edu.ifce.gerenciador.acao.Acao;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifce.gerenciador.acao.AlteraEmpresa;
import br.edu.ifce.gerenciador.acao.ListaEmpresas;
import br.edu.ifce.gerenciador.acao.MostraEmpresa;
import br.edu.ifce.gerenciador.acao.NovaEmpresa;
import br.edu.ifce.gerenciador.acao.RemoveEmpresa;
import javax.servlet.RequestDispatcher;

@WebServlet("/entrada")
public class UnicaEntradaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

     protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String paramAcao = request.getParameter("acao");
        String nomeDaAcao = null;
        String nomeDaClasse = "br.edu.ifce.gerenciador.acao."+paramAcao;
        
        Acao acao;
        try {
            Class classe = Class.forName(nomeDaClasse);
            acao = (Acao) classe.newInstance();
            nomeDaAcao = acao.executa(request, response);
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex){
             throw new ServletException(ex);
        }
        
        String[] tipoDeEndereco = nomeDaAcao.split(":");
        
        if(tipoDeEndereco[0].equals("forward")){
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/"+tipoDeEndereco[1]);
            rd.forward(request, response);
        }else{
            response.sendRedirect(tipoDeEndereco[1]);
        }    
    }

}
