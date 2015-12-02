package br.com.twautomacao.safetycontrolsms.util;

import android.util.Log;



public class Senha {
	public String geraSenha(String imei, String numeros){
			if (numeros.length()==1)
				numeros="0"+numeros;
			if(numeros.length()>2){
				int numAux=0;
				for (int i = 0; i < (numeros.length()-1); i++) {
					numAux=numAux+Integer.parseInt(String.valueOf(numeros.charAt(i)));
				}
				numAux=numAux%10;
				numeros=String.valueOf(numAux)+numeros.charAt(numeros.length()-1);
				
			}
				
			if(imei.trim().length()==15){
				if(numeros.length()>0){
					imei+=numeros;
					String[] letras ={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J",  
							"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","A","B","C","D"  
							,"E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W",  
							"X","Y","Z"};    
					String b = "";    
					for ( int i = 0; i <imei.length() ; i++){ 
						int num = imei.charAt(i);
						num=num+i*79;
						num = num%62;
						b += letras[num];    
					}
					return b;
				}else{
					return null;
				}
				
			}else{
				return null;
			}
			
		}
}
