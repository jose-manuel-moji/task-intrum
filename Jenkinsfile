pipeline {
    agent any
    stages {
    	stage('Test') { 
            steps {
                sh 'mvn test' 
            }
        }
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}