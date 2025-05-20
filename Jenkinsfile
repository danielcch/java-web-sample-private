pipeline {
    agent any

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
        CUSTOM_COMMANDS_ENABLED = credentials('CUSTOM_COMMANDS_ENABLED') // O define como variable global
        CUSTOM_VAR = credentials('CUSTOM_VAR') // O define como variable global
    }

    options {
        skipDefaultCheckout()
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Cache Maven Dependencies') {
            steps {
                // Jenkins puede cachear usando un directorio persistente, pero aquí solo se muestra la estructura
                echo 'Maven dependencies will be cached in .m2/repository'
            }
        }
        stage('Set up JDK and Matrix Build') {
            matrix {
                axes {
                    axis {
                        name: 'JAVA_VERSION'
                        values: '8', '11', '17'
                    }
                }
                stages {
                    stage('Set JDK') {
                        steps {
                            script {
                                if (env.JAVA_VERSION == '8') {
                                    tool name: 'jdk8', type: 'jdk'
                                } else if (env.JAVA_VERSION == '11') {
                                    tool name: 'jdk11', type: 'jdk'
                                } else {
                                    tool name: 'jdk17', type: 'jdk'
                                }
                            }
                        }
                    }
                    stage('Check Dependencies') {
                        steps {
                            sh 'mvn -B dependency:analyze'
                        }
                    }
                    stage('Dependency Vulnerability Check') {
                        steps {
                            sh 'mvn -B org.owasp:dependency-check-maven:check'
                        }
                    }
                    stage('Run Tests') {
                        steps {
                            script {
                                try {
                                    sh 'mvn -B test'
                                } catch (err) {
                                    currentBuild.result = 'UNSTABLE'
                                    if (env.CHANGE_ID) {
                                        // Notifica en el PR (requiere plugin de GitHub)
                                        echo '❌ Los tests fallaron en la matriz de versiones de Java. Por favor revisa los logs del build.'
                                    }
                                    throw err
                                }
                            }
                        }
                    }
                    stage('Custom commands on self-hosted') {
                        when {
                            expression { env.CUSTOM_COMMANDS_ENABLED == 'true' && env.NODE_LABELS?.contains('self-hosted') }
                        }
                        steps {
                            script {
                                echo "Ejecutando comandos personalizados en runner self-hosted"
                                if (env.CUSTOM_VAR) {
                                    echo "CUSTOM_VAR está definido: ${env.CUSTOM_VAR}"
                                    // Agrega aquí tus comandos personalizados
                                }
                            }
                        }
                    }
                }
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B package'
            }
        }
    }
    post {
        failure {
            script {
                // Notificación a Slack (requiere plugin de Slack)
                slackSend (color: '#FF0000', message: "El pipeline falló: ${env.BUILD_URL}")
                // Notificación a Teams (requiere integración externa)
                echo "Notificar a Teams: El pipeline falló: ${env.BUILD_URL}"
            }
        }
    }
}
