package com.hand.demo.domain.service.impl;

import org.hzero.boot.report.app.IReportService;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
public class ReportServiceImpl implements IReportService {

    @Override
    public ByteArrayOutputStream execute(Map<String, String> params) {
        return null;
    }

    @Override
    public String filename() {
        return IReportService.super.filename();
    }
}
