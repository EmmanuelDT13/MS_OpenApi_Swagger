package com.emmanuel.swagger.infraestructure.feign.clients;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.emmanuel.swagger.infraestructure.feign.beans.CelularFallback;
import com.emmanuel.swagger.configuration.FeignConfiguration;
import com.emmanuel.swagger.infraestructure.feign.dtoresponse.Celular;

@FeignClient(name="ICelularClient", url="${external.feign.apiphone.host}", configuration=FeignConfiguration.class,
fallback=CelularFallback.class)
public interface ICelularClient {
	
	@GetMapping("${external.feign.apiphone.getallcellphones}")
	public List<Celular> getCelularesFromFeignCliente();
	
	@GetMapping(path="${external.feign.apiphone.getone}")
	public Celular getCelularFromFeignClient();
	
}

