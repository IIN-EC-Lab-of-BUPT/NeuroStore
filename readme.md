# NeuroStore

NeuroStore is an open source distributed data storage platform dedicated to data storage and management in the field of brain-computer interfaces.

## BackGround
In brain-computer interface applications, it is necessary to collect information at all stages of the use process, and to process the information in terms of aggregation, calculation, and sorting to extract the value of it. In addition, brain-machine interface research is gradually becoming systematic and standardized. Data is an important resource in research, and the standardized classification, sorting, and label management of long-term accumulated data will become essential. If there is a data platform that can provide data management and processing functions, in terms of application, business value can be mined from data to drive business with data; in terms of research work, it will reduce the difficulty of researchers' research work, reduce the probability of errors in data management, and improve the efficiency of researchers.

## Features

* Concurrent streaming storage: The system can store data continuously from one or more data sources, even if these sources do not have a clear end point. The system ensures data correctness and consistency while storing data from multiple sources concurrently.
* Concurrent querying: The system provides concurrent querying capabilities, allowing users to perform multiple and diverse queries simultaneously. This functionality can handle high concurrency scenarios, such as serving multiple processes as a data center, quickly meeting user query requirements.
* Multi-modal data management: The system can store different types of data, including physiological data, speech, images, and other types of data. Additionally, the system can store descriptive information about multi-modal data, such as tags and metadata. Users can quickly locate the desired multi-modal data through retrieval and aggregation of these descriptive information.
* Process information management: The system can store relevant information related to brain-computer interface usage, such as participant information and experimental results. The system can also establish relationships between this information to enable users to combine and analyze this information in data processing and gain more scientific discoveries.


## Characteristics

* Model universality: NeuroStore uses a universal data model based on the BIDS data specification and the OHBM COBIDAS MEEG specification, which can describe common data in brain-computer interface research, such as EEG (Electroencephalogram), MEG (Magnetoencephalography), and other research information such as stage results and related personnel. The universal data model makes NeuroStore suitable for various brain-computer interface projects.More infomation see [Datamodel](https://www.processon.com/view/link/640c18faeb191b58c3edbb1b)
* Interface universality: NeuroStore's interface is developed using the world's popular programming language, Python, which can collect various types of data, such as EEG, images, eye-tracking, etc. NeuroStore also provides interfaces with various querying functions, and complex queries can be achieved by combining multiple simple interfaces to meet various querying requirements. NeuroStore can store and query common data in brain-computer interface research to support subsequent analysis of scientific research and can be applied to other brain-computer interface systems as a data center to provide data services for other modules.
* Flexibility: NeuroStore adopts a modular design and scalable architecture, which can expand the system's performance and storage capacity by adding new hardware devices. This can make NeuroStore more suitable for project requirements while reducing project costs.
* Ease of use: NeuroStore is deployed using container technology, and users can deploy the system's various components with a single configuration change. The system does not require high-performance servers or personnel with specialized software knowledge to deploy and manage. NeuroStore provides simple and understandable interfaces that are easy for interested researchers to use.


## Installation
### NeuroStore installation
Before installing NeuroStore, you need to install Docker. You can refer to the official documentation of Docker for specific steps.

After installing Docker , you can install the NeuroStore project using the following command:

1. Import the datamanager image
   ```sh
   sudo docker load -i datamanagerimage.tar
   ```
2. Import the database image
   ```sh
   sudo docker load -i mysqlimage.tar
   ```
3. Import the Message Queue image
   ```sh
   sudo docker load -i Queue-autolaunch-image-file.tar
   ```
   
4. Decompress DataManager.tar and remember file address

5. run datamanager
   ```sh
   sudo docker run --name=datamanagerc \
   --restart=always \
   -p 0.0.0.0:8083:8081\
   --add-host=server:10.112.120.252 \
   --add-host=mysqlAddr:10.112.184.38 \
   --mount type=bind,src=DestinationFolder,dst=/opt/DataManager \
   -d datamanager:1.0 
   ```
6. unzip database.zip and remember file address

7. run database
   ```sh
   sudo docker run --name=mysql\
   -p 0.0.0.0:3306:3306\
   --mount type=bind,src=DestinationFolder/database/config/my.cnf,dst=/etc/my.cnf \
   --mount type=bind,src=DestinationFolder/database/datadir,dst=/var/lib/mysql \
   -e MYSQL_ROOT_PASSWORD=root \
   -d mysql/mysql-server:latest
   ```
8. run MessageQueue
   ```sh
   sudo docker run -d -it -p 0.0.0.0:2222:22 -p 0.0.0.0:60000:9092 -p 0.0.0.0:60001:2181 --name="KafkaServerContainer" -h KafkaServerDocker --add-host=server:192.168.31.31 --privileged=true --restart=always kafka-image:1.1.1 /usr/sbin/init
   ```
After NeuroStore is started, you can access the NeuroStore web interface by visiting http://localhost:8083.

### API Insatllation 
Before installing API, you need to install Python 3.Then using the following command:

```sh
   pip install PlatformAPI-1.0.0-py3-none-any.whl
 ```  
## Contribution guide

If you would like to contribute code or feedback questions to NeuroStore, you can do so in the following ways:

* Submit Questions or Suggestions: If you experience any problems or have any suggestions while using NeuroStore, please submit an Issue. We'll get back to you as soon as possible.
* Submit code: If you would like to submit code for NeuroStore, Fork the project first, then make changes in your own repository, and submit a Pull Request to us. We will review your code and merge it as soon as possible.
* Contact us: If you would like to contact us, please email JelenaBeatrice387@outlook.com

## License

Distributed under the GNU General Public License v2.0 License. See `LICENSE` for more information.

## Contact

Email: JelenaBeatrice387@outlook.com

## Acknowledgements
- [kafka](https://github.com/apache/kafka)
- [kafka-python](https://github.com/dpkp/kafka-python)
- [MySQL](https://github.com/mysqljs/mysql)
- [BIDS](https://bids.neuroimaging.io/)
- [COBIDAS](https://www.humanbrainmapping.org/cobidas)
