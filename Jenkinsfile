pipeline {
    agent any

    // Trigger to run every 5 minutes
    triggers {
        cron('H/5 * * * *')
    }

    stages {
        stage('Deploy to Tomcat') {
            // Only run this stage if NOT triggered by the 5-minute timer
            when {
                not { triggeredBy 'TimerTrigger' }
            }
            steps {
                echo 'Deploying application to Tomcat...'
                // IMPORTANT: Replace this with your actual Windows deployment command!
                // bat 'copy *.jsp "C:\\Path\\To\\Tomcat\\webapps\\DevOps_Liel_Almog_Almog_Stav_Meshi\\" /Y'
                
                sleep time: 10, unit: 'SECONDS'
            }
        }
        
        stage('Availability Monitor API') {
            // No 'when' condition means this stage ALWAYS runs (both on timer and manual/push)
            steps {
                echo 'Checking UptimeRobot Monitor via API...'
                bat '''
                curl -X POST -s ^
                     -H "Content-Type: application/x-www-form-urlencoded" ^
                     -d "api_key=ur3685461-b27e0236b1c8fc6a5144f1d8&format=json&monitors=803853314" ^
                     https://api.uptimerobot.com/v2/getMonitors | findstr "\"status\":2"
                '''
            }
        }
        
        stage('Automation UI Tests') {
            // Only run this stage if NOT triggered by the 5-minute timer
            when {
                not { triggeredBy 'TimerTrigger' }
            }
            steps {
                echo 'Setting up Python and running UI validations on Windows...'
                bat '''
                    :: Create a clean virtual environment
                    python -m venv venv
                    
                    :: Activate the virtual environment
                    call venv\\Scripts\\activate.bat
                    
                    :: Install testing libraries
                    pip install pytest pytest-playwright
                    
                    :: Install the headless Chromium browser
                    playwright install chromium --with-deps
                    
                    :: Run the test script and generate a report
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
