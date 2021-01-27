package Compiladorfinal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JLabel;
import java.awt.Font;


public class Interfaz extends JFrame {
	public JTextArea textArea;
	public JTextArea textArea_1;
	public String cadena;
	Stack<String> pila = new Stack<String>();// La pila principal del programa donde se va ir metiendo la entrada con su estado
	//Stack<String> semantico = new Stack<String>();
	String[] parts;// donde se separa toda la cadena
	Map<String, Declaracion2> mapa = new HashMap<>();// donde declaro las variables
	Map<String, String> semantico_pop = new HashMap<>();// la produccion hasta donde se elimina segun la produccion que se tenga
	Map<String, String> semantico_push = new HashMap<>();// regresa la parte de la produccion que se va sustituir por lo que elimine
	Stack<String> pila_anidacion = new Stack<String>(); 
	Stack<String> etiqueta_else = new Stack<String>();
	Stack<String> pila_pos = new Stack<String>();
	Stack<String> posfija = new Stack<String>();
	Stack<String> pilaS = new Stack<String>();
	Stack<String> pila_tipos = new Stack<String>();
	Stack<String> pilaS_boleanos = new Stack<String>();
	Stack<String> tipos_boleanos = new Stack<String>();
	Stack<String> aux_posfija;
	Stack<String> aux_tipos;
	String er_char = "['][']|['][^\'][']";
	String er_palabra = "[\"][^\"]*[\"]";
	String er_var = "\\w";
	String er_entero = "(^\\d+)";
	String id = "[A-z][A-z]*[0-9]*[A-z]*";
	Boolean ban_asignacion = false;
	Boolean ban_declaracion = false;
	Boolean ban_if = false;
	Boolean ban_else = false;
	Boolean ban_while = false;
	Boolean ban_print = false;
	Boolean ban_scan = false;
	String terminador = ";";
	String asignacion = "=";
	String[] operacion = { "+", "-", "*", "/" };
	String tipos_dato[] = { "String", "char", "int", "float", "bool" };
	String retornar_igual[] = { ":", ",", "{", "}", "(", ")", "[", "]", "$" };
	String operacion_bool[] = { "<", ">", "<=", ">=", "!=", "||", "&&" };
	String comp_num_id[] = { "<", ">", "<=", ">=" };
	String comp_bool[] = { "!=", "||", "&&" };
	String palab_clave[] = { "if","while", "else", "print", "scan", "Fin" };
	String boleanos[] = { "true", "false" };
	String cod_obj = "#include <stdio.h>\n#include <stdbool.h> \nint main() { \n";
	String cod_obj_id_asigna = "";  //asignacion de variables 
	String etiuno = "";
	String etidos = "";
	int cont_var = 0;
	int cont_labels = 0;
	String MayPri[] = { "*", "/" };
	String MenPri[] = { "-", "+" };
	String var1, var2, tipo1, tipo2;
	Vector<String[]> tablacompleta = new Vector<String[]>();
	String ci=   "if	(	)	{	else	;	while	print	}	Fin	id	,	String	char	int	float	bool	=	?	scan	palabra	carácter	+	-	*	/	[	]	num	>	<	>=	<=	!=	&&	||	true	false	caracter	$	PROG’	PROG	COD	CODP	Declaracion	IDEN	Tipo	Asigna	E	Oper	T	F	LOG	S	V";
	String fi = "I0	I1	I2	I3	I4	I5	I6	I7	I8	I9	I10	I11	I12	I13	I14	I15	I16	I17	I18	I19	I20	I21	I22	I23	I24	I25	I26	I27	I28	I29	I30	I31	I32	I33	I34	I35	I36	I37	I38	I39	I40	I41	I42	I43	I44	I45	I46	I47	I48	I49	I50	I51	I52	I53	I54	I55	I56	I57	I58	I59	I60	I61	I62	I63	I64	I65	I66	I67	I68	I69	I70	I71	I72	I73	I74	I75	I76	I77	I78	I79	I80	I81	I82	I83	I84	I85	I86	I87	I88	I89	I90	I91	I92	I93	I94	I95	I96	I97	I98	I99	I100	I101	I102	I103	I104	I105	I106	I107	I108	I109	I110	I111	I112	I113	I114	I115	I116	I117	I118	I119	I120	I121	I122	I123	I124";
	String[] columnas;
	String[] filas;
	String tabla[] = {
			"												I4	I5	I6	I7	I8																									I1			I2		I3								", 
			"																																							P0															", 
			"I11						I12	I13		I14	I15																																	I9				I10							", 
			"										I16																																												", 
			"										P16																																												", 
			"										P17																																												", 
			"										P18																																												", 
			"										P19																																												", 
			"										P20																																												", 
			"																																							P1															", 
			"I11						I12	I13		I14	I15																																	I17				I10							", 
			"	I18																																																					", 
			"	I19																																																					", 
			"	I20																																																					", 
			"																																							P11															", 
			"																	I21																																					", 
			"					I24						I23																																		I22									", 
			"																																							P7															", 
			"	I27									I28																		I31								I29	I30															I25	I26	", 
			"	I27									I28																		I31								129	I30															I32	I26	", 
			"										I34										I36	I37							I35																										I33", 
			"	I27									I28									I41	I42	I43					I46		I31								I29	I30											I38	I39	I44	I45	I40	I26	", 
			"P12						P12	P12		P12	P12																																												", 
			"										I47																																												", 
			"P15						P15	P15		P15	P15		I4	I5	I6	I7	I8																												I48		I3								", 
			"		I49																											I50	I51	I52	I53	I54	I55	I56																			", 
			"		P43																P43											P43	P43	P43	P43	P43	P43	P43																			", 
			"	I27									I28																		I31								I29	I30															I122	I26	", 
			"		P45			P34													P45				P34	P34	P34	P34		P34		P45	P45	P45	P45	P45	P45	P45																			", 
			"		P46																P46											P46	P46	P46	P46	P46	P46	P46																			", 
			"		P47																P47											P47	P47	P47	P47	P47	P47	P47																			", 
			"		P48			P35													P48				P35	P35	P35	P35		P35		P48	P48	P48	P48	P48	P48	P48																			", 
			"		I57																											I50	I51	I52	I53	I54	I55	I56																			", 
			"		I58																																																				", 
			"		P49																																																				", 
			"		P50																																																				", 
			"		P51																																																				", 
			"		P52																																																				", 
			"					I59																																																	", 
			"					P22																	I60	I61																															", 
			"																		I124											I50	I51	I52	I53	I54	I55	I56																			", 
			"	I62																																																					", 
			"					P25																																																	", 
			"					P26																																																	", 
			"					P29																	P29	P29	I63	I64		P29																											", 
			"					P32																	P32	P32	P32	P32		P32																											", 
			"										I28																I46		I31																					I65	I44	I45			", 
			"					I24						I23																																		I66									", 
			"P14						P14	P14		P14	P14																																												", 
			"			I67																																																			", 
			"	I27									I28																		I31								I29	I30																I68	", 
			"	I27									I28																		I31								I29	I30																I69	", 
			"	I27									I28																		I31								I29	I30																I70	", 
			"	I27									I28																		I31								I29	I30																I71	", 
			"	I27									I28																		I31								I29	I30																I72	", 
			"	I27									I28																		I31								I29	I30																I73	", 
			"	I27									I28																		I31								I29	I30																I74	", 
			"			I75																																																			", 
			"					I76																																																	", 
			"P21						P21	P21	P21	P21	P21																																												", 
			"										I28																I46		I31																						I77	I45			", 
			"										I28																I46		I31																						I78	I45			", 
			"										I34										I36	I37							I35																										I79", 
			"										I28																I46		I31																							I80			",
			"										I28																I46		I31																							I81			", 
			"																						I60	I61				I82																											", 
			"P13						P13	P13		P13	P13																																												", 
			"I85						I86	I87	I88		I15																																I83					I84							", 
			"		P36																P36											P36	P36	P36	P36	P36	P36	P36																			",
			"		P37																P37											P37	P37	P37	P37	P37	P37	P37																			", 
			"		P38																P38											P38	P38	P38	P38	P38	P38	P38																			", 
			"		P39																P39											P39	P39	P39	P39	P39	P39	P39																			", 
			"		P40																P40											P40	P40	P40	P40	P40	P40	P40																			", 
			"		P41																P41											P41	P41	P41	P41	P41	P41	P41																			", 
			"		P42																P42											P42	P42	P42	P42	P42	P42	P42																			", 
			"I85						I86	I87	I88		I15																																I89					I84							", 
			"I11						I12	I13		I14	I15																																	I90				I10							", 
			"					P27																	P27	P27				P27																											", 
			"					P28																	P28	P28				P28																											", 
			"		I91																																																				", 
			"					P30																	P30	P30	P30	P30		P30																											", 
			"					P31																	P31	P31	P31	P31		P31																											", 
			"					P33																	P33	P33	P33	P33		P33																											", 
			"				I92																																																		", 
			"I85						I86	I87	I88		I15																																I93					I84							", 
			"	I94																																																					",
			"	I95																																																					", 
			"	I96																																																					", 
			"				P6	P6																																																	", 
			"					I97																																																	", 
			"																																							P10															", 
			"					P24																																																	", 
			"			I98																																																			", 
			"				P2	P2																																																	", 
			"	I27									I28																		I31								I29	I30															I99	I26	", 
			"	I27									I28																		I31								I29	I30															I100	I26	", 
			"										I34										I36	I37							I35																										I101", 
			"I11						I12	I13		I14	I15																																	I102				I10							", 
			"I85						I86	I87	I88		I15																																I103					I84							", 
			"		I104																											I50	I51	I52	I53	I54	I55	I56																			",
			"		I105																											I50	I51	I52	I53	I54	I55	I56																			", 
			"		I106																																																				", 
			"																																							P9															",
			"					I107																																																	", 
			"			I108																																																			", 
			"			I109																																																			", 
			"					I110																																																	", 
			"I11						I12	I13		I14	I15																																	I111				I10							", 
			"I85						I86	I87	I88		I15																																I112					I84							", 
			"I85						I86	I87	I88		I15																																I113					I84							", 
			"I85						I86	I87	I88		I15																																I114					I84							", 
			"																																							P8															",
			"				I115																																																		", 
			"					I116																																																	", 
			"				P5	P5																																																	",
			"			I117																																																			", 
			"I85						I86	I87	I88		I15																																I118					I84							", 
			"I85						I86	I87	I88		I15																																I119					I84							", 
			"				P4	P4																																																	",
			"					I120																																																	", 
			"I85						I86	I87	I88		I15																																I121					I84							", 
			"				P3	P3																																																	", 
			"		I123																											I50	I51	I52	I53	I54	I55	I56																			", 
			"		P44																P44											P44	P44	P44	P44	P44	P44	P44																			", 
			"					P23																																																	"};
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz() {

		filas = fi.split("	");
		columnas = ci.split("	");
		//cadena = "int a , b , c ; bool d ; c = a + b ; if ( a > b ) { if ( c < b ) { d = 5 < 9 : ; } else { if ( a > b ) { if ( c < b ) { d = 5 < 9 : ; } else { while ( ( 5 < 9 ) && ( 5 < 9 ) ) { } ; } ; } else { } ; } ; } else { } ; Fin $";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Analisis");
		btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			cadena=limpiartexto();
			System.out.println(cadena);
			Separar();
			Generatabla();
			GeneraProducciones();
			Sintactico();
			cod_obj += "}";
			textArea_1.setText(cod_obj);
		  }
		});
	 btnNewButton.setBounds(316, 361, 89, 23);
		 contentPane.add(btnNewButton);

		 JScrollPane scrollPane = new JScrollPane();
	 scrollPane.setBounds(10, 21, 337, 310);
		 contentPane.add(scrollPane);

		 textArea = new JTextArea();
		 scrollPane.setViewportView(textArea);
		 
		 JScrollPane scrollPane_1 = new JScrollPane();
		 scrollPane_1.setBounds(374, 21, 337, 310);
		 contentPane.add(scrollPane_1);
		 
		 textArea_1 = new JTextArea();
		 scrollPane_1.setViewportView(textArea_1);
		 
		 JLabel lblNewLabel = new JLabel("Codigo Fuente");
		 lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
		 lblNewLabel.setBounds(146, 0, 81, 23);
		 contentPane.add(lblNewLabel);
		 
		 JLabel lblCodigoObjeto = new JLabel("Codigo Objeto");
		 lblCodigoObjeto.setFont(new Font("Arial", Font.BOLD, 12));
		 lblCodigoObjeto.setBounds(504, 0, 81, 23);
		 contentPane.add(lblCodigoObjeto);
	}

	public void Separar() {
		parts = cadena.split(" ");
		System.out.println(Arrays.asList(parts));
	}
	
	public String limpiartexto() {//eliminamos cualquier salto de linea del texto q se recibe del textArea separado por un espacio con el metodo split
		String txt_limpiar = textArea.getText();
		String text = txt_limpiar.replaceAll("\\r\\n|\\r|\\n", "");
		return text;
	}

	public void Generatabla() {
		// System.out.println(columnas.length);
		for (int i = 0; i < tabla.length; i++) {
			tablacompleta.add(i, tabla[i].split((char) 9 + "", 400)); // aqui agregamos al vector tabla completa cada
																		// elemeto del vector tabla y la funcion split																// es para separar cada uno con un tabulador
		}
	}
	
	public boolean Verificar(String y) {
		if (y.matches(id)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean BuscarOp(String q) {
		for (int i = 0; i < operacion.length; i++) {
			if (q.equalsIgnoreCase(operacion[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean BuscarRetI(String q) {// buscar simbolo
		for (int i = 0; i < retornar_igual.length; i++) {
			if (q.equalsIgnoreCase(retornar_igual[i])) {
				return true;
			}
		}
		return false;

	}

	public int BuscarFilas() {// va buscar el ultimo elemento de la pila que debe ser un estado y me regresa
								// en que posicin se encuentra
		String faux = pila.elementAt(pila.size() - 1);
		for (int i = 0; i < filas.length; i++) {
			if (filas[i].equalsIgnoreCase(faux)) {
				return i;
			}
		}
		return 0;
	}

	public int BuscarColumnas(String x) {
		for (int i = 0; i < columnas.length; i++) {
			// System.out.println("columna:"+columnas[i]+" X:"+x);
			if (columnas[i].equalsIgnoreCase(x)) {
				return i;
			}
		}
		return 0;
	}

	public int TipoDato(String td) {
		for (int i = 0; i < tipos_dato.length; i++) {
			if (td.equalsIgnoreCase(tipos_dato[i])) {
				return i;
			}
		}
		return -1;
	}

	public int index(String vec[], String ent) {
		int ret = -1;
		for (int i = 0; i < vec.length; i++) {
			if (vec[i].equals(ent)) {
				ret = i;
			}

		}
		return ret;
	}

	public void Sintactico() {
		int i = 0, f = 0, c = 0;
		pila.push("I0");// siempre empieza la pila I0 para analizarlo
		String caracter = ""; // es donde se va recibir lo q mande el analizador lexico cuando le mande la
								// palabra almacenaadad en el parts
		do {
			caracter = AnaLex(parts[i]);// Aqui entra el analizador lexico
			f = BuscarFilas();
			c = BuscarColumnas(caracter);
			System.out.println("Fila: " + f + " Columna: " + c + " Entrada: " + caracter + " Tabla:"
					+ tablacompleta.elementAt(f)[c]);

			if (caracter.equalsIgnoreCase("$") && tablacompleta.elementAt(f)[c].equalsIgnoreCase("P0")) {
				System.out.println("cadena aceptada");
				// JOptionPane.showMessageDialog(null, "Cadena Aceptada");
				break;
			}

			System.out.println(pila);
			if (tablacompleta.elementAt(f)[c].charAt(0) == 'I') {
				// Realizar desplazamiento
				if (index(tipos_dato, parts[i]) != -1) {  // si es un tipo de dato el q nos llega entonces
					if (parts[i].equals("String")) {      //si es un string lo cambiamos por un char porque en c no existen Strings
						cod_obj += "char ";
					} else {
						cod_obj += parts[i] + " ";
					}
				} else if (ban_print) {  //si nos llega un print le agregamos al codigo objto un print f
					if (caracter.equals("print")) {
						cod_obj += "printf";
					} else if(parts[i].equals("(") || parts[i].equals(")")){
						cod_obj += parts[i];
						if(parts[i].equals(")")) 
							ban_print= false;
					}else if(caracter.equals("id")){

						if(mapa.get(parts[i]).tipo.equals("char")){
							cod_obj += "\"%c\"," + parts[i];
						}else if(mapa.get(parts[i]).tipo.equals("int")){
							cod_obj += "\"%d\"," + parts[i];
						}else if(mapa.get(parts[i]).tipo.equals("String")){
							cod_obj += "\"%s\"," + parts[i];
						}else if(mapa.get(parts[i]).tipo.equals("bool")){
							System.out.println("Error");
						}
					}if(caracter.equals("carácter")){
						cod_obj += "\"%c\"," + parts[i];
					}else if(caracter.equals("num")){
						cod_obj += "\"%d\"," + parts[i];
					}else if(caracter.equals("palabra")){
						cod_obj += "\"%s\"," + parts[i];
					}
					if (caracter.equals(";")) {   // si le llega un punto y coma hacemos salto de linea y desactivamos la bandera de print
						cod_obj += "\n";
						ban_print = false;
					}
				} else if (ban_while) { // si le llega un while vamos armando la posfija para dividir lo que esta dentro del while en expresiones mas sencillaz
					infijo_posfijo(parts[i]);  
					if (parts[i].equals("{")) {

						etiuno = "label" + (++cont_labels);  //genera las etiquetas o ciclos que necesita el while para continuar y para salirse
						etidos = "label" + (++cont_labels);  

						cod_obj += etidos + ":;\n";
						Posfija();

						if (!tipos_boleanos.isEmpty())
							tipo1 = tipos_boleanos.pop(); //esto es solo para dejar la pila de boleanos vacia

						pila_anidacion.push("goto " + etidos + "; \n" + etiuno + ": ; \n");//mete a la pila de anidacion la etiueta dos y la etiqeta uno en orden
						cod_obj += "if( !( iden" + cont_var + " ) ) \ngoto " + etiuno + ";\n"; //aqui en el if del cosigo objeto va meter la ultima variable que se declaro
						ban_while = false;
					}
				} else if (ban_else) {

					if (parts[i].equals("{")) {

						etiuno = "label" + (++cont_labels);  //hacemos otra etiqtea
						pila_anidacion.push(etiuno + ": ; \n"); //anidamos la etiqueta
						cod_obj += "if( ( " + etiqueta_else.pop() + " ) ) \ngoto " + etiuno + ";\n"; // agregamos la condicion en el if sin el signo y el goto a etiqueta uno
						ban_else = false;                                                            //auqn ya tiene el contador de etiquetas aumentado
					}
				} else if (ban_if) {
					infijo_posfijo(parts[i]);
					if (parts[i].equals("{")) {

						Posfija();

						if (!tipos_boleanos.isEmpty())
							tipo1 = tipos_boleanos.pop();  // esto es solo para dejar la pila de boleanos vacia
						etiuno = "label" + (++cont_labels);
						pila_anidacion.push(etiuno + ": ; \n");
						etiqueta_else.push("iden" + cont_var); //guardamos la variable q usaremos en el else, guardamos la condicion del else
						cod_obj += "if( !( iden" + cont_var + " ) ) \ngoto " + etiuno + ";\n";
						ban_if = false;
					}
				} else if (caracter.equals("}")) {  // cuando se encuentr una llave q cierra va sacar todas las etiquetas q esten anidadas esto hace q se pueda anidar todo
					cod_obj += pila_anidacion.pop();   //porque saca la etiqueta cuando le toca su llave q cierra
				} else if (caracter.equals("=")) {
					cod_obj_id_asigna += parts[i] + " ";

				} else if (caracter.equals(";")) {
					if (ban_declaracion)
						ban_declaracion = false;

					if (ban_asignacion) {   // si en el punto y coma llega con la banndera de asignacion
						infijo_posfijo(parts[i]);// se termina hacer la posfija  porq la posfija se debe termianr con punto y coma  o llave que abre
			//			System.out.println(posfija);
				//		System.out.println(posfija_tipos);

						if (posfija.size() == 1) {
							tipo1 = pila_tipos.pop();
							mapa.put("iden" + (++cont_var), new Declaracion2("iden" + cont_var, tipo1, "id", 0));
							cod_obj += tipo1 + " iden" + cont_var + " = " + posfija.pop() + ";\n";
					//		System.out.println("Posfija: " + posfija);
						//	System.out.println("Posfija_tipos: " + posfija_tipos);
						//	System.out.println("Pila Semantica: " + pilaS);
						} else {
							Posfija(); // se analiza esto quiere decir q se generan las operaciones mas sencillas para podersela asifnar al id asigna
						}
					//	System.out.println("id_asignar= " + mapa.get(id_asignar).tipo);
					//	System.out.println("Variable= " + mapa.get("iden" + cont_var).nombre);
						if (mapa.get("iden" + cont_var).tipo.equals(mapa.get(id_asignar).tipo)) { // comparamos que el tipo de la ultima variable que se decalro y el tipo de
							cod_obj_id_asigna += "iden" + cont_var;                               //la variable que queremos asignar sean el mismo
							cod_obj += cod_obj_id_asigna;        
							cod_obj_id_asigna = "";
							try {
								if (!aux_tipos.isEmpty())
									aux_tipos.pop();
								if (!pilaS_boleanos.isEmpty())    //limpiamos las pilas
									pilaS_boleanos.pop();
								if (!tipos_boleanos.isEmpty())
									tipos_boleanos.pop();
							} catch (Exception e) {
			
							}

						} else {
							System.out.println("Error en los tipos");
							break;
						}
						ban_asignacion = false;
					}
					if (ban_scan)
						ban_scan = false;
					if (!parts[i - 1].contentEquals("}"))  //si tiene un punto y coma y antes de este tiene una llave que cierre no mete el punto y coma
						cod_obj += parts[i] + " \n";
				} else if (ban_asignacion && !caracter.equals("=")) {    // bandera scan despues de una asignacion
					if (!ban_scan) {     
						infijo_posfijo(parts[i]);   // si es falsa la bandera de leer se puede hacer una asignacion normal 
					} else {                     // y sino ps debemos de comparara los tipos de datos 
						if (mapa.get(id_asignar).tipo.equals("char")) {
							cod_obj += "scanf(\"%c\",&" + id_asignar + ")";
						} else if (mapa.get(id_asignar).tipo.equals("int")) {
							cod_obj += "scanf(\"%d\",&" + id_asignar + ")";
						} else if (mapa.get(id_asignar).tipo.equals("String")) {
							cod_obj += "scanf(\"%s\"," + id_asignar + ")";
						}
						cod_obj_id_asigna = "";
						ban_asignacion = false;
					}
				} else if (caracter.equals("id")) {
					if (!ban_declaracion)
						cod_obj_id_asigna += parts[i];
					if (ban_declaracion) {
						if (tipo_actual.equals("String")) {
							cod_obj += parts[i] + "[254] ";
						} else {
							cod_obj += parts[i] + " ";
						}
					}
				} else {

					if (caracter.equals(","))
						cod_obj += parts[i] + " ";
				}

				pila.push(caracter);// metes el caracter a la pila
				pila.push(tablacompleta.elementAt(f)[c]);// metes el estado a la pila
				i++;
				
			} else if (tablacompleta.elementAt(f)[c].charAt(0) == 'P') {
				// REALIZAR REDUCCION
				do {
					pila.pop();
				} while (!semantico_pop.get(tablacompleta.elementAt(f)[c]).equalsIgnoreCase(pila.peek()));

				pila.pop();
				pila.push(semantico_push.get(tablacompleta.elementAt(f)[c]));

				f = Integer.parseInt(
						pila.elementAt(pila.size() - 2).substring(1, pila.elementAt(pila.size() - 2).length()));
				c = BuscarColumnas(pila.elementAt(pila.size() - 1));
				pila.push(tablacompleta.elementAt(f)[c]);// meto a la pila lo q nos encontramos

			} else {
				break;
			}

		} while (i <= parts.length);
		System.out.println(cod_obj);

	}

	String ult_iden = "";
	String id_asignar = "";
	String tipo_actual = "";

	public String AnaLex(String p) {// me va regresar una string dependiendo del caracter
		if (index(palab_clave, p) != -1) {   //saca la poicion del vector con un string
			if (p.equals("if"))
				ban_if = true;
			if (p.equals("else"))
				ban_else = true;
			if (p.equals("while"))
				ban_while = true;
			if (p.equals("print"))
				ban_print = true;
			if (p.equals("scan"))
				ban_scan = true;
			return p;
		} else if (TipoDato(p) != -1) {// verifica si es un tipo de dato y lo empieza a declarar
			tipo_actual = p;
			ban_declaracion = true;
			return p;
		} else {
			if (p.equalsIgnoreCase(terminador)) {// Cuando se encuentre un terminador va limpiar el tipo actual
				tipo_actual = "";
				return p;
			} else if (BuscarRetI(p)) {// Busca un simbolo de los de retornar igual
				return p;
			} else if (p.equalsIgnoreCase(asignacion)) {
				ban_asignacion = true;
				id_asignar = ult_iden;   //en ult_iden vamos guardando el ultimo identificador q se ingreso
				return p;
			} else if (BuscarOp(p)) {
				return p;
			} else if (Verificar(p) && !p.matches(er_entero)) {
				// verificar si es una variable
				if (tipo_actual.equalsIgnoreCase("")) {// si el tipo de dato esta vacio
					
					if (!ban_asignacion) {
						ult_iden = p;  //guardamos el valor del identificador
					}
				} else {// si el tipo de dato actual tiene algo
					mapa.put(p, new Declaracion2(p, tipo_actual, "id", 0));// Declaracion de variables
				}
				return "id";
			} else if (p.matches(er_entero)) {
				return "num";
			} else if (p.matches(er_palabra)) {
				return "palabra";
			} else if (p.matches(er_char)) {
				return "carácter";
			} else if (index(boleanos, p) != -1) {
				return p;
			}
			return p;
		}

	}

	public void GeneraProducciones() {
		semantico_pop.put("P0", "PROG");
		semantico_pop.put("P1", "Declaracion");
		semantico_pop.put("P2", "Asigna");
		semantico_pop.put("P3", "if");
		semantico_pop.put("P4", "while");
		semantico_pop.put("P5", "print");
		semantico_pop.put("P6", "}");
		semantico_pop.put("P7", "Asigna");
		semantico_pop.put("P8", "if");
		semantico_pop.put("P9", "while");
		semantico_pop.put("P10", "print");
		semantico_pop.put("P11", "Fin");
		semantico_pop.put("P12", "Tipo");
		semantico_pop.put("P13", ",");
		semantico_pop.put("P14", ";");
		semantico_pop.put("P15", ";");
		semantico_pop.put("P16", "String");
		semantico_pop.put("P17", "char");
		semantico_pop.put("P18", "int");
		semantico_pop.put("P19", "float");
		semantico_pop.put("P20", "bool");
		semantico_pop.put("P21", "id");
		semantico_pop.put("P22", "Oper");
		semantico_pop.put("P23", "LOG");
		semantico_pop.put("P24", "scan");
		semantico_pop.put("P25", "palabra");
		semantico_pop.put("P26", "carácter");
		semantico_pop.put("P27", "Oper");
		semantico_pop.put("P28", "Oper");
		semantico_pop.put("P29", "T");
		semantico_pop.put("P30", "T");
		semantico_pop.put("P31", "T");
		semantico_pop.put("P32", "F");
		semantico_pop.put("P33", "[");
		semantico_pop.put("P34", "id");
		semantico_pop.put("P35", "num");
		semantico_pop.put("P36", "LOG");
		semantico_pop.put("P37", "LOG");
		semantico_pop.put("P38", "LOG");
		semantico_pop.put("P39", "LOG");
		semantico_pop.put("P40", "LOG");
		semantico_pop.put("P41", "LOG");
		semantico_pop.put("P42", "LOG");
		semantico_pop.put("P43", "S");
		semantico_pop.put("P44", "(");
		semantico_pop.put("P45", "id");
		semantico_pop.put("P46", "true");
		semantico_pop.put("P47", "false");
		semantico_pop.put("P48", "num");
		semantico_pop.put("P49", "id");
		semantico_pop.put("P50", "num");
		semantico_pop.put("P51", "palabra");
		semantico_pop.put("P52", "carácter");

		semantico_push.put("P0", "PROG’");
		semantico_push.put("P1", "PROG");
		semantico_push.put("P2", "COD");
		semantico_push.put("P3", "COD");
		semantico_push.put("P4", "COD");
		semantico_push.put("P5", "COD");
		semantico_push.put("P6", "COD");
		semantico_push.put("P7", "CODP");
		semantico_push.put("P8", "CODP");
		semantico_push.put("P9", "CODP");
		semantico_push.put("P10", "CODP");
		semantico_push.put("P11", "CODP");
		semantico_push.put("P12", "Declaracion");
		semantico_push.put("P13", "IDEN");
		semantico_push.put("P14", "IDEN");
		semantico_push.put("P15", "IDEN");
		semantico_push.put("P16", "Tipo");
		semantico_push.put("P17", "Tipo");
		semantico_push.put("P18", "Tipo");
		semantico_push.put("P19", "Tipo");
		semantico_push.put("P20", "Tipo");
		semantico_push.put("P21", "Asigna");
		semantico_push.put("P22", "E");
		semantico_push.put("P23", "E");
		semantico_push.put("P24", "E");
		semantico_push.put("P25", "E");
		semantico_push.put("P26", "E");
		semantico_push.put("P27", "Oper");
		semantico_push.put("P28", "Oper");
		semantico_push.put("P29", "Oper");
		semantico_push.put("P30", "T");
		semantico_push.put("P31", "T");
		semantico_push.put("P32", "T");
		semantico_push.put("P33", "F");
		semantico_push.put("P34", "F");
		semantico_push.put("P35", "F");
		semantico_push.put("P36", "LOG");
		semantico_push.put("P37", "LOG");
		semantico_push.put("P38", "LOG");
		semantico_push.put("P39", "LOG");
		semantico_push.put("P40", "LOG");
		semantico_push.put("P41", "LOG");
		semantico_push.put("P42", "LOG");
		semantico_push.put("P43", "LOG");
		semantico_push.put("P44", "S");
		semantico_push.put("P45", "S");
		semantico_push.put("P46", "S");
		semantico_push.put("P47", "S");
		semantico_push.put("P48", "S");
		semantico_push.put("P49", "V");
		semantico_push.put("P50", "V");
		semantico_push.put("P51", "V");
		semantico_push.put("P52", "V");

	}

	class Declaracion2 {
		String nombre, tipo, lexema;
		int valor;

		public Declaracion2(String n, String t, String l, int v) {
			nombre = n;
			tipo = t;
			lexema = l;
			valor = v;
		}
	}

	public void infijo_posfijo(String cod) {

		if (cod.equals("(") || cod.equals("[")) {   //los parentesis que abren se van a gregar a la pila 
			pila_pos.push(cod);
		} else if (cod.matches(er_char)) {    //numeros e identificadores se meten directamente a la expresion posfija
			posfija.push(cod);
			pila_tipos.push("char");
			System.out.println(posfija);
			System.out.println(pila_tipos);  //a la pila tipos para hacer comparacion de los tipos
		} else if (cod.matches(er_entero)) {
			posfija.push(cod);
			pila_tipos.push("int");
		} else if (cod.matches(er_var)) {
			posfija.push(cod);
			pila_tipos.push(mapa.get("" + cod).tipo);
		} else if (index(operacion, cod) != -1) {
			if (index(MayPri, cod) != -1) {
				if (!pila_pos.empty()) {
					while (index(MayPri, pila_pos.peek()) != -1) {   //mientras los que esta encima de la pla posfija no sea de mayor prioridad
						posfija.push(pila_pos.pop());                //lo va sacando de la pila posfija y lo mete a la posfija
						if (pila_pos.empty())
							break;
					}
				}
			} else if (index(MenPri, cod) != -1) {
				if (!pila_pos.empty()) {
					while (index(operacion, pila_pos.peek()) != -1) { //mientras lo q este encima de la pila no sea ningun operador 
						posfija.push(pila_pos.pop());                 // lo va sacar todo y lo va a meter a la posfija 
						if (pila_pos.empty())
							break;
					}
				}
			}
			pila_pos.push(cod);
		} else if (index(operacion_bool, cod) != -1) {         // si se encuentra un operador booleano
			if (index(comp_bool, cod) != -1) {
				if (!pila_pos.empty()) {
					while (index(comp_bool, pila_pos.peek()) != -1) {  // y muentras haya un comparador de boolenaos va sacar todo de la pila
						posfija.push(pila_pos.pop());
						if (pila_pos.empty())
							break;
					}
				}
			} else if (index(comp_num_id, cod) != -1) {
				if (!pila_pos.empty()) {
					while (index(operacion_bool, pila_pos.peek()) != -1) {
						posfija.push(pila_pos.pop());
						if (pila_pos.empty())
							break;
					}
				}
			}

			pila_pos.push(cod);
		} else if (cod.equals(")") || cod.equals("]")) {   // si se encuentra un parentesis qe cierra lo va sacar todo hasta q se encuentra su parentesis q abre
			if (cod.equals(")")) {
				while (!pila_pos.peek().equals("(")) {
					posfija.push(pila_pos.pop());
				}
			} else if (cod.equals("]")) {
				while (!pila_pos.peek().equals("[")) {
					posfija.push(pila_pos.pop());
				}
			}
			pila_pos.pop();// saca el parentesis que abre 
		} else if (cod.equals(";") || cod.equals("{") || cod.equals("?")) { // y cuado se encuentra cuelquiera de estos signos saca todo de la pila 
			while (pila_pos.size() > 0) {
				posfija.push(pila_pos.pop());
			}
		}
	}

	public void Posfija() {
		aux_posfija = new Stack<String>();  
		aux_tipos = new Stack<String>();

		while (!posfija.isEmpty()) {
			aux_posfija.push(posfija.pop());
		}
                                                 //en estos dos solo los volteamos 
		while (!pila_tipos.isEmpty()) {
			aux_tipos.push(pila_tipos.pop());                    
		}

		System.out.println("Posfija: " + aux_posfija);
		System.out.println("Posfija_tipos: " + aux_tipos);
		System.out.println("Pila Semantica: " + pilaS);

		
		while (!aux_posfija.empty()) {    // mientras la posfija no este vacia vamos a ir mentiendo a la pila semantica  lo q nos vayamos encontrando
			System.out.println("Pila Semantica: " + pilaS);
			if (!pilaS.empty() && index(operacion, aux_posfija.peek()) != -1) {         //hasta q nos encontremos un signo de operacion 
				var1 = pilaS.pop();
				var2 = pilaS.pop();   //sacamos los valores y sus tipos
				tipo1 = aux_tipos.pop();
				System.out.println(tipo1);
				tipo2 = aux_tipos.pop();
				System.out.println(tipo2);

				if (!tipo1.equalsIgnoreCase("char") && !tipo2.equalsIgnoreCase("char")) {   // si los tipos coinciden emepezamos a decalarar variables 
					if (!tipo1.equalsIgnoreCase(tipo2)) {// si son diferentes osea un entero con un flotante o viceversa
															
						pilaS.push("float");

					} else {

						pilaS.push(" iden" + (++cont_var));     //declaramos variables 
						mapa.put("iden" + cont_var, new Declaracion2("iden" + cont_var, tipo1, "id", 0));
						cod_obj += tipo1 + pilaS.peek() + " = " + var2 + ' ' + aux_posfija.pop() + ' ' + var1 + ";\n";
						aux_tipos.push(tipo1);
						// pilaS.push(var1);
						// System.out.println(semantico);
					}
				}
			} else if (!pilaS.empty() && index(comp_num_id, aux_posfija.peek()) != -1) {
				var1 = pilaS.pop();
				var2 = pilaS.pop();

				tipo1 = aux_tipos.pop();
				tipo2 = aux_tipos.pop();

				if (!tipo1.equalsIgnoreCase("char") && !tipo2.equalsIgnoreCase("char")) {
					if (!tipo1.equalsIgnoreCase(tipo2)) {// si son diferentes osea un entero con un flotante o viceversa
															// me va
															// producir un flotante
						pilaS.push("float");

					} else {

						pilaS_boleanos.push(" iden" + (++cont_var));
						mapa.put("iden" + cont_var, new Declaracion2("iden" + cont_var, "bool", "id", 0));
						cod_obj += "bool" + pilaS_boleanos.peek() + " = " + var2 + ' ' + aux_posfija.pop() + ' ' + var1
								+ ";\n";
						tipos_boleanos.push("bool");
						// pilaS.push(var1);
						// System.out.println(semantico);
					}
				}
			} else if (!pilaS_boleanos.empty() && index(comp_bool, aux_posfija.peek()) != -1) {    // && || !=
				var1 = pilaS_boleanos.pop();
				var2 = pilaS_boleanos.pop();

				tipo1 = tipos_boleanos.pop();
				tipo2 = tipos_boleanos.pop();

				if (!tipo1.equalsIgnoreCase("char") && !tipo2.equalsIgnoreCase("char")) {
					if (!tipo1.equalsIgnoreCase(tipo2)) {// si son diferentes osea un entero con un flotante o viceversa
															// me va
															// producir un flotante
						pilaS.push("float");

					} else {
						pilaS_boleanos.push(" iden" + (++cont_var));
						mapa.put("iden" + cont_var, new Declaracion2("iden" + cont_var, "bool", "id", 0));
						cod_obj += tipo1 + pilaS_boleanos.peek() + " = " + var2 + ' ' + aux_posfija.pop() + ' ' + var1
								+ ";\n";
						tipos_boleanos.push("bool");
					}
				}
			} else {
				pilaS.push(aux_posfija.pop());
			}
		}
	}
}
