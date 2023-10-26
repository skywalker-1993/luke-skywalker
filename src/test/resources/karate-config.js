function fn() {

  const env = karate.env;

  let config = {
    reqresUrl: "https://reqres.in",
    mockserverUrl: "http://localhost:1080",
    userName: "eve.holt@reqres.in",
    userPassword: "cityslicka",
    env: "dev"
  }

  if (env === "qa") {
    //Here the config attribute values can be overwritten according to the test environment
    config.env = "qa"
    config.mockserverUrl ="http://mockserver:1080"
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