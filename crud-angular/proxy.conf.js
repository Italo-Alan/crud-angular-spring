//O Proxy serve para podermos fazer requisição do front para o back sem ter que configurar o CORS
const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://localhost:8080/',
    secure: false,
    logLevel: 'debug'
  }
];

module.exports = PROXY_CONFIG;

//É configurado no package.json que quando for feito o ng serve ele configura o proxy
//Com isso, passaremos a usar o cmd "npm run start"
