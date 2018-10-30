# Description
bulk-sftp copy files from a given sftp source to a given local destination directory. Story  [TFD-324] (https://jira.gtie.dell.com/browse/TFD-324)

# Behavior & Design decisions
1. Download all files from a given source folder to a given destination folder
2. Run a scheduler to check files based on given pattern
3. Skip copying files if the are exists and are same
4. Read configurations from application.yml
5. Log all exceptions as error
8. Move files from source sftp directory to cached directory, file name includes timestamp

# Clone
```bash
git clone https://gitlab-sdp.telecomitalia.local/factory-apps/bulk-sftp.git
```

# Build & Compile
```bash
mvn clean verify
```

# Deploy
```bash
cd deploy
CF_PASSWORD=<PCF PASSWORD> ./deploy.sh
```
## deploy to different org or space
```bash
cd deploy
CF_PASSWORD=<PCF PASSWORD> CF_ORG=<ORG> CF_SPACE=<SPACE> ./deploy.sh
```

## deploy to using different application name and environment variables
```bash
cd deploy
CF_PASSWORD=<PCF PASSWORD> CF_ORG=<ORG> CF_SPACE=<SPACE> CF_APP_NAME=<APP NAME> CF_VARS_FILE=<VARS FILE PATH> ./deploy.sh

# For example deploying to dettagliotraffic
CF_PASSWORD=<PCF PASSWORD> CF_ORG=collaudo-evolutivo CF_SPACE=collaudo-evolutivo CF_APP_NAME=bulk-sftp-dettagliotraffic CF_VARS_FILE=dettagliotraffic_vars.yml ./deploy.sh
```

# Configure intellij
Ensure that you define the active application properties profile to "dev", through Edit configuration, choose "Application"  then set VM options to "-Dspring.profiles.active=dev"