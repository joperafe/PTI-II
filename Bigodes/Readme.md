No ficheiro versao_case_V0.0.java falta implementar as políticas de controlo de memória/performance.
Entenda-se por política de performance:
  - uma função que compare TTLs e descarte pacotes fora de prazo ou iguais;
  - content store com o fifo, para estabelecer um certo limite de dados;
  - pacotes de interesse duplicados são descartados, caso ttl seja igual.
  
  
