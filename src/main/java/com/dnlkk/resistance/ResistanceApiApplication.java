package com.dnlkk.resistance;

import com.dnlkk.resistance.dto.responses.ErrorResponseDTO;
import com.dnlkk.resistance.dto.responses.GraphResponseDTO;
import com.dnlkk.resistance.exceptions.AppException;
import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class ResistanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResistanceApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Создание типового отображения между классами ResistorMatrixWeightedGraph и GraphResponseDTO
		TypeMap<ResistorMatrixWeightedGraph, GraphResponseDTO> typeMapGraph = modelMapper.createTypeMap(ResistorMatrixWeightedGraph.class, GraphResponseDTO.class);
		// Настройка маппинга поля graph
		typeMapGraph.addMapping(ResistorMatrixWeightedGraph::toString, GraphResponseDTO::setGraph);

		TypeMap<AppException, ErrorResponseDTO> typeMapException = modelMapper.createTypeMap(AppException.class, ErrorResponseDTO.class);
		typeMapException.addMappings(mapper -> {
			mapper.map(AppException::getMessage, ErrorResponseDTO::setMessage);
			mapper.map(AppException::getStatus, ErrorResponseDTO::setStatus);
		});

		return modelMapper;
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}

}
