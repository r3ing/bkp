/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `auditoria` */

CREATE TABLE `auditoria` (
  `LAST_MOVTO` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `COD_USUARIO` int(11) NOT NULL COMMENT 'C�digo do usu�rio que efetuou a opera��o',
  `NOME_USUARIO` varchar(255) NOT NULL COMMENT 'Nome do usu�rio que efetuou a opera��o',
  `SISTEMA` varchar(255) NOT NULL COMMENT 'Sistema que originou a opera��o',
  `TIPO_OPERACAO` varchar(255) NOT NULL COMMENT 'Legenda com a opera��o efetuada',
  `TIPO_REGISTRO` varchar(255) NOT NULL,
  `DT_MOVTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Data e Hora que a oper��o foi efetuada',
  `VALOR_ANTERIOR` varchar(255) NOT NULL COMMENT 'Valor do campo antes da opera��o ser efetuada',
  `VALOR_NOVO` varchar(255) NOT NULL COMMENT 'Valor do campo ap�s da opera��o ser efetuada',
  `HISTORICO` varchar(255) NOT NULL COMMENT 'Dados sobre a opera��o',
  `DOC_CODIGO` varchar(255) NOT NULL COMMENT 'N�mero do documento ou c�digo do registro que a opera��o foi efetuada',
  PRIMARY KEY (`COD_USUARIO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com log para auditoria das opera�oes de exclus�o';

/*Table structure for table `cidade` */

CREATE TABLE `cidade` (
  `LAST_MOVTO` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'C�digo Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Nome da Cidade',
  `CODIGO_IBGE` varchar(255) NOT NULL COMMENT 'C�digo IBGE do Munic�pio',
  `COD_UF` int(11) NOT NULL COMMENT 'C�digo UF do Munic�pio',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela de Cidades';

/*Table structure for table `compartilhamento` */

CREATE TABLE `compartilhamento` (
  `LAST_MOVTO` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `COD_EMPCOMPARTILHADA` int(11) NOT NULL COMMENT 'Lista com os c�digos das empresas compartilhadas com a empresa',
  `MOD_FUNCAO` int(11) NOT NULL COMMENT 'Fun��o a ser compartilhada',
  PRIMARY KEY (`COD_EMPCOMPARTILHADA`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com os compartilhamentos de dados entre as empresas';

/*Table structure for table `config` */

CREATE TABLE `config` (                                                                                                                             
          `LAST_MOVTO` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',                                                       
          `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',                                                 
          `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',                                      
          `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',                                                                          
          `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',                                  
          `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',                                                                         
          `IMG_LOGOMARCA` text COMMENT 'Imagem de LogoMarca da Empresa',                                                                                    
          `SEG_FLAG_ALTSENHAPERIO` int(11) DEFAULT NULL COMMENT 'Flag que defini se � obrigat�rio a troca de senha periodicamente',                       
          `SEG_PRAZO_ALTERASENHA` int(11) DEFAULT NULL COMMENT 'Prazo para obrigar a altera��o de senha periodica',                                       
          `SEG_QTDCARACTER` int(11) DEFAULT NULL COMMENT 'Quantidade m�nima de caracteres contidos na senha do usu�rio',                                  
          `SEG_SEQDIGITOS` int(11) DEFAULT NULL COMMENT 'Flag que defini se a senha pode conter sequ�ncia de dig�tos',                                    
          `SEG_FLAG_CONTEMLETRA` int(11) DEFAULT NULL COMMENT 'Flag que defini se � obrigat�rio conter Letras na senha do usu�rio',                      
          `SEG_FLAG_REUTILIZAPWD` int(11) DEFAULT NULL COMMENT 'Flag que defini se o usu�rio pode utilizar uma das 3 �ltimas senhas ao trocar de senha',  
          PRIMARY KEY (`CODEMP`,`RECORD_NO`)                                                                                                                
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com as configura�oes gerais do Sistema';  

/*Table structure for table `departamento` */

CREATE TABLE `departamento` (
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro inativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'C�digo Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Descri��o do Departamento',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabela de Cadastros de Departamentos de Estoque';

/*Table structure for table `empresa` */

CREATE TABLE `empresa` (
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `DT_IMPLANTACAO` timestamp NULL DEFAULT NULL COMMENT 'Data da implanta��o do sistema',
  `CODIGO` int(11) NOT NULL COMMENT 'C�digo Interno do registro',
  `NOME_FANTASIA` varchar(255) DEFAULT NULL COMMENT 'Nome Fantasia',
  `RAZAO_SOCIAL` varchar(255) DEFAULT NULL COMMENT 'Raz�o Social',
  `DESC_REDUZIDA` char(5) DEFAULT NULL COMMENT 'Sigla com iniciais (Abreviatura)',
  `ENDERECO` varchar(255) DEFAULT NULL COMMENT 'Endere�o',
  `END_NUMERO` varchar(255) DEFAULT NULL COMMENT 'N�mero do Endere�o',
  `BAIRRO` varchar(255) DEFAULT NULL COMMENT 'Bairro',
  `FONE` varchar(255) DEFAULT NULL COMMENT 'Telefone',
  `CELULAR` varchar(255) DEFAULT NULL COMMENT 'N�mero do Fax',
  `COD_CIDADE` int(11) DEFAULT NULL COMMENT 'C�digo da Cidade',
  `CIDADE` varchar(255) DEFAULT NULL COMMENT 'Nome da Cidade',
  `UF` char(5) DEFAULT NULL COMMENT 'Unidade Federativa',
  `CEP` varchar(255) DEFAULT NULL COMMENT 'C�digo Postal',
  `CNPJ` varchar(255) DEFAULT NULL COMMENT 'CNPJ Inscri��o de Pessoa Jur�dica',
  `IE` varchar(255) DEFAULT NULL COMMENT 'Inscri��o Estadual\n',
  `INC_MUNICIPAL` varchar(255) DEFAULT NULL COMMENT 'Inscri��o Municipal',
  `SUFRAMA` varchar(255) DEFAULT NULL COMMENT 'N�mero de Inscri��o da Suframa',
  `CNAE_FISCAL` varchar(255) DEFAULT NULL COMMENT 'C�digo CNAE Principal',
  `NO_SERIE` varchar(255) DEFAULT NULL COMMENT 'N�mero de S�rie do Sistema (Licen�a de Uso)',
  `VERSAO` varchar(255) DEFAULT NULL COMMENT 'Vers�o Atual do Sistema\n',
  `SISTEMA` varchar(255) DEFAULT NULL COMMENT 'Vers�o Disenho do Sistema',
  `CENTRAL_SAC` varchar(255) DEFAULT NULL COMMENT 'Central de Antedimento ao Cliente (Revenda Eptus)',
  `TELEFONE_SAC` varchar(255) DEFAULT NULL COMMENT 'Telefone da Revenda Respons�vel por atender o Cliente',
  `COD_REGIMETRIB` int(11) DEFAULT NULL COMMENT 'Codigo do regime tributario da mepresa',
  `IND_TIPOATIV` int(11) DEFAULT NULL COMMENT 'Flag Indicador de tipo de Atividade da Empresa',
  `COD_NATJURIDICA` varchar(255) DEFAULT NULL COMMENT 'Codigo do tipo de Natureza Jur�dica da Empresa\n',
  `COD_ENQUADRAPORTE` int(11) DEFAULT NULL COMMENT 'C�digo do Porte que se enquadra a Empresa',
  `WEB_HOMEPAGE` varchar(255) DEFAULT NULL COMMENT 'Url do Site da Empresa',
  `LINHA_ADICIONAL` varchar(255) DEFAULT NULL,
  `EMAIL_NOME` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Nome para exibi��o',
  `EMAIL_EMAIL` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Endere�o de Email',
  `EMAIL_HOSTSAI` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Endere�o do Host de saida SMTP',
  `EMAIL_PORTSAI` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Porta de saida SMTP',
  `EMAIL_FLAGAUTENTICACAO` char(5) DEFAULT NULL COMMENT 'Conf. Email - Flag Host exige autentica��o',
  `EMAIL_LOGIN` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Login',
  `EMAIL_PASSWORD` varchar(255) DEFAULT NULL COMMENT 'Conf. Email - Senha',
  `CRC_CHECKSYS` varchar(255) DEFAULT NULL COMMENT 'Chave Criptografada usada para Autenticar licen�a instalada na Empresa',
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
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `COD_USUARIO` int(11) NOT NULL COMMENT 'C�digo do usu�rio',
  `FLAG_VIEW` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permiss�o de visualizar o nivel de acesso',
  `FLAG_INSERT` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permiss�o de inserir dados referente ao nivel de acesso',
  `FLAG_UPDATE` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permiss�o de alterar dados do nivel acesso',
  `FLAG_DISABLE` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permiss�o de inativar(deletar) dados do nivel de acesso',
  `FLAG_ENABLE` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permiss�o de reativar dados do nivel de acesso',
  `FLAG_PRINT` int(11) DEFAULT NULL COMMENT 'Flag que define se o usuario tem permiss�o de imprimir dados do nivel de acesso',
  `FLAG_MENUCADASTRO` int(11) DEFAULT NULL COMMENT 'Flag que define se o menu da acesso a um cadastro)',
  `MENU` varchar(255) DEFAULT NULL COMMENT 'C�digo do Menu definido nos niveis de acesso do usu�rio',
  PRIMARY KEY (`COD_USUARIO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela com as defini��es de n�vel de acesso do usu�rio';

/*Table structure for table `pais` */

CREATE TABLE `pais` (
  `LAST_MOVTO` timestamp NULL  DEFAULT CURRENT_TIMESTAMP COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'C�digo Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Nome do Pa�s',
  `CODIGO_BACEN` varchar(255) NOT NULL COMMENT 'C�digo Bacen do Pa�s',
  `CODIGO_SISCOMEX` varchar(255) NOT NULL COMMENT 'C�digo Siscomex do Pa�s',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela de Paises';

/*Table structure for table `sequencial` */

CREATE TABLE `sequencial` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `RECORD_NUMBER` int(11) DEFAULT NULL COMMENT 'Parametro que controla a sequ�ncia da Coluna Record_No em todas as tabelas',
  `RECORD_SESSION` int(11) DEFAULT NULL COMMENT 'Parametro que controla a sequ�ncia de cada sess�o do mysql',
  `RECORD_DELETE` int(11) DEFAULT NULL COMMENT 'Parametro que controla a sequ�ncia de cada sess�o do mysql usada na coluna Check_Delete',
  `CP_UNIDADE` int(11) DEFAULT NULL,
  `CP_DEPARTAMENTO` int(11) DEFAULT NULL,
  `UT_USUARIO` int(11) DEFAULT NULL,
  PRIMARY KEY (`CODEMP`,`RECORD_NO`),
  KEY `empresa_idxUTC` (`UTCTAG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela que controla a sequencia dos c�digos internos nos cad';

/*Table structure for table `uf` */

CREATE TABLE `uf` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'C�digo Interno do registro',
  `DESCRICAO` varchar(255) DEFAULT NULL COMMENT 'Nome do Estado(UF)',
  `SIGLA` char(5) DEFAULT NULL COMMENT 'Sigla do Estado(UF)',
  `CODIGO_IBGE` int(11) NOT NULL COMMENT 'C�digo IBGE do Estado(UF)',
  `COD_PAIS` int(11) NOT NULL COMMENT 'C�digo do Pa�s',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Tabela de Estados(UF)';

/*Table structure for table `unidade` */

CREATE TABLE `unidade` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` char(5) DEFAULT NULL COMMENT 'Flag para marcar registro intativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'C�digo Interno do registro',
  `DESCRICAO` char(5) DEFAULT NULL COMMENT 'Sigla da Unidade',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabela com as unidades de medidas';

/*Table structure for table `usuario` */

  CREATE TABLE `usuario` (
  `LAST_MOVTO` timestamp NULL DEFAULT NULL COMMENT 'Data da �ltima altera��o no registro',
  `LAST_CODUSER` int(11) DEFAULT NULL COMMENT 'Usu�rio que fez a �ltima altera��o no registro',
  `CHECK_DELETE` int(11) DEFAULT NULL COMMENT 'Chave usada para associar registro que possuem relacionamento',
  `RECORD_NO` int(11) NOT NULL COMMENT 'Identificador �nico do registro',
  `UTCTAG` varchar(255) DEFAULT NULL COMMENT 'Data e Hora da �ltima altera��o no registro em formato UnixTime',
  `FLAG_ATIVO` int(11) DEFAULT NULL COMMENT 'Flag para marcar registro inativos',
  `CODEMP` int(11) NOT NULL COMMENT 'Empresa onde o registro foi inserido',
  `CODIGO` int(11) NOT NULL COMMENT 'C�digo Interno do registro',
  `NOME` varchar(255) DEFAULT NULL COMMENT 'Nome do Usu�rio',
  `PWD` varchar(255) DEFAULT NULL COMMENT 'Senha Atual',
  `PWD1` varchar(255) DEFAULT NULL COMMENT '�ltima Senha Usada',
  `PWD2` varchar(255) DEFAULT NULL COMMENT 'Pen�ltima senha usada',
  `PWD3` varchar(255) DEFAULT NULL COMMENT 'Ante-Pen�ltima senha usada',
  `COD_FUNCIONARIO` int(11) DEFAULT NULL COMMENT 'C�digo do Funcion�rio associado ao usu�rio',
  `COD_EMPDISPLOGIN` varchar(255) DEFAULT NULL COMMENT 'Empresas disponiveis para logir',
  `IDIOMA` char(5) DEFAULT NULL COMMENT 'Idioma utilizado pelo usu�rio',
  `ACTION_DEFNIVACESSO` int(11) DEFAULT NULL COMMENT 'Flag que define a a��o do usu�rio - Define Nivel de Acesso',
  `ACTION_COPYUSUARIO` int(11) DEFAULT NULL COMMENT 'Flag que define a a��o do usu�rio - Copia dados do usu�rio',
  `ACTION_COPYNIVACESSO` int(11) DEFAULT NULL COMMENT 'Flag que define a a��o do usu�rio - Copia N�vel de Acesso',
  `CONF_FLAG_ALTDTMOVTO` int(11) DEFAULT NULL COMMENT 'Flag que define se o usu�rio pode alterar a data de movimento do sistema',
  `CONF_DIAS_RETRODTMOVTO` int(11) DEFAULT NULL COMMENT 'Quantidade de dias que o usu�rio pode retroagir a data de movimento do sistema',
  `CONF_DIAS_AVANDTMOVTO` int(11) DEFAULT NULL COMMENT 'Quantidade de dias que o usu�rio pode avan�ar a data de movimento do sistema',
  PRIMARY KEY (`CODIGO`,`CODEMP`,`RECORD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1  COMMENT='Tabela com Usu�rios do Sistema';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
