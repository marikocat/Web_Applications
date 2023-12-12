package com.my;

import com.my.util.DBConnector;
import com.my.util.UtilFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class DisplayTag extends TagSupport {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    private static final Logger log = LogManager.getLogger(DBConnector.class);

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String greeting = "";
        if (!name.equals("")) {
            greeting = "Hello, " + name + "!\n";
        }

        greeting += "Today is " + UtilFunctions.getCurrentDate();
        try {
            out.println(greeting);
        } catch (IOException e) {
            log.error("Cannot display tag");
            throw new IllegalStateException(e);
        }
        return SKIP_BODY;
    }
}
