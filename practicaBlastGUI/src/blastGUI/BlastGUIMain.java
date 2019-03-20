package blastGUI;


import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import blast.BlastController;

public class BlastGUIMain {

	private static final String dataBaseFile = new String("yeast.aa");
	private static final String dataBaseIndexes = new String("yeast.aa.indexs");
	
	public static void main(String args[]){
		BlastController bCnt = new BlastController();
		try{
			//creamos el jframe
			JFrame frame1= new JFrame("BlastMenu");//creamos la ventana
			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cerramos la app cuando cerramos la ventana
			
			//creamos paneles y colores
			JPanel panel1 = new JPanel(); //creamos un panel
			JPanel panel2 = new JPanel(); //creamos un panel
			panel1.setLayout(new FlowLayout());//preparamos el layout
			panel2.setLayout(new FlowLayout());//preparamos el layout
			panel1.setBackground(Color.CYAN);//añadimos un color de fondo al panel
			panel2.setBackground(Color.WHITE);//añadimos un color de fondo al panel
			
			//boton desplegable
			String[] opciones = {"proteinas","nucleotidos"};//opciones disponibles del desplegable
			JComboBox<String>box=new JComboBox<>(opciones);//creamos un JComboBox
			panel1.add(box);//añadimos el combobox al panel 
			
			//etiqueta y caja para escribir el porcentaje
			JLabel instruccion = new JLabel("Introduza el porcentaje de acierto (valor entre 0  y 1):");//creamos la etiqueta 
			JTextField caja = new JTextField("",5);//creamos una caja de texto con texto por defecto y 20 columnas de espacio
			panel1.add(instruccion);
			panel1.add(caja);
			
			//etiquetas de las bases de datos
			JLabel DataBaseFile = new JLabel("data base file: " );//creamos la etiqueta
			JLabel DataBaseFile2 = new JLabel( dataBaseFile);//creamos la etiqueta
			//cambiamos la negrita
			Font f = DataBaseFile2.getFont();
			DataBaseFile2.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
			//
			
			JLabel DataBaseIndex = new JLabel("data base index: ");//creamos la etiqueta
			JLabel DataBaseIndex2 = new JLabel(dataBaseIndexes);//creamos la etiqueta
			
			//cambiamos la negrita
			Font f2 = DataBaseIndex2.getFont();
			DataBaseIndex2.setFont(f2.deriveFont(f2.getStyle() & ~Font.BOLD));
			//
			
			panel2.add(DataBaseIndex,"North");//añadimos su posicion
			panel2.add(DataBaseIndex2);//añadimos su posicion
			panel2.add(DataBaseFile,"Center");//añadimos su posicion
			panel2.add(DataBaseFile2,"Center");//añadimos su posicion
			
			//query sequence
			JLabel etiquetaSecuencia = new JLabel("Introduzca la secuencia deseada: ");//etiqueta
			JComboBox<String> caja2 = new JComboBox<String>();//combobox
			caja2.setEditable(true);//indicamos que se pueden añadir nuevos elementos
			
			caja2.addActionListener(new ActionListener() {//evento para añadir los nuevos elementos 
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//guardamos el string introducido por el usuario y lo pasamos a mayusculas
					String entrada = caja2.getEditor().getItem().toString().toUpperCase();
					caja2.addItem(entrada);//añadimos el string del usuario
					//comprobamos con este for que no hay duplicados en nuestro historial
					for(int i=0;i<caja2.getItemCount();i++){
						if((caja2.getItemAt(i).equals(caja2.getItemAt(i+1)) && caja2.getItemAt(i+1)!=null )) {
							caja2.removeItemAt(i);
						}
					}
					
				}
			});
			
			
			panel1.add(etiquetaSecuencia);//añadimos la etiqueta previa al jcombobox
			panel1.add(caja2);//añadimos el jcombobox al panel
			
			//resultado
			JTextArea result = new JTextArea(15,60);
			
			//boton para empezar el analisis
			JButton analisis= new JButton("Comenzar Busqueda");//boton para comenzar la busqueda
			analisis.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					char type='p';//obtenemos el tipo de busqueda
					float porcentaje=Float.parseFloat(caja.getText());//obtenemos el porcentaje de acierto
					String peticion = caja2.getSelectedItem().toString();//obtenemos la secuencia introducida por el usuario
					//comprobamos el tipo de busqueda
					if(box.getSelectedItem()=="proteinas") {
						type='p';
					}else {
						type='n';
					}
					try {
						//escribimos el resultado en la ventana de resultado
						if(type=='n') {
							result.setText("No hay ejemplos disponibles de nucleotidos");
						}else {
							result.setText(bCnt.blastQuery(type, dataBaseFile, dataBaseIndexes, porcentaje, peticion));	
						}
						
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						System.out.println("Error en la llamada: " + e1.toString());
						//
						//
					}
				}
				
			});
			
			panel1.add(analisis);//añadimos al panel el boton de busqueda
			panel1.add(result);//añadimos al panel el cuadro de resultado
			
			//creamos una barra para poder ascender o descender en el resultado
			JScrollPane scroll = new JScrollPane(result);//asignamos la barra al cuadro del resultado
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			panel1.add(scroll);
			
			
			//posicion de los paneles y tamaño y visibilidad del frame
			frame1.getContentPane().add(panel1,"Center");//colocamos el panel dentro de la ventana
			frame1.getContentPane().add(panel2,"North");//colocamos el panel dentro de la ventana
			frame1.setSize(800, 500);//establecemos el tamaño de la ventana
			frame1.setVisible(true);//ponemos la ventana visible
			
		} catch(Exception exc){
			System.out.println("Error en la llamada: " + exc.toString());
			
		}
	}
}
