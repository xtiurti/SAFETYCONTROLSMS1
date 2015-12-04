package br.com.twautomacao.safetycontrolsms.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.text.Selection;
import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.pojo.Comando;

public class ComandosDao {

	public static ArrayList<Comando> COMANDOS_ENDERECO;
	public static ArrayList<Comando> COMANDOS;

	static {

		ArrayList<Comando> comandos = new ArrayList<Comando>();
		Comando l = new Comando("VIVO", "S01", R.drawable.locate);
		l.setDescricao("zap.vivo.com.br,vivo,vivo");
		comandos.add(l);
		Comando lm = new Comando("TIM", "S02", R.drawable.locate_map);
		lm.setDescricao("tim.com,tim,tim");
		comandos.add(lm);
		Comando mb = new Comando("CLARO", "S03", R.drawable.enginec);
		mb.setDescricao("claro.com.br,claro,claro");
		comandos.add(mb);
		Comando md = new Comando("OI", "S04", R.drawable.engine);
		md.setDescricao("gprs.oi.com.br,oiwap,oioioi");
		comandos.add(md);
		Comando ea = new Comando("TIM", "S05", R.drawable.escuta);
		ea.setDescricao("timbrasil.br,tim,tim");
		comandos.add(ea);
		Comando ed = new Comando("LINK 1", "S06", R.drawable.escutac);
		ed.setDescricao("linksol.vivo.com.br,link,link");
		comandos.add(ed);
		Comando tc1 = new Comando("LINK 2", "S07", R.drawable.phone1a);
		tc1.setDescricao("link.claro.br,claro,claro");
		comandos.add(tc1);
		Comando ta1 = new Comando("LINK 3", "S08", R.drawable.phone1d);
		ta1.setDescricao("link.oi.com.br,link,link");
		comandos.add(ta1);
		Comando tc2 = new Comando("LINK 4", "S09", R.drawable.phone2a);
		tc2.setDescricao("linkm2m.tim.br,link,link");
		comandos.add(tc2);
		Comando ta2 = new Comando("LINK 5", "S10", R.drawable.phone2d);
		ta2.setDescricao("vpn.terapar.br");
		comandos.add(ta2);
		Comando tc3 = new Comando("LINK 6", "S11", R.drawable.phone3a);
		tc3.setDescricao("ls.datora.br");
		comandos.add(tc3);

		COMANDOS_ENDERECO = comandos;
	}


