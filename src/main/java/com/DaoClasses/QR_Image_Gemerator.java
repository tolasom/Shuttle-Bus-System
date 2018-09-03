package com.DaoClasses;

import java.awt.image.BufferedImage;  
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;
import java.io.File;  
import java.io.IOException;  
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;  

import javax.imageio.ImageIO;  

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.apache.commons.codec.binary.Base64;  

import com.EntityClasses.Booking_Master;
public class QR_Image_Gemerator {  
     /**  
      * @param args  
      */  
     public static void main(String[] args) throws IOException {  
          // TODO Auto-generated method stub  
          Scanner s=new Scanner(System.in);  
          System.out.println("Enter base64 string to be converted to image"); 
          String base64="iVBORw0KGgoAAAANSUhEUgAAAH0AAAB9AQAAAACn+1GIAAAApElEQVR42u3VMQ7DMAwDQP6A//+lxm4qxbZoPZpOtwQGnNxg2JSMoNfngRsugALQgKcYPN5TCoVqlqcTqOIFUGcwQ9/raTdB6XrN39Q3wc9saqn+HhQbZM2cg96ISYk5tMNRqRCDDkZH9Ak5AfeKztfIYTp/9oaOwcXWkqwDeOWszTGH6Xr4CpzB2jAZqNDf6gdgU/P+XuRdmJCbBDuG+x/1b3gCOSaFcLrWXbIAAAAASUVORK5CYII";
          //String base64=s.nextLine();  
          byte[] base64Val=convertToImg(base64);  
          System.out.println(base64Val);
          writeByteToImageFile(base64Val, "D:/Directory1/6.png");  
          System.out.println("Saved the base64 as image in current directory with name image.png");  
          
          
          
        
     }  
     public Boolean qr_generator(Booking_Master bm) {        
    	System.out.println(bm.getQr());
    	//QR---> source_id+destin_id+dept_date+dept_time+booing_master_id
    	String qr_code=	bm.getSource_id()
    				+bm.getDestination_id()
    				+dateToString(bm.getDept_date())
    				+timeToString(bm.getDept_time())
    				+bm.getId();
        ByteArrayOutputStream out = QRCode.from(qr_code).to(ImageType.PNG).stream();  
  		byte[] test = out.toByteArray();
  		String encodedImage = java.util.Base64.getEncoder().encodeToString(test);
         
        byte[] base64Val;
		try {
			//Create New Folder
			new File("D:\\Directory_Test").mkdir();
			
			base64Val = convertToImg(encodedImage);
			writeByteToImageFile(base64Val, "D:/Directory_Test/"+bm.getQr_name()+".png");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		return true;  
     } 
     public static byte[] convertToImg(String base64) throws IOException  
     {  
          return Base64.decodeBase64(base64);  
     }  
     public static void writeByteToImageFile(byte[] imgBytes, String imgFileName) throws IOException  
     {  
    	 System.out.println(imgBytes);
          File imgFile = new File(imgFileName);  
          BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));  
          ImageIO.write(img, "png", imgFile);  
     }  
     
     
     public static String dateToString(Date date){
 		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
 		return f.format(date);
 	}

 	public static String timeToString(Date date){
 		SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
 		return f.format(date);
 	}
}
