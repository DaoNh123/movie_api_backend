package com.example.backend_sem2.example;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.List;
import java.util.Properties;

public class TLSEmail {

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for TLS/STARTTLS: 587
	 */
	public static void main(String[] args) {

		List<Customer> customerList = List.of(
				new Customer(1L,"Linh Bui","maianhyl2@gmail.com",20,"hongchau-yenlac"),
				new Customer(2L,"Datcao","caothanhdat21112003@gmail.com",25,"dongda-hanoi")
		);
		for (Customer customer: customerList) {
			final String fromEmail = "caothanhdat21112003@gmail.com"; //requires valid gmail id
			final String password = "kkzg mddt dggf mach"; // correct password for gmail id
//			final String toEmail = customer.getEmail(); // can be any email id

			System.out.println("TLSEmail Start");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
			props.put("mail.smtp.port", "587"); //TLS Port
			props.put("mail.smtp.auth", "true"); //enable authentication
			props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

			//create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			Session session = Session.getInstance(props, auth);

			EmailUtil.sendEmail(session, customer.getEmail(), "Booking movie ticket successful !", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
					"<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n" +
					" <head>\n" +
					"  <meta charset=\"UTF-8\">\n" +
					"  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
					"  <meta name=\"x-apple-disable-message-reformatting\">\n" +
					"  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
					"  <meta content=\"telephone=no\" name=\"format-detection\">\n" +
					"  <title>New Template 2</title><!--[if (mso 16)]>\n" +
					"    <style type=\"text/css\">\n" +
					"    a {text-decoration: none;}\n" +
					"    </style>\n" +
					"    <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n" +
					"<xml>\n" +
					"    <o:OfficeDocumentSettings>\n" +
					"    <o:AllowPNG></o:AllowPNG>\n" +
					"    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
					"    </o:OfficeDocumentSettings>\n" +
					"</xml>\n" +
					"<![endif]--><!--[if !mso]><!-- -->\n" +
					"  <link href=\"https://fonts.googleapis.com/css2?family=Syne&display=swap\" rel=\"stylesheet\">\n" +
					"  <link href=\"https://fonts.googleapis.com/css2?family=Yeseva+One&display=swap\" rel=\"stylesheet\"><!--<![endif]--><!--[if !mso]><!-- -->\n" +
					"  <link href=\"https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i\" rel=\"stylesheet\">\n" +
					"  <link href=\"https://fonts.googleapis.com/css?family=Roboto:400,400i,700,700i\" rel=\"stylesheet\"><!--<![endif]-->\n" +
					"  <style type=\"text/css\">\n" +
					"#outlook a {\n" +
					"\tpadding:0;\n" +
					"}\n" +
					".es-button {\n" +
					"\tmso-style-priority:100!important;\n" +
					"\ttext-decoration:none!important;\n" +
					"}\n" +
					"a[x-apple-data-detectors] {\n" +
					"\tcolor:inherit!important;\n" +
					"\ttext-decoration:none!important;\n" +
					"\tfont-size:inherit!important;\n" +
					"\tfont-family:inherit!important;\n" +
					"\tfont-weight:inherit!important;\n" +
					"\tline-height:inherit!important;\n" +
					"}\n" +
					".es-desk-hidden {\n" +
					"\tdisplay:none;\n" +
					"\tfloat:left;\n" +
					"\toverflow:hidden;\n" +
					"\twidth:0;\n" +
					"\tmax-height:0;\n" +
					"\tline-height:0;\n" +
					"\tmso-hide:all;\n" +
					"}\n" +
					"@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:30px!important; text-align:left } h2 { font-size:24px!important; text-align:left } h3 { font-size:20px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important; text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:24px!important; text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:12px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:14px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:12px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:18px!important; display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0!important } .es-m-p0r { padding-right:0!important } .es-m-p0l { padding-left:0!important } .es-m-p0t { padding-top:0!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } .es-m-p5 { padding:5px!important } .es-m-p5t { padding-top:5px!important } .es-m-p5b { padding-bottom:5px!important } .es-m-p5r { padding-right:5px!important } .es-m-p5l { padding-left:5px!important } .es-m-p10 { padding:10px!important } .es-m-p10t { padding-top:10px!important } .es-m-p10b { padding-bottom:10px!important } .es-m-p10r { padding-right:10px!important } .es-m-p10l { padding-left:10px!important } .es-m-p15 { padding:15px!important } .es-m-p15t { padding-top:15px!important } .es-m-p15b { padding-bottom:15px!important } .es-m-p15r { padding-right:15px!important } .es-m-p15l { padding-left:15px!important } .es-m-p20 { padding:20px!important } .es-m-p20t { padding-top:20px!important } .es-m-p20r { padding-right:20px!important } .es-m-p20l { padding-left:20px!important } .es-m-p25 { padding:25px!important } .es-m-p25t { padding-top:25px!important } .es-m-p25b { padding-bottom:25px!important } .es-m-p25r { padding-right:25px!important } .es-m-p25l { padding-left:25px!important } .es-m-p30 { padding:30px!important } .es-m-p30t { padding-top:30px!important } .es-m-p30b { padding-bottom:30px!important } .es-m-p30r { padding-right:30px!important } .es-m-p30l { padding-left:30px!important } .es-m-p35 { padding:35px!important } .es-m-p35t { padding-top:35px!important } .es-m-p35b { padding-bottom:35px!important } .es-m-p35r { padding-right:35px!important } .es-m-p35l { padding-left:35px!important } .es-m-p40 { padding:40px!important } .es-m-p40t { padding-top:40px!important } .es-m-p40b { padding-bottom:40px!important } .es-m-p40r { padding-right:40px!important } .es-m-p40l { padding-left:40px!important } }\n" +
					"@media screen and (max-width:384px) {.mail-message-content { width:414px!important } }\n" +
					"</style>\n" +
					" </head>\n" +
					" <body style=\"width:100%;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n" +
					"  <div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"en\" style=\"background-color:#FFFFFF\"><!--[if gte mso 9]>\n" +
					"\t\t\t<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
					"\t\t\t\t<v:fill type=\"tile\" color=\"#ffffff\" origin=\"0.5, 0\" position=\"0.5, 0\"></v:fill>\n" +
					"\t\t\t</v:background>\n" +
					"\t\t<![endif]-->\n" +
					"   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FFFFFF\">\n" +
					"     <tr>\n" +
					"      <td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
					"       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
					"         <tr>\n" +
					"          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
					"           <table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#fff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\">\n" +
					"             <tr>\n" +
					"              <td class=\"es-m-p20r es-m-p20l\" align=\"left\" style=\"padding:0;Margin:0\">\n" +
					"               <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
					"                 <tr>\n" +
					"                  <td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n" +
					"                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
					"                     <tr>\n" +
					"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#252222;font-size:16px\"><img class=\"adapt-img\" src=\"https://ecyedqq.stripocdn.email/content/guids/CABINET_c7c91cb14bb8159e41e26920340ae77f9ace79a9302dd09dd77b1ae1c1ba6086/images/sweet_bakery.png\" alt=\"Logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"600\" title=\"Logo\"></a></td>\n" +
					"                     </tr>\n" +
					"                   </table></td>\n" +
					"                 </tr>\n" +
					"               </table></td>\n" +
					"             </tr>\n" +
					"             <tr>\n" +
					"              <td class=\"es-m-p20r es-m-p20l\" align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-top:30px;padding-bottom:40px;padding-left:40px;padding-right:40px;background-color:#ffffff\">\n" +
					"               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
					"                 <tr>\n" +
					"                  <td class=\"es-m-p0r es-m-p20b\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\">\n" +
					"                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#fce5cd\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;background-color:#fce5cd;border-radius:30px\">\n" +
					"                     <tr>\n" +
					"                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:20px;padding-left:30px;padding-right:30px\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:'Yeseva One', Arial, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#333333\"><br>Your movie ticket booking has been successful !</h1></td>\n" +
					"                     </tr>\n" +
					"                     <tr>\n" +
					"                      <td align=\"left\" style=\"padding:0;Margin:0;padding-left:30px;padding-right:30px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:27px;color:#333333;font-size:18px\"><strong>AVENGER : END GAME</strong></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:24px;color:#333333;font-size:16px\"><b>Showtime:</b></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:24px;color:#333333;font-size:16px\"><em>Friday,26 February,2023</em></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:24px;color:#333333;font-size:16px\"><em>19:30 ~ 22:32&nbsp;</em></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:24px;color:#333333;font-size:16px\"><b>Your seat:</b></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:24px;color:#333333;font-size:16px\"><i>C3 , C4</i></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:24px;color:#333333;font-size:16px\"><b>Your Booking Id:</b></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:roboto, 'helvetica neue', helvetica, arial, sans-serif;line-height:24px;color:#333333;font-size:16px\"><b>12gabd67f98j</b><em>(Please take this code to staff in the cinema!)</em></p></td>\n" +
					"                     </tr>\n" +
					"                     <tr>\n" +
					"                      <td align=\"center\" style=\"Margin:0;padding-bottom:10px;padding-top:30px;padding-left:30px;padding-right:30px;font-size:0\">\n" +
					"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
					"                         <tr>\n" +
					"                          <td style=\"padding:0;Margin:0;border-bottom:2px dashed #bf9000;background:unset;height:1px;width:100%;margin:0px\"></td>\n" +
					"                         </tr>\n" +
					"                       </table></td>\n" +
					"                     </tr>\n" +
					"                     <tr>\n" +
					"                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:15px;padding-left:30px;padding-right:30px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'playfair display', georgia, 'times new roman', serif;line-height:24px;color:#333333;font-size:16px\">Thank you for booking tickets at our cinema !</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'playfair display', georgia, 'times new roman', serif;line-height:24px;color:#333333;font-size:16px\">We look forward to serving you again soon!</p></td>\n" +
					"                     </tr>\n" +
					"                     <tr>\n" +
					"                      <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#252222;font-size:16px\"><img src=\"https://ecyedqq.stripocdn.email/content/guids/CABINET_02973bbb2e632e00462ca20be2f25971f783da8ba416b631beb63111c7a2fa2c/images/p_avengersendgame_19751_e14a0104.jpeg\" alt=\"Iris Mccormick\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" title=\"Iris Mccormick\" width=\"475\"></a></td>\n" +
					"                     </tr>\n" +
					"                     <tr>\n" +
					"                      <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-left:30px;padding-right:30px;padding-bottom:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'playfair display', georgia, 'times new roman', serif;line-height:24px;color:#333333;font-size:16px\">Thank you so much !</p></td>\n" +
					"                     </tr>\n" +
					"                   </table></td>\n" +
					"                 </tr>\n" +
					"               </table></td>\n" +
					"             </tr>\n" +
					"           </table></td>\n" +
					"         </tr>\n" +
					"       </table></td>\n" +
					"     </tr>\n" +
					"   </table>\n" +
					"  </div>\n" +
					"  <div class=\"ddict_btn\" style=\"top:1206px;left:425.188px\">\n" +
					"   <img src=\"chrome-extension://bpggmmljdiliancllaapiggllnkbjocb/logo/48.png\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\">\n" +
					"  </div>\n" +
					" </body>\n" +
					"</html>");

		}

	}

	
}