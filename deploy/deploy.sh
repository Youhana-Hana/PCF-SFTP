#!/bin/bash
set -eu

export API=${CF_API}
export USERNAME=admin
export PASSWORD=${CF_PASSWORD}
export APP_NAME=${CF_APP_NAME:=bulk-sftp}
export ORG=${CF_ORG:=factory}
export SPACE=${CF_SPACE:=dev1}
export VARS_FILE=${CF_VARS_FILE:=dev_vars.yml}

cf login -a ${API} -u ${USERNAME} -p ${PASSWORD} -o ${ORG} -s ${SPACE} --skip-ssl-validation

cf delete ${APP_NAME} -f

cf push ${APP_NAME} -u process --no-start --vars-file ${VARS_FILE}

cf bind-service ${APP_NAME} NFS -c '{"uid":"XXXX", "gid":"XXXX", "mount":"/var/bulk"}'

cf start ${APP_NAME}