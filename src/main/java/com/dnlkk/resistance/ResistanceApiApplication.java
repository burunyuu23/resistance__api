package com.dnlkk.resistance;

import com.dnlkk.resistance.dto.GraphResponseDTO;
import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResistanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResistanceApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Создание типового отображения между классами ResistorMatrixWeightedGraph и GraphResponseDTO
		TypeMap<ResistorMatrixWeightedGraph, GraphResponseDTO> typeMap = modelMapper.createTypeMap(ResistorMatrixWeightedGraph.class, GraphResponseDTO.class);

		// Настройка маппинга поля graph
		typeMap.addMapping(ResistorMatrixWeightedGraph::toString, GraphResponseDTO::setGraph);

		return modelMapper;
	}

}