	static{
		ArrayList<Comando> comandos = new ArrayList<Comando>();
		Comando l = new Comando("Localizar", "F",R.drawable.locate);
		comandos.add(l);
		Comando lm = new Comando("Localizar com Mapa", "FM",R.drawable.locate_map);
		lm.setDescricao("No SMS de resposta, clique no link para abrir o mapa da localização.");
		comandos.add(lm);
		Comando mb = new Comando("Motor Bloquear", "D",R.drawable.enginec);
		mb.setDescricao("Bloqueia, desliga, o motor ou ignição do veiculo (conforme foi feita a ligação elétrica).");
		comandos.add(mb);
		Comando md = new Comando("Motor Desbloquear", "E",R.drawable.engine);
		md.setDescricao("Desbloqueia, libera, o motor ou ignição do veículo (conforme foi feita a ligação elétrica).");
		comandos.add(md);
		Comando ea = new Comando("Escuta Ativar", "P1",R.drawable.escuta);
		ea.setDescricao("Ativa a função para escutar a conversa interna. \n Apos ativa a funçao basta fazer uma ligação telefônica para o rastreador para abrir a escuta.");
		comandos.add(ea);
		Comando ed = new Comando("Escuta Cancelar", "P0",R.drawable.escutac);
		ed.setDescricao("Desativa a função escuta.");
		comandos.add(ed);
		Comando tc1 = new Comando("1- Telefone Cadastrar", "A1",R.drawable.phone1a);
		tc1.setDescricao("Cadastra na memória do rastreador o telefone na posição 1.");
		comandos.add(tc1);
		Comando ta1 = new Comando("1- Telefone Apagar", "A0",R.drawable.phone1d);
		ta1.setDescricao("Apaga da memória do rastreador o telefone na posição 1.");
		comandos.add(ta1);
		Comando tc2 = new Comando("2- Telefone Cadastrar", "B1",R.drawable.phone2a);
		tc2.setDescricao("Cadastra na memória do rastreador o telefone na posição 2.");
		comandos.add(tc2);
		Comando ta2 = new Comando("2- Telefone Apagar", "B0",R.drawable.phone2d);
		ta2.setDescricao("Apaga da memória do rastreador o telefone na posição 2.");
		comandos.add(ta2);
		Comando tc3 = new Comando("3- Telefone Cadastrar", "C1",R.drawable.phone3a);
		tc3.setDescricao("Cadastra na memória do rastreador o telefone na posição 3.");
		comandos.add(tc3);
		Comando ta3 = new Comando("3- Telefone Apagar", "C0",R.drawable.phone3d);
		ta3.setDescricao("Apaga da memória do rastreador o telefone na posição 3.");
		comandos.add(ta3);
		Comando sa = new Comando("Senha Alterar", "G",R.drawable.password);
		sa.setDescricao("Altera a senha do rastreador.");
		comandos.add(sa);
		Comando ca = new Comando("Cerca Ativar", "I+X,Y,Z,ddd+T",R.drawable.fence);
		ca.setDescricao("Ativa e configura a cerca eletrônica.\n"+
				"É possível configurar 3 cercas eletrônicas diferentes.\n"+
				"Para configurar a posição da cerca eletrônica, o veículo deverá estar na posição desejada, pois pegará a posição da localização no momento da configuração do comando.\n"+
				"A distancia mínima de configuração é 100 metros.");
		comandos.add(ca);
		Comando cc = new Comando("Cerca Cancelar", "I+X,Y",R.drawable.fencec);
		cc.setDescricao("Cancela a cerca eletrônica desejada.");
		comandos.add(cc);
		Comando va = new Comando("Vibração Ativar", "W1,xxT",R.drawable.vibrate);
		va.setDescricao("Ativa o alerta de vibração.\n" +
				"Quando o rastreador vibrar, enviará um SMS de alerta.\n" +
				"O intervalo de tempo programado será o tempo que o rastreador \"esperará\" para enviar novamente um novo alerta de vibração.\n" +
				"O tempo passa a contar assim que o rastreador estiver totalmente sem vibração.\n");
		comandos.add(va);
		Comando vd = new Comando("Vibração Cancelar", "W0",R.drawable.vibraten);
		vd.setDescricao("Cancela o alerta de vibração.");
		comandos.add(vd);
		Comando eca = new Comando("Econômia Bateria Ativar", "SP1",R.drawable.battery_econ);
		eca.setDescricao("Ativa a função para economizar bateria em motocicletas.\n" +
				"Economiza 50% do consumo do rastreador.\n" +
				"Ative essa função apenas em motocicletas ou jetsky.");
		comandos.add(eca);
		Comando ecd = new Comando("Econômia Bateria Cancelar", "SP0",R.drawable.battery_no);
		ecd.setDescricao("Cancela a função para economizar bateria em motocicletas.");
		comandos.add(ecd);
		Comando vela = new Comando("Velocidade Ativar", "J1+VELOCIDADE",R.drawable.speed);
		vela.setDescricao("Ativa o aviso de excesso de velocidade.\n" +
				"Apos o rastreador ultrapassar a velocidade programada o rastreador enviar o SMS de alerta.");
		comandos.add(vela);
		Comando veld = new Comando("Velocidade Cancelar", "J0",R.drawable.speedc);
		veld.setDescricao("Cancela o aviso de excesso de velocidade.");
		comandos.add(veld);
		Comando spa = new Comando("Salva Posição Ativar", "K1",R.drawable.saveposition);
		spa.setDescricao("Salva na memória a última localização, apos 10 minutos sem ter a localização do sinal do satélite GPS.");
		comandos.add(spa);
		Comando spd = new Comando("Salva Posição Cancelar", "K0",R.drawable.savepositionc);
		spd.setDescricao("Não salva na memória a última posição da localização.");
		comandos.add(spd);
		Comando cba = new Comando("Corta Bateria Ativar", "N1",R.drawable.battery);
		cba.setDescricao("Avisa por SMS quando a bateria principal do veículo é retirada, desconectada, desligada.");
		comandos.add(cba);
		Comando cbd = new Comando("Corta Bateria Cancelar", "N0",R.drawable.bateryc);
		cbd.setDescricao("Cancela o aviso de bateria retirada, desconectada, desligada.");
		comandos.add(cbd);
		Comando aja = new Comando("Anti Jammer Ativar", "DR1",R.drawable.jammer);
		aja.setDescricao("Ativa a função anti Jammer.");
		comandos.add(aja);
		Comando ajd = new Comando("Anti Jammer Cancelar", "DR0",R.drawable.jammern);
		ajd.setDescricao("Cancela a função anti Jammer.");
		comandos.add(ajd);
		Comando lga = new Comando("Localizar GSM Ativar", "GSM1",R.drawable.gsm);
		lga.setDescricao("Ativa a localização GSM caso não tenha sinal GPS."+
				"A localização GSM não é precisa, pois é realizada pela triangulação das antenas de celular, serve apenas como referencia.");
		comandos.add(lga);
		Comando lgd = new Comando("Localizar GSM Cancelar", "GSM0",R.drawable.gsmc);
		lgd.setDescricao("Cancela a localização GSM.");
		comandos.add(lgd);
		Comando csa = new Comando("Conecta Site Ativar", "S",R.drawable.connect);
		csa.setDescricao("Configura o rastreador com o apn,usu�rio e senha da operadora do chip do rastreador.\n" +
				"Essa configuração �é fundamental para o rastreador poder conectar na rede gprs e enviar as informações para o site de rastreamento.");
		comandos.add(csa);
		Comando csa2 = new Comando("Conecta Site Ativar 2", "S",R.drawable.connect);
		csa2.setDescricao("Configura o rastreador com o apn,usu�rio e senha da operadora do chip do rastreador.\n" +
				"Essa configuração é fundamental para o rastreador poder conectar na rede gprs e enviar as informações para o site de rastreamento");
		comandos.add(csa2);
		Comando csc = new Comando("Conecta Site Cancelar", "S0",R.drawable.connectc);
		csc.setDescricao("O rastreador para de enviar as informações para o site de rastreamento.");
		comandos.add(csc);
		Comando cs = new Comando("Verificar Configuração", "G",R.drawable.verifica);
		cs.setDescricao("Verifica na memória a configuração do rastreador.");
		comandos.add(cs);
		Comando gprsa = new Comando("Salva GPRS Ativar", "Y1",R.drawable.gprs);
		gprsa.setDescricao("Quando o veículo está também operando através do site localizevoce, quando ativa essa função, quando o veículo for desligado, o " +
				"rastreador irá parar de enviar as informações para o site após 5 minutos, quando o veículo for ligado novamente o rastreador voltará a enviar " +
				"as informações. Essa função evita o consumo de dados do chip gsm e também evitará a geração de grande histórico e relatório com registros desnecessários. " +
				"Poderá auxiliar no não bloqueio do chip gsm, pois quando existe muito trafego de dados as operadoras costumam bloquear o chip gsm.");
		comandos.add(gprsa);
		Comando gprsd = new Comando("Salva GPRS Cancelar", "Y0", R.drawable.gprs);
		gprsd.setDescricao("Cancela a função Salva GPRS, e se o rastreador estiver operando também atraves do site localizevoce, o rastreador poderá sempre transmitir" +
				" os dados, NÃO ACONSELHAMOS que seja cancelada a função, pois a função evitará histórico e relatório com dados desnecessários.");
		comandos.add(gprsd);
		Comando rs = new Comando("Reset", "RESET!", R.drawable.undo);
		rs.setDescricao("Apaga todas as configurações do rastreador, voltando com as configurações originais de fábrica.\n " +
				"O cliente deverá fazer novamente as configurações desejadas.\n" +
				"A senha padrão é : 123456 ");
		comandos.add(rs);
		COMANDOS = comandos;
	}

	public ArrayList<Comando> getAll() {

		return COMANDOS;
	}

	public Comando getOne(String name) {
		Comando c = new Comando();
		c.setNome(name);

		return COMANDOS.get(COMANDOS.indexOf(c));
	}

	public ArrayList<Comando> listAllComandosEndereco() {


		return COMANDOS_ENDERECO;
	}

}
