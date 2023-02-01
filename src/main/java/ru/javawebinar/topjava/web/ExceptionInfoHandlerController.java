package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 4)
public class ExceptionInfoHandlerController {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandlerController.class);


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView conflict(HttpServletRequest req, DataIntegrityViolationException e) {
//        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
//        if (rootMsg != null) {
//            String lowerCaseMsg = rootMsg.toLowerCase();
//            for (Map.Entry<String, String> entry : CONSTRAINS_I18N_MAP.entrySet()) {
//                if (lowerCaseMsg.contains(entry.getKey())) {
//                    return logAndGetErrorInfo(req, e, true, VALIDATION_ERROR, messageSourceAccessor.getMessage(entry.getValue()));
//                }
//            }
//        }

        HttpStatus httpStatus = HttpStatus.CONFLICT;
        Throwable rootCause = ValidationUtil.getRootCause(e);
        //ErrorInfo errorInfo = logAndGetErrorInfo(req, e, true, DATA_ERROR);
        ModelAndView mav = new ModelAndView("exception",
                Map.of("exception", rootCause, "message", rootCause.toString(), "status", httpStatus));
        mav.setStatus(httpStatus);
        return mav;
    }


    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.toString());
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String msg) {
        return logAndGetErrorInfo(req, new Exception(msg), logException, errorType);
    }
}