# ConnessioneTCP
Struttura della repository e dei branch: mentre gli altri branch hanno ancora una struttura disordinata e una filosofia di programmazione imperativa, il branch master è più ordinato e strutturato in classi e metodi, è inoltre commentato molto meglio. Tralasciando l'ordine la differenza sostanziale tra i vari branch è l'impiego di diverse tipologie di Stream, di seguito lascio la descrizione del contenuto di ogni branch, nella descrizione del branch master sono contenuti anche i diagrammi delle classi e gli schemi relativi all'esercizio.
### master
input gestito con Scanner, ouput gestito con PrintWriter.
![UML](https://i.imgur.com/KYEHRIF.png)
![Schema](https://i.imgur.com/ZYVEkjO.png)
### BufferedReaderAndBufferedWriter
input gestito con BufferedReader, output gestito con BufferedWriter
Il BufferedWriter non funzionava inizialmente, il problema era il non inserimento di un carattere di newLine alla fine della trasmissione del messaggio (per qualche motivo). Risolto aggiungendo i metodi newLine() e flush().
### BufferedReaderAndPrintWriter
input gestito con BufferedReader, output gestito con PrintWriter
### unstable
branch dedicato alla sperimentazione e alla prova, utilizzato principalemnte per effettuare la conversione a oggetti del codice
