#!/usr/bin/env bash

export BOOT_VERSION=2.4.4
export JAVA_VERSION=11
export GROUP=learn.fady

mkdir microservices
cd microservices

spring init \
--boot-version=$BOOT_VERSION \
--build=gradle \
--java-version=$JAVA_VERSION \
--packaging=jar \
--name=product-service \
--package-name=$GROUP.microservices.core.product \
--groupId=$GROUP.microservices.core.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-service

spring init \
--boot-version=$BOOT_VERSION \
--build=gradle \
--java-version=$JAVA_VERSION \
--packaging=jar \
--name=review-service \
--package-name=$GROUP.microservices.core.review \
--groupId=$GROUP.microservices.core.review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
review-service

spring init \
--boot-version=$BOOT_VERSION \
--build=gradle \
--java-version=$JAVA_VERSION \
--packaging=jar \
--name=recommendation-service \
--package-name=$GROUP.microservices.core.recommendation \
--groupId=$GROUP.microservices.core.recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
recommendation-service

spring init \
--boot-version=$BOOT_VERSION \
--build=gradle \
--java-version=$JAVA_VERSION \
--packaging=jar \
--name=product-composite-service \
--package-name=$GROUP.microservices.composite.product \
--groupId=$GROUP.microservices.composite.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-composite-service

cd ..
