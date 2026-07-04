Objective:
Magnetic Cart is a system that receives real-time data from a serial port (COM), processes it according to defined rules, and displays it through a web application.

What is the "cart" physically?
It is a mobile unit that moves along an aluminum profile. The mobile unit is suspended in the air using neodymium magnets, resulting in almost zero friction.

What does it measure/send (sensors, position, speed)?
It features optical sensors that detect the passing of the cart, and a microcontroller-based system measures the times in milliseconds.

What is the educational or practical objective of the project?
It was created as an educational tool to teach robotics and kinematics concepts in schools, allowing students to visualize in real time the data that a cart equipped with magnetic sensors sends to a computer.

The project is divided into three independent modules:

- driver       Connects to the hardware / COM port and captures raw data  
- cm-server    Processes the received data and exposes an API
- cm-web       Web interface to visualize the processed data


Features:
- Real-time data reading via COM port
- Data processing and transformation according to business rules
- Web visualization of the results

Installation and Usage
Prerequisites
- Java 8+ and Maven
- Node.js and npm
- Hardware-specific driver CH341SER.EXE (Windows)

1. Clone the repository\
   git clone https://github.com/gsuenaga/CarroMagnetico.git \
   cd CarroMagnetico

2. Start the server (cm-server)\
   cd cm-server\
   mvn clean install\
   mvn spring-boot:run

3. Start the web application (cm-web)\
   cd cm-web\
   npm install\
   npm start
