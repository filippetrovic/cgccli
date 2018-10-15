# CGC command line tool 
The Cancer Genomics Cloud (CGC), powered by Seven Bridges, is one of three pilot systems funded by the National Cancer Institute to explore the paradigm of colocalizing massive genomics datasets, like The Cancer Genomics Atlas (TCGA), alongside secure and scalable computational resources to analyze them.

### CLI tool supports following operations

* List projects (​https://docs.cancergenomicscloud.org/docs/list-all-your-projects​)
* List files in project (​https://docs.cancergenomicscloud.org/docs/list-files-in-a-project​)
* Get file details (​https://docs.cancergenomicscloud.org/docs/get-file-details​)
* Update file details (​https://docs.cancergenomicscloud.org/docs/update-file-details​)
* Download file (​https://docs.cancergenomicscloud.org/docs/get-download-url-for-a-file​)

### Sample usage
~~~
cgccli --token {token} projects list 
cgccli --token {token} files list --project test/simons-genome-diversity-project-sgdp 
cgccli --token {token} files stat --file {file_id} 
cgccli --token {token} files update --file {file_id} name=bla 
cgccli --token {token} files update --file {file_id} metadata.sample_id=asdasf 
cgccli --token {token} files download --file {file_id} --dest /tmp/foo.bar
~~~
