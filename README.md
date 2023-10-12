# robot-java
Las clases para trabajar con el Robot en Introducción a la Programación I


# Configuración de Java 1.8 para Ejecutar un Robot en Visual Studio Code

Siga estos pasos para configurar Java 1.8 y ejecutar un robot en Visual Studio Code:

1. Diríjase a la opción "Descargar nuevo JDK" en Visual Studio Code y seleccione Java 8.

2. Una vez descargado, descomprima el archivo `OpenJDK8U-jdk_x64_linux_hotspot_8u382b05.tar.gz`.

3. Esto le dará una carpeta llamada `jdk8u382-b05`.

4. Cree una nueva carpeta para contener esta versión de JDK:
   ```bash
   mkdir ~/jdk
   cp ~/Downloads/jdk8u382-b05 ~/jdk
   ```
 5. En Visual Studio Code, cree una nueva carpeta llamada `.vscode` y un archivo llamado `settings.json`.
    
-   Copie el siguiente archivo de configuración JSON en `settings.json`:
```json
{
    "java.home": "~/jdk/jdk8u382-b05",
    "java.configuration.runtimes": [
      {
        "name": "JavaSE-1.8",
        "path": "~/jdk/jdk8u382-b05"
      }
    ]
}
```
6. Debería funcionar. Ahora busque 'Java Runtime' en la paleta de comandos (`ctrl + shift + p`). Debería mostrar "1.8" en la configuración del robot.
    

¡Con estos pasos, habrá configurado Java 1.8 para ejecutar su robot en Visual Studio Code!

