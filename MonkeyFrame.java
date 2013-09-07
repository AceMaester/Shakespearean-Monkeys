/*
 * Shakespearean Monkey Gui by aett072
 * 
 * Main contents:
 * 1) Implementation of MonkeyFrame with swing components
 * 2) ActionPerformed method when 'start' button pressed containing:
 * 	  - swing worker for parallel evolution
 * 	  - swing worker for sequential evolution
 * 	  - swing worker that provides a timer
 */


import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import javax.swing.border.BevelBorder;


public class MonkeyFrame extends JFrame implements ActionListener
{

	
	
	
	public static boolean isParallel;
	public static boolean isComplete;
	public static final double crossoverProb = 0.87;
	public static final double mutationProb = 0.02;
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField monkeysPerGenerationSizeTextField;
	
	private JTextArea generatedTextArea;
	JTextArea targetTextArea; 
	private JCheckBox chckbxParallel ;
	private JButton btnStart;
	
	private JLabel lblUpdateTime;
	private JLabel lblUpdateGeneration;
	private JLabel lblUpdateGenerationPerSec;
	
	private SwingWorker<Void, String> sw;
	private SwingWorker<Void, String> evolutionWorker;
	private SwingWorker<Void, String> evolutionParallelWorker;
	
	public MonkeyFrame()
	{
		setTitle("Shakespearean Monkeys");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 955, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		//lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Admin\\workspace\\Assignment 4.1\\src\\george bush monkey.jpg"));
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("george bush monk.jpg")));
				
		generatedTextArea = new JTextArea();
		generatedTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		targetTextArea = new JTextArea();
		targetTextArea.setText("To be or not to be, that is the question;\nWhether 'tis nobler in the mind to suffer\nThe slings and arrows of outrageous fortune,\nOr to take arms against a sea of troubles,\nAnd by opposing end them.");
		targetTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		btnStart = new JButton("Start");
		btnStart.setActionCommand("Start");
		
		btnStart.addActionListener(this);
		
		chckbxParallel = new JCheckBox("Parallel");
		chckbxParallel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxParallel.isSelected()){
				isParallel = true;
				}else{
					isParallel = false;
				}
				
			}
		});
		
		JLabel lblMonkeysPerGeneration = new JLabel("Monkeys Per Generation");
		lblMonkeysPerGeneration.setFont(new Font("Arial", Font.BOLD, 14));
		
		monkeysPerGenerationSizeTextField = new JTextField();
		monkeysPerGenerationSizeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		monkeysPerGenerationSizeTextField.setText("2000");
		monkeysPerGenerationSizeTextField.setColumns(10);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblGenerations = new JLabel("Generations:");
		lblGenerations.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblGenerationsPerSec = new JLabel("Generations / Sec");
		lblGenerationsPerSec.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblUpdateTime = new JLabel("___");
		lblUpdateTime.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblUpdateGeneration = new JLabel("___");
		lblUpdateGeneration.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblUpdateGenerationPerSec = new JLabel("___");
		lblUpdateGenerationPerSec.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(28)
					.addComponent(lblNewLabel)
					.addGap(89)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblMonkeysPerGeneration, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(monkeysPerGenerationSizeTextField, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(chckbxParallel)))
							.addGap(14)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblGenerationsPerSec, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblGenerations, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblTime, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblUpdateGenerationPerSec, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblUpdateTime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblUpdateGeneration, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(generatedTextArea, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(targetTextArea, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
					.addGap(13))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMonkeysPerGeneration, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
								.addComponent(monkeysPerGenerationSizeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTime)
								.addComponent(lblUpdateTime))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnStart)
										.addComponent(chckbxParallel)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblGenerations)
										.addComponent(lblUpdateGeneration))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblGenerationsPerSec)
										.addComponent(lblUpdateGenerationPerSec))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(generatedTextArea, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
								.addComponent(targetTextArea, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(325, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	//----------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(btnStart.getText().equals("Start")){//start button pressed
			
			if(!Generator.checkTargetText(targetTextArea.getText())){
				JOptionPane.showMessageDialog(contentPane, "Please enter valid text between 1-300 characters long.");
				return;
			}
			
			if(!Generator.checkPopulationSize(monkeysPerGenerationSizeTextField.getText())){
				JOptionPane.showMessageDialog(contentPane, "Please enter a valid even number between 2 and 10000.");
				return;
			}
			
			
			
			
			btnStart.setText("Cancel");
			
			
			targetTextArea.setEditable(false);
			generatedTextArea.setEditable(false);
			generatedTextArea.setText("");
			monkeysPerGenerationSizeTextField.setEditable(false);
			chckbxParallel.setEnabled(false);
			generatedTextArea.setBackground(Color.lightGray);
			
			Generator.resetGenerations();
			
			
			if(isParallel && Integer.parseInt(monkeysPerGenerationSizeTextField.getText()) > 1400 ){
		   //----------------------------------------------------------------------------------------------------------------
		   //PARALLEL EVOLUTION	
		   //----------------------------------------------------------------------------------------------------------------
			
			
		   evolutionParallelWorker = new SwingWorker<Void, String>(){
			
			   public String[] nextPopulation;
			   
			   	
			   @Override
				protected Void doInBackground() {
					
				   
					
				     isComplete = false;
					 String targetText = targetTextArea.getText();
					 String[] currentPopulation =  Generator.CreateRandomPopulation();
					 nextPopulation = new String[currentPopulation.length];
					 
					 int [] currentScores = Generator.GetFitnessScores(currentPopulation);
					 
					 int[] breedingWeights = Generator.GetBreedingWeights(currentScores);
					 int totalWeight = Generator.GetTotalWeight(breedingWeights);
					 
					 
					 int currentBestIndex;
					 String currentBest = "";
					 int currentBestFitScore = targetText.length();
					 
					 
					 
					 
					 while(!currentBest.equals(targetText)){
						 
						 if(isCancelled()) //break if cancelled
								return null;
						 
						 
						
						 		
						 currentBestIndex = MonkeyLogic.getBestMonkey(currentScores);
						 
						 
						 
						 
						 if(currentScores[currentBestIndex] + 1 <  currentBestFitScore){
							 
						   currentBestFitScore = currentScores[currentBestIndex];
							 publish(currentBest); //publish the best monkey only if it is better than the last monkey
						 }
						 
						 
						 List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
						 int maxallowed_threads = 1 * Runtime.getRuntime().availableProcessors();
						 //int chunks = Integer.parseInt(monkeysPerGenerationSizeTextField.getText())/4;
						 int chunks = currentPopulation.length/(2*maxallowed_threads);
						
						 for(int low=0; low< currentPopulation.length/2; low += chunks){
							 final int Low = low;
			                 final int High = Math.min(currentPopulation.length/2, low+chunks);
			                 final int totalWeightF = totalWeight;
			                 final int[] breedingWeightsF = breedingWeights;
			                 final String[] currentPopulationF = currentPopulation;
			                 
			                    callables.add(new Callable<Void>() {                        
			                        public Void call() {
			                            for (int i = Low; i < High; i++) {
			                            	String[] s = task(i, totalWeightF, breedingWeightsF, currentPopulationF);
			   							 nextPopulation[2*i] = s[0];
			   						     nextPopulation[2*i + 1] = s[1];
			                            }
			                            return null;
			                        }
			                    });
						}
						 
						 
						
						 ExecutorService executor = new ForkJoinPool(maxallowed_threads); 
						
                         Executors.newFixedThreadPool(maxallowed_threads);
						 
						 
						 try {
				                List<Future<Void>> futures = executor.invokeAll(callables);
				                
				            } catch (Exception ex) {
				                System.err.format ("*** %s %n", ex.getMessage());
				            }
	
						 
						 callables = null;
						 executor.shutdown();
						 executor = null;
						 
						 
						 currentBest = currentPopulation[currentBestIndex];
						// currentBestFitScore = currentScores[currentBestIndex];
						 
						 
						 currentPopulation = nextPopulation; //recycle storage later
						 currentScores = Generator.GetFitnessScores(currentPopulation);
						 breedingWeights = Generator.GetBreedingWeights(currentScores);
						 totalWeight = Generator.GetTotalWeight(breedingWeights);
						 
						 Generator.incrementGenerations();
				                
				                
				                
				           
					 }
								
					 
					 publish(currentBest);//finally publish the best monkey	
					 isComplete = true;
					 return null;
					
				}
				
				public String[] task(int i, int totalWeight, int[] breedingWeights, String[] currentPopulation) {
					int parent1 = MonkeyLogic.randomParent(totalWeight, breedingWeights);
					 int parent2 = MonkeyLogic.randomParent(totalWeight, breedingWeights);
					 String child1;
					 String child2;
					 
					 
					 
					 
					 if(Math.random() < crossoverProb){
						
						
						int crossoverIndex = (int) ((Math.random()) * currentPopulation[parent1].length());
						child1 = currentPopulation[parent1].substring(0, crossoverIndex) + currentPopulation[parent2].substring(crossoverIndex);
						child2 = currentPopulation[parent2].substring(0, crossoverIndex) +currentPopulation[parent1].substring(crossoverIndex);
						
						
					}else{
						child1 = currentPopulation[parent1];
						child2 = currentPopulation[parent2];
						
					}
					 
				    if(Math.random() < mutationProb){
						 
						 
						int rand = (int)(Math.random()*child1.length());
				        StringBuilder s = new StringBuilder(child1);
						
						s.setCharAt(rand, Generator.getRandomChar());
						child1 = s.toString();
						
					}
					
					if(Math.random() < mutationProb){
						int rand = (int)(Math.random()*child2.length());
					
						StringBuilder s = new StringBuilder(child2);
						s.setCharAt(rand, Generator.getRandomChar());
						
						child2 = s.toString();
						
					}
					
					
					String[] s = {child1, child2};	
					
					return s;
				}

				@Override
				protected void process(List<String> chunks) { 
					
				String recentValue = chunks.get(chunks.size()-1);
				
					
				generatedTextArea.setText(recentValue);
				
				
					
				}
				@Override
				protected void done() { 
				
				sw.cancel(true);
				btnStart.setText("Start");
			    targetTextArea.setEditable(true);
				generatedTextArea.setEditable(true);
				monkeysPerGenerationSizeTextField.setEditable(true);
				chckbxParallel.setEnabled(true);
				
			    
			    sw.cancel(true);
			    
					
					
				}

			};
			evolutionParallelWorker.execute();
			
			   
		   
			
			
			
			}else{
			
				
			//----------------------------------------------------------------------------------------------------------------
			//SEQUENTIAL EVOLUTION	
			//----------------------------------------------------------------------------------------------------------------
			evolutionWorker = new SwingWorker<Void, String>(){
				@Override
				protected Void doInBackground() {
					
					 
					
				     isComplete = false;
					 String targetText = targetTextArea.getText();
					 String[] currentPopulation =  Generator.CreateRandomPopulation();
					 String[] nextPopulation = new String[currentPopulation.length];
					 
					 int [] currentScores = Generator.GetFitnessScores(currentPopulation);
					 
					 int[] breedingWeights = Generator.GetBreedingWeights(currentScores);
					 int totalWeight = Generator.GetTotalWeight(breedingWeights);
					 
					 
					 int currentBestIndex;
					 String currentBest = "";
					 int currentBestFitScore = targetText.length();
					 
					 
					 
					 
					 while(!currentBest.equals(targetText)){
						 
						 if(isCancelled()) //break if cancelled
								return null;
						 
						 
						
						 		
						 currentBestIndex = MonkeyLogic.getBestMonkey(currentScores);
						 
						 
						 
						 
						 if(currentScores[currentBestIndex] + 1 <  currentBestFitScore){
							 currentBestFitScore = currentScores[currentBestIndex];
							 publish(currentBest); //publish the best monkey only if it is better than the last monkey
						 }
						 
						 
						 
						 for(int i=0; i< currentPopulation.length/2; i++){
							 
							 int parent1 = MonkeyLogic.randomParent(totalWeight, breedingWeights);
							 int parent2 = MonkeyLogic.randomParent(totalWeight, breedingWeights);
							 String child1;
							 String child2;
							 
							 
							 
							 
							 if(Math.random() < crossoverProb){
								
								
								int crossoverIndex = (int) ((Math.random()) * currentPopulation[parent1].length());
								child1 = currentPopulation[parent1].substring(0, crossoverIndex) + currentPopulation[parent2].substring(crossoverIndex);
								child2 = currentPopulation[parent2].substring(0, crossoverIndex) +currentPopulation[parent1].substring(crossoverIndex);
								
								
							}else{
								child1 = currentPopulation[parent1];
								child2 = currentPopulation[parent2];
								
							}
							 
						    if(Math.random() < mutationProb){
								 
								 
								int rand = (int)(Math.random()*child1.length());
						        StringBuilder s = new StringBuilder(child1);
								
								s.setCharAt(rand, Generator.getRandomChar());
								child1 = s.toString();
								
							}
							
							if(Math.random() < mutationProb){
								int rand = (int)(Math.random()*child2.length());
							
								StringBuilder s = new StringBuilder(child2);
								s.setCharAt(rand, Generator.getRandomChar());
								
								child2 = s.toString();
								
							}
								
								
							
							 
							 
							 nextPopulation[2*i] = child1;
						     nextPopulation[2*i + 1] = child2;
						     
							 
							 
						 }
				            
						 
						 currentBest = currentPopulation[currentBestIndex];
						 //currentBestFitScore = currentScores[currentBestIndex];
						 
						 
						 currentPopulation = nextPopulation; //recylce storage later
						 currentScores = Generator.GetFitnessScores(currentPopulation);
						 breedingWeights = Generator.GetBreedingWeights(currentScores);
						 totalWeight = Generator.GetTotalWeight(breedingWeights);
						 
						 Generator.incrementGenerations();
				                
				                
				                
				           
					 }
								
					 publish(currentBest);//finally publish the best monkey	
					 isComplete = true;
					 return null;
					
				}
				
				@Override
				protected void process(List<String> chunks) { 
					
				String recentValue = chunks.get(chunks.size()-1);
				
					
				generatedTextArea.setText(recentValue);
				
				
					
				}
				@Override
				protected void done() { 
				
				sw.cancel(true);
				btnStart.setText("Start");
			    targetTextArea.setEditable(true);
				generatedTextArea.setEditable(true);
				monkeysPerGenerationSizeTextField.setEditable(true);
				chckbxParallel.setEnabled(true);
				
			    
			    
			    
					
					
				}

			};
			evolutionWorker.execute();
			}
			
			
		//----------------------------------------------------------------------------------------------------------------

			
			//anonymous swing worker class, provides background timer.
		    sw = new SwingWorker<Void, String>(){
					@Override
					protected Void doInBackground() {
						
						publish(0 + "");
						
						
						for(int i=1; i<9999; i++){
							
							
							if(isCancelled()){
								break;
							}else{
								if(i%10 == 0){
								publish(i/10 + "");
								 
								
								}
								
							}
							
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									
									
								}
							
						}
						
						return null; 
					}
					@Override
					protected void process(List<String> chunks) { 
						
					String recentValue = chunks.get(chunks.size()-1);
					
						
					lblUpdateTime.setText(recentValue);
					lblUpdateGeneration.setText("" + Generator.getGenerations());
					
					try{
						int gps = Generator.getGenerations()/(Integer.parseInt(recentValue));
					
						lblUpdateGenerationPerSec.setText("" + gps);
					}catch(ArithmeticException e){
						
					}
					
						
					}
					@Override
					protected void done() { 
					
						
						targetTextArea.setEditable(true);
						generatedTextArea.setEditable(true);
						monkeysPerGenerationSizeTextField.setEditable(true);
						chckbxParallel.setEnabled(true);
						publish();
						Generator.resetGenerations();
						if(isComplete){
							generatedTextArea.setBackground(Color.green);
						}else{
						generatedTextArea.setBackground(Color.white);
						}
						btnStart.setText("Start");
						
					}};
				sw.execute();
				
				
				
				
		}else{//cancel button pressed
			btnStart.setText("Start");
			
			targetTextArea.setEditable(true);
			generatedTextArea.setEditable(true);
			monkeysPerGenerationSizeTextField.setEditable(true);
			chckbxParallel.setEnabled(true);
			generatedTextArea.setBackground(Color.white);
			sw.cancel(true);
			Generator.resetGenerations();
			
			if(isParallel){
			
				try{
					
					evolutionParallelWorker.cancel(true);
					evolutionWorker.cancel(true);
				}catch(NullPointerException e1){
					
				}
			
			
			
			}else{
				evolutionWorker.cancel(true);
			}
		}
		
		
	}

}









