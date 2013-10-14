INSERT INTO `Configs`.`Global` (`name`, `value`) VALUES ('timeoutMS', '100');
INSERT INTO `Configs`.`Global` (`name`, `value`) VALUES ('serviceA', 'http://global:880');
INSERT INTO `Configs`.`Global` (`name`, `value`) VALUES ('serviceB', 'http://global:432');
INSERT INTO `Configs`.`Global` (`name`, `value`) VALUES ('numThreads', '100');
INSERT INTO `Configs`.`Global` (`name`, `value`) VALUES ('jarName', 'global.jar');
INSERT INTO `Configs`.`Global` (`name`, `value`) VALUES ('maxCon', '1000');

INSERT INTO `Configs`.`EnvironmentOverrides` (`name`, `value`, `environment`) VALUES ('invalid', 'nevershow', 'nullEnv');
INSERT INTO `Configs`.`EnvironmentOverrides` (`name`, `value`, `environment`) VALUES ('maxCon', '200', 'qa');
INSERT INTO `Configs`.`EnvironmentOverrides` (`name`, `value`, `environment`) VALUES ('maxCon', '1', 'dev');
INSERT INTO `Configs`.`EnvironmentOverrides` (`name`, `value`, `environment`) VALUES ('numThreads', '500', 'prod');
INSERT INTO `Configs`.`EnvironmentOverrides` (`name`, `value`, `environment`) VALUES ('serviceA', 'http://prod', 'prod');
INSERT INTO `Configs`.`EnvironmentOverrides` (`name`, `value`, `environment`) VALUES ('timeoutMS', '300', 'qa');
INSERT INTO `Configs`.`EnvironmentOverrides` (`name`, `value`, `environment`) VALUES ('timeoutMS', '10', 'dev');

INSERT INTO `Configs`.`ApplicationOverrides` (`name`, `value`, `environment`, `application`) VALUES ('timeoutMS', '1', 'dev', 'ssa');
INSERT INTO `Configs`.`ApplicationOverrides` (`name`, `value`, `environment`, `application`) VALUES ('timeoutMS', '1000000', 'prod', 'ssa');
INSERT INTO `Configs`.`ApplicationOverrides` (`name`, `value`, `environment`, `application`) VALUES ('maxCon', '150', 'qa', 'ssa');
INSERT INTO `Configs`.`ApplicationOverrides` (`name`, `value`, `environment`, `application`) VALUES ('serviceA', 'http://prod/ssa', 'prod', 'ssa');
INSERT INTO `Configs`.`ApplicationOverrides` (`name`, `value`, `environment`, `application`) VALUES ('serviceA', 'http://prod/sb', 'prod', 'sb');
INSERT INTO `Configs`.`ApplicationOverrides` (`name`, `value`, `environment`, `application`) VALUES ('jarName', 'sampleSsa.jar', 'prod', 'ssa');
