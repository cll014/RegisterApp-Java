package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/productDetail")
public class ProductDetailRouteController extends BaseRouteController{
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(@RequestParam Map<String, String> queryParameters, final HttpServletRequest request) {
		final Optional<ActiveUserEntity> user = this.getCurrentUser(request);

		if(!user.isPresent()) {
			return this.buildInvalidSessionResponse();
		}
		else if (!this.isElevatedUser(user.get())){
			return this.buildNoPermissionsResponse(ViewNames.PRODUCT_LISTING.getRoute());
		}
	
		final ModelAndView modelAndView = this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName()), queryParameters);
		modelAndView.addObject(ViewModelNames.IS_ELEVATED_USER.getValue(), true);
		modelAndView.addObject(ViewModelNames.PRODUCT.getValue(), (new Product()).setLookupCode(StringUtils.EMPTY).setCount(0));

		return modelAndView;

	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView startWithProduct(@PathVariable final UUID productId, @RequestParam Map<String, String> queryParameters, final HttpServletRequest request) {
		final Optional<ActiveUserEntity> user = this.getCurrentUser(request);


		if (!user.isPresent()) {
			return this.buildInvalidSessionResponse();
		} 
		
		final ModelAndView modelAndView = this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName()), queryParameters);
			
		modelAndView.addObject(ViewModelNames.IS_ELEVATED_USER.getValue(), EmployeeClassification.isElevatedUser(user.get().getClassification()));

			try {
				modelAndView.addObject(
						ViewModelNames.PRODUCT.getValue(),
						this.productQuery.setProductId(productId).execute());
			} catch (final Exception e) {
				modelAndView.addObject(
						ViewModelNames.ERROR_MESSAGE.getValue(),
						e.getMessage());
				modelAndView.addObject(
						ViewModelNames.PRODUCT.getValue(),
						(new Product())
								.setCount(0)
								.setLookupCode(StringUtils.EMPTY));
			}

			return modelAndView;

	}

	// Properties
	@Autowired
	private ProductQuery productQuery;
}
