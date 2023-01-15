function fn() {

  let config = {
    reqresUrl: "https://reqres.in",
    userName: "eve.holt@reqres.in",
    userPassword: "cityslicka"
  }

  let result = {
    ...karate.callSingle('../HelpFeatures/ImportDependencies.feature', config),
  }

  for (const key in result) {
    config[key] = result[key]
  }

  karate.configure('retry', { count: 30, interval: 1000});

  karate.configure('logPrettyRequest', true);
  karate.configure('logPrettyResponse', true);

  return config;

}