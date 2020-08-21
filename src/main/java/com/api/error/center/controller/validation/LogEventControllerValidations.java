package com.api.error.center.controller.validation;

import com.api.error.center.dto.LogEventDtoToPageble;
import com.api.error.center.entity.Source;
import com.api.error.center.response.Response;
import com.api.error.center.util.DateUtil;
import org.springframework.data.domain.Page;

public abstract class LogEventControllerValidations {

    protected boolean validateFindAllByFiltersRequestParams(Long sourceId, Source source, String startDateIn, String endDateIn, Response<Page<LogEventDtoToPageble>> response) {
        if (!validateSourceExistenceFromRequestParam(sourceId, source, response) || !validateDatesFromRequestParams(startDateIn, endDateIn, response)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSourceExistenceFromRequestParam(Long sourceId, Source source, Response<Page<LogEventDtoToPageble>> response) {
        if (source == null && sourceId != null) {
            response.addError("source with id " + sourceId + " dont't exist");
            return false;
        } else
            return true;
    }

    private boolean validateDatesFromRequestParams(String startDateIn, String endDateIn, Response<Page<LogEventDtoToPageble>> response) {
        if ((startDateIn != null && !DateUtil.validateDateWithRegex(startDateIn)) || (endDateIn != null && !DateUtil.validateDateWithRegex(endDateIn))) {
            response.addError("date must be in the format: dd-MM-yyyy HH:mm:ss");
            return false;
        } else {
            return true;
        }
    }
}
