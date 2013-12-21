/**
 * Build-Id: EMPTY
 */
package com.vraidsys.server;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vraidsys.server.data.FooModel;
import com.vraidsys.shared.FooObj;
import com.vraidsys.shared.constants.RequestMappingConstants;
import com.vraidsys.shared.helpers.StringExt;

/**
 * @author jzerbe
 */
@Controller
public class FooController {

    @Autowired
    private Validator validator;

    public static final String kErrorPrefix = "ERROR";
    public static final String kSuccessPrefix = "SUCCESS";

    @RequestMapping(value = RequestMappingConstants.kFoo,
                    method = RequestMethod.GET)
    public @ResponseBody
    List<FooObj> getListOfFooObj(final Principal thePrincipal,
                    final HttpServletResponse response) {
        if ((thePrincipal == null) || StringExt.isEmpty(thePrincipal.getName())) {
            return null;
        } else {
            final List<FooObj> aListOfApps = FooModel.get(thePrincipal
                            .getName());
            return aListOfApps;
        }
    }

    @RequestMapping(value = RequestMappingConstants.kFoo,
                    method = RequestMethod.POST)
    public @ResponseBody
    String putFooObj(@RequestBody final FooObj theFooObj,
                    final BindingResult theBindingResult) {
        this.validator.validate(theFooObj, theBindingResult);
        if (theBindingResult.hasErrors()) {
            return (FooController.kErrorPrefix + " Account was unable to be stored due to data errors :/");
        } else {
            // TODO: store in db
            return FooController.kSuccessPrefix;
        }
    }

    public void setValidator(final Validator theValidator) {
        this.validator = theValidator;
    }
}
