# ConnessioneTCP
Struttura della repository e dei branch
### master
input gestito con BufferedReader, output gestito con PrintWriter
### BufferedReaderAndBufferedWriter
input gestito con BufferedReader, output gestito con BufferedWriter
Il BufferedWriter non funzionava inizialmente, il problema era il non inserimento di un carattere di newLine alla fine della trasmissione del messaggio (per qualche motivo). Risolto aggiungendo i metodi newLine() e flush().
