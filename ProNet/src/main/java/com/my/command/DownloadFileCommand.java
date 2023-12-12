package com.my.command;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.my.domain.Tariff;
import com.my.domain.TariffPlan;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class DownloadFileCommand implements Command {
    private static final Logger log = LogManager.getLogger(DownloadFileCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        String pathToApp = req.getServletContext().getRealPath("/");
        System.out.println(pathToApp);
        long userId = Long.parseLong(req.getParameter("userId"));
        TariffPlan tariffPlan;
        List<Tariff> tariffs;
        try {
            tariffPlan = ServiceManager.getInstance().findTariffPlanByUserId(userId);
            tariffs = ServiceManager.getInstance().findAllTariffsByPlanId(tariffPlan.getId());
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Tariff Plan")
            .append(System.getProperty("line.separator"))
            .append(System.getProperty("line.separator"))
            .append("Start Date: ")
            .append(tariffPlan.getStartDate())
            .append(System.getProperty("line.separator"))
            .append("End Date: ")
            .append(tariffPlan.getEndDate())
            .append(System.getProperty("line.separator"))
            .append("Cost: ")
            .append(tariffPlan.getCost())
            .append(" UAH")
            .append(System.getProperty("line.separator"))
            .append("Status: ")
            .append(tariffPlan.getStatus().getName())
            .append(System.getProperty("line.separator"))
            .append(System.getProperty("line.separator"))
            .append("Tariffs:")
            .append(System.getProperty("line.separator"))
            .append(System.getProperty("line.separator"));
        for (Tariff t : tariffs) {
            sb.append(t.getService().getName())
                .append(" - ")
                .append(t.getName())
                .append(": ")
                .append(t.getDescription())
                .append(" Cost: ")
                .append(t.getCost())
                .append(" UAH")
                .append(System.getProperty("line.separator"))
                .append(System.getProperty("line.separator"));
        }

        String fileAddress = "/pdf/tariff_plan_" + tariffPlan.getId() + ".pdf";
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathToApp + fileAddress));
            document.open();
            document.addTitle("Tariff Plan");
            document.add(new Paragraph(sb.toString()));
            document.close();
            writer.close();
            return fileAddress;
        } catch (DocumentException e) {
            log.error("Cannot write to a file", e);
            throw new AppException("Cannot download a file!", e);
        } catch (FileNotFoundException e) {
            log.error("Cannot find a file", e);
            throw new AppException("Cannot download a file!", e);
        }

        /*
        try (FileWriter fw = new FileWriter(pathToApp + fileAddress)) {
            fw.write(sb.toString());
            return fileAddress;
        } catch (IOException e) {
            log.error("Cannot download a file with a tariff", e);
            throw new AppException("Cannot download a file!", e);
        }
         */
    }
}
