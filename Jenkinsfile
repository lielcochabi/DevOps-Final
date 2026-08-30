pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Deploy to Tomcat') {
            when {
                not { triggeredBy 'TimerTrigger' }
            }
            steps {
                echo 'Deploying application to Tomcat...'
                bat '''
                    ssh -i C:\\Users\\user\\.ssh\\oracle_vm_key -o StrictHostKeyChecking=no opc@84.13.82.200 "mkdir -p /opt/tomcat/webapps/DevOps_Liel_Almog_Almog_Stav_Meshi"
                    scp -i C:\\Users\\user\\.ssh\\oracle_vm_key -o StrictHostKeyChecking=no index.jsp opc@84.13.82.200:/opt/tomcat/webapps/DevOps_Liel_Almog_Almog_Stav_Meshi/
                    scp -i C:\\Users\\user\\.ssh\\oracle_vm_key -o StrictHostKeyChecking=no welcome.jsp opc@84.13.82.200:/opt/tomcat/webapps/DevOps_Liel_Almog_Almog_Stav_Meshi/
                '''
            }
        }

        stage('Availability Monitor API') {
            steps {
                echo 'Checking UptimeRobot Monitor via API...'
                bat '''
                curl -X POST -s ^
                    -H "Content-Type: application/x-www-form-urlencoded" ^
                    -d "api_key=ur3685461-b27e0236b1c8fc6a5144f1d8&format=json&monitors=803853314" ^
                    https://api.uptimerobot.com/v2/getMonitors
                '''
            }
        }

        stage('Automation UI Tests') {
            when {
                not { triggeredBy 'TimerTrigger' }
            }
            steps {
                echo 'Setting up Python and running UI validations on Windows...'
                bat '''
                    python -m venv venv
                    call venv\\Scripts\\activate.bat
                    pip install pytest pytest-playwright
                    playwright install chromium --with-deps
                    pytest automation.py -v --log-cli-level=INFO --junitxml=report.xml
                '''
            }
            post {
                always {
                    echo 'UI Tests finished. Generating test reports...'
                    junit 'report.xml'
                }
            }
        }
    }
}