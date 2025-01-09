#!/bin/bash

echo "debconf debconf/frontend select noninteractive" | debconf-set-selections

apt-get update --quiet

apt-get dist-upgrade --quiet --assume-yes

apt-get install \
    --quiet \
    --assume-yes \
    --no-install-recommends \
    bash \
    wget \
    iproute2 \
    less \
    dnsutils \
    procps \
    sysstat