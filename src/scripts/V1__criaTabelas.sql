/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `auditoria` */

CREATE TABLE `auditoria` (
  `LAST_MOVTO` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `COD_USUARIO` int(11) NOT NULL COMMENT 'Código do usuário que efetuou a operação',
  `NOME_USUARIO` varchar(255) NOT NULL COMMENT 'Nome do usuário que efetuou a operação',
  `SISTEMA` varchar(255) NOT NULL COMMENT 'Sistema que originou a operação',
  `TIPO_OPERACAO` varchar(255) NOT NULL COMMENT 'Legenda com a operação efetuada',
  `TIPO_REGISTRO` varchar(255) NOT NULL,
  `DT_MOVTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Data e Hora que a operção foi efetuada',
  `VALOR_ANTERIOR` varchar(255) NOT NULL COMMENT 'Valor do campo antes da operação ser efetuada',
  `VALOR_NOVO` varchar(255) NOT NULL COMMENT 'Valor do campo após da operação ser efetuada',
  `HISTORICO` varchar(255) NOT NULL COMMENT 'Dados sobre a operação',
  `DOC_CODIGO` varchar(255) NOT NULL COMMENT 'Número do documento ou código do registro que a operação foi efetuada',
  PRIMARY KEY (`COD_USUARIO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com log para auditoria das operaçoes de exclusão';

/*Table structure for table `cidade` */

CREATE TABLE `cidade` (
  `LAST_MOVTO` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'Código Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Nome da Cidade',
  `CODIGO_IBGE` varchar(255) NOT NULL COMMENT 'Código IBGE do Município',
  `COD_UF` int(11) NOT NULL COMMENT 'Código UF do Município',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela de Cidades';

/*Table structure for table `compartilhamento` */

CREATE TABLE `compartilhamento` (
  `LAST_MOVTO` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `COD_EMPCOMPARTILHADA` int(11) NOT NULL COMMENT 'Lista com os códigos das empresas compartilhadas com a empresa',
  `MOD_FUNCAO` int(11) NOT NULL COMMENT 'Função a ser compartilhada',
  PRIMARY KEY (`COD_EMPCOMPARTILHADA`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com os compartilhamentos de dados entre as empresas';

/*Table structure for table `config` */

CREATE TABLE `config` (                                                                                                                             
          `LAST_MOVTO` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',                                                       
          `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',                                                 
          `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',                                      
          `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',                                                                          
          `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',                                  
          `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',                                                                         
          `IMG_LOGOMARCA` text COMMENT 'Imagem de LogoMarca da Empresa',                                                                                    
          `SEG_FLAG_ALTSENHAPERIO` int(11) DEFAULT NULL COMMENT 'Flag que defini se é obrigatório a troca de senha periodicamente',                       
          `SEG_PRAZO_ALTERASENHA` int(11) DEFAULT NULL COMMENT 'Prazo para obrigar a alteração de senha periodica',                                       
          `SEG_QTDCARACTER` int(11) DEFAULT NULL COMMENT 'Quantidade mínima de caracteres contidos na senha do usuário',                                  
          `SEG_SEQDIGITOS` int(11) DEFAULT NULL COMMENT 'Flag que defini se a senha pode conter sequência de digítos',                                    
          `SEG_FLAG_CONTEMLETRA` int(11) DEFAULT NULL COMMENT 'Flag que defini se é obrigatório conter Letras na senha do usuário',                      
          `SEG_FLAG_REUTILIZAPWD` int(11) DEFAULT NULL COMMENT 'Flag que defini se o usuário pode utilizar uma das 3 últimas senhas ao trocar de senha',  
          PRIMARY KEY (`CODEMP`,`RECORD_NO`)                                                                                                                
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com as configuraçoes gerais do Sistema';  

/*Table structure for table `departamento` */

CREATE TABLE `departamento` (
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro inativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'Código Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Descrição do Departamento',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabela de Cadastros de Departamentos de Estoque';

/*Table structure for table `empresa` */

CREATE TABLE `empresa` (
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `DT_IMPLANTACAO` timestamp NULL DEFAULT NULL COMMENT 'Data da implantação do sistema',
  `CODIGO` int(11) NOT NULL COMMENT 'Código Interno do registro',
  `NOME_FANTASIA` varchar(255) DEFAULT NULL COMMENT 'Nome Fantasia',
  `RAZAO_SOCIAL` varchar(255) DEFAULT NULL COMMENT 'Razão Social',
  `DESC_REDUZIDA` char(5) DEFAULT NULL COMMENT 'Sigla com iniciais (Abreviatura)',
  `ENDERECO` varchar(255) DEFAULT NULL COMMENT 'Endereço',
  `END_NUMERO` varchar(255) DEFAULT NULL COMMENT 'Número do Endereço',
  `BAIRRO` varchar(255) DEFAULT NULL COMMENT 'Bairro',
  `FONE` varchar(255) DEFAULT NULL COMMENT 'Telefone',
  `CELULAR` varchar(255) DEFAULT NULL COMMENT 'Número do Fax',
  `COD_CIDADE` int(11) DEFAULT NULL COMMENT 'Código da Cidade',
  `CIDADE` varchar(255) DEFAULT NULL COMMENT 'Nome da Cidade',
  `UF` char(5) DEFAULT NULL COMMENT 'Unidade Federativa',
  `CEP` varchar(255) DEFAULT NULL COMMENT 'Código Postal',
  `CNPJ` varchar(255) DEFAULT NULL COMMENT 'CNPJ Inscrição de Pessoa Jurídica',
  `IE` varchar(255) DEFAULT NULL COMMENT 'Inscrição Estadual\n',
  `INC_MUNICIPAL` varchar(255) DEFAULT NULL COMMENT 'Inscrição Municipal',
  `SUFRAMA` varchar(255) DEFAULT NULL COMMENT 'Número de Inscrição da Suframa',
  `CNAE_FISCAL` varchar(255) DEFAULT NULL COMMENT 'Código CNAE Principal',
  `NO_SERIE` varchar(255) DEFAULT NULL COMMENT 'Número de Série do Sistema (Licença de Uso)',
  `VERSAO` varchar(255) DEFAULT NULL COMMENT 'Versão Atual do Sistema\n',
  `SISTEMA` varchar(255) DEFAULT NULL COMMENT 'Versão Disenho do Sistema',
  `CENTRAL_SAC` varchar(255) DEFAULT NULL COMMENT 'Central de Antedimento ao Cliente (Revenda Eptus)',
  `TELEFONE_SAC` varchar(255) DEFAULT NULL COMMENT 'Telefone da Revenda Responsável por atender o Cliente',
  `COD_REGIMETRIB` int(11) DEFAULT NULL COMMENT 'Codigo do regime tributario da mepresa',
  `IND_TIPOATIV` int(11) DEFAULT NULL COMMENT 'Flag Indicador de tipo de Atividade da Empresa',
  `COD_NATJURIDICA` varchar(255) DEFAULT NULL COMMENT 'Codigo do tipo de Natureza Jurídica da Empresa\n',
  `COD_ENQUADRAPORTE` int(11) DEFAULT NULL COMMENT 'Código do Porte que se enquadra a Empresa',
  `WEB_HOMEPAGE` varchar(255) DEFAULT NULL COMMENT 'Url do Site da Empresa',
  `LINHA_ADICIONAL` varchar(255) DEFAULT NULL,
  `EMAIL_NOME` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Nome para exibição',
  `EMAIL_EMAIL` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Endereço de Email',
  `EMAIL_HOSTSAI` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Endereço do Host de saida SMTP',
  `EMAIL_PORTSAI` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Porta de saida SMTP',
  `EMAIL_FLAGAUTENTICACAO` char(5) DEFAULT NULL COMMENT 'Conf. Email - Flag Host exige autenticação',
  `EMAIL_LOGIN` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Login',
  `EMAIL_PASSWORD` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Senha',
  `CRC_CHECKSYS` varchar(255) DEFAULT NULL COMMENT 'Chave Criptografada usada para Autenticar licença instalada na Empresa',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com os dados da Empresa';

/*Table structure for table `log` */

CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comando` varchar(255) DEFAULT NULL,
  `tabela` varchar(255) DEFAULT NULL,
  `fecha` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `nivel_acesso` */

CREATE TABLE `nivel_acesso` (
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `COD_USUARIO` int(11) NOT NULL COMMENT 'Código do usuário',
  `FLAG_VIEW` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permissão de visualizar o nivel de acesso',
  `FLAG_INSERT` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permissão de inserir dados referente ao nivel de acesso',
  `FLAG_UPDATE` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permissão de alterar dados do nivel acesso',
  `FLAG_DISABLE` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permissão de inativar(deletar) dados do nivel de acesso',
  `FLAG_ENABLE` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permissão de reativar dados do nivel de acesso',
  `FLAG_PRINT` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permissão de imprimir dados do nivel de acesso',
  `FLAG_MENUCADASTRO` int(11) DEFAULT NULL COMMENT 'Flag que define se o menu da acesso a um cadastro)',
  `MENU` varchar(255) DEFAULT NULL COMMENT 'Código do Menu definido nos niveis de acesso do usuário',
  PRIMARY KEY (`COD_USUARIO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com as definições de nível de acesso do usuário';

/*Table structure for table `pais` */

CREATE TABLE `pais` (
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'Código Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Nome do País',
  `CODIGO_BACEN` varchar(255) NOT NULL COMMENT 'Código Bacen do País',
  `CODIGO_SISCOMEX` varchar(255) NOT NULL COMMENT 'Código Siscomex do País',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela de Paises';

/*Table structure for table `sequencial` */

CREATE TABLE `sequencial` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `RECORD_NUMBER` int(11) DEFAULT NULL COMMENT 'Parametro que controla a sequência da Coluna Record_No em todas as tabelas',
  `RECORD_SESSION` int(11) DEFAULT NULL COMMENT 'Parametro que controla a sequência de cada sessão do mysql',
  `RECORD_DELETE` int(11) DEFAULT NULL COMMENT 'Parametro que controla a sequência de cada sessão do mysql usada na coluna Check_Delete',
  `CP_UNIDADE` int(11) DEFAULT NULL,
  `CP_DEPARTAMENTO` int(11) DEFAULT NULL,
  `UT_USUARIO` int(11) DEFAULT NULL,
  PRIMARY KEY (`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela que controla a sequencia dos códigos internos nos cad';

/*Table structure for table `uf` */

CREATE TABLE `uf` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'Código Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Nome do Estado(UF)',
  `SIGLA` char(5) DEFAULT NULL COMMENT 'Sigla do Estado(UF)',
  `CODIGO_IBGE` int(11) NOT NULL COMMENT 'Código IBGE do Estado(UF)',
  `COD_PAIS` int(11) NOT NULL COMMENT 'Código do País',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela de Estados(UF)';

/*Table structure for table `unidade` */

CREATE TABLE `unidade` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'Código Interno do registro',
  `DESCRICAO` char(5) DEFAULT NULL COMMENT 'Sigla da Unidade',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabela com as unidades de medidas';

/*Table structure for table `usuario` */

  CREATE TABLE `usuario` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da última alteração no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usuário que fez a última alteração no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador único do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da última alteração no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro inativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'Código Interno do registro',
  `NOME` varchar(255) DEFAULT NULL COMMENT 'Nome do Usuário',
  `PWD` varchar(255) DEFAULT NULL COMMENT 'Senha Atual',
  `PWD1` varchar(255) DEFAULT NULL COMMENT 'Última Senha Usada',
  `PWD2` varchar(255) DEFAULT NULL COMMENT 'Penúltima senha usada',
  `PWD3` varchar(255) DEFAULT NULL COMMENT 'Ante-Penúltima senha usada',
  `COD_FUNCIONARIO` int(11) DEFAULT NULL COMMENT 'Código do Funcionário associado ao usuário',
  `COD_EMPDISPLOGIN` varchar(255) DEFAULT NULL COMMENT 'Empresas disponiveis para logir',
  `IDIOMA` char(5) DEFAULT NULL COMMENT 'Idioma utilizado pelo usuário',
  `ACTION_DEFNIVACESSO` int(11) DEFAULT NULL COMMENT 'Flag que define a ação do usuário - Define Nivel de Acesso',
  `ACTION_COPYUSUARIO` int(11) DEFAULT NULL COMMENT 'Flag que define a ação do usuário - Copia dados do usuário',
  `ACTION_COPYNIVACESSO` int(11) DEFAULT NULL COMMENT 'Flag que define a ação do usuário - Copia Nível de Acesso',
  `CONF_FLAG_ALTDTMOVTO` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuário pode alterar a data de movimento do sistema',
  `CONF_DIAS_RETRODTMOVTO` int(11) DEFAULT NULL COMMENT 'Quantidade de dias que o usuário pode retroagir a data de movimento do sistema',
  `CONF_DIAS_AVANDTMOVTO` int(11) DEFAULT NULL COMMENT 'Quantidade de dias que o usuário pode avançar a data de movimento do sistema',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1  COMMENT='Tabela com Usuários do Sistema';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
