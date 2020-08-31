/**
 * Package che contiene i vari splitter:
 * 
 * FileLocation - padre di tutti gli splitter, contiene solo i file path e i file input/output
 * SplitterInterface - interfaccia degli splitter, contenente split() e join()
 * NByteSplitter - splitter che divide un file dando come input la dimensione dei vari pezzi splittati
 * ZipSplitter - splitter che estende NByteSplitter, zippando anche ogni file ottenuto
 * NPartsSplitter - splitter che divide un file dando come input la quantita'  finale di file che vogliamo avere
 * CryptoSplitter - splitter che estende NByteSplitter, rendendo sicuri i file divisi tramite una chiave di cifratura
 * 
 * @author Gamberi Elia
 */
package splitters;