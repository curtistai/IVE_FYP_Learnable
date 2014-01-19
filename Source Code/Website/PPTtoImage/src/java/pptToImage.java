/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;

/**
 *
 * @author Yiu
 */
public class pptToImage extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String id="1";


        if (true) {

            InputStream filecontent = null;
            SlideShow ppt = null;


            boolean correctFileType = false;

            ArrayList<String> imglist = new ArrayList<String>();
            ArrayList<String> imgpreview = new ArrayList<String>();

            jdbcmysql db = new jdbcmysql();
            db.execute();
            String host = "192.168.1.104";



            /*Get Powerpoint file*/

            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();

                        if(fieldname.equals("pid")){
                            id = item.getString();
                        }
                        
                        // ... (do your job here)
                        out.print(fieldname);
                    } else {
                        // Process form file field (input type="file").
                        String fieldname = item.getFieldName();
                        String filename = FilenameUtils.getName(item.getName());
                       
                        filecontent = item.getInputStream();

                       

                        if (filename.length() > 4) {
                            if (filename.substring(filename.length() - 3, filename.length()).equalsIgnoreCase("ppt")) {
                                correctFileType = true;
                            }
                        }

                        if (correctFileType) {
                            ppt = new SlideShow(item.getInputStream());
                        }
                    }
                }


            } catch (FileUploadException e) {
                throw new ServletException("Cannot parse multipart request.", e);
            }


            if (!correctFileType) {
                RequestDispatcher rd;                        
                        rd = getServletContext().getRequestDispatcher("/index.jsp");
                        rd.forward(request, response);
                

            }


            if (correctFileType) {
                File file = new File(id + ".txt");
                BufferedWriter writer = null;
                FileWriter fstream = new FileWriter(file, false);
                writer = new BufferedWriter(fstream);




                try {


                    Dimension pgsize = ppt.getPageSize();

                    Slide[] slide = ppt.getSlides();
                    for (int i = 0; i < slide.length; i++) {

                        BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                        Graphics2D graphics = img.createGraphics();
                        //clear the drawing area
                        graphics.setPaint(Color.white);
                        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                        slide[i].draw(graphics);



                        //save the output
                        File temp = new File(id + "_slide-" + (i + 1) + ".png");
                        javax.imageio.ImageIO.write(img, "png", temp);
                        UploadPPt up = new UploadPPt(temp, "http://"+host+"/learnable/ppt/androidUpload.php");
                        String link = up.execute();
                        writer.write(link);
                        imglist.add("http://"+host+"/learnable"+link);
                        writer.newLine();

                        // Create new (blank) image of required (scaled) size

                        BufferedImage scaledImage = new BufferedImage(
                                500, pgsize.height * 500 / pgsize.width, BufferedImage.TYPE_INT_ARGB);



                        Graphics2D smallimg = scaledImage.createGraphics();
                        smallimg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        smallimg.drawImage(img, 0, 0, 500, pgsize.height * 500 / pgsize.width, null);


                        smallimg.dispose();


                        temp = new File(id+"_slide-" + (i + 1) + "s.jpg");
                        javax.imageio.ImageIO.write(scaledImage, "png", temp);
                        up = new UploadPPt(temp, "http://"+host+"/learnable/ppt/androidUpload.php");
                        link = up.execute();
                        imgpreview.add("http://"+host+"/learnable"+link);





                    }
                } finally {
                    writer.close();
                    if (correctFileType) {
                        UploadPPt up = new UploadPPt(file, "http://"+host+"/learnable/ppt/androidUpload.php");

                        db.updatePPtpath(up.execute(), Integer.parseInt(id));
                        RequestDispatcher rd;

                        request.setAttribute("links", imglist);
                        request.setAttribute("plinks", imgpreview);
                        request.setAttribute("id", id);
                        rd = getServletContext().getRequestDispatcher("/display.jsp");
                        rd.forward(request, response);
                    }


                    out.close();




                }
            } else {
                RequestDispatcher rd;
                request.setAttribute("wrong", "");
                rd = getServletContext().getRequestDispatcher("/index.jsp");

            }
        } else {
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
