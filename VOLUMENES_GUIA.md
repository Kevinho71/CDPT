# 📁 Guía de Volúmenes Docker - CADET Backend

## ¿Dónde se Almacenan los Volúmenes?

### En Docker Desktop (Windows/Mac)
Los volúmenes Docker se guardan en ubicaciones específicas del sistema:

**Windows:**
```
C:\Users\{tu-usuario}\AppData\Local\Docker\wsl\data\ext4.vhdx
```
Los datos están dentro de un disco virtual de WSL2.

**Para acceder al contenido:**

#### Opción 1: Desde Docker Desktop (GUI)
1. Abre Docker Desktop
2. Ve a la sección "Volumes"
3. Verás los volúmenes: `cadet-backend_socios_logos`, `cadet-backend_empresas_logos`, etc.
4. Click en cada volumen → "Data" → "Inspect in Terminal"

#### Opción 2: Desde WSL2 (Línea de comandos)
```bash
# Acceder a WSL2
wsl

# Ver los volúmenes
ls /var/lib/docker/volumes/

# Acceder a un volumen específico
cd /var/lib/docker/volumes/cadet-backend_socios_logos/_data

# Listar archivos
ls -la
```

#### Opción 3: Copiar archivos entre host y volumen
```bash
# Copiar desde el volumen al host
docker run --rm -v cadet-backend_socios_logos:/source -v ${PWD}:/backup alpine tar -czf /backup/socios_logos_backup.tar.gz -C /source .

# Copiar desde el host al volumen
docker run --rm -v cadet-backend_socios_logos:/dest -v ${PWD}:/source alpine sh -c "cd /source && cp -r * /dest/"
```

---

## En Coolify (Servidor Linux)

Cuando despliegues en Coolify, los volúmenes se crearán en:

```
/var/lib/docker/volumes/{nombre-proyecto}_{volumen}_data/_data/
```

**Ejemplo:**
```
/var/lib/docker/volumes/cadet-backend_socios_logos/_data/
/var/lib/docker/volumes/cadet-backend_empresas_logos/_data/
/var/lib/docker/volumes/cadet-backend_empresas_catalogos/_data/
```

### Acceder en el servidor de Coolify:
```bash
# SSH al servidor
ssh usuario@tu-servidor.com

# Ver volúmenes
docker volume ls | grep cadet

# Acceder al contenido
sudo ls -la /var/lib/docker/volumes/cadet-backend_socios_logos/_data/

# Ver imágenes de socios
sudo ls -la /var/lib/docker/volumes/cadet-backend_socios_logos/_data/
```

---

## Comandos Útiles

### Ver todos los volúmenes
```bash
docker volume ls
```

### Inspeccionar un volumen específico
```bash
docker volume inspect cadet-backend_socios_logos
```

### Backup de un volumen
```bash
# Crear backup
docker run --rm -v cadet-backend_socios_logos:/data -v ${PWD}:/backup alpine tar -czf /backup/socios_logos_$(date +%Y%m%d).tar.gz -C /data .

# Restaurar backup
docker run --rm -v cadet-backend_socios_logos:/data -v ${PWD}:/backup alpine sh -c "cd /data && tar -xzf /backup/socios_logos_YYYYMMDD.tar.gz"
```

### Limpiar volúmenes no utilizados
```bash
docker volume prune
```

### Ver espacio utilizado por volúmenes
```bash
docker system df -v
```

---

## Acceder desde el Contenedor en Ejecución

```bash
# Conectarse al contenedor backend
docker exec -it cadet_backend sh

# Navegar a los directorios de imágenes
ls -la /home/SIST_SOCIOS_LOGOS/
ls -la /home/SIST_EMPRESAS_LOGOS/
ls -la /home/SIST_EMPRESAS_CATALOGOS/

# Ver una imagen específica
cat /home/SIST_SOCIOS_LOGOS/nombre-archivo.jpg

# Salir del contenedor
exit
```

---

## Verificar que los Volúmenes Funcionan

### Prueba después de levantar Docker Compose:

```bash
# 1. Sube una imagen desde tu aplicación

# 2. Verifica que se guardó
docker exec cadet_backend ls -la /home/SIST_SOCIOS_LOGOS/

# 3. Reinicia el contenedor
docker-compose restart backend

# 4. Verifica que la imagen sigue ahí
docker exec cadet_backend ls -la /home/SIST_SOCIOS_LOGOS/
```

Si la imagen persiste después del reinicio, ¡los volúmenes están funcionando correctamente! ✅

---

## Configuración en Coolify

Al desplegar en Coolify:

1. **Importa tu repositorio** con el `docker-compose.yml`
2. **Coolify detectará automáticamente los volúmenes** definidos
3. Los volúmenes se crearán en el servidor y persistirán entre despliegues
4. Puedes ver los volúmenes en la sección "Storages" de tu aplicación en Coolify

**Nota Importante:** Los volúmenes son locales al servidor. Si cambias de servidor o usas múltiples instancias, necesitarás una solución diferente (S3, NFS, etc.).

---

## Migrar Datos Existentes

Si ya tienes imágenes en tu sistema local de desarrollo:

```bash
# 1. Copiar desde Windows al volumen Docker
docker run --rm -v cadet-backend_socios_logos:/dest -v C:/SIST_SOCIOS_LOGOS:/source alpine cp -r /source/* /dest/

# 2. Verificar
docker exec cadet_backend ls -la /home/SIST_SOCIOS_LOGOS/
```

---

## Monitoreo del Espacio en Disco

```bash
# Ver tamaño de cada volumen
docker system df -v | grep cadet-backend

# Tamaño total de volúmenes
du -sh /var/lib/docker/volumes/cadet-backend_*
```

---

## 🚨 Importante

- ✅ Los volúmenes **persisten** cuando el contenedor se reinicia o actualiza
- ✅ Los volúmenes **NO se eliminan** con `docker-compose down`
- ⚠️ Para eliminar volúmenes: `docker-compose down -v` (¡cuidado, esto borra todo!)
- ⚠️ Haz **backups regulares** de los volúmenes importantes
- ⚠️ Los volúmenes ocupan espacio en disco, monitorea el uso
