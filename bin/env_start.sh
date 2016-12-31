../sbin/flume/bin/flume-ng agent -c conf -f ../sbin/flume/conf/avro.conf -n a1 -Dflume.root.logger=DEBUG,console &
../sbin/kafka/bin/zookeeper-server-start.sh ../sbin/kafka/config/zookeeper.properties &
../sbin/kafka/bin/kafka-server-start.sh ../sbin/kafka/config/server.properties &
../sbin/hadoop/sbin/start-all.sh