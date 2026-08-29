pipeline {
    agent any

    stages {
        stage('Deploy to Tomcat') {
            steps {
                echo 'Deploying application to Tomcat...'
                // IMPORTANT: Replace this with your actual deployment command!
                // For example, if you just copy JSP files:
                // sh 'cp *.jsp /opt/tomcat/webapps/DevOps_Liel_Almog_Almog_Stav_Meshi/'
                
                // Give Tomcat 10 seconds to load the updated files
                sleep time: 10, unit: 'SECONDS'
            }
        }
        
        stage('Automation UI Tests') {
            steps {
                echo 'Setting up Python and running UI validations...'
                sh '''
                    # Create and activate a clean virtual environment
                    python3 -m venv venv
                    . venv/bin/activate
                    
                    # Install testing libraries
                    pip install pytest pytest-playwright
                    
                    # Install the headless Chromium browser and Linux dependencies
                    playwright install chromium --with-deps
                    
                    # Run the test script and generate a report
                    pytest test_ui.py -v --log-cli-level=INFO --junitxml=report.xml
                '''
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline finished. Generating test reports...'
            // This tells Jenkins to read the report.xml file and create a graph
            junit 'report.xml'
        }
    }
}
