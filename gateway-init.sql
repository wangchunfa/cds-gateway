-- ----------------------------
-- Records of gateway properties
-- ----------------------------
INSERT INTO `properties` VALUES ('filter.PostLogFilter.filterOrder', '31', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('filter.PostLogFilter.pattern', '(^/product1/.*)|(^/product2/.*)', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('filter.PreLogFilter.filterOrder', '20', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('filter.PreLogFilter.pattern', '(^/product1/.*)|(^/product2/.*)', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service1.ribbon.ConnectTimeout', '3000', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service1.ribbon.listOfServers', '127.0.0.1:8771, 127.0.0.1:8772', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service1.ribbon.MaxAutoRetries', '1', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service1.ribbon.MaxAutoRetriesNextServer', '1', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service1.ribbon.NFLoadBalancerRuleClassName', 'com.netflix.loadbalancer.BestAvailableRule', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service1.ribbon.ReadTimeout', '6000', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service2.ribbon.ConnectTimeout', '3000', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service2.ribbon.listOfServers', '127.0.0.1:8773, 127.0.0.1:8774', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service2.ribbon.MaxAutoRetries', '1', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service2.ribbon.MaxAutoRetriesNextServer', '1', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service2.ribbon.NFLoadBalancerRuleClassName', 'com.netflix.loadbalancer.BestAvailableRule', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('product-service2.ribbon.ReadTimeout', '6000', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('ribbon.eager-load.clients', 'product-service1, product-service2', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('ribbon.eager-load.enabled', 'true', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('ribbon.eureka.enabled', 'false', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.decodeUrl', 'false', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.forceOriginalQueryStringEncoding', 'true', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.ratelimit.enabled', 'false', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.ratelimit.policies.product-service1.limit', '100000', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.ratelimit.policies.product-service1.refresh-interval', '60', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.ratelimit.policies.product-service1.type', 'origin', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.ratelimit.policies.product-service2.limit', '100000', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.ratelimit.policies.product-service2.refresh-interval', '60', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.ratelimit.policies.product-service2.type', 'origin', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.retryable', 'true', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.routes.product-service1.path', '/product1/**', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.routes.product-service1.serviceId', 'product-service1', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.routes.product-service2.path', '/product2/**', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.routes.product-service2.serviceId', 'product-service2', 'cds-gateway', 'test', 'master');
INSERT INTO `properties` VALUES ('zuul.sensitive-headers', '', 'cds-gateway', 'test', 'master');