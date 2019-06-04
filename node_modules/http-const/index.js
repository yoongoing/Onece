var httpConst = {
  methods: {
    get: 'GET',
    head: 'HEAD',
    post: 'POST',
    put: 'PUT',
    del: 'DELETE',
    trace: 'TRACE',
    options: 'OPTIONS',
    connect: 'CONNECT',
    patch: 'PATCH'
  },
  
  protocols: {
    http: 'HTTP',
    https: 'HTTPS'
  },
  
  protocolVersions: {
    v10: 'HTTP/1.0',
    v11: 'HTTP/1.1',
    v12: 'HTTP/1.2',
    v20: 'HTTP/2.0'
  },
  
  headers: {
    contentType: 'Content-Type',
    contentLength: 'Content-Length'
  },
  
  contentTypes: {
    formData: 'multipart/form-data',
    xWwwFormUrlencoded: 'application/x-www-form-urlencoded',
    json: 'application/json',
    plain: 'text/plain'
  },
  
  boundaryParam: 'boundary',
};

httpConst.arrays = {
  methods: [
    httpConst.methods.get,
    httpConst.methods.head,
    httpConst.methods.post,
    httpConst.methods.put,
    httpConst.methods.del,
    httpConst.methods.trace,
    httpConst.methods.options,
    httpConst.methods.connect,
    httpConst.methods.patch
  ],
  
  protocols: [
    httpConst.protocols.http,
    httpConst.protocols.https
  ],
  
  protocolVersions: [
    httpConst.protocolVersions.v10,
    httpConst.protocolVersions.v11,
    httpConst.protocolVersions.v12,
    httpConst.protocolVersions.v20
  ]
};

module.exports = httpConst;
