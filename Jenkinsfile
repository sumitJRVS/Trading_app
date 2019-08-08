pipeline {
    agent any
    tools {
        maven "M3"
    }

    environment {
        app_name = 'trading_app'

    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn c
                ean package -DskipTests'
                echo "app_name is ${env.app_name} "
                archiveArtifacts 'target/*zip'
            }
        }
        stage('Deploy_dev') {
            when { branch 'development' }
            steps {
                echo "Current Branch is: ${env.GIT_BRANCH}"
                sh "bash ./eb/eb_deploy.sh TradingApp-dev"
            }
        }
        stage('Deploy_prod') {
            when { branch 'master' }
            steps {
                echo "Current Branch is: ${env.GIT_BRANCH}"
                sh "./eb/eb_deploy.sh TradingApp-prod"
            }
        }
    }
}