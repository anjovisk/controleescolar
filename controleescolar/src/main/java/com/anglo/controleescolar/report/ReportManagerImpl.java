package com.anglo.controleescolar.report;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anglo.controleescolar.notification.email.MailSenderService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class ReportManagerImpl implements ReportManager {
	private String applicationPath;
	private String reportDir;
	private JasperReport architectureTestReport;
	
	@Autowired
	public ReportManagerImpl() {
		try {
			applicationPath = URLDecoder.decode(MailSenderService.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
			reportDir = applicationPath + File.separator + "reports";
			String architectureReportPath = String.format("%s/%s", reportDir, "architecture-test.jasper");
			architectureTestReport = (JasperReport)JRLoader.loadObjectFromFile(architectureReportPath);			
		} catch (UnsupportedEncodingException | JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] exportArchitectureTestReport(Object[] items) {
		return exportReportToPdf(architectureTestReport, items, null);
	}
	
	private byte[] exportReportToPdf(JasperReport report, Object[] items, Map<String, Object> properties) {
		try {
			if (properties == null) {
				properties =  new HashMap<>();
			}
			JRDataSource dataSource = new JRBeanArrayDataSource(items);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("SUBREPORT_DIR", reportDir);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
}