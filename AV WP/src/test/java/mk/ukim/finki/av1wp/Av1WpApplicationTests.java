package mk.ukim.finki.av1wp;

import mk.ukim.finki.av1wp.model.Category;
import mk.ukim.finki.av1wp.model.Manufacturer;
import mk.ukim.finki.av1wp.model.Product;
import mk.ukim.finki.av1wp.model.Role;
import mk.ukim.finki.av1wp.service.CategoryService;
import mk.ukim.finki.av1wp.service.ManufacturerService;
import mk.ukim.finki.av1wp.service.ProductService;
import mk.ukim.finki.av1wp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class Av1WpApplicationTests {
	MockMvc mockMvc;

	@Autowired
	UserService userService;

	@Autowired
	ManufacturerService manufacturerService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	private static Category c1;
	private static Manufacturer m1;
	private static boolean dataInitialized = false;

	@BeforeEach
	public void setup(WebApplicationContext webApplicationContext){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		initData();
	}

	private void initData(){
		if (!dataInitialized) {
			c1 = categoryService.create("c1", "c1");
			categoryService.create("c2", "c2");
			m1 = manufacturerService.save("m1", "m1").get();
			manufacturerService.save("m2", "m2");
			String user = "user";
			String admin = "admin";
			userService.register(user, user, user, user, user, Role.ROLE_USER);
			userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN);
			dataInitialized = true;
		}
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetProduct() throws Exception {
		MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products");
		mockMvc.perform(productRequest)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("products"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("bodyContent", "products"))
				.andExpect(MockMvcResultMatchers.view().name("master-template"));
	}

	@Test
	public void testDeleteProducts() throws Exception {
		Product product = productService.save("test", 2.0, 2, c1.getId(), m1.getId()).get();
		MockHttpServletRequestBuilder productDeleteRequest = MockMvcRequestBuilders.delete("/products/delete/" + product.getId());
		mockMvc.perform(productDeleteRequest)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/products"));
	}
}
