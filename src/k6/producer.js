import exec from 'k6/execution';
import {
  SharedArray
} from 'k6/data';
import http from 'k6/http';
import {
  check,
  fail,
  sleep
} from 'k6';

//How to run: k6 run script.js - BEFORE RUNNING, REVIEW CONFIGURATIONS

//------------- INPUTS - BEGIN -----------------------------------------
//k6 executor options
const rpm = 120; //amount of request per minute
const testDuration = '30m'; //how long the test will run
var number = 1; //message identifier

const TOPIC_CONFIGURATION = {
    topic: "topic-1",
    key: "kafka-examples-key",
    partition: 0
};

const CONFIGURATION = {
    host: "http://localhost",
    port: "8081",
    message: "mensagem ",
    paths: {
        topic: "/topics/" + TOPIC_CONFIGURATION.topic + "/messages",
        topicKey: "/topics/" + TOPIC_CONFIGURATION.topic + "/keys/" + TOPIC_CONFIGURATION.key + "/messages",
        topicKeyPartition: "/topics/" + TOPIC_CONFIGURATION.topic + "/keys/" + TOPIC_CONFIGURATION.key + "/partitions/" + TOPIC_CONFIGURATION.partition + "/messages",
    }
};

//------------- INPUTS - END -----------------------------------------

//test scenarios - default is 1 execution
export const options = {
  scenarios: {
    contacts: {
      executor: 'constant-arrival-rate',

      // Our test should last {testDuration} seconds in total
      duration: testDuration,

      // It should start {rpm} iterations per `timeUnit`. Note that iterations starting points
      // will be evenly spread across the `timeUnit` period.
      rate: rpm,

      // It should start `rate` iterations per min
      timeUnit: '60s',

      // It should preallocate 60 VUs before starting the test
      preAllocatedVUs: 120,

      // It is allowed to spin up to 60 maximum VUs to sustain the defined
      // constant arrival rate.
      maxVUs: 120,
    },
  },
};

function getPath() {
    return CONFIGURATION.host + ":" + CONFIGURATION.port + CONFIGURATION.paths.topic;
}

function sendMessage() {
  const res = http.post(getPath(), CONFIGURATION.message + new Date(), {})

  if (!check(res, {
      'Mensagem enviada': (r) => r.status == 200
    })) {
    fail('falha ao enviar mensagem id ' + id)
  }
}

//-------------------- BEGIN ----------------------------------------

export default function () {
  sendMessage();
}
//-------------------- END ----------------------------------------
