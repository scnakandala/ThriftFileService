namespace java org.apache.airavata.services

service FileResource {
	void init()
	binary downloadFile(1: string accessToken, 2: string path)
	bool uploadFileForPath(1: string accessToken, 2: string path, 3: string fileName, 4: binary buffer)
	bool uploadFileForExperiment(1: string accessToken, 2: string experimentId, 3: string fileName, 4: binary buffer)
	list<string> listOutputFilesForExperiment(1: string accessToken, 2: string experimentId)
	list<string> listInputFilesForExperiment(1: string accessToken, 2: string experimentId)
	list<string> listFilesInPath(1: string accessToken, 2: string path)
	bool deleteDir(1: string accessToken, 2: string path)
	bool deleteFile(1: string accessToken, 2: string path)
	bool rename(1: string accessToken, 2: string path, 3: string name)
	bool getRemoteFile(1: string accessToken, 2: string host, 3: string path)
	bool isDirectoryExists(1: string accessToken, 2: string path)
	bool isFileExists(1: string accessToken, 2: string path)
}
