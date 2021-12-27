cd "D:\my_projects\SwissBank"
start /b mvn spring-boot:run > spring-boot.out 2>&1
cd "D:\my_projects\SwissBank\src\reacts"
start /b npm start > "D:\my_projects\SwissBank\react-app.out" 2>&1
