package org.codeforamerica.htmxtypeahead;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
  @GetMapping("/")
  public String index() {
    return "index";
  }

  @HxRequest
  @PostMapping("/search")
  public HtmxResponse htmxSearch(
      @RequestParam("q") String q
  ) throws IOException {
    Reader reader = Files.newBufferedReader(Paths.get(new ClassPathResource("data/mock.csv").getURI()));

    CsvToBean<Provider> csvToBean = new CsvToBeanBuilder<Provider>(reader)
        .withType(Provider.class)
        .withIgnoreLeadingWhiteSpace(true)
        .build();

    List<Provider> providers = csvToBean.parse();

    int threshold = 2;
    List<Provider> searchedProviders = providers.stream()
        .filter(provider -> isFuzzyMatch(q, provider, threshold))
        .collect(Collectors.toList());

    HashMap<String, Object> model = new HashMap<>();
    model.put("searchedProviders", searchedProviders);
    
    return HtmxResponse.builder()
            .view(new ModelAndView("search", model))
            .build();
  }

  // This is not the best search, but it's serviceable for a quick demo
  public boolean isFuzzyMatch(String searchParam, Provider provider, int threshold) {
    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    if (levenshteinDistance.apply(searchParam,  provider.getId()) <= threshold) {
      return true;
    }
    if (levenshteinDistance.apply(searchParam,  provider.getName()) <= threshold) {
      return true;
    }
    if (levenshteinDistance.apply(searchParam,  provider.getEmail()) <= threshold) {
      return true;
    }
    if (levenshteinDistance.apply(searchParam, provider.getPhone()) <= threshold) {
      return true;
    }
    if (levenshteinDistance.apply(searchParam, provider.getAddress()) <= threshold) {
      return true;
    }

    return false;
  }
}
