
/* Application
 * 
 * 
 */





import java.awt.EventQueue;



public class App
{
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					
					//System.out.println(System.getProperty("java.runtime.version"));
					MonkeyFrame frame = new MonkeyFrame();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
